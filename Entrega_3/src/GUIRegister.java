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

public class GUIRegister extends JFrame implements ActionListener {

    JTextField usuario;
    JTextField clave;
    JButton register;
    JButton volver;
    Ventana frame;
    JComboBox tipoUsuario;
    JLabel estado;

    public GUIRegister() {

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

        // Creacion de texto

        JLabel tituloTxt = new JLabel();
        tituloTxt.setText("Registro de Usuario");
        tituloTxt.setFont(new Font("Times New Roman", Font.PLAIN, 55));
        tituloTxt.setForeground(Color.WHITE);
        tituloTxt.setAlignmentX(Component.CENTER_ALIGNMENT);

        estado = new JLabel();
        estado.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        estado.setForeground(Color.WHITE);
        estado.setAlignmentX(Component.CENTER_ALIGNMENT);

        // creacion de TextFile

        usuario = new JTextField();
        usuario.setText("USUARIO");
        usuario.setFont(new Font("Consolas", Font.PLAIN, 18));
        usuario.setForeground(new Color(143, 138, 167, 255));

        clave = new JTextField();
        clave.setText("CONTRASEÑA");
        clave.setFont(new Font("Consolas", Font.PLAIN, 18));
        clave.setForeground(new Color(143, 138, 167, 255));

        // creacion de botones

        register = new JButton("Registrarse");
        register.setFocusable(false);
        register.setBackground(new Color(37, 32, 70, 255));
        register.setBorder(BorderFactory.createEtchedBorder());
        register.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        register.setForeground(Color.WHITE);
        register.addActionListener(this);

        volver = new JButton("Volver");
        volver.setFocusable(false);
        volver.setBackground(new Color(37, 32, 70, 255));
        volver.setBorder(BorderFactory.createEtchedBorder());
        volver.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        volver.setForeground(Color.WHITE);
        volver.addActionListener(this);
        volver.setPreferredSize(new Dimension(100, 50));

        // Creacion de JCombobox

        Tipo_Usuario[] usuarios = { Tipo_Usuario.PARTICIPANTE, Tipo_Usuario.ADMINISTRADOR };
        tipoUsuario = new JComboBox(usuarios);

        // agregar valores a la ventana

        frame = new Ventana();

        titulo.add(tituloTxt);

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(estado);
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(usuario);
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(clave);
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        menu.add(register);
        menu.add(tipoUsuario);
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        vacioS.add(volver);

        frame.add(titulo, BorderLayout.NORTH);
        frame.add(vacioW, BorderLayout.WEST);
        frame.add(vacioE, BorderLayout.EAST);
        frame.add(vacioS, BorderLayout.SOUTH);
        frame.add(menu, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        if (e.getSource() == register) {

            if (tipoUsuario.getSelectedItem().equals(Tipo_Usuario.PARTICIPANTE)) {
                try {
                    String resultado = Aplicacion.CrearUsuario(usuario.getText(), clave.getText(),
                            Tipo_Usuario.PARTICIPANTE);
                    estado.setText(resultado);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }

            else if (tipoUsuario.getSelectedItem().equals(Tipo_Usuario.ADMINISTRADOR)) {
                try {
                    String resultado = Aplicacion.CrearUsuario(usuario.getText(), clave.getText(),
                            Tipo_Usuario.ADMINISTRADOR);
                    estado.setText(resultado);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }

        }

        else if (e.getSource() == volver) {
            frame.dispose();
            try {
                new GUIAplicacion();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }

    }
}
