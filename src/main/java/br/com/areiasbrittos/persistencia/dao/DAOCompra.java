/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.persistencia.dao;

import br.com.areiasbrittos.controle.objetos.Compra;
import br.com.areiasbrittos.controle.objetos.seguranca.Transacao;
import br.com.areiasbrittos.controle.utils.Dates;
import br.com.areiasbrittos.controle.utils.Horas;
import br.com.areiasbrittos.gui.FramePrincipal;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import br.com.areiasbrittos.persistencia.dao.AbstractDAO;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOTransacao;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class DAOCompra extends AbstractDAO {

    /**
     * Seta uma transação como concluída
     *
     * @param compra
     */
    public void concluir(Compra compra) {
        try {
            /**
             * Verifica se a transação já foi concluída*
             */
            if (!compra.isConcluida()) {
                /**
                 * Conclui a transação*
                 */
                compra.setConcluida(true);
            }

            /**
             * Salva no BD*
             */
            inicializaSessao();
            transacao = sessao.beginTransaction();
            compra.setDataAlteracao(Dates.getDataHoje());
            compra.setHoraAlteracao(Horas.getHoraAgora());
            sessao.update(compra);
            transacao.commit();

            /*
             * Registra o Dedo duro
             */
            DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Concluiu a Compra " + compra.getIdCompra()));

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível concluir a Compra.\n" + er,
                    "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            transacao.rollback();
        } finally {
            sessao.flush();
            sessao.close();
        }
    }

    /**
     * Conclui varias transações
     */
    public void concluirVarios(int[] codigo) {
        try {
            String str = " ";

            /*
             * Monta a string de consulta com os ids das transações a serem alteradas
             */
            for (int i = 0; i < codigo.length; i++) {
                str += "idCompra=" + codigo[i];
                if ((i + 1) < codigo.length) {
                    str += " or ";
                }
            }

            /*
             * Carrega @list com as compras consultadas no bd
             */
            List<Compra> list = consultar("Compra", str);

            /*
             * Conclui uma por uma e salva no bd
             */
            for (int i = 0; i < codigo.length; i++) {
                concluir(list.get(i));
            }

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível concluir estas compras "
                    + ".\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
        }
    }

    /**
     * Desconclui uma compra
     *
     * @param compra
     */
    public void desconcluir(Compra compra) {
        try {
            
            //Verifica se a transação esta foi concluída*
            if (compra.isConcluida()) {
                //Verifica se a compra não está quitada
                if (!compra.isQuitada()) {
                    //Desconclui a transação
                    compra.setConcluida(false);
                } else {
                    JOptionPane.showMessageDialog(null, "A compra de código " + compra.getIdCompra() + " não pode ser desconcluída pois já está quitada.",
                            "Atenção!", JOptionPane.WARNING_MESSAGE);
                }
            }

            //Salva no BD*
            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.update(compra);
            transacao.commit();

            //Registra o Dedo duro
            DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Desconcluiu a Compra " + compra.getIdCompra()));

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível desconcluir a Compra.\n" + er,
                    "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            transacao.rollback();
        } finally {
            sessao.flush();
            sessao.close();
        }
    }

    /**
     * Desconclui varias transações
     */
    public void desconcluirVarios(int[] codigo) {
        try {
            String str = " ";

            //Monta a string de consulta com os ids das transações a serem alteradas
            for (int i = 0; i < codigo.length; i++) {
                str += "idCompra=" + codigo[i];
                if ((i + 1) < codigo.length) {
                    str += " or ";
                }
            }

            //Carrega @list com as compras consultadas no bd
            List<Compra> list = consultar("Compra", str);

            //Conclui uma por uma e salva no bd
            for (int i = 0; i < codigo.length; i++) {

                desconcluir(list.get(i));

            }

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível concluir estas compras "
                    + ".\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
        }
    }
}
