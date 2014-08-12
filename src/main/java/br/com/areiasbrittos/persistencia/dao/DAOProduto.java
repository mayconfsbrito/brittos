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
import org.hibernate.exception.ConstraintViolationException;
import br.com.areiasbrittos.controle.objetos.Produto;
import br.com.areiasbrittos.controle.objetos.seguranca.Transacao;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOTransacao;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class DAOProduto {

    private DAOTransacao DAOtransacao = new DAOTransacao();
    private Session sessao;
    Transaction transacao = null;

    public DAOProduto() {
        inicializaSessao();
    }

    public final void inicializaSessao() {
        this.sessao = hibernateSessionFactory_brittos_bd.getSession();
    }

    public void inserir(Produto produto) {
        try {

            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.save(produto);
            transacao.commit();

            /*Registra o Dedo duro*/
            DAOtransacao.inserir(new Transacao(FramePrincipal.user, "Cadastrou o produto " + produto.getNome()));


        } catch (ConstraintViolationException er) {

            JOptionPane.showMessageDialog(null, produto.getNome()
                    + " já existe portanto não é possível cadastrá-lo(a)!", "ERRO!", JOptionPane.ERROR_MESSAGE);
            transacao.rollback();
            this.sessao = hibernateSessionFactory_brittos_bd.getSession();

        } catch (HibernateException er) {

            JOptionPane.showMessageDialog(null, "Não foi possível cadastrar o produto!\n" + er,
                    "Erro!", JOptionPane.ERROR_MESSAGE);
            transacao.rollback();
            this.sessao = hibernateSessionFactory_brittos_bd.getSession();

        } finally {
            sessao.close();
        }
    }

    //Exclui apenas um produto apartir de um objeto recuperado
    public void ativarDesativar(Produto produto) {
        try {

            String mensagem = "";

            if (produto.isAtivo()) {
                produto.setAtivo(false);
            } else {
                produto.setAtivo(true);
            }

            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.update(produto);
            transacao.commit();

            if (produto.isAtivo()) {
                mensagem = produto.getNome() + " ativado com sucesso!";

                /*Registra o Dedo duro*/
                DAOtransacao.inserir(new Transacao(FramePrincipal.user, "Ativou o produto " + produto.getNome()));

            } else {
                mensagem = produto.getNome() + " desativado com sucesso!";

                /*Registra o Dedo duro*/
                DAOtransacao.inserir(new Transacao(FramePrincipal.user, "Desativou o produto " + produto.getNome()));
            }

            JOptionPane.showMessageDialog(null, mensagem, "Desativação",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível desativar " + produto.getNome()
                    + ".\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
            transacao.rollback();
        } finally {
            sessao.close();
        }
    }

    //Exclui mais de um produto através dos respectivos códigos das mesmas
    public void ativarDesativarVarios(int[] codigo) {
        try {
            String str = " ";

            for (int i = 0; i < codigo.length; i++) {
                str += "idProduto=" + codigo[i];
                if ((i + 1) < codigo.length) {
                    str += " or ";
                }
            }

            List<Produto> list = consultar(str);

            for (int i = 0; i < codigo.length; i++) {
                list.get(i).setIdProduto(codigo[i]);
                ativarDesativar(list.get(i));
            }
        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível ativar/desativar estes produtos "
                    + ".\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void alterar(Produto produto) {
        try {
            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.update(produto);
            transacao.commit();
            JOptionPane.showMessageDialog(null, produto.getNome() + " alterado(a) com sucesso!", "Alteração",
                    JOptionPane.INFORMATION_MESSAGE);
            
            /*Registra o Dedo duro*/
            DAOtransacao.inserir(new Transacao(FramePrincipal.user, "Alterou o produto " + produto.getNome()));
            
        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar" + produto.getNome() + ".\n" + er,
                    "Erro!", JOptionPane.ERROR_MESSAGE);
            transacao.rollback();
        } finally {
            sessao.close();
        }
    }

    //Consulta produtos a partir dos parâmetos inseridos na String
    public List consultar(String str) {
        inicializaSessao();
        transacao = sessao.beginTransaction();
        List list = sessao.createQuery("from Produto where " + str).list();
        sessao.close();
        return list;

    }

    public List listar() {
        try {
            inicializaSessao();
            transacao = sessao.beginTransaction();
            List list = sessao.createQuery("from Produto").list();
            sessao.close();
            return list;
        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível listar os Produtos!" + er, "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
