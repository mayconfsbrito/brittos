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

import br.com.areiasbrittos.controle.gui.ControleIFProdutosVenda;
import br.com.areiasbrittos.gui.utils.JTextFieldCodigo;
import br.com.areiasbrittos.gui.utils.MascarasJTextField;
import br.com.areiasbrittos.gui.utils.NumeroDocument;
import javax.swing.JFormattedTextField;

/**
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class InternalFrameProdutosVenda extends br.com.areiasbrittos.gui.superclass.InternalFrame {

    /*Variáveis desta classe*/
    public SelectionListener listener;
    private ControleIFProdutosVenda controle = new ControleIFProdutosVenda(this);

    /** Creates new form InternalFramePesagem */
    public InternalFrameProdutosVenda() {
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

        groupPagamento = new javax.swing.ButtonGroup();
        groupBuscaPagamento = new javax.swing.ButtonGroup();
        groupBuscaCriacao = new javax.swing.ButtonGroup();
        panelInformacoes = new javax.swing.JPanel();
        labelFornecedor = new javax.swing.JLabel();
        textPlaca = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("UUU-####"));
        comboFornecedor = new javax.swing.JComboBox();
        labelCodigo = new javax.swing.JLabel();
        textCodigo = new javax.swing.JTextField();
        labelPlaca = new javax.swing.JLabel();
        labelMotorista = new javax.swing.JLabel();
        textMotorista = new javax.swing.JTextField();
        labelDataCriacao = new javax.swing.JLabel();
        textDataCriacao = new javax.swing.JTextField();
        labelHoraCriacao = new javax.swing.JLabel();
        textHoraCriacao = new javax.swing.JTextField();
        panelPagamento1 = new javax.swing.JPanel();
        labelPagamento1 = new javax.swing.JLabel();
        textPagamentoDia = new javax.swing.JTextField();
        labelAs = new javax.swing.JLabel();
        textPagamentoHora = new javax.swing.JTextField();
        radioAberto = new javax.swing.JRadioButton();
        radioQuitada = new javax.swing.JRadioButton();
        labelPagamento2 = new javax.swing.JLabel();
        textPagamentoIdConta = new javax.swing.JTextField();
        panelPagamento = new javax.swing.JPanel();
        labelVencimento = new javax.swing.JLabel();
        textVencimento = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("##/##/####"));
        ((JFormattedTextField)textVencimento).setFocusLostBehavior(JFormattedTextField.COMMIT);
        labelPagamento = new javax.swing.JLabel();
        comboPagamento = new javax.swing.JComboBox();
        labelValorPesagem = new javax.swing.JLabel();
        labelRS = new javax.swing.JLabel();
        textValor = new javax.swing.JTextField();
        panelTabela = new javax.swing.JPanel();
        scrollTabela = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        panelOpcoes = new javax.swing.JPanel();
        buttonCancelar = new javax.swing.JButton();
        buttonCadastro = new javax.swing.JButton();
        buttonAlterar = new javax.swing.JButton();
        buttonLimpar = new javax.swing.JButton();
        buttonImprimir = new javax.swing.JButton();
        buttonConcluir = new javax.swing.JButton();
        buttonDesconcluir = new javax.swing.JButton();
        buttonQuitar = new javax.swing.JButton();
        panelMensagem = new javax.swing.JPanel();
        textMensagem = new javax.swing.JTextField();
        panelProdutos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaProdutos = new javax.swing.JTable();
        buttonInserir = new javax.swing.JButton();
        buttonRemover = new javax.swing.JButton();
        panelBusca = new javax.swing.JPanel();
        buttonBuscar = new javax.swing.JButton();
        buttonLimparBusca = new javax.swing.JButton();
        labelBuscaCodigo = new javax.swing.JLabel();
        textBuscaCodigo = new JTextFieldCodigo(8, false);
        panelPagamento2 = new javax.swing.JPanel();
        radioBuscaPagamentoAberto = new javax.swing.JRadioButton();
        radioBuscaPagamentoQuitada = new javax.swing.JRadioButton();
        radioBuscaPagamentoTodas = new javax.swing.JRadioButton();
        labelBuscaCliente = new javax.swing.JLabel();
        comboBuscaCliente = new javax.swing.JComboBox();
        panelPagamento3 = new javax.swing.JPanel();
        radioBuscaCriacaoEntre = new javax.swing.JRadioButton();
        radioBuscaCriacaoTodas = new javax.swing.JRadioButton();
        textBuscaCriacaoDe = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("##/##/####"));
        ((JFormattedTextField)textBuscaCriacaoDe).setFocusLostBehavior(JFormattedTextField.COMMIT);
        labelBuscaDe = new javax.swing.JLabel();
        labelBuscaAte = new javax.swing.JLabel();
        textBuscaCriacaoAte = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("##/##/####"));
        ((JFormattedTextField)textBuscaCriacaoAte).setFocusLostBehavior(JFormattedTextField.COMMIT);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Vendas/Saídas de Produtos");

        panelInformacoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        labelFornecedor.setText("Cliente:");

        ((JFormattedTextField)textPlaca).setFocusLostBehavior(JFormattedTextField.COMMIT);
        ((JFormattedTextField)textPlaca).setHorizontalAlignment(textPlaca.LEFT);

        labelCodigo.setText("Codigo:");

        textCodigo.setEnabled(false);

        labelPlaca.setText("Placa:");

        labelMotorista.setText("Motorista:");

        labelDataCriacao.setText("Data de Criação:");

        textDataCriacao.setEnabled(false);

        labelHoraCriacao.setText("Hora de Criação:");

        textHoraCriacao.setEnabled(false);
        textHoraCriacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textHoraCriacaoActionPerformed(evt);
            }
        });

        panelPagamento1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pagamento", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        labelPagamento1.setText("Quitada dia");

        textPagamentoDia.setEnabled(false);

        labelAs.setText("às");

        textPagamentoHora.setEnabled(false);
        textPagamentoHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textPagamentoHoraActionPerformed(evt);
            }
        });

        groupPagamento.add(radioAberto);
        radioAberto.setSelected(true);
        radioAberto.setText("Em Aberto");
        radioAberto.setEnabled(false);

        groupPagamento.add(radioQuitada);
        radioQuitada.setText("Quitada");
        radioQuitada.setEnabled(false);

        labelPagamento2.setText("Codigo da conta a receber:");

        textPagamentoIdConta.setEnabled(false);

        javax.swing.GroupLayout panelPagamento1Layout = new javax.swing.GroupLayout(panelPagamento1);
        panelPagamento1.setLayout(panelPagamento1Layout);
        panelPagamento1Layout.setHorizontalGroup(
            panelPagamento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagamento1Layout.createSequentialGroup()
                .addComponent(radioAberto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioQuitada)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPagamento1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelPagamento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelPagamento1Layout.createSequentialGroup()
                        .addComponent(labelPagamento2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textPagamentoIdConta))
                    .addGroup(panelPagamento1Layout.createSequentialGroup()
                        .addComponent(labelPagamento1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textPagamentoDia, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelAs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textPagamentoHora, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelPagamento1Layout.setVerticalGroup(
            panelPagamento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagamento1Layout.createSequentialGroup()
                .addGroup(panelPagamento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioAberto)
                    .addComponent(radioQuitada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPagamento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPagamento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelAs)
                        .addComponent(textPagamentoHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPagamento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelPagamento1)
                        .addComponent(textPagamentoDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPagamento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPagamento2)
                    .addComponent(textPagamentoIdConta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelInformacoesLayout = new javax.swing.GroupLayout(panelInformacoes);
        panelInformacoes.setLayout(panelInformacoesLayout);
        panelInformacoesLayout.setHorizontalGroup(
            panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInformacoesLayout.createSequentialGroup()
                                .addComponent(labelCodigo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelInformacoesLayout.createSequentialGroup()
                                .addComponent(labelDataCriacao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textDataCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labelHoraCriacao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textHoraCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelPlaca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInformacoesLayout.createSequentialGroup()
                                .addComponent(textPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelMotorista)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(comboFornecedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelPagamento1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelInformacoesLayout.setVerticalGroup(
            panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacoesLayout.createSequentialGroup()
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCodigo)
                    .addComponent(textCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDataCriacao)
                    .addComponent(textDataCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelHoraCriacao)
                    .addComponent(textHoraCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelFornecedor)
                    .addComponent(comboFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPlaca)
                    .addComponent(textPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelMotorista)
                    .addComponent(textMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addComponent(panelPagamento1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panelPagamento.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pagamento", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        labelVencimento.setText("Vencimento:");

        ((JFormattedTextField)textVencimento).setHorizontalAlignment(textVencimento.LEFT);

        labelPagamento.setText("Pagamento:");

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

        labelValorPesagem.setText("Valor da Total da Venda:");

        labelRS.setText("R$");

        textValor.setHorizontalAlignment(textValor.RIGHT);
        textValor.setDocument(new NumeroDocument(10,2));

        javax.swing.GroupLayout panelPagamentoLayout = new javax.swing.GroupLayout(panelPagamento);
        panelPagamento.setLayout(panelPagamentoLayout);
        panelPagamentoLayout.setHorizontalGroup(
            panelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelPagamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelVencimento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelValorPesagem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelRS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textValor, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelPagamentoLayout.setVerticalGroup(
            panelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(labelPagamento)
                .addComponent(comboPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(labelVencimento)
                .addComponent(textVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(labelValorPesagem)
                .addComponent(labelRS)
                .addComponent(textValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelTabela.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Histórico de Vendas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        tabela.setAutoCreateRowSorter(true);
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod", "Data", "Fornecedor", "Quitada", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
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
            .addComponent(scrollTabela, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
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
        buttonLimpar.setText("Nova/Limpar");
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

        buttonConcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Concluir.png"))); // NOI18N
        buttonConcluir.setText("Concluir");
        buttonConcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonConcluirActionPerformed(evt);
            }
        });

        buttonDesconcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Desconcluir.png"))); // NOI18N
        buttonDesconcluir.setText("Desconcluir");
        buttonDesconcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDesconcluirActionPerformed(evt);
            }
        });

        buttonQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Quitar.png"))); // NOI18N
        buttonQuitar.setText("Quitar");
        buttonQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonQuitarActionPerformed(evt);
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
                .addComponent(buttonImprimir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonConcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonDesconcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonQuitar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelOpcoesLayout.setVerticalGroup(
            panelOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonCadastro)
            .addGroup(panelOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(buttonAlterar)
                .addComponent(buttonLimpar)
                .addComponent(buttonImprimir)
                .addComponent(buttonCancelar)
                .addComponent(buttonConcluir)
                .addComponent(buttonDesconcluir)
                .addComponent(buttonQuitar))
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

        panelProdutos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Produtos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        tabelaProdutos.setAutoCreateRowSorter(true);
        tabelaProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Produto", "Tara", "Peso Bruto", "Peso Líquido", "Valor Unit.", "Valor Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaProdutosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaProdutos);

        buttonInserir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Inserir.png"))); // NOI18N
        buttonInserir.setText("Inserir");
        buttonInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonInserirActionPerformed(evt);
            }
        });

        buttonRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Remover.png"))); // NOI18N
        buttonRemover.setText("Remover");
        buttonRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRemoverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelProdutosLayout = new javax.swing.GroupLayout(panelProdutos);
        panelProdutos.setLayout(panelProdutosLayout);
        panelProdutosLayout.setHorizontalGroup(
            panelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProdutosLayout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonInserir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRemover, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)))
        );
        panelProdutosLayout.setVerticalGroup(
            panelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProdutosLayout.createSequentialGroup()
                .addComponent(buttonInserir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonRemover))
        );

        panelBusca.setBorder(javax.swing.BorderFactory.createTitledBorder("Busca"));

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

        labelBuscaCodigo.setText("Codigo:");

        textBuscaCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textBuscaCodigoActionPerformed(evt);
            }
        });

        panelPagamento2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pagamento", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

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

        javax.swing.GroupLayout panelPagamento2Layout = new javax.swing.GroupLayout(panelPagamento2);
        panelPagamento2.setLayout(panelPagamento2Layout);
        panelPagamento2Layout.setHorizontalGroup(
            panelPagamento2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagamento2Layout.createSequentialGroup()
                .addComponent(radioBuscaPagamentoTodas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioBuscaPagamentoQuitada)
                .addGap(4, 4, 4)
                .addComponent(radioBuscaPagamentoAberto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelPagamento2Layout.setVerticalGroup(
            panelPagamento2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagamento2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(radioBuscaPagamentoTodas)
                .addComponent(radioBuscaPagamentoQuitada)
                .addComponent(radioBuscaPagamentoAberto))
        );

        labelBuscaCliente.setText("Cliente:");

        panelPagamento3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Criação:", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        groupBuscaCriacao.add(radioBuscaCriacaoEntre);
        radioBuscaCriacaoEntre.setText("Entre");
        radioBuscaCriacaoEntre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioBuscaCriacaoEntreActionPerformed(evt);
            }
        });

        groupBuscaCriacao.add(radioBuscaCriacaoTodas);
        radioBuscaCriacaoTodas.setSelected(true);
        radioBuscaCriacaoTodas.setText("Todas");
        radioBuscaCriacaoTodas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioBuscaCriacaoTodasActionPerformed(evt);
            }
        });

        ((JFormattedTextField)textBuscaCriacaoDe).setHorizontalAlignment(textBuscaCriacaoDe.LEFT);
        textBuscaCriacaoDe.setEnabled(false);

        labelBuscaDe.setText("De:");

        labelBuscaAte.setText("Até:");

        ((JFormattedTextField)textBuscaCriacaoAte).setHorizontalAlignment(textBuscaCriacaoAte.LEFT);
        textBuscaCriacaoAte.setEnabled(false);

        javax.swing.GroupLayout panelPagamento3Layout = new javax.swing.GroupLayout(panelPagamento3);
        panelPagamento3.setLayout(panelPagamento3Layout);
        panelPagamento3Layout.setHorizontalGroup(
            panelPagamento3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagamento3Layout.createSequentialGroup()
                .addGroup(panelPagamento3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPagamento3Layout.createSequentialGroup()
                        .addComponent(radioBuscaCriacaoTodas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radioBuscaCriacaoEntre))
                    .addGroup(panelPagamento3Layout.createSequentialGroup()
                        .addComponent(labelBuscaDe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textBuscaCriacaoDe, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelBuscaAte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textBuscaCriacaoAte, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelPagamento3Layout.setVerticalGroup(
            panelPagamento3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagamento3Layout.createSequentialGroup()
                .addGroup(panelPagamento3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioBuscaCriacaoTodas)
                    .addComponent(radioBuscaCriacaoEntre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPagamento3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBuscaDe)
                    .addComponent(textBuscaCriacaoDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelBuscaAte)
                    .addComponent(textBuscaCriacaoAte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelBuscaLayout = new javax.swing.GroupLayout(panelBusca);
        panelBusca.setLayout(panelBuscaLayout);
        panelBuscaLayout.setHorizontalGroup(
            panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBuscaLayout.createSequentialGroup()
                .addGroup(panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBuscaLayout.createSequentialGroup()
                        .addComponent(labelBuscaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBuscaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBuscaLayout.createSequentialGroup()
                        .addComponent(buttonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonLimparBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBuscaLayout.createSequentialGroup()
                        .addComponent(labelBuscaCodigo)
                        .addGap(17, 17, 17)
                        .addComponent(textBuscaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelPagamento3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelPagamento2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelBuscaLayout.setVerticalGroup(
            panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBuscaLayout.createSequentialGroup()
                .addGroup(panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelPagamento2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelBuscaLayout.createSequentialGroup()
                        .addGroup(panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textBuscaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelBuscaCodigo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelBuscaCliente)
                            .addComponent(comboBuscaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonBuscar)
                    .addComponent(buttonLimparBusca))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelBuscaLayout.createSequentialGroup()
                .addComponent(panelPagamento3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelProdutos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelPagamento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelOpcoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelMensagem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelInformacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelBusca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(panelTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOpcoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Cadastra uma nova pesagem
     * @param evt 
     */
    private void buttonCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCadastroActionPerformed
        controle.cadastro();
}//GEN-LAST:event_buttonCadastroActionPerformed

    private void buttonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAlterarActionPerformed
        controle.alterar();
}//GEN-LAST:event_buttonAlterarActionPerformed

    private void buttonImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonImprimirActionPerformed
        controle.imprimir();
}//GEN-LAST:event_buttonImprimirActionPerformed

    private void comboPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPagamentoActionPerformed
        controle.calculaTextVencimento();
    }//GEN-LAST:event_comboPagamentoActionPerformed

    private void comboPagamentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comboPagamentoFocusLost
        controle.calculaTextVencimento();
    }//GEN-LAST:event_comboPagamentoFocusLost

    private void buttonConcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonConcluirActionPerformed
        controle.concluir();
    }//GEN-LAST:event_buttonConcluirActionPerformed

    private void buttonDesconcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDesconcluirActionPerformed
        controle.desconcluir();
    }//GEN-LAST:event_buttonDesconcluirActionPerformed

    private void buttonInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInserirActionPerformed
        controle.inserirProduto();
    }//GEN-LAST:event_buttonInserirActionPerformed

    private void buttonRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRemoverActionPerformed
        controle.removerProduto();
    }//GEN-LAST:event_buttonRemoverActionPerformed

    private void tabelaProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaProdutosMouseClicked
        controle.consultaTabelaProdutos(evt);

    }//GEN-LAST:event_tabelaProdutosMouseClicked

    private void textHoraCriacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textHoraCriacaoActionPerformed
    }
        // TODO add your handling code here:}//GEN-LAST:event_textHoraCriacaoActionPerformed

    private void buttonQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonQuitarActionPerformed
        controle.quitar();
    }//GEN-LAST:event_buttonQuitarActionPerformed

    private void textPagamentoHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textPagamentoHoraActionPerformed

   }//GEN-LAST:event_textPagamentoHoraActionPerformed

    private void buttonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBuscarActionPerformed
        controle.buscar();
    }//GEN-LAST:event_buttonBuscarActionPerformed

    private void buttonLimparBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLimparBuscaActionPerformed
        controle.limparBusca();
    }//GEN-LAST:event_buttonLimparBuscaActionPerformed

    private void textBuscaCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textBuscaCodigoActionPerformed
        controle.buscar();
    }//GEN-LAST:event_textBuscaCodigoActionPerformed

    private void radioBuscaPagamentoAbertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBuscaPagamentoAbertoActionPerformed

   }//GEN-LAST:event_radioBuscaPagamentoAbertoActionPerformed

    private void radioBuscaPagamentoQuitadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBuscaPagamentoQuitadaActionPerformed

   }//GEN-LAST:event_radioBuscaPagamentoQuitadaActionPerformed

    private void radioBuscaPagamentoTodasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBuscaPagamentoTodasActionPerformed

   }//GEN-LAST:event_radioBuscaPagamentoTodasActionPerformed

    private void radioBuscaCriacaoEntreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBuscaCriacaoEntreActionPerformed
        controle.verificaDataCriacao();
    }//GEN-LAST:event_radioBuscaCriacaoEntreActionPerformed

    private void radioBuscaCriacaoTodasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBuscaCriacaoTodasActionPerformed
        controle.verificaDataCriacao();
    }//GEN-LAST:event_radioBuscaCriacaoTodasActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton buttonAlterar;
    public javax.swing.JButton buttonBuscar;
    public javax.swing.JButton buttonCadastro;
    public javax.swing.JButton buttonCancelar;
    public javax.swing.JButton buttonConcluir;
    public javax.swing.JButton buttonDesconcluir;
    public javax.swing.JButton buttonImprimir;
    public javax.swing.JButton buttonInserir;
    public javax.swing.JButton buttonLimpar;
    public javax.swing.JButton buttonLimparBusca;
    public javax.swing.JButton buttonQuitar;
    public javax.swing.JButton buttonRemover;
    public javax.swing.JComboBox comboBuscaCliente;
    public javax.swing.JComboBox comboFornecedor;
    public javax.swing.JComboBox comboPagamento;
    private javax.swing.ButtonGroup groupBuscaCriacao;
    private javax.swing.ButtonGroup groupBuscaPagamento;
    private javax.swing.ButtonGroup groupPagamento;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelAs;
    private javax.swing.JLabel labelBuscaAte;
    private javax.swing.JLabel labelBuscaCliente;
    private javax.swing.JLabel labelBuscaCodigo;
    private javax.swing.JLabel labelBuscaDe;
    private javax.swing.JLabel labelCodigo;
    private javax.swing.JLabel labelDataCriacao;
    private javax.swing.JLabel labelFornecedor;
    private javax.swing.JLabel labelHoraCriacao;
    private javax.swing.JLabel labelMotorista;
    private javax.swing.JLabel labelPagamento;
    private javax.swing.JLabel labelPagamento1;
    private javax.swing.JLabel labelPagamento2;
    private javax.swing.JLabel labelPlaca;
    private javax.swing.JLabel labelRS;
    private javax.swing.JLabel labelValorPesagem;
    private javax.swing.JLabel labelVencimento;
    private javax.swing.JPanel panelBusca;
    private javax.swing.JPanel panelInformacoes;
    private javax.swing.JPanel panelMensagem;
    private javax.swing.JPanel panelOpcoes;
    private javax.swing.JPanel panelPagamento;
    private javax.swing.JPanel panelPagamento1;
    private javax.swing.JPanel panelPagamento2;
    private javax.swing.JPanel panelPagamento3;
    private javax.swing.JPanel panelProdutos;
    private javax.swing.JPanel panelTabela;
    public javax.swing.JRadioButton radioAberto;
    public javax.swing.JRadioButton radioBuscaCriacaoEntre;
    public javax.swing.JRadioButton radioBuscaCriacaoTodas;
    public javax.swing.JRadioButton radioBuscaPagamentoAberto;
    public javax.swing.JRadioButton radioBuscaPagamentoQuitada;
    public javax.swing.JRadioButton radioBuscaPagamentoTodas;
    public javax.swing.JRadioButton radioQuitada;
    private javax.swing.JScrollPane scrollTabela;
    public javax.swing.JTable tabela;
    public javax.swing.JTable tabelaProdutos;
    public javax.swing.JTextField textBuscaCodigo;
    public javax.swing.JTextField textBuscaCriacaoAte;
    public javax.swing.JTextField textBuscaCriacaoDe;
    public javax.swing.JTextField textCodigo;
    public javax.swing.JTextField textDataCriacao;
    public javax.swing.JTextField textHoraCriacao;
    public javax.swing.JTextField textMensagem;
    public javax.swing.JTextField textMotorista;
    public javax.swing.JTextField textPagamentoDia;
    public javax.swing.JTextField textPagamentoHora;
    public javax.swing.JTextField textPagamentoIdConta;
    public javax.swing.JTextField textPlaca;
    public javax.swing.JTextField textValor;
    public javax.swing.JTextField textVencimento;
    // End of variables declaration//GEN-END:variables
}
