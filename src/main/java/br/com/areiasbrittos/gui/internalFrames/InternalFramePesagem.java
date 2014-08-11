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

import br.com.areiasbrittos.controle.gui.ControleFPreferencias;
import br.com.areiasbrittos.controle.objetos.*;
import br.com.areiasbrittos.gui.utils.TamanhoJTextField;
import br.com.areiasbrittos.gui.utils.MascarasJTextField;
import br.com.areiasbrittos.gui.utils.ConsertaBugsGUI;
import br.com.areiasbrittos.gui.utils.ApagaElementosDaInterface;
import br.com.areiasbrittos.gui.utils.EnabledGUIElements;
import br.com.areiasbrittos.gui.utils.jtable.RendererTable;
import br.com.areiasbrittos.persistencia.dao.DAOEntidade;
import br.com.areiasbrittos.persistencia.dao.DAOPesagem;
import br.com.areiasbrittos.persistencia.dao.DAOTabelaPesagem;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import br.com.areiasbrittos.persistencia.ConnectionFactory_brittos_bd;
import br.com.areiasbrittos.controle.utils.Dates;
import br.com.areiasbrittos.controle.utils.Horas;
import br.com.areiasbrittos.controle.utils.ReportUtils;
import br.com.areiasbrittos.gui.FramePrincipal;
import br.com.areiasbrittos.gui.utils.*;
import java.util.*;
import br.com.areiasbrittos.persistencia.dao.AbstractDAO;

/**
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class InternalFramePesagem extends br.com.areiasbrittos.gui.superclass.InternalFrame {

    /*
     * Variáveis desta classe
     */
    private DAOPesagem dao = new DAOPesagem();
    private DAOEntidade entDao = new DAOEntidade();
    private TamanhoJTextField limita = new TamanhoJTextField();
    private final int[] tamanhosJTextField = {45};
    private DecimalFormat df = new DecimalFormat("###,##0.00");
    private ApagaElementosDaInterface apaga = new ApagaElementosDaInterface();
    

    /**
     * Creates new form InternalFramePesagem
     */
    public InternalFramePesagem() {
        initComponents();
        setVisible(true);
        inicializa();
    }

    @Override
    protected void inicializa() {

        //Verifica caixa diário
        Caixadiario.verificaCaixasBd();

        //Limita a quantidade de caracteres possíveis em textMotorista*
        limita.limitaTamanho(tamanhosJTextField, textMotorista);

        //Inicializa o ComboBox de clientes*
        comboCliente.setModel(entDao.inicializaComboBoxEntidades("Selecione...", "Cliente"));
        comboBuscaCliente.setModel(AbstractDAO.inicializaComboBox("Selecione...", "Entidade where tipo like '%Cliente%' order by nome"));
        
        //Inicializa TextFields
        this.inicializaTextData();
        this.inicializaTextVencimento();
        textMensagem.setEditable(false);
        textMensagem.setText("Aqui são registradas as transações de pesagem simples.");

        //Inicializa eventos da tabela
        SelectionListener listener = new SelectionListener(this.tabela);
        this.tabela.getSelectionModel().addListSelectionListener(listener);
        this.tabela.getColumnModel().getSelectionModel().addListSelectionListener(listener);
        textValor.setText(0 + "");

        //Configura o comportamento dos botões da interface
        this.verificaAcessos();
        EnabledGUIElements.enabledJComponent(buttonCadastro);
        EnabledGUIElements.disabledJComponent(this.buttonAlterar, this.buttonImprimir, this.buttonConcluir, this.buttonDesconcluir);

    }

    /**
     * Verifica o acesso do usuário para utilizar este frame
     */
    @Override
    protected void verificaAcessos() {

        //Verifica a permissao de @user para este frame
        if (!FramePrincipal.acessos.get(FramePrincipal.PERMISSAO_ALTERA_PESAGEM).isPermissao()) {
            EnabledGUIElements.disabledJComponent(this.buttonCadastro, this.buttonAlterar, this.buttonConcluir);
        }

        if (!FramePrincipal.acessos.get(FramePrincipal.PERMISSAO_DESCONCLUI_PESAGEM).isPermissao()) {
            EnabledGUIElements.disabledJComponent(this.buttonDesconcluir);
        }
    }

    @Override
    protected void guiAtiva(boolean concluida) {

        //Verifica se a pesagem consultada esta concluida e configura o comportamento dos botoes
        if (concluida) {
            EnabledGUIElements.disabledJComponent(textMotorista, textPesoBruto,
                    textPlaca, textTara, textValor, textVencimento, buttonAlterar,
                    buttonConcluir, comboCliente, comboPagamento);
            EnabledGUIElements.enabledJComponent(this.buttonDesconcluir);
            textMensagem.setText("Esta pesagem encontra-se concluida.");
        } else {
            EnabledGUIElements.enabledJComponent(textMotorista, textPesoBruto, textVencimento,
                    textPlaca, textTara, textValor, buttonAlterar,
                    buttonConcluir, comboCliente, comboPagamento);
            EnabledGUIElements.disabledJComponent(this.buttonDesconcluir);

            textMensagem.setText("Esta pesagem encontra-se não concluida.");
        }

        this.verificaAcessos();

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
            List<Pesagem> consulta = AbstractDAO.consultar("from Pesagem p LEFT JOIN FETCH p.entidade e WHERE p.idPesagem=" + cod);

            if (consulta.size() > 0) {
                Pesagem p = consulta.get(0);
                this.preencheGUI(p);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            // ex.printStackTrace();
        }

    }

    /**
     * Preenche os componente da gui a partir de um objeto
     *
     * @param p
     */
    public void preencheGUI(Pesagem p) {

        textCodigo.setText(Integer.toString(p.getIdPesagem()));
        textDataCriacao.setText(ConsertaBugsGUI.getDataFormatadaNoBd(p.getDataCriacao(), "dd/MM/yyyy"));
        textHoraCriacao.setText(p.getHoraCriacao().toString());
        comboCliente.setSelectedItem(p.getEntidade().getNome());
        textMotorista.setText(p.getMotorista());
        textTara.setText(Integer.toString(p.getTara()));
        textPesoBruto.setText(Integer.toString(p.getPesoBruto()));
        textPesoLiquido.setText(Integer.toString(p.getPesoLiquido()));

        //Verifica o preenchimento de textPlaca
        if (p.getPlaca().equals("   -    ")) {
            textPlaca.setText("");
        } else {
            textPlaca.setText(p.getPlaca());
        }

        //Verifica os atributos de quitada
        if (p.isQuitada()) {

            ArrayList<Contasreceber> conta = (ArrayList<Contasreceber>) AbstractDAO.consultar("Contasreceber", "idPesagem=" + p.getIdPesagem());
            if (conta.size() > 0) {
                radioQuitada.setSelected(true);
                textPagamentoDia.setText(ConsertaBugsGUI.getDataFormatadaNoBd(conta.get(0).getDataPagamento(), "dd/MM/yyyy"));
                textPagamentoHora.setText(conta.get(0).getHoraPagamento().toString());
                textPagamentoIdConta.setText(conta.get(0).getIdConta() + "");
            }

            buttonQuitar.setEnabled(false);

        } else {
            
            radioAberto.setSelected(true);
            textPagamentoDia.setText("");
            textPagamentoHora.setText("");
            textPagamentoIdConta.setText("");

            buttonQuitar.setEnabled(true);
        }

        //Configura o comportamento dos botões da interface
        buttonCadastro.setEnabled(false);
        comboPagamento.setSelectedItem(p.getPagamento());
        textVencimento.setText(ConsertaBugsGUI.getDataFormatadaNoBd(p.getVencimento(), "dd/MM/yyyy"));

        //Reconfigura o valor total
        textValor.setText(p.getValorPesagem().toString());

        //Configura o comportamento da GUI em caso dela ser estar concluídas
        EnabledGUIElements.disabledJComponent(buttonCadastro);
        EnabledGUIElements.enabledJComponent(this.buttonAlterar, this.buttonImprimir, this.buttonConcluir, this.buttonDesconcluir);
        if (p.isConcluida()) {
            this.guiAtiva(true);
            EnabledGUIElements.disabledJComponent(this.buttonConcluir);
        } else {
            this.guiAtiva(false);
            EnabledGUIElements.disabledJComponent(this.buttonDesconcluir);
        }
        if (p.isQuitada()) {
            EnabledGUIElements.disabledJComponent(this.buttonDesconcluir);
        }

        this.verificaAcessos();
    }

    @Override
    protected void limparGUI() {

        this.buscar();
        comboCliente.setSelectedIndex(0);
        comboPagamento.setSelectedIndex(0);
        radioAberto.setSelected(true);
        apaga.apagaJTextField(textCodigo, textDataCriacao, textHoraCriacao, textPlaca, textValor, textVencimento, textMotorista,
                textTara, textPesoBruto, textPesoLiquido, textPagamentoDia, textPagamentoHora, textPagamentoIdConta);
        this.textValor.setText("0");

        this.inicializaTextData();
        this.inicializaTextVencimento();

        //Torna a GUI completamente editável novamente*
        this.guiAtiva(false);
        this.verificaAcessos();
        EnabledGUIElements.enabledJComponent(this.buttonCadastro);
        EnabledGUIElements.disabledJComponent(this.buttonAlterar, this.buttonImprimir, this.buttonConcluir, this.buttonDesconcluir);

    }

    /**
     * Inicializa o TextField para data*
     */
    public void inicializaTextData() {

        Date data = new Date(System.currentTimeMillis());
        DateFormat hoje = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("pt", "BR"));

        textDataCriacao.setText(hoje.format(data));
    }

    /**
     * Inicializa o TextField para vencimento*
     */
    public void inicializaTextVencimento() {

        Date data = new Date(System.currentTimeMillis());
        DateFormat hoje = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("pt", "BR"));

        textVencimento.setText(hoje.format(data));
    }

    /**
     * Cria um objeto pesagem a partir dos dados fornecidos aos elementos deste
     * frame
     *
     * @param data - data que será armazenada com o objeto
     * @return - retorna o objeto Pesagem obtido pelo frame
     * @throws NumberFormatException
     */
    private Pesagem criaPesagem() throws NumberFormatException {

        //Captura os atributos nesta GUI e inicializa as variáveis com seus devidos valores
        int tara = ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(textTara);
        int pesoBruto = ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(textPesoBruto);
        int pesoLiquido = ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(textPesoLiquido);
        BigDecimal valor = new BigDecimal(textValor.getText().replaceAll("\\.", "").replaceAll(",", "\\."));
        Entidade entidade = entDao.getEntidade(comboCliente.getSelectedItem().toString());
        String placa = textPlaca.getText();
        Date dataCriacao = null;
        Time horaCriacao = null;
        Date dataAlteracao = Dates.getDataHoje();
        Time horaAlteracao = Horas.getHoraAgora();
        String pagamento = this.comboPagamento.getSelectedItem().toString();
        Date vencimento = null;

        //Verifica se o peso bruto não é menor do que a tara
        if (!this.verificaTaraPesoBruto()) {
            try {

                //Verifica se a data e a hora de criação já foram preenchidos
                if (textDataCriacao.getText().isEmpty()) {
                    dataCriacao = Dates.getDataHoje();
                } else {
                    dataCriacao = Dates.getDate(textDataCriacao.getText());
                }

                if (textHoraCriacao.getText().isEmpty()) {
                    horaCriacao = Horas.getHoraAgora();
                } else {
                    horaCriacao = Horas.getTime(textHoraCriacao.getText());
                }

                vencimento = new Date(new SimpleDateFormat("dd/MM/yyyy").parse(textVencimento.getText()).getTime());
                vencimento = Dates.verificaDigitosAno(vencimento);

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this, "Erro ao manipular datas.", "Erro", JOptionPane.ERROR_MESSAGE);
                vencimento = new Date(System.currentTimeMillis());
                ex.printStackTrace();

                return null;
            }
        } else {

            JOptionPane.showMessageDialog(this, "A tara não pode ser maior que o peso bruto.", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }


        Pesagem pesagem = new Pesagem(entidade, dataCriacao, horaCriacao, dataAlteracao, horaAlteracao, pesoBruto, tara,
                pesoLiquido, placa, valor, vencimento, false, textMotorista.getText(), pagamento, false);

        //Verifica se a GUI contem o id da pesagem
        if (ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(this.textCodigo) > 0) {
            pesagem.setIdPesagem(ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(this.textCodigo));
        }

        return pesagem;
    }

    /**
     * Verifica se o valor em @textTara é maior do que @textPesoBruto, caso
     * positivo retorna true e em caso negativo retorna falso
     */
    private boolean verificaTaraPesoBruto() {

        int tara = 0;
        int pesoBruto = 0;

        //Encontra os valores para tara e peso bruto
        if (!(textTara.getText().replace(" ", "").isEmpty())) {
            tara = ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(textTara);
        }

        if (!(textPesoBruto.getText().replace(" ", "").isEmpty())) {
            pesoBruto = ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(textPesoBruto);
        }

        if (tara > pesoBruto && pesoBruto != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calcula o peso líquido a partir da tara e do peso bruto inseridos neste
     * frame
     */
    private void calculaPesoLiquido() {

        int tara = 0;
        int pesoBruto = 0;

        //Encontra os valores para tara e peso bruto
        if (!(textTara.getText().replace(" ", "").isEmpty())) {

            tara = ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(textTara);
        }

        if (!(textPesoBruto.getText().replace(" ", "").isEmpty())) {

            pesoBruto = ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(textPesoBruto);
        }

        //Verifica se não há possibilidade da tara ser maior do que o peso bruto
        //evitando assim que o peso líquido não seja negativo
        if ((tara > 0 && pesoBruto == 0) || (tara == 0 && pesoBruto == 0) || this.verificaTaraPesoBruto()) {
            textPesoLiquido.setText("0");

        } else if (!this.verificaTaraPesoBruto()) {
            textPesoLiquido.setText(Integer.toString(pesoBruto - tara));

        }

        //Calcula o valor da pesagem a partir da tara ou do peso bruto
        if (tara > -1) {
            DAOTabelaPesagem tpDAO = new DAOTabelaPesagem();

            List<Tabelapesagem> list = tpDAO.consultar("tara<=" + tara + " order by tara desc");

            //Escolhe o valor a vista ou a prazo
            if (comboPagamento.getSelectedIndex() == 0) {
                //Preço a vista
                textValor.setText(list.get(0).getPrecoAVista().toString());

            } else {
                //Preço a prazo
                textValor.setText(list.get(0).getPrecoAPrazo().toString());

            }
        }
    }

    @Override
    protected void listarTodosElementosNaTabela() {

        List<Pesagem> list = dao.listar("dataAlteracao", "horaAlteracao");
        listarPesquisaNaTabela(list);

    }

    /**
     * Lista na tabela os objetos Pesagem em @list
     *
     * @param list - Lista de objetos Pesagem
     */
    private void listarPesquisaNaTabela(List<Pesagem> list) {

        //Inicializa as colunas da tabela*
        TableColumn coluna1 = tabela.getColumnModel().getColumn(0);
        TableColumn coluna2 = tabela.getColumnModel().getColumn(1);
        TableColumn coluna3 = tabela.getColumnModel().getColumn(2);
        TableColumn coluna4 = tabela.getColumnModel().getColumn(3);
        TableColumn coluna5 = tabela.getColumnModel().getColumn(4);

        //Dimensiona as colunas na tabela*
        coluna1.setPreferredWidth(50);
        coluna2.setPreferredWidth(70);
        coluna3.setPreferredWidth(500);
        coluna4.setPreferredWidth(50);
        coluna5.setPreferredWidth(90);

        //Inicializa o modelo declarado acima*
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);

        //Inicializa o DefaultTableCellRender modificado para personalizar algumas células*
        DefaultTableCellRenderer renderer = new RendererTable(list);

        try {

            if (list != null) {

                //Preenche a tabela com os elementos de @list*
                for (int i = 0; i < list.size(); i++) {
                    Pesagem p = list.get(i);
                    modelo.addRow(new Object[]{p.getIdPesagem(), ConsertaBugsGUI.getDataFormatadaNoBd(p.getDataCriacao(), "dd/MM/yyyy"), p.getEntidade().getNome(),
                                p.isQuitada() ? "Sim" : "Não", ("R$ " + df.format(p.getValorPesagem()))});

                    //Altera as propriedades das células desta linha de acordo com o estado de conclusão da transação atual*
                    coluna1.setCellRenderer(renderer);
                    coluna2.setCellRenderer(renderer);
                    coluna3.setCellRenderer(renderer);
                    coluna4.setCellRenderer(renderer);
                    coluna5.setCellRenderer(renderer);
                }
            }
        } catch (Exception er) {
            JOptionPane.showMessageDialog(this, "Erro na listagem!\n" + er, "Erro!", JOptionPane.ERROR_MESSAGE);

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

        Date vencimento = new Date(System.currentTimeMillis());
        int tara = ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(textTara);
        int pesoBruto = ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(textPesoBruto);
        Tabelapesagem itemTabela = null;

        /*
         * Verifica se não há possibilidade da tara ser maior do que o peso
         * bruto evitando assim que o peso líquido não seja negativo
         */
        if ((tara > 0 && pesoBruto == 0) || (tara == 0 && pesoBruto == 0) || this.verificaTaraPesoBruto()) {
            textPesoLiquido.setText("0");

        } else if (!this.verificaTaraPesoBruto()) {
            textPesoLiquido.setText(Integer.toString(pesoBruto - tara));

        }

        /*
         * Recupera o valor da pesagem a partir da tara ou do peso bruto
         */
        /*
         * Caso a tara exista, recupera dela
         */
        if (tara > 0) {
            itemTabela = (Tabelapesagem) AbstractDAO.consultar("Tabelapesagem", "tara<=" + tara + " order by tara desc").get(0);
        } else {
            /*
             * Caso não exista, recupera do peso bruto
             */
            itemTabela = (Tabelapesagem) AbstractDAO.consultar("Tabelapesagem", "tara<=" + pesoBruto + " order by tara desc").get(0);
        }

        //Caso algum tenha sido encontrado algum item da tabela de pesagem
        if (itemTabela != null) {
            //Verifica a forma de pagamento e assim define o preço a vista ou a prazo da tabela de pesagens
            if (comboPagamento.getSelectedIndex() == 0) {
                //Se o pagamento é a vista
                textValor.setText(itemTabela.getPrecoAVista().toString());
            } else {
                //Se o pagamento é a prazo
                textValor.setText(itemTabela.getPrecoAPrazo().toString());
            }
        }

        textVencimento.setText(ConsertaBugsGUI.getDataFormatadaNoBd(vencimento, "dd/MM/yyyy"));
    }

    public boolean verificaDataCriacao() {
        if (this.radioBuscaCriacaoEntre.isSelected()) {
            EnabledGUIElements.enabledJComponent(this.textBuscaCriacaoAte, this.textBuscaCriacaoDe);
        } else {
            EnabledGUIElements.disabledJComponent(this.textBuscaCriacaoAte, this.textBuscaCriacaoDe);
            new ApagaElementosDaInterface().apagaJTextField(this.textBuscaCriacaoAte, this.textBuscaCriacaoDe);
        }

        return true;
    }
    
    public boolean buscar() {

        try {

            //Captura os parametros do frame
            String id = this.textBuscaCodigo.getText();
            String idCliente = null;
            String deCriacao = this.textBuscaCriacaoDe.getText();
            String ateCriacao = this.textBuscaCriacaoAte.getText();
            
            if(this.comboBuscaCliente.getSelectedIndex() != 0){
                List<Entidade> listEnt = new DAOEntidade().consultar("nome='" + this.comboBuscaCliente.getSelectedItem().toString() + "'");
                if(listEnt != null && !listEnt.isEmpty()){
                    idCliente = listEnt.get(0).getIdEntidade().toString();
                }
            }

            //Realiza a busca no bd
            List<Pesagem> list = null;

            //Incrementa a string para consutar a busca
            String query = "";
            if (id != null && !id.isEmpty()) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                query += " p.idPesagem=" + id;
            }
            if (deCriacao != null && !deCriacao.isEmpty() && !deCriacao.equals("  /  /    ")
                    && ateCriacao != null && !ateCriacao.isEmpty() && !ateCriacao.equals("  /  /    ")) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                Date dateDe = Dates.getDate(deCriacao);
                Date dateAte = Dates.getDate(ateCriacao);
                query += " p.dataCriacao >= '" + dateDe.toString() + "' AND p.dataCriacao <='" + dateAte.toString() + "'";
            }
            if (idCliente != null && !idCliente.isEmpty()) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                query += " e.idEntidade=" + idCliente;
            }
            if (this.radioBuscaPagamentoQuitada.isSelected()) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                query += " p.quitada=TRUE";
            }
            if (this.radioBuscaPagamentoAberto.isSelected()) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                query += " p.quitada=FALSE";
            }

            if (!query.equals("")) {
                //System.out.println(query);
                list = AbstractDAO.consultar("from Pesagem p LEFT JOIN FETCH p.entidade e WHERE " + query);
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

        new ApagaElementosDaInterface().apagaJTextField(this.textBuscaCodigo, this.textBuscaCriacaoAte, this.textBuscaCriacaoDe);
        this.radioBuscaPagamentoTodas.setSelected(true);
        this.radioBuscaCriacaoTodas.setSelected(true);
        this.comboBuscaCliente.setSelectedIndex(0);
        this.verificaDataCriacao();
        this.limparGUI();
        this.listarPesquisaNaTabela(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        groupBuscaPagamento = new javax.swing.ButtonGroup();
        groupBuscaCriacao = new javax.swing.ButtonGroup();
        panelInformacoes = new javax.swing.JPanel();
        textDataCriacao = new javax.swing.JTextField();
        labelCliente = new javax.swing.JLabel();
        labelDataCriacao = new javax.swing.JLabel();
        textPlaca = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("UUU-####"));
        comboCliente = new javax.swing.JComboBox();
        labelCodigo = new javax.swing.JLabel();
        textCodigo = new javax.swing.JTextField();
        labelPlaca = new javax.swing.JLabel();
        labelTara = new javax.swing.JLabel();
        labelPesoBruto = new javax.swing.JLabel();
        labelPesoLiquido = new javax.swing.JLabel();
        textPesoLiquido = new javax.swing.JTextField();
        textTara = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("######"));
        textPesoBruto = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("######"));
        labelMotorista = new javax.swing.JLabel();
        textMotorista = new javax.swing.JTextField();
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
        setTitle("Pesagem");

        panelInformacoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        textDataCriacao.setEnabled(false);

        labelCliente.setText("Cliente:");

        labelDataCriacao.setText("Data de Criação:");

        ((JFormattedTextField)textPlaca).setFocusLostBehavior(JFormattedTextField.COMMIT);
        ((JFormattedTextField)textPlaca).setHorizontalAlignment(textPlaca.LEFT);

        labelCodigo.setText("Codigo:");

        textCodigo.setEnabled(false);

        labelPlaca.setText("Placa:");

        labelTara.setText("Tara:");

        labelPesoBruto.setText("Peso Bruto:");

        labelPesoLiquido.setText("Peso Líquido:");

        textPesoLiquido = new javax.swing.JFormattedTextField(new MascarasJTextField().inserirMascara("######"));
        textPesoLiquido.setEnabled(false);

        ((JFormattedTextField)textTara).setFocusLostBehavior(JFormattedTextField.COMMIT);
        ((JFormattedTextField)textTara).setHorizontalAlignment(textTara.LEFT);
        textTara.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textTaraFocusLost(evt);
            }
        });

        ((JFormattedTextField)textPesoBruto).setFocusLostBehavior(JFormattedTextField.COMMIT);
        ((JFormattedTextField)textPesoBruto).setHorizontalAlignment(textPesoBruto.LEFT);
        textPesoBruto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textPesoBrutoFocusLost(evt);
            }
        });

        labelMotorista.setText("Motorista:");

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

        buttonGroup1.add(radioAberto);
        radioAberto.setSelected(true);
        radioAberto.setText("Em Aberto");
        radioAberto.setEnabled(false);

        buttonGroup1.add(radioQuitada);
        radioQuitada.setText("Quitada");
        radioQuitada.setEnabled(false);

        labelPagamento2.setText("Codigo da conta a receber:");

        textPagamentoIdConta.setEnabled(false);

        javax.swing.GroupLayout panelPagamento1Layout = new javax.swing.GroupLayout(panelPagamento1);
        panelPagamento1.setLayout(panelPagamento1Layout);
        panelPagamento1Layout.setHorizontalGroup(
            panelPagamento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagamento1Layout.createSequentialGroup()
                .addGroup(panelPagamento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(radioQuitada)
                    .addComponent(radioAberto)
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
                .addGap(0, 10, Short.MAX_VALUE))
        );
        panelPagamento1Layout.setVerticalGroup(
            panelPagamento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagamento1Layout.createSequentialGroup()
                .addComponent(radioAberto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioQuitada)
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
                    .addComponent(textPagamentoIdConta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                            .addComponent(labelPlaca)
                            .addComponent(labelTara))
                        .addGap(4, 4, 4)
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textPlaca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                            .addComponent(textTara, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelInformacoesLayout.createSequentialGroup()
                                .addComponent(labelPesoBruto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textPesoBruto, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelPesoLiquido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textPesoLiquido, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelInformacoesLayout.createSequentialGroup()
                                .addComponent(labelMotorista)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textMotorista))))
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
                        .addComponent(textHoraCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addComponent(labelCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(60, 60, 60)
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
                    .addComponent(labelCliente)
                    .addComponent(comboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelPlaca)
                            .addComponent(textPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTara)
                            .addComponent(textTara, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelInformacoesLayout.createSequentialGroup()
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelMotorista)
                            .addComponent(textMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelPesoBruto)
                            .addComponent(textPesoBruto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelPesoLiquido)
                            .addComponent(textPesoLiquido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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

        labelValorPesagem.setText("Valor da Pesagem:");

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

        panelTabela.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Histórico de Pesagens", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        tabela.setAutoCreateRowSorter(true);
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod", "Data", "Cliente", "Quitada", "Valor"
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
            .addComponent(scrollTabela, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        panelTabelaLayout.setVerticalGroup(
            panelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollTabela, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
        );

        panelOpcoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opções", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

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

        buttonLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Novo.png"))); // NOI18N
        buttonLimpar.setText("Nova/Limpar");
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

        buttonConcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Concluir.png"))); // NOI18N
        buttonConcluir.setText("Concluir");
        buttonConcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonConcluirActionPerformed(evt);
            }
        });

        buttonDesconcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Desconcluir.png"))); // NOI18N
        buttonDesconcluir.setText("Desconcluir");
        buttonDesconcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDesconcluirActionPerformed(evt);
            }
        });

        buttonQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Quitar.png"))); // NOI18N
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

        panelBusca.setBorder(javax.swing.BorderFactory.createTitledBorder("Busca"));

        buttonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Buscar.png"))); // NOI18N
        buttonBuscar.setText("Buscar");
        buttonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBuscarActionPerformed(evt);
            }
        });

        buttonLimparBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Limpar.png"))); // NOI18N
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
                        .addComponent(comboBuscaCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(59, 59, 59))
                    .addGroup(panelBuscaLayout.createSequentialGroup()
                        .addGroup(panelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBuscaLayout.createSequentialGroup()
                                .addComponent(buttonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonLimparBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelBuscaLayout.createSequentialGroup()
                                .addComponent(labelBuscaCodigo)
                                .addGap(17, 17, 17)
                                .addComponent(textBuscaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
            .addComponent(panelBusca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTabela, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelInformacoes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelPagamento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelOpcoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
     *
     * @param evt
     */
    private void buttonCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCadastroActionPerformed

        try {
            /*
             * Pergunta se o cliente realmente deseja cadastrar a transação
             */
            int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja efetuar o cadastro desta pesagem?",
                    "Cadastrar",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    botoesOpcoes, botoesOpcoes[0]);

            /*
             * O cliente confirmou que deseja efetuar o cadastro da pesagem
             */
            if (opcao == JOptionPane.YES_OPTION) {
                /*
                 * O campo código está vazio, ou seja, verifica se esta não é
                 * uma pesagem existente
                 */
                if (textCodigo.getText().isEmpty()) {
                    /*
                     * Verifica se alguns campos já foram preenchidos
                     */
                    if (!(textDataCriacao.getText().equals("") || textValor.getText().equals("")
                            || textVencimento.getText().equals("") || comboCliente.getSelectedIndex() == 0)) {
                        /*
                         * Realiza o cadastro no banco de dados
                         */
                        Pesagem p = this.criaPesagem();
                        if (p != null) {
                            if (dao.inserir(p)) {
                                /*
                                 * Preenche o campo com o código da nova
                                 * transação no banco de dados
                                 */
                                List<Pesagem> list = dao.listar();
                                textCodigo.setText(Integer.toString(list.get(list.size() - 1).getIdPesagem()));

                                /*
                                 * Verifica opções para realizar impressão da
                                 * transação
                                 */
                                buttonImprimirActionPerformed(evt);

                                limparGUI();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Pelo menos estas condições devem ser satisfeitas para "
                                + "efetuar um novo cadastro:"
                                + "\n-Defina o cliente."
                                + "\n-Defina a placa."
                                + "\n-Defina a forma de pagamento."
                                + "\n-Defina o vencimento."
                                + "\n-Defina o valor total.", "Atenção!", JOptionPane.WARNING_MESSAGE);

                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Esta pesagem já existe portanto não é possível cadastrá-la,"
                            + "somente alterá-la!", "Atenção!", JOptionPane.WARNING_MESSAGE);

                }
                /**
                 * Relista as transações na tabela*
                 */
                buscar();


            }
        } catch (NumberFormatException er) {
            JOptionPane.showMessageDialog(this, "Valor numérico digitado de forma inválida, favor tentar novamente!\n",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            apaga.apagaJTextField(textTara, textPesoBruto, textPesoLiquido, textValor);

        }
}//GEN-LAST:event_buttonCadastroActionPerformed

    private void buttonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAlterarActionPerformed

        /*
         * Pergunta se o usuário deseja realizar a alteração
         */
        int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja alterar esta pesagem?", "Alterar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesOpcoes, botoesOpcoes[0]);

        try {
            /*
             * Caso o usuário deseje alterar a transação
             */
            if (opcao == JOptionPane.YES_OPTION) {
                /*
                 * Verifica se alguma transação foi selecionada e o campo do
                 * código está preenchido
                 */
                if (!textCodigo.getText().equals("")) {
                    /*
                     * Verifica se alguns campos já foram preenchidos
                     */
                    if (!(textDataCriacao.getText().equals("") || textValor.getText().equals("")
                            || textVencimento.getText().equals("") || comboCliente.getSelectedIndex() == 0)) {

                        /*
                         * Captura o id da transação
                         */
                        Pesagem p = criaPesagem();
                        if (p != null) {
                            p.setIdPesagem(Integer.parseInt(textCodigo.getText()));
                            /**
                             * Realiza a alteração no BD a partir do id*
                             */
                            if (dao.alterar(p)) {
                                this.buscar();

                                /**
                                 * Verifica opções para realizar impressão da
                                 * transação*
                                 */
                                buttonImprimirActionPerformed(evt);
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "Pelo menos estas condições devem ser satisfeitas para "
                                + "efetuar a alteração:"
                                + "\n-Defina o cliente."
                                + "\n-Defina a placa."
                                + "\n-Defina a forma de pagamento."
                                + "\n-Defina o vencimento."
                                + "\n-Defina o valor total.", "Atenção!", JOptionPane.WARNING_MESSAGE);

                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Escolha um produto na tabela para alterar.",
                            "Atenção!", JOptionPane.WARNING_MESSAGE);

                }


            }
        } catch (NumberFormatException er) {
            JOptionPane.showMessageDialog(this, "Valor numérico digitado de forma inválida, favor tentar novamente!\n" + er,
                    "Erro", JOptionPane.ERROR_MESSAGE);
            apaga.apagaJTextField(textTara, textPesoBruto, textPesoLiquido, textValor);

        }
}//GEN-LAST:event_buttonAlterarActionPerformed

    private void buttonImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonImprimirActionPerformed

        int opcaoImprimir = JOptionPane.showOptionDialog(null, "Deseja Imprimir a nova transação?", "Imprimir",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesOpcoes, botoesOpcoes[0]);
        if (opcaoImprimir == JOptionPane.YES_OPTION) {

            if (!textCodigo.getText().isEmpty()) {

                try {

                    InputStream inputStream = getClass().getResourceAsStream("/Pesagem.jasper");

                    Map parametros = new HashMap();

                    parametros.put("idPesagem", Integer.parseInt(textCodigo.getText()));
                    FileInputStream imagem = new FileInputStream("Logo.png");
                    parametros.put("imagem", imagem);

                    parametros = ControleFPreferencias.preencheParametrosRelatorio(parametros);

                    ReportUtils.openReport("Pesagem", inputStream, parametros, ConnectionFactory_brittos_bd.getConnection());

                } catch (Exception er) {

                    JOptionPane.showMessageDialog(this, "Erro ao criar o relatório.\n" + er, "Erro", JOptionPane.ERROR_MESSAGE);
                    er.printStackTrace();

                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma pesagem na tabela para imprimir.", "Atenção",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
}//GEN-LAST:event_buttonImprimirActionPerformed

    private void comboPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPagamentoActionPerformed
        calculaTextVencimento();
    }//GEN-LAST:event_comboPagamentoActionPerformed

    private void comboPagamentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comboPagamentoFocusLost
        calculaTextVencimento();
    }//GEN-LAST:event_comboPagamentoFocusLost

    private void textTaraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textTaraFocusLost
        calculaPesoLiquido();
    }//GEN-LAST:event_textTaraFocusLost

    private void textPesoBrutoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textPesoBrutoFocusLost
        calculaPesoLiquido();
    }//GEN-LAST:event_textPesoBrutoFocusLost

    private void buttonConcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonConcluirActionPerformed

        int opcao = JOptionPane.showOptionDialog(null, "Você tem certeza que deseja concluir esta(s) pesagem(ns)?\n"
                + "Ela(s) nunca mais poderá(ão) ter seus valores alterados.", "Concluir",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                botoesOpcoes, botoesOpcoes[0]);

        if (opcao == JOptionPane.YES_OPTION) {

            if (!textCodigo.getText().equals("")) {

                int[] selectedRows = tabela.getSelectedRows();

                if (selectedRows.length > 0) {
                    for (int i = 0; i < selectedRows.length; i++) {
                        selectedRows[i] = (Integer) tabela.getValueAt(selectedRows[i], 0);

                    }

                    //Conclui no BD todas as transações selecionadas*
                    dao.concluirVarios(selectedRows);

                    //Lista a nova atualização na tabela da GUI*
                    this.buscar();
                    this.limparGUI();

                }

            }
        }

    }//GEN-LAST:event_buttonConcluirActionPerformed

    private void buttonDesconcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDesconcluirActionPerformed

        int opcao = JOptionPane.showOptionDialog(null, "Você deseja desconcluir esta(s) pesagem(ns)?\n", "Desconcluir",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                botoesOpcoes, botoesOpcoes[0]);

        if (opcao == JOptionPane.YES_OPTION) {

            if (!textCodigo.getText().equals("")) {

                int[] selectedRows = tabela.getSelectedRows();

                if (selectedRows.length > 0) {
                    for (int i = 0; i < selectedRows.length; i++) {
                        selectedRows[i] = (Integer) tabela.getValueAt(selectedRows[i], 0);

                    }

                    //Desconclui no BD todas as transações selecionadas*
                    dao.desconcluirVarios(selectedRows);

                    //Lista a nova atualização na tabela da GUI*
                    this.buscar();
                    this.limparGUI();

                }

            }
        }

    }//GEN-LAST:event_buttonDesconcluirActionPerformed

    private void textHoraCriacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textHoraCriacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textHoraCriacaoActionPerformed

    private void buttonQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonQuitarActionPerformed

        //Verifica caixa diário
        Caixadiario.verificaCaixasBd();

        //Pergunta se o usuário deseja quitar a pesagem
        int opcao = JOptionPane.showOptionDialog(this, "Tem certeza que deseja quitar esta(s) pesagem(ns) e gerar a(s) respectiva(s)\n"
                + "conta(s) a receber já quitada?", "Quitar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, this.botoesOpcoes, this.botoesOpcoes[0]);

        if (opcao == JOptionPane.YES_OPTION) {

            int[] selectedRows = this.tabela.getSelectedRows();

            if (selectedRows.length > 0) {
                for (int i = 0; i < selectedRows.length; i++) {
                    selectedRows[i] = (Integer) this.tabela.getValueAt(selectedRows[i], 0);

                }

                //Desconclui no BD todas as transações selecionadas*
                AbstractDAO.quitarVarios("Pesagem p INNER JOIN FETCH p.entidade e ", "p.idPesagem", selectedRows);

                //Lista a nova atualização na tabela da GUI
                this.buscar();
                this.limparGUI();
            }
        }

    }//GEN-LAST:event_buttonQuitarActionPerformed

    private void textPagamentoHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textPagamentoHoraActionPerformed
   }//GEN-LAST:event_textPagamentoHoraActionPerformed

    private void buttonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBuscarActionPerformed
        this.buscar();
    }//GEN-LAST:event_buttonBuscarActionPerformed

    private void buttonLimparBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLimparBuscaActionPerformed
        this.limparBusca();
    }//GEN-LAST:event_buttonLimparBuscaActionPerformed

    private void radioBuscaPagamentoAbertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBuscaPagamentoAbertoActionPerformed
    }//GEN-LAST:event_radioBuscaPagamentoAbertoActionPerformed

    private void radioBuscaPagamentoQuitadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBuscaPagamentoQuitadaActionPerformed
    }//GEN-LAST:event_radioBuscaPagamentoQuitadaActionPerformed

    private void radioBuscaPagamentoTodasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBuscaPagamentoTodasActionPerformed
    }//GEN-LAST:event_radioBuscaPagamentoTodasActionPerformed

    private void textBuscaCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textBuscaCodigoActionPerformed
        this.buscar();
    }//GEN-LAST:event_textBuscaCodigoActionPerformed

    private void radioBuscaCriacaoEntreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBuscaCriacaoEntreActionPerformed
        this.verificaDataCriacao();
    }//GEN-LAST:event_radioBuscaCriacaoEntreActionPerformed

    private void radioBuscaCriacaoTodasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBuscaCriacaoTodasActionPerformed
        this.verificaDataCriacao();
    }//GEN-LAST:event_radioBuscaCriacaoTodasActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAlterar;
    public javax.swing.JButton buttonBuscar;
    private javax.swing.JButton buttonCadastro;
    private javax.swing.JButton buttonCancelar;
    private javax.swing.JButton buttonConcluir;
    private javax.swing.JButton buttonDesconcluir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton buttonImprimir;
    private javax.swing.JButton buttonLimpar;
    public javax.swing.JButton buttonLimparBusca;
    public javax.swing.JButton buttonQuitar;
    private javax.swing.JComboBox comboBuscaCliente;
    private javax.swing.JComboBox comboCliente;
    private javax.swing.JComboBox comboPagamento;
    private javax.swing.ButtonGroup groupBuscaCriacao;
    private javax.swing.ButtonGroup groupBuscaPagamento;
    private javax.swing.JLabel labelAs;
    private javax.swing.JLabel labelBuscaAte;
    private javax.swing.JLabel labelBuscaCliente;
    private javax.swing.JLabel labelBuscaCodigo;
    private javax.swing.JLabel labelBuscaDe;
    private javax.swing.JLabel labelCliente;
    private javax.swing.JLabel labelCodigo;
    private javax.swing.JLabel labelDataCriacao;
    private javax.swing.JLabel labelHoraCriacao;
    private javax.swing.JLabel labelMotorista;
    private javax.swing.JLabel labelPagamento;
    private javax.swing.JLabel labelPagamento1;
    private javax.swing.JLabel labelPagamento2;
    private javax.swing.JLabel labelPesoBruto;
    private javax.swing.JLabel labelPesoLiquido;
    private javax.swing.JLabel labelPlaca;
    private javax.swing.JLabel labelRS;
    private javax.swing.JLabel labelTara;
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
    private javax.swing.JPanel panelTabela;
    private javax.swing.JRadioButton radioAberto;
    public javax.swing.JRadioButton radioBuscaCriacaoEntre;
    public javax.swing.JRadioButton radioBuscaCriacaoTodas;
    public javax.swing.JRadioButton radioBuscaPagamentoAberto;
    public javax.swing.JRadioButton radioBuscaPagamentoQuitada;
    public javax.swing.JRadioButton radioBuscaPagamentoTodas;
    private javax.swing.JRadioButton radioQuitada;
    private javax.swing.JScrollPane scrollTabela;
    private javax.swing.JTable tabela;
    public javax.swing.JTextField textBuscaCodigo;
    public javax.swing.JTextField textBuscaCriacaoAte;
    public javax.swing.JTextField textBuscaCriacaoDe;
    private javax.swing.JTextField textCodigo;
    public javax.swing.JTextField textDataCriacao;
    public javax.swing.JTextField textHoraCriacao;
    private javax.swing.JTextField textMensagem;
    private javax.swing.JTextField textMotorista;
    public javax.swing.JTextField textPagamentoDia;
    public javax.swing.JTextField textPagamentoHora;
    public javax.swing.JTextField textPagamentoIdConta;
    private javax.swing.JTextField textPesoBruto;
    private javax.swing.JTextField textPesoLiquido;
    private javax.swing.JTextField textPlaca;
    private javax.swing.JTextField textTara;
    public javax.swing.JTextField textValor;
    private javax.swing.JTextField textVencimento;
    // End of variables declaration//GEN-END:variables
}
