import java.util.*;

public class EquipoFantasia extends Equipo {
	private String nombre;
	private int presupuesto;
	private Alineacion alineacionpasada;
	private HashMap<Fecha, Alineacion> fechasJugadas;
	private HashMap<Posicion, ArrayList<Jugador>> Jugadores;
	private PriorityQueue<Pair> rankingJugadores = new PriorityQueue<>(new Comparador());
	private Jugador susMedio;
	private Jugador susDelantero;
	private Jugador susArquero;
	private Jugador susDefensa;

	public EquipoFantasia(String nombre, Temporada temporada) {
		super(nombre, temporada);
		this.presupuesto = temporada.getPresupuesto();
		this.Jugadores = new HashMap<Posicion, ArrayList<Jugador>>();
		this.fechasJugadas = new HashMap<Fecha, Alineacion>();

	}

	public int getPresupuesto() {
		return presupuesto;
	}

	public HashMap<Posicion, ArrayList<Jugador>> getMapa() {
		return Jugadores;
	}

	public void setPresupuesto(int presupuesto) {
		this.presupuesto = presupuesto;
	}

	public Alineacion getFechaJugadas(Fecha fecha) {
		return fechasJugadas.get(fecha);
	}

	public void addFechaJugadas(Fecha fechajugada, Alineacion alineacion) {
		fechasJugadas.put(fechajugada, alineacion);
	}

	public Jugador getSusMedio() {
		return susMedio;
	}

	public void setSusMedio(Jugador susMedio) {
		this.susMedio = susMedio;
	}

	public Jugador getSusDelantero() {
		return susDelantero;
	}

	public void setSusDelantero(Jugador susDelantero) {
		this.susDelantero = susDelantero;
	}

	public Jugador getSusArquero() {
		return susArquero;
	}

	public void setSusArquero(Jugador susArquero) {
		this.susArquero = susArquero;
	}

	public Jugador getSusDefensa() {
		return susDefensa;
	}

	public void setSusDefensa(Jugador susDefensa) {
		this.susDefensa = susDefensa;
	}

	public void crearAlineacion(ArrayList<Jugador> listajugadores) {
		Alineacion alineacion = new Alineacion(listajugadores, this);
		this.alineacionpasada = alineacion;

	}
	public PriorityQueue<Pair> getRankingJugadores(){
		return rankingJugadores;
	}
	public Alineacion getAlineacion() {
		return alineacionpasada;
	}

	public Mercado crearMercado() {
		Mercado mercado = new Mercado();
		return mercado;
	}

	public void removeJugador(Jugador jugador) {
		Posicion pos = jugador.getPosicion();
		ArrayList<Jugador> players = this.getJugadoresPosicion(pos);
		setPresupuesto(presupuesto + jugador.getValorVenta());
		players.remove(jugador);
		Jugadores.put(pos, players);
	}

	public void addJugadorRanking(Pair playerpuntos) {
		Jugador jugador = (Jugador) playerpuntos.getValue();
		int puntos = playerpuntos.getKey();
		Iterator<Pair> value = rankingJugadores.iterator();
		boolean playerfound = false;
		while(value.hasNext()){
			Pair pair = value.next();
			Jugador player = (Jugador) pair.getValue();
			if (jugador==player){
				int puntos1 = pair.getKey();
				puntos1= puntos1+puntos;
				pair.setKey(puntos1);
				playerfound=true;
			}
		}
		if (playerfound==false){
			rankingJugadores.add(playerpuntos);
		}
		
	}

}
