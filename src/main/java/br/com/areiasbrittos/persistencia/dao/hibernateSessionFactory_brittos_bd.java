
package br.com.areiasbrittos.persistencia.dao;

import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Gerencia a fabrica de sessões
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class hibernateSessionFactory_brittos_bd {

    private static SessionFactory fabricaSessoes;
    static{
        try{
            fabricaSessoes = new Configuration().configure("hibernate/mapeamento/hibernate_brittos_bd.cfg.xml").buildSessionFactory();

        }
        catch(Exception er){
            JOptionPane.showMessageDialog(null, "Erro ao criar a SessionFactory\n " +er);
            er.printStackTrace();
            fabricaSessoes = null;
        }
    }

    public static Session getSession(){
        return fabricaSessoes.openSession();
    }

}
