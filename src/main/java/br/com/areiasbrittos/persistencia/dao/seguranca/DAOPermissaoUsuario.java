/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.persistencia.dao.seguranca;

import br.com.areiasbrittos.gui.FramePrincipal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import br.com.areiasbrittos.controle.objetos.seguranca.Permissao;
import br.com.areiasbrittos.controle.objetos.seguranca.PermissaoUsuario;
import br.com.areiasbrittos.controle.objetos.seguranca.Transacao;
import br.com.areiasbrittos.controle.objetos.seguranca.Usuario;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.areiasbrittos.persistencia.dao.hibernateSessionFactory_bd_Seguranca;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class DAOPermissaoUsuario {

    DAOTransacao daoTransacao = new DAOTransacao();
    private Session sessao;
    Transaction transacao = null;

    public DAOPermissaoUsuario() {
        this.inicializaSessao();
    }

    public void inicializaSessao() {
        this.sessao = hibernateSessionFactory_bd_Seguranca.getSession();
    }

    /**
     * Configura a permissão do usuário ao sistema inserindo-a no bd
     * @param acesso
     * @return 
     */
    public boolean inserir(PermissaoUsuario acesso) {
        try {
            this.inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.save(acesso);
            transacao.commit();

            /*Registra o Dedo duro*/
            daoTransacao.inserir(new Transacao(FramePrincipal.user, "Cadastrou a permissão " + acesso.getId().getIdPermissao() + " para o usuário " + acesso.getId().getIdUsuario()));
            sessao.close();

            return true;

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível cadastrar o acesso " + acesso.getId() + "!", "ERRO!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            transacao.rollback();
            sessao.close();

            return false;
        }
    }

    /**
     * Insere todos os acessos de um usuario no bd
     * @param acessos
     * @return 
     */
    public boolean inserir(ArrayList<PermissaoUsuario> acessos) {
        int index = 0;

        while (index < acessos.size()) {
            if (this.inserir(acessos.get(index))) {
                index++;
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar os acessos para este usuário!", "ERRO!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        JOptionPane.showMessageDialog(null, "Todos os acessos cadastrados!", "Cadastro de acessos!", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    /**
     * Cria ou modifica os acessos de um usuário para administrador total, ou seja, todos os acessos permitidos
     * @param user - Usuário a ter seus acessos criados ou modificados como administrador
     * @return 
     */
    public boolean configuraAcessosAdministrador(Usuario user) {

        /*Recupera todos os acessos do usuário*/
        List<PermissaoUsuario> listAcesso = this.consultar(user);

        /*Verifica se o usuário não tem acessos cadastrados no bd*/
        if (listAcesso.isEmpty()) {

            /*Recupera todos os tipos de permissões possíveis*/
            DAOPermissao daoPermissao = new DAOPermissao();
            List<Permissao> listPermissao = daoPermissao.listar();

            /*Verifica se as permissões do sistema já foram todas cadastradas no bd*/
            if (listPermissao.size() == FramePrincipal.NUMERO_DE_PERMISSOES) {
                int contador = 0;
                for (int index = 0; index < FramePrincipal.NUMERO_DE_PERMISSOES; index++) {
                    /*Insere cada permissão para o usuário, permitindo o acesso*/
                    if (this.inserir(new PermissaoUsuario(user, listPermissao.get(index), true))) {
                        contador++;
                    }
                }

                /*Todos os acessos foram criados para o usuário*/
                if (contador == FramePrincipal.NUMERO_DE_PERMISSOES) {
                    return true;
                } else {
                    /*Os acessos não foram todos criados para o usuário*/
                    JOptionPane.showMessageDialog(null, "Não foi possível cadastrar os acessos de administrador para o usuário " + user.getNome() + " !", "ERRO!", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }

        } /*Caso o usuário já tenha acessos cadastrados no bd*/ else {

            int contador = 0;

            /*Altera todos estes acessos para true*/
            for (int index = 0; index < listAcesso.size(); index++) {
                PermissaoUsuario acessoTemporario = listAcesso.get(index);
                acessoTemporario.setPermissao(true);
                if (this.alterar(acessoTemporario)) {
                    contador++;
                }
            }

            /*Todos os acessos foram modificados para o usuário*/
            if (contador == FramePrincipal.NUMERO_DE_PERMISSOES) {
                return true;
            } else {
                /*Os acessos não foram todos modificados*/
                JOptionPane.showMessageDialog(null, "Não foi possível alterar todos os acessos de para o usuário " + user.getNome() + " !", "ERRO!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return false;
    }

    /**
     * Altera um determinado acesso do usuário ao sistema
     * @param acesso
     * @return 
     */
    public boolean alterar(PermissaoUsuario acesso) {
        try {
            inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.update(acesso);
            transacao.commit();

            /*Registra o Dedo duro*/
            daoTransacao.inserir(new Transacao(FramePrincipal.user, "Alterou a permissão " + acesso.getId().getIdPermissao() + " para o usuário " + acesso.getId().getIdUsuario()));
            sessao.close();

            return true;

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar o acesso!\n", "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            transacao.rollback();
            sessao.close();

            return false;
        }
    }

    /**
     * Altera todos os acessos presentes em @acessos
     * @param acessos
     * @return 
     */
    public boolean alterar(ArrayList<PermissaoUsuario> acessos) {
        int index = 0;

        while (index < acessos.size()) {
            if (this.alterar(acessos.get(index))) {
                index++;
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao alterar os acessos para este usuário!", "ERRO!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        JOptionPane.showMessageDialog(null, "Todos os acessos alterados!", "Cadastro de acessos!", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    /**
     * Lista todas os acessos no bd para um determinado usuário
     * @return - uma lista com todas as permissões
     */
    public List<PermissaoUsuario> consultar(Usuario user) {

        this.inicializaSessao();
        transacao = sessao.beginTransaction();
        List<PermissaoUsuario> list = sessao.createQuery("from PermissaoUsuario where idUsuario=" + user.getIdUsuario()).list();
        sessao.close();

        return list;

    }
}
