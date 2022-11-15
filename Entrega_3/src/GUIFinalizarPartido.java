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
import java.util.*;

import javax.swing.*;

public class GUIFinalizarPartido extends JFrame implements ActionListener {

    JTextField fechaPartido;
    JTextField localPartido;
    JTextField horaPartido;
    JButton archivoPartido;
    JButton volver;
    JButton cerrar;
    File filePartido;
    Ventana frame;

    public GUIFinalizarPartido() {

        // Creacion de paneles

        JPanel titulo = new JPanel();
        titulo.setBackground(new Color(25, 24, 55, 255));
        titulo.setPreferredSize(new Dimension(0, 85));
        titulo.setBorder(BorderFactory.createEtchedBorder());

        JPanel menu = new JPanel();
        menu.setBackground(new Color(25, 24, 55, 255));
        menu.setLayout(new GridLayout(5, 2, 20, 20));
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
        tituloTxt.setText("Finalizar Partido");
        tituloTxt.setFont(new Font("Times New Roman", Font.PLAIN, 55));
        tituloTxt.setForeground(Color.WHITE);
        tituloTxt.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Creacion de botones

        archivoPartido = new JButton("Partido");
        archivoPartido.setFocusable(false);
        archivoPartido.setBackground(new Color(37, 32, 70, 255));
        archivoPartido.setBorder(BorderFactory.createEtchedBorder());
        archivoPartido.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        archivoPartido.setForeground(Color.WHITE);
        archivoPartido.addActionListener(this);

        cerrar = new JButton("Crear");
        cerrar.setFocusable(false);
        cerrar.setBackground(new Color(37, 32, 70, 255));
        cerrar.setBorder(BorderFactory.createEtchedBorder());
        cerrar.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        cerrar.setForeground(Color.WHITE);
        cerrar.addActionListener(this);
        cerrar.setPreferredSize(new Dimension(100, 50));

        volver = new JButton("Volver");
        volver.setFocusable(false);
        volver.setBackground(new Color(37, 32, 70, 255));
        volver.setBorder(BorderFactory.createEtchedBorder());
        volver.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        volver.setForeground(Color.WHITE);
        volver.addActionListener(this);
        volver.setPreferredSize(new Dimension(100, 50));

        // Creacion de JTextField
        fechaPartido = new JTextField();
        fechaPartido.setText("Fecha del partido (dd-mm)");
        fechaPartido.setFont(new Font("Consolas", Font.PLAIN, 18));
        fechaPartido.setForeground(new Color(143, 138, 167, 255));

        localPartido = new JTextField();
        localPartido.setText("Equipo local (CHE)");
        localPartido.setFont(new Font("Consolas", Font.PLAIN, 15));
        localPartido.setForeground(new Color(143, 138, 167, 255));

        horaPartido = new JTextField();
        horaPartido.setText("Hora del partido (hh:mm)");
        horaPartido.setFont(new Font("Consolas", Font.PLAIN, 15));
        horaPartido.setForeground(new Color(143, 138, 167, 255));

        // agregar los elementos al frame

        frame = new Ventana();

        titulo.add(tituloTxt);

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        menu.add(fechaPartido);
        menu.add(archivoPartido);

        menu.add(localPartido);
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        menu.add(horaPartido);
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        menu.add(Box.createRigidArea(new Dimension(5, 0)));
        menu.add(Box.createRigidArea(new Dimension(5, 0)));

        vacioS.add(volver);
        vacioS.add(cerrar);

        frame.add(titulo, BorderLayout.NORTH);
        frame.add(vacioW, BorderLayout.WEST);
        frame.add(vacioE, BorderLayout.EAST);
        frame.add(vacioS, BorderLayout.SOUTH);
        frame.add(menu, BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        if (e.getSource() == archivoPartido) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/data/partidos"));
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                filePartido = new File(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }

        else if (e.getSource() == cerrar) {

            try {
                String nombrePartido = horaPartido.getText() + localPartido.getText();
                Fecha fecha = Aplicacion.temporadaActual.getFecha(fechaPartido.getText());
                Partido partido = fecha.getPartido(nombrePartido);
                Aplicacion.admin.finalizarPartido(partido, nombrePartido, filePartido, fecha);
                partido.setfileReporte(filePartido);

                ArrayList<EquipoFantasia> lista_fantasy = Temporada.getEquiposFantasy();
                if (lista_fantasy != null) {
                    System.out.println(lista_fantasy.size());
                    for (int c = 0; c < lista_fantasy.size(); c++) {
                        EquipoFantasia equipo_fantasy = lista_fantasy.get(c);
                        System.out.println(equipo_fantasy.getNombre());
                        Alineacion all = equipo_fantasy.getAlineacion();
                        all.jugarPartido(partido, fecha);
                        int puntos_equipo = 0;
                        PriorityQueue<Pair> pq = equipo_fantasy.getRankingJugadores();
                        Iterator<Pair> value = pq.iterator();
                        while (value.hasNext()) {
                            Pair pair = value.next();
                            Jugador ppplayer = (Jugador) pair.getValue();
                            puntos_equipo += pair.getKey();
                            System.out.println(ppplayer.getNombre() + " " + pair.getKey());
                        }
                        Pair pair_equipo = new Pair(puntos_equipo, equipo_fantasy);
                        Aplicacion.temporadaActual.addEquipoFantasyRanking(pair_equipo);
                    }
                }

                frame.dispose();
                new GUIAdministrador(Aplicacion.admin.getNombre());

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Error encontrando el archivo", "Error",
                        JOptionPane.WARNING_MESSAGE);
                e1.printStackTrace();
                ;
            }

        }

        else if (e.getSource() == volver) {
            frame.dispose();
            new GUIAdministrador(Aplicacion.admin.getNombre());
        }

    }

}
