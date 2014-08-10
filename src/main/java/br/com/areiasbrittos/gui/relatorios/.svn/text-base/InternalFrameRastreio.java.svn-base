/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InternalFrameClientes.java
 *
 * Created on 17/01/2011, 18:27:01
 */
package gui.relatorios;

import gui.utils.MascarasJTextField;
import gui.utils.ApagaElementosDaInterface;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import persistencia.ConnectionFactory_bd_seguranca;
import persistencia.dao.seguranca.DAOUsuario;
import controle.utils.ReportUtils;

/**
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class InternalFrameRastreio extends gui.superclass.InternalFrame {

    private String[] botoesOpcoes = {"Sim", "Não"};
    ApagaElementosDaInterface apaga = new ApagaElementosDaInterface();

    /** Creates new form InternalFrameClientes */
    public InternalFrameRastreio() {
        initComponents();
        setVisible(true);
        inicializa();
    }

    /**Inicializa as opções personalizadas para esta GUI**/
    @Override
    public void inicializa() {
        textMensagem.setText("Defina as opções para gerar o relatório.");
        comboUsuarios.setModel(new DAOUsuario().inicializaComboBox("Todos", "Todos"));

        /**Inicializa os botões**/
        radioTodoPeriodo.setSelected(true);
    }

    /**
     * Limpa e reinicializa elementos desta GUI
     */
    @Override
    protected void limparGUI() {
        int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja limpar estes campos?", "Limpar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesOpcoes, botoesOpcoes[0]);

        if (opcao == JOptionPane.YES_OPTION) {
            comboUsuarios.setSelectedIndex(0);
            apaga.apagaJTextField(textPeriodoFinal, textPeriodoInicial);

            radioTodoPeriodoActionPerformed(null);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupPeriodo = new javax.swing.ButtonGroup();
        groupEntidade = new javax.swing.ButtonGroup();
        groupPagamento = new javax.swing.ButtonGroup();
        groupConclusao = new javax.swing.ButtonGroup();
        panelBotoes = new javax.swing.JPanel();
        buttonCancelar = new javax.swing.JButton();
        buttonLimpar = new javax.swing.JButton();
        buttonImprimir = new javax.swing.JButton();
        panelDefinicoes = new javax.swing.JPanel();
        labelEntidade = new javax.swing.JLabel();
        comboUsuarios = new javax.swing.JComboBox();
        panelMensagem = new javax.swing.JPanel();
        textMensagem = new javax.swing.JTextField();
        panelPeriodo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        radioTodoPeriodo = new javax.swing.JRadioButton();
        radioDurantePeriodo = new javax.swing.JRadioButton();
        textPeriodoInicial = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("##/##/####"));
        ((JFormattedTextField)textPeriodoInicial).setFocusLostBehavior(JFormattedTextField.COMMIT);
        jLabel2 = new javax.swing.JLabel();
        textPeriodoFinal = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("##/##/####"));
        ((JFormattedTextField)textPeriodoFinal).setFocusLostBehavior(JFormattedTextField.COMMIT);

        setClosable(true);
        setIconifiable(true);
        setTitle("Relatório de Rastreio de Usuários");
        setToolTipText("");

        panelBotoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opções", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        buttonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Cancelar.png"))); // NOI18N
        buttonCancelar.setText("Cancelar");
        buttonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelarActionPerformed(evt);
            }
        });

        buttonLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Novo.png"))); // NOI18N
        buttonLimpar.setText("Limpar");
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
                .addComponent(buttonImprimir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonLimpar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCancelar)
                .addContainerGap(121, Short.MAX_VALUE))
        );
        panelBotoesLayout.setVerticalGroup(
            panelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotoesLayout.createSequentialGroup()
                .addGroup(panelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonImprimir)
                    .addComponent(buttonLimpar)
                    .addComponent(buttonCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelDefinicoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Definições", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        labelEntidade.setText("Usuário:");

        javax.swing.GroupLayout panelDefinicoesLayout = new javax.swing.GroupLayout(panelDefinicoes);
        panelDefinicoes.setLayout(panelDefinicoesLayout);
        panelDefinicoesLayout.setHorizontalGroup(
            panelDefinicoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDefinicoesLayout.createSequentialGroup()
                .addGroup(panelDefinicoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelEntidade)
                    .addGroup(panelDefinicoesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(comboUsuarios, 0, 406, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelDefinicoesLayout.setVerticalGroup(
            panelDefinicoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDefinicoesLayout.createSequentialGroup()
                .addComponent(labelEntidade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelMensagem.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        textMensagem.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        textMensagem.setBorder(null);

        javax.swing.GroupLayout panelMensagemLayout = new javax.swing.GroupLayout(panelMensagem);
        panelMensagem.setLayout(panelMensagemLayout);
        panelMensagemLayout.setHorizontalGroup(
            panelMensagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
        );
        panelMensagemLayout.setVerticalGroup(
            panelMensagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMensagemLayout.createSequentialGroup()
                .addComponent(textMensagem)
                .addContainerGap())
        );

        panelPeriodo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Período", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel1.setText("De:");

        groupPeriodo.add(radioTodoPeriodo);
        radioTodoPeriodo.setText("Todas as ocorrências");
        radioTodoPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioTodoPeriodoActionPerformed(evt);
            }
        });

        groupPeriodo.add(radioDurantePeriodo);
        radioDurantePeriodo.setText("Entre:");
        radioDurantePeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioDurantePeriodoActionPerformed(evt);
            }
        });

        ((JFormattedTextField)textPeriodoInicial).setHorizontalAlignment(textPeriodoInicial.LEFT);
        textPeriodoInicial.setEnabled(false);

        jLabel2.setText("Até:");

        ((JFormattedTextField)textPeriodoFinal).setHorizontalAlignment(textPeriodoFinal.LEFT);
        textPeriodoFinal.setEnabled(false);

        javax.swing.GroupLayout panelPeriodoLayout = new javax.swing.GroupLayout(panelPeriodo);
        panelPeriodo.setLayout(panelPeriodoLayout);
        panelPeriodoLayout.setHorizontalGroup(
            panelPeriodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPeriodoLayout.createSequentialGroup()
                .addGroup(panelPeriodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioTodoPeriodo)
                    .addGroup(panelPeriodoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textPeriodoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textPeriodoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(radioDurantePeriodo))
                .addContainerGap(242, Short.MAX_VALUE))
        );
        panelPeriodoLayout.setVerticalGroup(
            panelPeriodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPeriodoLayout.createSequentialGroup()
                .addComponent(radioTodoPeriodo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioDurantePeriodo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelPeriodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textPeriodoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(textPeriodoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDefinicoes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelPeriodo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelBotoes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelMensagem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelDefinicoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Filtra as opções escolhida e executa o relatório
     * @param evt 
     */
    private void buttonImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonImprimirActionPerformed

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        boolean imprime = true;

        try {
            InputStream inputStream = getClass().getResourceAsStream("/DedoDuro.jasper");
            Map parametros = new HashMap();

            FileInputStream imagem = new FileInputStream("Logo.png");
            parametros.put("imagem", imagem);

            /*Define parâmetros de período*/
            if (radioTodoPeriodo.isSelected()) {
                parametros.put("periodo", "Todos");

            } else {
                /*Inicializa DateFormat*/
                DateFormat dataFormatada = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("pt", "BR"));
                Date periodoInicial = new Date(format.parse(textPeriodoInicial.getText()).getTime());
                Date periodoFinal = new Date(format.parse(textPeriodoFinal.getText()).getTime());

                if (!periodoFinal.before(periodoInicial)) {
                    parametros.put("periodoInicial", periodoInicial);
                    parametros.put("periodoFinal", periodoFinal);
                    parametros.put("periodo", dataFormatada.format(periodoInicial) + " a " + dataFormatada.format(periodoFinal));
                    
                    
                    

                } else {
                    JOptionPane.showMessageDialog(this, "A data inicial não pode ser maior do que a data final do período definido.",
                            "Atenção", JOptionPane.WARNING_MESSAGE);
                    imprime = false;
                }
            }

            /*Define o parâmetro @nomeUsuario*/
            parametros.put("nomeUsuario", comboUsuarios.getSelectedItem().toString());

            /**Executa o relatório**/
            if (imprime) {
                ReportUtils.openReport("Relatório Dedo Duro", inputStream, parametros, ConnectionFactory_bd_seguranca.getConnection());
            }

        } catch (ParseException er) {
            JOptionPane.showMessageDialog(this, "Preenchimento incorreto das datas de periodo inicial ou periodo final.\n"
                    + "Favor preencher corretamente ou selecionar \"todas as ocorrências\".",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            er.printStackTrace();

        } catch (Exception er) {
            JOptionPane.showMessageDialog(this, "Erro ao criar o relatório.\n" + er, "Erro", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
        }

}//GEN-LAST:event_buttonImprimirActionPerformed

    private void radioTodoPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioTodoPeriodoActionPerformed
        radioTodoPeriodo.setSelected(true);
        textPeriodoInicial.setEnabled(false);
        textPeriodoFinal.setEnabled(false);
        apaga.apagaJTextField(textPeriodoFinal, textPeriodoInicial);
    }//GEN-LAST:event_radioTodoPeriodoActionPerformed

    private void radioDurantePeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioDurantePeriodoActionPerformed
        textPeriodoInicial.setEnabled(true);
        textPeriodoFinal.setEnabled(true);
        apaga.apagaJTextField(textPeriodoFinal, textPeriodoInicial);
    }//GEN-LAST:event_radioDurantePeriodoActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancelar;
    private javax.swing.JButton buttonImprimir;
    private javax.swing.JButton buttonLimpar;
    private javax.swing.JComboBox comboUsuarios;
    private javax.swing.ButtonGroup groupConclusao;
    private javax.swing.ButtonGroup groupEntidade;
    private javax.swing.ButtonGroup groupPagamento;
    private javax.swing.ButtonGroup groupPeriodo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel labelEntidade;
    private javax.swing.JPanel panelBotoes;
    private javax.swing.JPanel panelDefinicoes;
    private javax.swing.JPanel panelMensagem;
    private javax.swing.JPanel panelPeriodo;
    private javax.swing.JRadioButton radioDurantePeriodo;
    private javax.swing.JRadioButton radioTodoPeriodo;
    private javax.swing.JTextField textMensagem;
    private javax.swing.JTextField textPeriodoFinal;
    private javax.swing.JTextField textPeriodoInicial;
    // End of variables declaration//GEN-END:variables
}
