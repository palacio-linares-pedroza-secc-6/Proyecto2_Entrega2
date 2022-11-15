import java.io.Serializable;
import java.util.*;

public class Jugador implements Serializable {
	private String nombre;
	private Posicion posicion;
	private Equipo equipo;
	private int valor;
	private int valorVenta;
	private HashMap<String, ReporteJugador> reportes = new HashMap<String, ReporteJugador>();

	public Jugador(String nombre, Equipo shortEquipo, Posicion posicion, int precio) {
		this.nombre = nombre;
		this.posicion = posicion;
		this.equipo = shortEquipo;
		this.valor = precio;
		this.valorVenta = (int) (precio * .9);
	}

	public String getNombre() {
		return nombre;
	}

	public Posicion getPosicion() {
		return posicion;
	}

	public int getValor() {
		return valor;
	}

	public int getValorVenta() {
		return valorVenta;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public ReporteJugador getReporte(String nombrePartido) {
		return reportes.get(nombrePartido);
	}

	public void addReporte(ReporteJugador reporte, String nombrePartido) {
		reportes.put(nombrePartido, reporte);
	}

}
