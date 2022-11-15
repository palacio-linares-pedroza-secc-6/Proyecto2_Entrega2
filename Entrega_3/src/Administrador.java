import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import javax.swing.*;

public class Administrador extends Usuario {

	public Administrador(String nombre, String contrasena) {
		super(nombre, contrasena);
	}

	/**
	 * Crea una nueva temporada en la aplicacion
	 * 
	 * @param nombreTemporada nombre que se le quiere dar a la nueva temporada
	 * @param presupuesto     presupuesto para los equipos de esta temporada
	 * @param fileTemporada   nombre del archivo de la nueva temporada
	 * @param fileEquipo      nombre del archivo de los equiposReales de la
	 *                        temporada
	 * @param fileJugadores   nombre del archivo de los Jugadores de la temporada
	 * @return Objeto de la clase temporada con los datos especificados
	 * @throws FileNotFoundException <br>
	 *                               1. Si no se encuentran los archivos indicados
	 *                               por parametro
	 */
	public Temporada crearTemporada(String nombreTemporada, int presupuesto, File fileTemporada, File fileEquipo,
			File fileJugadores) throws FileNotFoundException {

		Temporada temporada = new Temporada(nombreTemporada, presupuesto, fileTemporada, fileEquipo, fileJugadores);
		return temporada;
	}

	/**
	 * Cierra un partido y calcula los puntos de los jugadores que jugaron en ese
	 * partido
	 * <b>pre:</b> El partido a cerrar ya esta inicializado. La fecha del partido ya
	 * contiene a ese partido <br>
	 * <b>post:</b> El partido se cierra con los puntos de cada jugador calculados
	 * por los datos dados
	 * 
	 * @param partido     El partido a cerrar
	 * @param partidoBus  El nombre del partido
	 * @param filePartido archivo con la informacion de las estadisticas de los
	 *                    jugadores de ese partido
	 * @param fecha       La fecha en la cual se juega el partido
	 * @throws FileNotFoundException Si no se encuentra el archivo del partido
	 *
	 */
	public void finalizarPartido(Partido partido, String partidoBus, File filePartido, Fecha fecha)
			throws FileNotFoundException {
		partido.setfileReporte(filePartido);
		ArrayList<Jugador> jugadoresTotal = partido.getJugadores();
		for (int i = 0; i < jugadoresTotal.size(); i++) {
			System.out.println(jugadoresTotal.get(i).getNombre() + " " + jugadoresTotal.get(i).getEquipo().getNombre());
		}
		// local
		Scanner scanner = new Scanner(
				new FileReader(filePartido));
		String linea = scanner.nextLine();
		HashMap<Equipo, Integer> golesMap = new HashMap<Equipo, Integer>();
		while (scanner.hasNextLine()) {
			linea = scanner.nextLine();
			String[] info = linea.split(";");
			String nombreJugador = info[0];
			golesMap.put(partido.getLocal(), 0);
			golesMap.put(partido.getVisitante(), 0);
			for (int i = 0; i < jugadoresTotal.size(); i++) {
				Jugador jugador = jugadoresTotal.get(i);
				if (jugador.getNombre().equals(nombreJugador)) {
					int minJugados = Integer.parseInt(info[2]);
					int minIngresado = Integer.parseInt(info[3]);
					int minSalido = Integer.parseInt(info[4]);
					int goles = Integer.parseInt(info[5]);
					int golesPenaltis = Integer.parseInt(info[6]);
					int autogoles = Integer.parseInt(info[7]);
					int asistencias = Integer.parseInt(info[8]);
					int golesRecibidos = Integer.parseInt(info[9]);
					int penaltisDetenidos = Integer.parseInt(info[10]);
					int penaltisErrados = Integer.parseInt(info[11]);
					int tarjetasAmarillas = Integer.parseInt(info[12]);
					int tarjetasRojas = Integer.parseInt(info[13]);
					Integer num_goles = golesMap.get(jugador.getEquipo());
					num_goles += goles;
					golesMap.put(jugador.getEquipo(), num_goles);
					ReporteJugador reporte = new ReporteJugador(partidoBus, minJugados, minIngresado, minSalido, goles,
							golesPenaltis, autogoles, asistencias, golesRecibidos, penaltisDetenidos, penaltisErrados,
							tarjetasAmarillas, tarjetasRojas);
					jugador.addReporte(reporte, partidoBus);
				}
			}
		}
		Integer marcadorlocal = golesMap.get(partido.getLocal());
		Integer maracdorVisit = golesMap.get(partido.getVisitante());
		partido.setMarcador(marcadorlocal, maracdorVisit);
	}
}
