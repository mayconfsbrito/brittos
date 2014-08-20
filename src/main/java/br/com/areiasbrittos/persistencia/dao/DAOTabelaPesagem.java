/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.persistencia.dao;

import br.com.areiasbrittos.gui.FramePrincipal;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import br.com.areiasbrittos.controle.objetos.Tabelapesagem;
import br.com.areiasbrittos.controle.objetos.seguranca.Transacao;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOTransacao;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class DAOTabelaPesagem {

    private DAOTransacao DAOtransacao = new DAOTransacao();
    private Session sessao;
    Transaction transacao = null;

    public DAOTabelaPesagem() {
        inicializaSessao();
    }

    /**
     * Inicializa sessão com o banco de dados
     */
    public final void inicializaSessao() {
        this.sessao = hibernateSessionFactory_brittos_bd.getSession();
    }

    public void inserir(Tabelapesagem itemTabela) {
        try {

            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.save(itemTabela);
            transacao.commit();

            /*Registra o Dedo duro*/
            DAOtransacao.inserir(new Transacao(FramePrincipal.user, "Inseriu " + itemTabela.getNome() + " na tabela de pesagem"));

        } catch (HibernateException er) {

            JOptionPane.showMessageDialog(null, "Não foi possível cadastrar este item na tabela de preços!\n" + er,
                    "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            transacao.rollback();
            this.sessao = hibernateSessionFactory_brittos_bd.getSession();

        } finally {

            sessao.close();

        }
    }

    public void alterar(Tabelapesagem itemTabela) {
        try {
            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.update(itemTabela);
            transacao.commit();

            JOptionPane.showMessageDialog(null, "Item alterado com sucesso!", "Alteração", JOptionPane.INFORMATION_MESSAGE);

            /*Registra o Dedo duro*/
            DAOtransacao.inserir(new Transacao(FramePrincipal.user, "Alterou " + itemTabela.getNome() + " na tabela de pesagem"));

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar o item.\n" + er,
                    "Erro!", JOptionPane.ERROR_MESSAGE);
            transacao.rollback();
        } finally {
            sessao.close();
        }
    }

    public void excluir(Tabelapesagem itemTabela) {
        try {

            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.delete(itemTabela);
            transacao.commit();
            JOptionPane.showMessageDialog(null, itemTabela.getNome() + " excluido(a) com sucesso!", "Exclusão", JOptionPane.INFORMATION_MESSAGE);

            /*Registra o Dedo duro*/
            DAOtransacao.inserir(new Transacao(FramePrincipal.user, "Excluiu " + itemTabela.getNome() + " na tabela de pesagem"));

        } catch (HibernateException er) {

            JOptionPane.showMessageDialog(null, "Não foi possível excluir " + itemTabela.getNome()
                    + ".\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
            transacao.rollback();

        } finally {
            sessao.close();
        }
    }

    public void excluirVarios(int[] codigo) {
        try {
            String str = " ";

            for (int i = 0; i < codigo.length; i++) {
                str += "idtabelaPesagem=" + codigo[i];
                if ((i + 1) < codigo.length) {
                    str += " or ";
                }
            }

            List<Tabelapesagem> list = consultar(str);



            for (int i = 0; i < codigo.length; i++) {
                list.get(i).setIdTabelaPesagem(codigo[i]);
                excluir(list.get(i));
            }

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir estes items "
                    + ".\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List consultar(String str) {
        try {

            inicializaSessao();
            transacao = sessao.beginTransaction();
            List list = sessao.createQuery("from Tabelapesagem where " + str).list();
            sessao.close();
            return list;

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar os itens da tabela de preços!\n" + er, "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }

    public List listar() {
        try {

            inicializaSessao();
            List list = sessao.createQuery("from Tabelapesagem").list();
            sessao.close();
            return list;

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível listar os itens da tabela de preços!\n" + er, "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
