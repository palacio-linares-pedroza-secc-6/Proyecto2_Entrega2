import java.io.Serializable;
import java.util.*;

public class Mercado implements Serializable {

    HashMap<String, Jugador> mercadoJugadores = new HashMap<>();
    HashMap<Posicion, ArrayList<Jugador>> mercadoPosiciones = new HashMap<>();

    public Mercado() {
        mercadoPosiciones.put(Posicion.PORTERO, null);
        mercadoPosiciones.put(Posicion.DEFENSA, null);
        mercadoPosiciones.put(Posicion.MEDIOCAMPISTA, null);
        mercadoPosiciones.put(Posicion.DELANTERO, null);
    }

    public void mostrarJugadores(Posicion posicion) {
        ArrayList<Jugador> lista = getJugadoresporPosicion(posicion);
        System.out.println(posicion);
        System.out.println("Opcion||Nombre.....||Equipo.....||Precio.....");
        for (int a = 0; a < lista.size(); a++) {
            Jugador jugador = lista.get(a);
            String nombre = jugador.getNombre();
            String equipoShort = jugador.getEquipo().getNombreShort();
            int valor = jugador.getValor();
            System.out.println(String.valueOf(a + 1) + "||" + nombre + "||" + equipoShort + "||" + valor);

        }
    }

    public ArrayList<Jugador> getJugadoresporPosicion(Posicion pos) {
        ArrayList<Jugador> lista = mercadoPosiciones.get(pos);
        return lista;
    }
}