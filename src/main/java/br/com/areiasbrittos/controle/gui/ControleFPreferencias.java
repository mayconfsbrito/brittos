/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.controle.gui;

import br.com.areiasbrittos.controle.objetos.Conf;
import br.com.areiasbrittos.controle.objetos.seguranca.Transacao;
import br.com.areiasbrittos.gui.FramePrincipal;
import br.com.areiasbrittos.gui.internalFrames.InternalFramePreferencias;
import br.com.areiasbrittos.gui.utils.EnabledGUIElements;
import br.com.areiasbrittos.gui.utils.MyDesktopPanel;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import br.com.areiasbrittos.persistencia.ManipulaArquivos;
import br.com.areiasbrittos.persistencia.dao.AbstractDAO;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOTransacao;

/**
 *
 * @author Maycon Fernando Silva Brito @email mayconfsbrito@gmail.com
 */
public class ControleFPreferencias {

    private InternalFramePreferencias frame;
    public static Conf conf = null;

    public ControleFPreferencias(InternalFramePreferencias frame) {
        this.frame = frame;
    }

    public void inicializa() {
        preencheGui(frame);
    }

    public static boolean verificaConfiguracoes() {

        if (AbstractDAO.consultar("Conf", "idConf=1").size() == 0) {

            String str = "";
            conf = new Conf("Nunca", "Metal 1.png", str, str, str, str, str, str, str, str, false, null, false);
            conf.setIdConf(1);

            AbstractDAO.inserir(conf);

        } else {
            conf = (Conf) AbstractDAO.consultar("Conf", "idConf=1").get(0);
        }

        return true;

    }

    public void guiBkpAtiva(Conf conf) {

        if (conf.isBkpAtivacao()) {
            frame.checkBkpAtivacao.setSelected(true);
            frame.checkBkpAviso.setSelected(true);
            EnabledGUIElements.enabledJComponent(frame.buttonAlterar, frame.checkBkpAviso);
            frame.textBkpEnderecoBackup.setText("");

            if (conf.getBkpEndereco() != null) {
                frame.textBkpEnderecoBackup.setText(converteBytesToString(conf.getBkpEndereco()));
            }

        } else {
            frame.checkBkpAtivacao.setSelected(false);
            frame.checkBkpAviso.setSelected(false);
            EnabledGUIElements.disabledJComponent(frame.buttonAlterar, frame.checkBkpAviso);
            frame.textBkpEnderecoBackup.setText("");
        }
    }

    public void guiBkpAtiva(JCheckBox checkBox) {

        if (checkBox.isSelected()) {
            EnabledGUIElements.enabledJComponent(frame.buttonAlterar, frame.checkBkpAviso);

            if (frame.textBkpEnderecoBackup.getText().isEmpty()) {
                frame.checkBkpAviso.setSelected(true);
            }

        } else {
            EnabledGUIElements.disabledJComponent(frame.buttonAlterar, frame.checkBkpAviso);
            frame.checkBkpAviso.setSelected(false);
        }
    }

    /**
     * Le a tabela de configuracao e carrega na GUI as preferencias salvas
     *
     * @param comboTransacoes
     * @return
     */
    public boolean preencheGui(InternalFramePreferencias frame) {

        conf = (Conf) AbstractDAO.consultar("Conf", "idConf=1").get(0);

        //Carrega a preferencia do tempo de duracao dos registros de transacoes dos usuarios
        frame.comboDuracao.setSelectedItem(conf.getValidadeTransacoes());

        //Carrega a preferencia da imagem de deskop
        frame.comboImagem.setSelectedItem(conf.getImagem().replaceAll(".png", ""));

        //Carrega as informações da empresa
        frame.textNome.setText(conf.getNome());
        frame.textLogradouro.setText(conf.getLogradouro());
        frame.textNumero.setText(conf.getNumero());
        frame.textBairro.setText(conf.getBairro());
        frame.textCidade.setText(conf.getCidade());
        frame.textCep.setText(conf.getCep());
        frame.textCnpj.setText(conf.getCnpj());
        frame.textTelefone.setText(conf.getTelefone());

        //Preenche as informações de backup
        this.guiBkpAtiva(conf);


        return true;
    }

    /**
     * Salva as preferencias definidas na gui
     *
     * @return
     */
    public boolean salvaPreferencias(InternalFramePreferencias frame) {

        byte[] bkpEndereco = null;

        //Verifica se o backup automático foi marcado
        if (frame.checkBkpAtivacao.isSelected()) {
            try {

                //Verifica se o endereço de backup foi preenchido
                if (frame.textBkpEnderecoBackup.getText().length() > 0) {
                    bkpEndereco = frame.textBkpEnderecoBackup.getText().getBytes();

                } else {
                    JOptionPane.showMessageDialog(frame, "Defina o endereço de armazenamento dos backups automáticos.", "Atenção!",
                            JOptionPane.WARNING_MESSAGE);

                    return false;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erro ao armazenar o endereço de armazenamento de backup.", "Erro!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        conf = new Conf(frame.comboDuracao.getSelectedItem().toString(), frame.comboImagem.getSelectedItem().toString() + ".png",
                frame.textNome.getText(), frame.textLogradouro.getText(), frame.textNumero.getText(), frame.textBairro.getText(),
                frame.textCep.getText(), frame.textCidade.getText(), frame.textTelefone.getText(), frame.textCnpj.getText(),
                frame.checkBkpAtivacao.isSelected(), bkpEndereco, frame.checkBkpAviso.isSelected());
        conf.setIdConf(1);

        AbstractDAO.alterar(conf);

        //Registra a transação do usuário
        DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Alterou as configurações do sistema"));

        //Carrega o desktopPanel com a imagem padrão
        ((MyDesktopPanel) FramePrincipal.desktopPanel).setImgBackground(frame.comboImagem.getSelectedItem().toString() + ".png");


        frame.dispose();

        return true;
    }

    /**
     * Realiza a troca do logotipo
     */
    public void copiarLogotipo(MyDesktopPanel panelLogo, Component frame) {

        try {

            File origem = null;
            File destino = new File("logo.png");
            JFileChooser fileChooser = new JFileChooser();


            fileChooser.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "gif", "png", "bmp"));
            fileChooser.setDialogTitle("Escolha o logotipo:");
            int retorno = fileChooser.showOpenDialog(null);

            if (retorno == JFileChooser.APPROVE_OPTION) {
                origem = fileChooser.getSelectedFile().getAbsoluteFile();
            }

            ManipulaArquivos.copyFile(origem, destino);

            panelLogo.setImgBackground(new File("logo.png"));
            panelLogo.repaint();
            FramePrincipal.carregarLogotipo();

            JOptionPane.showMessageDialog(frame, "Logotipo trocado com sucesso!", "Logotipo",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Erro durante a cópia da imagem.\n" + ex, "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public static Map preencheParametrosRelatorio(Map parametros) {

        parametros.put("nome", conf.getNome());
        parametros.put("logradouro", conf.getLogradouro());
        parametros.put("bairro", conf.getBairro());
        parametros.put("numero", conf.getNumero());
        parametros.put("cidade", conf.getCidade());
        parametros.put("cep", conf.getCep());
        parametros.put("cnpj", conf.getCnpj());
        parametros.put("telefone", conf.getTelefone());

        return parametros;

    }

    public void eventoLocalizarArquivoBackup(JTextField textEnderecoArquivo) {

        File origem = null;
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle("Escolha o diretório para backup:");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int retorno = fileChooser.showOpenDialog(null);

        if (retorno == JFileChooser.APPROVE_OPTION) {
            origem = fileChooser.getSelectedFile().getAbsoluteFile();
            textEnderecoArquivo.setText(origem.getAbsolutePath());
        }

    }

    public void restaurarBackup() {
        /**
         * String nomeArquivo = frame.textBkpEnderecoRestaurar.getText();
         * Process runtimeProcess;
         *
         * try {
         *
         * //Verifica se o endereço do arquivo não está vazio if
         * (!nomeArquivo.isEmpty()) {
         *
         * runtimeProcess = Runtime.getRuntime().exec("cmd.exe /c mysql -uroot
         * -padmin < " + nomeArquivo); int processComplete =
         * runtimeProcess.waitFor();
         *
         * if(processComplete == 0){ JOptionPane.showMessageDialog(frame,
         * "Restauração realizada com sucesso!", "Restauração!",
         * JOptionPane.INFORMATION_MESSAGE); } else {
         * JOptionPane.showMessageDialog(frame, "Erro ao restaurar o script de
         * backup do arquivo " + nomeArquivo, "Erro!",
         * JOptionPane.ERROR_MESSAGE); }
         *
         * } else { JOptionPane.showMessageDialog(frame, "Atenção! Selecione um
         * arquivo de backup para ser restaurado.", "Atenção!",
         * JOptionPane.WARNING_MESSAGE); }
         *
         * } catch (Exception ex) { JOptionPane.showMessageDialog(frame, "Erro ao
         * restaurar o script de backup do arquivo " + nomeArquivo, "Erro!",
         * JOptionPane.ERROR_MESSAGE); ex.printStackTrace(); }
         */
    }

    public static String converteBytesToString(byte[] bkpEndereco) {
        try {
            if (bkpEndereco != null) {
                return new String(bkpEndereco);
            }

            return "";

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO", "Erro ao armazenar/carregar o endereço de backup!", JOptionPane.ERROR_MESSAGE);

            return "";
        }
    }
}
