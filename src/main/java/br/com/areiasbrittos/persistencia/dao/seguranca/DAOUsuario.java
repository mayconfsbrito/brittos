/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.persistencia.dao.seguranca;

import br.com.areiasbrittos.gui.FramePrincipal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import br.com.areiasbrittos.controle.objetos.seguranca.Transacao;
import br.com.areiasbrittos.controle.objetos.seguranca.Usuario;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.SQLGrammarException;
import br.com.areiasbrittos.persistencia.dao.hibernateSessionFactory_bd_Seguranca;

/**
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class DAOUsuario {

    DAOTransacao daoTransacao = new DAOTransacao();
    private Session sessao;
    Transaction transacao = null;

    public DAOUsuario() {
        this.inicializaSessao();
    }

    public final void inicializaSessao() {
        this.sessao = hibernateSessionFactory_bd_Seguranca.getSession();
    }

    /*
     * Insere um novo usuario no bd @param user - Usuário a ser inserido no bd
     */
    public boolean inserir(Usuario user) {
        try {
            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.save(user);
            transacao.commit();

            /*
             * Registra o Dedo duro
             */
            daoTransacao.inserir(new Transacao(FramePrincipal.user, "Cadastrou o usuario " + user.getNome()));

            JOptionPane.showMessageDialog(null, "Usuário " + user.getNome() + " cadastrado(a) com sucesso!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);

            return true;

        } catch (ConstraintViolationException er) {
            JOptionPane.showMessageDialog(null, user.getNome()
                    + " já existe portanto não é possível cadastrá-lo(a)!", "ERRO!", JOptionPane.ERROR_MESSAGE);
            transacao.rollback();

            return false;

        } catch (HibernateException er) {

            JOptionPane.showMessageDialog(null, "Erro ao cadastrar. Não foi possível cadastrar o usuário!\n" + er,
                    "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            transacao.rollback();

            return false;
            
        } finally {

            sessao.flush();
            sessao.close();
        }
    }

    public boolean alterar(Usuario user) {
        try {
            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.update(user);
            transacao.commit();

            /*
             * Registra o Dedo duro
             */
            daoTransacao.inserir(new Transacao(FramePrincipal.user, "Alterou o usuário " + user.getNome()));

            JOptionPane.showMessageDialog(null, "Usuário " + user.getNome() + " alterado(a) com sucesso!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);

            return true;

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar o usuário!\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            transacao.rollback();

            return false;

        } finally {

            sessao.flush();
            sessao.close();
        }
    }

    /**
     * Lista todos os usuarios a partir
     *
     * @return - retora a lista de todos os usuários do bd
     */
    public List<Usuario> listarTodos(boolean listarAdministrador) {

        try {
            
            String query = " from Usuario ";
            
            if(!listarAdministrador){
                query += " where idUsuario!=1";
            }
            
            inicializaSessao();
            transacao = sessao.beginTransaction();
            List list = sessao.createQuery(query).list();
            sessao.close();

            return list;

        } catch (SQLGrammarException er) {
            JOptionPane.showMessageDialog(null, "Não foi encontrado o banco de dados de segurança do sistema.\n"
                    + "Favor contactar o administrador do sistema.",
                    "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            System.exit(0);

            return null;
        }

    }

    /**
     * Lista usuário a partir do parâmetro enviado por @parametros
     *
     * @param parametros
     * @return - retorna a lista de usuários consultada no bd
     */
    public List<Usuario> consultar(String parametros) {

        inicializaSessao();
        transacao = sessao.beginTransaction();
        List list = sessao.createQuery("from Usuario where " + parametros).list();
        sessao.close();

        return list;

    }

    /**
     * Ativa um usuario do sistema no bd
     *
     * @param user
     * @return
     */
    public boolean ativa(Usuario user) {

        if (!user.isAtivo()) {
            /*
             * Ativa o usuário na memória
             */
            user.setAtivo(true);

            /*
             * Altera o usuário agora ativo na memória
             */
            try {
                inicializaSessao();
                transacao = sessao.beginTransaction();
                sessao.update(user);
                transacao.commit();

                /*
                 * Registra o Dedo duro
                 */
                daoTransacao.inserir(new Transacao(FramePrincipal.user, "Ativou o usuário " + user.getNome()));

                JOptionPane.showMessageDialog(null, "Usuário(a) " + user.getNome() + " ativado no sistema com sucesso!", "Ativação de usuário", JOptionPane.INFORMATION_MESSAGE);

                return true;

            } catch (HibernateException er) {
                JOptionPane.showMessageDialog(null, "Não foi possível ativar o usuário!\nEntre em contato com o administrador do sistema.", "Erro!", JOptionPane.ERROR_MESSAGE);
                transacao.rollback();

                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "O usuário " + user.getNome() + " já se encontra ativo.",
                    "Erro!", JOptionPane.ERROR_MESSAGE);

            return true;
        }
    }

    /**
     * Desativa um usuario do sistema no bd
     *
     * @param user
     * @return
     */
    public boolean desativa(Usuario user) {

        if (user.isAtivo()) {
            /*
             * Desativa o usuário na memória
             */
            user.setAtivo(false);

            /*
             * Altera o usuário no bd agora desativado na memória
             */
            try {
                inicializaSessao();
                transacao = sessao.beginTransaction();
                sessao.update(user);
                transacao.commit();

                /*
                 * Registra o Dedo duro
                 */
                daoTransacao.inserir(new Transacao(FramePrincipal.user, "Desativou o usuário " + user.getNome()));

                JOptionPane.showMessageDialog(null, "Usuário(a) " + user.getNome() + " desativado no sistema com sucesso!", "desativação de usuário", JOptionPane.INFORMATION_MESSAGE);

                return true;

            } catch (HibernateException er) {
                JOptionPane.showMessageDialog(null, "Não foi possível desativar o usuário!\nEntre em contato com o administrador do sistema.", "Erro!", JOptionPane.ERROR_MESSAGE);
                transacao.rollback();

                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "O usuário " + user.getNome() + " já se encontra desativado.",
                    "Erro!", JOptionPane.ERROR_MESSAGE);

            return true;
        }
    }

    /**
     * Inicializa um jComboBox com todas os usuários presentes no BD
     *
     * @param primeiraString
     * @param tabela
     * @return
     */
    public DefaultComboBoxModel inicializaComboBox(String primeiraString, String tabela) {
        try {

            List<String> list = new ArrayList<String>();

            if (!tabela.equals("Todos")) {
                list = sessao.createQuery("select nome from Usuario where tipo like '%" + tabela + "%' order by nome").list();
            } else {
                list = sessao.createQuery("select nome from Usuario order by nome").list();
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel();

            model.addElement(primeiraString);
            for (int i = 0; i < list.size(); i++) {
                model.addElement(list.get(i));
            }

            sessao.close();

            return model;

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar o ComboBox de Usuarios.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
