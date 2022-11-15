import java.io.Serializable;
import java.util.*;

public class Equipo implements Serializable {
	private String nombre;
	private String nombreShort;
	private Temporada temporada;
	private HashMap<Posicion, ArrayList<Jugador>> Jugadores;

	public Equipo(String nombre, String nombreShort, Temporada temporada) {
		this.nombre = nombre;
		this.nombreShort = nombreShort;
		this.temporada = temporada;
		this.Jugadores = new HashMap<Posicion, ArrayList<Jugador>>();
	}

	public Equipo(String nombre, Temporada temporada) {
		this.nombre = nombre;
		this.temporada = temporada;
		this.Jugadores = new HashMap<Posicion, ArrayList<Jugador>>();
	}

	public void addJugador(Jugador player) {
		Posicion position = player.getPosicion();
		if (Jugadores.containsKey(position)) {
			ArrayList<Jugador> listajug = Jugadores.get(position);
			listajug.add(player);
			Jugadores.put(position, listajug);
		} else {
			ArrayList<Jugador> listajug = new ArrayList<Jugador>();
			listajug.add(player);
			Jugadores.put(position, listajug);
		}

	}

	public String getNombre() {
		return nombre;
	}

	public String getNombreShort() {
		return nombreShort;
	}

	public Temporada getTemporada() {
		return temporada;
	}

	public ArrayList<Jugador> getJugadores() {
		System.out.println(this.getNombre() + "---------------");
		Object[] listallaves = Jugadores.keySet().toArray();
		ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
		for (int i = 0; i < listallaves.length; i++) {
			System.out.println(listallaves[i]);
			ArrayList<Jugador> jugposicon = Jugadores.get(listallaves[i]);
			for (int j = 0; j < jugposicon.size(); j++) {
				System.out.println(jugposicon.get(j).getNombre() + " " + jugposicon.get(j).getEquipo().getNombre());
			}
			jugadores.addAll(jugposicon);
		}
		return jugadores;
	}

	public ArrayList<Jugador> getJugadoresPosicion(Posicion posicion) {
		return Jugadores.get(posicion);
	}
}
