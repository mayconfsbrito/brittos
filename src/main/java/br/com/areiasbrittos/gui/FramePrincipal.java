
/*
 * FramePrincipal.java
 *
 * Created on 17/01/2011, 17:45:01
 */
package br.com.areiasbrittos.gui;

import br.com.areiasbrittos.controle.gui.ControleFPreferencias;
import br.com.areiasbrittos.gui.internalFrames.InternalFrameProdutos;
import br.com.areiasbrittos.gui.internalFrames.InternalFrameUsuarios;
import br.com.areiasbrittos.gui.internalFrames.InternalFrameEntidade;
import br.com.areiasbrittos.gui.internalFrames.InternalFrameSobre;
import br.com.areiasbrittos.gui.internalFrames.InternalFramePreferencias;
import br.com.areiasbrittos.gui.internalFrames.InternalFrameAlteraSenha;
import br.com.areiasbrittos.gui.internalFrames.InternalFrameLogin;
import br.com.areiasbrittos.gui.internalFrames.InternalFrameProdutosCompra;
import br.com.areiasbrittos.gui.internalFrames.InternalFramePesagem;
import br.com.areiasbrittos.gui.internalFrames.InternalFrameProdutosVenda;
import br.com.areiasbrittos.gui.internalFrames.InternalFrameTabelaPrecosPesagem;
import br.com.areiasbrittos.controle.gui.ControleFPrincipal;
import br.com.areiasbrittos.controle.objetos.Caixadiario;
import br.com.areiasbrittos.controle.objetos.Entidade;
import br.com.areiasbrittos.controle.objetos.Produto;
import br.com.areiasbrittos.controle.objetos.Tabelapesagem;
import br.com.areiasbrittos.gui.relatorios.InternalFrameRastreio;
import java.text.ParseException;
import br.com.areiasbrittos.gui.relatorios.InternalFramePorEntidade;
import br.com.areiasbrittos.gui.utils.EnabledGUIElements;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import br.com.areiasbrittos.controle.objetos.seguranca.Permissao;
import br.com.areiasbrittos.controle.objetos.seguranca.PermissaoUsuario;
import br.com.areiasbrittos.controle.objetos.seguranca.Usuario;
import br.com.areiasbrittos.gui.internalFrames.*;
import br.com.areiasbrittos.gui.relatorios.InternalFrameEstoque;
import br.com.areiasbrittos.gui.utils.MyDesktopPanel;
import br.com.areiasbrittos.gui.utils.WindowListenerFramePrincipal;
import java.io.File;
import org.hibernate.exception.SQLGrammarException;
import br.com.areiasbrittos.persistencia.dao.AbstractDAO;
import br.com.areiasbrittos.persistencia.dao.hibernateSessionFactory_bd_Seguranca;
import br.com.areiasbrittos.persistencia.dao.hibernateSessionFactory_brittos_bd;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOPermissao;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOPermissaoUsuario;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOUsuario;
import br.com.areiasbrittos.gui.*;

/**
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class FramePrincipal extends javax.swing.JFrame {

    /*
     * Variáveis manuais estáticas do sistema
     */
    public static Usuario user;
    public static List<PermissaoUsuario> acessos;
    /*
     * Variáveis locais
     */
    protected String[] botoesOpcoes = {"Sim", "Não"};
    /*
     * Constantes do sistema
     */
    public static final int NUMERO_DE_PERMISSOES = 34;
    public static final int PERMISSAO_CONSULTA_ENTIDADE = 0;
    public static final int PERMISSAO_ALTERA_ENTIDADE = 1;
    public static final int PERMISSAO_CONSULTA_PRODUTO = 2;
    public static final int PERMISSAO_ALTERA_PRODUTO = 3;
    public static final int PERMISSAO_CONSULTA_TABELA = 4;
    public static final int PERMISSAO_ALTERA_TABELA = 5;
    public static final int PERMISSAO_CONSULTA_PESAGEM = 6;
    public static final int PERMISSAO_ALTERA_PESAGEM = 7;
    public static final int PERMISSAO_DESCONCLUI_PESAGEM = 8;
    public static final int PERMISSAO_CONSULTA_COMPRA = 9;
    public static final int PERMISSAO_ALTERA_COMPRA = 10;
    public static final int PERMISSAO_DESCONCLUI_COMPRA = 11;
    public static final int PERMISSAO_CONSULTA_VENDA = 12;
    public static final int PERMISSAO_ALTERA_VENDA = 13;
    public static final int PERMISSAO_DESCONCLUI_VENDA = 14;
    public static final int PERMISSAO_ALTERA_USUARIO = 15;
    public static final int PERMISSAO_RELATORIO_ENTIDADE = 16;
    public static final int PERMISSAO_RELATORIO_PRODUTO = 17;
    public static final int PERMISSAO_RELATORIO_DEDODURO = 18;
    public static final int PERMISSAO_PREFERENCIAS = 19;
    public static final int PERMISSAO_CONSULTA_CONTAS_PAGAR = 20;
    public static final int PERMISSAO_ALTERA_CONTAS_PAGAR = 21;
    public static final int PERMISSAO_CONSULTA_CONTAS_RECEBER = 22;
    public static final int PERMISSAO_ALTERA_CONTAS_RECEBER = 23;
    public static final int PERMISSAO_CONSULTA_CAIXA_DIARIO = 24;
    public static final int PERMISSAO_ALTERA_CAIXA_DIARIO = 25;
    public static final int PERMISSAO_RELATORIO_CAIXA_DIARIO = 26;
    public static final int PERMISSAO_CONSULTA_CATEGORIAS = 27;
    public static final int PERMISSAO_ALTERA_CATEGORIAS = 28;
    public static final int PERMISSAO_CONSULTA_ENTRADA_ESTOQUE = 29;
    public static final int PERMISSAO_ALTERA_ENTRADA_ESTOQUE = 30;
    public static final int PERMISSAO_CONSULTA_SAIDA_ESTOQUE = 31;
    public static final int PERMISSAO_ALTERA_SAIDA_ESTOQUE = 32;
    public static final int PERMISSAO_RELATORIO_ALMOXARIFADO = 33;

    /**
     * Creates new form FramePrincipal
     */
    public FramePrincipal() {
        inicializaTema();
        verificaBDs();

        //Verifica o arquivo de configuração/preferencias
        ControleFPreferencias.verificaConfiguracoes();
        Caixadiario.verificaCaixasBd();
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);

        //Instancia os eventos do JFrame
        this.addWindowListener(new WindowListenerFramePrincipal());
        
        //Inicializa as configurações e eventos da aplicação
        inicializa();
    }

    private void inicializaTema() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception er) {
            JOptionPane.showMessageDialog(this, "Não foi possível inicializar o tema.\n" + er, "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
       
    }
    
    /**
     * Verifica se os banco de dados existem e em caso negativo cria-os
     */
    private void verificaBDs() {

        try {

            hibernateSessionFactory_brittos_bd.getSession().beginTransaction();
            hibernateSessionFactory_brittos_bd.getSession().flush();
            hibernateSessionFactory_brittos_bd.getSession().close();

        } catch (SQLGrammarException ex) {
            JOptionPane.showMessageDialog(this, "O Banco de Dados \"bd_brittos\" ainda não foi instalado.\n"
                    + "Favor instalar o banco de dados antes de iniciar a aplicação!", "Erro!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            
            System.exit(1);
        }

        try {

            hibernateSessionFactory_bd_Seguranca.getSession().beginTransaction();
            hibernateSessionFactory_bd_Seguranca.getSession().flush();
            hibernateSessionFactory_bd_Seguranca.getSession().close();

        } catch (SQLGrammarException ex) {
            JOptionPane.showMessageDialog(this, "O Banco de Dados \"bd_seguranca\" ainda não foi instalado.\n"
                    + "Favor instalar o banco de dados antes de iniciar a aplicação!", "Erro!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            
            System.exit(1);
        }
        
    }

    private void inicializa() {
        try {
            textMensagem.setEditable(false);
            textMensagem.setText("Seja bem vindo!");
            this.primeiroAcesso();
            this.desconectaUsuario();
            carregarLogotipo();


            /*
             * Verifica registros de transações dos usuários que estão vencidas
             */
            new ControleFPrincipal().eliminaTransacoesVencidas();

            buttonLoginActionPerformed(null);

        } catch (ParseException er) {
            JOptionPane.showMessageDialog(this, "Erro ao configurar o primeiro acesso do sistema!\n" + er, "Configurando o primeiro acesso.", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Realiza os procedimentos de conexão de um usuário ao sistema
     *
     * @param user - Usuario a ser conectado
     * @return - Confirma que o usuário está conectado
     */
    public static boolean conectaUsuario(Usuario user) {
        /*
         * Instancia o usuário
         */
        FramePrincipal.user = user;

        /*
         * Configura a barra lateral de usuários
         */
        labelConectado.setVisible(true);
        labelDesconectado.setVisible(false);
        labelUsuario.setText(user.getNome());

        return true;
    }

    /**
     * Realiza os procedimentos de conexão de um usuário ao sistema
     *
     * @param user - Usuario a ser conectado
     * @return - Confirma que o usuário está conectado
     */
    public static boolean desconectaUsuario() {
        /*
         * Instancia o usuário
         */
        FramePrincipal.user = null;

        /*
         * Configura a barra lateral de usuários
         */
        labelConectado.setVisible(false);
        labelDesconectado.setVisible(true);
        labelUsuario.setText("Usuario Desconectado");

        /*
         * Fecha todos os frames internos
         */
        FramePrincipal.fechaTodosFramesInternos();

        /*
         * Desabilita todos os elementos do FramePrincipal
         */
        EnabledGUIElements.disabledJComponent(menuInformacoes, menuTransacoes, menuOpcoes, menuRelatorio, menuCaixa);
        EnabledGUIElements.disabledJComponent(buttonEntidades, buttonPesagem, buttonRelatorioPorEntidade, buttonTabelaDePesagem, buttonProdutos,
                buttonCompra, buttonVenda, buttonContasPagar, buttonContasReceber, buttonCaixaDiário, buttonPreferencias);

        return true;
    }

    /**
     * Inicializa os componentes deste frame a partir das permissões do usuário
     *
     * @return - confirma o sucesso da aplicação do método
     */
    public static boolean verificaAcessos() {

        //Permite acesso a todos os componentes livre para qualquer usuário
        EnabledGUIElements.enabledJComponent(menuItemUsuariosAlterarSenha, menuInformacoes, menuTransacoes, menuCaixa,
                menuRelatorio, menuOpcoes);

        //Recupera os acessos do usuario no bd
        FramePrincipal.getPermissaoUsuarioUser();

        //Verifica se o menu item Entidades e seu button pode ser acessado
        if (acessos.get(PERMISSAO_CONSULTA_ENTIDADE).isPermissao()) {
            FramePrincipal.menuItemEntidades.setEnabled(true);
            FramePrincipal.buttonEntidades.setEnabled(true);
        } else {
            FramePrincipal.menuItemEntidades.setEnabled(false);
            FramePrincipal.buttonEntidades.setEnabled(false);
        }

        //Verifica se o menu item Produtos pode ser acessado
        if (acessos.get(PERMISSAO_CONSULTA_PRODUTO).isPermissao()) {
            FramePrincipal.menuItemProdutos.setEnabled(true);
            FramePrincipal.buttonProdutos.setEnabled(true);
        } else {
            FramePrincipal.menuItemProdutos.setEnabled(false);
            FramePrincipal.buttonProdutos.setEnabled(false);
        }

        //Verifica se o menu item Tabela de Pesagens pode ser acessado
        if (acessos.get(PERMISSAO_CONSULTA_TABELA).isPermissao()) {
            FramePrincipal.menuItemTabelaPesagem.setEnabled(true);
            FramePrincipal.buttonTabelaDePesagem.setEnabled(true);
        } else {
            FramePrincipal.menuItemTabelaPesagem.setEnabled(false);
            FramePrincipal.buttonTabelaDePesagem.setEnabled(false);
        }

        //Verifica se o menu item Pesagens pode ser acessado
        if (acessos.get(PERMISSAO_CONSULTA_PESAGEM).isPermissao()) {
            FramePrincipal.menuItemPesagem.setEnabled(true);
            FramePrincipal.buttonPesagem.setEnabled(true);
        } else {
            FramePrincipal.menuItemPesagem.setEnabled(false);
            FramePrincipal.buttonPesagem.setEnabled(false);
        }

        //Verifica se o menu item Compra de produtos pode ser acessado
        if (acessos.get(PERMISSAO_CONSULTA_COMPRA).isPermissao()) {
            FramePrincipal.menuItemProdutosCompra.setEnabled(true);
            FramePrincipal.buttonCompra.setEnabled(true);
        } else {
            FramePrincipal.menuItemProdutosCompra.setEnabled(false);
            FramePrincipal.buttonCompra.setEnabled(false);
        }

        //Verifica se o menu item Venda de produtos pode ser acessado
        if (acessos.get(PERMISSAO_CONSULTA_VENDA).isPermissao()) {
            FramePrincipal.menuItemProdutosVenda.setEnabled(true);
            FramePrincipal.buttonVenda.setEnabled(true);
        } else {
            FramePrincipal.menuItemProdutosVenda.setEnabled(false);
            FramePrincipal.buttonVenda.setEnabled(false);
        }

        //Verifica se o menu item Gerenciar Usuários pode ser acessado
        if (acessos.get(PERMISSAO_ALTERA_USUARIO).isPermissao()) {
            FramePrincipal.menuItemUsuariosGerenciar.setEnabled(true);
        } else {
            FramePrincipal.menuItemUsuariosGerenciar.setEnabled(false);
        }

        //Verifica se o menu item Relatório por Entidade pode ser acessado
        if (acessos.get(PERMISSAO_RELATORIO_ENTIDADE).isPermissao()) {
            FramePrincipal.menuItemRelatorioPorEntidade.setEnabled(true);
            FramePrincipal.buttonRelatorioPorEntidade.setEnabled(true);
        } else {
            FramePrincipal.menuItemRelatorioPorEntidade.setEnabled(false);
            FramePrincipal.buttonRelatorioPorEntidade.setEnabled(false);
        }

        //Verifica se o menu item Relatório por Produto pode ser acessado
        if (acessos.get(PERMISSAO_RELATORIO_PRODUTO).isPermissao()) {
            FramePrincipal.menuItemRelatorioEstoque.setEnabled(true);
        } else {
            FramePrincipal.menuItemRelatorioEstoque.setEnabled(false);
        }

        //Verifica se o menu item Relatório de Rastreamento pode ser acessado
        if (acessos.get(PERMISSAO_RELATORIO_DEDODURO).isPermissao()) {
            FramePrincipal.menuItemRelatorioDedoDuro.setEnabled(true);
        } else {
            FramePrincipal.menuItemRelatorioDedoDuro.setEnabled(false);
        }

        //Verifica se o menu item Preferências pode ser acessado
        if (acessos.get(PERMISSAO_PREFERENCIAS).isPermissao()) {
            FramePrincipal.menuItemPreferencias.setEnabled(true);
            FramePrincipal.buttonPreferencias.setEnabled(true);
        } else {
            FramePrincipal.menuItemPreferencias.setEnabled(false);
            FramePrincipal.buttonPreferencias.setEnabled(false);
        }

        //Verifica se o menu item Contas a pagar pode ser acessado
        if (acessos.get(PERMISSAO_CONSULTA_CONTAS_PAGAR).isPermissao()) {
            FramePrincipal.menuItemContasPagar.setEnabled(true);
        } else {
            FramePrincipal.menuItemContasPagar.setEnabled(false);
        }

        //Verifica se o menu item Contas a receber pode ser acessado
        if (acessos.get(PERMISSAO_CONSULTA_CONTAS_RECEBER).isPermissao()) {
            FramePrincipal.menuItemContasReceber.setEnabled(true);
        } else {
            FramePrincipal.menuItemContasReceber.setEnabled(false);
        }

        //Verifica se o menu item Contas a Pagar pode ser acessado
        if (acessos.get(PERMISSAO_CONSULTA_CONTAS_PAGAR).isPermissao()) {
            FramePrincipal.menuItemContasPagar.setEnabled(true);
            FramePrincipal.buttonContasPagar.setEnabled(true);
        } else {
            FramePrincipal.menuItemContasPagar.setEnabled(false);
            FramePrincipal.buttonContasPagar.setEnabled(false);
        }

        //Verifica se o menu item Contas a Receber pode ser acessado
        if (acessos.get(PERMISSAO_CONSULTA_CONTAS_RECEBER).isPermissao()) {
            FramePrincipal.menuItemContasReceber.setEnabled(true);
            FramePrincipal.buttonContasReceber.setEnabled(true);
        } else {
            FramePrincipal.menuItemContasReceber.setEnabled(false);
            FramePrincipal.buttonContasReceber.setEnabled(false);
        }

        //Verifica se o menu item Movimento de Caixa Diário pode ser acessado
        if (acessos.get(PERMISSAO_CONSULTA_CAIXA_DIARIO).isPermissao()) {
            FramePrincipal.menuItemMovimentoCaixaDiario.setEnabled(true);
            FramePrincipal.buttonCaixaDiário.setEnabled(true);
        } else {
            FramePrincipal.menuItemMovimentoCaixaDiario.setEnabled(false);
            FramePrincipal.buttonCaixaDiário.setEnabled(false);
        }

        //Verifica o menu de Caixa
        if (!(acessos.get(PERMISSAO_CONSULTA_CAIXA_DIARIO).isPermissao()
                || acessos.get(PERMISSAO_CONSULTA_CONTAS_PAGAR).isPermissao()
                || acessos.get(PERMISSAO_CONSULTA_CONTAS_RECEBER).isPermissao())) {
            FramePrincipal.menuCaixa.setEnabled(false);
        } else {
            FramePrincipal.menuCaixa.setEnabled(true);
        }

        //Verifica o menu de Relatórios
        if (!(acessos.get(PERMISSAO_RELATORIO_ALMOXARIFADO).isPermissao()
                || acessos.get(PERMISSAO_RELATORIO_DEDODURO).isPermissao()
                || acessos.get(PERMISSAO_RELATORIO_ENTIDADE).isPermissao()
                || acessos.get(PERMISSAO_RELATORIO_PRODUTO).isPermissao())) {
            FramePrincipal.menuRelatorio.setEnabled(false);
        } else {
            FramePrincipal.menuRelatorio.setEnabled(true);
        }

        //Verifica o menu de Transação
        if (!(acessos.get(PERMISSAO_CONSULTA_COMPRA).isPermissao()
                || acessos.get(PERMISSAO_CONSULTA_VENDA).isPermissao()
                || acessos.get(PERMISSAO_CONSULTA_PESAGEM).isPermissao())) {
            FramePrincipal.menuTransacoes.setEnabled(false);
        } else {
            FramePrincipal.menuTransacoes.setEnabled(true);
        }

        //Verifica o menu de Informações
        if (!(acessos.get(PERMISSAO_CONSULTA_ENTIDADE).isPermissao()
                || acessos.get(PERMISSAO_CONSULTA_PRODUTO).isPermissao()
                || acessos.get(PERMISSAO_CONSULTA_TABELA).isPermissao())) {
            FramePrincipal.menuInformacoes.setEnabled(false);
        } else {
            FramePrincipal.menuInformacoes.setEnabled(true);
        }

        return true;
    }

    /**
     * Recupera no banco de dados as permissões de acessos para @user
     *
     * @return - retorna o sucesso da operação
     */
    private static boolean getPermissaoUsuarioUser() {
        acessos = new DAOPermissaoUsuario().consultar(FramePrincipal.user);

        return true;
    }

    /**
     * Configura o sistema e o bd_segurança para o primeiro acesso.
     */
    private void primeiroAcesso() throws ParseException {

        /*
         * Variáveis para criação do primeiro usuário
         */
        DAOUsuario dao = new DAOUsuario();
        List<Usuario> listUsuarios = dao.listarTodos(true);
        java.sql.Date validade = new java.sql.Date(new SimpleDateFormat("dd/MM/yy").parse("01/01/9999").getTime());

        /*
         * Caso não exista nenhum usuário registrado no bd
         */
        while (listUsuarios.size() < 1) {
            /*
             * Cria um usuário do sistema
             */
            this.user = new Usuario("administrador", "AdminRoot12", "Administrador", true, validade);
            dao.inserir(this.user);
            listUsuarios = dao.listarTodos(true);

            if (listUsuarios.size() < 1) {
                JOptionPane.showMessageDialog(this, "Criando um novo usuário para acessar o sistema!", "Configurando o primeiro acesso.", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        conectaUsuario(listUsuarios.get(0));

        /*
         * Caso não exista as permissões do sistema no bd
         */
        List<Permissao> ListPermissoes = new DAOPermissao().listar();
        if (ListPermissoes.size() < this.NUMERO_DE_PERMISSOES) {

            //Grava no bd as permissões do sistema
            if (!new DAOPermissao().primeiroAcesso()) {
                System.exit(1);
            }
        }

        /*
         * Verifica se System User já tem seus acessos configurados
         */
        DAOPermissaoUsuario daoAcessos = new DAOPermissaoUsuario();
        List<PermissaoUsuario> listAcessos = daoAcessos.consultar(user);
        if (listAcessos.size() < NUMERO_DE_PERMISSOES) {
            /*
             * Configura os acessos de System User
             */
            if (new DAOPermissaoUsuario().configuraAcessosAdministrador(this.user)) {
                JOptionPane.showMessageDialog(this, "Acessos para System User criados com sucesso!", "Configurando o primeiro acesso.", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao criar os acessos para System User!",
                        "Configurando o primeiro acesso.",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }
    }

    /**
     * Fecha todos os Frames internos do DesktopPanel
     */
    public static void fechaTodosFramesInternos() {
        JInternalFrame[] frames = FramePrincipal.desktopPanel.getAllFrames();

        for (int index = 0; index < frames.length; index++) {
            frames[index].dispose();
        }
    }

    public static void carregarLogotipo() {
        try {
            File file = new File("logo.png");
            ((MyDesktopPanel) panelLogo).setImgBackground(file);
        } catch (Exception er) {
            er.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar o arquivo logo.png para o painel lateral!", "ERRO!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPanel = new MyDesktopPanel();
        panelMensagem = new javax.swing.JPanel();
        textMensagem = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        buttonEntidades = new javax.swing.JButton();
        buttonProdutos = new javax.swing.JButton();
        buttonTabelaDePesagem = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        buttonPesagem = new javax.swing.JButton();
        buttonCompra = new javax.swing.JButton();
        buttonVenda = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        buttonContasPagar = new javax.swing.JButton();
        buttonContasReceber = new javax.swing.JButton();
        buttonCaixaDiário = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        buttonRelatorioPorEntidade = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        buttonPreferencias = new javax.swing.JButton();
        panelLateral = new javax.swing.JPanel();
        labelUsuario = new javax.swing.JLabel();
        buttonLogin = new javax.swing.JButton();
        buttonLogoff = new javax.swing.JButton();
        labelConectado = new javax.swing.JLabel();
        labelDesconectado = new javax.swing.JLabel();
        panelLogo = new MyDesktopPanel();
        menuBar = new javax.swing.JMenuBar();
        menuInformacoes = new javax.swing.JMenu();
        menuItemEntidades = new javax.swing.JMenuItem();
        menuItemProdutos = new javax.swing.JMenuItem();
        menuItemTabelaPesagem = new javax.swing.JMenuItem();
        menuTransacoes = new javax.swing.JMenu();
        menuItemPesagem = new javax.swing.JMenuItem();
        menuItemProdutosCompra = new javax.swing.JMenuItem();
        menuItemProdutosVenda = new javax.swing.JMenuItem();
        menuCaixa = new javax.swing.JMenu();
        menuItemMovimentoCaixaDiario = new javax.swing.JMenuItem();
        menuItemContasPagar = new javax.swing.JMenuItem();
        menuItemContasReceber = new javax.swing.JMenuItem();
        menuRelatorio = new javax.swing.JMenu();
        menuItemRelatorioPorEntidade = new javax.swing.JMenuItem();
        menuItemRelatorioEstoque = new javax.swing.JMenuItem();
        menuItemRelatorioDedoDuro = new javax.swing.JMenuItem();
        menuOpcoes = new javax.swing.JMenu();
        Usuarios = new javax.swing.JMenu();
        menuItemUsuariosAlterarSenha = new javax.swing.JMenuItem();
        menuItemUsuariosGerenciar = new javax.swing.JMenuItem();
        menuItemPreferencias = new javax.swing.JMenuItem();
        menuAjuda = new javax.swing.JMenu();
        menuItemSobre = new javax.swing.JMenuItem();

        setTitle("Sistema de Automação de Pesagem de Cargas e Veículos - Versão 2.1");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        desktopPanel.setBackground(new java.awt.Color(255, 255, 255));
        desktopPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        desktopPanel.setToolTipText("");
        desktopPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        panelMensagem.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        textMensagem.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        textMensagem.setBorder(null);

        javax.swing.GroupLayout panelMensagemLayout = new javax.swing.GroupLayout(panelMensagem);
        panelMensagem.setLayout(panelMensagemLayout);
        panelMensagemLayout.setHorizontalGroup(
            panelMensagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, 1155, Short.MAX_VALUE)
        );
        panelMensagemLayout.setVerticalGroup(
            panelMensagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMensagemLayout.createSequentialGroup()
                .addComponent(textMensagem)
                .addContainerGap())
        );

        jToolBar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        buttonEntidades.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/barra/Entidades.png"))); // NOI18N
        buttonEntidades.setToolTipText("Entidades");
        buttonEntidades.setFocusable(false);
        buttonEntidades.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonEntidades.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonEntidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEntidadesActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonEntidades);

        buttonProdutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/barra/Produtos.png"))); // NOI18N
        buttonProdutos.setToolTipText("Produtos");
        buttonProdutos.setFocusable(false);
        buttonProdutos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonProdutos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonProdutosActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonProdutos);

        buttonTabelaDePesagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/barra/Tabela de Pesagem.png"))); // NOI18N
        buttonTabelaDePesagem.setToolTipText("Tabela de Preços de Pesagem");
        buttonTabelaDePesagem.setFocusable(false);
        buttonTabelaDePesagem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonTabelaDePesagem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonTabelaDePesagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTabelaDePesagemActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonTabelaDePesagem);
        jToolBar1.add(jSeparator2);

        buttonPesagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/barra/Pesagem.png"))); // NOI18N
        buttonPesagem.setToolTipText("Pesagens");
        buttonPesagem.setFocusable(false);
        buttonPesagem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonPesagem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonPesagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPesagemActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonPesagem);

        buttonCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/barra/Compra.png"))); // NOI18N
        buttonCompra.setToolTipText("Compra");
        buttonCompra.setFocusable(false);
        buttonCompra.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonCompra.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCompraActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonCompra);

        buttonVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/barra/Venda.png"))); // NOI18N
        buttonVenda.setToolTipText("Venda");
        buttonVenda.setFocusable(false);
        buttonVenda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonVenda.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonVendaActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonVenda);
        jToolBar1.add(jSeparator1);

        buttonContasPagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/barra/Pagar.png"))); // NOI18N
        buttonContasPagar.setToolTipText("Contas a Pagar");
        buttonContasPagar.setFocusable(false);
        buttonContasPagar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonContasPagar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonContasPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonContasPagarActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonContasPagar);

        buttonContasReceber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/barra/Receber.png"))); // NOI18N
        buttonContasReceber.setToolTipText("Contas a Receber");
        buttonContasReceber.setFocusable(false);
        buttonContasReceber.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonContasReceber.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonContasReceber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonContasReceberActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonContasReceber);

        buttonCaixaDiário.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/barra/Caixa Diário.png"))); // NOI18N
        buttonCaixaDiário.setToolTipText("Movimento de Caixa Diário");
        buttonCaixaDiário.setFocusable(false);
        buttonCaixaDiário.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonCaixaDiário.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonCaixaDiário.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCaixaDiárioActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonCaixaDiário);
        jToolBar1.add(jSeparator3);

        buttonRelatorioPorEntidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/barra/Relatorio Entidade.png"))); // NOI18N
        buttonRelatorioPorEntidade.setToolTipText("Relatório Por Entidades");
        buttonRelatorioPorEntidade.setFocusable(false);
        buttonRelatorioPorEntidade.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonRelatorioPorEntidade.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonRelatorioPorEntidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRelatorioPorEntidadeActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonRelatorioPorEntidade);
        jToolBar1.add(jSeparator4);

        buttonPreferencias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/barra/preferencias.png"))); // NOI18N
        buttonPreferencias.setFocusable(false);
        buttonPreferencias.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonPreferencias.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonPreferencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPreferenciasActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonPreferencias);

        panelLateral.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelUsuario.setText("Usuário Desconectado");

        buttonLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Login.png"))); // NOI18N
        buttonLogin.setText("Login");
        buttonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLoginActionPerformed(evt);
            }
        });

        buttonLogoff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Logoff.png"))); // NOI18N
        buttonLogoff.setText("Logoff");
        buttonLogoff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLogoffActionPerformed(evt);
            }
        });

        labelConectado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Conectado.png"))); // NOI18N

        labelDesconectado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Desconectado.png"))); // NOI18N

        carregarLogotipo();
        panelLogo.setBackground(new java.awt.Color(231, 231, 227));

        javax.swing.GroupLayout panelLateralLayout = new javax.swing.GroupLayout(panelLateral);
        panelLateral.setLayout(panelLateralLayout);
        panelLateralLayout.setHorizontalGroup(
            panelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLateralLayout.createSequentialGroup()
                .addGroup(panelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(buttonLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonLogoff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                        .addComponent(panelLogo))
                    .addGroup(panelLateralLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelDesconectado, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelConectado, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelLateralLayout.setVerticalGroup(
            panelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLateralLayout.createSequentialGroup()
                .addGroup(panelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelConectado, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDesconectado, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelUsuario)
                .addGap(18, 18, 18)
                .addComponent(buttonLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonLogoff)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        menuInformacoes.setText("Informações");
        menuInformacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nenhum(evt);
            }
        });

        menuItemEntidades.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        menuItemEntidades.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/menus/Entidades (1).png"))); // NOI18N
        menuItemEntidades.setText("Entidades");
        menuItemEntidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemEntidadesActionPerformed(evt);
            }
        });
        menuInformacoes.add(menuItemEntidades);

        menuItemProdutos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        menuItemProdutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/menus/Produtos.png"))); // NOI18N
        menuItemProdutos.setText("Produtos");
        menuItemProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemProdutosActionPerformed(evt);
            }
        });
        menuInformacoes.add(menuItemProdutos);

        menuItemTabelaPesagem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.ALT_MASK));
        menuItemTabelaPesagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/menus/Tabela de Pesagem.png"))); // NOI18N
        menuItemTabelaPesagem.setText("Tabela de Pesagem");
        menuItemTabelaPesagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemTabelaPesagemActionPerformed(evt);
            }
        });
        menuInformacoes.add(menuItemTabelaPesagem);

        menuBar.add(menuInformacoes);

        menuTransacoes.setText("Transações");

        menuItemPesagem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        menuItemPesagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/menus/Pesagem.png"))); // NOI18N
        menuItemPesagem.setText("Pesagem");
        menuItemPesagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPesagemActionPerformed(evt);
            }
        });
        menuTransacoes.add(menuItemPesagem);

        menuItemProdutosCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/menus/Compra.png"))); // NOI18N
        menuItemProdutosCompra.setText("Compra de Produtos");
        menuItemProdutosCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemProdutosCompraActionPerformed(evt);
            }
        });
        menuTransacoes.add(menuItemProdutosCompra);

        menuItemProdutosVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/menus/Venda.png"))); // NOI18N
        menuItemProdutosVenda.setText("Venda de Produtos");
        menuItemProdutosVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemProdutosVendaActionPerformed(evt);
            }
        });
        menuTransacoes.add(menuItemProdutosVenda);

        menuBar.add(menuTransacoes);

        menuCaixa.setText("Caixa");

        menuItemMovimentoCaixaDiario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/menus/Caixa Diário.png"))); // NOI18N
        menuItemMovimentoCaixaDiario.setText("Movimento de Caixa Diário");
        menuItemMovimentoCaixaDiario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemMovimentoCaixaDiarioActionPerformed(evt);
            }
        });
        menuCaixa.add(menuItemMovimentoCaixaDiario);

        menuItemContasPagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/menus/Pagar.png"))); // NOI18N
        menuItemContasPagar.setText("Contas a Pagar");
        menuItemContasPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemContasPagarActionPerformed(evt);
            }
        });
        menuCaixa.add(menuItemContasPagar);

        menuItemContasReceber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/menus/Receber.png"))); // NOI18N
        menuItemContasReceber.setText("Contas a Receber");
        menuItemContasReceber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemContasReceberActionPerformed(evt);
            }
        });
        menuCaixa.add(menuItemContasReceber);

        menuBar.add(menuCaixa);

        menuRelatorio.setText("Relatórios");

        menuItemRelatorioPorEntidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/menus/Relatorio por Entidade (2).png"))); // NOI18N
        menuItemRelatorioPorEntidade.setText("Por Entidade");
        menuItemRelatorioPorEntidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemRelatorioPorEntidadeActionPerformed(evt);
            }
        });
        menuRelatorio.add(menuItemRelatorioPorEntidade);

        menuItemRelatorioEstoque.setText("Estoque");
        menuItemRelatorioEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemRelatorioEstoqueActionPerformed(evt);
            }
        });
        menuRelatorio.add(menuItemRelatorioEstoque);

        menuItemRelatorioDedoDuro.setText("Rastreio de Usuários");
        menuItemRelatorioDedoDuro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemRelatorioDedoDuroActionPerformed(evt);
            }
        });
        menuRelatorio.add(menuItemRelatorioDedoDuro);

        menuBar.add(menuRelatorio);

        menuOpcoes.setText("Opções");
        menuOpcoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOpcoesActionPerformed(evt);
            }
        });

        Usuarios.setText("Usuários");

        menuItemUsuariosAlterarSenha.setText("Alterar minha senha");
        menuItemUsuariosAlterarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemUsuariosAlterarSenhaActionPerformed(evt);
            }
        });
        Usuarios.add(menuItemUsuariosAlterarSenha);

        menuItemUsuariosGerenciar.setText("Gerenciar usuários");
        menuItemUsuariosGerenciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemUsuariosGerenciarActionPerformed(evt);
            }
        });
        Usuarios.add(menuItemUsuariosGerenciar);

        menuOpcoes.add(Usuarios);

        menuItemPreferencias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/menus/preferencias.png"))); // NOI18N
        menuItemPreferencias.setText("Preferências");
        menuItemPreferencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPreferenciasActionPerformed(evt);
            }
        });
        menuOpcoes.add(menuItemPreferencias);

        menuBar.add(menuOpcoes);

        menuAjuda.setText("Ajuda");

        menuItemSobre.setText("Sobre");
        menuItemSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSobreActionPerformed(evt);
            }
        });
        menuAjuda.add(menuItemSobre);

        menuBar.add(menuAjuda);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
                    .addComponent(desktopPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelLateral, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(desktopPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                    .addComponent(panelLateral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addComponent(panelMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemProdutosActionPerformed
        InternalFrameProdutos produtos = new InternalFrameProdutos();
        desktopPanel.add(produtos);

        desktopPanel.getDesktopManager().activateFrame(produtos);
    }//GEN-LAST:event_menuItemProdutosActionPerformed

    private void nenhum(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nenhum
    }//GEN-LAST:event_nenhum

    private void menuItemTabelaPesagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemTabelaPesagemActionPerformed
        InternalFrameTabelaPrecosPesagem tabelaPesagem = new InternalFrameTabelaPrecosPesagem();
        desktopPanel.add(tabelaPesagem);

        desktopPanel.getDesktopManager().activateFrame(tabelaPesagem);
    }//GEN-LAST:event_menuItemTabelaPesagemActionPerformed

    private void menuItemEntidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemEntidadesActionPerformed
        InternalFrameEntidade entidade = new InternalFrameEntidade();
        desktopPanel.add(entidade);

        desktopPanel.getDesktopManager().activateFrame(entidade);
    }//GEN-LAST:event_menuItemEntidadesActionPerformed

    private void menuItemPesagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPesagemActionPerformed

        int countTabela = AbstractDAO.count(Tabelapesagem.class);
        int countEntidade = AbstractDAO.count(Entidade.class);

        if (countTabela > 0) {
            if (countEntidade > 0) {
                InternalFramePesagem pesagem = new InternalFramePesagem();
                desktopPanel.add(pesagem);
                desktopPanel.getDesktopManager().activateFrame(pesagem);

            } else {
                JOptionPane.showMessageDialog(this, "Ainda não foi cadastrado nenhuma entidade, "
                        + "realize o cadastro antes de solicitar esta transação.", "Atenção",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ainda não foi cadastrado item para tabela de pesagem, "
                    + "realize o cadastro antes de solicitar esta transação.", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_menuItemPesagemActionPerformed

    private void menuItemSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSobreActionPerformed
        InternalFrameSobre sobre = new InternalFrameSobre();
        desktopPanel.add(sobre);

        desktopPanel.getDesktopManager().activateFrame(sobre);
    }//GEN-LAST:event_menuItemSobreActionPerformed

    private void menuOpcoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOpcoesActionPerformed
    }//GEN-LAST:event_menuOpcoesActionPerformed

    private void menuItemPreferenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPreferenciasActionPerformed
        InternalFramePreferencias pref = new InternalFramePreferencias();
        desktopPanel.add(pref);

        desktopPanel.getDesktopManager().activateFrame(pref);
    }//GEN-LAST:event_menuItemPreferenciasActionPerformed

    private void menuItemRelatorioPorEntidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemRelatorioPorEntidadeActionPerformed
        InternalFramePorEntidade rel = new InternalFramePorEntidade();
        desktopPanel.add(rel);

        desktopPanel.getDesktopManager().activateFrame(rel);
    }//GEN-LAST:event_menuItemRelatorioPorEntidadeActionPerformed

private void buttonTabelaDePesagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTabelaDePesagemActionPerformed
    this.menuItemTabelaPesagemActionPerformed(evt);
}//GEN-LAST:event_buttonTabelaDePesagemActionPerformed

private void buttonPesagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPesagemActionPerformed
    this.menuItemPesagemActionPerformed(evt);
}//GEN-LAST:event_buttonPesagemActionPerformed

private void buttonRelatorioPorEntidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRelatorioPorEntidadeActionPerformed
    this.menuItemRelatorioPorEntidadeActionPerformed(evt);
}//GEN-LAST:event_buttonRelatorioPorEntidadeActionPerformed

private void menuItemProdutosCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemProdutosCompraActionPerformed
    InternalFrameProdutosCompra compra = new InternalFrameProdutosCompra();
    desktopPanel.add(compra);

    desktopPanel.getDesktopManager().activateFrame(compra);
}//GEN-LAST:event_menuItemProdutosCompraActionPerformed

private void menuItemProdutosVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemProdutosVendaActionPerformed
    InternalFrameProdutosVenda venda = new InternalFrameProdutosVenda();
    desktopPanel.add(venda);

    /*
     * Focaliza o novo JInternalFrame
     */
    desktopPanel.getDesktopManager().activateFrame(venda);
}//GEN-LAST:event_menuItemProdutosVendaActionPerformed

private void buttonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLoginActionPerformed
    /*
     * Chama Frame de Login
     */
    InternalFrameLogin login = new InternalFrameLogin();
    this.desktopPanel.add(login);

    desktopPanel.getDesktopManager().activateFrame(login);
}//GEN-LAST:event_buttonLoginActionPerformed

private void buttonLogoffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLogoffActionPerformed

    /**
     * Pergunta se o cliente realmente deseja cadastrar a transação*
     */
    int opcao = JOptionPane.showOptionDialog(this, "Tem certeza que efetuar logoff?", "Logoff",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesOpcoes, botoesOpcoes[0]);

    if (opcao == JOptionPane.YES_OPTION) {
        FramePrincipal.desconectaUsuario();
        buttonLogin.setEnabled(true);
        buttonLogoff.setEnabled(false);
    }
}//GEN-LAST:event_buttonLogoffActionPerformed

    private void menuItemUsuariosGerenciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemUsuariosGerenciarActionPerformed
        InternalFrameUsuarios usuarios = new InternalFrameUsuarios();
        this.desktopPanel.add(usuarios);

        desktopPanel.getDesktopManager().activateFrame(usuarios);
    }//GEN-LAST:event_menuItemUsuariosGerenciarActionPerformed

    private void menuItemUsuariosAlterarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemUsuariosAlterarSenhaActionPerformed
        InternalFrameAlteraSenha altera = new InternalFrameAlteraSenha();
        this.desktopPanel.add(altera);

        desktopPanel.getDesktopManager().activateFrame(altera);
    }//GEN-LAST:event_menuItemUsuariosAlterarSenhaActionPerformed

private void menuItemRelatorioDedoDuroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemRelatorioDedoDuroActionPerformed
    InternalFrameRastreio dedoDuro = new InternalFrameRastreio();
    this.desktopPanel.add(dedoDuro);

    desktopPanel.getDesktopManager().activateFrame(dedoDuro);
}//GEN-LAST:event_menuItemRelatorioDedoDuroActionPerformed

    private void buttonCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCompraActionPerformed

        int countProduto = AbstractDAO.count(Produto.class);
        int countEntidade = AbstractDAO.count(Entidade.class);

        if (countProduto > 0) {
            if (countEntidade > 0) {
                InternalFrameProdutosCompra compra = new InternalFrameProdutosCompra();
                desktopPanel.add(compra);
                desktopPanel.getDesktopManager().activateFrame(compra);

            } else {
                JOptionPane.showMessageDialog(this, "Ainda não foi cadastrado nenhuma entidade, "
                        + "realize o cadastro antes de solicitar esta transação.", "Atenção",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ainda não foi cadastrado nenhum produto, "
                    + "realize o cadastro antes de solicitar esta transação.", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_buttonCompraActionPerformed

    private void buttonVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonVendaActionPerformed
        int countProduto = AbstractDAO.count(Produto.class);
        int countEntidade = AbstractDAO.count(Entidade.class);

        if (countProduto > 0) {
            if (countEntidade > 0) {

                InternalFrameProdutosVenda venda = new InternalFrameProdutosVenda();
                desktopPanel.add(venda);
                desktopPanel.getDesktopManager().activateFrame(venda);

            } else {
                JOptionPane.showMessageDialog(this, "Ainda não foi cadastrado nenhuma entidade, "
                        + "realize o cadastro antes de solicitar esta transação.", "Atenção",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ainda não foi cadastrado nenhum produto, "
                    + "realize o cadastro antes de solicitar esta transação.", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_buttonVendaActionPerformed

    private void buttonProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonProdutosActionPerformed
        InternalFrameProdutos produtos = new InternalFrameProdutos();
        desktopPanel.add(produtos);

        desktopPanel.getDesktopManager().activateFrame(produtos);
    }//GEN-LAST:event_buttonProdutosActionPerformed

    private void menuItemRelatorioEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemRelatorioEstoqueActionPerformed
        InternalFrameEstoque produtos = new InternalFrameEstoque();
        desktopPanel.add(produtos);

        desktopPanel.getDesktopManager().activateFrame(produtos);
    }//GEN-LAST:event_menuItemRelatorioEstoqueActionPerformed

    private void menuItemMovimentoCaixaDiarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemMovimentoCaixaDiarioActionPerformed
        InternalFrameMovimentoCaixa iFrame = new InternalFrameMovimentoCaixa();
        desktopPanel.add(iFrame);

        desktopPanel.getDesktopManager().activateFrame(iFrame);
    }//GEN-LAST:event_menuItemMovimentoCaixaDiarioActionPerformed

    private void menuItemContasReceberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemContasReceberActionPerformed
        InternalFrameContasReceber iFrame = new InternalFrameContasReceber();
        desktopPanel.add(iFrame);

        desktopPanel.getDesktopManager().activateFrame(iFrame);
    }//GEN-LAST:event_menuItemContasReceberActionPerformed

    private void menuItemContasPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemContasPagarActionPerformed
        InternalFrameContasPagar iFrame = new InternalFrameContasPagar();
        desktopPanel.add(iFrame);

        desktopPanel.getDesktopManager().activateFrame(iFrame);
    }//GEN-LAST:event_menuItemContasPagarActionPerformed

    private void buttonContasPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonContasPagarActionPerformed
        this.menuItemContasPagarActionPerformed(evt);
    }//GEN-LAST:event_buttonContasPagarActionPerformed

    private void buttonContasReceberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonContasReceberActionPerformed
        this.menuItemContasReceberActionPerformed(evt);
    }//GEN-LAST:event_buttonContasReceberActionPerformed

    private void buttonCaixaDiárioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCaixaDiárioActionPerformed
        this.menuItemMovimentoCaixaDiarioActionPerformed(evt);
    }//GEN-LAST:event_buttonCaixaDiárioActionPerformed

    private void buttonEntidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEntidadesActionPerformed
        this.menuItemEntidadesActionPerformed(evt);
    }//GEN-LAST:event_buttonEntidadesActionPerformed

    private void buttonPreferenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPreferenciasActionPerformed
        this.menuItemPreferenciasActionPerformed(evt);
    }//GEN-LAST:event_buttonPreferenciasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new FramePrincipal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Usuarios;
    public static javax.swing.JButton buttonCaixaDiário;
    public static javax.swing.JButton buttonCompra;
    public static javax.swing.JButton buttonContasPagar;
    public static javax.swing.JButton buttonContasReceber;
    private static javax.swing.JButton buttonEntidades;
    public static javax.swing.JButton buttonLogin;
    public static javax.swing.JButton buttonLogoff;
    private static javax.swing.JButton buttonPesagem;
    public static javax.swing.JButton buttonPreferencias;
    public static javax.swing.JButton buttonProdutos;
    private static javax.swing.JButton buttonRelatorioPorEntidade;
    private static javax.swing.JButton buttonTabelaDePesagem;
    public static javax.swing.JButton buttonVenda;
    public static MyDesktopPanel desktopPanel;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar jToolBar1;
    private static javax.swing.JLabel labelConectado;
    private static javax.swing.JLabel labelDesconectado;
    private static javax.swing.JLabel labelUsuario;
    private javax.swing.JMenu menuAjuda;
    private javax.swing.JMenuBar menuBar;
    private static javax.swing.JMenu menuCaixa;
    private static javax.swing.JMenu menuInformacoes;
    private static javax.swing.JMenuItem menuItemContasPagar;
    private static javax.swing.JMenuItem menuItemContasReceber;
    private static javax.swing.JMenuItem menuItemEntidades;
    private static javax.swing.JMenuItem menuItemMovimentoCaixaDiario;
    private static javax.swing.JMenuItem menuItemPesagem;
    private static javax.swing.JMenuItem menuItemPreferencias;
    private static javax.swing.JMenuItem menuItemProdutos;
    static javax.swing.JMenuItem menuItemProdutosCompra;
    static javax.swing.JMenuItem menuItemProdutosVenda;
    static javax.swing.JMenuItem menuItemRelatorioDedoDuro;
    public static javax.swing.JMenuItem menuItemRelatorioEstoque;
    static javax.swing.JMenuItem menuItemRelatorioPorEntidade;
    private javax.swing.JMenuItem menuItemSobre;
    private static javax.swing.JMenuItem menuItemTabelaPesagem;
    private static javax.swing.JMenuItem menuItemUsuariosAlterarSenha;
    private static javax.swing.JMenuItem menuItemUsuariosGerenciar;
    private static javax.swing.JMenu menuOpcoes;
    static javax.swing.JMenu menuRelatorio;
    private static javax.swing.JMenu menuTransacoes;
    private javax.swing.JPanel panelLateral;
    public static MyDesktopPanel panelLogo;
    private javax.swing.JPanel panelMensagem;
    private javax.swing.JTextField textMensagem;
    // End of variables declaration//GEN-END:variables
}
