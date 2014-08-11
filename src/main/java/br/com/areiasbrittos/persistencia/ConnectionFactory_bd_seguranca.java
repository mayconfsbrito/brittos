/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.persistencia;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class ConnectionFactory_bd_seguranca {

    /*
     * Realiza a conexão com o banco de dados brittos_bd
     */
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_seguranca", "root", "AdminRoot12");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao Banco de Dados bd_seguranca.\n" + ex, "Erro na conexão!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

}
