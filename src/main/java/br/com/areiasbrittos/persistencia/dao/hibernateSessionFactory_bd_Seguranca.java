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
public class hibernateSessionFactory_bd_Seguranca {

    private static SessionFactory fabricaSessoesBDSeguranca;
    static{
        try{
            fabricaSessoesBDSeguranca = new Configuration().configure("hibernate/mapeamento/hibernate_bd_seguranca.cfg.xml").buildSessionFactory();
 
        }
        catch(Exception er){
            JOptionPane.showMessageDialog(null, "Erro ao criar a SessionFactory\n " +er);
            er.printStackTrace();
            fabricaSessoesBDSeguranca = null;
        }
    }

    public static Session getSession(){
        return fabricaSessoesBDSeguranca.openSession();
    }

}
