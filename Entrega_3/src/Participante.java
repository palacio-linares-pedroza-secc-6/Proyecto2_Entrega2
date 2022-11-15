public class Participante extends Usuario {
    EquipoFantasia equipo;

    public Participante(String nombre, String contrasena) {
        super(nombre, contrasena);

    }

    /**
     * Devuelve el equipo de fantasia relacionado al usuario
     * 
     * @return El equipo de fantasia creado por el usuario, null si no ha creado uno
     */
    public EquipoFantasia getEquipo() {
        return equipo;
    }

    /**
     * Pone el equipo especificado como el equipo de fantasia del usuario <br>
     * <b> post </b> El atributo de equipo del usuario deberia ser el equipo
     * especificado
     * 
     * @param equipo
     */
    public void setEquipo(EquipoFantasia equipo) {
        this.equipo = equipo;
    }

    /**
     * Crea un equipo de fantasia y lo pone como el equipo de fantasia del usuario
     * 
     * @param nombreEquipo    Nombre del equipo de fantasia, no valida si ese nombre
     *                        ya lo tiene alguien mas
     * @param temporadaActual Temporada en la que jugara el equipo
     * @return El equipo de fantasia creado por el usuario
     */
    public EquipoFantasia crearEquipoFantasia(String nombreEquipo, Temporada temporadaActual) {

        EquipoFantasia equipoFantasia = new EquipoFantasia(nombreEquipo, temporadaActual);
        setEquipo(equipoFantasia);
        return equipoFantasia;
    }

    /**
     * Le añade un jugador al equipo de fantasia del usuario <br>
     * <b> pre:</b> El usuario debe tener un equipo de fantasia <br>
     * <b> post: </b> Al equipo de fantasia del usuario se le añade el jugador y se
     * le quita el valor de este de su presupuesto
     * 
     * @param jugador Jugador al que desea añadir
     */
    public void comprarJugador(Jugador jugador) {
        equipo.setPresupuesto(equipo.getPresupuesto() - jugador.getValor());
        equipo.addJugador(jugador);
    }

    /**
     * Remueve un jugador del equipo de fantasia del usuario <br>
     * <b> pre:</b> El usuario debe tener un equipo de fantasia <br>
     * <b> post: </b> Al equipo de fantasia del usuario se le quita el jugador y se
     * le añade el valor de este de su presupuesto
     * 
     * @param jugador Jugador que desea remover
     */
    public void venderJugador(Jugador jugador) {
        equipo.setPresupuesto(equipo.getPresupuesto() + jugador.getValorVenta());
        equipo.removeJugador(jugador);
    }

}