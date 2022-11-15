
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

public class GUIAplicacion extends JFrame implements ActionListener {

    Aplicacion aplicacion;
    JButton regist;
    JButton logIn;
    JButton out;
    Ventana frame;

    public GUIAplicacion() throws IOException {
        aplicacion = new Aplicacion();
        
        // Creacion de paneles

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

        // Creacion de Textos

        JLabel tituloTxt = new JLabel();
        tituloTxt.setText("EQUIPO DE FANTASIA V.2");
        tituloTxt.setFont(new Font("Times New Roman", Font.PLAIN, 55));
        tituloTxt.setForeground(Color.WHITE);
        tituloTxt.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Creacion de Botones

        regist = new JButton("Registrarse");
        regist.setFocusable(false);
        regist.setBackground(new Color(37, 32, 70, 255));
        regist.setBorder(BorderFactory.createEtchedBorder());
        regist.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        regist.setForeground(Color.WHITE);
        regist.addActionListener(this);

        logIn = new JButton("Iniciar Sesion");
        logIn.setFocusable(false);
        logIn.setBackground(new Color(37, 32, 70, 255));
        logIn.setBorder(BorderFactory.createEtchedBorder());
        logIn.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        logIn.setForeground(Color.WHITE);
        logIn.addActionListener(this);

        // Agregar Valores al frame

        titulo.add(tituloTxt);

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(regist);
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(logIn);
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        frame = new Ventana();

        frame.add(titulo, BorderLayout.NORTH);
        frame.add(vacioW, BorderLayout.WEST);
        frame.add(vacioE, BorderLayout.EAST);
        frame.add(vacioS, BorderLayout.SOUTH);
        frame.add(menu, BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        if (e.getSource() == regist) {
            frame.dispose();
            new GUIRegister();

        }

        else if (e.getSource() == logIn) {
            frame.dispose();
            new GUILogIn();

        }

    }

    public static void main(String[] args) throws IOException {
        new GUIAplicacion();
    }
}
