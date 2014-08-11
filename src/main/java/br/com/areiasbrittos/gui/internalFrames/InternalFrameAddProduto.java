/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InternalFrameAddProduto.java
 *
 * Created on 26/01/2012, 13:54:12
 */
package br.com.areiasbrittos.gui.internalFrames;

import br.com.areiasbrittos.controle.gui.ControleIFAddProduto;
import br.com.areiasbrittos.gui.superclass.InternalFrame;
import br.com.areiasbrittos.gui.utils.MascarasJTextField;
import br.com.areiasbrittos.gui.utils.TextFieldMoedaReal;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class InternalFrameAddProduto extends InternalFrame {

    private ControleIFAddProduto controle = new ControleIFAddProduto(this);
    /*Modelo da tabela onde será adicionado o produto*/
    public DefaultTableModel modelo;

    /** Creates new form InternalFrameAddProduto */
    public InternalFrameAddProduto(DefaultTableModel modelo) {
        initComponents();
        this.setVisible(true);
        this.modelo = modelo;
        this.inicializa();
    }

    @Override
    public void inicializa() {
        controle.inicializa();

    }

    public void exibirProduto(JTable tabela) {
        controle.exibirProduto(tabela);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelProduto = new javax.swing.JPanel();
        labelTara = new javax.swing.JLabel();
        textTara = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("######"));
        labelPesoBruto = new javax.swing.JLabel();
        textPesoBruto = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("######"));
        textPesoLiquido = new javax.swing.JTextField();
        labelPesoLiquido = new javax.swing.JLabel();
        labelProduto = new javax.swing.JLabel();
        comboProduto = new javax.swing.JComboBox();
        textUnitario = new TextFieldMoedaReal();
        labelRS = new javax.swing.JLabel();
        labelValorUnitario = new javax.swing.JLabel();
        labelValorTotal = new javax.swing.JLabel();
        labelRS1 = new javax.swing.JLabel();
        textValor = new TextFieldMoedaReal();
        labelCodigo = new javax.swing.JLabel();
        textCodigo = new javax.swing.JTextField();
        comboPagamento = new javax.swing.JComboBox();
        labelPagamento = new javax.swing.JLabel();
        panelMensagem = new javax.swing.JPanel();
        textMensagem = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        buttonCancelar = new javax.swing.JButton();
        buttonOk = new javax.swing.JButton();
        buttonLimpar = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Adicionar Produto");

        panelProduto.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Adicionar Produto", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        labelTara.setText("Tara:");

        ((JFormattedTextField)textTara).setFocusLostBehavior(JFormattedTextField.COMMIT);
        ((JFormattedTextField)textTara).setHorizontalAlignment(textTara.LEFT);
        textTara.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textTaraFocusLost(evt);
            }
        });

        labelPesoBruto.setText("Peso Bruto:");

        ((JFormattedTextField)textPesoBruto).setFocusLostBehavior(JFormattedTextField.COMMIT);
        ((JFormattedTextField)textPesoBruto).setHorizontalAlignment(textPesoBruto.LEFT);
        textPesoBruto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textPesoBrutoFocusLost(evt);
            }
        });

        textPesoLiquido = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("######"));
        textPesoLiquido.setEnabled(false);

        labelPesoLiquido.setText("Peso Líquido:");

        labelProduto.setText("Produto:");

        comboProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboProdutoActionPerformed(evt);
            }
        });

        textUnitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textUnitarioActionPerformed(evt);
            }
        });
        textUnitario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textUnitarioFocusLost(evt);
            }
        });

        labelRS.setText("R$");

        labelValorUnitario.setText("Valor Unitário:");

        labelValorTotal.setText("Valor Total:");

        labelRS1.setText("R$");

        labelCodigo.setText("Codigo:");

        textCodigo.setEnabled(false);

        comboPagamento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A vista", "A prazo" }));
        comboPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPagamentoActionPerformed(evt);
            }
        });
        comboPagamento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                comboPagamentoFocusLost(evt);
            }
        });

        labelPagamento.setText("Preço:");

        javax.swing.GroupLayout panelProdutoLayout = new javax.swing.GroupLayout(panelProduto);
        panelProduto.setLayout(panelProdutoLayout);
        panelProdutoLayout.setHorizontalGroup(
            panelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProdutoLayout.createSequentialGroup()
                .addGroup(panelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelTara, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelProdutoLayout.createSequentialGroup()
                        .addComponent(labelValorUnitario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelRS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelValorTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelRS1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textValor, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelProdutoLayout.createSequentialGroup()
                        .addGroup(panelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelPagamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelProdutoLayout.createSequentialGroup()
                                .addComponent(textTara, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelPesoBruto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textPesoBruto, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelPesoLiquido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textPesoLiquido, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(comboProduto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelProdutoLayout.setVerticalGroup(
            panelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProdutoLayout.createSequentialGroup()
                .addGroup(panelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCodigo)
                    .addComponent(textCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelProduto)
                    .addComponent(comboProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTara)
                    .addComponent(textTara, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPesoBruto)
                    .addComponent(textPesoBruto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPesoLiquido)
                    .addComponent(textPesoLiquido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPagamento)
                    .addComponent(comboPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelValorUnitario)
                    .addComponent(textUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelRS)
                    .addComponent(labelValorTotal)
                    .addComponent(textValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelRS1)))
        );

        panelMensagem.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        textMensagem.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        textMensagem.setBorder(null);

        javax.swing.GroupLayout panelMensagemLayout = new javax.swing.GroupLayout(panelMensagem);
        panelMensagem.setLayout(panelMensagemLayout);
        panelMensagemLayout.setHorizontalGroup(
            panelMensagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textMensagem, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        panelMensagemLayout.setVerticalGroup(
            panelMensagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMensagemLayout.createSequentialGroup()
                .addComponent(textMensagem)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opções", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        buttonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Cancelar.png"))); // NOI18N
        buttonCancelar.setText("Cancelar");
        buttonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelarActionPerformed(evt);
            }
        });

        buttonOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Confirma.png"))); // NOI18N
        buttonOk.setText("Ok");
        buttonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOkActionPerformed(evt);
            }
        });

        buttonLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Novo.png"))); // NOI18N
        buttonLimpar.setText("Nova/Limpar");
        buttonLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(buttonOk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonLimpar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(buttonOk)
                .addComponent(buttonCancelar)
                .addComponent(buttonLimpar))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textTaraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textTaraFocusLost
        controle.calculaPesoLiquido();
    }//GEN-LAST:event_textTaraFocusLost

    private void textPesoBrutoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textPesoBrutoFocusLost
        controle.calculaPesoLiquido();
    }//GEN-LAST:event_textPesoBrutoFocusLost

    @Override
    public void buttonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_buttonCancelarActionPerformed

    private void buttonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOkActionPerformed
        controle.inserirAlterarLinha();
    }//GEN-LAST:event_buttonOkActionPerformed

    private void comboProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboProdutoActionPerformed
        controle.calculaPrecoUnitario();
    }//GEN-LAST:event_comboProdutoActionPerformed

    private void textUnitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textUnitarioActionPerformed
    }//GEN-LAST:event_textUnitarioActionPerformed

    @Override
    public void buttonLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLimparActionPerformed
        controle.limparGUI();
    }//GEN-LAST:event_buttonLimparActionPerformed

    private void textUnitarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textUnitarioFocusLost
        controle.calculaValorTotal();
    }//GEN-LAST:event_textUnitarioFocusLost

    private void comboPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPagamentoActionPerformed
        controle.calculaPrecoUnitario();
    }//GEN-LAST:event_comboPagamentoActionPerformed

    private void comboPagamentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comboPagamentoFocusLost
        controle.calculaPrecoUnitario();
    }//GEN-LAST:event_comboPagamentoFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton buttonCancelar;
    private javax.swing.JButton buttonLimpar;
    public javax.swing.JButton buttonOk;
    public javax.swing.JComboBox comboPagamento;
    public javax.swing.JComboBox comboProduto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelCodigo;
    private javax.swing.JLabel labelPagamento;
    private javax.swing.JLabel labelPesoBruto;
    private javax.swing.JLabel labelPesoLiquido;
    private javax.swing.JLabel labelProduto;
    private javax.swing.JLabel labelRS;
    private javax.swing.JLabel labelRS1;
    private javax.swing.JLabel labelTara;
    private javax.swing.JLabel labelValorTotal;
    private javax.swing.JLabel labelValorUnitario;
    private javax.swing.JPanel panelMensagem;
    private javax.swing.JPanel panelProduto;
    public javax.swing.JTextField textCodigo;
    public javax.swing.JTextField textMensagem;
    public javax.swing.JTextField textPesoBruto;
    public javax.swing.JTextField textPesoLiquido;
    public javax.swing.JTextField textTara;
    public javax.swing.JTextField textUnitario;
    public javax.swing.JTextField textValor;
    // End of variables declaration//GEN-END:variables

    public void modelo(Object[] object) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
