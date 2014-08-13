/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.controle.gui;

import br.com.areiasbrittos.controle.interfaces.Constantes;
import br.com.areiasbrittos.controle.objetos.Conf;
import br.com.areiasbrittos.controle.utils.Dates;
import br.com.areiasbrittos.gui.frames.frameBackup;
import br.com.areiasbrittos.gui.utils.ConsertaBugsGUI;
import br.com.areiasbrittos.persistencia.dao.AbstractDAO;

/**
 *
 * @author maycon
 */
public class ControleFrameBackup {

    private frameBackup frame;
    
    //Constantes do Frame
    public static final Integer REALIZANDO_BACKUP = 1;
    public static final Integer BACKUP_OK = 2;
    public static final Integer BACKUP_ERRO = 3;

    public ControleFrameBackup(frameBackup frame) {
        this.frame = frame;
    }

    public void inicializa() {

        frame.setLocationRelativeTo(null);
        
        new Thread(new Runnable() {

            @Override
            public void run() {
                setEstadoAviso(REALIZANDO_BACKUP);
            }}).start();
        
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                //Recupera as configurações no BD
                Conf conf = (Conf) AbstractDAO.consultar("Conf", "idConf=1").get(0);

                if (conf.isBkpAtivacao()) {
                    frame.setVisible(true);
                }

                setEstadoAviso(executaBackup(conf));

                if (!conf.isBkpAtivacao()) {
                    System.exit(0);
                }


            }
        });

        thread.start();


    }

    public Integer executaBackup(Conf conf) {

        Process processRuntime1;
        Process processRuntime2;

        try {
            //Verifica se o backup está ativo
            if (conf.isBkpAtivacao()) {

                //Verifica se o endereço da configuração não está nulo
                String endereco = ControleFPreferencias.converteBytesToString(conf.getBkpEndereco());
                if (conf.getBkpEndereco() != null) {

                    String data = ConsertaBugsGUI.getDataFormatadaNoBd(Dates.getDataHoje(), "dd-MM-yyyy");
                    String nomeBdBrittos = "\"" + endereco + "\\Backup__bd_brittos__" + data + ".sql" + "\"";
                    String nomeBdSeguranca = "\"" + endereco + "\\Backup__bd_seguranca__" + data + ".sql" + "\"";

                    processRuntime1 = Runtime.getRuntime().exec("cmd.exe /c mysqldump -u " + Constantes.BD_USER + " --password=" + Constantes.BD_PASSWORD + " --database bd_brittos > " + nomeBdBrittos);
                    processRuntime2 = Runtime.getRuntime().exec("cmd.exe /c mysqldump -u " + Constantes.BD_USER + " --password=" + Constantes.BD_PASSWORD + " --database bd_seguranca > " + nomeBdSeguranca);
                    int processComplete1 = processRuntime1.waitFor();
                    int processComplete2 = processRuntime2.waitFor();

                    if (processComplete1 == 0 && processComplete2 == 0) {
                        return BACKUP_OK;

                    } else {
                        return BACKUP_ERRO;
                    }


                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return BACKUP_ERRO;
    }

    public void setEstadoAviso(Integer estado) {

        if (estado.equals(BACKUP_OK)) {
            frame.getLabelAviso().setText("Backup realizado com sucesso!");
            frame.getLabelIcone().setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/panels/Ok.png")));
        } else if (estado.equals(BACKUP_ERRO)) {
            frame.getLabelAviso().setText("Erro ao realizar backup!");
            frame.getLabelIcone().setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/panels/Erro.png")));
        } else {
            frame.getLabelAviso().setText("Realizando backup...");
            frame.getLabelIcone().setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/panels/Backup.png")));
            frame.setEnabled(false);
            
            return;
        }

        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        frame.setEnabled(true);
        System.exit(0);

    }
}
