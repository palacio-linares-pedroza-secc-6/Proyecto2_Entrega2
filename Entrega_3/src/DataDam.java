import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class DataDam {

    public void creatFilesUsuarios() throws FileNotFoundException {

        File fileParticipantes = new File(System.getProperty("user.dir") + "/data/usuarios/participantes.csv");
        File fileAdmin = new File(System.getProperty("user.dir") + "/data/usuarios/administradores.csv");

        if (!fileParticipantes.exists()) {

            PrintWriter out = new PrintWriter(fileParticipantes);
            out.printf("%s,%s\n", "Nombre", "Contrasena");
            out.close();
        }

        if (!fileAdmin.exists()) {

            PrintWriter out = new PrintWriter(fileAdmin);
            out.printf("%s,%s\n", "Nombre", "Contrasena");
            out.close();
        }

    }

    public void addParticipante(String nombre, String contrasena) throws IOException {
        File fileParticipantes = new File(System.getProperty("user.dir") + "/data/usuarios/participantes.csv");

        Writer out = new BufferedWriter(new FileWriter(fileParticipantes, true));
        out.append(nombre + ";" + contrasena + "\n");
        out.close();
    }

    public void addAdministrador(String nombre, String contrasena) throws IOException {
        File fileAdministrador = new File(System.getProperty("user.dir") + "/data/usuarios/administradores.csv");

        Writer out = new BufferedWriter(new FileWriter(fileAdministrador, true));
        out.append(nombre + ";" + contrasena + "\n");
        out.close();
    }

    public void loadUsuarios() throws IOException {

        Scanner scanner = new Scanner(
                new FileReader(System.getProperty("user.dir") + "/data/usuarios/participantes.csv"));
        String linea = scanner.nextLine();
        while (scanner.hasNextLine()) {
            linea = scanner.nextLine();
            String[] info = linea.split(";");
            Aplicacion.reCrearUsuarios(info[0], info[1], Tipo_Usuario.PARTICIPANTE);
        }
        scanner = new Scanner(
                new FileReader(System.getProperty("user.dir") + "/data/usuarios/administradores.csv"));
        linea = scanner.nextLine();
        while (scanner.hasNextLine()) {
            linea = scanner.nextLine();
            String[] info = linea.split(";");
            Aplicacion.reCrearUsuarios(info[0], info[1], Tipo_Usuario.ADMINISTRADOR);
        }

    }

    public static HashMap<String, Equipo> cargarEquipos(File fileEquipo, Temporada temporadaActual)
            throws FileNotFoundException {

        HashMap<String, Equipo> equipos = new HashMap<>();
        Scanner scanner;
        scanner = new Scanner(new FileReader(fileEquipo));

        String linea = scanner.nextLine();
        while (scanner.hasNextLine()) {
            linea = scanner.nextLine();
            String[] info = linea.split(";");
            String nombre = info[0];
            String nombreShort = info[1];
            Equipo equipo = new Equipo(nombre, nombreShort, temporadaActual);
            equipos.put(nombreShort, equipo);
        }
        return equipos;

    }

    public static void cargarJugadores(File fileJugadores, Temporada temporadaActual) throws FileNotFoundException {

        Scanner scanner;
        scanner = new Scanner(new FileReader(fileJugadores));
        String linea = scanner.nextLine();
        while (scanner.hasNextLine())

        {
            linea = scanner.nextLine();
            String[] info = linea.split(";");
            String nombreJug = info[1];
            String shortEquipo = info[2];
            String precio = info[4];
            Equipo equipo = temporadaActual.getEquiposMap().get(shortEquipo);

            if (info[3].toUpperCase().equals("PORTERO")) {

                Posicion posicion = Posicion.PORTERO;
                Jugador jugador = new Jugador(nombreJug, equipo, posicion, Integer.parseInt(precio));
                equipo.addJugador(jugador);
            } else if (info[3].toUpperCase().equals("DEFENSA")) {
                Posicion posicion = Posicion.DEFENSA;
                Jugador jugador = new Jugador(nombreJug, equipo, posicion, Integer.parseInt(precio));
                equipo.addJugador(jugador);
            } else if (info[3].toUpperCase().equals("MEDIOCAMPISTA")) {
                Posicion posicion = Posicion.MEDIOCAMPISTA;
                Jugador jugador = new Jugador(nombreJug, equipo, posicion, Integer.parseInt(precio));
                equipo.addJugador(jugador);
            } else {
                Posicion posicion = Posicion.DELANTERO;
                Jugador jugador = new Jugador(nombreJug, equipo, posicion, Integer.parseInt(precio));
                equipo.addJugador(jugador);
            }

        }

    }

    public static HashMap<String, Fecha> cargarFechas(File fileTemporada, Temporada temporadaActual)
            throws FileNotFoundException {

        HashMap<String, Fecha> fechas = new HashMap<>();
        Scanner scanner;
        scanner = new Scanner(new FileReader(fileTemporada));
        String linea = scanner.nextLine();
        while (scanner.hasNextLine()) {
            linea = scanner.nextLine();
            String[] info = linea.split(";");
            String date = info[0];
            String hora = info[1];
            Equipo local = temporadaActual.getEquiposMap().get(info[2]);
            Equipo visitante = temporadaActual.getEquiposMap().get(info[3]);
            if (fechas.containsKey(date)) {
                Fecha fechaMod = fechas.get(date);
                fechaMod.addPartido(hora, local, visitante);
            } else {
                Fecha fecha = new Fecha(date);
                fecha.addPartido(hora, local, visitante);
                fechas.put(date, fecha);
            }

        }

        return fechas;
    }

}
