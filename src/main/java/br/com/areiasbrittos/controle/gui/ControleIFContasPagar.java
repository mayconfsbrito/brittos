/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.controle.gui;

import br.com.areiasbrittos.controle.objetos.*;
import br.com.areiasbrittos.controle.objetos.seguranca.Transacao;
import br.com.areiasbrittos.controle.utils.Dates;
import br.com.areiasbrittos.controle.utils.Horas;
import br.com.areiasbrittos.gui.FramePrincipal;
import br.com.areiasbrittos.gui.internalFrames.InternalFrameContasPagar;
import br.com.areiasbrittos.gui.utils.ApagaElementosDaInterface;
import br.com.areiasbrittos.gui.utils.ConsertaBugsGUI;
import br.com.areiasbrittos.gui.utils.EnabledGUIElements;
import br.com.areiasbrittos.gui.utils.TamanhoJTextField;
import br.com.areiasbrittos.gui.utils.jtable.RendererTable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import br.com.areiasbrittos.persistencia.dao.AbstractDAO;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOTransacao;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class ControleIFContasPagar {

    /*
     * Variáveis da classe
     */
    private InternalFrameContasPagar frame;
    private DecimalFormat df = new DecimalFormat("###,##0.00");

    public ControleIFContasPagar(InternalFrameContasPagar frame) {
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

        //Limita a quantidade de caracteres possíveis em textMotorista*
        final int[] tamanhosJTextField = {100, 200};
        TamanhoJTextField.limitaTamanho(tamanhosJTextField, frame.textDescricao, frame.textObservacao);

        //Inicializa TextFields
        frame.textMensagem.setEditable(false);
        frame.textMensagem.setText("Aqui são gerenciadas as contas a pagar.");

        //Inicializa eventos da tabela
        frame.tabela.getSelectionModel().addListSelectionListener(frame.listener);
        frame.tabela.getColumnModel().getSelectionModel().addListSelectionListener(frame.listener);

        //Configura o comportamento dos botões da interface
        this.limparGUI();
        this.verificaAcessos();

    }

    /**
     * Verifica o acesso do usuário para utilizar este frame
     */
    public void verificaAcessos() {

        //Verifica a permissao de @user para este frame
        if (!FramePrincipal.acessos.get(FramePrincipal.PERMISSAO_ALTERA_CONTAS_PAGAR).isPermissao()) {
            EnabledGUIElements.disabledJComponent(frame.buttonCadastrar, frame.buttonAlterar, frame.buttonQuitar);
        }
    }

    /**
     * Determina o comportamento do InternalFrame
     *
     * @param quitada
     */
    public void guiAtiva(boolean quitada) {

        //Verifica se o objeto consultado e configura o comportamento da interface
        if (quitada) {
            EnabledGUIElements.disabledJComponent(frame.textDescricao, frame.textObservacao, frame.textPagamentoDia, frame.textPagamentoHora,
                    frame.textValor, frame.textVencimento, frame.buttonCadastrar, frame.buttonAlterar,
                    frame.buttonQuitar);
            EnabledGUIElements.enabledJComponent(frame.buttonLimpar);
            frame.textMensagem.setText("Esta conta já está quitada/paga.");
        } else {
            EnabledGUIElements.enabledJComponent(frame.textDescricao, frame.textObservacao,
                    frame.textValor, frame.textVencimento, frame.buttonCadastrar, frame.buttonAlterar,
                    frame.buttonQuitar);
            frame.textMensagem.setText("Esta conta ainda não foi quitada/paga.");
        }
        frame.buttonCadastrar.setEnabled(false);
        this.verificaAcessos();
    }

    /**
     * Preenche o InternalFrame a partir da interação com a tabela
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

            List<Contaspagar> consulta = AbstractDAO.consultar("Contaspagar", "idConta=" + cod);

            if (consulta.size() > 0) {
                Contaspagar c = consulta.get(0);

                frame.textCodigo.setText(Integer.toString(c.getIdConta()));
                frame.textVencimento.setText(ConsertaBugsGUI.getDataFormatadaNoBd(c.getDataVencimento(), "dd/MM/yyyy"));
                frame.textDescricao.setText(c.getDescricao());
                frame.textValor.setText(c.getValor().toString());

                if (c.getObservacao() != null) {
                    frame.textObservacao.setText(c.getObservacao().toString());
                } else {
                    frame.textObservacao.setText("");
                }

                if (c.getCompra() != null) {
                    frame.textCompra.setText(c.getCompra().getIdCompra().toString());
                } else {
                    frame.textCompra.setText("");
                }

                if (c.getDataPagamento() != null) {
                    frame.textPagamentoDia.setText(ConsertaBugsGUI.getDataFormatadaNoBd(c.getDataPagamento(), "dd/MM/yyyy"));
                } else {
                    frame.textPagamentoDia.setText("");
                }

                if (c.getHoraPagamento() != null) {
                    frame.textPagamentoHora.setText(c.getHoraPagamento().toString());
                } else {
                    frame.textPagamentoHora.setText("");
                }

                if (c.isQuitada()) {
                    frame.checkQuitada.setSelected(true);
                    frame.buttonQuitar.setEnabled(false);
                    frame.buttonExcluir.setEnabled(false);
                } else {
                    frame.checkQuitada.setSelected(false);
                    frame.buttonQuitar.setEnabled(true);
                    frame.buttonExcluir.setEnabled(true);
                }

                //Configura o comportamento dos botões da interface
                this.guiAtiva(c.isQuitada());

            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            // ex.printStackTrace();
        }
    }

    /**
     * Cria um objeto compra a partir dos dados fornecidos aos elementos deste
     * frame
     *
     * @param data - data que será armazenada com o objeto
     * @return - retorna o objeto Compra obtido pelo frame
     * @throws NumberFormatException
     */
    public Contaspagar criaConta(int opcao) {

        //Verifica se o cliente confirmou que deseja efetuar o cadastro
        if (opcao == JOptionPane.YES_OPTION) {

            try {

                Compra compra = null;
                Date vencimento = Dates.getDate(frame.textVencimento.getText());
                Date dataPagamento = Dates.getDate(frame.textPagamentoDia.getText());
                Time horaPagamento = Horas.getTime(frame.textPagamentoHora.getText());
                String descricao = frame.textDescricao.getText();
                String observacao = frame.textObservacao.getText();
                BigDecimal valor = new BigDecimal(frame.textValor.getText().replaceAll("\\.", "").replaceAll(",", "\\."));
                boolean quitada = false;

                if (vencimento != null) {
                    if (descricao != null && !descricao.trim().equals("")) {
                        if (valor != null) {

                            //Captura os atributos do InternalFrame e inicializa as variáveis com seus devidos valores
                            //Verifica se a Conta a pagar está associada a uma compra
                            if (ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(frame.textCompra) != 0) {
                                compra = (Compra) AbstractDAO.consultar("Compra", "idCompra=" + ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(frame.textCompra)).get(0);
                            }

                            //Verifica se o ano do vencimento não tem quatro dígitos
                            if (vencimento.getTime() <= Dates.subYears(Dates.getDataHoje(), 100).getTime()) {
                                //Soma dois mil anos ao ano do vencimento
                                vencimento = Dates.addYears(vencimento, 2000);
                            }

                            //Captura se a conta esta quitada
                            if (frame.checkQuitada.isSelected()) {
                                quitada = true;
                            } else {
                                quitada = false;
                            }

                            //Cria o objeto
                            Contaspagar conta = new Contaspagar(compra, descricao, observacao, vencimento, dataPagamento, horaPagamento, valor, quitada);

                            //Captura o código na GUI
                            if (ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(frame.textCodigo) != 0) {
                                conta.setIdConta(ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(frame.textCodigo));
                            }

                            //Retorna a nova compra
                            return conta;


                        } else {
                            JOptionPane.showMessageDialog(frame, "Por favor, preencha o valor da conta.", "Infomações incompletas", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Por favor, preencha a descrição da conta.", "Infomações incompletas", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Por favor, preencha o vencimento da conta.", "Infomações incompletas", JOptionPane.WARNING_MESSAGE);
                }

            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(frame, "Por favor, preencha a data de vencimento corretamente!", "Erro", JOptionPane.ERROR_MESSAGE);
                //ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Limpa a gui
     */
    public void limparGUI() {

        this.buscar();
        new ApagaElementosDaInterface().apagaJTextField(frame.textCodigo, frame.textCompra, frame.textVencimento, frame.textDescricao, frame.textObservacao,
                frame.textValor, frame.textPagamentoDia, frame.textPagamentoHora);
        frame.textValor.setText("0");
        frame.checkQuitada.setSelected(false);
        frame.tabela.clearSelection();

        //Torna a GUI completamente editável novamente*
        this.guiAtiva(false);
        EnabledGUIElements.enabledJComponent(frame.buttonCadastrar);
        EnabledGUIElements.disabledJComponent(frame.buttonAlterar, frame.buttonQuitar, frame.buttonExcluir);

        this.verificaAcessos();
    }

    public void listarTodosElementosNaTabela() {

        List<Contaspagar> list = AbstractDAO.listar("Contaspagar", "dataVencimento");
        listarPesquisaNaTabela(list);

    }

    /**
     * Lista na tabela os objetos em @list
     *
     * @param list - Lista de objetos
     */
    public void listarPesquisaNaTabela(List<Contaspagar> list) {

        //Inicializa as colunas da tabela
        TableColumn coluna1 = frame.tabela.getColumnModel().getColumn(0);
        TableColumn coluna2 = frame.tabela.getColumnModel().getColumn(1);
        TableColumn coluna3 = frame.tabela.getColumnModel().getColumn(2);
        TableColumn coluna4 = frame.tabela.getColumnModel().getColumn(3);
        TableColumn coluna5 = frame.tabela.getColumnModel().getColumn(4);

        //Dimensiona as colunas na tabela
        coluna1.setPreferredWidth(60);
        coluna2.setPreferredWidth(350);
        coluna3.setPreferredWidth(70);
        coluna4.setPreferredWidth(70);
        coluna5.setPreferredWidth(80);

        //Inicializa o modelo declarado acima
        DefaultTableModel modelo = (DefaultTableModel) frame.tabela.getModel();
        modelo.setNumRows(0);

        if (list != null) {

            //Inicializa o DefaultTableCellRender modificado para personalizar algumas células
            DefaultTableCellRenderer renderer = new RendererTable(list);

            try {
                //Preenche a tabela com os elementos de @list
                for (int i = 0; i < list.size(); i++) {
                    Contaspagar c = list.get(i);
                    modelo.addRow(new Object[]{c.getIdConta(), c.getDescricao(), ConsertaBugsGUI.getDataFormatadaNoBd(c.getDataVencimento(), "dd/MM/yyyy"),
                                ConsertaBugsGUI.getDataFormatadaNoBd(c.getDataPagamento(), "dd/MM/yyyy"), "R$ " + df.format(c.getValor())});

                    //Altera as propriedades das células desta linha de acordo com o estado de conclusão da transação atual
                    coluna1.setCellRenderer(renderer);
                    coluna2.setCellRenderer(renderer);
                    coluna3.setCellRenderer(renderer);
                    coluna4.setCellRenderer(renderer);
                    coluna5.setCellRenderer(renderer);
                }
            } catch (Exception er) {
                JOptionPane.showMessageDialog(frame, "Erro na listagem!\n", "Erro!", JOptionPane.ERROR_MESSAGE);
                er.printStackTrace();

            }
        }
    }

    public void cadastrar() {

        //Pergunta se o cliente realmente deseja inserir a transação
        int opcao = JOptionPane.showOptionDialog(frame, "Tem certeza que deseja efetuar o cadastro da conta a pagar?", "Cadastrar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                frame.botoesOpcoes, frame.botoesOpcoes[0]);

        //O campo código está vazio, ou seja, verifica se esta não é uma transação existente
        if (frame.textCodigo.getText().isEmpty()) {
            //Realiza o cadastro no banco de dados*
            if (this.criaConta(opcao) != null) {
                Contaspagar c = this.criaConta(opcao);

                //Verifica o peso da tara e o bruto
                if (AbstractDAO.inserir(c)) {
                    //Preenche o campo com o código da nova transação no banco de dados
                    List<Contaspagar> list = AbstractDAO.listar("Contaspagar");
                    frame.textCodigo.setText(Integer.toString(AbstractDAO.max(Contaspagar.class, "idConta") + 1));

                    //Registra a transação do usuário
                    DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Cadastrou a Conta a Pagar " + AbstractDAO.max(Contaspagar.class, "idConta")));

                    limparGUI();
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Esta conta já existe portanto não é possível cadastrá-la, somente alterá-la!",
                    "Atenção!", JOptionPane.WARNING_MESSAGE);
        }
        //Relista as transações na tabela*
        buscar();

    }

    public void alterar() {

        //Pergunta se o usuário deseja realizar a alteração
        int opcao = JOptionPane.showOptionDialog(frame, "Tem certeza que deseja alterar esta conta?", "Alterar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, frame.botoesOpcoes, frame.botoesOpcoes[0]);

        try {
            //Verifica se alguma transação foi selecionada e o campo do código está preenchido*
            if (!frame.textCodigo.getText().equals("")) {

                Contaspagar c = criaConta(opcao);

                //Realiza a alteração no BD a partir do id*
                if (c != null) {
                    if (AbstractDAO.alterar(c)) {
                        //Registra a transação do usuário
                        DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Alterou a Conta a Pagar " + c.getIdConta()));
                        buscar();
                        limparGUI();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Escolha uma conta na tabela para alterar.",
                        "Atenção!", JOptionPane.WARNING_MESSAGE);
            }


        } catch (NumberFormatException er) {
            JOptionPane.showMessageDialog(frame, "Valor numérico digitado de forma inválida, favor tentar novamente!\n\n" + er,
                    "Erro", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
            new ApagaElementosDaInterface().apagaJTextField(frame.textValor);

        }
    }

    /**
     * Exclui uma única conta
     */
    public void excluir() {

        int opcao = JOptionPane.showOptionDialog(frame, "Tem certeza que deseja excluir esta conta a pagar?", "Excluir",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, frame.botoesOpcoes, frame.botoesOpcoes[0]);

        //Verifica se a conta cadastrada foi selecionada
        if (ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(frame.textCodigo) != 0) {

            Contaspagar conta = this.criaConta(opcao);

            //Verifica se a conta ainda não foi quitada
            if (!conta.isQuitada()) {
                AbstractDAO.excluir(conta);

                //Registra a transação do usuário
                DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Excluiu a Conta a Pagar " + conta.getIdConta()));

                limparGUI();
                buscar();
            } else {
                JOptionPane.showMessageDialog(frame, "A conta já foi quitada e por isto não pode ser excluída.", "Atenção!", JOptionPane.WARNING_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(frame, "Selecione na tabela uma conta a pagar já cadastrada para ser excluída.",
                    "Atenção!", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Quita a compra
     */
    public void quitar() {

        //Verifica caixa diário
        Caixadiario.verificaCaixasBd();

        BigDecimal valor = new BigDecimal(frame.textValor.getText().replaceAll("\\.", "").replaceAll(",", "\\."));

        int opcao = JOptionPane.showOptionDialog(frame, "Você tem certeza que deseja quitar esta conta a pagar com o valor de R$" + valor.toString() + "?\n", "Quitar conta a pagar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                frame.botoesOpcoes, frame.botoesOpcoes[0]);

        if (opcao == JOptionPane.YES_OPTION) {

            //Verifica se tem uma conta carregada na interface
            if (!frame.textCodigo.getText().equals("")) {
                //Captura a conta no bd
                Contaspagar conta = this.criaConta(opcao);
                Contaspagar contaBd = (Contaspagar) AbstractDAO.consultar("Contaspagar", "idConta=" + conta.getIdConta()).get(0);

                //Verifica se a conta ainda não está quitada
                if (!contaBd.isQuitada() && !frame.checkQuitada.isSelected()) {

                    //Quita a conta e grava no bd
                    conta.setQuitada(true);
                    conta.setDataPagamento(Dates.getDataHoje());
                    conta.setHoraPagamento(Horas.getHoraAgora());

                    //Recupera no bd o caixa do dia presente
                    List<Caixadiario> listCaixa = AbstractDAO.consultar("Caixadiario", "data='" + Dates.getDataHoje() + "'");

                    //Verifica se o caixa de hoje foi encontrado
                    if (!listCaixa.isEmpty()) {

                        Caixadiario cx = listCaixa.get(0);

                        //Verifica se o caixa está aberto
                        if (!cx.isFechado()) {

                            if (AbstractDAO.alterar(conta)) {

                                //Registra a transação do usuário
                                DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Quitou a Conta a Pagar " + conta.getIdConta()));

                                //Grava um CaixadiarioHasContasPagar no bd
                                CaixadiarioHasContaspagar chp = new CaixadiarioHasContaspagar(cx, conta);
                                AbstractDAO.inserir(chp);
                            }

                        } else {
                            JOptionPane.showMessageDialog(frame, "O caixa de hoje encontra-se fechado, por isso não é possível quitar novas contas hoje.",
                                    "Atenção", JOptionPane.WARNING_MESSAGE);
                        }
                        
                        buscar();

                    } else {

                        //Busca o último caixa aberto no bd
                        listCaixa = AbstractDAO.consultar("Caixadiario", "fechado=false order by data desc");
                        String str2 = "Não é possível quitar esta conta pois o caixa de hoje ainda não foi criado pelo sistema.\n";

                        if (!listCaixa.isEmpty()) {
                            str2 += "Feche o caixa do dia " + ConsertaBugsGUI.getDataFormatadaNoBd(listCaixa.get(0).getData(), "dd/MM/yyyy")
                                    + " para que o sistema possa gerar o caixa de hoje.";
                        }
                        //Notifica o usuário
                        JOptionPane.showMessageDialog(frame, str2, "Atenção!", JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(frame, "Não foi possível quitar a conta.", "ERRO!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Esta conta já se encontra quitada.", "Conta quitada", JOptionPane.WARNING_MESSAGE);
            }
        }
        buscar();
    }

    public boolean verificaDataPagamento() {
        if (frame.radioBuscaPagamentoQuitada.isSelected()) {
            EnabledGUIElements.enabledJComponent(frame.textBuscaPagamentoDataDe, frame.textBuscaPagamentoAte);
        } else {
            EnabledGUIElements.disabledJComponent(frame.textBuscaPagamentoDataDe, frame.textBuscaPagamentoAte);
            new ApagaElementosDaInterface().apagaJTextField(frame.textBuscaPagamentoDataDe, frame.textBuscaPagamentoAte);
        }

        return true;
    }

    public boolean verificaDataVencimento() {
        if (frame.radioBuscaVencimentoEntre.isSelected()) {
            EnabledGUIElements.enabledJComponent(frame.textBuscaVencimentoDe, frame.textBuscaVencimentoAte);
        } else {
            EnabledGUIElements.disabledJComponent(frame.textBuscaVencimentoDe, frame.textBuscaVencimentoAte);
            new ApagaElementosDaInterface().apagaJTextField(frame.textBuscaVencimentoDe, frame.textBuscaVencimentoAte);
        }

        return true;
    }

    public boolean buscar() {

        try {

            //Captura os parametros do frame
            String id = frame.textBuscaCodigo.getText();
            String desc = frame.textBuscaDescricao.getText();
            String dePagamento = frame.textBuscaPagamentoDataDe.getText();
            String atePagamento = frame.textBuscaPagamentoAte.getText();
            String deVencimento = frame.textBuscaVencimentoDe.getText();
            String ateVencimento = frame.textBuscaVencimentoAte.getText();

            //Realiza a busca no bd
            List<Contaspagar> list = null;

            //Incrementa a string para consutar a busca
            String query = "";
            if (id != null && !id.isEmpty()) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                query += " idConta=" + id;
            }
            if (desc != null && !desc.isEmpty()) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                query += " descricao like '%" + desc + "%'";
            }
            if (dePagamento != null && !dePagamento.isEmpty() && !dePagamento.equals("  /  /    ")
                    && atePagamento != null && !atePagamento.isEmpty() && !atePagamento.equals("  /  /    ")) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                Date dateDe = Dates.getDate(dePagamento);
                Date dateAte = Dates.getDate(atePagamento);
                query += " dataPagamento >= '" + dateDe.toString() + "' AND dataPagamento <='" + dateAte.toString() + "'";
            }
            if (deVencimento != null && !deVencimento.isEmpty() && !deVencimento.equals("  /  /    ")
                    && ateVencimento != null && !ateVencimento.isEmpty() && !ateVencimento.equals("  /  /    ")) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                Date dateDe = Dates.getDate(deVencimento);
                Date dateAte = Dates.getDate(ateVencimento);
                query += " dataVencimento >= '" + dateDe.toString() + "' AND dataVencimento <='" + dateAte.toString() + "'";
            }
            if (frame.radioBuscaPagamentoQuitada.isSelected()) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                query += " quitada=TRUE";
            }
            if (frame.radioBuscaPagamentoAberto.isSelected()) {
                if (query.length() > 0) {
                    query += " AND ";
                }
                query += " quitada=FALSE";
            }

            if (!query.equals("")) {
                //System.out.println(query);
                list = AbstractDAO.consultar("Contaspagar", query);
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

        new ApagaElementosDaInterface().apagaJTextField(frame.textBuscaCodigo, frame.textBuscaDescricao,
                frame.textBuscaPagamentoAte, frame.textBuscaPagamentoDataDe, frame.textBuscaVencimentoAte, frame.textBuscaVencimentoDe);
        frame.radioBuscaPagamentoTodas.setSelected(true);
        frame.radioBuscaVencimentoTodos.setSelected(true);
        this.verificaDataPagamento();
        this.verificaDataVencimento();
        this.limparGUI();
        this.listarPesquisaNaTabela(null);
    }
}
