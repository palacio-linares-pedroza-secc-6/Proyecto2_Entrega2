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
import java.util.ArrayList;

import javax.swing.*;

public class GUIMercado extends JFrame implements ActionListener {
    Ventana frame;
    JScrollPane mostron;
    JButton buscar;
    JButton comprar;
    JLabel presupuesto;
    JComboBox posiciones;
    Mercado mercado = Aplicacion.temporadaActual.getMercado();
    JPanel jugadoresMenu;
    JScrollPane jcp = new JScrollPane();

    public GUIMercado() {

        JPanel titulo = new JPanel();
        titulo.setBackground(new Color(25, 24, 55, 255));
        titulo.setPreferredSize(new Dimension(0, 85));
        titulo.setBorder(BorderFactory.createEtchedBorder());

        JPanel menu = new JPanel();
        menu.setBackground(new Color(25, 24, 55, 255));
        menu.setLayout(new GridLayout(4, 3, 20, 20));
        menu.setBorder(BorderFactory.createEtchedBorder());

        jugadoresMenu = new JPanel();
        jugadoresMenu.setBackground(new Color(25, 24, 55, 255));
        jugadoresMenu.setPreferredSize(new Dimension(450, 100));

        JPanel menuOpciones = new JPanel();
        menuOpciones.setBackground(new Color(25, 24, 55, 255));
        menuOpciones.setPreferredSize(new Dimension(300, 100));

        JPanel vacioS = new JPanel();
        vacioS.setBackground(new Color(25, 24, 55, 255));
        vacioS.setPreferredSize(new Dimension(300, 1));

        // Creacion de texto

        JLabel tituloTxt = new JLabel();
        tituloTxt.setText("Mercado");
        tituloTxt.setFont(new Font("Times New Roman", Font.PLAIN, 55));
        tituloTxt.setForeground(Color.WHITE);
        tituloTxt.setAlignmentX(Component.CENTER_ALIGNMENT);

        presupuesto = new JLabel();
        presupuesto.setText("Presupuesto: " + Integer.toString(Aplicacion.user.getEquipo().getPresupuesto()));
        presupuesto.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        presupuesto.setForeground(Color.WHITE);
        presupuesto.setAlignmentX(Component.CENTER_ALIGNMENT);

        Posicion[] posicionesList = { Posicion.PORTERO, Posicion.DEFENSA, Posicion.MEDIOCAMPISTA, Posicion.DELANTERO };
        posiciones = new JComboBox(posicionesList);

        buscar = new JButton("Buscar");
        buscar.setFocusable(false);
        buscar.setBackground(new Color(37, 32, 70, 255));
        buscar.setBorder(BorderFactory.createEtchedBorder());
        buscar.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        buscar.setForeground(Color.WHITE);
        buscar.addActionListener(this);
        buscar.setPreferredSize(new Dimension(85, 35));

        comprar = new JButton("Comprar");
        comprar.setFocusable(false);
        comprar.setBackground(new Color(37, 32, 70, 255));
        comprar.setBorder(BorderFactory.createEtchedBorder());
        comprar.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        comprar.setForeground(Color.WHITE);
        comprar.addActionListener(this);
        comprar.setPreferredSize(new Dimension(85, 35));

        frame = new Ventana();

        titulo.add(tituloTxt);
        menuOpciones.add(presupuesto);
        menuOpciones.add(posiciones);
        menuOpciones.add(buscar);
        menuOpciones.add(comprar);

        frame.add(titulo, BorderLayout.NORTH);
        frame.add(jugadoresMenu, BorderLayout.WEST);
        frame.add(menuOpciones, BorderLayout.EAST);
        frame.add(vacioS, BorderLayout.SOUTH);
        // frame.add(menu, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        if (e.getSource() == buscar) {

            ArrayList<Jugador> jugadores = mercado.getJugadoresporPosicion((Posicion) posiciones.getSelectedItem());
            String[] list = new String[jugadores.size()];

            for (int a = 0; a < jugadores.size(); a++) {
                Jugador jugador = jugadores.get(a);
                String nombre = jugador.getNombre();
                String equipoShort = jugador.getEquipo().getNombreShort();
                int valor = jugador.getValor();
                list[a] = ("||" + nombre + "||" + equipoShort + "||" + valor);
            }

            JList playerList = new JList<String>(list);
            playerList.setVisibleRowCount(12);
            jugadoresMenu.remove(jcp);
            jcp = new JScrollPane(playerList);
            jugadoresMenu.add(jcp);

        }

    }

}
