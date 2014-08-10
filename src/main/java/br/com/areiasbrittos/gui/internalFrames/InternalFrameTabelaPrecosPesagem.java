/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InternalFrameClientes.java
 *
 * Created on 17/01/2011, 18:27:01
 */
package gui.internalFrames;

import gui.utils.TamanhoJTextField;
import gui.utils.MascarasJTextField;
import gui.utils.ConsertaBugsGUI;
import gui.utils.ApagaElementosDaInterface;
import gui.utils.EnabledGUIElements;
import persistencia.dao.DAOTabelaPesagem;
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
import controle.objetos.Tabelapesagem;
import persistencia.ConnectionFactory_brittos_bd;
import controle.utils.ReportUtils;
import gui.FramePrincipal;
import gui.utils.*;

/**
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class InternalFrameTabelaPrecosPesagem extends gui.superclass.InternalFrame {

    private DAOTabelaPesagem dao = new DAOTabelaPesagem();
    private TamanhoJTextField limita = new TamanhoJTextField();
    private final int[] tamanhosJTextField = {30};
    private DecimalFormat df = new DecimalFormat("###,##0.00");
    ApagaElementosDaInterface apaga = new ApagaElementosDaInterface();

    /** Creates new form InternalFrameClientes */
    public InternalFrameTabelaPrecosPesagem() {
        initComponents();
        setVisible(true);
        inicializa();
    }

    @Override
    protected void inicializa() {
        limita.limitaTamanho(tamanhosJTextField, textNome);
        listarTodosElementosNaTabela();

        /*Configura o comportamento dos botões da interface*/
        this.buttonCadastro.setEnabled(true);
        EnabledGUIElements.disabledJComponent(this.buttonAlterar, this.buttonExcluir);

        this.guiAtiva(true);

        /*Inicializa eventos da tabela*/
        SelectionListener listener = new SelectionListener(this.tabela);
        this.tabela.getSelectionModel().addListSelectionListener(listener);
        this.tabela.getColumnModel().getSelectionModel().addListSelectionListener(listener);
    }

    private Tabelapesagem criaItem() throws NumberFormatException {

        int tara = ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(textTara);

        return new Tabelapesagem(tara, textNome.getText(), 
                new BigDecimal(textPrecoAVista.getText().replaceAll("\\.", "").replaceAll(",", "\\.")), new BigDecimal(textPrecoAPrazo.getText().replaceAll("\\.", "").replaceAll(",", "\\.")));

    }

    @Override
    protected void listarTodosElementosNaTabela() {

        List<Tabelapesagem> list = dao.listar();
        listarPesquisaNaTabela(list);

    }

    @Override
    protected void tabelaPreencheGUI() {

        try {
            int linha = tabela.getSelectedRow();
            Integer cod;

            try {
                cod = (Integer) tabela.getValueAt(linha, 0);
            } catch (IndexOutOfBoundsException er) {
                cod = 0;
            }

            List<Tabelapesagem> consulta = dao.consultar("idtabelaPesagem=" + cod);

            if (consulta.size() > 0) {
                Tabelapesagem tp = consulta.get(0);
                textCodigo.setText(Integer.toString(tp.getIdTabelaPesagem()));
                textNome.setText(tp.getNome());
                textTara.setText(Integer.toString(tp.getTara()));
                textPrecoAVista.setText(tp.getPrecoAVista().toString());
                textPrecoAPrazo.setText(tp.getPrecoAPrazo().toString());
                buttonCadastro.setEnabled(false);

                //Configura o comportamento dos botões da interface
                this.buttonCadastro.setEnabled(false);
                EnabledGUIElements.enabledJComponent(this.buttonAlterar, this.buttonExcluir);
            }

            this.guiAtiva(true);

        } catch (ArrayIndexOutOfBoundsException ex) {
        }

    }

    @Override
    protected void limparGUI() {

        tabela.clearSelection();
        this.listarTodosElementosNaTabela();
        apaga.apagaJTextField(textCodigo, textNome, textPrecoAVista, textPrecoAPrazo, textTara);

        /*Configura o comportamento dos botões da interface*/
        this.buttonCadastro.setEnabled(true);
        EnabledGUIElements.disabledJComponent(this.buttonAlterar, this.buttonExcluir);
        this.verificaAcessos();

    }

    /**
     * Verifica o acesso do usuário para utilizar este frame
     */
    @Override
    protected void verificaAcessos() {

        /*Verifica a permissao de @user para este frame
         */
        if (!FramePrincipal.acessos.get(FramePrincipal.PERMISSAO_ALTERA_TABELA).isPermissao()) {
            EnabledGUIElements.disabledJComponent(this.buttonCadastro, this.buttonAlterar, this.buttonExcluir);
        }
    }

    @Override
    protected void guiAtiva(boolean concluida) {

        this.verificaAcessos();
    }

    private void listarPesquisaNaTabela(List<Tabelapesagem> list) {

        tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(300);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(100);

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);

        try {
            for (int i = 0; i < list.size(); i++) {
                Tabelapesagem tp = list.get(i);
                modelo.addRow(new Object[]{tp.getIdTabelaPesagem(), tp.getNome(),
                            tp.getTara(), "R$ " + df.format(tp.getPrecoAVista()), "R$ " + df.format(tp.getPrecoAPrazo())});
            }
        } catch (Exception er) {
            JOptionPane.showMessageDialog(this, "Erro na listagem!\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBotoes = new javax.swing.JPanel();
        buttonCancelar = new javax.swing.JButton();
        buttonCadastro = new javax.swing.JButton();
        buttonAlterar = new javax.swing.JButton();
        buttonExcluir = new javax.swing.JButton();
        buttonLimpar = new javax.swing.JButton();
        buttonImprimir = new javax.swing.JButton();
        panelTabela = new javax.swing.JPanel();
        scrollTabela = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        panelInformacoes = new javax.swing.JPanel();
        labelCodigo = new javax.swing.JLabel();
        labelNome = new javax.swing.JLabel();
        textNome = new javax.swing.JTextField();
        textCodigo = new javax.swing.JTextField();
        labelPrecoAVista = new javax.swing.JLabel();
        labelTara = new javax.swing.JLabel();
        textTara = new javax.swing.JTextField();
        labelKg = new javax.swing.JLabel();
        labelRS = new javax.swing.JLabel();
        labelPrecoAPrazo = new javax.swing.JLabel();
        labelRS1 = new javax.swing.JLabel();
        textPrecoAPrazo = new javax.swing.JTextField();
        textPrecoAVista = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Tabela de Preços de Pesagem");
        setToolTipText("");

        panelBotoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opções", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        buttonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Cancelar.png"))); // NOI18N
        buttonCancelar.setText("Cancelar");
        buttonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelarActionPerformed(evt);
            }
        });

        buttonCadastro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Confirma.png"))); // NOI18N
        buttonCadastro.setText("Cadastrar");
        buttonCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCadastroActionPerformed(evt);
            }
        });

        buttonAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Alterar (2).png"))); // NOI18N
        buttonAlterar.setText("Alterar");
        buttonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAlterarActionPerformed(evt);
            }
        });

        buttonExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Excluir.png"))); // NOI18N
        buttonExcluir.setText("Excluir");
        buttonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExcluirActionPerformed(evt);
            }
        });

        buttonLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Novo.png"))); // NOI18N
        buttonLimpar.setText("Novo/Limpar");
        buttonLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLimparActionPerformed(evt);
            }
        });

        buttonImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Imprimir (2).png"))); // NOI18N
        buttonImprimir.setText("Imprimir");
        buttonImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonImprimirActionPerformed(evt);
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
                .addComponent(buttonExcluir)
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
                .addComponent(buttonAlterar)
                .addComponent(buttonLimpar)
                .addComponent(buttonExcluir)
                .addComponent(buttonImprimir)
                .addComponent(buttonCancelar))
        );

        panelTabela.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tabela de Pesos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        tabela.setAutoCreateRowSorter(true);
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod", "Nome", "Tara", "Preço a vista", "Preço a prazo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
            .addGroup(panelTabelaLayout.createSequentialGroup()
                .addComponent(scrollTabela, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelInformacoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        labelCodigo.setText("Codigo:");

        labelNome.setText("Nome:");

        textNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNomeActionPerformed(evt);
            }
        });

        textCodigo.setEnabled(false);
        textCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textCodigoActionPerformed(evt);
            }
        });
        textCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textCodigoFocusLost(evt);
            }
        });

        labelPrecoAVista.setText("Preço a vista:");

        labelTara.setText("A partir da Tara:");

        textTara = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("######"));
        ((JFormattedTextField)textTara).setFocusLostBehavior(JFormattedTextField.COMMIT);
        ((JFormattedTextField)textTara).setHorizontalAlignment(textTara.LEFT);

        labelKg.setText("Kg.");

        labelRS.setText("R$");

        labelPrecoAPrazo.setText("Preço a prazo:");

        labelRS1.setText("R$");

        textPrecoAPrazo.setHorizontalAlignment(textPrecoAPrazo.RIGHT);
        textPrecoAPrazo.setDocument(new NumeroDocument(10,2));

        textPrecoAVista.setHorizontalAlignment(textPrecoAVista.RIGHT);
        textPrecoAVista.setDocument(new NumeroDocument(7,2));

        javax.swing.GroupLayout panelInformacoesLayout = new javax.swing.GroupLayout(panelInformacoes);
        panelInformacoes.setLayout(panelInformacoesLayout);
        panelInformacoesLayout.setHorizontalGroup(
            panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacoesLayout.createSequentialGroup()
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addComponent(labelNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textNome))
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInformacoesLayout.createSequentialGroup()
                                .addComponent(labelCodigo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelInformacoesLayout.createSequentialGroup()
                                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelTara)
                                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                                        .addComponent(labelPrecoAVista)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelRS)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                                        .addComponent(textPrecoAVista, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(labelPrecoAPrazo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelRS1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textPrecoAPrazo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                                        .addComponent(textTara, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelKg)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                    .addComponent(labelTara)
                    .addComponent(textTara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelKg))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrecoAVista)
                    .addComponent(labelRS)
                    .addComponent(labelPrecoAPrazo)
                    .addComponent(labelRS1)
                    .addComponent(textPrecoAPrazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textPrecoAVista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelInformacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panelTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCadastroActionPerformed

        try {
            int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja cadastrar " + textNome.getText()
                    + "?", "Cadastrar",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesOpcoes, botoesOpcoes[0]);

            int tara = ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(textTara);

            if (opcao == JOptionPane.YES_OPTION) {

                List<Tabelapesagem> list = dao.consultar("tara=" + tara);

                if (textCodigo.getText().isEmpty() && list.isEmpty()) {

                    if (!(textNome.getText().isEmpty() || textPrecoAVista.getText().isEmpty() || textPrecoAPrazo.getText().isEmpty())) {

                        if (tara >= 0) {

                            dao.inserir(criaItem());
                            this.limparGUI();

                        } else {
                            JOptionPane.showMessageDialog(this, "A tara não pode ter um valor negativo ou vazio.", "Atenção",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "Pelo menos estas condições devem ser satisfeitas para efetuar um novo cadastro:"
                                + "\n-Defina um nome para o item da Tabela de Pesagem."
                                + "\n-Defina a tara para o item."
                                + "\n-Defina o preço a vista para o item."
                                + "\n-Defina o preço a prazo para o item.",
                                "Atenção!", JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Este item já existe, ou existe um item com o mesmo valor para esta tara,\n"
                            + " portanto não é possível cadastrá-lo!", "Atenção!", JOptionPane.WARNING_MESSAGE);
                }
                listarTodosElementosNaTabela();
            }
        } catch (NumberFormatException er) {
            JOptionPane.showMessageDialog(this, "Valor numérico digitado de forma inválida, favor tentar novamente!\n" + er,
                    "Erro", JOptionPane.ERROR_MESSAGE);
            apaga.apagaJTextField(textTara, textPrecoAVista);

        }
}//GEN-LAST:event_buttonCadastroActionPerformed

    private void buttonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAlterarActionPerformed
        int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja alterar este item?", "Alterar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesOpcoes, botoesOpcoes[0]);

        try {
            if (opcao == JOptionPane.YES_OPTION) {
                textTara.setText(textTara.getText().replaceAll(" ", ""));
                if (!(textCodigo.getText().isEmpty())) {

                    textTara.setText(textTara.getText().replaceAll(" ", ""));

                    if (!(textTara.getText().isEmpty() || textNome.getText().isEmpty())) {

                        Tabelapesagem tp = criaItem();
                        tp.setIdTabelaPesagem(Integer.parseInt(textCodigo.getText()));
                        dao.alterar(tp);

                    } else {
                        JOptionPane.showMessageDialog(this, "A tara e o nome não podem ficar vazios.",
                                "Atenção!", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Escolha um item na tabela para alterar.",
                            "Atenção!", JOptionPane.WARNING_MESSAGE);

                }
                listarTodosElementosNaTabela();
                this.limparGUI();
            }
        } catch (NumberFormatException er) {
            JOptionPane.showMessageDialog(this, "Valor numérico digitado de forma inválida, favor tentar novamente!\n" + er,
                    "Erro", JOptionPane.ERROR_MESSAGE);
            apaga.apagaJTextField(textTara, textPrecoAVista);

        }
}//GEN-LAST:event_buttonAlterarActionPerformed

    private void buttonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExcluirActionPerformed

        int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir este(s) item(s)?", "Excluir",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesOpcoes, botoesOpcoes[0]);

        if (opcao == JOptionPane.YES_OPTION) {

            if (!textCodigo.getText().equals("")) {

                int[] selectedRows = tabela.getSelectedRows();

                for (int i = 0; i < selectedRows.length; i++) {
                    selectedRows[i] = (Integer) tabela.getValueAt(selectedRows[i], 0);

                }

                dao.excluirVarios(selectedRows);

            } else {
                JOptionPane.showMessageDialog(this, "Escolha pelo menos um item na tabela para excluir.",
                        "Atenção!", JOptionPane.WARNING_MESSAGE);

            }
            limparGUI();
            listarTodosElementosNaTabela();

        }
}//GEN-LAST:event_buttonExcluirActionPerformed

    private void buttonImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonImprimirActionPerformed

        InputStream inputStream = getClass().getResourceAsStream("/TabelaDePesagem.jasper");

        try {

            Map parametros = new HashMap();
            FileInputStream imagem = new FileInputStream("Logo.png");
            parametros.put("imagem", imagem);



            ReportUtils.openReport("Tabela de Preço de Pesagens", inputStream, parametros, ConnectionFactory_brittos_bd.getConnection());

        } catch (Exception er) {

            JOptionPane.showMessageDialog(this, "Erro ao criar o relatório.\n" + er, "Erro", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();

        }
}//GEN-LAST:event_buttonImprimirActionPerformed

    private void textNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNomeActionPerformed
        List<Tabelapesagem> list = dao.consultar("nome like\'%" + textNome.getText() + "%\'");
        listarPesquisaNaTabela(
                list);
    }//GEN-LAST:event_textNomeActionPerformed

    private void textCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textCodigoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_textCodigoFocusLost

    private void textCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textCodigoActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAlterar;
    private javax.swing.JButton buttonCadastro;
    private javax.swing.JButton buttonCancelar;
    private javax.swing.JButton buttonExcluir;
    private javax.swing.JButton buttonImprimir;
    private javax.swing.JButton buttonLimpar;
    private javax.swing.JLabel labelCodigo;
    private javax.swing.JLabel labelKg;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelPrecoAPrazo;
    private javax.swing.JLabel labelPrecoAVista;
    private javax.swing.JLabel labelRS;
    private javax.swing.JLabel labelRS1;
    private javax.swing.JLabel labelTara;
    private javax.swing.JPanel panelBotoes;
    private javax.swing.JPanel panelInformacoes;
    private javax.swing.JPanel panelTabela;
    private javax.swing.JScrollPane scrollTabela;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField textCodigo;
    private javax.swing.JTextField textNome;
    public javax.swing.JTextField textPrecoAPrazo;
    public javax.swing.JTextField textPrecoAVista;
    private javax.swing.JTextField textTara;
    // End of variables declaration//GEN-END:variables
}
