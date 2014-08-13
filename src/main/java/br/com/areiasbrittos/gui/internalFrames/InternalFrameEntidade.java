/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InternalFrameEntidade.java
 *
 * Created on 17/01/2011, 18:27:01
 */
package br.com.areiasbrittos.gui.internalFrames;

import br.com.areiasbrittos.controle.gui.ControleFPreferencias;
import br.com.areiasbrittos.gui.utils.TamanhoJTextField;
import br.com.areiasbrittos.gui.utils.MascarasJTextField;
import br.com.areiasbrittos.gui.utils.ApagaElementosDaInterface;
import br.com.areiasbrittos.gui.utils.EnabledGUIElements;
import br.com.areiasbrittos.persistencia.dao.DAOEntidade;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import br.com.areiasbrittos.controle.objetos.Entidade;
import br.com.areiasbrittos.persistencia.ConnectionFactory_brittos_bd;
import br.com.areiasbrittos.controle.utils.ReportUtils;
import br.com.areiasbrittos.gui.FramePrincipal;
import br.com.areiasbrittos.gui.utils.*;
import br.com.areiasbrittos.persistencia.dao.AbstractDAO;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class InternalFrameEntidade extends br.com.areiasbrittos.gui.superclass.InternalFrame {

    /*
     * Variaveis da classe
     */
    private DAOEntidade dao = new DAOEntidade();
    private final int[] tamanhosJTextField = {60, 40, 40, 60, 60, 60};
    private ApagaElementosDaInterface apaga = new ApagaElementosDaInterface();
    private EnabledGUIElements enabledGui = new EnabledGUIElements();

    /**
     * Creates new form InternalFrameEntidade
     */
    public InternalFrameEntidade() {
        initComponents();
        setVisible(true);
        inicializa();
    }

    @Override
    public void inicializa() {

        textMensagem.setText("Aqui são gerenciados os Clientes e Fornecedores.");
        TamanhoJTextField.limitaTamanho(tamanhosJTextField, textBairro, textCidade, textFantasia, textLogradouro, textNome);

        //Configura o comportamento dos botões da interface
        buttonCadastro.setEnabled(true);
        EnabledGUIElements.disabledJComponent(this.buttonAlterar, this.buttonExcluir, this.buttonImprimir);
        this.guiAtiva(true);

        //Inicializa eventos da tabela
        SelectionListener listener = new SelectionListener(this.tabela);
        this.tabela.getSelectionModel().addListSelectionListener(listener);
        this.tabela.getColumnModel().getSelectionModel().addListSelectionListener(listener);
    }

    @Override
    protected void listarTodosElementosNaTabela() {

        List<Entidade> list = dao.listar("nome");
        listarPesquisaNaTabela(list);

    }

    private void listarPesquisaNaTabela(List<Entidade> list) {

        tabela.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(500);

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);



        try {
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    Entidade e = list.get(i);
                    modelo.addRow(new Object[]{e.getIdEntidade(), e.getNome()});
                }
            }
        } catch (Exception er) {
            JOptionPane.showMessageDialog(this, "Erro na listagem!\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    protected void limparGUI() {

        this.buscar();
        apaga.apagaCheckBox(checkCliente, checkFornecedor);
        apaga.apagaJComboBox(comboEstado);
        apaga.apagaJTextField(textBairro, textCelular, textCep, textCidade, textCodigo, textCpf, textCnpj, textFantasia,
                textInsEstadual, textFax, textLogradouro, textNome, textNumero, textTelefone);
        groupPessoa.clearSelection();
        this.tabela.clearSelection();

        //Configura o comportamento dos botões da interface
        buttonCadastro.setEnabled(true);
        EnabledGUIElements.disabledJComponent(this.buttonAlterar, this.buttonExcluir, this.buttonImprimir, this.buttonExcluir);
        this.guiAtiva(true);

    }

    /**
     * Verifica o acesso do usuário para utilizar este frame
     */
    @Override
    protected void verificaAcessos() {

        /*
         * Verifica a permissao de @user para este frame
         */
        if (!FramePrincipal.acessos.get(FramePrincipal.PERMISSAO_ALTERA_ENTIDADE).isPermissao()) {
            EnabledGUIElements.disabledJComponent(this.buttonCadastro, this.buttonAlterar, this.buttonExcluir);
        }
    }

    /**
     * Altera a disponibilidade dos componentes graficos da GUI
     *
     * @param permissao
     */
    @Override
    protected void guiAtiva(boolean permissao) {

        this.verificaAcessos();
    }

    @Override
    protected void tabelaPreencheGUI() {

        int linha = tabela.getSelectedRow();
        Integer cod;
        try {
            cod = (Integer) tabela.getValueAt(linha, 0);
        } catch (IndexOutOfBoundsException er) {
            cod = 0;
        }
        List<Entidade> consulta = dao.consultar("idEntidade=" + cod);

        apaga.apagaCheckBox(checkCliente, checkFornecedor);
        groupPessoa.clearSelection();

        if (consulta.size() > 0) {

            Entidade e = consulta.get(0);
            textCodigo.setText(Integer.toString(e.getIdEntidade()));
            textNome.setText(e.getNome());
            textFantasia.setText(e.getFantasia());
            textCidade.setText(e.getCidade());
            comboEstado.setSelectedItem(e.getEstado());
            textLogradouro.setText(e.getLogradouro());
            textBairro.setText(e.getBairro());
            textCep.setText(e.getCep());
            textTelefone.setText(e.getTelefone());
            textCelular.setText(e.getCelular());
            textFax.setText(e.getFax());
            textNumero.setText(e.getNumero());
            textInsEstadual.setText(e.getInscricaoEstadual());

            /*
             * Verifica se o cliente é físico ou jurídico para preencher o cpf
             * ou cnpj
             */
            if (e.isPesJuridica()) {
                textCnpj.setText(e.getCpfCnpj());
                radioPesJuridica.setSelected(true);
                radioPesJuridicaActionPerformed(null);
            } else {
                textCpf.setText(e.getCpfCnpj());
                radioPesFisica.setSelected(true);
                radioPesFisicaActionPerformed(null);
            }

            /*
             * Configura o comportamento dos botões da interface
             */
            buttonCadastro.setEnabled(false);
            EnabledGUIElements.enabledJComponent(this.buttonImprimir, this.buttonAlterar, this.buttonAlterar, this.buttonExcluir);
            this.verificaAcessos();

            if (e.getTipo().equals("Cliente")) {
                checkCliente.setSelected(true);
                checkFornecedor.setSelected(false);
            } else if (e.getTipo().equals("Fornecedor")) {
                checkCliente.setSelected(false);
                checkFornecedor.setSelected(true);
            } else if (e.getTipo().equals("Cliente e Fornecedor")) {
                checkCliente.setSelected(true);
                checkFornecedor.setSelected(true);
            }
        }
    }

    private Entidade criaEntidade() throws NumberFormatException {

        String tipoEntidade = "";
        String cpjfCnpj = "";

        //Aqui se descobre o tipo da entidade:
        if (checkCliente.isSelected() && checkFornecedor.isSelected()) {
            tipoEntidade = "Cliente e Fornecedor";
        } else if (checkFornecedor.isSelected()) {
            tipoEntidade = "Fornecedor";
        } else {
            tipoEntidade = "Cliente";
        }

        boolean pessoa = false;
        /*
         * Aqui se descobre o tipo de Pessoa da entidade: true = Pessoa Física
         * false = Pessoa Jurídica
         */
        if (radioPesJuridica.isSelected()) {
            pessoa = true;
            cpjfCnpj = textCnpj.getText();
        } else {
            cpjfCnpj = textCpf.getText();
        }

        return new Entidade(textNome.getText(), textFantasia.getText(), textCidade.getText(),
                ((String) comboEstado.getSelectedItem()), textLogradouro.getText(), textNumero.getText(),
                textBairro.getText(), textCep.getText(), cpjfCnpj, textTelefone.getText(),
                textCelular.getText(), textFax.getText(), textInsEstadual.getText(),
                tipoEntidade, pessoa);
    }

    private boolean verificaMascaras() {

        MascarasJTextField mascaras = new MascarasJTextField();

        if (mascaras.validaMascaraTelefone(textCelular.getText())
                && mascaras.validaMascaraTelefone(textTelefone.getText())
                && mascaras.validaMascaraTelefone(textFax.getText())) {

            if (mascaras.validaMascaraCep(textCep.getText())) {

                if (mascaras.validaMascaraCpf(textCpf.getText())) {

                    return true;

                } else {
                    JOptionPane.showMessageDialog(this, "O valor digitado para CPF é inválido!\n"
                            + "Por favor, digite um valor correto.", "Atenção!", JOptionPane.WARNING_MESSAGE);
                    return false;
                }

            } else {

                JOptionPane.showMessageDialog(this, "O valor digitado para CEP é inválido!\n"
                        + "Por favor, digite um valor correto.", "Atenção!", JOptionPane.WARNING_MESSAGE);
                return false;
            }

        } else {
            JOptionPane.showMessageDialog(this, "O valor digitado para telefone, celular ou fax é inválido!\n"
                    + "Por favor, digite um valor correto.", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    public boolean buscar() {

        try {

            //Captura os parametros do frame
            String id = this.textBuscaCodigo.getText();
            String desc = this.textBuscaDescricao.getText();

            //Realiza a busca no bd
            List<Entidade> list = null;

            //Incrementa a string para consutar a busca
            String query = "";
            if (id != null && !id.isEmpty()) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                query += " idEntidade=" + id;
            }
            if (desc != null && !desc.isEmpty()) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                query += " nome like '%" + desc + "%'";
            }

            if (!query.equals("")) {
                //System.out.println(query);
                list = AbstractDAO.consultar("Entidade", query);
            }

            //Lista os objetos encontrados na tabela
            if (list != null) {
                this.listarPesquisaNaTabela(list);

            } else {
                this.listarPesquisaNaTabela(null);

            }

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return false;
    }

    public void buscarTodos() {
        this.listarTodosElementosNaTabela();
    }

    public void limparBusca() {

        new ApagaElementosDaInterface().apagaJTextField(this.textBuscaCodigo, this.textBuscaDescricao);
        this.limparGUI();
        this.listarPesquisaNaTabela(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupPessoa = new javax.swing.ButtonGroup();
        panelBotoes = new javax.swing.JPanel();
        buttonCancelar = new javax.swing.JButton();
        buttonCadastro = new javax.swing.JButton();
        buttonAlterar = new javax.swing.JButton();
        buttonExcluir = new javax.swing.JButton();
        buttonLimpar = new javax.swing.JButton();
        buttonImprimir = new javax.swing.JButton();
        panelInformacoes = new javax.swing.JPanel();
        textLogradouro = new javax.swing.JTextField();
        textFantasia = new javax.swing.JTextField();
        textNome = new javax.swing.JTextField();
        labelNome = new javax.swing.JLabel();
        labelCodigo = new javax.swing.JLabel();
        labelFantasia = new javax.swing.JLabel();
        labelCpf = new javax.swing.JLabel();
        labelCep = new javax.swing.JLabel();
        labelCidade = new javax.swing.JLabel();
        textBairro = new javax.swing.JTextField();
        textCpf = new javax.swing.JTextField();
        textCep = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("#####-###"))
        ;
        comboEstado = new javax.swing.JComboBox();
        textCidade = new javax.swing.JTextField();
        textInsEstadual = new javax.swing.JTextField();
        labelInsMunicipal = new javax.swing.JLabel();
        labelEstado = new javax.swing.JLabel();
        labelLogradouro = new javax.swing.JLabel();
        labelTelefone = new javax.swing.JLabel();
        labelNumero = new javax.swing.JLabel();
        labelCelular = new javax.swing.JLabel();
        labelBairro = new javax.swing.JLabel();
        textTelefone = new javax.swing.JTextField();
        textCodigo = new javax.swing.JTextField();
        textCelular = new javax.swing.JTextField();
        labelInsEstadual = new javax.swing.JLabel();
        checkCliente = new javax.swing.JCheckBox();
        checkFornecedor = new javax.swing.JCheckBox();
        radioPesFisica = new javax.swing.JRadioButton();
        radioPesJuridica = new javax.swing.JRadioButton();
        textFax = new javax.swing.JTextField();
        labelCnpj = new javax.swing.JLabel();
        textCnpj = new javax.swing.JTextField();
        textNumero = new javax.swing.JTextField();
        panelTabela = new javax.swing.JPanel();
        scrollTabela = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        panelMensagem = new javax.swing.JPanel();
        textMensagem = new javax.swing.JTextField();
        panelBusca = new javax.swing.JPanel();
        labelBuscaDesc1 = new javax.swing.JLabel();
        buttonBuscar = new javax.swing.JButton();
        buttonLimparBusca = new javax.swing.JButton();
        textBuscaDescricao = new javax.swing.JTextField();
        labelBuscaCodigo1 = new javax.swing.JLabel();
        textBuscaCodigo = new JTextFieldCodigo(8, false);

        groupPessoa.add(radioPesFisica);
        groupPessoa.add(radioPesJuridica);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Entidades");
        setToolTipText("");

        panelBotoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opções", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

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

        buttonExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Excluir.png"))); // NOI18N
        buttonExcluir.setText("Excluir");
        buttonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExcluirActionPerformed(evt);
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

        javax.swing.GroupLayout panelBotoesLayout = new javax.swing.GroupLayout(panelBotoes);
        panelBotoes.setLayout(panelBotoesLayout);
        panelBotoesLayout.setHorizontalGroup(
            panelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotoesLayout.createSequentialGroup()
                .addComponent(buttonCadastro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonLimpar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonImprimir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBotoesLayout.setVerticalGroup(
            panelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotoesLayout.createSequentialGroup()
                .addGroup(panelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonCadastro)
                    .addGroup(panelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonExcluir)
                        .addComponent(buttonAlterar))
                    .addGroup(panelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonLimpar)
                        .addComponent(buttonImprimir)
                        .addComponent(buttonCancelar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelInformacoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações e Pesquisa", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        textNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNomeActionPerformed(evt);
            }
        });

        labelNome.setText("Nome:");

        labelCodigo.setText("Codigo:");

        labelFantasia.setText("Fantasia:");

        labelCpf.setText("CPF:");

        labelCep.setText("CEP:");

        labelCidade.setText("Cidade:");

        textCpf = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("###.###.###-##"));
        ((JFormattedTextField)textCpf).setFocusLostBehavior(JFormattedTextField.COMMIT);

        ((JFormattedTextField)textCep).setFocusLostBehavior(JFormattedTextField.COMMIT);
        ((JFormattedTextField)textCep).setHorizontalAlignment(textCep.LEFT);

        comboEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));

        textInsEstadual = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("###.###.###-##-##"));
        ((JFormattedTextField)textInsEstadual).setFocusLostBehavior(JFormattedTextField.COMMIT);

        labelInsMunicipal.setText("Fax:");

        labelEstado.setText("Estado:");

        labelLogradouro.setText("Logradouro:");

        labelTelefone.setText("Telefone:");

        labelNumero.setText("Número:");

        labelCelular.setText("Celular:");

        labelBairro.setText("Bairro:");

        textTelefone = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("(##)####-####"));
        ((JFormattedTextField)textTelefone).setFocusLostBehavior(JFormattedTextField.COMMIT);

        textCodigo.setEnabled(false);

        textCelular = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("(##)####-####"));
        ((JFormattedTextField)textCelular).setFocusLostBehavior(JFormattedTextField.COMMIT);

        labelInsEstadual.setText("Inscrição Estadual:");

        checkCliente.setText("Cliente");
        checkCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkClienteActionPerformed(evt);
            }
        });

        checkFornecedor.setText("Fornecedor");

        groupPessoa.add(radioPesFisica);
        radioPesFisica.setText("Pessoa Física");
        radioPesFisica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioPesFisicaActionPerformed(evt);
            }
        });

        groupPessoa.add(radioPesJuridica);
        radioPesJuridica.setText("Pessoa Jurídica");
        radioPesJuridica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioPesJuridicaActionPerformed(evt);
            }
        });

        textFax = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("(##)####-####"));
        ((JFormattedTextField)textFax).setFocusLostBehavior(JFormattedTextField.COMMIT);

        labelCnpj.setText("CNPJ:");

        textCnpj = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("##.###.###/####-##"));
        ((JFormattedTextField)textCnpj).setFocusLostBehavior(JFormattedTextField.COMMIT);


        javax.swing.GroupLayout panelInformacoesLayout = new javax.swing.GroupLayout(panelInformacoes);
        panelInformacoes.setLayout(panelInformacoesLayout);
        panelInformacoesLayout.setHorizontalGroup(
            panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacoesLayout.createSequentialGroup()
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCpf)
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addComponent(labelTelefone)
                        .addGap(18, 18, 18)
                        .addComponent(textTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addComponent(labelCnpj)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelInsEstadual)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textInsEstadual)
                        .addContainerGap())
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addComponent(labelCelular)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textCelular)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelInsMunicipal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFax, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(140, 140, 140))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInformacoesLayout.createSequentialGroup()
                .addComponent(labelCodigo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelInformacoesLayout.createSequentialGroup()
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInformacoesLayout.createSequentialGroup()
                                .addComponent(checkCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(checkFornecedor))
                            .addGroup(panelInformacoesLayout.createSequentialGroup()
                                .addComponent(radioPesFisica)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(radioPesJuridica)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelLogradouro)
                            .addComponent(labelCidade)
                            .addComponent(labelBairro))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInformacoesLayout.createSequentialGroup()
                                .addComponent(textCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelInformacoesLayout.createSequentialGroup()
                                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(textLogradouro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                                    .addComponent(textCidade, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textBairro))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelEstado)
                                    .addComponent(labelNumero)
                                    .addComponent(labelCep))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textNumero)
                                    .addComponent(textCep)
                                    .addComponent(comboEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addComponent(labelFantasia)
                        .addGap(18, 18, 18)
                        .addComponent(textFantasia))
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addComponent(labelNome)
                        .addGap(32, 32, 32)
                        .addComponent(textNome)))
                .addContainerGap())
        );
        panelInformacoesLayout.setVerticalGroup(
            panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInformacoesLayout.createSequentialGroup()
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkFornecedor)
                    .addComponent(checkCliente))
                .addGap(3, 3, 3)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioPesJuridica)
                    .addComponent(radioPesFisica))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCodigo)
                    .addComponent(textCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNome)
                    .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelFantasia)
                    .addComponent(textFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelCidade)
                            .addComponent(textCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelLogradouro)
                            .addComponent(textLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelBairro)
                            .addComponent(textBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInformacoesLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelNumero)
                                    .addComponent(textNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelEstado)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelCep)
                            .addComponent(textCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCnpj)
                    .addComponent(textCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelInsEstadual)
                    .addComponent(textInsEstadual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCpf))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textFax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelInsMunicipal))
                    .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelCelular)
                        .addComponent(textTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelTelefone)))
                .addGap(74, 74, 74))
        );

        panelTabela.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Entidades existentes", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        tabela.setAutoCreateRowSorter(true);
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod", "Nome"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollTabela.setViewportView(tabela);
        tabela.getColumnModel().getColumn(0).setResizable(false);
        tabela.getColumnModel().getColumn(1).setResizable(false);

        javax.swing.GroupLayout panelTabelaLayout = new javax.swing.GroupLayout(panelTabela);
        panelTabela.setLayout(panelTabelaLayout);
        panelTabelaLayout.setHorizontalGroup(
            panelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollTabela)
        );
        panelTabelaLayout.setVerticalGroup(
            panelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollTabela, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
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

        labelBuscaDesc1.setText("Nome:");

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

        labelBuscaCodigo1.setText("Codigo:");

        textBuscaCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textBuscaCodigoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBuscaLayout = new javax.swing.GroupLayout(panelBusca);
        panelBusca.setLayout(panelBuscaLayout);
        panelBuscaLayout.setHorizontalGroup(
            panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBuscaLayout.createSequentialGroup()
                .addComponent(labelBuscaCodigo1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textBuscaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelBuscaDesc1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textBuscaDescricao)
                .addGap(135, 135, 135))
            .addGroup(panelBuscaLayout.createSequentialGroup()
                .addComponent(buttonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonLimparBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelBuscaLayout.setVerticalGroup(
            panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBuscaLayout.createSequentialGroup()
                .addGroup(panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textBuscaDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelBuscaDesc1)
                    .addComponent(textBuscaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelBuscaCodigo1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonBuscar)
                    .addComponent(buttonLimparBusca)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBusca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelInformacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTabela, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCadastroActionPerformed
        int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja cadastrar " + textNome.getText()
                + "?", "Cadastrar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesOpcoes, botoesOpcoes[0]);

        try {

            if (opcao == JOptionPane.YES_OPTION) {

                if (textCodigo.getText().isEmpty()) {

                    if ((radioPesFisica.isSelected() || radioPesJuridica.isSelected()) && !textNome.getText().equals("")) {

                        if ((!checkCliente.isSelected()) && (!checkFornecedor.isSelected())) {
                            JOptionPane.showMessageDialog(this, "Escolha se a entidade é cliente, fornecedor ou ambos.",
                                    "Atenção.", JOptionPane.WARNING_MESSAGE);

                        } else if (verificaMascaras()) {

                            dao.inserir(criaEntidade());
                            this.limparGUI();
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "Pelo menos estas condições devem ser satisfeitas para efetuar um novo cadastro:"
                                + "\n-Escolha se a entidade é pessoa Física ou Jurídica."
                                + "\n-Defina um nome para a entidade.", "Atenção!", JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Esta entidade já existe portanto não é possível cadastrá-lo(a)!", "Atenção!", JOptionPane.WARNING_MESSAGE);
                }
                buscar();

            }
        } catch (NumberFormatException er) {
            JOptionPane.showMessageDialog(this, "Valor numérico digitado de forma inválida, favor tentar novamente!\n" + er, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_buttonCadastroActionPerformed

    private void checkClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkClienteActionPerformed

    private void textNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNomeActionPerformed
        List<Entidade> list = dao.consultar("nome like\'%" + textNome.getText() + "%\'");
        listarPesquisaNaTabela(list);
    }//GEN-LAST:event_textNomeActionPerformed

    private void buttonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAlterarActionPerformed
        int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja alterar esta entidade?", "Alterar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesOpcoes, botoesOpcoes[0]);
        if (opcao == JOptionPane.YES_OPTION) {
            if (!textCodigo.getText().equals("")) {
                Entidade e = criaEntidade();
                e.setIdEntidade(Integer.parseInt(textCodigo.getText()));
                dao.alterar(e);
                this.limparGUI();

            } else {
                JOptionPane.showMessageDialog(this, "Escolha uma entidade na tabela para alterar.",
                        "Atenção!", JOptionPane.WARNING_MESSAGE);
            }
            buscar();
        }
    }//GEN-LAST:event_buttonAlterarActionPerformed

    private void buttonImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonImprimirActionPerformed

        InputStream inputStream = null;
        Map parametros = new HashMap();

        if (!textCodigo.getText().isEmpty()) {

            if (radioPesFisica.isSelected()) {
                inputStream = getClass().getResourceAsStream("/relatorios/EntidadeUnicaPessoaFisica.jasper");

            } else if (radioPesJuridica.isSelected()) {
                inputStream = getClass().getResourceAsStream("/relatorios/EntidadeUnicaPessoaJuridica.jasper");
            }

            try {
                FileInputStream imagem = new FileInputStream(new java.io.File("logo.png"));
                parametros.put("Parametro_idEntidade", Integer.parseInt(textCodigo.getText()));
                parametros.put("imagem", imagem);
                parametros.put("nome", ControleFPreferencias.conf.getNome());

                ReportUtils.openReport("Entidade", inputStream, parametros, ConnectionFactory_brittos_bd.getConnection());

            } catch (Exception er) {

                JOptionPane.showMessageDialog(this, "Erro ao criar o relatório.\n" + er, "Erro", JOptionPane.ERROR_MESSAGE);
                er.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Selecione um item na tabela para imprimir.", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_buttonImprimirActionPerformed

    private void radioPesFisicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioPesFisicaActionPerformed
        enabledGui.disabledJComponent(textInsEstadual, textCnpj);
        enabledGui.enabledJComponent(textCpf);
        apaga.apagaJTextField(textInsEstadual, textCnpj);

    }//GEN-LAST:event_radioPesFisicaActionPerformed

    private void radioPesJuridicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioPesJuridicaActionPerformed
        enabledGui.enabledJComponent(textInsEstadual, textCnpj);
        enabledGui.disabledJComponent(textCpf);
        apaga.apagaJTextField(textCpf);

    }//GEN-LAST:event_radioPesJuridicaActionPerformed

    private void buttonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExcluirActionPerformed
        int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir esta(s) entidade(s)?", "Excluir",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesOpcoes, botoesOpcoes[0]);

        if (opcao == JOptionPane.YES_OPTION) {

            if (!textCodigo.getText().equals("")) {

                int[] selectedRows = tabela.getSelectedRows();

                for (int i = 0; i < selectedRows.length; i++) {

                    selectedRows[i] = (Integer) tabela.getValueAt(selectedRows[i], 0);

                }

                dao.excluirVarios(selectedRows);
                this.limparGUI();
            } else {

                JOptionPane.showMessageDialog(this, "Escolha pelo menos uma entidade na tabela para excluir.",
                        "Atenção!", JOptionPane.WARNING_MESSAGE);

            }
            buscar();
        }
}//GEN-LAST:event_buttonExcluirActionPerformed

    private void buttonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBuscarActionPerformed
        this.buscar();
    }//GEN-LAST:event_buttonBuscarActionPerformed

    private void buttonLimparBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLimparBuscaActionPerformed
        this.limparBusca();
    }//GEN-LAST:event_buttonLimparBuscaActionPerformed

    private void textBuscaDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textBuscaDescricaoActionPerformed
        this.buscar();
    }//GEN-LAST:event_textBuscaDescricaoActionPerformed

    private void textBuscaCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textBuscaCodigoActionPerformed
        this.buscar();
    }//GEN-LAST:event_textBuscaCodigoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAlterar;
    public javax.swing.JButton buttonBuscar;
    private javax.swing.JButton buttonCadastro;
    private javax.swing.JButton buttonCancelar;
    private javax.swing.JButton buttonExcluir;
    private javax.swing.JButton buttonImprimir;
    private javax.swing.JButton buttonLimpar;
    public javax.swing.JButton buttonLimparBusca;
    private javax.swing.JCheckBox checkCliente;
    private javax.swing.JCheckBox checkFornecedor;
    private javax.swing.JComboBox comboEstado;
    private javax.swing.ButtonGroup groupPessoa;
    private javax.swing.JLabel labelBairro;
    private javax.swing.JLabel labelBuscaCodigo1;
    private javax.swing.JLabel labelBuscaDesc1;
    private javax.swing.JLabel labelCelular;
    private javax.swing.JLabel labelCep;
    private javax.swing.JLabel labelCidade;
    private javax.swing.JLabel labelCnpj;
    private javax.swing.JLabel labelCodigo;
    private javax.swing.JLabel labelCpf;
    private javax.swing.JLabel labelEstado;
    private javax.swing.JLabel labelFantasia;
    private javax.swing.JLabel labelInsEstadual;
    private javax.swing.JLabel labelInsMunicipal;
    private javax.swing.JLabel labelLogradouro;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelNumero;
    private javax.swing.JLabel labelTelefone;
    private javax.swing.JPanel panelBotoes;
    private javax.swing.JPanel panelBusca;
    private javax.swing.JPanel panelInformacoes;
    private javax.swing.JPanel panelMensagem;
    private javax.swing.JPanel panelTabela;
    private javax.swing.JRadioButton radioPesFisica;
    private javax.swing.JRadioButton radioPesJuridica;
    private javax.swing.JScrollPane scrollTabela;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField textBairro;
    public javax.swing.JTextField textBuscaCodigo;
    public javax.swing.JTextField textBuscaDescricao;
    private javax.swing.JTextField textCelular;
    private javax.swing.JTextField textCep;
    private javax.swing.JTextField textCidade;
    private javax.swing.JTextField textCnpj;
    private javax.swing.JTextField textCodigo;
    private javax.swing.JTextField textCpf;
    private javax.swing.JTextField textFantasia;
    private javax.swing.JTextField textFax;
    private javax.swing.JTextField textInsEstadual;
    private javax.swing.JTextField textLogradouro;
    private javax.swing.JTextField textMensagem;
    private javax.swing.JTextField textNome;
    private javax.swing.JTextField textNumero;
    private javax.swing.JTextField textTelefone;
    // End of variables declaration//GEN-END:variables
}
