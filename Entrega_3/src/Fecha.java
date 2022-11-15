import java.io.Serializable;
import java.util.*;

@SuppressWarnings("unchecked")
public class Fecha implements Serializable {

    String fecha;
    PriorityQueue<Pair> rankingJugadores;
    PriorityQueue<Pair> rankingEquipoFantasia;
    HashMap<String, Partido> partidos = new HashMap<String, Partido>();

    public Fecha(String fecha) {
        this.fecha = fecha;
    }

    public Partido crearPartido(String hora, Equipo local, Equipo visitante) {
        Partido partido = new Partido(hora, local, visitante);
        return partido;
    }

    public void addPartido(String hora, Equipo local, Equipo visitante) {
        String nombrelocal = local.getNombreShort();
        String nombrePartido = hora + nombrelocal;
        if (!partidos.containsKey(nombrePartido)) {
            Partido partido = crearPartido(hora, local, visitante);
            partidos.put(nombrePartido, partido);
        }
    }

    public Partido getPartido(String nombrePartido) {
        return partidos.get(nombrePartido);
    }

    public Pair[] calcularRankingEquipos() {
        ArrayList<EquipoFantasia> equipos = Temporada.getEquiposFantasy();
        Iterable<Pair> pares = (Iterable<Pair>) rankingJugadores.iterator();
        for (EquipoFantasia equipo : equipos) {
            int puntosEquipo = 0;
            for (Jugador jugador : equipo.getAlineacion().getJugadores()) {
                while (((Iterator<Pair>) pares).hasNext()) {
                    if (((Iterator<Pair>) pares).next().getValue() == jugador) {
                        puntosEquipo += ((Iterator<Pair>) pares).next().getKey();
                    }
                }
            }

            Pair equipoFantasia = new Pair(puntosEquipo, equipo);
            rankingEquipoFantasia.add(equipoFantasia);
        }

        return null;
    }

    public void addJugadoresRanking(PriorityQueue<Pair> jugadores) {
        rankingJugadores.addAll(jugadores);
    }

}
