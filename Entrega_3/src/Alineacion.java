import java.util.*;

public class Alineacion {
    private HashMap<Posicion, ArrayList<Jugador>> jugadores = new HashMap<Posicion, ArrayList<Jugador>>();
    private EquipoFantasia equipo;
    private Jugador capitan;

    public Alineacion(ArrayList<Jugador> listajugadores, EquipoFantasia equipo) {
        Aplicacion.crearMapa(jugadores, listajugadores);
        this.equipo = equipo;
    }

    /**
     * Confirma si la alineacion de un equipo tiene el numero de jugadores esperados
     * en las posiciones esperadas
     * <b> pre: </b> La alineacion tiene jugadores añadidos <br>
     * 
     * @return Valor booleano depictando el estado de la alineacion. False
     *         significando incompleta y True significando completa
     */
    public Boolean checkAlineacioncompleta() {
        Object[] posiciones = jugadores.keySet().toArray();
        for (int i = 0; i < 4; i++) {
            Posicion posicion = (Posicion) posiciones[i];
            ArrayList<Jugador> listaporposicion = jugadores.get(posicion);
            if (capitan == null) {
                return false;
            }
            if (posicion == Posicion.PORTERO) {
                if (listaporposicion.size() != 1) {
                    return false;
                }
            } else if (posicion == Posicion.DELANTERO) {
                if (listaporposicion.size() != 2) {
                    return false;
                }
            } else {
                if (listaporposicion.size() != 4) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Utiliza la alineacion para jugar el partido especificado, sustituyendo
     * jugadores si es necesario y calculando los puntos para los jugadores de dicha
     * alineacion
     * <b> pre:</b> La alineacion debe estar inicializada antes de entrar.
     * <b> post:</b> La alineacion es modificada para se realizen las sustituciones
     * necesarias
     * 
     * @param partido
     */
    public void jugarPartido(Partido partido, Fecha fecha) {
        Object[] posiciones = jugadores.keySet().toArray();
        HashMap<Posicion, Integer> sustituciones = new HashMap<Posicion, Integer>();
        for (int i = 0; i < 4; i++) {
            Posicion posicion = (Posicion) posiciones[i];
            sustituciones.put(posicion, 0);
            ArrayList<Jugador> listaporposicion = jugadores.get(posicion);
            for (int j = 0; j < listaporposicion.size(); j++) {
                Jugador jugadoractual = listaporposicion.get(j);
                ReporteJugador reporte = jugadoractual.getReporte(partido.getNombre());
                int numsustituciones = sustituciones.get(posicion);
                if (reporte == null) {
                    if (numsustituciones < 1) {
                        Sustituir(jugadoractual);
                        numsustituciones += 1;
                        sustituciones.put(posicion, numsustituciones);
                    } else {
                        continue;
                    }
                } else {
                    int minutosJugados = reporte.getminutosJugados();
                    if (minutosJugados == 0 && numsustituciones < 1) {
                        Sustituir(jugadoractual);
                        numsustituciones += 1;
                    }
                }
            }
        }
        equipo.addFechaJugadas(fecha, this);
        calcularPuntos(partido);
    }

    public void Sustituir(Jugador jugadoractual) {
        Posicion posicion = jugadoractual.getPosicion();
        ArrayList<Jugador> players = jugadores.get(posicion);
        if (posicion == Posicion.PORTERO) {
            Jugador sustituto = equipo.getSusArquero();
            equipo.setSusArquero(jugadoractual);
            players.remove(jugadoractual);
            if (sustituto != null) {
                players.add(sustituto);
            }
        } else if (posicion == Posicion.DEFENSA) {
            Jugador sustituto = equipo.getSusDefensa();
            equipo.setSusDefensa(jugadoractual);
            players.remove(jugadoractual);
            if (sustituto != null) {
                players.add(sustituto);
            }
        } else if (posicion == Posicion.MEDIOCAMPISTA) {
            Jugador sustituto = equipo.getSusMedio();
            equipo.setSusMedio(jugadoractual);
            players.remove(jugadoractual);
            if (sustituto != null) {
                players.add(sustituto);
            }
        } else {
            Jugador sustituto = equipo.getSusDelantero();
            equipo.setSusDelantero(jugadoractual);
            players.remove(jugadoractual);
            if (sustituto != null) {
                players.add(sustituto);
            }
        }
    }

    public void calcularPuntos(Partido partido) {
        if (checkAlineacioncompleta()) {
            Object[] posiciones = jugadores.keySet().toArray();
            for (int i = 0; i < posiciones.length; i++) {
                Posicion posicion = (Posicion) posiciones[i];
                for (Jugador jugador : jugadores.get(posicion)) {
                    int puntos = 0;
                    ReporteJugador reporte = jugador.getReporte(partido.getNombre());
                    if (reporte != null) {
                        if (reporte.getminutosJugados() > 0) {
                            if (jugador == capitan) {
                                if (partido.getLocal() == jugador.getEquipo()) {
                                    if (partido.getMarcador().getKey() > (int) partido.getMarcador().getValue()) {
                                        puntos += 5;
                                    }
                                } else {
                                    if (partido.getMarcador().getKey() < (int) partido.getMarcador().getValue()) {
                                        puntos += 5;
                                    }
                                }

                            }
                            puntos = puntos + reporte.getAsistencias() * 3;
                            puntos = puntos - reporte.getAutogoles() * 2;
                            puntos = puntos - reporte.getPenaltisErrados() * 2;
                            puntos = puntos - reporte.getTarjetasRojas() * 3;
                            puntos = puntos - reporte.getTarjetasAmarillas();
                            if (reporte.getminutosJugados() <= 60) {
                                puntos += 1;
                            } else {
                                puntos += 2;
                            }
                            if (jugador.getPosicion() == Posicion.DELANTERO) {
                                puntos = puntos + reporte.getGoles() * 4;
                            } else if (jugador.getPosicion() == Posicion.MEDIOCAMPISTA) {
                                puntos = puntos + reporte.getGoles() * 5;
                            } else {
                                puntos = puntos + reporte.getGoles() * 6;
                                if (partido.getLocal() == jugador.getEquipo()) {
                                    if ((int) partido.getMarcador().getValue() == 0) {
                                        puntos += 4;
                                    }
                                } else {
                                    if (partido.getMarcador().getKey() == 0) {
                                        puntos += 4;
                                    }
                                }
                                if (jugador.getPosicion() == Posicion.PORTERO) {
                                    puntos = puntos + reporte.getPenaltisDetenidos() * 5;
                                }
                            }
                        }
                    }
                    Pair playerpuntos = new Pair(puntos, jugador);
                    equipo.addJugadorRanking(playerpuntos);
                }
            }
        }

    }

    /**
     * Devuelve el capitan del equipo
     * <b> pre </b> Se debe haber inicializado la alineacion antes <br>
     * 
     * @return El capitan del equipo, null si el capitan no ha sido puesto
     */
    public Jugador getCapitan() {
        return capitan;
    }

    /**
     * Pone el capitan del equipo
     * <b> post:</b> La alineacion queda con el jugador indicado como capitan
     * 
     * @param capitan El jugador cual quiere poner como capitan
     */
    public void setCapitan(Jugador capitan) {
        this.capitan = capitan;
    }

    /**
     * Devuelve una lista de todos los jugadores de la alineacion
     * <b> pre </b> Se debe haber inicializado la alineacion primero. Debe haber
     * minimo un jugador en cada posicion <br>
     * 
     * @return La lista de jugadores que se tiene en la alineacion
     */
    public ArrayList<Jugador> getJugadores() {

        ArrayList<Jugador> jugadoresList = new ArrayList<Jugador>();
        Object[] posiciones = jugadores.keySet().toArray();

        for (int i = 0; i < 4; i++) {
            Posicion posicion = (Posicion) posiciones[i];
            ArrayList<Jugador> listaporposicion = jugadores.get(posicion);
            jugadoresList.addAll(listaporposicion);
        }
        return jugadoresList;
    }
}
