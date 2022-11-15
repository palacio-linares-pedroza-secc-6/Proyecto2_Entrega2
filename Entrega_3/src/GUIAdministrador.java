import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

public class GUIAdministrador extends JFrame implements ActionListener {

    JButton cargarTemporada;
    JButton cerrarPartido;
    JButton logOut;
    Ventana frame;
    JLabel estado;

    public GUIAdministrador(String nombre) {

        JPanel titulo = new JPanel();
        titulo.setBackground(new Color(25, 24, 55, 255));
        titulo.setPreferredSize(new Dimension(0, 85));
        titulo.setBorder(BorderFactory.createEtchedBorder());

        JPanel menu = new JPanel();
        menu.setBackground(new Color(25, 24, 55, 255));
        menu.setLayout(new GridLayout(4, 3, 20, 20));
        menu.setBorder(BorderFactory.createEtchedBorder());

        JPanel vacioW = new JPanel();
        vacioW.setBackground(new Color(7, 7, 33, 255));
        vacioW.setPreferredSize(new Dimension(100, 100));

        JPanel vacioE = new JPanel();
        vacioE.setBackground(new Color(7, 7, 33, 255));
        vacioE.setPreferredSize(new Dimension(100, 100));

        JPanel vacioS = new JPanel();
        vacioS.setBackground(new Color(7, 7, 33, 255));
        vacioS.setPreferredSize(new Dimension(100, 85));

        // Creacion de texto

        JLabel tituloTxt = new JLabel();
        tituloTxt.setText("Bienvenido admin " + nombre);
        tituloTxt.setFont(new Font("Times New Roman", Font.PLAIN, 55));
        tituloTxt.setForeground(Color.WHITE);
        tituloTxt.setAlignmentX(Component.CENTER_ALIGNMENT);

        // creacion de botones

        cargarTemporada = new JButton("Cargar Temporada");
        cargarTemporada.setFocusable(false);
        cargarTemporada.setBackground(new Color(37, 32, 70, 255));
        cargarTemporada.setBorder(BorderFactory.createEtchedBorder());
        cargarTemporada.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        cargarTemporada.setForeground(Color.WHITE);
        cargarTemporada.addActionListener(this);

        cerrarPartido = new JButton("Cerrar Partido");
        cerrarPartido.setFocusable(false);
        cerrarPartido.setBackground(new Color(37, 32, 70, 255));
        cerrarPartido.setBorder(BorderFactory.createEtchedBorder());
        cerrarPartido.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        cerrarPartido.setForeground(Color.WHITE);
        cerrarPartido.addActionListener(this);

        logOut = new JButton("Log out");
        logOut.setFocusable(false);
        logOut.setBackground(new Color(37, 32, 70, 255));
        logOut.setBorder(BorderFactory.createEtchedBorder());
        logOut.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        logOut.setForeground(Color.WHITE);
        logOut.addActionListener(this);
        logOut.setPreferredSize(new Dimension(100, 50));

        // agr`egar valores a la ventana

        frame = new Ventana();

        titulo.add(tituloTxt);

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(cargarTemporada);
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(cerrarPartido);
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        vacioS.add(logOut);

        frame.add(titulo, BorderLayout.NORTH);
        frame.add(vacioW, BorderLayout.WEST);
        frame.add(vacioE, BorderLayout.EAST);
        frame.add(vacioS, BorderLayout.SOUTH);
        frame.add(menu, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == cargarTemporada) {
            frame.dispose();
            new GUICargarTemporada();
        }
        if (e.getSource() == cerrarPartido) {
            frame.dispose();
            new GUIFinalizarPartido();
        }

        else if (e.getSource() == logOut) {
            frame.dispose();
            new GUILogIn();
        }

    }

}
