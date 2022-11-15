
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

public class Ventana extends JFrame {

    // public static void main(String[] args) {

    public Ventana() {

        this.setTitle("Football");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout(0, 50));
        this.setSize(850, 600);
        this.getContentPane().setBackground(new Color(7, 7, 33, 255));
        this.setVisible(true);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (Aplicacion.getTemporadaActual() != null) {
                    SerializarObjeto.serializarObjeto(System.getProperty("user.dir") + "/data/TemporadaActual.txt",
                            Aplicacion.getTemporadaActual());
                }
            }
        });

    }

}
