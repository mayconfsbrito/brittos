/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.persistencia.dao;

import br.com.areiasbrittos.gui.FramePrincipal;
import br.com.areiasbrittos.gui.utils.ConsertaBugsGUI;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import br.com.areiasbrittos.controle.objetos.Pesagem;
import br.com.areiasbrittos.controle.objetos.Tabelapesagem;
import br.com.areiasbrittos.controle.objetos.seguranca.Transacao;
import br.com.areiasbrittos.controle.utils.Dates;
import br.com.areiasbrittos.controle.utils.Horas;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOTransacao;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class DAOPesagem {

    private DAOTransacao DAOtransacao = new DAOTransacao();
    private Session sessao;
    Transaction transacao = null;

    public DAOPesagem() {
        inicializaSessao();
    }

    public final void inicializaSessao() {
        this.sessao = hibernateSessionFactory_brittos_bd.getSession();
    }

    public boolean inserir(Pesagem pesagem) {
        try {
            inicializaSessao();
            this.transacao = sessao.beginTransaction();
            this.sessao.save(pesagem);
            this.transacao.commit();

            /*Registra o Dedo duro*/
            DAOtransacao.inserir(new Transacao(FramePrincipal.user, "Cadastrou a pesagem " + this.getUltimoId().get(0)));

            return true;

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível inserir esta pesagem no banco de dados!\n",
                    "Erro!", JOptionPane.ERROR_MESSAGE);

            this.sessao.flush();
            this.transacao.rollback();
            er.printStackTrace();

            return false;
        }

    }

    public boolean alterar(Pesagem pesagem) {
        try {

            inicializaSessao();
            transacao = sessao.beginTransaction();
            pesagem.setDataAlteracao(Dates.getDataHoje());
            pesagem.setHoraAlteracao(Horas.getHoraAgora());
            sessao.update(pesagem);
            transacao.commit();

            JOptionPane.showMessageDialog(null, "Pesagem alterada!", "Alteração", JOptionPane.INFORMATION_MESSAGE);

            /*Registra o Dedo duro*/
            DAOtransacao.inserir(new Transacao(FramePrincipal.user, "Alterou a pesagem " + pesagem.getIdPesagem()));

            return true;

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar a pesagem no banco de dados.\n",
                    "Erro!", JOptionPane.ERROR_MESSAGE);
            transacao.rollback();
            er.printStackTrace();
            return false;

        } finally {
            sessao.close();
        }
    }

    public List consultar(String str) {
        inicializaSessao();
        transacao = sessao.beginTransaction();
        List list = sessao.createQuery("from Pesagem where " + str).list();
        sessao.close();
        return list;

    }

    /*
     * Recupera a pesagem com o maior id, ou seja, a ultima pesagem inserida no bd
     */
    public List getUltimoId() {
        inicializaSessao();
        transacao = sessao.beginTransaction();
        List list = sessao.createQuery("select max(idPesagem) from Pesagem").list();
        sessao.close();
        return list;
    }

    /**
     * Lista os objetos Pesagem
     * @return - lista de objetos Pesagem
     */
    public List listar() {
        try {
            inicializaSessao();
            List list = sessao.createQuery("from Pesagem").list();
            sessao.close();
            return list;
        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível listar as Pesagens!\n" + er, "Erro",
                    JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            return null;
        }
    }

    /**
     * Lista os elementos por ordem do atributo @obj
     * @param coluna - string do atributo que ordenará a lista
     * @return - lista ordenada de objetos Pesagem
     */
    public List listar(String coluna) {
        try {
            inicializaSessao();
            List list = sessao.createQuery("from Pesagem order by " + coluna + " desc").list();
            sessao.close();
            return list;
        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível listar as Pesagens!\n" + er, "Erro",
                    JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            return null;
        }
    }

    /**
     * Lista os elementos por ordem do atributo @obj1 e @obj2
     * @param coluna - string do atributo que ordenará a lista
     * @return - lista ordenada de objetos Pesagem
     */
    public List listar(String coluna1, String coluna2) {
        try {
            inicializaSessao();
            List list = sessao.createQuery("from Pesagem order by " + coluna1 + " desc, " + coluna2 + " desc").list();
            sessao.close();
            return list;
        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível listar as Pesagens!\n" + er, "Erro",
                    JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            return null;
        }
    }

    /**
     * Seta uma transação como concluída
     * @param pesagem 
     */
    public void concluir(Pesagem pesagem) {
        try {
            /**Verifica se a transação já foi concluída**/
            if (!pesagem.isConcluida()) {
                /**Conclui a transação**/
                pesagem.setConcluida(true);
            }

            /**Salva no BD**/
            inicializaSessao();
            transacao = sessao.beginTransaction();
            pesagem.setDataAlteracao(Dates.getDataHoje());
            pesagem.setHoraAlteracao(Horas.getHoraAgora());
            sessao.update(pesagem);
            transacao.commit();

            /*Registra o Dedo duro*/
            DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Concluiu a pesagem " + pesagem.getIdPesagem()));

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível concluir a Pessagem.\n" + er,
                    "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            transacao.rollback();
        } finally{
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

            /*Monta a string de consulta com os ids das transações a serem alteradas*/
            for (int i = 0; i < codigo.length; i++) {
                str += "idPesagem=" + codigo[i];
                if ((i + 1) < codigo.length) {
                    str += " or ";
                }
            }

            /*Carrega @list com as pesagens consultadas no bd*/
            List<Pesagem> list = consultar(str);

            /*Conclui uma por uma e salva no bd*/
            for (int i = 0; i < codigo.length; i++) {
                concluir(list.get(i));
            }

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível concluir estas pesagens "
                    + ".\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
        }
    }

    /**
     * Desconclui uma pesagem
     * @param pesagem 
     */
    public void desconcluir(Pesagem pesagem) {
        try {
            //Verifica se a transação esta foi concluída
            if (pesagem.isConcluida()) {
                //Verifica se a compra não está quitada
                if (!pesagem.isQuitada()) {
                    //Desconclui a transação
                    pesagem.setConcluida(false);
                } else {
                    JOptionPane.showMessageDialog(null, "A pesagem de código " + pesagem.getIdPesagem() + " não pode ser desconcluída pois já está quitada.",
                            "Atenção!", JOptionPane.WARNING_MESSAGE);
                }
            }

            /**Salva no BD**/
            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.update(pesagem);
            transacao.commit();

            /*Registra o Dedo duro*/
            DAOtransacao.inserir(new Transacao(FramePrincipal.user, "Desconcluiu a pesagem " + pesagem.getIdPesagem()));

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível desconcluir a Pessagem.\n" + er,
                    "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            transacao.rollback();
        } finally{
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

            /*Monta a string de consulta com os ids das transações a serem alteradas*/
            for (int i = 0; i < codigo.length; i++) {
                str += "idPesagem=" + codigo[i];
                if ((i + 1) < codigo.length) {
                    str += " or ";
                }
            }

            /*Carrega @list com as pesagens consultadas no bd*/
            List<Pesagem> list = consultar(str);

            /*Conclui uma por uma e salva no bd*/
            for (int i = 0; i < codigo.length; i++) {
                desconcluir(list.get(i));
            }

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível concluir estas pesagens "
                    + ".\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
        }
    }

    //Recebe o valor da tara através do JTextField e retorna o item
    //da tabela de pesagem correspondente aquele peso encontrado no JTextField
    public Tabelapesagem getItemTabelaPesagem(JTextField textTara) {

        ConsertaBugsGUI con = new ConsertaBugsGUI();
        int tara = con.recebeInteiroEmJTextFieldComMascara(textTara);

        try {

            DAOTabelaPesagem tpDao = new DAOTabelaPesagem();
            List<Tabelapesagem> list = tpDao.consultar("tara<=" + tara + " order by tara desc");

            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Não encontrado nenhum item na tabela de preços de pesagens correspondente"
                        + " ao peso indicado.", "Atenção", JOptionPane.ERROR_MESSAGE);
                return null;
            } else {
                return list.get(0);
            }


        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar o item da tabela de preços de pesagem correspondente"
                    + " a este peso.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            return null;
        }
    }
}
