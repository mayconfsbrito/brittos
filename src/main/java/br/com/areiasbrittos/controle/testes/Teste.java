/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.controle.testes;

import br.com.areiasbrittos.controle.objetos.Produto;
import br.com.areiasbrittos.persistencia.dao.AbstractDAO;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * @author Maycon Fernando Silva Brito @email mayconfsbrito@gmail.com
 */
public class Teste {

    InputStream inputStream = null;

    public static void main(String[] args) throws IOException {
        hibernateListener();
    }
    
    public static void hibernateListener(){
        ArrayList<Produto> list = (ArrayList<Produto>) AbstractDAO.consultar("Produto", "idProduto=2");
        System.out.println(list.toString());
        
        list.get(0).setEstoque(list.get(0).getEstoque() + 1);
        
        AbstractDAO.alterar(list.get(0));
        
        System.exit(0);
    }
            
    public static void backup() {
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
