/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InternalFrameUsuarios.java
 *
 * Created on 19/09/2011, 08:45:16
 */
package br.com.areiasbrittos.gui.internalFrames;

import br.com.areiasbrittos.controle.objetos.seguranca.*;
import br.com.areiasbrittos.gui.utils.ApagaElementosDaInterface;
import br.com.areiasbrittos.gui.utils.ConsertaBugsGUI;
import br.com.areiasbrittos.gui.utils.EnabledGUIElements;
import br.com.areiasbrittos.gui.utils.TamanhoJTextField;
import br.com.areiasbrittos.gui.utils.MascarasJTextField;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import br.com.areiasbrittos.gui.FramePrincipal;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOPermissao;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOPermissaoUsuario;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOTransacao;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOUsuario;

/**
 * @author Maycon Fernando Silva Brito @email mayconfsbrito@gmail.com
 */
public class InternalFrameUsuarios extends br.com.areiasbrittos.gui.superclass.InternalFrame {

    /*
     * Instâncias para outras classes
     */
    DAOUsuario daoUsuario = new DAOUsuario();
    DAOPermissaoUsuario daoAcessos = new DAOPermissaoUsuario();
    DAOPermissao daoPermissao = new DAOPermissao();
    private ConsertaBugsGUI con = new ConsertaBugsGUI();
    /*
     * Variáveis para limitar TextFields
     */
    private TamanhoJTextField limita = new TamanhoJTextField();
    private final int[] tamanhosJTextField = {45, 45, 45};
    TableModel modeloAcessos;

    /**
     * Creates new form InternalFrameUsuarios
     */
    public InternalFrameUsuarios() {
        super();
        initComponents();
        this.setVisible(true);
        this.inicializa();
    }

    @Override
    protected void inicializa() {
        /**
         * Limita a quantidade de caracteres possíveis em textMotorista*
         */
        limita.limitaTamanho(tamanhosJTextField, textNome, textLogin);

        /**
         * Inicializa eventos da tabela de Usuários*
         */
        SelectionListener listener = new SelectionListener(this.tabelaUsuarios);
        this.tabelaUsuarios.getSelectionModel().addListSelectionListener(listener);
        this.tabelaUsuarios.getColumnModel().getSelectionModel().addListSelectionListener(listener);

        this.limparGUI();
        this.guiAtiva(true);
    }

    /**
     * Preenche a interface a partir da seleção de um elemento na tabela
     */
    @Override
    protected void tabelaPreencheGUI() {
        try {
            int linha = tabelaUsuarios.getSelectedRow();

            Integer cod;
            try {
                cod = (Integer) tabelaUsuarios.getValueAt(linha, 0);
            } catch (IndexOutOfBoundsException er) {
                cod = 0;
            }
            List<Usuario> consulta = daoUsuario.consultar("idUsuario=" + cod);

            if (consulta.size() > 0) {
                Usuario user = consulta.get(0);

                if (user.getIdUsuario() != 1) {

                    //Preenche os componentes do painel de informações
                    textCodigo.setText(user.getIdUsuario().toString());
                    textNome.setText(user.getNome());
                    textLogin.setText(user.getLogin());
                    textValidade.setText(con.getDataFormatadaNoBd(user.getValidade(), "dd/MM/yyyy"));

                    if (user.isAtivo()) {
                        radioAtivo.setSelected(true);
                    } else {
                        radioInativo.setSelected(true);
                    }

                    //Preenche a tabela de acessos
                    this.listarPesquisaNaTabelaAcessos(user);
                    this.guiAtiva(false);
                    
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            // ex.printStackTrace();
        }
    }

    @Override
    protected void limparGUI() {

        textCodigo.setText("");
        tabelaUsuarios.clearSelection();
        tabelaAcessos.clearSelection();
        this.listarTodosElementosNaTabelaUsuarios();
        this.listarPesquisaNaTabelaAcessos(null);
        new ApagaElementosDaInterface().apagaJTextField(textLogin, textNome, textValidade);
        groupAtivo.clearSelection();

        this.guiAtiva(true);

    }

    private void listarTodosElementosNaTabelaUsuarios() {

        List<Usuario> listUsuarios = daoUsuario.listarTodos(false);
        
        //Inicializa as colunas da tabela*
        TableColumn coluna = tabelaUsuarios.getColumnModel().getColumn(0);
        TableColumn coluna1 = tabelaUsuarios.getColumnModel().getColumn(1);
        TableColumn coluna2 = tabelaUsuarios.getColumnModel().getColumn(2);

        //Dimensiona as colunas na tabela*
        coluna.setPreferredWidth(20);
        coluna1.setPreferredWidth(80);
        coluna2.setPreferredWidth(60);

        //Inicializa o modelo declarado acima*
        DefaultTableModel modelo = (DefaultTableModel) tabelaUsuarios.getModel();
        modelo.setNumRows(0);

        try {
            
            //Preenche a tabela com os elementos de @list*
            for (int i = 0; i < listUsuarios.size(); i++) {
                modelo.addRow(new Object[]{listUsuarios.get(i).getIdUsuario(), listUsuarios.get(i).getNome(), listUsuarios.get(i).getLogin()});
            }
        } catch (Exception er) {
            JOptionPane.showMessageDialog(this, "Erro na listagem!\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();

        }
    }

    @Override
    protected void guiAtiva(boolean estado) {
        if (estado) {
            new EnabledGUIElements().enabledJComponent(buttonCadastro);
            new EnabledGUIElements().disabledJComponent(buttonAlterar);

        } else {
            new EnabledGUIElements().enabledJComponent(buttonAlterar);
            new EnabledGUIElements().disabledJComponent(buttonCadastro);
        }
    }

    /**
     * Lista na tabela os objetos Pesagem em @list
     *
     * @param list - Lista de objetos Pesagem
     */
    private void listarPesquisaNaTabelaAcessos(Usuario user) {

        //Consulta as permissões possíveis
        List<Permissao> listPermissao = daoPermissao.listar();

        //Inicializa as colunas da tabela
        TableColumn coluna = tabelaAcessos.getColumnModel().getColumn(0);
        TableColumn coluna1 = tabelaAcessos.getColumnModel().getColumn(1);
        TableColumn coluna2 = tabelaAcessos.getColumnModel().getColumn(2);

        //Dimensiona as colunas na tabela
        coluna.setPreferredWidth(20);
        coluna1.setPreferredWidth(70);
        coluna2.setPreferredWidth(10);

        //Seta o DefaultTableModel
        DefaultTableModel modelo = (DefaultTableModel) tabelaAcessos.getModel();
        modelo.setNumRows(0);

        //Caso seja passado em @user o usuário
        if (user != null) {

            //Consulta os acessos do usuário
            try {
                List<PermissaoUsuario> listAcessos = daoAcessos.consultar(user);
              
                //Preenche a tabela com os acessos de @user*
                for (int i = 0; i < listAcessos.size(); i++) {
                    modelo.addRow(new Object[]{listPermissao.get(i).getIdPermissao(), listPermissao.get(i).getDescricao(), listAcessos.get(i).isPermissao()});
                }

            } catch (Exception er) {
                JOptionPane.showMessageDialog(this, "Erro na listagem!\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
                er.printStackTrace();
            }
        } else {
            
            //Preenche a tabela com os tipos de acessos possíveis
            try {
                for (int i = 0; i < listPermissao.size(); i++) {
                    modelo.addRow(new Object[]{listPermissao.get(i).getIdPermissao(), listPermissao.get(i).getDescricao(), false});

                }
            } catch (Exception er) {
                JOptionPane.showMessageDialog(this, "Erro na listagem!\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
                er.printStackTrace();
            }
        }

    }

    /**
     * Captura os valores da GUI para criar um objeto Usuario
     *
     * @return - Instancia do Usuário capturado pela GUI ou NULL caso a GUI
     * esteja incompleta
     */
    private Usuario criaUsuario() {

        /*
         * Verifica se os campos obrigatórios estão preenchidos
         */
        if (textNome.getText().isEmpty() || textLogin.getText().isEmpty() || (radioAtivo.isSelected() && radioInativo.isSelected())) {
            JOptionPane.showMessageDialog(this, "Pelo menos os seguintes campos precisam ser preenchidos:"
                    + "\nNome."
                    + "\nLogin."
                    + "\nEstado, ativo ou inativo.", "Erro!", JOptionPane.WARNING_MESSAGE);

            return null;

        } else {
            /*
             * Caso os campos obrigatórios estejam preenchidos, instancia o
             * @user com eles
             */
            Usuario user = new Usuario();

            /*
             * Captura o codigo caso ele esteja preenchido e instancia a senha,
             * para um novo usuario ou para um já existente
             */
            if (!textCodigo.getText().isEmpty()) {
                /*
                 * Como é um usuário já existente, recupera o código
                 */
                user.setIdUsuario(Integer.parseInt(textCodigo.getText()));
                /*
                 * Recupera a senha no bd e instancia @user com ela
                 */
                List<Usuario> listUser = new DAOUsuario().consultar("idUsuario=" + user.getIdUsuario());
                user.setSenha(listUser.get(0).getSenha());

            } else {
                /*
                 * Caso o código não esteja preenchido, então é um novo usuário
                 */

                /*
                 * Instancia o Id do usuario com a quantidade de usuarios no bd
                 */
                List<Usuario> listUsuario = new DAOUsuario().listarTodos(true);
                user.setIdUsuario(listUsuario.size() + 1);

                /*
                 * Instancia com a senha padrao
                 */
                user.setSenhaComMD5("123");
            }

            /*
             * Captura as informações dos campos de texto
             */
            user.setNome(textNome.getText());
            user.setLogin(textLogin.getText());
            user.setAtivo(radioAtivo.isSelected());

            /*
             * Captura a validade
             */
            try {
                if (!textValidade.getText().equals("  /  /    ")) {
                    user.setValidade(new Date(new SimpleDateFormat("dd/MM/yyyy").parse(textValidade.getText()).getTime()));
                } else {
                    user.setValidade(new Date(new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2999").getTime()));
                }
            } catch (Exception er) {
                er.printStackTrace();
            }

            return user;
        }
    }

    /**
     * Captura os acessos do usuário
     *
     * @param user
     */
    private ArrayList<PermissaoUsuario> criaAcessos(Usuario user) {

        modeloAcessos = this.tabelaAcessos.getModel();
        ArrayList<PermissaoUsuario> acessos = new ArrayList<PermissaoUsuario>();
        for (int linha = 0; linha < modeloAcessos.getRowCount(); linha++) {
            acessos.add(new PermissaoUsuario(new PermissaoUsuarioId(linha + 1, user.getIdUsuario()), user, new Permissao(linha + 1), modeloAcessos.getValueAt(linha, 2).equals(true)));
        }

        return acessos;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupAtivo = new javax.swing.ButtonGroup();
        panelInformacoes = new javax.swing.JPanel();
        labelNome = new javax.swing.JLabel();
        labelValidade = new javax.swing.JLabel();
        labelLogin = new javax.swing.JLabel();
        labelAtivo = new javax.swing.JLabel();
        textNome = new javax.swing.JTextField();
        textLogin = new javax.swing.JTextField();
        radioAtivo = new javax.swing.JRadioButton();
        radioInativo = new javax.swing.JRadioButton();
        labelCodigo = new javax.swing.JLabel();
        textCodigo = new javax.swing.JTextField();
        textValidade = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("##/##/####"));
        ((JFormattedTextField)textValidade).setFocusLostBehavior(JFormattedTextField.COMMIT);
        panelAcessos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaAcessos = new javax.swing.JTable();
        panelUsuarios = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaUsuarios = new javax.swing.JTable();
        panelOpcoes = new javax.swing.JPanel();
        buttonCancelar = new javax.swing.JButton();
        buttonCadastro = new javax.swing.JButton();
        buttonAlterar = new javax.swing.JButton();
        buttonLimpar = new javax.swing.JButton();
        buttonRedefinirSenha = new javax.swing.JButton();
        panelMensagem = new javax.swing.JPanel();
        textMensagem = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Controle de Usuários");

        panelInformacoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        labelNome.setText("Nome:");

        labelValidade.setText("Validade:");

        labelLogin.setText("Login:");

        labelAtivo.setText("Estado de ativação:");

        groupAtivo.add(radioAtivo);
        radioAtivo.setSelected(true);
        radioAtivo.setText("Ativo");

        groupAtivo.add(radioInativo);
        radioInativo.setText("Inativo");

        labelCodigo.setText("Código:");

        textCodigo.setEnabled(false);

        ((JFormattedTextField)textValidade).setHorizontalAlignment(textValidade.LEFT);

        javax.swing.GroupLayout panelInformacoesLayout = new javax.swing.GroupLayout(panelInformacoes);
        panelInformacoes.setLayout(panelInformacoesLayout);
        panelInformacoesLayout.setHorizontalGroup(
            panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelAtivo)
            .addGroup(panelInformacoesLayout.createSequentialGroup()
                .addComponent(radioAtivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioInativo))
            .addGroup(panelInformacoesLayout.createSequentialGroup()
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCodigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelInformacoesLayout.createSequentialGroup()
                    .addComponent(labelValidade)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(textValidade))
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelInformacoesLayout.createSequentialGroup()
                    .addComponent(labelLogin)
                    .addGap(18, 18, 18)
                    .addComponent(textLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        panelInformacoesLayout.setVerticalGroup(
            panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacoesLayout.createSequentialGroup()
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCodigo)
                    .addComponent(textCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNome)
                    .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textValidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelValidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelLogin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelAtivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioAtivo)
                    .addComponent(radioInativo))
                .addGap(55, 55, 55))
        );

        panelAcessos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Acessos deste usuário", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        tabelaAcessos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod", "Transação", "Permissão"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabelaAcessos);
        tabelaAcessos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaAcessos.getColumnModel().getColumn(0).setResizable(false);
        tabelaAcessos.getColumnModel().getColumn(0).setHeaderValue("Cod");
        tabelaAcessos.getColumnModel().getColumn(1).setResizable(false);
        tabelaAcessos.getColumnModel().getColumn(1).setHeaderValue("Transação");
        tabelaAcessos.getColumnModel().getColumn(2).setResizable(false);
        tabelaAcessos.getColumnModel().getColumn(2).setHeaderValue("Permissão");

        javax.swing.GroupLayout panelAcessosLayout = new javax.swing.GroupLayout(panelAcessos);
        panelAcessos.setLayout(panelAcessosLayout);
        panelAcessosLayout.setHorizontalGroup(
            panelAcessosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
        );
        panelAcessosLayout.setVerticalGroup(
            panelAcessosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAcessosLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelUsuarios.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Usuários", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        tabelaUsuarios.setAutoCreateRowSorter(true);
        tabelaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod", "Nome", "Login"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabelaUsuarios);
        tabelaUsuarios.getColumnModel().getColumn(0).setResizable(false);
        tabelaUsuarios.getColumnModel().getColumn(1).setResizable(false);

        javax.swing.GroupLayout panelUsuariosLayout = new javax.swing.GroupLayout(panelUsuarios);
        panelUsuarios.setLayout(panelUsuariosLayout);
        panelUsuariosLayout.setHorizontalGroup(
            panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelUsuariosLayout.setVerticalGroup(
            panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        panelOpcoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opções", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        buttonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Cancelar.png"))); // NOI18N
        buttonCancelar.setText("Cancelar");
        buttonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelarActionPerformed(evt);
            }
        });

        buttonCadastro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Confirma.png"))); // NOI18N
        buttonCadastro.setText("Cadastrar");
        buttonCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCadastroActionPerformed(evt);
            }
        });

        buttonAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Alterar (2).png"))); // NOI18N
        buttonAlterar.setText("Alterar");
        buttonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAlterarActionPerformed(evt);
            }
        });

        buttonLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Novo.png"))); // NOI18N
        buttonLimpar.setText("Novo/Limpar");
        buttonLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLimparActionPerformed(evt);
            }
        });

        buttonRedefinirSenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Erro (2).png"))); // NOI18N
        buttonRedefinirSenha.setText("Redefinir Senha");
        buttonRedefinirSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRedefinirSenhaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelOpcoesLayout = new javax.swing.GroupLayout(panelOpcoes);
        panelOpcoes.setLayout(panelOpcoesLayout);
        panelOpcoesLayout.setHorizontalGroup(
            panelOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcoesLayout.createSequentialGroup()
                .addComponent(buttonCadastro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonLimpar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonRedefinirSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCancelar)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelOpcoesLayout.setVerticalGroup(
            panelOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcoesLayout.createSequentialGroup()
                .addGroup(panelOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonCadastro)
                    .addGroup(panelOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonAlterar)
                        .addComponent(buttonLimpar)
                        .addComponent(buttonCancelar)
                        .addComponent(buttonRedefinirSenha)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        panelMensagem.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        textMensagem.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        textMensagem.setBorder(null);

        javax.swing.GroupLayout panelMensagemLayout = new javax.swing.GroupLayout(panelMensagem);
        panelMensagem.setLayout(panelMensagemLayout);
        panelMensagemLayout.setHorizontalGroup(
            panelMensagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
        );
        panelMensagemLayout.setVerticalGroup(
            panelMensagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMensagemLayout.createSequentialGroup()
                .addComponent(textMensagem)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(panelOpcoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelAcessos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelInformacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAcessos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOpcoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonRedefinirSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRedefinirSenhaActionPerformed
        /*
         * Captura o atual usuario da GUI
         */
        Usuario user = this.criaUsuario();
        /*
         * Redefine sua senha para 123 e salva no bd
         */
        user.setSenhaComMD5("123");
        new DAOUsuario().alterar(user);
        DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Redefiniu a senha do usuário " + user.getNome()));

        /*
         * Verifica se o usuario na memoria é o mesmo que alterou a senha
         */
        if (FramePrincipal.user.getIdUsuario() == Integer.parseInt(this.textCodigo.getText())) {
            /*
             * Altera a senha do usuario na memoria
             */
            FramePrincipal.user.setSenhaComMD5("123");
        }

    }//GEN-LAST:event_buttonRedefinirSenhaActionPerformed

    private void buttonCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCadastroActionPerformed

        /*
         * Cria um novo usuário a partir dos dados da GUI
         */
        Usuario user = this.criaUsuario();

        /*
         * Salva o novo usuario no bd
         */
        if (new DAOUsuario().inserir(user)) {
            /*
             * Salva todos os acessos deste usuario no bd
             */
            if (new DAOPermissaoUsuario().inserir(this.criaAcessos(user))) {
                limparGUI();
            }
        }
    }//GEN-LAST:event_buttonCadastroActionPerformed

    private void buttonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAlterarActionPerformed
        /*
         * Cria um novo usuário a partir dos dados da GUI
         */
        Usuario user = this.criaUsuario();

        /*
         * Salva o novo usuario no bd
         */
        if (new DAOUsuario().alterar(user)) {
            /*
             * Salva todos os acessos deste usuario no bd
             */
            if (new DAOPermissaoUsuario().alterar(this.criaAcessos(user))) {
                limparGUI();
            }
        }

        /*
         * Verifica se o usuário alterado é o mesmo da memória
         */
        if (user.getIdUsuario().equals(FramePrincipal.user.getIdUsuario())) {
            /*
             * Altera as informações do usuário na memória
             */
            FramePrincipal.user = user;

            /*
             * Altera as permissões do usuário na memória e reconfigura o
             * comportamento da interface
             */
            if (!FramePrincipal.verificaAcessos()) {
                JOptionPane.showMessageDialog(this, "Erro ao trocar a permissões deste usuário na memória.",
                        "Atenção.", JOptionPane.WARNING_MESSAGE);
            }


        }

    }//GEN-LAST:event_buttonAlterarActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAlterar;
    private javax.swing.JButton buttonCadastro;
    private javax.swing.JButton buttonCancelar;
    private javax.swing.JButton buttonLimpar;
    private javax.swing.JButton buttonRedefinirSenha;
    private javax.swing.ButtonGroup groupAtivo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelAtivo;
    private javax.swing.JLabel labelCodigo;
    private javax.swing.JLabel labelLogin;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelValidade;
    private javax.swing.JPanel panelAcessos;
    private javax.swing.JPanel panelInformacoes;
    private javax.swing.JPanel panelMensagem;
    private javax.swing.JPanel panelOpcoes;
    private javax.swing.JPanel panelUsuarios;
    private javax.swing.JRadioButton radioAtivo;
    private javax.swing.JRadioButton radioInativo;
    private javax.swing.JTable tabelaAcessos;
    private javax.swing.JTable tabelaUsuarios;
    private javax.swing.JTextField textCodigo;
    private javax.swing.JTextField textLogin;
    private javax.swing.JTextField textMensagem;
    private javax.swing.JTextField textNome;
    private javax.swing.JTextField textValidade;
    // End of variables declaration//GEN-END:variables
}
