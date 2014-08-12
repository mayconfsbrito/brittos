/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.persistencia.dao;

import br.com.areiasbrittos.gui.FramePrincipal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import br.com.areiasbrittos.controle.objetos.Entidade;
import br.com.areiasbrittos.controle.objetos.seguranca.Transacao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOTransacao;

/**
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class DAOEntidade {

    private DAOTransacao DAOtransacao = new DAOTransacao();    
    private Session sessao;
    Transaction transacao = null;

    public DAOEntidade() {
        inicializaSessao();
    }

    public final void inicializaSessao() {
        this.sessao = hibernateSessionFactory_brittos_bd.getSession();
    }

    public void inserir(Entidade entidade) {
        try {
            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.save(entidade);
            transacao.commit();
            
            /*Registra o Dedo duro*/
            DAOtransacao.inserir(new Transacao(FramePrincipal.user, "Cadastrou a entidade " + entidade.getNome()));
            
        } catch (ConstraintViolationException er) {
            JOptionPane.showMessageDialog(null, entidade.getNome()
                    + " já existe portanto não é possível cadastrá-lo(a)!", "ERRO!", JOptionPane.ERROR_MESSAGE);
            transacao.rollback();
            this.sessao = hibernateSessionFactory_brittos_bd.getSession();
        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível cadastrar a entidade!\n" + er,
                    "Erro!", JOptionPane.ERROR_MESSAGE);
            transacao.rollback();
            this.sessao = hibernateSessionFactory_brittos_bd.getSession();
        } finally {
            sessao.close();
        }
    }

    //Exclui apenas uma entidade apartir de um objeto recuperado
    public void excluir(Entidade entidade) {
        try {
            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.delete(entidade);
            transacao.commit();
            JOptionPane.showMessageDialog(null, entidade.getNome() + " excluido(a)  com sucesso!", "Exclusão",
                    JOptionPane.INFORMATION_MESSAGE);
                        
            /*Registra o Dedo duro*/
            DAOtransacao.inserir(new Transacao(FramePrincipal.user, "Excluiu a entidade " + entidade.getNome()));
            
        } catch(ConstraintViolationException er){
            JOptionPane.showMessageDialog(null, "Não é possível excluir entidades que já realizaram transações.\n", "Erro!", JOptionPane.ERROR_MESSAGE);
            transacao.rollback();
        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir " + entidade.getNome()
                    + ".\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
            transacao.rollback();
        } finally {
            sessao.close();
        }
    }

    //Exclui mais de uma entidade através dos respectivos códigos das mesmas
    public void excluirVarios(int[] codigo) {
        try {
            String str = " ";

            for (int i = 0; i < codigo.length; i++) {
                str += "idEntidade=" + codigo[i];
                if ((i + 1) < codigo.length) {
                    str += " or ";
                }
            }

            List<Entidade> list = consultar(str);

            for (int i = 0; i < codigo.length; i++) {
                list.get(i).setIdEntidade(codigo[i]);
                excluir(list.get(i));
            }
        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir estas entidades "
                    + ".\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void alterar(Entidade entidade) {
        try {
            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.update(entidade);
            transacao.commit();
            JOptionPane.showMessageDialog(null, entidade.getNome() + " alterado(a)  com sucesso!", "Alteração",
                    JOptionPane.INFORMATION_MESSAGE);
                        
            /*Registra o Dedo duro*/
            DAOtransacao.inserir(new Transacao(FramePrincipal.user, "Alterou a entidade " + entidade.getNome()));
            
        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar" + entidade.getNome() + ".\n" + er,
                    "Erro!", JOptionPane.ERROR_MESSAGE);
            transacao.rollback();
        } finally {
            sessao.close();
        }
    }

    //Consulta entidades a partir dos parâmetos inseridos na String
    public List<Entidade> consultar(String str) {

        inicializaSessao();
        transacao = sessao.beginTransaction();
        List<Entidade> list = sessao.createQuery("from Entidade where " + str).list();
        sessao.close();

        return list;

    }

    /**
     * Lista todos os objetos Entidade
     * @return lista com todos os objetos Entidade
     */
    public List listar(String order) {
        try {

            inicializaSessao();
            transacao = sessao.beginTransaction();
            List list = sessao.createQuery("from Entidade order by " + order).list();
            sessao.close();

            return list;

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível listar as entidades!\n" + er, "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Inicializa um jComboBox com todas as entidades presentes no BD
     * @param primeiraPosicao
     * @param tipo
     * @return 
     */
    public DefaultComboBoxModel inicializaComboBoxEntidades(String primeiraPosicao, String tipo) {
        try {

            List<String> list = new ArrayList<String>();

            if(!tipo.equals("Todos")){
                list = sessao.createQuery("select nome from Entidade where tipo like '%"+ tipo +"%' order by nome").list();
            } else{
                list = sessao.createQuery("select nome from Entidade order by nome").list();
            }


            DefaultComboBoxModel model = new DefaultComboBoxModel();

            model.addElement(primeiraPosicao);
            for (int i = 0; i < list.size(); i++) {
                model.addElement(list.get(i));
            }

            sessao.close();

            return model;

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar o ComboBox de Clientes.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public Entidade getEntidade(String nome){

        List<Entidade> list = consultar("nome like \'" + nome + "\'");

        if(list.isEmpty()){

            JOptionPane.showMessageDialog(null, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;

        } else{
            
            return list.get(0);
            
        }
    }
}
