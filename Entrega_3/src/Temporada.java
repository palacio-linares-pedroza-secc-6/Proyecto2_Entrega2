import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.*;

public class Temporada implements Serializable {
	private String nombreTemporada;
	private File fileTemporada;
	private Mercado mercado;
	private int presupuesto;
	private static HashMap<String, Equipo> equipos;
	private HashMap<String, Fecha> fechas;
	private static ArrayList<EquipoFantasia> equiposFantasy = new ArrayList<EquipoFantasia>();
	private PriorityQueue<Pair> rankingEquipoFantasia;

	public Temporada(String nombreTemporada, int presupuesto, File fileTemporada, File fileEquipo,
			File fileJugadores) throws FileNotFoundException {

		this.nombreTemporada = nombreTemporada;
		this.presupuesto = presupuesto;
		this.fileTemporada = fileTemporada;

		equipos = DataDam.cargarEquipos(fileEquipo, this);
		DataDam.cargarJugadores(fileJugadores, this);
		fechas = DataDam.cargarFechas(fileTemporada, this);

		crearMercado();

	}

	/**
	 * Crea el mercado de los jugadores de la temporada <br>
	 * <b> pre: </b> Debe haber estado inicializada la Temporada y la lista de
	 * equipos de esta no puede estar vacia <br>
	 * <b> post: </b> Se pone el atributo de mercado de la Temporada como el mercado
	 * que se crea
	 */
	public void crearMercado() {
		this.mercado = new Mercado();
		Object[] listaEquipos = equipos.keySet().toArray();
		for (int i = 0; i < listaEquipos.length; i++) {
			Equipo equipo = equipos.get(listaEquipos[i]);
			ArrayList<Jugador> jugadoresPosi = equipo.getJugadoresPosicion(Posicion.PORTERO);

			if (mercado.mercadoPosiciones.get(Posicion.PORTERO) == null) {

				mercado.mercadoPosiciones.put(Posicion.PORTERO, jugadoresPosi);
			}

			else {
				ArrayList<Jugador> listaJugadores = mercado.mercadoPosiciones.get(Posicion.PORTERO);
				listaJugadores.addAll(jugadoresPosi);
				mercado.mercadoPosiciones.put(Posicion.PORTERO, listaJugadores);

			}

			jugadoresPosi = equipo.getJugadoresPosicion(Posicion.DEFENSA);

			if (mercado.mercadoPosiciones.get(Posicion.DEFENSA) == null) {

				mercado.mercadoPosiciones.put(Posicion.DEFENSA, jugadoresPosi);
			}

			else {
				ArrayList<Jugador> listaJugadores = mercado.mercadoPosiciones.get(Posicion.DEFENSA);
				listaJugadores.addAll(jugadoresPosi);
				mercado.mercadoPosiciones.put(Posicion.DEFENSA, listaJugadores);

			}

			jugadoresPosi = equipo.getJugadoresPosicion(Posicion.MEDIOCAMPISTA);

			if (mercado.mercadoPosiciones.get(Posicion.MEDIOCAMPISTA) == null) {

				mercado.mercadoPosiciones.put(Posicion.MEDIOCAMPISTA, jugadoresPosi);
			}

			else {
				ArrayList<Jugador> listaJugadores = mercado.mercadoPosiciones.get(Posicion.MEDIOCAMPISTA);
				listaJugadores.addAll(jugadoresPosi);
				mercado.mercadoPosiciones.put(Posicion.MEDIOCAMPISTA, listaJugadores);

			}

			jugadoresPosi = equipo.getJugadoresPosicion(Posicion.DELANTERO);

			if (mercado.mercadoPosiciones.get(Posicion.DELANTERO) == null) {

				mercado.mercadoPosiciones.put(Posicion.DELANTERO, jugadoresPosi);
			}

			else {
				ArrayList<Jugador> listaJugadores = mercado.mercadoPosiciones.get(Posicion.DELANTERO);
				listaJugadores.addAll(jugadoresPosi);
				mercado.mercadoPosiciones.put(Posicion.DELANTERO, listaJugadores);

			}

			ArrayList<Jugador> jugadores = equipo.getJugadores();
			for (int a = 0; a < jugadores.size(); a++) {
				Jugador jugador = jugadores.get(a);
				String nombre = jugador.getNombre();
				mercado.mercadoJugadores.put(nombre, jugador);

			}
		}

	}

	// Funciones de busqueda
	/**
	 * Retorna la fecha indicada
	 * 
	 * @param fecha Nombre de la fecha que debe retornar
	 * @return Fecha inidcada por el parametro
	 */
	public Fecha getFecha(String fecha) {
		return fechas.get(fecha);
	}

	/**
	 * Retorna el nombre de la temporada
	 * 
	 * @return Nombre de la temporada
	 */
	public String getNombreTemporada() {
		return nombreTemporada;
	}

	/**
	 * Retorna el nombre del archivo utilizado para cargar la temporada
	 * 
	 * @return Nombre del archivo utilizado para cargar la temporada
	 */
	public File getFileTemporada() {
		return fileTemporada;
	}

	/**
	 * Retorna el presupuesto de los equipos de la temporada
	 * 
	 * @return Presupuesto de los equipos de la temporada
	 */
	public int getPresupuesto() {
		return presupuesto;
	}

	/**
	 * Retorna los equipos de la temporada <br>
	 * <b> pre: </b> Se debe haber inicializado la lista de equipos
	 * 
	 * @return Lista de equipos que juegan en la temporada
	 */
	public ArrayList<Equipo> getEquipos() {
		Object[] listallaves = equipos.keySet().toArray();
		ArrayList<Equipo> EQUIPOS = new ArrayList<Equipo>();
		for (int i = 0; i < listallaves.length; i++) {
			Equipo equipo = equipos.get(listallaves[i]);
			EQUIPOS.add(equipo);
		}
		return EQUIPOS;
	}

	/**
	 * Retorna el mapa donde estan guardados los equipos por nombre
	 * 
	 * @return Mapa donde estan guardados los equipos por nombre
	 */
	public HashMap<String, Equipo> getEquiposMap() {
		return equipos;
	}

	/**
	 * Retorna el equipo buscado <br>
	 * <b> pre:</b> Debe estar inicializado el mapa de los equipos
	 * 
	 * @param equipo Equipo a buscar
	 * @return Equipo buscado, null si no existe
	 */
	public Equipo getEquipo(String equipo) {
		return equipos.get(equipo);
	}

	/**
	 * Retorna los equipos de fantasia en la temporada <br>
	 * 
	 * @return Lista de equipos de fantasia de la temporada, null si no esta creada
	 */
	public static ArrayList<EquipoFantasia> getEquiposFantasy() {
		return equiposFantasy;
	}

	/**
	 * Retorna el ranking de los equipos de fantiasia
	 * 
	 * @return PrioirtyQueue de los equipos de fantasia en orden, null si no esta
	 *         creada
	 */
	public PriorityQueue<Pair> getRankingEquipoFantasia() {
		return rankingEquipoFantasia;
	}

	/**
	 * Retorna el mercado de la temporada
	 * 
	 * @return Mercado de la temporada, null si no esta creadod
	 */
	public Mercado getMercado() {
		return mercado;
	}

	/**
	 * <b> pre: </b> Debe estar inicializada la lista de equipos de la temporada
	 * <br>
	 * <b> post: </b> Añade el equipo especificado a la lista de equipos
	 * 
	 * @param equipoFantasia
	 */
	public void addEquipoFantasy(EquipoFantasia equipoFantasia) {
		equiposFantasy.add(equipoFantasia);
	}

	public void addEquipoFantasyRanking(Pair pair_equipo) {
		EquipoFantasia equipo_fantasy = (EquipoFantasia) pair_equipo.getValue();
		int puntos = pair_equipo.getKey();
		Iterator<Pair> value = equipo_fantasy.getRankingJugadores().iterator();
		boolean equipofound = false;
		while (value.hasNext()) {
			Pair pair = value.next();
			EquipoFantasia equipo = (EquipoFantasia) pair.getValue();
			if (equipo == equipo_fantasy) {
				int puntos1 = pair.getKey();
				puntos1 = puntos1 + puntos;
				pair.setKey(puntos1);
				equipofound = true;
			}
		}
		if (equipofound == false) {
			rankingEquipoFantasia.add(pair_equipo);
		}

	}

}
