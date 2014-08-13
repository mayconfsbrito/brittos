/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.controle.testes;

import br.com.areiasbrittos.controle.interfaces.Constantes;
import br.com.areiasbrittos.controle.utils.Dates;
import br.com.areiasbrittos.gui.utils.ConsertaBugsGUI;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Maycon Fernando Silva Brito @email mayconfsbrito@gmail.com
 */
public class Teste {

    public static void main(String[] args) throws IOException {

        try {

            InputStream inputStream = Teste.class.getResourceAsStream("/relatorios/PorEntidade.jasper");
            System.out.println(inputStream);
            System.exit(0);

            Process processRuntime1;
            Process processRuntime2;

            String data = ConsertaBugsGUI.getDataFormatadaNoBd(Dates.getDataHoje(), "dd-MM-yyyy");
            String nomeBdBrittos = "\"H:/Backup Brittos" + "/Backup__bd_brittos__" + data + ".sql\"";
            String nomeBdSeguranca = "\"H:/Backup Brittos" + "/Backup__bd_seguranca__" + data + ".sql\"";

            processRuntime1 = Runtime.getRuntime().exec("cmd.exe /c mysqldump -u " + Constantes.BD_USER + " --password=" + Constantes.BD_PASSWORD + " --database bd_brittos > " + nomeBdBrittos);
            processRuntime2 = Runtime.getRuntime().exec("cmd.exe /c mysqldump -u " + Constantes.BD_USER + " --password=" + Constantes.BD_PASSWORD + " --database bd_seguranca > " + nomeBdSeguranca);
            int processComplete1 = processRuntime1.waitFor();
            int processComplete2 = processRuntime2.waitFor();

            if (processComplete1 == 0 && processComplete2 == 0) {
                System.out.println("OK");

            } else {
                System.out.println("False " + nomeBdBrittos);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
