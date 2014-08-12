/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.persistencia.dao.seguranca;

import br.com.areiasbrittos.gui.FramePrincipal;
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
 * 1  Consultar entidades
 * 2  Cadastrar/Alterar/Excluir entidades
 * 3  Consultar produtos
 * 4  Cadastrar/Alterar/Ativar/Desativar produtos
 * 5  Consultar tabela de preços de pesagens
 * 6  Cadastrar/Alterar/Excluir Itens da tabela de pesagens
 * 7  Consultar pesagens
 * 8  Cadastrar/Alterar/Concluir pesagens.
 * 9  Desconcluir Pesagens.
 * 10  Consultar compras
 * 11  Cadastrar/Alterar/Concluir compras.
 * 12  Desconcluir compras.
 * 13  Consultar vendas.
 * 14  Cadastrar/Alterar/Concluir vendas.
 * 15  Desconcluir vendas.
 * 16  Consultar/Cadastrar/Alterar usuários.
 * 17  Imprimir relatório por entidade.
 * 18  Imprimir relatório por produto.
 * 19  Imprimir relatório dedo-duro.
 * 20  Consultar/Alterar preferências do sistema.
 * 
 */
public class DAOPermissao {

    DAOTransacao daoTransacao = new DAOTransacao();
    private Session sessao;
    Transaction transacao = null;

    public DAOPermissao() {
        this.inicializaSessao();
    }

    public void inicializaSessao() {
        this.sessao = hibernateSessionFactory_bd_Seguranca.getSession();
    }

    /**
     * Insere uma nova permissão no banco de dados
     * @param permissao
     * @return 
     */
    public boolean inserir(Permissao permissao) {
        try {
            this.inicializaSessao();
            transacao = sessao.beginTransaction();
            sessao.save(permissao);
            transacao.commit();

            /*Registra o Dedo duro*/
            daoTransacao.inserir(new Transacao(FramePrincipal.user, "Inseriu a permissao " + permissao.getDescricao() + " no sistema."));

            sessao.close();

            return true;

        } catch (HibernateException er) {
            JOptionPane.showMessageDialog(null, "Não foi possível cadastrar a permissão " + permissao.getDescricao() + "!", "ERRO!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            transacao.rollback();
            sessao.close();

            return false;
        }
    }

    /**
     * Cria todas as permissões possíveis para o primeiro acesso do sistema em um bd ainda vazio
     * @return 
     */
    public boolean primeiroAcesso() {

        if (this.listar().isEmpty()) {
            this.inserir(new Permissao("Consultar entidades"));
            this.inserir(new Permissao("Cadastrar/Alterar/Excluir entidades"));
            this.inserir(new Permissao("Consultar produtos"));
            this.inserir(new Permissao("Cadastrar/Alterar/Ativar/Desativar produtos"));
            this.inserir(new Permissao("Consultar tabela de preços de pesagens"));
            this.inserir(new Permissao("Cadastrar/Alterar/Excluir Itens da tabela de pesagens"));
            this.inserir(new Permissao("Consultar pesagens"));
            this.inserir(new Permissao("Cadastrar/Alterar/Concluir pesagens"));
            this.inserir(new Permissao("Desconcluir Pesagens"));
            this.inserir(new Permissao("Consultar compras"));
            this.inserir(new Permissao("Cadastrar/Alterar/Concluir compras"));
            this.inserir(new Permissao("Desconcluir compras"));
            this.inserir(new Permissao("Consultar vendas"));
            this.inserir(new Permissao("Cadastrar/Alterar/Concluir vendas"));
            this.inserir(new Permissao("Desconcluir vendas"));
            this.inserir(new Permissao("Consultar/Cadastrar/Alterar usuários"));
            this.inserir(new Permissao("Imprimir relatório por entidade"));
            this.inserir(new Permissao("Imprimir relatório por produto"));
            this.inserir(new Permissao("Imprimir relatório dedo-duro"));
            this.inserir(new Permissao("Consultar/Alterar preferências do sistema"));
            this.inserir(new Permissao("Consultar contas a pagar"));
            this.inserir(new Permissao("Alterar contas a pagar"));
            this.inserir(new Permissao("Consultar contas a receber"));
            this.inserir(new Permissao("Alterar contas a receber"));
            this.inserir(new Permissao("Consultar caixa diário"));
            this.inserir(new Permissao("Alterar caixa diário"));
            this.inserir(new Permissao("Imprimir relatório caixa-diário"));
            this.inserir(new Permissao("Consultar categorias"));
            this.inserir(new Permissao("Alterar categorias"));
            this.inserir(new Permissao("Consultar entrada estoque"));
            this.inserir(new Permissao("Alterar entrada estoque"));
            this.inserir(new Permissao("Consultar saida estoque"));
            this.inserir(new Permissao("Alterar saida estoque"));
            this.inserir(new Permissao("Imprimir relatorio almoxaridado"));

            JOptionPane.showMessageDialog(null, "Permissões do sistema criadas com sucesso!", "Configurando o primeiro acesso.", JOptionPane.INFORMATION_MESSAGE);

            return true;

        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível criar todas as permissões do sistema. \nFavor contactar o administrador do sistema!", "ERRO!", JOptionPane.ERROR_MESSAGE);

            return false;
        }


    }

    /**
     * Lista todas as permissões presentes no bd
     * @return - uma lista com todas as permissões
     */
    public List<Permissao> listar() {

        this.inicializaSessao();
        transacao = sessao.beginTransaction();
        List<Permissao> list = sessao.createQuery("from Permissao").list();
        sessao.close();

        return list;

    }

    /**
     * Lista acessos a partir do parâmetro enviado por @parametros
     * @param parametros
     * @return - retorna a lista de acessos consultada no bd
     */
    public List<PermissaoUsuario> consultar(String parametros) {

        inicializaSessao();
        transacao = sessao.beginTransaction();
        List list = sessao.createQuery("from PermissaoUsuario where " + parametros).list();
        sessao.close();

        return list;

    }
}
