/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.persistencia.dao.seguranca;

import java.sql.Date;
import java.util.List;
import javax.swing.JOptionPane;
import br.com.areiasbrittos.controle.objetos.seguranca.Transacao;
import br.com.areiasbrittos.controle.objetos.seguranca.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import br.com.areiasbrittos.persistencia.dao.hibernateSessionFactory_bd_Seguranca;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class DAOTransacao {

    private static Session sessao;
    private static Transaction transacao = null;

    public DAOTransacao() {
        this.inicializaSessao();
    }

    public static final void inicializaSessao() {
        sessao = hibernateSessionFactory_bd_Seguranca.getSession();
    }

    /*Armazena a trasacao realizada pelo usuario*/
    public static void inserir(Transacao tran) {
        try {
            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.save(tran);
            transacao.commit();

        } catch (ConstraintViolationException er) {
            transacao.rollback();

        } catch (HibernateException er) {
            transacao.rollback();
            er.printStackTrace();
        }
    }

    /*
     * Consulta transações a partir dos parâmetos inseridos na String
     */
    public static List consultar(String str) {

        inicializaSessao();
        transacao = sessao.beginTransaction();
        List list = sessao.createQuery("from Transacao where " + str).list();
        sessao.close();

        return list;

    }

    /**
     * Lista todos as Transações
     * @return lista com todos os objetos Transacao
     */
    public static List listar() {
        try {

            inicializaSessao();
            transacao = sessao.beginTransaction();
            List list = sessao.createQuery("from Transacao").list();
            sessao.close();

            return list;

        } catch (HibernateException er) {
            return null;
        }
    }

    /*
     * Exclui todas as transações dedo-duro que ocorreram antes de @data
     * @data - data limite para exclusão de transações dedo-duro
     */
    public static boolean excluir(Date data) {
        try {


            List<Transacao> listTran = DAOTransacao.consultar("data<='" + data.toString() + "'");
            for (int index = 0; index < listTran.size(); index++) {
                DAOTransacao.excluir(listTran.get(index));
            }

            /*Registra o Dedo duro*/
            if (listTran.size() > 0) {
                DAOTransacao dao = new DAOTransacao();
                Usuario systemUser = new DAOUsuario().consultar("idUsuario=1").get(0);
                dao.inserir(new Transacao(systemUser, "Excluiu dedo-duro até " + data.toString()));
            }

            return true;

        } catch (HibernateException er) {

            JOptionPane.showMessageDialog(null, "Não foi possível excluir as transações dedo-duro antes de " + data.toString() + ".\n", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    /**
     * Exclui uma unica transacao que é passada por parâmetro
     */
    public static boolean excluir(Transacao tran) {

        try {
            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.delete(tran);
            transacao.commit();

            return true;

        } catch (HibernateException er) {

            JOptionPane.showMessageDialog(null, "Não foi possível excluir a transação dedo-duro " + tran.getIdTransacao() + ".\n", "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            transacao.rollback();

            return false;
        }
    }
}