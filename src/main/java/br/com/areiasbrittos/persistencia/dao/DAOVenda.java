/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.persistencia.dao;

import br.com.areiasbrittos.controle.objetos.Venda;
import br.com.areiasbrittos.controle.objetos.seguranca.Transacao;
import br.com.areiasbrittos.controle.utils.Dates;
import br.com.areiasbrittos.controle.utils.Horas;
import br.com.areiasbrittos.gui.FramePrincipal;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOTransacao;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class DAOVenda extends AbstractDAO {

    /**
     * Seta uma transação como concluída
     *
     * @param venda
     */
    public void concluir(Venda venda) {
        try {
            
            //Verifica se a transação já foi concluída*
            if (!venda.isConcluida()) {
                //Conclui a transação
                venda.setConcluida(true);
            }

            //Salva no BD*
            inicializaSessao();
            transacao = sessao.beginTransaction();
            venda.setDataAlteracao(Dates.getDataHoje());
            venda.setHoraAlteracao(Horas.getHoraAgora());
            sessao.update(venda);
            transacao.commit();

            //Registra o Dedo duro
            DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Concluiu a venda " + venda.getIdVenda()));

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível concluir a Venda.\n" + er,
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

            //Monta a string de consulta com os ids das transações a serem alteradas
            for (int i = 0; i < codigo.length; i++) {
                str += "idVenda=" + codigo[i];
                if ((i + 1) < codigo.length) {
                    str += " or ";
                }
            }

            //Carrega @list com as transações consultadas no bd
            List<Venda> list = consultar("Venda", str);

            //Conclui uma por uma e salva no bd
            for (int i = 0; i < codigo.length; i++) {
                concluir(list.get(i));
            }

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível concluir estas vendas "
                    + ".\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
        }
    }

    /**
     * Desconclui uma venda
     *
     * @param venda
     */
    public void desconcluir(Venda venda) {
        try {
            
            //Verifica se a transação esta foi concluída*
            if (venda.isConcluida()) {
                //Verifica se a venda não está quitada
                if (!venda.isQuitada()) {
                    //Desconclui a transação
                    venda.setConcluida(false);
                } else {
                    JOptionPane.showMessageDialog(null, "A venda de código " + venda.getIdVenda() + " não pode ser desconcluída pois já está quitada.",
                            "Atenção!", JOptionPane.WARNING_MESSAGE);
                }
            }

            //Salva no BD*
            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.update(venda);
            transacao.commit();

            //Registra o Dedo duro
            DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Desconcluiu a Venda " + venda.getIdVenda()));

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível desconcluir a Venda.\n" + er,
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
                str += "idVenda=" + codigo[i];
                if ((i + 1) < codigo.length) {
                    str += " or ";
                }
            }

            //Carrega @list com as transações consultadas no bd
            List<Venda> list = consultar("Venda", str);

            //Conclui uma por uma e salva no bd
            for (int i = 0; i < codigo.length; i++) {
                desconcluir(list.get(i));
            }

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível concluir estas vendas "
                    + ".\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
        }
    }
}
