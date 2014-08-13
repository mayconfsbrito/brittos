/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InternalFrameClientes.java
 *
 * Created on 17/01/2011, 18:27:01
 */
package br.com.areiasbrittos.gui.internalFrames;

import br.com.areiasbrittos.gui.utils.TamanhoJTextField;
import br.com.areiasbrittos.gui.utils.MascarasJTextField;
import br.com.areiasbrittos.gui.utils.ApagaElementosDaInterface;
import br.com.areiasbrittos.gui.utils.EnabledGUIElements;
import br.com.areiasbrittos.persistencia.dao.DAOProduto;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import br.com.areiasbrittos.controle.objetos.Produto;
import br.com.areiasbrittos.persistencia.ConnectionFactory_brittos_bd;
import br.com.areiasbrittos.controle.utils.ReportUtils;
import br.com.areiasbrittos.gui.FramePrincipal;
import br.com.areiasbrittos.gui.utils.*;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class InternalFrameProdutos extends br.com.areiasbrittos.gui.superclass.InternalFrame {

    private DAOProduto dao = new DAOProduto();
    private TamanhoJTextField limita = new TamanhoJTextField();
    private final int[] tamanhosJTextField = {30};
    private DecimalFormat df = new DecimalFormat("###,##0.00");
    ApagaElementosDaInterface apaga = new ApagaElementosDaInterface();

    /**
     * Creates new form InternalFrameClientes
     */
    public InternalFrameProdutos() {
        initComponents();
        setVisible(true);
        inicializa();
    }

    @Override
    protected void inicializa() {

        this.textMensagem.setEditable(false);
        this.textMensagem.setText("Aqui são gerenciados todos os materiais da empresa.");
        this.limita.limitaTamanho(tamanhosJTextField, textNome);
        this.listarTodosElementosNaTabela();

        //Configura o comportamento dos botões da interface
        this.buttonCadastro.setEnabled(true);
        EnabledGUIElements.disabledJComponent(this.buttonAlterar, this.buttonAtivarDesativar);
        this.verificaAcessos();

        //Inicializa eventos da tabela
        SelectionListener listener = new SelectionListener(this.tabela);
        this.tabela.getSelectionModel().addListSelectionListener(listener);
        this.tabela.getColumnModel().getSelectionModel().addListSelectionListener(listener);
    }

    @Override
    protected void tabelaPreencheGUI() {

        try {
            int linha = tabela.getSelectedRow();
            Integer cod = (Integer) tabela.getValueAt(linha, 0);
            List<Produto> consulta = dao.consultar("idProduto=" + cod);

            if (consulta.size() > 0) {
                Produto p = consulta.get(0);
                textCodigo.setText(Integer.toString(p.getIdProduto()));
                textNome.setText(p.getNome());
                textEstoque.setText(Integer.toString(p.getEstoque()));
                textPrecoAVista.setText(p.getPrecoAVista().toString());
                textPrecoAPrazo.setText(p.getPrecoAPrazo().toString());

                //Configura o comportamento dos botões da interface
                this.buttonCadastro.setEnabled(false);
                EnabledGUIElements.enabledJComponent(this.buttonAlterar, this.buttonAtivarDesativar, this.buttonImprimir);

                if (!p.isAtivo()) {
                    buttonAlterar.setEnabled(false);
                    textNome.setEnabled(false);
                    textPrecoAVista.setEnabled(false);
                    textPrecoAPrazo.setEnabled(false);
                    textMensagem.setText("Produto desativado.");
                    
                } else {
                    buttonAlterar.setEnabled(true);
                    textNome.setEnabled(true);
                    textPrecoAVista.setEnabled(true);
                    textPrecoAPrazo.setEnabled(true);
                    textMensagem.setText("Produto ativado.");
                }
            }

            this.buttonCadastro.setEnabled(false);
            this.buttonAlterar.setEnabled(true);
            this.verificaAcessos();


        } catch (Exception ex) {
        }

    }

    /**
     * Verifica o acesso do usuário para utilizar este frame
     */
    @Override
    protected void verificaAcessos() {

        /*
         * Verifica a permissao de @user para este frame
         */
        if (!FramePrincipal.acessos.get(FramePrincipal.PERMISSAO_ALTERA_PRODUTO).isPermissao()) {
            new EnabledGUIElements().disabledJComponent(this.buttonCadastro, this.buttonAlterar, this.buttonAtivarDesativar);
        }
    }

    @Override
    protected void guiAtiva(boolean concluida) {

        /**
         * Instancia a classe de suporte para GUIs*
         */
        EnabledGUIElements gui = new EnabledGUIElements();

        if (concluida) {
            gui.disabledJComponent(buttonAlterar, buttonAtivarDesativar);
            textMensagem.setText("Este produto encontra-se ativo.");
        } else {
            gui.enabledJComponent(buttonAlterar, buttonAtivarDesativar);

            textMensagem.setText("Este produto não se encontra ativo.");
        }

        this.verificaAcessos();
    }

    @Override
    protected void limparGUI() {

        tabela.clearSelection();
        this.listarTodosElementosNaTabela();
        apaga.apagaJTextField(textCodigo, textNome, textPrecoAVista, textPrecoAPrazo, textEstoque);

        /*
         * Configura o comportamento dos botões da interface
         */
        this.buttonCadastro.setEnabled(true);
        EnabledGUIElements.disabledJComponent(this.buttonAlterar, this.buttonAtivarDesativar);
        this.verificaAcessos();

    }

    @Override
    protected void listarTodosElementosNaTabela() {

        List<Produto> list = dao.listar();
        listarPesquisaNaTabela(list);

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the
     * Form Editor.
     */
    private Produto criaProduto() throws NumberFormatException {
        int estoque = 0;

        if(textNome.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Preencha o nome do produto.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        return new Produto(textNome.getText(), estoque,
                new BigDecimal(textPrecoAVista.getText().replaceAll("\\.", "").replaceAll(",", "\\.")),
                new BigDecimal(textPrecoAPrazo.getText().replaceAll("\\.", "").replaceAll(",", "\\.")),
                true);
    }

    private void listarPesquisaNaTabela(List<Produto> list) {

        tabela.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(600);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(100);


        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);

        try {
            for (int i = 0; i < list.size(); i++) {
                Produto p = list.get(i);
                modelo.addRow(new Object[]{p.getIdProduto(), p.getNome(),
                            "R$ " + df.format(p.getPrecoAVista()),
                            "R$ " + df.format(p.getPrecoAPrazo())});
            }
        } catch (Exception er) {
            JOptionPane.showMessageDialog(this, "Erro na listagem!\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBotoes = new javax.swing.JPanel();
        buttonCadastro = new javax.swing.JButton();
        buttonAlterar = new javax.swing.JButton();
        buttonAtivarDesativar = new javax.swing.JButton();
        buttonLimpar = new javax.swing.JButton();
        buttonImprimir = new javax.swing.JButton();
        buttonCancelar = new javax.swing.JButton();
        panelTabela = new javax.swing.JPanel();
        scrollTabela = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        panelInformacoes = new javax.swing.JPanel();
        labelCodigo = new javax.swing.JLabel();
        labelNome = new javax.swing.JLabel();
        textNome = new javax.swing.JTextField();
        textCodigo = new javax.swing.JTextField();
        labelPrecoAVista = new javax.swing.JLabel();
        labelQuantidade = new javax.swing.JLabel();
        textEstoque = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("########"));
        labelKg = new javax.swing.JLabel();
        labelRS = new javax.swing.JLabel();
        labelRS1 = new javax.swing.JLabel();
        labelPrecoAPrazo = new javax.swing.JLabel();
        textPrecoAPrazo = new javax.swing.JTextField();
        textPrecoAVista = new javax.swing.JTextField();
        panelMensagem1 = new javax.swing.JPanel();
        textMensagem = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Produtos");
        setToolTipText("");

        panelBotoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opções", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

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

        buttonAtivarDesativar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Concluir.png"))); // NOI18N
        buttonAtivarDesativar.setText("Ativar/Desativar");
        buttonAtivarDesativar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAtivarDesativarActionPerformed(evt);
            }
        });

        buttonLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Novo.png"))); // NOI18N
        buttonLimpar.setText("Novo/Limpar");
        buttonLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLimparActionPerformed(evt);
            }
        });

        buttonImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Imprimir (2).png"))); // NOI18N
        buttonImprimir.setText("Imprimir");
        buttonImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonImprimirActionPerformed(evt);
            }
        });

        buttonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Cancelar.png"))); // NOI18N
        buttonCancelar.setText("Cancelar");
        buttonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotoesLayout = new javax.swing.GroupLayout(panelBotoes);
        panelBotoes.setLayout(panelBotoesLayout);
        panelBotoesLayout.setHorizontalGroup(
            panelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotoesLayout.createSequentialGroup()
                .addComponent(buttonCadastro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonLimpar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonAtivarDesativar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonImprimir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCancelar)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        panelBotoesLayout.setVerticalGroup(
            panelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonCadastro)
            .addGroup(panelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(buttonAtivarDesativar)
                .addComponent(buttonAlterar)
                .addComponent(buttonLimpar)
                .addComponent(buttonImprimir)
                .addComponent(buttonCancelar))
        );

        panelTabela.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Produtos existentes", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        tabela.setAutoCreateRowSorter(true);
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod", "Nome", "Preço a vista", "Preço a prazo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollTabela.setViewportView(tabela);

        javax.swing.GroupLayout panelTabelaLayout = new javax.swing.GroupLayout(panelTabela);
        panelTabela.setLayout(panelTabelaLayout);
        panelTabelaLayout.setHorizontalGroup(
            panelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollTabela)
        );
        panelTabelaLayout.setVerticalGroup(
            panelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollTabela, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );

        panelInformacoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações de Produtos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        labelCodigo.setText("Codigo:");

        labelNome.setText("Nome:");

        textNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNomeActionPerformed(evt);
            }
        });

        textCodigo.setEnabled(false);

        labelPrecoAVista.setText("Preço a vista:");

        labelQuantidade.setText("Quantidade em estoque:");

        ((JFormattedTextField)textEstoque).setFocusLostBehavior(JFormattedTextField.COMMIT);
        textEstoque.setEnabled(false);
        textEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textEstoqueActionPerformed(evt);
            }
        });

        labelKg.setText("Kg.");

        labelRS.setText("R$");

        labelRS1.setText("R$");

        labelPrecoAPrazo.setText("Preço a prazo:");

        textPrecoAPrazo.setHorizontalAlignment(textPrecoAPrazo.RIGHT);
        textPrecoAPrazo.setDocument(new NumeroDocument(10,2));

        textPrecoAVista.setHorizontalAlignment(textPrecoAVista.RIGHT);
        textPrecoAVista.setDocument(new NumeroDocument(10,2));

        javax.swing.GroupLayout panelInformacoesLayout = new javax.swing.GroupLayout(panelInformacoes);
        panelInformacoes.setLayout(panelInformacoesLayout);
        panelInformacoesLayout.setHorizontalGroup(
            panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacoesLayout.createSequentialGroup()
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addComponent(labelCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNome)
                            .addComponent(labelPrecoAVista))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInformacoesLayout.createSequentialGroup()
                                .addComponent(labelRS)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textPrecoAVista, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelPrecoAPrazo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelRS1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textPrecoAPrazo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(textNome)))
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addComponent(labelQuantidade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelKg)))
                .addContainerGap())
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
                    .addComponent(labelPrecoAVista)
                    .addComponent(labelRS)
                    .addComponent(labelPrecoAPrazo)
                    .addComponent(labelRS1)
                    .addComponent(textPrecoAPrazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textPrecoAVista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelQuantidade)
                    .addComponent(textEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelKg)))
        );

        panelMensagem1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        textMensagem.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        textMensagem.setBorder(null);

        javax.swing.GroupLayout panelMensagem1Layout = new javax.swing.GroupLayout(panelMensagem1);
        panelMensagem1.setLayout(panelMensagem1Layout);
        panelMensagem1Layout.setHorizontalGroup(
            panelMensagem1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textMensagem)
        );
        panelMensagem1Layout.setVerticalGroup(
            panelMensagem1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMensagem1Layout.createSequentialGroup()
                .addComponent(textMensagem)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelInformacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelMensagem1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMensagem1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCadastroActionPerformed

        try {
            int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja cadastrar " + textNome.getText()
                    + "?", "Cadastrar",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesOpcoes, botoesOpcoes[0]);

            if (opcao == JOptionPane.YES_OPTION) {

                if (textCodigo.getText().isEmpty()) {

                    if (!(textNome.getText().equals("") && textPrecoAVista.getText().equals("") && textPrecoAPrazo.getText().equals(""))) {

                        Produto p = this.criaProduto();
                        if (p != null) {
                            dao.inserir(p);
                            this.limparGUI();
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "Pelo menos estas condições devem ser satisfeitas para efetuar um novo cadastro:"
                                + "\n-Defina o nome do novo produto."
                                + "\n-Defina o preço a vista do novo produto."
                                + "\n-Defina o preço a prazo do novo produto.",
                                "Atenção!", JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Esta entidade já existe portanto não é possível cadastrá-lo(a)!", "Atenção!", JOptionPane.WARNING_MESSAGE);
                }
                listarTodosElementosNaTabela();
            }
        } catch (NumberFormatException er) {
            JOptionPane.showMessageDialog(this, "Valor numérico digitado de forma inválida, favor tentar novamente!\n" + er,
                    "Erro", JOptionPane.ERROR_MESSAGE);
            apaga.apagaJTextField(textEstoque, textPrecoAVista, textPrecoAPrazo);

        }
}//GEN-LAST:event_buttonCadastroActionPerformed

    private void buttonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAlterarActionPerformed
        int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja alterar este produto?", "Alterar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesOpcoes, botoesOpcoes[0]);

        try {

            if (opcao == JOptionPane.YES_OPTION) {

                if (!textCodigo.getText().equals("")) {

                    Produto p = this.criaProduto();
                    if (p != null) {
                        p.setIdProduto(Integer.parseInt(textCodigo.getText()));
                        dao.alterar(p);
                        this.limparGUI();
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Escolha um produto na tabela para alterar.",
                            "Atenção!", JOptionPane.WARNING_MESSAGE);


                }
                listarTodosElementosNaTabela();


            }
        } catch (NumberFormatException er) {
            JOptionPane.showMessageDialog(this, "Valor numérico digitado de forma inválida, favor tentar novamente!\n" + er,
                    "Erro", JOptionPane.ERROR_MESSAGE);
            apaga.apagaJTextField(textEstoque, textPrecoAVista, textPrecoAPrazo);

        }
}//GEN-LAST:event_buttonAlterarActionPerformed

    private void buttonAtivarDesativarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAtivarDesativarActionPerformed

        List<Produto> list = dao.consultar(" idProduto=" + textCodigo.getText());
        String mensagem = "";

        if (list.get(0).isAtivo()) {
            mensagem = "Tem certeza que deseja desativar este(s) produtos(s)?";
        } else {
            mensagem = "Tem certeza que deseja ativar este(s) produtos(s)?";
        }

        int opcao = JOptionPane.showOptionDialog(null, mensagem, "Desativar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesOpcoes, botoesOpcoes[0]);

        if (opcao == JOptionPane.YES_OPTION) {

            if (!textCodigo.getText().equals("")) {

                int[] selectedRows = tabela.getSelectedRows();

                if (selectedRows.length > 0) {
                    for (int i = 0; i < selectedRows.length; i++) {
                        selectedRows[i] = (Integer) tabela.getValueAt(selectedRows[i], 0);

                    }

                    dao.ativarDesativarVarios(selectedRows);

                } else {
                    dao.ativarDesativar(list.get(0));
                }

                //Configura o comportamento da interface após a ativação/desativação do item presente
                if (list.get(0).isAtivo()) {
                    textNome.setEnabled(false);
                    textPrecoAVista.setEnabled(false);
                    textPrecoAPrazo.setEnabled(false);
                    buttonAlterar.setEnabled(false);
                    textMensagem.setText("Produto desativado.");
                } else {
                    textNome.setEnabled(true);
                    textPrecoAVista.setEnabled(true);
                    textPrecoAPrazo.setEnabled(true);
                    buttonAlterar.setEnabled(true);
                    textMensagem.setText("Produto ativado.");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Escolha pelo menos um produto na tabela para excluir.",
                        "Atenção!", JOptionPane.WARNING_MESSAGE);

            }
            listarTodosElementosNaTabela();


        }
}//GEN-LAST:event_buttonAtivarDesativarActionPerformed

    private void buttonImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonImprimirActionPerformed

        InputStream inputStream = getClass().getResourceAsStream("/relatorios/Produtos.jasper");

        Map parametros = new HashMap();

        try {
            FileInputStream imagem = new FileInputStream(new java.io.File("logo.png"));
            parametros.put("imagem", imagem);

            ReportUtils.openReport("Tabela de Produtos", inputStream, parametros, ConnectionFactory_brittos_bd.getConnection());

        } catch (Exception er) {

            JOptionPane.showMessageDialog(this, "Erro ao criar o relatório.\n" + er, "Erro", JOptionPane.ERROR_MESSAGE);

            er.printStackTrace();
        }
}//GEN-LAST:event_buttonImprimirActionPerformed

    private void textNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNomeActionPerformed
        List<Produto> list = dao.consultar("nome like\'%" + textNome.getText() + "%\'");
        listarPesquisaNaTabela(
                list);
    }//GEN-LAST:event_textNomeActionPerformed

    private void textEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textEstoqueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textEstoqueActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAlterar;
    private javax.swing.JButton buttonAtivarDesativar;
    private javax.swing.JButton buttonCadastro;
    private javax.swing.JButton buttonCancelar;
    private javax.swing.JButton buttonImprimir;
    private javax.swing.JButton buttonLimpar;
    private javax.swing.JLabel labelCodigo;
    private javax.swing.JLabel labelKg;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelPrecoAPrazo;
    private javax.swing.JLabel labelPrecoAVista;
    private javax.swing.JLabel labelQuantidade;
    private javax.swing.JLabel labelRS;
    private javax.swing.JLabel labelRS1;
    private javax.swing.JPanel panelBotoes;
    private javax.swing.JPanel panelInformacoes;
    private javax.swing.JPanel panelMensagem1;
    private javax.swing.JPanel panelTabela;
    private javax.swing.JScrollPane scrollTabela;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField textCodigo;
    private javax.swing.JTextField textEstoque;
    private javax.swing.JTextField textMensagem;
    private javax.swing.JTextField textNome;
    public javax.swing.JTextField textPrecoAPrazo;
    public javax.swing.JTextField textPrecoAVista;
    // End of variables declaration//GEN-END:variables
}
