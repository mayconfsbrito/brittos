/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.gui.utils;

import br.com.areiasbrittos.controle.gui.ControleFPreferencias;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import javax.swing.JDesktopPane;

/**
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class MyDesktopPanel extends JDesktopPane {

    private static final long serialVersionUID = 1L;
    Image img;

    public MyDesktopPanel() {
        String path = ControleFPreferencias.conf.getImagem();
        this.setImgBackground(path);

    }

    public MyDesktopPanel(String path) {
        this.setImgBackground(path);
    }

    public MyDesktopPanel(File file) {
        this.setImgBackground(file);
    }

    public void setImgBackground(String path) {
        try {
            img = javax.imageio.ImageIO.read(new java.net.URL(getClass().getResource(path), path));
            this.repaint();
        } catch (Exception e) {
        }//do nothing  
    }

    public void setImgBackground(File file) {
        try {
            img = javax.imageio.ImageIO.read(file);
            this.repaint();
        } catch (Exception e) {
        }//do nothing  
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        if (img != null) {

            //Dimension dimension = this.getSize();
            //int x = (int) (dimension.getWidth() - img.getWidth(this)) / 2;
            //int y = (int) (dimension.getHeight() - img.getHeight(this)) / 2;
            //g.drawImage(img, x, y, img.getWidth(this), img.getHeight(this), this);

            g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            g2d.dispose();

        } else {
            g2d.drawString("DesktopPanel! Imagem não encontrada.", 50, 50);
        }
    }
}
