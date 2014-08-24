/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.controle.testes;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Maycon Fernando Silva Brito @email mayconfsbrito@gmail.com
 */
public class Teste {

    InputStream inputStream = null;

    public static void main(String[] args) throws IOException {

        try {

            Process processRuntime1;
            Process processRuntime2;

            String comando1 = "cmd.exe /c mysqldump -u root --password=AdminRoot12 --database bd_brittos > \"M:\\Nova pasta\\Backup__bd_brittos__24-08-2014.sql\"";
            String comando2 = "cmd.exe /c mysqldump -u root --password=AdminRoot12 --database bd_seguranca > \"M:\\Nova pasta\\Backup__bd_seguranca__24-08-2014.sql\"";
            System.out.println(comando1);
            System.out.println(comando2);

            processRuntime1 = Runtime.getRuntime().exec(comando1);
            processRuntime2 = Runtime.getRuntime().exec(comando2);

            int processComplete1 = processRuntime1.waitFor();
            int processComplete2 = processRuntime2.waitFor();
            System.out.println(processComplete1);
            System.out.println(processComplete2);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
