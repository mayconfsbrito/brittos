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
import java.text.ParseException;
import br.com.areiasbrittos.gui.FramePrincipal;
import br.com.areiasbrittos.gui.internalFrames.InternalFrameContasPagar;
import br.com.areiasbrittos.gui.internalFrames.InternalFrameContasReceber;
import br.com.areiasbrittos.gui.internalFrames.InternalFrameMovimentoCaixa;
import br.com.areiasbrittos.gui.utils.ApagaElementosDaInterface;
import br.com.areiasbrittos.gui.utils.ConsertaBugsGUI;
import br.com.areiasbrittos.gui.utils.EnabledGUIElements;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import br.com.areiasbrittos.persistencia.ConnectionFactory_brittos_bd;
import br.com.areiasbrittos.persistencia.dao.AbstractDAO;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOTransacao;

/**
 *
 * @author Maycon Fernando Silva Brito @email mayconfsbrito@gmail.com
 */
public class ControleIFMovimentoCaixaDiario {

    /*
     * Variáveis da classe
     */
    private InternalFrameMovimentoCaixa frame;
    private DecimalFormat df = new DecimalFormat("###,##0.00");

    public ControleIFMovimentoCaixaDiario(InternalFrameMovimentoCaixa frame) {
        this.frame = frame;
    }

    /**
     * Inicializa o InternalFrame
     *
     * @param frame
     */
    public void inicializa() {

        //Inicializa TextFields
        frame.textMensagem.setEditable(false);
        frame.textMensagem.setText("Aqui são gerenciados os Movimentos de Caixa Diário.");
        frame.textSaldoCreditoTotal.setText(0 + "");
        frame.textSaldoDebitoTotal.setText(0 + "");
        frame.textSaldoTotal.setText(0 + "");

        //Realiza as devidas verificações no bd
        Caixadiario.verificaCaixasBd();

        //Configura o comportamento dos botões da interface
        this.limparGUI();
        this.verificaAcessos();
    }

    /**
     * Verifica o acesso do usuário para utilizar este frame
     */
    public void verificaAcessos() {

        //Verifica a permissao de @user para este frame
        if (!FramePrincipal.acessos.get(FramePrincipal.PERMISSAO_ALTERA_CAIXA_DIARIO).isPermissao()) {
            EnabledGUIElements.disabledJComponent(frame.buttonFechar, frame.buttonApagar, frame.buttonAreceber,
                    frame.radioAberto, frame.radioFechado, frame.buttonDesfazer);
        }

        //Verifica a permissao de @user para imprimir o relatório
        if (!FramePrincipal.acessos.get(FramePrincipal.PERMISSAO_RELATORIO_CAIXA_DIARIO).isPermissao()) {
            EnabledGUIElements.disabledJComponent(frame.buttonImprimir);
        }
    }

    /**
     * Determina o comportamento do InternalFrame
     *
     * @param fechado
     */
    public void guiAtiva(boolean fechado) {

        //Verifica se o objeto consultado e configura o comportamento da interface
        if (fechado) {
            EnabledGUIElements.disabledJComponent(frame.buttonFechar, frame.buttonApagar, frame.buttonAreceber, frame.radioAberto, frame.radioFechado,
                    frame.buttonApagar, frame.buttonAreceber, frame.buttonDesfazer);
            frame.textMensagem.setText("O caixa deste dia já está fechado e não pode ser movimentado.");
            frame.radioFechado.setSelected(true);
            frame.buttonImprimir.setEnabled(true);
        } else {
            EnabledGUIElements.enabledJComponent(frame.buttonFechar, frame.buttonApagar, frame.buttonAreceber, frame.radioAberto, frame.radioFechado,
                    frame.buttonApagar, frame.buttonAreceber, frame.buttonDesfazer, frame.buttonImprimir);
            frame.textMensagem.setText("O caixa deste dia ainda está em aberto e pode ser movimentado.");
            frame.radioAberto.setSelected(true);
        }
        this.verificaAcessos();
    }

    /**
     * Limpa a gui
     */
    public void limparGUI() {

        frame.tabela.clearSelection();
        this.listarPesquisaNaTabela(null);
        new ApagaElementosDaInterface().apagaJTextField(frame.textDataConsulta, frame.textHoraFechamento, frame.textSaldoCreditoTotal,
                frame.textSaldoDebitoTotal, frame.textSaldoTotal, frame.textCodigo);
        EnabledGUIElements.disabledJComponent(frame.buttonImprimir, frame.buttonFechar, frame.buttonApagar, frame.buttonAreceber, frame.buttonDesfazer);
        frame.radioAberto.setSelected(false);

        //Torna a GUI completamente editável novamente*
        this.guiAtiva(true);
        frame.buttonImprimir.setEnabled(false);
        this.verificaAcessos();
    }

    /**
     * Lista na tabela os objetos em @list
     *
     * @param list - Lista de objetos
     */
    public void listarPesquisaNaTabela(Caixadiario caixa) {

        //Inicializa as colunas da tabela
        TableColumn coluna1 = frame.tabela.getColumnModel().getColumn(0);
        TableColumn coluna2 = frame.tabela.getColumnModel().getColumn(1);
        TableColumn coluna3 = frame.tabela.getColumnModel().getColumn(2);
        TableColumn coluna4 = frame.tabela.getColumnModel().getColumn(3);
        TableColumn coluna5 = frame.tabela.getColumnModel().getColumn(4);
        TableColumn coluna6 = frame.tabela.getColumnModel().getColumn(5);

        //Dimensiona as colunas na tabela
        coluna1.setPreferredWidth(60);
        coluna2.setPreferredWidth(60);
        coluna3.setPreferredWidth(40);
        coluna4.setPreferredWidth(300);
        coluna5.setPreferredWidth(60);
        coluna6.setPreferredWidth(60);

        //Inicializa o modelo declarado acima
        DefaultTableModel modelo = (DefaultTableModel) frame.tabela.getModel();
        modelo.setNumRows(0);

        if (caixa != null) {

            ArrayList<CaixadiarioHasContaspagar> listPagar = new ArrayList(caixa.getCaixadiarioHasContaspagars());
            ArrayList<CaixadiarioHasContasreceber> listReceber = new ArrayList(caixa.getCaixadiarioHasContasrecebers());

            try {
                
                Collections.sort(listPagar);
                Collections.sort(listReceber);
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {

                //Preenche a tabela com os elementos das listas ordenada por hora
                int contPagar = 0;
                int contReceber = 0;
                while (contPagar < listPagar.size() || contReceber < listReceber.size()) {

                    if (listPagar.size() == contPagar) {
                        Contasreceber c = listReceber.get(contReceber).getContasreceber();
                        modelo.addRow(new Object[]{"", c.getIdConta(), c.getHoraPagamento().toString(), c.getDescricao(), "R$ " + df.format(c.getValor()), ""});
                        contReceber++;

                    } else if (listReceber.size() == contReceber) {
                        Contaspagar c = listPagar.get(contPagar).getContaspagar();
                        modelo.addRow(new Object[]{c.getIdConta(), "", c.getHoraPagamento().toString(), c.getDescricao(), "", "R$ " + df.format(c.getValor())});
                        contPagar++;

                    } else {

                        if (listPagar.get(contPagar).getContaspagar().getHoraPagamento().before(listReceber.get(contReceber).getContasreceber().getHoraPagamento())) {
                            Contaspagar c = listPagar.get(contPagar).getContaspagar();
                            modelo.addRow(new Object[]{c.getIdConta(), "", c.getHoraPagamento().toString(), c.getDescricao(), "", "R$ " + df.format(c.getValor())});
                            contPagar++;

                        } else {
                            Contasreceber c = listReceber.get(contReceber).getContasreceber();
                            modelo.addRow(new Object[]{"", c.getIdConta(), c.getHoraPagamento().toString(), c.getDescricao(), "R$ " + df.format(c.getValor()), ""});
                            contReceber++;
                        }

                    }
                }

            } catch (Exception er) {
                JOptionPane.showMessageDialog(frame, "Erro na listagem!\n", "Erro!", JOptionPane.ERROR_MESSAGE);
                er.printStackTrace();

            }

        } else {
            modelo.setRowCount(0);
        }
    }

    /**
     * Preenche a gui a partir do objeto
     */
    public void preencheGui(Caixadiario cx) {

        //Preenche os componentes com seus respectivos atributos
        frame.textCodigo.setText(cx.getIdCaixaDiario() + "");
        if (cx.isFechado()) {
            frame.radioFechado.setSelected(true);
            frame.textHoraFechamento.setText(cx.getHoraFechamento().toString());
        } else {
            frame.radioAberto.setSelected(true);
            frame.textHoraFechamento.setText("");
        }

        frame.textSaldoCreditoTotal.setText(cx.getCreditoTotal().toString());
        frame.textSaldoDebitoTotal.setText(cx.getDebitoTotalTotal().toPlainString());
        frame.textSaldoTotal.setText(df.format(cx.getSaldoTotal()));

        //Preenche a tabela deste caixa diário
        this.listarPesquisaNaTabela(cx);

        //Comportamento da gui
        this.guiAtiva(cx.isFechado());

    }

    /**
     * Recupera no bd um caixa diário a partir da data
     */
    public void consulta() {

        try {

            //Realiza a verificação de caixas diário no bd
            Caixadiario.verificaCaixasBd();

            //Recupera a data na gui
            Date data = Dates.getDate(frame.textDataConsulta.getText());

            //Verifica se foi digitada a data corretamente, se não, consulta o último caixa em aberto ou o caixa atual
            if (frame.textDataConsulta.getText().equals("  /  /    ")) {

                //Verifica o último caixa em aberto
                List<Caixadiario> list = AbstractDAO.consultar("Caixadiario", "fechado=false order by data desc");
                if (!list.isEmpty()) {
                    data = ((Caixadiario) list.get(0)).getData();
                    frame.textDataConsulta.setText(ConsertaBugsGUI.getDataFormatadaNoBd(data, "dd/MM/yyyy"));

                    //Se não existe nenhum caixa aberto, busca o caixa de hoje
                } else {
                    data = Dates.getDataHoje();
                    frame.textDataConsulta.setText(ConsertaBugsGUI.getDataFormatadaNoBd(data, "dd/MM/yyyy"));

                }
            }

            //Verifica se o ano do vencimento não tem quatro dígitos
            data = Dates.verificaDigitosAno(data);

            //Verifica se o caixa diário desta data existe
            List<Caixadiario> list = AbstractDAO.consultar(
                    "from Caixadiario c "
                    + "LEFT JOIN FETCH c.caixadiarioHasContaspagars cp "
                    + "LEFT JOIN FETCH c.caixadiarioHasContasrecebers cr "
                    + "LEFT JOIN FETCH cp.contaspagar cpp "
                    + "LEFT JOIN FETCH cr.contasreceber crr "
                    + "WHERE c.data='" + data.toString() + "'");
            if (list.size() > 0) {

                Caixadiario cx = list.get(0);

                //Preenche a gui
                this.preencheGui(cx);

            } else {
                JOptionPane.showMessageDialog(frame, "Ainda não existe nenhum movimento caixa diário cadastrado para esta data.\n"
                        + "Obs: Um novo caixa só pode ser criado pelo sistema quando todos os caixas anteriores estiverem fechados.", "Caixa inexistente!", JOptionPane.WARNING_MESSAGE);
                this.limparGUI();
            }
        } catch (ParseException er) {
            JOptionPane.showMessageDialog(frame, "Data em formato inválido. Por favor, digite novamente de maneira correta.", "Erro na data!", JOptionPane.INFORMATION_MESSAGE);
            this.limparGUI();
            er.printStackTrace();

        } catch (Exception er) {
            JOptionPane.showMessageDialog(frame, "Erro ao consultar o caixa diário para esta data, digite uma data válida.", "Erro ao consultar!", JOptionPane.INFORMATION_MESSAGE);
            this.limparGUI();
            er.printStackTrace();

        }
    }

    /**
     * Fecha o movimento de caixa atual
     */
    public void fechar() {

        int opcao = JOptionPane.showOptionDialog(frame, "Você tem certeza que deseja fechar o caixa de hoje?", "Fechar Caixa",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                frame.botoesOpcoes, frame.botoesOpcoes[0]);

        if (opcao == JOptionPane.YES_OPTION) {

            //Recupera este caixa no bd pelo código
            Caixadiario caixa = (Caixadiario) AbstractDAO.consultar(
                    "from Caixadiario c LEFT JOIN FETCH "
                    + "c.caixadiarioHasContaspagars cp LEFT JOIN FETCH "
                    + "c.caixadiarioHasContasrecebers cr LEFT JOIN FETCH "
                    + "cp.contaspagar cpp LEFT JOIN FETCH "
                    + "cr.contasreceber crr "
                    + "WHERE c.idCaixaDiario=" + frame.textCodigo.getText()).get(0);

            //Verifica se este caixa não está fechado
            if (!caixa.isFechado()) {

                //Verifica se o caixa não está com Saldo Total negativo
                if (caixa.getSaldoTotal().doubleValue() >= 0) {

                    //Altera este os atributos deste caixa
                    caixa.setHoraFechamento(Horas.getHoraAgora());
                    caixa.setFechado(true);

                    //Altera no bd
                    AbstractDAO.alterar(caixa);

                    //Registra a transação do usuário
                    DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Fechou o Caixa Diário " + caixa.getIdCaixaDiario()));

                    //Altera o comportamento da gui
                    this.guiAtiva(true);
                    frame.textHoraFechamento.setText(caixa.getHoraFechamento().toString());

                } else {
                    JOptionPane.showMessageDialog(frame, "O caixa não pode ser fechado com saldo total negativo.", "Saldo Negativo.", JOptionPane.WARNING_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(frame, "Este caixa já se encontra fechado.", "Caixa Fechado", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void imprimir() {

        if (!frame.textCodigo.getText().isEmpty()) {

            try {
                InputStream inputStream = getClass().getResourceAsStream("/relatorios/CaixaDiario.jasper");

                Map parametros = new HashMap();

                parametros.put("idCaixaDiario", Integer.parseInt(frame.textCodigo.getText()));
                FileInputStream imagem = new FileInputStream(new java.io.File("logo.png"));
                parametros.put("imagem", imagem);
                parametros.put("nome", ControleFPreferencias.conf.getNome());

                ReportUtils.openReport("Caixa Diário", inputStream, parametros, ConnectionFactory_brittos_bd.getConnection());

            } catch (Exception er) {

                JOptionPane.showMessageDialog(frame, "Erro ao criar o relatório.\n" + er, "Erro", JOptionPane.ERROR_MESSAGE);
                er.printStackTrace();

            }
        } else {
            JOptionPane.showMessageDialog(frame, "Consulte um caixa diário cadastrado para imprimir", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void adicionarContaPagar() {

        if (this.verificaCaixaConsultado()) {
            if (this.verificaCaixaAberto()) {
                InternalFrameContasPagar iFrame = new InternalFrameContasPagar();
                FramePrincipal.desktopPanel.add(iFrame);
                FramePrincipal.desktopPanel.getDesktopManager().activateFrame(iFrame);
            }
        }

    }

    public void adicionarContaReceber() {

        if (this.verificaCaixaConsultado()) {
            if (this.verificaCaixaAberto()) {
                InternalFrameContasReceber iFrame = new InternalFrameContasReceber();
                FramePrincipal.desktopPanel.add(iFrame);
                FramePrincipal.desktopPanel.getDesktopManager().activateFrame(iFrame);

            }
        }
    }

    /**
     * Desfaz o pagamento de uma determinada conta selecionada na tabela do
     * movimento de caixa
     */
    public boolean desfazerPagamento() {

        //Realiza as verificações mínimas para esta ação
        if (this.verificaCaixaConsultado()) {
            if (this.verificaCaixaAberto()) {
                if (this.verificaSelecaoTabela()) {

                    //Verifica se é uma conta a pagar ou a receber e captura o seu código
                    int linha = frame.tabela.getSelectedRow();
                    String pagar = frame.tabela.getModel().getValueAt(linha, 0).toString();
                    String receber = frame.tabela.getModel().getValueAt(linha, 1).toString();

                    //Captura no bd a conta e a altera, tornando-a ainda não quitada
                    if (!pagar.equals("")) {

                        Integer idPagar = Integer.parseInt(pagar);
                        Contaspagar conta = (Contaspagar) AbstractDAO.consultar(
                                "from Contaspagar c LEFT JOIN FETCH "
                                + "c.caixadiarioHasContaspagars cp "
                                + "WHERE c.idConta=" + idPagar).get(0);

                        Caixadiario cx = (Caixadiario) AbstractDAO.consultar(
                                "from Caixadiario c LEFT JOIN FETCH "
                                + "c.caixadiarioHasContaspagars cp LEFT JOIN FETCH "
                                + "c.caixadiarioHasContasrecebers cr LEFT JOIN FETCH "
                                + "cp.contaspagar cpp LEFT JOIN FETCH "
                                + "cr.contasreceber crr "
                                + "WHERE c.idCaixaDiario=" + frame.textCodigo.getText()).get(0);

                        //Remove o chp do bd
                        CaixadiarioHasContaspagar chp = (CaixadiarioHasContaspagar) conta.getCaixadiarioHasContaspagars().iterator().next();
                        cx.getCaixadiarioHasContaspagars().remove(chp);
                        conta.getCaixadiarioHasContaspagars().remove(chp);

                        //Altera a conta e o caixa no bd
                        if (conta.getCompra() != null) {
                            //Se for de compra, marca ela como ainda não quitada, altera no bd e exclui a conta
                            Compra compra = (Compra) AbstractDAO.consultar("Compra", "idCompra=" + conta.getCompra().getIdCompra()).get(0);
                            compra.setQuitada(false);
                            AbstractDAO.alterar(compra);

                            //Registra a transação do usuário
                            DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Desfez o Pagamento da Compra " + compra.getIdCompra()));

                            AbstractDAO.excluir(conta);

                            //Registra a transação do usuário
                            DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Excluiu a Conta a Pagar " + conta.getIdConta()));

                        } else {
                            //Se não for de nada, apenas altera a conta
                            conta.setQuitada(false);
                            conta.setHoraPagamento(null);
                            conta.setDataPagamento(null);
                            AbstractDAO.excluir(chp);
                            AbstractDAO.alterar(conta);

                            //Registra a transação do usuário
                            DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Desfez o pagamento da Conta a Pagar " + conta.getIdConta()));

                        }

                        //Altera o caixa diário
                        AbstractDAO.alterar(cx);

                        //Atualiza a consulta
                        this.consulta();

                        return true;

                    } else {

                        Integer idReceber = Integer.parseInt(receber);
                        Contasreceber conta = (Contasreceber) AbstractDAO.consultar(
                                "from Contasreceber c LEFT JOIN FETCH "
                                + "c.caixadiarioHasContasrecebers cr "
                                + "WHERE c.idConta=" + idReceber).get(0);

                        Caixadiario cx = (Caixadiario) AbstractDAO.consultar(
                                "from Caixadiario c LEFT JOIN FETCH "
                                + "c.caixadiarioHasContaspagars cp LEFT JOIN FETCH "
                                + "c.caixadiarioHasContasrecebers cr "
                                + "WHERE c.idCaixaDiario=" + frame.textCodigo.getText()).get(0);

                        //Remove o chp do bd
                        CaixadiarioHasContasreceber chp = (CaixadiarioHasContasreceber) conta.getCaixadiarioHasContasrecebers().iterator().next();
                        cx.getCaixadiarioHasContasrecebers().remove(chp);
                        conta.getCaixadiarioHasContasrecebers().remove(chp);

                        //Altera a conta e o caixa no bd
                        //Verifica se a conta é de uma pesagem
                        if (conta.getPesagem() != null) {
                            //Se for de pesagem, marca ela como ainda não quitada, altera no bd e exclui a conta
                            Pesagem pesagem = (Pesagem) AbstractDAO.consultar("Pesagem", "idPesagem=" + conta.getPesagem().getIdPesagem()).get(0);
                            AbstractDAO.excluir(conta);
                            pesagem.setQuitada(false);
                            AbstractDAO.alterar(pesagem);

                            //Registra a transação do usuário
                            DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Desfez o pagamento da Pesagem " + pesagem.getIdPesagem()));

                            //Registra a transação do usuário
                            DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Excluiu a Conta a Receber " + conta.getIdConta()));

                        } else if (conta.getVenda() != null) {
                            //Se for de venda, marca ela como ainda não quitada, altera no bd e exclui a conta
                            Venda venda = (Venda) AbstractDAO.consultar("Venda", "idVenda=" + conta.getVenda().getIdVenda()).get(0);
                            venda.setQuitada(false);
                            AbstractDAO.alterar(venda);

                            //Registra a transação do usuário
                            DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Desfez o pagamento da Venda " + venda.getIdVenda()));

                            AbstractDAO.excluir(conta);

                            //Registra a transação do usuário
                            DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Excluiu a Conta a Receber " + conta.getIdConta()));

                        } else {
                            //Se não for de, apenas altera a conta
                            conta.setQuitada(false);
                            conta.setHoraPagamento(null);
                            conta.setDataPagamento(null);
                            AbstractDAO.excluir(chp);
                            AbstractDAO.alterar(conta);
                        }

                        //Registra a transação do usuário
                        DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Alterou o Caixa Diário " + cx.getIdCaixaDiario()));

                        //Atualiza a consulta
                        this.consulta();

                        return true;
                    }
                }
            }
        }

        this.consulta();

        return false;

    }

    public boolean verificaSelecaoTabela() {

        if (frame.tabela.getSelectedRow() != -1) {
            return true;
        } else {
            JOptionPane.showMessageDialog(frame, "Selecione um elemento na tabela acima antes de realizar esta ação.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }

    /**
     * Verifica se o caixa está aberto
     *
     * @return
     */
    public boolean verificaCaixaConsultado() {

        if (!frame.textCodigo.getText().isEmpty()) {
            return true;
        } else {
            JOptionPane.showMessageDialog(frame, "Não há nenhum caixa sendo consultado no momento.", "Atenção", JOptionPane.WARNING_MESSAGE);

        }
        return false;
    }

    /**
     * Verifica se o caixa está aberto
     *
     * @return
     */
    public boolean verificaCaixaAberto() {

        if (frame.textHoraFechamento.getText().isEmpty()) {
            return true;
        } else {
            JOptionPane.showMessageDialog(frame, "Este caixa está fechado e não pode ser editado no momento", "Atenção", JOptionPane.WARNING_MESSAGE);

        }
        return false;
    }
}
