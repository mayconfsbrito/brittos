/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InternalFramePesagem.java
 *
 * Created on 21/01/2011, 18:56:32
 */
package br.com.areiasbrittos.gui.internalFrames;

import br.com.areiasbrittos.controle.gui.ControleIFContasReceber;
import br.com.areiasbrittos.gui.utils.JTextFieldCodigo;
import br.com.areiasbrittos.gui.utils.MascarasJTextField;
import br.com.areiasbrittos.gui.utils.NumeroDocument;
import javax.swing.JFormattedTextField;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class InternalFrameContasReceber extends br.com.areiasbrittos.gui.superclass.InternalFrame {

    //Variáveis desta classe
    public SelectionListener listener;
    private ControleIFContasReceber controle = new ControleIFContasReceber(this);

    //Creates new form InternalFramePesagem
    public InternalFrameContasReceber() {
        initComponents();
        setVisible(true);
        listener = new SelectionListener(this.tabela);
        inicializa();
    }

    @Override
    protected void inicializa() {
        controle.inicializa();
    }

    @Override
    public void tabelaPreencheGUI() {
        controle.tabelaPreencheGUI();
    }

    @Override
    protected void limparGUI() {
        controle.limparGUI();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupBuscaPagamento = new javax.swing.ButtonGroup();
        groupBuscaVencimento = new javax.swing.ButtonGroup();
        panelInformacoes = new javax.swing.JPanel();
        labelCodigo = new javax.swing.JLabel();
        textCodigo = new javax.swing.JTextField();
        labelPesagem = new javax.swing.JLabel();
        textPesagem = new javax.swing.JTextField();
        labelDescricao = new javax.swing.JLabel();
        textDescricao = new javax.swing.JTextField();
        labelObservacao = new javax.swing.JLabel();
        textObservacao = new javax.swing.JTextField();
        labelVencimento = new javax.swing.JLabel();
        textVencimento = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("##/##/####"));
        ((JFormattedTextField)textVencimento).setFocusLostBehavior(JFormattedTextField.COMMIT);
        labelValor = new javax.swing.JLabel();
        labelRS = new javax.swing.JLabel();
        panelPagamento = new javax.swing.JPanel();
        checkQuitada = new javax.swing.JCheckBox();
        labelPagamento = new javax.swing.JLabel();
        textPagamentoDia = new javax.swing.JTextField();
        labelAs = new javax.swing.JLabel();
        textPagamentoHora = new javax.swing.JTextField();
        textVenda = new javax.swing.JTextField();
        labelVenda = new javax.swing.JLabel();
        textValor = new javax.swing.JTextField();
        panelTabela = new javax.swing.JPanel();
        scrollTabela = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        panelOpcoes = new javax.swing.JPanel();
        buttonCancelar = new javax.swing.JButton();
        buttonCadastrar = new javax.swing.JButton();
        buttonAlterar = new javax.swing.JButton();
        buttonLimpar = new javax.swing.JButton();
        buttonQuitar = new javax.swing.JButton();
        buttonExcluir1 = new javax.swing.JButton();
        panelMensagem = new javax.swing.JPanel();
        textMensagem = new javax.swing.JTextField();
        panelBusca = new javax.swing.JPanel();
        labelBuscaDesc = new javax.swing.JLabel();
        buttonBuscar = new javax.swing.JButton();
        buttonLimparBusca = new javax.swing.JButton();
        textBuscaDescricao = new javax.swing.JTextField();
        labelBuscaCodigo = new javax.swing.JLabel();
        textBuscaCodigo = new JTextFieldCodigo(8, false);
        panelPagamento1 = new javax.swing.JPanel();
        radioBuscaPagamentoAberto = new javax.swing.JRadioButton();
        radioBuscaPagamentoQuitada = new javax.swing.JRadioButton();
        radioBuscaPagamentoTodas = new javax.swing.JRadioButton();
        labelBuscaDe = new javax.swing.JLabel();
        textBuscaPagamentoDataDe = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("##/##/####"));
        ((JFormattedTextField)textBuscaPagamentoDataDe).setFocusLostBehavior(JFormattedTextField.COMMIT);
        labelBuscaAte = new javax.swing.JLabel();
        textBuscaPagamentoAte = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("##/##/####"));
        ((JFormattedTextField)textBuscaPagamentoAte).setFocusLostBehavior(JFormattedTextField.COMMIT);
        panelPagamento2 = new javax.swing.JPanel();
        radioBuscaVencimentoEntre = new javax.swing.JRadioButton();
        radioBuscaVencimentoTodos = new javax.swing.JRadioButton();
        labelDe = new javax.swing.JLabel();
        textBuscaVencimentoDe = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("##/##/####"));
        ((JFormattedTextField)textBuscaPagamentoDataDe).setFocusLostBehavior(JFormattedTextField.COMMIT);
        labelAte = new javax.swing.JLabel();
        textBuscaVencimentoAte = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("##/##/####"));
        ((JFormattedTextField)textBuscaPagamentoAte).setFocusLostBehavior(JFormattedTextField.COMMIT);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Contas a Receber");

        panelInformacoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        labelCodigo.setText("Codigo:");

        textCodigo.setEnabled(false);

        labelPesagem.setText("Pesagem:");

        textPesagem.setEnabled(false);

        labelDescricao.setText("Descrição:");

        textDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textDescricaoActionPerformed(evt);
            }
        });

        labelObservacao.setText("Observação:");

        textObservacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textObservacaoActionPerformed(evt);
            }
        });

        labelVencimento.setText("Vencimento:");

        ((JFormattedTextField)textVencimento).setHorizontalAlignment(textVencimento.LEFT);

        labelValor.setText("Valor:");

        labelRS.setText("R$");

        panelPagamento.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pagamento", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        checkQuitada.setText("Quitada");
        checkQuitada.setEnabled(false);

        labelPagamento.setText("Pagamento dia");

        textPagamentoDia.setEnabled(false);

        labelAs.setText("às");

        textPagamentoHora.setEnabled(false);
        textPagamentoHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textPagamentoHoraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPagamentoLayout = new javax.swing.GroupLayout(panelPagamento);
        panelPagamento.setLayout(panelPagamentoLayout);
        panelPagamentoLayout.setHorizontalGroup(
            panelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(checkQuitada)
            .addGroup(panelPagamentoLayout.createSequentialGroup()
                .addComponent(labelPagamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textPagamentoDia, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelAs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textPagamentoHora, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelPagamentoLayout.setVerticalGroup(
            panelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagamentoLayout.createSequentialGroup()
                .addComponent(checkQuitada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelAs)
                        .addComponent(textPagamentoHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelPagamento)
                        .addComponent(textPagamentoDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        textVenda.setEnabled(false);

        labelVenda.setText("Venda:");

        textValor.setHorizontalAlignment(textValor.RIGHT);
        textValor.setDocument(new NumeroDocument(10,2));

        javax.swing.GroupLayout panelInformacoesLayout = new javax.swing.GroupLayout(panelInformacoes);
        panelInformacoes.setLayout(panelInformacoesLayout);
        panelInformacoesLayout.setHorizontalGroup(
            panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacoesLayout.createSequentialGroup()
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addComponent(labelObservacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textObservacao))
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addComponent(labelValor)
                        .addGap(20, 20, 20)
                        .addComponent(labelRS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textValor, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addComponent(labelDescricao)
                        .addGap(16, 16, 16)
                        .addComponent(textDescricao))
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInformacoesLayout.createSequentialGroup()
                                .addComponent(labelVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelInformacoesLayout.createSequentialGroup()
                                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                                        .addComponent(labelCodigo)
                                        .addGap(29, 29, 29)
                                        .addComponent(textCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                                        .addComponent(labelPesagem)
                                        .addGap(19, 19, 19)
                                        .addComponent(textPesagem, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelVenda)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelInformacoesLayout.setVerticalGroup(
            panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacoesLayout.createSequentialGroup()
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelCodigo)
                            .addComponent(textCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelPesagem)
                            .addComponent(textPesagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelVenda)
                            .addComponent(textVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelVencimento)
                            .addComponent(textVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(panelPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDescricao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelObservacao)
                    .addComponent(textObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelValor)
                    .addComponent(labelRS)
                    .addComponent(textValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelTabela.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Histórico de Contas a Receber", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        tabela.setAutoCreateRowSorter(true);
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod", "Descrição", "Vencimento", "Recebido em", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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
        tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollTabela.setViewportView(tabela);

        javax.swing.GroupLayout panelTabelaLayout = new javax.swing.GroupLayout(panelTabela);
        panelTabela.setLayout(panelTabelaLayout);
        panelTabelaLayout.setHorizontalGroup(
            panelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollTabela)
        );
        panelTabelaLayout.setVerticalGroup(
            panelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollTabela, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
        );

        panelOpcoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opções", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        buttonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Cancelar.png"))); // NOI18N
        buttonCancelar.setText("Cancelar");
        buttonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelarActionPerformed(evt);
            }
        });

        buttonCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Confirma.png"))); // NOI18N
        buttonCadastrar.setText("Cadastrar");
        buttonCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCadastrarActionPerformed(evt);
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
        buttonLimpar.setText("Nova/Limpar");
        buttonLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLimparActionPerformed(evt);
            }
        });

        buttonQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Quitar.png"))); // NOI18N
        buttonQuitar.setText("Quitar");
        buttonQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonQuitarActionPerformed(evt);
            }
        });

        buttonExcluir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Excluir.png"))); // NOI18N
        buttonExcluir1.setText("Excluir");
        buttonExcluir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExcluir1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelOpcoesLayout = new javax.swing.GroupLayout(panelOpcoes);
        panelOpcoes.setLayout(panelOpcoesLayout);
        panelOpcoesLayout.setHorizontalGroup(
            panelOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcoesLayout.createSequentialGroup()
                .addComponent(buttonCadastrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonLimpar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonQuitar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonExcluir1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelOpcoesLayout.setVerticalGroup(
            panelOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonCadastrar)
            .addGroup(panelOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(buttonAlterar)
                .addComponent(buttonLimpar)
                .addComponent(buttonCancelar)
                .addComponent(buttonQuitar)
                .addComponent(buttonExcluir1))
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

        panelBusca.setBorder(javax.swing.BorderFactory.createTitledBorder("Busca"));

        labelBuscaDesc.setText("Descrição:");

        buttonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Buscar.png"))); // NOI18N
        buttonBuscar.setText("Buscar");
        buttonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBuscarActionPerformed(evt);
            }
        });

        buttonLimparBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Limpar.png"))); // NOI18N
        buttonLimparBusca.setText("Limpar Busca");
        buttonLimparBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLimparBuscaActionPerformed(evt);
            }
        });

        textBuscaDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textBuscaDescricaoActionPerformed(evt);
            }
        });

        labelBuscaCodigo.setText("Codigo:");

        textBuscaCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textBuscaCodigoActionPerformed(evt);
            }
        });

        panelPagamento1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pagamento", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        groupBuscaPagamento.add(radioBuscaPagamentoAberto);
        radioBuscaPagamentoAberto.setText("Em Aberto");
        radioBuscaPagamentoAberto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioBuscaPagamentoAbertoActionPerformed(evt);
            }
        });

        groupBuscaPagamento.add(radioBuscaPagamentoQuitada);
        radioBuscaPagamentoQuitada.setText("Quitada");
        radioBuscaPagamentoQuitada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioBuscaPagamentoQuitadaActionPerformed(evt);
            }
        });

        groupBuscaPagamento.add(radioBuscaPagamentoTodas);
        radioBuscaPagamentoTodas.setSelected(true);
        radioBuscaPagamentoTodas.setText("Todas");
        radioBuscaPagamentoTodas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioBuscaPagamentoTodasActionPerformed(evt);
            }
        });

        labelBuscaDe.setText("De:");

        ((JFormattedTextField)textBuscaPagamentoDataDe).setHorizontalAlignment(textBuscaPagamentoDataDe.LEFT);
        textBuscaPagamentoDataDe.setEnabled(false);

        labelBuscaAte.setText("Até:");

        ((JFormattedTextField)textBuscaPagamentoAte).setHorizontalAlignment(textBuscaPagamentoAte.LEFT);
        textBuscaPagamentoAte.setEnabled(false);

        javax.swing.GroupLayout panelPagamento1Layout = new javax.swing.GroupLayout(panelPagamento1);
        panelPagamento1.setLayout(panelPagamento1Layout);
        panelPagamento1Layout.setHorizontalGroup(
            panelPagamento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagamento1Layout.createSequentialGroup()
                .addGroup(panelPagamento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPagamento1Layout.createSequentialGroup()
                        .addComponent(radioBuscaPagamentoTodas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radioBuscaPagamentoQuitada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radioBuscaPagamentoAberto))
                    .addGroup(panelPagamento1Layout.createSequentialGroup()
                        .addComponent(labelBuscaDe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textBuscaPagamentoDataDe, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelBuscaAte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textBuscaPagamentoAte, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        panelPagamento1Layout.setVerticalGroup(
            panelPagamento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagamento1Layout.createSequentialGroup()
                .addGroup(panelPagamento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioBuscaPagamentoTodas)
                    .addComponent(radioBuscaPagamentoQuitada)
                    .addComponent(radioBuscaPagamentoAberto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPagamento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBuscaDe)
                    .addComponent(textBuscaPagamentoDataDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelBuscaAte)
                    .addComponent(textBuscaPagamentoAte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelPagamento2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Vencimento:", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        groupBuscaVencimento.add(radioBuscaVencimentoEntre);
        radioBuscaVencimentoEntre.setText("Entre");
        radioBuscaVencimentoEntre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioBuscaVencimentoEntreActionPerformed(evt);
            }
        });

        groupBuscaVencimento.add(radioBuscaVencimentoTodos);
        radioBuscaVencimentoTodos.setSelected(true);
        radioBuscaVencimentoTodos.setText("Todos");
        radioBuscaVencimentoTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioBuscaVencimentoTodosActionPerformed(evt);
            }
        });

        labelDe.setText("De:");

        ((JFormattedTextField)textBuscaPagamentoDataDe).setHorizontalAlignment(textBuscaPagamentoDataDe.LEFT);
        textBuscaVencimentoDe.setEnabled(false);

        labelAte.setText("Até:");

        ((JFormattedTextField)textBuscaPagamentoAte).setHorizontalAlignment(textBuscaPagamentoAte.LEFT);
        textBuscaVencimentoAte.setEnabled(false);

        javax.swing.GroupLayout panelPagamento2Layout = new javax.swing.GroupLayout(panelPagamento2);
        panelPagamento2.setLayout(panelPagamento2Layout);
        panelPagamento2Layout.setHorizontalGroup(
            panelPagamento2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagamento2Layout.createSequentialGroup()
                .addGroup(panelPagamento2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPagamento2Layout.createSequentialGroup()
                        .addComponent(radioBuscaVencimentoTodos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radioBuscaVencimentoEntre))
                    .addGroup(panelPagamento2Layout.createSequentialGroup()
                        .addComponent(labelDe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textBuscaVencimentoDe, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelAte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textBuscaVencimentoAte, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelPagamento2Layout.setVerticalGroup(
            panelPagamento2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagamento2Layout.createSequentialGroup()
                .addGroup(panelPagamento2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioBuscaVencimentoTodos)
                    .addComponent(radioBuscaVencimentoEntre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelPagamento2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDe)
                    .addComponent(textBuscaVencimentoDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelAte)
                    .addComponent(textBuscaVencimentoAte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout panelBuscaLayout = new javax.swing.GroupLayout(panelBusca);
        panelBusca.setLayout(panelBuscaLayout);
        panelBuscaLayout.setHorizontalGroup(
            panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBuscaLayout.createSequentialGroup()
                .addGroup(panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBuscaLayout.createSequentialGroup()
                        .addComponent(panelPagamento1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelPagamento2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelBuscaLayout.createSequentialGroup()
                        .addComponent(labelBuscaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textBuscaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelBuscaDesc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textBuscaDescricao)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonLimparBusca, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                    .addComponent(buttonBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelBuscaLayout.setVerticalGroup(
            panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBuscaLayout.createSequentialGroup()
                .addGroup(panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBuscaLayout.createSequentialGroup()
                        .addGroup(panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textBuscaDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelBuscaDesc)
                            .addComponent(textBuscaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelBuscaCodigo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelPagamento1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelPagamento2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelBuscaLayout.createSequentialGroup()
                        .addComponent(buttonBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonLimparBusca)))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelOpcoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTabela, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelInformacoes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelBusca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addComponent(panelInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOpcoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Cadastra uma nova pesagem
     *
     * @param evt
     */
    private void buttonCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCadastrarActionPerformed
        controle.cadastrar();
}//GEN-LAST:event_buttonCadastrarActionPerformed

    private void buttonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAlterarActionPerformed
        controle.alterar();
}//GEN-LAST:event_buttonAlterarActionPerformed

    private void textPagamentoHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textPagamentoHoraActionPerformed
    }//GEN-LAST:event_textPagamentoHoraActionPerformed

    private void textDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textDescricaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textDescricaoActionPerformed

    private void textObservacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textObservacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textObservacaoActionPerformed

    private void buttonQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonQuitarActionPerformed
        controle.quitar();
    }//GEN-LAST:event_buttonQuitarActionPerformed

    private void buttonExcluir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExcluir1ActionPerformed
        controle.excluir();
    }//GEN-LAST:event_buttonExcluir1ActionPerformed

    private void buttonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBuscarActionPerformed
        controle.buscar();
    }//GEN-LAST:event_buttonBuscarActionPerformed

    private void buttonLimparBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLimparBuscaActionPerformed
        controle.limparBusca();
    }//GEN-LAST:event_buttonLimparBuscaActionPerformed

    private void radioBuscaPagamentoAbertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBuscaPagamentoAbertoActionPerformed
        controle.verificaDataPagamento();
    }//GEN-LAST:event_radioBuscaPagamentoAbertoActionPerformed

    private void radioBuscaPagamentoQuitadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBuscaPagamentoQuitadaActionPerformed
        controle.verificaDataPagamento();
    }//GEN-LAST:event_radioBuscaPagamentoQuitadaActionPerformed

    private void radioBuscaPagamentoTodasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBuscaPagamentoTodasActionPerformed
        controle.verificaDataPagamento();
    }//GEN-LAST:event_radioBuscaPagamentoTodasActionPerformed

    private void radioBuscaVencimentoEntreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBuscaVencimentoEntreActionPerformed
        controle.verificaDataVencimento();
    }//GEN-LAST:event_radioBuscaVencimentoEntreActionPerformed

    private void radioBuscaVencimentoTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBuscaVencimentoTodosActionPerformed
        controle.verificaDataVencimento();
    }//GEN-LAST:event_radioBuscaVencimentoTodosActionPerformed

    private void textBuscaCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textBuscaCodigoActionPerformed
        controle.buscar();
    }//GEN-LAST:event_textBuscaCodigoActionPerformed

    private void textBuscaDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textBuscaDescricaoActionPerformed
        controle.buscar();
    }//GEN-LAST:event_textBuscaDescricaoActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton buttonAlterar;
    public javax.swing.JButton buttonBuscar;
    public javax.swing.JButton buttonCadastrar;
    public javax.swing.JButton buttonCancelar;
    public javax.swing.JButton buttonExcluir1;
    public javax.swing.JButton buttonLimpar;
    public javax.swing.JButton buttonLimparBusca;
    public javax.swing.JButton buttonQuitar;
    public javax.swing.JCheckBox checkQuitada;
    private javax.swing.ButtonGroup groupBuscaPagamento;
    private javax.swing.ButtonGroup groupBuscaVencimento;
    private javax.swing.JLabel labelAs;
    private javax.swing.JLabel labelAte;
    private javax.swing.JLabel labelBuscaAte;
    private javax.swing.JLabel labelBuscaCodigo;
    private javax.swing.JLabel labelBuscaDe;
    private javax.swing.JLabel labelBuscaDesc;
    private javax.swing.JLabel labelCodigo;
    private javax.swing.JLabel labelDe;
    private javax.swing.JLabel labelDescricao;
    private javax.swing.JLabel labelObservacao;
    private javax.swing.JLabel labelPagamento;
    private javax.swing.JLabel labelPesagem;
    private javax.swing.JLabel labelRS;
    private javax.swing.JLabel labelValor;
    private javax.swing.JLabel labelVencimento;
    private javax.swing.JLabel labelVenda;
    private javax.swing.JPanel panelBusca;
    private javax.swing.JPanel panelInformacoes;
    private javax.swing.JPanel panelMensagem;
    private javax.swing.JPanel panelOpcoes;
    private javax.swing.JPanel panelPagamento;
    private javax.swing.JPanel panelPagamento1;
    private javax.swing.JPanel panelPagamento2;
    private javax.swing.JPanel panelTabela;
    public javax.swing.JRadioButton radioBuscaPagamentoAberto;
    public javax.swing.JRadioButton radioBuscaPagamentoQuitada;
    public javax.swing.JRadioButton radioBuscaPagamentoTodas;
    public javax.swing.JRadioButton radioBuscaVencimentoEntre;
    public javax.swing.JRadioButton radioBuscaVencimentoTodos;
    private javax.swing.JScrollPane scrollTabela;
    public javax.swing.JTable tabela;
    public javax.swing.JTextField textBuscaCodigo;
    public javax.swing.JTextField textBuscaDescricao;
    public javax.swing.JTextField textBuscaPagamentoAte;
    public javax.swing.JTextField textBuscaPagamentoDataDe;
    public javax.swing.JTextField textBuscaVencimentoAte;
    public javax.swing.JTextField textBuscaVencimentoDe;
    public javax.swing.JTextField textCodigo;
    public javax.swing.JTextField textDescricao;
    public javax.swing.JTextField textMensagem;
    public javax.swing.JTextField textObservacao;
    public javax.swing.JTextField textPagamentoDia;
    public javax.swing.JTextField textPagamentoHora;
    public javax.swing.JTextField textPesagem;
    public javax.swing.JTextField textValor;
    public javax.swing.JTextField textVencimento;
    public javax.swing.JTextField textVenda;
    // End of variables declaration//GEN-END:variables
}
