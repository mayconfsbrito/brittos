/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class ConnectionFactory_brittos_bd {

    /*
     * Realiza a conexão com o banco de dados brittos_bd
     */
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_brittos", "root", "AdminRoot12");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao Banco de Dados bd_brittos.\n" + ex, "Erro na conexão!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

}
