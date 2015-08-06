package br.com.areiasbrittos.persistencia;

import javax.swing.JOptionPane;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;

/**
 *
 * @author Maycon
 */
public class MyHibernateEventListener implements PreUpdateEventListener   {

    public MyHibernateEventListener() {
        this.atencao();
    }
    
    public void atencao() {
        //System.out.println("EEEEUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU\n\n");
        //JOptionPane.showMessageDialog(null, "MyLoadListener");
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent pue) {
        this.atencao();
        
        return true;
    }

}
