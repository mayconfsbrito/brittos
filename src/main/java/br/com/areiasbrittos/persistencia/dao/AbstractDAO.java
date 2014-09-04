/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.persistencia.dao;

import br.com.areiasbrittos.controle.objetos.*;
import br.com.areiasbrittos.controle.objetos.seguranca.Transacao;
import br.com.areiasbrittos.controle.utils.Dates;
import br.com.areiasbrittos.controle.utils.Horas;
import br.com.areiasbrittos.gui.FramePrincipal;
import br.com.areiasbrittos.gui.utils.ConsertaBugsGUI;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOTransacao;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class AbstractDAO {

    protected static Session sessao;
    protected static Transaction transacao = null;

    public static synchronized Session getSessao(){
        if(sessao != null && sessao.isOpen()) {
            fechaSessao();
            sessao = hibernateSessionFactory_brittos_bd.getSession();
            
        } else {
            sessao = hibernateSessionFactory_brittos_bd.getSession();
        }
        return sessao;
    }
    
    /**
     * Inicializa a sessao para efetuar as transacoes no banco de dados
     */
    public static final void inicializaSessao() {
        sessao = getSessao();

    }

    /**
     * Fecha a sessao caso ela esteja aberta
     */
    public static boolean fechaSessao() {
        if (sessao.isOpen()) {
            sessao.flush();
            sessao.close();

            return true;
        }

        return false;
    }

    /**
     * Cadastra um unico objeto no banco de dados
     *
     * @param obj - Objetto a ser cadastrado
     * @return - @true se o objeto foi cadastrado, @false se o objeto nao foi
     * cadastrado
     */
    public static boolean inserir(Object obj) {
        try {

            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.merge(obj);

            transacao.commit();

            return true;

        } catch (HibernateException er) {

            er.printStackTrace();
            transacao.rollback();

            return false;

        } finally {
            fechaSessao();
        }
    }

    /**
     * Altera um unico objeto no banco de dados
     *
     * @param obj - Objetto a ser alterado
     * @return - @true se o objeto foi alterado, @false se o objeto nao foi
     * alterado
     */
    public static boolean alterar(Object obj) {
        try {

            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.merge(obj);
            transacao.commit();

            return true;

        } catch (HibernateException er) {

            er.printStackTrace();
            transacao.rollback();

            return false;

        } finally {
            fechaSessao();
        }
    }

    public static boolean quitarVarios(String nomeClasse, String nomeAtributoId, int[] ids) {

        try {
            String str = " ";

            //Monta a string de consulta com os ids das transações a serem concluidas
            for (int i = 0; i < ids.length; i++) {
                str += nomeAtributoId + "=" + ids[i];
                if ((i + 1) < ids.length) {
                    str += " or ";
                }
            }

            //Carrega @list com as transações consultadas no bd
            List list = consultar(nomeClasse, str);

            //Recupera no bd o caixa do dia presente
            List<Caixadiario> listCaixa = AbstractDAO.consultar("Caixadiario", "data='" + Dates.getDataHoje() + "'");

            //Verifica se o caixa de hoje foi encontrado
            if (!listCaixa.isEmpty()) {

                Caixadiario cx = listCaixa.get(0);
                
                //Verifica se o caixa está aberto
                if (!cx.isFechado()) {

                    //Conclui uma por uma e salva no bd
                    for (int i = 0; i < ids.length; i++) {
                        //Verifica se a transação já foi quitada
                        if (list.get(i) instanceof Venda) {

                            Venda venda = (Venda) list.get(i);
                            if (!venda.isQuitada()) {
                                venda.setQuitada(true);
                                venda.setConcluida(true);

                                //Gera uma conta a receber e grava no bd
                                Contasreceber conta = new Contasreceber(null, venda, "Venda cod." + venda.getIdVenda() + " - " + venda.getEntidade().getNome(), null,
                                        venda.getVencimento(), Dates.getDataHoje(), Horas.getHoraAgora(), venda.getValorTotal(), true);
                                AbstractDAO.inserir(conta);
                                int idConta = AbstractDAO.max(Contasreceber.class, "idConta");
                                conta.setIdConta(idConta);

                                alterar(venda);
                                DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Quitou a Venda " + venda.getIdVenda()));

                                //Grava um CaixadiarioHasContasPagar no bd
                                CaixadiarioHasContasreceber chp = new CaixadiarioHasContasreceber(cx, conta);
                                AbstractDAO.inserir(chp);
                            }

                        } else if (list.get(i) instanceof Compra) {

                            Compra compra = (Compra) list.get(i);
                            if (!compra.isQuitada()) {
                                compra.setQuitada(true);
                                compra.setConcluida(true);

                                //Gera uma conta a pagar e grava no bd
                                Contaspagar conta = new Contaspagar(compra, "Compra cod." + compra.getIdCompra() + " - " + compra.getEntidade().getNome(), null,
                                        compra.getVencimento(), Dates.getDataHoje(), Horas.getHoraAgora(), compra.getValorTotal(), true);
                                AbstractDAO.inserir(conta);
                                int idConta = AbstractDAO.max(Contaspagar.class, "idConta");
                                conta.setIdConta(idConta);

                                alterar(compra);
                                DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Quitou a Compra " + compra.getIdCompra()));

                                //Grava um CHP no bd
                                CaixadiarioHasContaspagar chp = new CaixadiarioHasContaspagar(cx, conta);
                                AbstractDAO.inserir(chp);
                            }

                        } else if (list.get(i) instanceof Pesagem) {

                            Pesagem pesagem = (Pesagem) list.get(i);
                            if (!pesagem.isQuitada()) {
                                pesagem.setQuitada(true);
                                pesagem.setConcluida(true);

                                //Gera uma conta a receber e grava no bd
                                Contasreceber conta = new Contasreceber(pesagem, null, "Pesagem cod." + pesagem.getIdPesagem() + " - "
                                        + pesagem.getEntidade().getNome(), null,
                                        pesagem.getVencimento(), Dates.getDataHoje(), Horas.getHoraAgora(), pesagem.getValorPesagem(), true);
                                AbstractDAO.inserir(conta);
                                int idConta = AbstractDAO.max(Contasreceber.class, "idConta");
                                conta.setIdConta(idConta);

                                alterar(pesagem);
                                DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Quitou a pesagem " + pesagem.getIdPesagem()));

                                //Grava um CaixadiarioHasContasPagar no bd
                                CaixadiarioHasContasreceber chp = new CaixadiarioHasContasreceber(cx, conta);
                                AbstractDAO.inserir(chp);
                            }

                        } else if (list.get(i) instanceof Contaspagar) {

                            Contaspagar conta = (Contaspagar) list.get(i);
                            if (!conta.isQuitada()) {
                                conta.setQuitada(true);
                                alterar(conta);

                                DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Quitou a conta a pagar " + conta.getIdConta()));
                            }

                        } else if (list.get(i) instanceof Contasreceber) {

                            Contasreceber conta = (Contasreceber) list.get(i);
                            if (!conta.isQuitada()) {
                                conta.setQuitada(true);
                                alterar(conta);

                                DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Quitou a conta a receber " + conta.getIdConta()));
                            }

                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "O caixa de hoje encontra-se fechado, por isso não é possível quitar novas contas hoje.",
                            "Atenção", JOptionPane.WARNING_MESSAGE);
                }
                
                //Caso não tenha sido encontrado o caixa de hoje
            } else {
                
                //Busca o último caixa aberto no bd
                listCaixa = AbstractDAO.consultar("Caixadiario", "fechado=false order by data desc");
                String str2 = "Não é possível quitar esta conta pois o caixa de hoje ainda não foi criado pelo sistema.\n";
                
                if(!listCaixa.isEmpty()){
                    str2 +=  "Feche o caixa do dia " + ConsertaBugsGUI.getDataFormatadaNoBd(listCaixa.get(0).getData(), "dd/MM/yyyy")  + " para que o sistema possa gerar o caixa de hoje.";
                }
                //Notifica o usuário
                JOptionPane.showMessageDialog(null, str2, "Atenção!", JOptionPane.WARNING_MESSAGE);
            }

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível quitar estas contas.\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
        }

        return true;
    }

    /**
     * Exclui um unico objeto no banco de dados
     *
     * @param obj - Objeto a ser excluido
     * @return - @true se o objeto foi excluido, @false se o objeto nao foi
     * excluido
     */
    public static boolean excluir(Object obj) {
        try {

            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.delete(obj);
            transacao.commit();

            return true;

        } catch (HibernateException er) {

            er.printStackTrace();
            transacao.rollback();

            return false;

        } finally {
            fechaSessao();
               
        }
    }

    /**
     * Esclui um determinado objeto a partir unicamente do seu id
     *
     * @param nomeObjeto
     * @param nomeId
     * @param id
     * @return
     */
    public static boolean excluirPorId(String nomeObjeto, String nomeId, Integer id) {

        try {

            List obj = consultar(nomeObjeto, "" + nomeId + "=" + id);
            Object objeto = obj.get(0);
            if (!excluir(objeto)) {
                System.out.println("Não foi possível excluir o objeto " + nomeObjeto + " de Id " + id);
            }

            return true;

        } catch (IndexOutOfBoundsException er) {
            er.printStackTrace();
            return true;

        } catch (Exception er) {

            er.printStackTrace();
            return false;

        }

    }

    /**
     * Consulta todos as instancias de um determinado objeto
     *
     * @param nomeObjeto - Nome dos objetos a serem consultados no banco de
     * dados
     * @return - Lista preenchida com as instancias do objeto no banco de dados,
     * @null caso exista alguma excecao
     */
    public static List listar(String nomeObjeto) {
        try {

            inicializaSessao();
            transacao = sessao.beginTransaction();
            List list = sessao.createQuery("from " + nomeObjeto).list();
            return list;

        } catch (HibernateException er) {
            er.printStackTrace();
            transacao.rollback();
            return null;

        } finally {
            fechaSessao();
        }
    }

    /**
     * Lista os elementos por ordem do atributo @obj
     *
     * @param coluna - string do atributo que ordenará a lista
     * @return - lista ordenada de objetos Pesagem
     */
    public static List listar(String nomeObjeto, String coluna) {
        try {

            inicializaSessao();
            List list = sessao.createQuery("from " + nomeObjeto + " order by " + coluna + " desc").list();

            return list;

        } catch (HibernateException er) {

            er.printStackTrace();
            return null;

        } finally {
            fechaSessao();

        }

    }

    /**
     * Lista os elementos por ordem do atributo @obj1 e @obj2
     *
     * @param coluna - string do atributo que ordenará a lista
     * @return - lista ordenada de objetos Pesagem
     */
    public static List listar(String nomeObjeto, String coluna1, String coluna2) {
        try {

            inicializaSessao();
            List list = sessao.createQuery("from " + nomeObjeto + " order by " + coluna1 + " desc, " + coluna2 + " desc").list();

            return list;

        } catch (HibernateException er) {

            er.printStackTrace();
            return null;

        } finally {
            fechaSessao();

        }
    }

    /**
     * Realiza uma listagem sem fechar a sessao
     *
     * @param nomeObjeto
     * @return
     */
    public static List listarSemFecharSessao(String nomeObjeto) {

        try {

            inicializaSessao();
            transacao = sessao.beginTransaction();
            List list = sessao.createQuery("from " + nomeObjeto).list();

            return list;

        } catch (Exception er) {

            er.printStackTrace();
            return null;

        } finally {
            fechaSessao();
        }
    }

    /**
     * Consulta um tipo de objetos do banco de dados a partir de uma determinada
     * condicao
     *
     * @param nomeObjeto - Nome dos objetos a serem consultados no banco de
     * dados
     * @param condicao - Condicao a ser satisfeita para realizar a consulta
     * @return - Lista preenchida com os objetos que satisfazem a condicao
     */
    public static List consultar(String nomeObjeto, String condicao) {

        try {
            inicializaSessao();
            transacao = sessao.beginTransaction();
            List list = sessao.createQuery("from " + nomeObjeto + " where " + condicao).list();

            return list;

        } catch (Exception er) {
            er.printStackTrace();
            transacao.rollback();
            return null;

        } finally {

            sessao.flush();
            sessao.close();

        }
    }
    /**
     * Realiza uma consulta puramente através do HQL
     *
     * @return
     */
    public static List consultar(String hql) {

        try {
            inicializaSessao();
            transacao = sessao.beginTransaction();
            List list = sessao.createQuery(hql).list();

            return list;

        } catch (Exception er) {

            er.printStackTrace();
            return null;

        } finally {
            fechaSessao();
        }
    }

    /**
     * Realiza a contagem de objetos na tabela
     *
     * @param nomeObjeto
     * @return
     */
    public static int count(Class classe) {
        try {
            inicializaSessao();
            Criteria criteria = sessao.createCriteria(classe);
            criteria.setProjection(Projections.rowCount());
            
            Object resultado = criteria.list().get(0);
            
            if(resultado instanceof Long){
                Long longResult = new Long(resultado.toString());
                return longResult.intValue();
            }
            
            return ((Integer) criteria.list().get(0)).intValue();

        } catch (Exception er) {
            er.printStackTrace();
            transacao.rollback();
            return -1;

        } finally {

            sessao.flush();
            sessao.close();

        }
    }

    /**
     * Verifica qual o maior valor do atributo da classe no bd
     *
     * @param nomeObjeto
     * @return
     */
    public static int max(Class classe, String atributo) {
        try {
            inicializaSessao();
            Criteria criteria = sessao.createCriteria(classe);
            criteria.setProjection(Projections.max(atributo));

            return ((Integer) criteria.list().get(0)).intValue();

        } catch (Exception er) {
            er.printStackTrace();
            transacao.rollback();
            return -1;

        } finally {

            sessao.flush();
            sessao.close();

        }
    }

    /**
     * Verifica o valor da soma de um determinado atributo de uma classe no bd
     *
     */
    public static BigDecimal sum(Class classe, String chavePrimaria, int valorChavePrimaria, String atributo) {
        try {
            inicializaSessao();
            Criteria criteria = sessao.createCriteria(classe);
            criteria.add(Restrictions.eq(chavePrimaria, valorChavePrimaria));
            criteria.setProjection(Projections.sum(atributo));

            return ((BigDecimal) criteria.list().get(0));

        } catch (Exception er) {
            er.printStackTrace();
            transacao.rollback();
            return new BigDecimal("0");

        } finally {

            sessao.flush();
            sessao.close();

        }
    }

    /**
     * Inicializa um jComboBox com todas as entidades presentes no BD
     *
     * @param primeiraSt
     * @param tabela
     * @return
     */
    public static DefaultComboBoxModel inicializaComboBox(String primeiraPosicao, String tabela) {
        try {

            List list = new ArrayList();

            inicializaSessao();
            list = sessao.createQuery("select nome from " + tabela).list();

            DefaultComboBoxModel model = new DefaultComboBoxModel();

            model.addElement(primeiraPosicao);
            for (int i = 0; i < list.size(); i++) {

                if (list.get(i) instanceof Produto) {
                    Produto obj = (Produto) list.get(i);
                    model.addElement(obj.getNome());
                } else {
                    model.addElement(list.get(i));
                }
            }

            return model;

        } catch (HibernateException er) {

            JOptionPane.showMessageDialog(null, "Não foi possível carregar o ComboBox Abstrato!", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;

        } finally {

            sessao.flush();
            sessao.close();

        }
    }

    /**
     * Retorna um vetor de Strings para criar o Model de um combobox
     *
     * @param primeiraSt
     * @param tabela
     * @return
     */
    public static String[] inicializaStringsComboBox(String primeiraPosicao, String tabela) {
        try {

            List list = listar("Produto", "nome");
            String[] strings;

            int cont = 0;
            if (primeiraPosicao != null) {
                cont++;
                strings = new String[list.size() + cont];
                strings[0] = primeiraPosicao;
            } else {
                strings = new String[list.size()];
            }

            if (list.get(0) instanceof Produto) {
                for (int i = 0; i < list.size(); i++) {
                    Produto p = (Produto) list.get(i);
                    strings[i + cont] = p.getNome();
                }

                return strings;

            } else {
                return null;
            }


        } catch (HibernateException er) {

            JOptionPane.showMessageDialog(null, "Não foi possível carregar o ComboBox Abstrato!", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;

        }

    }
}
