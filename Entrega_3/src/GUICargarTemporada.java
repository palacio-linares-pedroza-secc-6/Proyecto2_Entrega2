import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class GUICargarTemporada extends JFrame implements ActionListener {

    JButton archivoTemporada;
    JButton archivoEquipos;
    JButton archivoJugadores;
    JButton volver;
    JButton crear;
    JTextField presupuesto;
    JTextField nombre;
    Ventana frame;
    File fileTemporada;
    File fileEquipos;
    File fileJugadores;
    int plata;

    public GUICargarTemporada() {

        // Creacion de paneles

        JPanel titulo = new JPanel();
        titulo.setBackground(new Color(25, 24, 55, 255));
        titulo.setPreferredSize(new Dimension(0, 85));
        titulo.setBorder(BorderFactory.createEtchedBorder());

        JPanel menu = new JPanel();
        menu.setBackground(new Color(25, 24, 55, 255));
        menu.setLayout(new GridLayout(5, 3, 20, 20));
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
        tituloTxt.setText("Carga de archivos");
        tituloTxt.setFont(new Font("Times New Roman", Font.PLAIN, 55));
        tituloTxt.setForeground(Color.WHITE);
        tituloTxt.setAlignmentX(Component.CENTER_ALIGNMENT);

        // creacion de botones

        archivoTemporada = new JButton("Temporada");
        archivoTemporada.setFocusable(false);
        archivoTemporada.setBackground(new Color(37, 32, 70, 255));
        archivoTemporada.setBorder(BorderFactory.createEtchedBorder());
        archivoTemporada.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        archivoTemporada.setForeground(Color.WHITE);
        archivoTemporada.addActionListener(this);

        archivoEquipos = new JButton("Equipos");
        archivoEquipos.setFocusable(false);
        archivoEquipos.setBackground(new Color(37, 32, 70, 255));
        archivoEquipos.setBorder(BorderFactory.createEtchedBorder());
        archivoEquipos.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        archivoEquipos.setForeground(Color.WHITE);
        archivoEquipos.addActionListener(this);
        archivoEquipos.setPreferredSize(new Dimension(100, 50));

        archivoJugadores = new JButton("Jugadores");
        archivoJugadores.setFocusable(false);
        archivoJugadores.setBackground(new Color(37, 32, 70, 255));
        archivoJugadores.setBorder(BorderFactory.createEtchedBorder());
        archivoJugadores.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        archivoJugadores.setForeground(Color.WHITE);
        archivoJugadores.addActionListener(this);
        archivoJugadores.setPreferredSize(new Dimension(100, 50));

        crear = new JButton("Crear");
        crear.setFocusable(false);
        crear.setBackground(new Color(37, 32, 70, 255));
        crear.setBorder(BorderFactory.createEtchedBorder());
        crear.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        crear.setForeground(Color.WHITE);
        crear.addActionListener(this);
        crear.setPreferredSize(new Dimension(100, 50));

        volver = new JButton("Volver");
        volver.setFocusable(false);
        volver.setBackground(new Color(37, 32, 70, 255));
        volver.setBorder(BorderFactory.createEtchedBorder());
        volver.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        volver.setForeground(Color.WHITE);
        volver.addActionListener(this);
        volver.setPreferredSize(new Dimension(100, 50));

        // creacion del text field
        presupuesto = new JTextField();
        presupuesto.setText("0");
        presupuesto.setFont(new Font("Consolas", Font.PLAIN, 18));
        presupuesto.setForeground(new Color(143, 138, 167, 255));

        nombre = new JTextField();
        nombre.setText("Nombre de la temporda");
        nombre.setFont(new Font("Consolas", Font.PLAIN, 15));
        nombre.setForeground(new Color(143, 138, 167, 255));

        // agregar los elementos al frame

        frame = new Ventana();

        titulo.add(tituloTxt);

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(nombre);
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(archivoTemporada);
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(archivoEquipos);
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(archivoJugadores);
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(presupuesto);
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        vacioS.add(volver);
        vacioS.add(crear);

        frame.add(titulo, BorderLayout.NORTH);
        frame.add(vacioW, BorderLayout.WEST);
        frame.add(vacioE, BorderLayout.EAST);
        frame.add(vacioS, BorderLayout.SOUTH);
        frame.add(menu, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        if (e.getSource() == archivoTemporada) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/data"));
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                fileTemporada = new File(fileChooser.getSelectedFile().getAbsolutePath());

            }

        }

        else if (e.getSource() == archivoEquipos) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/data"));
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                fileEquipos = new File(fileChooser.getSelectedFile().getAbsolutePath());

            }

        }

        else if (e.getSource() == archivoJugadores) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/data"));
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                fileJugadores = new File(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }

        else if (e.getSource() == crear) {

            try {
                Integer.parseInt(presupuesto.getText());
                if ((fileTemporada == null) || (fileEquipos == null) || (fileJugadores == null)) {
                    JOptionPane.showMessageDialog(null, "Faltan archivos", "Error", JOptionPane.WARNING_MESSAGE);
                }

                else if (Integer.parseInt(presupuesto.getText()) <= 0) {
                    JOptionPane.showMessageDialog(null, "El presupuesto tiene que ser mayor a 0", "Error",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        Aplicacion.setTemporada(
                                Aplicacion.admin.crearTemporada(nombre.getText(),
                                        Integer.parseInt(presupuesto.getText()),
                                        fileTemporada, fileEquipos, fileJugadores));
                        frame.dispose();
                        new GUIAdministrador(Aplicacion.admin.getNombre());
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(null, "los archivos cargados no son los correctos", "Error",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Ingrese un valor numero entre 1 a 2,147,483,647", "Error",
                        JOptionPane.WARNING_MESSAGE);
            }

        }

        else if (e.getSource() == volver) {
            frame.dispose();
            new GUIAdministrador(Aplicacion.admin.getNombre());

        }
    }

}
