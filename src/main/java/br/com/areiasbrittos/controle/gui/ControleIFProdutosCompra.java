/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.controle.gui;

import br.com.areiasbrittos.controle.objetos.*;
import br.com.areiasbrittos.controle.objetos.seguranca.Transacao;
import br.com.areiasbrittos.controle.utils.Dates;
import br.com.areiasbrittos.controle.utils.Horas;
import br.com.areiasbrittos.controle.utils.ReportUtils;
import br.com.areiasbrittos.gui.FramePrincipal;
import br.com.areiasbrittos.gui.internalFrames.InternalFrameAddProduto;
import br.com.areiasbrittos.gui.internalFrames.InternalFrameProdutosCompra;
import br.com.areiasbrittos.gui.utils.*;
import br.com.areiasbrittos.gui.utils.jtable.RendererTable;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import br.com.areiasbrittos.persistencia.ConnectionFactory_brittos_bd;
import br.com.areiasbrittos.persistencia.dao.AbstractDAO;
import br.com.areiasbrittos.persistencia.dao.DAOEntidade;
import br.com.areiasbrittos.persistencia.dao.DAOCompra;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOTransacao;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class ControleIFProdutosCompra {

    /*
     * Variáveis da classe
     */
    private DAOEntidade entDao = new DAOEntidade();
    private InternalFrameProdutosCompra frame;
    private ApagaElementosDaInterface apaga = new ApagaElementosDaInterface();
    private DecimalFormat df = new DecimalFormat("###,##0.00");
    private Boolean pagamentoAPrazo = false;

    public ControleIFProdutosCompra(InternalFrameProdutosCompra frame) {
        this.frame = frame;
    }

    /**
     * Inicializa o InternalFrame
     *
     * @param frame
     */
    public void inicializa() {

        //Verifica caixa diário
        Caixadiario.verificaCaixasBd();

        TamanhoJTextField limita = new TamanhoJTextField();
        final int[] tamanhosJTextField = {45};

        //Limita a quantidade de caracteres possíveis em textMotorista
        limita.limitaTamanho(tamanhosJTextField, frame.textMotorista);

        //Inicializa o ComboBox de clientes
        frame.comboFornecedor.setModel(entDao.inicializaComboBoxEntidades("Selecione...", "Fornecedor"));
        frame.comboBuscaCliente.setModel(AbstractDAO.inicializaComboBox("Selecione...", "Entidade where tipo like '%Fornecedor%' order by nome"));

        //Inicializa TextFields
        this.inicializaTextData();
        this.inicializaTextVencimento();
        frame.textMensagem.setEditable(false);
        frame.textMensagem.setText("Aqui são registradas as compras ou entrada de produtos.");
        frame.textValor.setText(0 + "");

        //Inicializa eventos da tabela
        frame.tabela.getSelectionModel().addListSelectionListener(frame.listener);
        frame.tabela.getColumnModel().getSelectionModel().addListSelectionListener(frame.listener);
        this.getModelTabelaProdutos();

        //Configura o comportamento dos botões da interface
        this.verificaAcessos();
        EnabledGUIElements.enabledJComponent(frame.buttonCadastro);
        EnabledGUIElements.disabledJComponent(frame.buttonAlterar, frame.buttonImprimir, frame.buttonConcluir, frame.buttonDesconcluir, frame.buttonQuitar);

    }

    /**
     * Verifica o acesso do usuário para utilizar este frame
     */
    public void verificaAcessos() {

        /*
         * Verifica a permissao de @user para este frame
         */
        if (!FramePrincipal.acessos.get(FramePrincipal.PERMISSAO_ALTERA_COMPRA).isPermissao()) {
            EnabledGUIElements.disabledJComponent(frame.buttonCadastro, frame.buttonAlterar, frame.buttonConcluir);

        }

        if (!FramePrincipal.acessos.get(FramePrincipal.PERMISSAO_DESCONCLUI_COMPRA).isPermissao()) {
            EnabledGUIElements.disabledJComponent(frame.buttonDesconcluir);
        }
    }

    /**
     * Determina o comportamento do InternalFrame
     *
     * @param concluida
     */
    public void guiAtiva(boolean concluida) {

        //Verifica se a compra consultada esta concluida e configura o comportamento dos botoes
        if (concluida) {
            EnabledGUIElements.disabledJComponent(frame.textMotorista,
                    frame.textPlaca, frame.textValor, frame.textVencimento, frame.buttonAlterar,
                    frame.buttonConcluir, frame.comboFornecedor, frame.comboPagamento,
                    frame.buttonInserir, frame.buttonRemover);
            EnabledGUIElements.enabledJComponent(frame.buttonDesconcluir);
            frame.textMensagem.setText("Esta compra encontra-se concluida.");
        } else {
            EnabledGUIElements.enabledJComponent(frame.textMotorista, frame.textVencimento,
                    frame.textPlaca, frame.textValor, frame.buttonAlterar,
                    frame.buttonConcluir, frame.comboFornecedor, frame.comboPagamento,
                    frame.buttonInserir, frame.buttonRemover);
            EnabledGUIElements.disabledJComponent(frame.buttonDesconcluir);

            frame.textMensagem.setText("Esta compra não se encontra concluida.");
        }

        this.verificaAcessos();
    }

    /**
     * Preenche o InternalFrame a partir da iteração com a tabela
     */
    public void tabelaPreencheGUI() {

        try {
            int linha = frame.tabela.getSelectedRow();

            Integer cod;
            try {
                cod = (Integer) frame.tabela.getValueAt(linha, 0);
            } catch (IndexOutOfBoundsException er) {
                cod = 0;
            }

            List<Compra> consulta = AbstractDAO.consultar("from Compra c INNER JOIN FETCH c.entidade e LEFT JOIN FETCH c.compraHasProdutos h "
                    + " LEFT JOIN FETCH h.produto WHERE " + " c.idCompra=" + cod);

            if (consulta.size() > 0) {
                Compra c = consulta.get(0);
                this.preencheGUI(c);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            // ex.printStackTrace();
        }

    }

    /**
     * Preenche os componentes da gui a partir de um objeto
     */
    public void preencheGUI(Compra c) {

        frame.textCodigo.setText(Integer.toString(c.getIdCompra()));
        frame.textDataCriacao.setText(ConsertaBugsGUI.getDataFormatadaNoBd(c.getDataCriacao(), "dd/MM/yyyy"));
        frame.textHoraCriacao.setText(c.getHoraCriacao().toString());
        frame.comboFornecedor.setSelectedItem(c.getEntidade().getNome());
        frame.textMotorista.setText(c.getMotorista());


        //Verifica o preenchimento de textPlaca
        if (c.getPlaca().equals("   -    ")) {
            frame.textPlaca.setText("");
        } else {
            frame.textPlaca.setText(c.getPlaca());
        }

        //Preenche a tabela de produtos
        this.setTabelaProdutos(c);

        //Configura o comportamento dos botões da interface
        EnabledGUIElements.disabledJComponent(frame.buttonCadastro);
        EnabledGUIElements.enabledJComponent(frame.buttonAlterar, frame.buttonImprimir);

        if (c.getDataCriacao().equals(c.getVencimento())) {
            frame.comboPagamento.setSelectedIndex(0);
        } else {
            frame.comboPagamento.setSelectedIndex(1);
        }
        frame.textVencimento.setText(ConsertaBugsGUI.getDataFormatadaNoBd(c.getVencimento(), "dd/MM/yyyy"));

        //Reconfigura o valor total
        frame.textValor.setText(c.getValorTotal().toString());

        //Verifica se a Compra já foi quitada
        if (c.isQuitada()) {
            ArrayList<Contaspagar> conta = (ArrayList<Contaspagar>) AbstractDAO.consultar("Contaspagar", "idCompra=" + c.getIdCompra());
            if (conta.size() > 0) {
                frame.radioQuitada.setSelected(true);
                frame.textPagamentoDia.setText(ConsertaBugsGUI.getDataFormatadaNoBd(conta.get(0).getDataPagamento(), "dd/MM/yyyy"));
                frame.textPagamentoHora.setText(conta.get(0).getHoraPagamento().toString());
                frame.textPagamentoIdConta.setText(conta.get(0).getIdConta() + "");
            }

            frame.buttonQuitar.setEnabled(false);

        } else {
            frame.radioQuitada.setSelected(false);
            frame.textPagamentoDia.setText("");
            frame.textPagamentoHora.setText("");
            frame.textPagamentoIdConta.setText("");

            frame.buttonQuitar.setEnabled(true);
        }

        //Configura o comportamento da GUI caso a transação esteja concluída
        EnabledGUIElements.disabledJComponent(frame.buttonCadastro);
        EnabledGUIElements.enabledJComponent(frame.buttonAlterar, frame.buttonImprimir, frame.buttonConcluir, frame.buttonDesconcluir);
        if (c.isConcluida()) {
            this.guiAtiva(true);
            EnabledGUIElements.disabledJComponent(frame.buttonConcluir);
        } else {
            this.guiAtiva(false);
            EnabledGUIElements.disabledJComponent(frame.buttonDesconcluir);
        }
        if (c.isQuitada()) {
            EnabledGUIElements.disabledJComponent(frame.buttonDesconcluir);
        }
    }

    /**
     * Preenche a tabela de produtos a partir de uma determinada compra
     */
    public void setTabelaProdutos(Compra compra) {

        DefaultTableModel modelo = this.getModelTabelaProdutos();
        modelo.setNumRows(0);
        
        //Verifica se há algum a compra de algum produto registrado
        if (compra.getCompraHasProdutos().size() > 0) {
            
            //Percorre cada produto vendido e preenche cada linha da tabela de produtos
            for (Iterator it = compra.getCompraHasProdutos().iterator(); it.hasNext();) {

                CompraHasProduto obj = (CompraHasProduto) it.next();
                modelo.addRow(new Object[]{obj.getIdCompraHasProduto(), obj.getProduto().getNome(), obj.getTara(), obj.getPesoBruto(), obj.getPesoLiquido(), obj.getValorUnitario(), obj.getValorTotal().toString()});
            }
        }
    }

    /**
     * Captura os CompraHasProduto da tabela de produtos da compra consultada
     */
    public HashSet getTabelaProdutos(Compra compra) {

        HashSet chp = new HashSet();
        
        //Captura cada linha da tabela de produtos e instancia cada produto comprado
        DefaultTableModel modelo = (DefaultTableModel) frame.tabelaProdutos.getModel();
        
        //Verifica se a tabela foi preenchida
        if (frame.tabelaProdutos.getModel().getRowCount() > 0) {
            int contaLinha = 0;
            /*
             * Percorre todas as linhas criadas na tabela
             */
            while (contaLinha < modelo.getRowCount()) {

                /*
                 * Percorre cada coluna da linha para instanciar um novo
                 * CompraHasProduto
                 */
                Produto produto = (Produto) AbstractDAO.consultar("Produto", "nome='" + modelo.getValueAt(contaLinha, 1).toString() + "'").get(0);
                int tara = Integer.parseInt(modelo.getValueAt(contaLinha, 2).toString().replaceAll(" ", ""));
                int bruto = Integer.parseInt(modelo.getValueAt(contaLinha, 3).toString().replaceAll(" ", ""));
                int liquido = Integer.parseInt(modelo.getValueAt(contaLinha, 4).toString().replaceAll(" ", ""));
                BigDecimal valorUnitario = new BigDecimal(modelo.getValueAt(contaLinha, 5).toString().replaceAll(",", "\\."));
                BigDecimal valorTotal = new BigDecimal(modelo.getValueAt(contaLinha, 6).toString().replaceAll(",", "\\."));

                /*
                 * Instancia o registro
                 */
                CompraHasProduto compraProduto = new CompraHasProduto(compra, produto, tara, bruto, liquido, valorUnitario, valorTotal);

                /*
                 * Verifica se os registros tem id's
                 */
                if (modelo.getValueAt(contaLinha, 0) != null) {
                    int cod = Integer.parseInt(modelo.getValueAt(contaLinha, 0).toString());
                    compraProduto.setIdCompraHasProduto(cod);
                }

                chp.add(compraProduto);
                contaLinha++;
            }
        }

        return chp;
    }

    /**
     * Cria um objeto compra a partir dos dados fornecidos aos elementos deste
     * frame
     *
     * @param data - data que será armazenada com o objeto
     * @return - retorna o objeto Compra obtido pelo frame
     * @throws NumberFormatException
     */
    public Compra criaCompra() throws NumberFormatException {

        /*
         * Captura os atributos do InternalFrame e inicializa as variáveis com
         * seus devidos valores
         */
        BigDecimal valor = new BigDecimal(frame.textValor.getText().replaceAll("\\.", "").replaceAll(",", "\\."));
        Entidade entidade = entDao.getEntidade(frame.comboFornecedor.getSelectedItem().toString());
        String placa = frame.textPlaca.getText();
        String motorista = frame.textMotorista.getText();
        String pagamento = frame.comboPagamento.getSelectedItem().toString();
        Date dataCriacao = null;
        Time horaCriacao = null;
        Date dataAlteracao = Dates.getDataHoje();
        Time horaAlteracao = Horas.getHoraAgora();
        Date vencimento = null;

        try {
            /*
             * Verifica se a data e a hora de criação já foram preenchidos
             */
            if (frame.textDataCriacao.getText().isEmpty()) {
                dataCriacao = Dates.getDataHoje();
            } else {
                dataCriacao = Dates.getDate(frame.textDataCriacao.getText());
            }

            if (frame.textHoraCriacao.getText().isEmpty()) {
                horaCriacao = Horas.getHoraAgora();
            } else {
                horaCriacao = Horas.getTime(frame.textHoraCriacao.getText());
            }

            vencimento = new Date(new SimpleDateFormat("dd/MM/yyyy").parse(frame.textVencimento.getText()).getTime());

            vencimento = Dates.verificaDigitosAno(vencimento);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao manipular datas.", "Erro", JOptionPane.ERROR_MESSAGE);
            vencimento = new Date(System.currentTimeMillis());

            return null;
        }

        /*
         * Cria a compra
         */
        Compra compra = new Compra(entidade, dataCriacao, horaCriacao, dataAlteracao, horaAlteracao, placa, valor, vencimento, false, motorista, pagamento, false);


        //Verifica se a GUI contem o id da compra
        if (ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(frame.textCodigo) > 0) {
            compra.setIdCompra(ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(frame.textCodigo));
        }

        /*
         * Registra os produtos vendidos na compra registrados na tabela de
         * produtos
         */
        compra.setCompraHasProdutos(this.getTabelaProdutos(compra));

        /*
         * Retorna a nova compra
         */
        return compra;
    }

    /**
     * Limpa a gui
     */
    public void limparGUI() {

        this.buscar();
        frame.tabelaProdutos.clearSelection();
        this.getModelTabelaProdutos().setNumRows(0);
        frame.comboFornecedor.setSelectedIndex(0);
        frame.comboPagamento.setSelectedIndex(0);
        frame.radioAberto.setSelected(true);
        apaga.apagaJTextField(frame.textCodigo, frame.textDataCriacao, frame.textHoraCriacao, frame.textPlaca, frame.textValor, frame.textVencimento,
                frame.textMotorista, frame.textPagamentoDia, frame.textPagamentoHora, frame.textPagamentoIdConta);

        this.inicializaTextData();
        this.inicializaTextVencimento();

        /**
         * Torna a GUI completamente editável novamente*
         */
        this.guiAtiva(false);
        EnabledGUIElements.enabledJComponent(frame.buttonCadastro);
        EnabledGUIElements.disabledJComponent(frame.buttonAlterar, frame.buttonImprimir, frame.buttonConcluir, frame.buttonDesconcluir, frame.buttonQuitar);

        frame.buttonCadastro.setEnabled(true);
        frame.buttonAlterar.setEnabled(false);

        this.verificaAcessos();
    }

    /**
     * Inicializa o TextField para data*
     */
    public void inicializaTextData() {

        Date data = new Date(System.currentTimeMillis());
        DateFormat hoje = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("pt", "BR"));

        frame.textDataCriacao.setText(hoje.format(data));
    }

    /**
     * Inicializa o TextField para vencimento*
     */
    public void inicializaTextVencimento() {

        Date data = new Date(System.currentTimeMillis());
        DateFormat hoje = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("pt", "BR"));

        frame.textVencimento.setText(hoje.format(data));

    }

    /**
     * Verifica se o valor em @textTara é maior do que @textPesoBruto, caso
     * positivo retorna true e em caso negativo retorna falso
     */
    public boolean verificaTaraPesoBruto(Compra compra) {

        /*
         * Verifica todas as linhas da tabela de produtos
         */
        DefaultTableModel modelo = (DefaultTableModel) frame.tabelaProdutos.getModel();

        if (modelo.getRowCount() > 0) {
            HashSet set = this.getTabelaProdutos(compra);

            for (Iterator it = set.iterator(); it.hasNext();) {
                CompraHasProduto chp = (CompraHasProduto) it.next();
                if ((chp.getTara() > chp.getPesoBruto()) || chp.getPesoLiquido() < 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * A partir dos valores totais de cada produto comprado, soma
     * automaticamente o valor total da compra
     */
    public void somaValorTotal() {

        //Captura o modelo da tabela de produtos
        DefaultTableModel modelo = (DefaultTableModel) frame.tabelaProdutos.getModel();

        //Soma o valor total com o valor de cada linha
        BigDecimal valorTotal = new BigDecimal(BigInteger.ZERO);
        for (int index = 0; index < modelo.getRowCount(); index++) {
            valorTotal = valorTotal.add(new BigDecimal(modelo.getValueAt(index, 6).toString()));
        }

        //Escreve o valor total no textField
        frame.textValor.setText(valorTotal.toString());

    }

    public void listarTodosElementosNaTabela() {

        List<Compra> list = AbstractDAO.listar("Compra", "dataAlteracao", "horaAlteracao");
        listarPesquisaNaTabela(list);

    }

    /**
     * Lista na tabela os objetos em @list
     *
     * @param list - Lista de objetos
     */
    public void listarPesquisaNaTabela(List<Compra> list) {

        //Inicializa as colunas da tabela
        TableColumn coluna1 = frame.tabela.getColumnModel().getColumn(0);
        TableColumn coluna2 = frame.tabela.getColumnModel().getColumn(1);
        TableColumn coluna3 = frame.tabela.getColumnModel().getColumn(2);
        TableColumn coluna4 = frame.tabela.getColumnModel().getColumn(3);
        TableColumn coluna5 = frame.tabela.getColumnModel().getColumn(4);

        //Dimensiona as colunas na tabela
        coluna1.setPreferredWidth(50);
        coluna2.setPreferredWidth(70);
        coluna3.setPreferredWidth(500);
        coluna4.setPreferredWidth(60);
        coluna5.setPreferredWidth(90);

        //Inicializa o modelo declarado acima
        DefaultTableModel modelo = (DefaultTableModel) frame.tabela.getModel();
        modelo.setNumRows(0);

        //Inicializa o DefaultTableCellRender modificado para personalizar algumas células
        DefaultTableCellRenderer renderer = new RendererTable(list);

        try {

            if (list != null) {
                //Preenche a tabela com os elementos de @list
                for (int i = 0; i < list.size(); i++) {
                    Compra c = list.get(i);
                    modelo.addRow(new Object[]{c.getIdCompra(), ConsertaBugsGUI.getDataFormatadaNoBd(c.getDataCriacao(), "dd/MM/yyyy"),
                                c.getEntidade().getNome(), c.isQuitada() ? "Sim" : "Não", ("R$ " + df.format(c.getValorTotal()))});

                    //Altera as propriedades das células desta linha de acordo com o estado de conclusão da transação atual*
                    coluna1.setCellRenderer(renderer);
                    coluna2.setCellRenderer(renderer);
                    coluna3.setCellRenderer(renderer);
                    coluna4.setCellRenderer(renderer);
                    coluna5.setCellRenderer(renderer);
                }
            }
        } catch (Exception er) {
            JOptionPane.showMessageDialog(frame, "Erro na listagem!\n", "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();

        }

    }

    /**
     * Lista os produtos comprados na tabela de produtos
     *
     * @param list
     */
    public void listarProdutosNaTabela(List<CompraHasProduto> list) {

        DefaultTableModel modelo = this.getModelTabelaProdutos();

        try {
            /**
             * Preenche a tabela com os elementos de @list*
             */
            for (int i = 0; i < list.size(); i++) {
                CompraHasProduto c = list.get(i);
                modelo.addRow(new Object[]{});

            }
        } catch (Exception er) {
            JOptionPane.showMessageDialog(frame, "Erro na listagem dos produtos!\n", "Erro!", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();

        }

    }

    /**
     * Soma dias em uma data
     *
     * @param hoje - data a ser somada
     * @param dias - dias a serem incrementados
     * @return -
     */
    public Date addDias(Date hoje, int dias) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(hoje);
        calendar.add(Calendar.DATE, dias);

        java.util.Date dataUtil = calendar.getTime();
        java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());

        return dataSql;
    }

    public void calculaTextVencimento() {

        BigDecimal total;
        Date vencimento = new Date(System.currentTimeMillis());

        /**
         * Caso o campo valor não esteja preenchido*
         */
        if (frame.textValor.getText().isEmpty()) {
            total = new BigDecimal(BigInteger.ZERO);

            //Caso o campo valor esteja preenchido*
        } else {

            total = (new BigDecimal(frame.textValor.getText().replaceAll("\\.", "").replaceAll(",", "\\.")));

        }

        //Caso seja definida uma transação a vista*
        if (frame.comboPagamento.getSelectedIndex() == 0) {

            if (pagamentoAPrazo) {
                //total = total.divide(new BigDecimal(1.1), RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
                pagamentoAPrazo = false;
            }

            //Caso seja definida uma transação a prazo*
        } else if (frame.comboPagamento.getSelectedIndex() == 1) {

            if (!pagamentoAPrazo) {
                pagamentoAPrazo = true;
            }
        }

        //Escreve o valor no campo textValor*
        frame.textValor.setText(total.toString());
        frame.textVencimento.setText(ConsertaBugsGUI.getDataFormatadaNoBd(vencimento, "dd/MM/yyyy"));

    }

    /**
     * Insere os produtos comprados/produzidos em estoque ao cadastra-los
     *
     * @return
     */
    public boolean insereProdutoEstoque(Compra compra) {

        Set<CompraHasProduto> chp = compra.getCompraHasProdutos();

        /*
         * Percorre todos os produtos da compra
         */
        for (Iterator it = chp.iterator(); it.hasNext();) {
            CompraHasProduto obj = (CompraHasProduto) it.next();

            /*
             * Captura o produto no bd e incrementa seu estoque
             */
            Produto produto = (Produto) AbstractDAO.consultar("Produto", "idProduto=" + obj.getProduto().getIdProduto()).get(0);
            int estoque = produto.getEstoque() + obj.getPesoLiquido();
            produto.setEstoque(estoque);

            /*
             * Altera o produto no bd
             */
            AbstractDAO.alterar(produto);

        }

        return true;

    }

    /**
     * Altera a qtd dos produtos comprados/produzidos em estoque ao altera-los
     *
     * @return
     */
    public boolean alteraProdutoEstoque(Compra compra) {

        Set<CompraHasProduto> chp = compra.getCompraHasProdutos();

        /*
         * Percorre todos os produtos da compra
         */
        for (Iterator it = chp.iterator(); it.hasNext();) {
            CompraHasProduto obj = (CompraHasProduto) it.next();
            Produto produto = (Produto) AbstractDAO.consultar("Produto", "idProduto=" + obj.getProduto().getIdProduto()).get(0);

            /*
             * Verifica se o chp não existe no bd e incrementa o produto no
             * estoque
             *
             * Caso exista o chp, altera a qtd em estoque
             */
            if (obj.getIdCompraHasProduto() == null) {
                produto.setEstoque(produto.getEstoque() + obj.getPesoLiquido());

            } else {
                /*
                 * Recupera o objeto do chp no bd e calcula o estoque do produto
                 * a partir dos valores anterior e atual do chp
                 */
                CompraHasProduto chpBd = (CompraHasProduto) AbstractDAO.consultar("CompraHasProduto", "idCompraHasProduto=" + obj.getIdCompraHasProduto()).get(0);
                produto.setEstoque(produto.getEstoque() - chpBd.getPesoLiquido());
                produto.setEstoque(produto.getEstoque() + obj.getPesoLiquido());

            }

            /*
             * Altera o produto no bd
             */
            AbstractDAO.alterar(produto);

        }

        return true;

    }

    public void cadastro() {
        try {
            /*
             * Pergunta se o cliente realmente deseja inserir a transação
             */
            int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja efetuar o cadastro desta compra?", "Cadastrar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    frame.botoesOpcoes, frame.botoesOpcoes[0]);

            /*
             * O cliente confirmou que deseja efetuar o cadastro
             */
            if (opcao == JOptionPane.YES_OPTION) {
                /*
                 * O campo código está vazio, ou seja, verifica se esta não é
                 * uma transação existente
                 */
                if (frame.textCodigo.getText().isEmpty()) {
                    /*
                     * Verifica se alguns campos já foram preenchidos*
                     */
                    if (!(frame.textDataCriacao.getText().equals("") || frame.textValor.getText().equals("")
                            || frame.textVencimento.getText().equals("") || frame.comboFornecedor.getSelectedIndex() == 0)) {
                        /*
                         * Realiza o cadastro no banco de dados*
                         */
                        if (this.criaCompra() != null) {
                            Compra c = this.criaCompra();
                            /*
                             * Verifica o peso da tara e o bruto
                             */
                            if (!this.verificaTaraPesoBruto(c)) {
                                if (AbstractDAO.inserir(c)) {
                                    /*
                                     * Preenche o campo com o código da nova
                                     * transação no banco de dados
                                     */
                                    List<Compra> list = AbstractDAO.listar("Compra");
                                    frame.textCodigo.setText(Integer.toString(list.get(list.size() - 1).getIdCompra()));

                                    //Registra a transação do usuário
                                    DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Cadastrou a Compra " + list.get(list.size() - 1).getIdCompra()));

                                    /*
                                     * Insere em estoque a quantidade dos
                                     * produtos comprados
                                     */
                                    this.insereProdutoEstoque(c);

                                    /*
                                     * Verifica opções para realizar impressão
                                     * da transação*
                                     */
                                    JOptionPane.showMessageDialog(frame, "Cadastro efetuado com sucesso!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
                                    this.imprimir();
                                    limparGUI();
                                }
                            } else {
                                JOptionPane.showMessageDialog(frame, "A tara não pode ser maior que o peso bruto.", "Atenção!", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Pelo menos estas condições devem ser satisfeitas para "
                                + "efetuar um novo cadastro:"
                                + "\n-Defina o cliente."
                                + "\n-Defina a placa."
                                + "\n-Defina a forma de pagamento."
                                + "\n-Defina o vencimento."
                                + "\n-Defina o valor total.", "Atenção!", JOptionPane.WARNING_MESSAGE);

                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Esta compra já existe portanto não é possível cadastrá-la,"
                            + "somente alterá-la!", "Atenção!", JOptionPane.WARNING_MESSAGE);

                }
                /**
                 * Relista as transações na tabela*
                 */
                buscar();


            }
        } catch (NumberFormatException er) {
            JOptionPane.showMessageDialog(frame, "Valor numérico digitado de forma inválida, favor tentar novamente!\n\n" + er,
                    "Erro", JOptionPane.ERROR_MESSAGE);
            apaga.apagaJTextField(frame.textValor);
            er.printStackTrace();

        }
    }

    public void alterar() {

        /*
         * Pergunta se o usuário deseja realizar a alteração
         */
        int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja alterar esta compra?", "Alterar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, frame.botoesOpcoes, frame.botoesOpcoes[0]);

        try {
            /**
             * Caso o usuário deseje alterar a transação*
             */
            if (opcao == JOptionPane.YES_OPTION) {
                /**
                 * Verifica se alguma transação foi selecionada e o campo do
                 * código está preenchido*
                 */
                if (!frame.textCodigo.getText().equals("")) {
                    /**
                     * Verifica se alguns campos já foram preenchidos*
                     */
                    if (!(frame.textDataCriacao.getText().equals("") || frame.textValor.getText().equals("")
                            || frame.textVencimento.getText().equals("") || frame.comboFornecedor.getSelectedIndex() == 0)) {
                        /*
                         * Verifica se o peso bruto não é menor do que a tara
                         */
                        Compra c = criaCompra();
                        if (!this.verificaTaraPesoBruto(c)) {
                            /*
                             * Captura o id da transação*
                             */
                            c.setIdCompra(Integer.parseInt(frame.textCodigo.getText()));

                            /*
                             * Compara as CompraHasProduto da compra alterada na
                             * Gui com as do bd e adiciona as encontradas em
                             * @idObjetosBdEncontrados
                             */
                            Set<CompraHasProduto> setGui = c.getCompraHasProdutos();
                            ArrayList<CompraHasProduto> listBd = (ArrayList<CompraHasProduto>) AbstractDAO.consultar("CompraHasProduto", "idCompra=" + c.getIdCompra());
                            ArrayList<Integer> idObjetosBdEncontrados = new ArrayList<Integer>();

                            for (Iterator itG = setGui.iterator(); itG.hasNext();) {
                                CompraHasProduto objG = (CompraHasProduto) itG.next();

                                for (int index = 0; index < listBd.size(); index++) {
                                    CompraHasProduto objB = listBd.get(index);

                                    if (objG.getIdCompraHasProduto() != null) {
                                        if (objG.getIdCompraHasProduto().equals(objB.getIdCompraHasProduto())) {
                                            idObjetosBdEncontrados.add(objB.getIdCompraHasProduto());
                                        }
                                    }
                                }
                            }

                            /*
                             * Compara todos ids dos chp dessa compra no bd com
                             * os ids em @idObjetosBdEncontrados e apaga do bd
                             * todos aqueles chps que não tem seu id em
                             * @idObjetosBdEncontrados
                             */
                            for (int indexBd = 0; indexBd < listBd.size(); indexBd++) {
                                Boolean encontrada = false;
                                for (int index = 0; index < idObjetosBdEncontrados.size(); index++) {
                                    if (idObjetosBdEncontrados.get(index) == listBd.get(indexBd).getIdCompraHasProduto()) {
                                        encontrada = true;
                                    }
                                }
                                if (!encontrada) {
                                    //Decrementa o estoque do produto de chp e salva no bd o novo estoque 
                                    Produto produto = (Produto) AbstractDAO.consultar("Produto", "idProduto=" + listBd.get(indexBd).getProduto().getIdProduto()).get(0);
                                    produto.setEstoque(produto.getEstoque() - listBd.get(indexBd).getPesoLiquido());
                                    AbstractDAO.alterar(produto);

                                    //Exclui a chp do bd 
                                    AbstractDAO.excluirPorId("CompraHasProduto", "idCompraHasProduto", listBd.get(indexBd).getIdCompraHasProduto());
                                }
                            }

                            /*
                             * Realiza a alteração no BD a partir do id*
                             */
                            c.setDataAlteracao(Dates.getDataHoje());
                            c.setHoraAlteracao(Horas.getHoraAgora());
                            this.alteraProdutoEstoque(c);
                            if (AbstractDAO.alterar(c)) {

                                //Registra a transação do usuário
                                DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Alterou a Compra " + c.getIdCompra()));

                                this.buscar();

                                /*
                                 * Verifica opções para realizar impressão da
                                 * transação*
                                 */
                                this.imprimir();

                                limparGUI();
                            }

                        } else {
                            JOptionPane.showMessageDialog(frame, "A tara não pode ser maior que o peso bruto.", "Atenção!", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Pelo menos estas condições devem ser satisfeitas para "
                                + "efetuar a alteração:"
                                + "\n-Defina o cliente."
                                + "\n-Defina a placa."
                                + "\n-Defina a forma de pagamento."
                                + "\n-Defina o vencimento."
                                + "\n-Defina o valor total.", "Atenção!", JOptionPane.WARNING_MESSAGE);

                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Escolha um produto na tabela para alterar.",
                            "Atenção!", JOptionPane.WARNING_MESSAGE);

                }


            }
        } catch (NumberFormatException er) {
            JOptionPane.showMessageDialog(frame, "Valor numérico digitado de forma inválida, favor tentar novamente!\n\n" + er,
                    "Erro", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            apaga.apagaJTextField(frame.textValor);

        }
    }

    /**
     * Imprime o relatório do iReport
     */
    public void imprimir() {

        int opcaoImprimir = JOptionPane.showOptionDialog(null, "Deseja Imprimir esta compra?", "Imprimir",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, frame.botoesOpcoes, frame.botoesOpcoes[0]);
        if (opcaoImprimir == JOptionPane.YES_OPTION) {

            if (!frame.textCodigo.getText().isEmpty()) {

                try {
                    InputStream inputStream = getClass().getResourceAsStream("/relatorios/Compra.jasper");

                    Map parametros = new HashMap();

                    parametros.put("idCompra", Integer.parseInt(frame.textCodigo.getText()));
                    FileInputStream imagem = new FileInputStream(new java.io.File("logo.png"));
                    parametros.put("imagem", imagem);

                    parametros = ControleFPreferencias.preencheParametrosRelatorio(parametros);

                    ReportUtils.openReport("Compra", inputStream, parametros, ConnectionFactory_brittos_bd.getConnection());

                } catch (Exception er) {

                    JOptionPane.showMessageDialog(frame, "Erro ao criar o relatório.\n" + er, "Erro", JOptionPane.ERROR_MESSAGE);
                    er.printStackTrace();

                }
            } else {
                JOptionPane.showMessageDialog(frame, "Selecione uma compra na tabela para imprimir.", "Atenção",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void concluir() {

        int opcao = JOptionPane.showOptionDialog(null, "Você tem certeza que deseja concluir esta(s) compra(s)?\n"
                + "Ela(s) não poderá(ão) ter seus valores alterados.", "Concluir Compra",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                frame.botoesOpcoes, frame.botoesOpcoes[0]);

        if (opcao == JOptionPane.YES_OPTION) {

            if (!frame.textCodigo.getText().equals("")) {

                int[] selectedRows = frame.tabela.getSelectedRows();

                if (selectedRows.length > 0) {
                    for (int i = 0; i < selectedRows.length; i++) {
                        selectedRows[i] = (Integer) frame.tabela.getValueAt(selectedRows[i], 0);

                    }
                    /**
                     * Conclui no BD todas as transações selecionadas*
                     */
                    new DAOCompra().concluirVarios(selectedRows);

                    //Lista a nova atualização na tabela da GUI*
                    this.buscar();
                    this.limparGUI();

                    JOptionPane.showMessageDialog(frame, "Compra(s) concluídas(s) com sucesso!", "Concluindo Compras",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }
        }
    }

    public void desconcluir() {

        int opcao = JOptionPane.showOptionDialog(null, "Você deseja desconcluir esta(s) compra(s)?\n", "Desconcluir",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                frame.botoesOpcoes, frame.botoesOpcoes[0]);

        if (opcao == JOptionPane.YES_OPTION) {

            if (!frame.textCodigo.getText().equals("")) {

                int[] selectedRows = frame.tabela.getSelectedRows();

                if (selectedRows.length > 0) {
                    for (int i = 0; i < selectedRows.length; i++) {
                        selectedRows[i] = (Integer) frame.tabela.getValueAt(selectedRows[i], 0);

                    }

                    //Desconclui no BD todas as transações selecionadas*
                    new DAOCompra().desconcluirVarios(selectedRows);

                    //Lista a nova atualização na GUI
                    this.buscar();
                    this.limparGUI();
                }

            }
        }
    }

    /**
     * Quita a compra atual e gera uma conta a pagar
     */
    public void quitar() {

        //Pergunta se o usuário deseja quitar a compra
        int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja quitar esta(s) compra(s) e gerar a(s) respectiva(s)\n"
                + "conta(s) a pagar já quitada(s)?", "Quitar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, frame.botoesOpcoes, frame.botoesOpcoes[0]);

        if (opcao == JOptionPane.YES_OPTION) {

            int[] selectedRows = frame.tabela.getSelectedRows();

            if (selectedRows.length > 0) {
                for (int i = 0; i < selectedRows.length; i++) {
                    selectedRows[i] = (Integer) frame.tabela.getValueAt(selectedRows[i], 0);

                }

                //Desconclui no BD todas as transações selecionadas*
                AbstractDAO.quitarVarios("Compra c INNER JOIN FETCH c.entidade e LEFT JOIN FETCH c.compraHasProdutos h ", "c.idCompra", selectedRows);

                //Lista a nova atualização na tabela da GUI
                this.buscar();
                this.limparGUI();
            }
        }
    }

    /**
     * Insere um determinado produto na tabela de produtos comprados
     */
    public void inserirProduto() {

        DefaultTableModel modelo = (DefaultTableModel) frame.tabelaProdutos.getModel();

        //Verifica se na tabela de produtos já existe algum produto cadastrado
        if (frame.tabelaProdutos.getModel().getRowCount() == 0) {
            //Focaliza o novo JInternalFrame
            InternalFrameAddProduto frameAdd = new InternalFrameAddProduto(modelo);
            FramePrincipal.desktopPanel.add(frameAdd);
            FramePrincipal.desktopPanel.getDesktopManager().activateFrame(frameAdd);
            
        } else {
            JOptionPane.showMessageDialog(frame, "É permitido registrar apenas um produto por nota.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }

    }

    /**
     * Edita a compra de um determinado produto selecionado em @tabelaProdutos
     */
    public void editarProduto() {

        DefaultTableModel modelo = (DefaultTableModel) frame.tabelaProdutos.getModel();

        /*
         * Focaliza o novo JInternalFrame
         */
        InternalFrameAddProduto frameAdd = new InternalFrameAddProduto(modelo);
        FramePrincipal.desktopPanel.add(frameAdd);
        FramePrincipal.desktopPanel.getDesktopManager().activateFrame(frameAdd);
        frameAdd.exibirProduto(frame.tabelaProdutos);

    }

    /**
     * Remove um determinado produto na tabela de produtos comprados
     */
    public void removerProduto() {

        /*
         * Captura o modelo da tabela de produtos
         */
        DefaultTableModel modelo = (DefaultTableModel) frame.tabelaProdutos.getModel();

        /*
         * Verifica se existe pelo menos uma linha no modelo
         */
        if (modelo.getRowCount() > 0) {
            /*
             * Captura a linha selecionada
             */
            int linha = frame.tabelaProdutos.getSelectedRow();
            /*
             * Verifica se a linha capturada existe
             */
            if (linha != -1) {
                int opcao = JOptionPane.showOptionDialog(null, "Você tem certeza que deseja remover a compra do produto selecionado na tabela?", "Remoção de compra de produto",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        frame.botoesOpcoes, frame.botoesOpcoes[0]);
                if (opcao == JOptionPane.YES_OPTION) {
                    modelo.removeRow(linha);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Você não escolheu nenhuma linha da tabela de produtos para ser removida.", "Atenção", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * Abre o frame de um determinado produto selecionado na Tabela de Produtos
     */
    public void consultaTabelaProdutos(java.awt.event.MouseEvent evt) {

        //Verifica se foram dados 2 clicks do mouse durante o evento
        if (evt.getClickCount() == 2) {
            //Verifica se a compra não está concluida
            if (frame.buttonConcluir.isEnabled() || frame.buttonCadastro.isEnabled()) {
                //Abre o frame com o produto
                this.editarProduto();
            }
        }
    }

    /**
     * Seta e captura o modelo da tabela de produtos
     */
    public DefaultTableModel getModelTabelaProdutos() {

        TableColumn coluna0 = frame.tabelaProdutos.getColumnModel().getColumn(0);
        TableColumn coluna1 = frame.tabelaProdutos.getColumnModel().getColumn(1);
        TableColumn coluna2 = frame.tabelaProdutos.getColumnModel().getColumn(2);
        TableColumn coluna3 = frame.tabelaProdutos.getColumnModel().getColumn(3);
        TableColumn coluna4 = frame.tabelaProdutos.getColumnModel().getColumn(4);
        TableColumn coluna5 = frame.tabelaProdutos.getColumnModel().getColumn(5);
        TableColumn coluna6 = frame.tabelaProdutos.getColumnModel().getColumn(6);

        //Dimensiona as colunas na tabela*
        coluna0.setPreferredWidth(30);
        coluna1.setPreferredWidth(150);
        coluna2.setPreferredWidth(50);
        coluna3.setPreferredWidth(50);
        coluna4.setPreferredWidth(50);
        coluna5.setPreferredWidth(50);
        coluna6.setPreferredWidth(70);

        //Preenche a coluna 1 com um combobox de produtos
        coluna5.setCellEditor(new DefaultCellEditor(new TextFieldMoedaReal()));
        coluna6.setCellEditor(new DefaultCellEditor(new TextFieldMoedaReal()));

        //Aplica o modelo
        final DefaultTableModel modelo = (DefaultTableModel) frame.tabelaProdutos.getModel();
        modelo.setNumRows(0);

        //Configura eventos do modelo da tabela de produtos
        modelo.addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                somaValorTotal();

            }
        });

        return modelo;
    }

    public boolean verificaDataCriacao() {
        if (frame.radioBuscaCriacaoEntre.isSelected()) {
            EnabledGUIElements.enabledJComponent(frame.textBuscaCriacaoAte, frame.textBuscaCriacaoDe);
        } else {
            EnabledGUIElements.disabledJComponent(frame.textBuscaCriacaoAte, frame.textBuscaCriacaoDe);
            new ApagaElementosDaInterface().apagaJTextField(frame.textBuscaCriacaoAte, frame.textBuscaCriacaoDe);
        }

        return true;
    }

    public boolean buscar() {

        try {

            //Captura os parametros do frame
            String id = frame.textBuscaCodigo.getText();
            String idCliente = null;
            String deCriacao = frame.textBuscaCriacaoDe.getText();
            String ateCriacao = frame.textBuscaCriacaoAte.getText();

            if (frame.comboBuscaCliente.getSelectedIndex() != 0) {
                List<Entidade> listEnt = new DAOEntidade().consultar("nome='" + frame.comboBuscaCliente.getSelectedItem().toString() + "'");
                if (listEnt != null && !listEnt.isEmpty()) {
                    idCliente = listEnt.get(0).getIdEntidade().toString();
                }
            }

            //Realiza a busca no bd
            List<Compra> list = null;

            //Incrementa a string para consutar a busca
            String query = "";
            if (id != null && !id.isEmpty()) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                query += " c.idCompra=" + id;
            }
            if (deCriacao != null && !deCriacao.isEmpty() && !deCriacao.equals("  /  /    ")
                    && ateCriacao != null && !ateCriacao.isEmpty() && !ateCriacao.equals("  /  /    ")) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                Date dateDe = Dates.getDate(deCriacao);
                Date dateAte = Dates.getDate(ateCriacao);
                query += " c.dataCriacao >= '" + dateDe.toString() + "' AND c.dataCriacao <='" + dateAte.toString() + "'";
            }
            if (idCliente != null && !idCliente.isEmpty()) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                query += " e.idEntidade=" + idCliente;
            }
            if (frame.radioBuscaPagamentoQuitada.isSelected()) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                query += " c.quitada=TRUE";
            }
            if (frame.radioBuscaPagamentoAberto.isSelected()) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                query += " c.quitada=FALSE";
            }

            if (!query.equals("")) {
                list = AbstractDAO.consultar("from Compra c INNER JOIN FETCH c.entidade e LEFT JOIN FETCH c.compraHasProdutos h WHERE " + query);
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

        new ApagaElementosDaInterface().apagaJTextField(frame.textBuscaCodigo, frame.textBuscaCriacaoAte, frame.textBuscaCriacaoDe);
        frame.radioBuscaCriacaoTodas.setSelected(true);
        frame.radioBuscaPagamentoTodas.setSelected(true);
        frame.comboBuscaCliente.setSelectedIndex(0);
        this.verificaDataCriacao();
        this.limparGUI();
        this.listarPesquisaNaTabela(null);
    }
}
