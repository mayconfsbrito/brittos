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

import controle.gui.ControleFPreferencias;
import controle.utils.Dates;
import controle.utils.ReportUtils;
import gui.utils.ApagaElementosDaInterface;
import gui.utils.MascarasJTextField;
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
import persistencia.ConnectionFactory_brittos_bd;
import persistencia.dao.DAOEntidade;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class InternalFramePorEntidade extends gui.superclass.InternalFrame {

    private String[] botoesOpcoes = {"Sim", "Não"};
    ApagaElementosDaInterface apaga = new ApagaElementosDaInterface();

    /**
     * Creates new form InternalFrameClientes
     */
    public InternalFramePorEntidade() {
        initComponents();
        setVisible(true);
        inicializa();
    }

    /**
     * Inicializa as opções personalizadas para esta GUI*
     */
    @Override
    public void inicializa() {
        textMensagem.setText("Defina as opções para gerar o relatório.");
        comboEntidade.setModel(new DAOEntidade().inicializaComboBoxEntidades("Todos", "Todos"));

        /**
         * Inicializa os botões*
         */
        radioTodoPeriodo.setSelected(true);
        radioTodos.setSelected(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupPeriodoCricacao = new javax.swing.ButtonGroup();
        groupEntidade = new javax.swing.ButtonGroup();
        groupPagamento = new javax.swing.ButtonGroup();
        groupConclusao = new javax.swing.ButtonGroup();
        groupPeriodoAlteracao = new javax.swing.ButtonGroup();
        groupQuitada = new javax.swing.ButtonGroup();
        panelBotoes = new javax.swing.JPanel();
        buttonCancelar = new javax.swing.JButton();
        buttonLimpar = new javax.swing.JButton();
        buttonImprimir = new javax.swing.JButton();
        panelDefinicoes = new javax.swing.JPanel();
        labelTransacao = new javax.swing.JLabel();
        labelTipoEntidade = new javax.swing.JLabel();
        radioTodos = new javax.swing.JRadioButton();
        radioCliente = new javax.swing.JRadioButton();
        radioFornecedor = new javax.swing.JRadioButton();
        labelEntidade = new javax.swing.JLabel();
        comboEntidade = new javax.swing.JComboBox();
        checkPesagem = new javax.swing.JCheckBox();
        radioClienteFornecedor = new javax.swing.JRadioButton();
        checkCompra = new javax.swing.JCheckBox();
        checkVenda = new javax.swing.JCheckBox();
        panelMensagem = new javax.swing.JPanel();
        textMensagem = new javax.swing.JTextField();
        panelFiltros = new javax.swing.JPanel();
        panelPeriodoCriação = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        radioTodoPeriodo = new javax.swing.JRadioButton();
        radioDurantePeriodo = new javax.swing.JRadioButton();
        textPeriodoInicial = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("##/##/####"));
        ((JFormattedTextField)textPeriodoInicial).setFocusLostBehavior(JFormattedTextField.COMMIT);
        jLabel2 = new javax.swing.JLabel();
        textPeriodoFinal = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("##/##/####"));
        ((JFormattedTextField)textPeriodoFinal).setFocusLostBehavior(JFormattedTextField.COMMIT);
        panelConclusao = new javax.swing.JPanel();
        radioConclusaoTodas = new javax.swing.JRadioButton();
        radioConclusaoNaoConcluidas = new javax.swing.JRadioButton();
        radioConclusaoConcluidas = new javax.swing.JRadioButton();
        panelPeriodoAlteração = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        radioTodoPeriodoAlteracao = new javax.swing.JRadioButton();
        textPeriodoInicialAlteracao = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("##/##/####"));
        ((JFormattedTextField)textPeriodoInicialAlteracao).setFocusLostBehavior(JFormattedTextField.COMMIT);
        jLabel6 = new javax.swing.JLabel();
        textPeriodoFinalAlteracao = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("##/##/####"));
        ((JFormattedTextField)textPeriodoFinalAlteracao).setFocusLostBehavior(JFormattedTextField.COMMIT);
        radioDurantePeriodoAlteracao = new javax.swing.JRadioButton();
        panelQuitada = new javax.swing.JPanel();
        radioQuitadaTodas = new javax.swing.JRadioButton();
        radioQuitadaNao = new javax.swing.JRadioButton();
        radioQuitada = new javax.swing.JRadioButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Relatório Por Entidade");
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        panelDefinicoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Definições de Entidades", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        labelTransacao.setText("Tipo de transação:");

        labelTipoEntidade.setText("Tipos de entidade:");

        groupEntidade.add(radioTodos);
        radioTodos.setSelected(true);
        radioTodos.setText("Todos");
        radioTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioTodosActionPerformed(evt);
            }
        });

        groupEntidade.add(radioCliente);
        radioCliente.setText("Apenas Cliente");
        radioCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioClienteActionPerformed(evt);
            }
        });

        groupEntidade.add(radioFornecedor);
        radioFornecedor.setText("Apenas Fornecedor");
        radioFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFornecedorActionPerformed(evt);
            }
        });

        labelEntidade.setText("Entidade:");

        checkPesagem.setText("Pesagem");

        groupEntidade.add(radioClienteFornecedor);
        radioClienteFornecedor.setText("Cliente e Fornecedor");
        radioClienteFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioClienteFornecedorActionPerformed(evt);
            }
        });

        checkCompra.setText("Compra");
        checkCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCompraActionPerformed(evt);
            }
        });

        checkVenda.setText("Venda");

        javax.swing.GroupLayout panelDefinicoesLayout = new javax.swing.GroupLayout(panelDefinicoes);
        panelDefinicoes.setLayout(panelDefinicoesLayout);
        panelDefinicoesLayout.setHorizontalGroup(
            panelDefinicoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDefinicoesLayout.createSequentialGroup()
                .addGroup(panelDefinicoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelTransacao)
                    .addGroup(panelDefinicoesLayout.createSequentialGroup()
                        .addComponent(checkPesagem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkCompra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkVenda))
                    .addComponent(labelTipoEntidade)
                    .addGroup(panelDefinicoesLayout.createSequentialGroup()
                        .addComponent(radioTodos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioFornecedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioClienteFornecedor))
                    .addComponent(labelEntidade)
                    .addComponent(comboEntidade, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDefinicoesLayout.setVerticalGroup(
            panelDefinicoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDefinicoesLayout.createSequentialGroup()
                .addComponent(labelTransacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDefinicoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkPesagem)
                    .addComponent(checkCompra)
                    .addComponent(checkVenda))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelTipoEntidade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDefinicoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioTodos)
                    .addComponent(radioCliente)
                    .addComponent(radioFornecedor)
                    .addComponent(radioClienteFornecedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelEntidade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboEntidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelMensagem.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        textMensagem.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        textMensagem.setBorder(null);

        javax.swing.GroupLayout panelMensagemLayout = new javax.swing.GroupLayout(panelMensagem);
        panelMensagem.setLayout(panelMensagemLayout);
        panelMensagemLayout.setHorizontalGroup(
            panelMensagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textMensagem)
        );
        panelMensagemLayout.setVerticalGroup(
            panelMensagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMensagemLayout.createSequentialGroup()
                .addComponent(textMensagem)
                .addContainerGap())
        );

        panelFiltros.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panelFiltros.setLayout(new java.awt.GridLayout(3, 0));

        panelPeriodoCriação.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Período de Criação", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel1.setText("De:");

        groupPeriodoCricacao.add(radioTodoPeriodo);
        radioTodoPeriodo.setSelected(true);
        radioTodoPeriodo.setText("Todas as ocorrências");
        radioTodoPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioTodoPeriodoActionPerformed(evt);
            }
        });

        groupPeriodoCricacao.add(radioDurantePeriodo);
        radioDurantePeriodo.setText("Durante:");
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

        javax.swing.GroupLayout panelPeriodoCriaçãoLayout = new javax.swing.GroupLayout(panelPeriodoCriação);
        panelPeriodoCriação.setLayout(panelPeriodoCriaçãoLayout);
        panelPeriodoCriaçãoLayout.setHorizontalGroup(
            panelPeriodoCriaçãoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPeriodoCriaçãoLayout.createSequentialGroup()
                .addGroup(panelPeriodoCriaçãoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioTodoPeriodo)
                    .addComponent(radioDurantePeriodo)
                    .addGroup(panelPeriodoCriaçãoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textPeriodoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textPeriodoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        panelPeriodoCriaçãoLayout.setVerticalGroup(
            panelPeriodoCriaçãoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPeriodoCriaçãoLayout.createSequentialGroup()
                .addComponent(radioTodoPeriodo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioDurantePeriodo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPeriodoCriaçãoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textPeriodoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(textPeriodoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelFiltros.add(panelPeriodoCriação);

        panelConclusao.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Conclusão", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        groupConclusao.add(radioConclusaoTodas);
        radioConclusaoTodas.setSelected(true);
        radioConclusaoTodas.setText("Todas");

        groupConclusao.add(radioConclusaoNaoConcluidas);
        radioConclusaoNaoConcluidas.setText("Apenas Não Concluídas");

        groupConclusao.add(radioConclusaoConcluidas);
        radioConclusaoConcluidas.setText("Apenas Concluidas");

        javax.swing.GroupLayout panelConclusaoLayout = new javax.swing.GroupLayout(panelConclusao);
        panelConclusao.setLayout(panelConclusaoLayout);
        panelConclusaoLayout.setHorizontalGroup(
            panelConclusaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConclusaoLayout.createSequentialGroup()
                .addGroup(panelConclusaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioConclusaoTodas)
                    .addComponent(radioConclusaoConcluidas)
                    .addComponent(radioConclusaoNaoConcluidas))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        panelConclusaoLayout.setVerticalGroup(
            panelConclusaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConclusaoLayout.createSequentialGroup()
                .addComponent(radioConclusaoTodas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioConclusaoConcluidas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioConclusaoNaoConcluidas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelFiltros.add(panelConclusao);

        panelPeriodoAlteração.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Período da Última Alteração", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel5.setText("De:");

        groupPeriodoAlteracao.add(radioTodoPeriodoAlteracao);
        radioTodoPeriodoAlteracao.setSelected(true);
        radioTodoPeriodoAlteracao.setText("Todas as ocorrências");
        radioTodoPeriodoAlteracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioTodoPeriodoAlteracaoActionPerformed(evt);
            }
        });

        ((JFormattedTextField)textPeriodoInicialAlteracao).setHorizontalAlignment(textPeriodoInicialAlteracao.LEFT);
        textPeriodoInicialAlteracao.setEnabled(false);

        jLabel6.setText("Até:");

        ((JFormattedTextField)textPeriodoFinalAlteracao).setHorizontalAlignment(textPeriodoFinalAlteracao.LEFT);
        textPeriodoFinalAlteracao.setEnabled(false);

        groupPeriodoAlteracao.add(radioDurantePeriodoAlteracao);
        radioDurantePeriodoAlteracao.setText("Durante:");
        radioDurantePeriodoAlteracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioDurantePeriodoAlteracaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPeriodoAlteraçãoLayout = new javax.swing.GroupLayout(panelPeriodoAlteração);
        panelPeriodoAlteração.setLayout(panelPeriodoAlteraçãoLayout);
        panelPeriodoAlteraçãoLayout.setHorizontalGroup(
            panelPeriodoAlteraçãoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPeriodoAlteraçãoLayout.createSequentialGroup()
                .addGroup(panelPeriodoAlteraçãoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioTodoPeriodoAlteracao)
                    .addGroup(panelPeriodoAlteraçãoLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textPeriodoInicialAlteracao, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textPeriodoFinalAlteracao, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(radioDurantePeriodoAlteracao))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        panelPeriodoAlteraçãoLayout.setVerticalGroup(
            panelPeriodoAlteraçãoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPeriodoAlteraçãoLayout.createSequentialGroup()
                .addComponent(radioTodoPeriodoAlteracao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioDurantePeriodoAlteracao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPeriodoAlteraçãoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(textPeriodoInicialAlteracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(textPeriodoFinalAlteracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelFiltros.add(panelPeriodoAlteração);

        panelQuitada.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Estado de Pagamento", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        groupQuitada.add(radioQuitadaTodas);
        radioQuitadaTodas.setSelected(true);
        radioQuitadaTodas.setText("Todas");

        groupQuitada.add(radioQuitadaNao);
        radioQuitadaNao.setText("Apenas não quitadas");

        groupQuitada.add(radioQuitada);
        radioQuitada.setText("Apenas quitadas");

        javax.swing.GroupLayout panelQuitadaLayout = new javax.swing.GroupLayout(panelQuitada);
        panelQuitada.setLayout(panelQuitadaLayout);
        panelQuitadaLayout.setHorizontalGroup(
            panelQuitadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelQuitadaLayout.createSequentialGroup()
                .addGroup(panelQuitadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioQuitadaTodas)
                    .addComponent(radioQuitada)
                    .addComponent(radioQuitadaNao))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        panelQuitadaLayout.setVerticalGroup(
            panelQuitadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelQuitadaLayout.createSequentialGroup()
                .addComponent(radioQuitadaTodas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioQuitada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioQuitadaNao)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelFiltros.add(panelQuitada);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelDefinicoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelFiltros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelDefinicoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void buttonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelarActionPerformed
        int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja cancelar a operação?", "Cancelar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesOpcoes, botoesOpcoes[0]);
        if (opcao == JOptionPane.YES_OPTION) {
            this.dispose();
        }
}//GEN-LAST:event_buttonCancelarActionPerformed

    /**
     * Limpa as opções da gui
     *
     * @param evt
     */
    @Override
    protected void buttonLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLimparActionPerformed
        int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja limpar estes campos?", "Limpar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesOpcoes, botoesOpcoes[0]);

        if (opcao == JOptionPane.YES_OPTION) {
            comboEntidade.setSelectedIndex(0);
            apaga.apagaJTextField(textPeriodoFinal, textPeriodoInicial);
            checkPesagem.setSelected(true);
            radioTodos.setSelected(true);
            radioConclusaoTodas.setSelected(true);
            radioQuitadaTodas.setSelected(true);
            radioTodoPeriodoActionPerformed(evt);
        }
    }//GEN-LAST:event_buttonLimparActionPerformed

    /**
     * Filtra as opções escolhida e executa o relatório
     *
     * @param evt
     */
    private void buttonImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonImprimirActionPerformed

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        boolean imprime = true;

        //Verifica se pelo menos uma opção de tipo de transação foi selecionada
        if (this.checkPesagem.isSelected() || this.checkCompra.isSelected() || this.checkVenda.isSelected()) {

            try {
                InputStream inputStream = getClass().getResourceAsStream("/PorEntidade.jasper");
                Map parametros = new HashMap();

                FileInputStream imagem = new FileInputStream("Logo.png");
                parametros.put("imagem", imagem);

                parametros = ControleFPreferencias.preencheParametrosRelatorio(parametros);

                //Define parâmetros de período
                if (radioTodoPeriodo.isSelected()) {
                    parametros.put("periodo", "Todos");

                } else {

                    //Inicializa DateFormat
                    DateFormat dataFormatada = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("pt", "BR"));
                    Date periodoInicial = new Date(format.parse(textPeriodoInicial.getText()).getTime());
                    Date periodoFinal = new Date(format.parse(textPeriodoFinal.getText()).getTime());
                    periodoInicial = Dates.verificaDigitosAno(periodoInicial);
                    periodoFinal = Dates.verificaDigitosAno(periodoFinal);

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

                //Define parâmetro tipo de entidade
                if (radioClienteFornecedor.isSelected()) {
                    parametros.put("tipo", "Cliente e Fornecedor");

                } else if (radioCliente.isSelected()) {
                    parametros.put("tipo", "Cliente");

                } else if (radioFornecedor.isSelected()) {
                    parametros.put("tipo", "Fornecedor");

                } else if (radioTodos.isSelected()) {
                    parametros.put("tipo", "Todos");

                }

                //Define o parâmetro do nome da entidade*
                parametros.put("nomeEntidade", comboEntidade.getSelectedItem().toString());

                //Define o parâmetro de conclusão das transações*
                if (radioConclusaoTodas.isSelected()) {
                    parametros.put("concluida", "Todos");

                } else if (radioConclusaoConcluidas.isSelected()) {
                    parametros.put("concluida", "Concluídas");

                } else if (radioConclusaoNaoConcluidas.isSelected()) {
                    parametros.put("concluida", "Não Concluídas");

                }

                //Define o parâmetro de Estado de Pagamento Quitado
                if (radioQuitadaTodas.isSelected()) {
                    parametros.put("quitada", "Todos");

                } else if (radioQuitada.isSelected()) {
                    parametros.put("quitada", "Quitada");

                } else {
                    parametros.put("quitada", "Não Quitada");

                }

                //Define parâmetros de tipo de transação
                parametros.put("isPesagem", checkPesagem.isSelected());
                parametros.put("isCompra", checkCompra.isSelected());
                parametros.put("isVenda", checkVenda.isSelected());

                //Define os parâmetros de perído de alteração
                if (radioTodoPeriodoAlteracao.isSelected()) {
                    parametros.put("periodoAlteracao", "Todos");

                } else {

                    Date periodoInicialAlteracao = new Date(format.parse(textPeriodoInicialAlteracao.getText()).getTime());
                    Date periodoFinalAlteracao = new Date(format.parse(textPeriodoFinalAlteracao.getText()).getTime());
                    periodoFinalAlteracao = Dates.verificaDigitosAno(periodoFinalAlteracao);
                    periodoInicialAlteracao = Dates.verificaDigitosAno(periodoInicialAlteracao);

                    if (!periodoFinalAlteracao.before(periodoInicialAlteracao)) {
                        DateFormat dataFormatada = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("pt", "BR"));
                        parametros.put("periodoAlteracaoInicial", periodoInicialAlteracao);
                        parametros.put("periodoAlteracaoFinal", periodoFinalAlteracao);
                        parametros.put("periodoAlteracao", dataFormatada.format(periodoInicialAlteracao) + " a " + dataFormatada.format(periodoFinalAlteracao));

                    } else {
                        JOptionPane.showMessageDialog(this, "A data inicial não pode ser maior do que a data final do período definido para última alteração da transação.",
                                "Atenção", JOptionPane.WARNING_MESSAGE);
                        imprime = false;
                    }
                }

                //Executa o relatório
                if (imprime) {
                    ReportUtils.openReport("Relatório por Entidade", inputStream, parametros, ConnectionFactory_brittos_bd.getConnection());
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


        } else {
            JOptionPane.showMessageDialog(this, "Selecione o tipo de transação (Pesagem, Compra ou Venda) que você deseja imprimir no relatório.\n",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
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

    private void radioTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioTodosActionPerformed
        comboEntidade.setModel(new DAOEntidade().inicializaComboBoxEntidades("Todos", "Todos"));
    }//GEN-LAST:event_radioTodosActionPerformed

    private void radioClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioClienteActionPerformed
        comboEntidade.setModel(new DAOEntidade().inicializaComboBoxEntidades("Todos", "Cliente"));
    }//GEN-LAST:event_radioClienteActionPerformed

    private void radioFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioFornecedorActionPerformed
        comboEntidade.setModel(new DAOEntidade().inicializaComboBoxEntidades("Todos", "Fornecedor"));
    }//GEN-LAST:event_radioFornecedorActionPerformed

    private void radioClienteFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioClienteFornecedorActionPerformed
        comboEntidade.setModel(new DAOEntidade().inicializaComboBoxEntidades("Todos", "Cliente e Fornecedor"));
    }//GEN-LAST:event_radioClienteFornecedorActionPerformed

    private void radioTodoPeriodoAlteracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioTodoPeriodoAlteracaoActionPerformed
        radioTodoPeriodoAlteracao.setSelected(true);
        textPeriodoInicialAlteracao.setEnabled(false);
        textPeriodoFinalAlteracao.setEnabled(false);
        apaga.apagaJTextField(textPeriodoFinalAlteracao, textPeriodoInicialAlteracao);
    }//GEN-LAST:event_radioTodoPeriodoAlteracaoActionPerformed

    private void checkCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkCompraActionPerformed

    private void radioDurantePeriodoAlteracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioDurantePeriodoAlteracaoActionPerformed
        textPeriodoInicialAlteracao.setEnabled(true);
        textPeriodoFinalAlteracao.setEnabled(true);
        apaga.apagaJTextField(textPeriodoFinalAlteracao, textPeriodoInicialAlteracao);
    }//GEN-LAST:event_radioDurantePeriodoAlteracaoActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancelar;
    private javax.swing.JButton buttonImprimir;
    private javax.swing.JButton buttonLimpar;
    private javax.swing.JCheckBox checkCompra;
    private javax.swing.JCheckBox checkPesagem;
    private javax.swing.JCheckBox checkVenda;
    private javax.swing.JComboBox comboEntidade;
    private javax.swing.ButtonGroup groupConclusao;
    private javax.swing.ButtonGroup groupEntidade;
    private javax.swing.ButtonGroup groupPagamento;
    private javax.swing.ButtonGroup groupPeriodoAlteracao;
    private javax.swing.ButtonGroup groupPeriodoCricacao;
    private javax.swing.ButtonGroup groupQuitada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel labelEntidade;
    private javax.swing.JLabel labelTipoEntidade;
    private javax.swing.JLabel labelTransacao;
    private javax.swing.JPanel panelBotoes;
    private javax.swing.JPanel panelConclusao;
    private javax.swing.JPanel panelDefinicoes;
    private javax.swing.JPanel panelFiltros;
    private javax.swing.JPanel panelMensagem;
    private javax.swing.JPanel panelPeriodoAlteração;
    private javax.swing.JPanel panelPeriodoCriação;
    private javax.swing.JPanel panelQuitada;
    private javax.swing.JRadioButton radioCliente;
    private javax.swing.JRadioButton radioClienteFornecedor;
    private javax.swing.JRadioButton radioConclusaoConcluidas;
    private javax.swing.JRadioButton radioConclusaoNaoConcluidas;
    private javax.swing.JRadioButton radioConclusaoTodas;
    private javax.swing.JRadioButton radioDurantePeriodo;
    private javax.swing.JRadioButton radioDurantePeriodoAlteracao;
    private javax.swing.JRadioButton radioFornecedor;
    private javax.swing.JRadioButton radioQuitada;
    private javax.swing.JRadioButton radioQuitadaNao;
    private javax.swing.JRadioButton radioQuitadaTodas;
    private javax.swing.JRadioButton radioTodoPeriodo;
    private javax.swing.JRadioButton radioTodoPeriodoAlteracao;
    private javax.swing.JRadioButton radioTodos;
    private javax.swing.JTextField textMensagem;
    private javax.swing.JTextField textPeriodoFinal;
    private javax.swing.JTextField textPeriodoFinalAlteracao;
    private javax.swing.JTextField textPeriodoInicial;
    private javax.swing.JTextField textPeriodoInicialAlteracao;
    // End of variables declaration//GEN-END:variables
}
