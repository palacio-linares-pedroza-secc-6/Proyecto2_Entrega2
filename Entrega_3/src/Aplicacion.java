import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import javax.swing.*;

public class Aplicacion {
	// atributos clase
	static DataDam dataDam = new DataDam();
	static Administrador admin;
	static Participante user;
	private HashMap<String, Temporada> historialTemporadas;
	static Temporada temporadaActual;
	private static ArrayList<Administrador> administradores = new ArrayList<Administrador>();
	private static ArrayList<Participante> participantes = new ArrayList<Participante>();
	private static HashMap<String, Administrador> findAdministrador = new HashMap<>();
	private static HashMap<String, Participante> findParticipantes = new HashMap<>();

	/**
	 * Crea un usuario y lo guarda en la aplicación
	 * 
	 * @param nombre     Nombre con el que desea guardar al usuario
	 * @param contrasena Contraseña con la que quiere relacionar al usuario
	 * @param tipo       Tipo de Usuario (Administrador o Participante)
	 * @return Retorna un string dependiendo del caso. Si ya existe el nombre de
	 *         usuario devuelve "Ese nombre de usuario ya existe"
	 * @throws IOException
	 */
	public static String CrearUsuario(String nombre, String contrasena, Tipo_Usuario tipo)
			throws IOException {

		if (tipo == Tipo_Usuario.ADMINISTRADOR) {
			for (int i = 0; i < administradores.size(); i++) {
				Administrador adminlista = administradores.get(i);
				if (adminlista.getNombre().equals(nombre)) {
					return "ya existe";
				}
			}
			Administrador Admin = new Administrador(nombre, contrasena);
			administradores.add(Admin);
			findAdministrador.put(nombre, Admin);
			dataDam.addAdministrador(nombre, contrasena);

			return "Se ha creado";
		} else if (tipo == Tipo_Usuario.PARTICIPANTE) {
			for (int i = 0; i < participantes.size(); i++) {
				Participante userlista = participantes.get(i);
				if (userlista.getNombre().equals(nombre)) {
					return "ya existe";

				}
			}
			Participante User = new Participante(nombre, contrasena);
			participantes.add(User);
			findParticipantes.put(nombre, User);
			dataDam.addParticipante(nombre, contrasena);

			return "Se ha creado";
		} else {
			return null;
		}
	}

	public static void reCrearUsuarios(String nombre, String contrasena, Tipo_Usuario tipo) {

		if (tipo == Tipo_Usuario.ADMINISTRADOR) {

			Administrador Admin = new Administrador(nombre, contrasena);
			administradores.add(Admin);
			findAdministrador.put(nombre, Admin);
		} else if (tipo == Tipo_Usuario.PARTICIPANTE) {
			Participante User = new Participante(nombre, contrasena);
			participantes.add(User);
			findParticipantes.put(nombre, User);
		}
	}

	public static String logIn(String nombre, String contrasena, Tipo_Usuario tipo) {

		if (tipo == Tipo_Usuario.ADMINISTRADOR) {
			for (int i = 0; i < administradores.size(); i++) {
				Administrador adminlista = administradores.get(i);

				if (adminlista.getNombre().equals(nombre) && adminlista.getContrasena().equals(contrasena)) {
					admin = adminlista;
					return "LogIn Valido";

				}
			}
			return "LogIn NO Valido";
		} else if (tipo == Tipo_Usuario.PARTICIPANTE) {
			for (int i = 0; i < participantes.size(); i++) {
				Participante userlista = participantes.get(i);

				if (userlista.getNombre().equals(nombre) && userlista.getContrasena().equals(contrasena)) {
					user = userlista;
					return "LogIn Valido";
				}
			}
			return "LogIn NO Valido";
		} else {
			return "Ingrese un tipo Valido";
		}
	}

	public static Temporada getTemporadaActual() {
		return temporadaActual;
	}

	public static void setTemporada(Temporada temporada) {
		temporadaActual = temporada;
	}

	public void cerrarTemporadaActual(Temporada temporadaNueva) {
		historialTemporadas.put(temporadaActual.getNombreTemporada(), temporadaActual);
		setTemporada(temporadaNueva);
	}

	public ArrayList<EquipoFantasia> QueuetoList(PriorityQueue<Pair> ganadores) {
		ArrayList<EquipoFantasia> lista = new ArrayList<EquipoFantasia>(ganadores.size());
		while (!ganadores.isEmpty()) {
			lista.add((EquipoFantasia) ganadores.poll().getValue());
		}
		return lista;
	}

	public static void crearMapa(HashMap<Posicion, ArrayList<Jugador>> hashmap, ArrayList<Jugador> lista) {
		for (int i = 0; i < lista.size(); i++) {
			Jugador jugadoract = lista.get(i);
			if (hashmap.containsKey(jugadoract.getPosicion())) {
				ArrayList<Jugador> listaposicion = hashmap.get(jugadoract.getPosicion());
				listaposicion.add(jugadoract);
			} else {
				ArrayList<Jugador> listanuevaposicion = new ArrayList<Jugador>();
				listanuevaposicion.add(jugadoract);
				hashmap.put(jugadoract.getPosicion(), listanuevaposicion);
			}
		}

	}

	public ArrayList<EquipoFantasia> getGanadores() {
		PriorityQueue<Pair> ganadores = temporadaActual.getRankingEquipoFantasia();
		ArrayList<EquipoFantasia> listaganadores = QueuetoList(ganadores);
		return listaganadores;

	}

	public ArrayList<EquipoFantasia> getGanadoresPasados(String nombreTemporada) {
		Temporada temporadavieja = historialTemporadas.get(nombreTemporada);
		PriorityQueue<Pair> ganadores = temporadavieja.getRankingEquipoFantasia();
		ArrayList<EquipoFantasia> Ganadores = QueuetoList(ganadores);
		return Ganadores;

	}

	public String getHora() {
		String hora = LocalTime.now().toString();
		return hora;
	}

	public Aplicacion() throws IOException {

		dataDam.creatFilesUsuarios();
		dataDam.loadUsuarios();
		Temporada temp = SerializarObjeto
				.deserializarObjeto(System.getProperty("user.dir") + "/data/TemporadaActual.txt", Temporada.class);
		if (temp != null) {
			temporadaActual = temp;
		}
	}

	public void cosas() throws IOException {

		int opcion;
		boolean continuar = true;

		System.out.println("Bienvenido a Equipo de Fantasia V1");

		while (continuar) {

			opcion = Integer.parseInt(input("\nSeleccione una opcion valida"));

			if (opcion > 3 || opcion < 0) {
				System.out.println("Opcion invalida, seleccione una opcion valida");
			}

			else if (opcion == 1) {
				String nombre;
				String contrasena;
				int opcionUsuario;

				nombre = input("\nIngrese su nombre de Usuario");
				contrasena = input("Ingrese su constraseña de Usuario");

				System.out.println("\n============================");
				System.out.println("Nombre: " + nombre);
				System.out.println("Contraseña: " + contrasena);
				System.out.println("============================");

				opcionUsuario = Integer.parseInt(input("\nUsted es?\n1.Participante\n2.Administrador\nOpcion"));

				if (opcionUsuario == 1) {
					System.out.println(CrearUsuario(nombre, contrasena, Tipo_Usuario.PARTICIPANTE));
				} else if (opcionUsuario == 2) {
					System.out.println(CrearUsuario(nombre, contrasena, Tipo_Usuario.ADMINISTRADOR));
				} else if (opcionUsuario > 2 || opcionUsuario <= 0) {
					System.out.println("\n!Error...Eliga una opcion valida");
				}
			}

			else if (opcion == 2) {

				String nombre;
				String contrasena;
				int opcionUsuario;
				String resultado;
				JOptionPane.showMessageDialog(null, "NO SE ENCONTRO UN ARCHIVO", "ERROR", JOptionPane.ERROR_MESSAGE);
				nombre = input("Ingrese su nombre de Usuario");
				contrasena = input("Ingrese su constraseña de Usuario");

				opcionUsuario = Integer.parseInt(input("\nUsted es?\n1.Participante\n2.Administrador\nOpcion"));
				if (opcionUsuario == 1) {
					resultado = logIn(nombre, contrasena, Tipo_Usuario.PARTICIPANTE);
					System.out.println(resultado);
					if (resultado.equals("\nLogIn Valido")) {

						// Menu para el participante
						System.out.println("\nBienvenido " + nombre);
						Participante user = findParticipantes.get(nombre);
						boolean continuarParticipante = true;

						while (continuarParticipante) {
							opcion = Integer.parseInt(input("Seleccione una opcion valida"));
							if (opcion > 4 || opcion < 0) {
								System.out.println("Opcion invalida, seleccione una opcion valida");
							}

							else if (opcion == 1) {

								if (temporadaActual == null) {
									System.out.println("No existe una tempoarada actual para jugar");
								}

								else {

									if (user.getEquipo() != null) {
										System.out.println("Ya tiene un equipo para esta temporada");
									}

									else {
										String nombreEquipo = input("Ingrese el nombre de su equipo:");
										EquipoFantasia equipoFantasia = user.crearEquipoFantasia(nombreEquipo,
												temporadaActual);
										Mercado mercado = temporadaActual.getMercado();
										ArrayList<Jugador> jugadoresfantasy = equipoFantasia.getJugadores();
										while (jugadoresfantasy.size() < 2) {
											mercado.mostrarJugadores(Posicion.PORTERO);
											ArrayList<Jugador> jugadoresMercado = mercado
													.getJugadoresporPosicion(Posicion.PORTERO);
											int posicionjugador = Integer.parseInt(input(
													"Porfavor escoga "
															+ String.valueOf(2 - equipoFantasia.getJugadores().size())
															+ " arqueros"));

											Jugador jugadorescogido = jugadoresMercado.get(posicionjugador - 1);
											if (equipoFantasia.getPresupuesto() >= jugadorescogido.getValor()) {

												if (jugadoresfantasy.isEmpty()) {
													user.comprarJugador(jugadorescogido);
													jugadoresfantasy.add(jugadorescogido);
												} else if (!jugadoresfantasy.contains(jugadorescogido)) {
													user.comprarJugador(jugadorescogido);
													jugadoresfantasy.add(jugadorescogido);
												} else {
													System.out.println("\nYa tiene a el jugador "
															+ jugadorescogido.getNombre() + " en su equipo\n");
												}
												System.out.println(
														"\nLe quedan " + String.valueOf(equipoFantasia.getPresupuesto())
																+ " dolares\n");

											} else {
												System.out.println(
														"No tienes suficiente presupuesto para este jugador\nFaltan "
																+ String.valueOf(jugadorescogido.getValor()
																		- equipoFantasia.getPresupuesto())
																+ " dolares\n");
											}
										}
										while (jugadoresfantasy.size() < 7) {
											mercado.mostrarJugadores(Posicion.DEFENSA);
											ArrayList<Jugador> jugadoresMercado = mercado
													.getJugadoresporPosicion(Posicion.DEFENSA);
											int posicionjugador = Integer.parseInt(input(
													"Porfavor escoga "
															+ String.valueOf(7 - equipoFantasia.getJugadores().size())
															+ " defensas"));

											Jugador jugadorescogido = jugadoresMercado.get(posicionjugador - 1);
											if (equipoFantasia.getPresupuesto() >= jugadorescogido.getValor()) {
												if (!jugadoresfantasy.contains(jugadorescogido)) {
													user.comprarJugador(jugadorescogido);
													jugadoresfantasy.add(jugadorescogido);
												} else {
													System.out.println("\nYa tiene a el jugador "
															+ jugadorescogido.getNombre() + " en su equipo\n");
												}
												System.out.println(
														"\nLe quedan " + String.valueOf(equipoFantasia.getPresupuesto())
																+ " dolares\n");
											} else {
												System.out.println(
														"No tienes suficiente presupuesto para este jugador\nFaltan "
																+ String.valueOf(jugadorescogido.getValor()
																		- equipoFantasia.getPresupuesto())
																+ " dolares\n");
											}
										}
										while (jugadoresfantasy.size() < 12) {
											mercado.mostrarJugadores(Posicion.MEDIOCAMPISTA);
											ArrayList<Jugador> jugadoresMercado = mercado
													.getJugadoresporPosicion(Posicion.MEDIOCAMPISTA);
											int posicionjugador = Integer.parseInt(input(
													"Porfavor escoga "
															+ String.valueOf(12 - equipoFantasia.getJugadores().size())
															+ " medios"));

											Jugador jugadorescogido = jugadoresMercado.get(posicionjugador - 1);
											if (equipoFantasia.getPresupuesto() >= jugadorescogido.getValor()) {
												if (!jugadoresfantasy.contains(jugadorescogido)) {
													user.comprarJugador(jugadorescogido);
													jugadoresfantasy.add(jugadorescogido);
												} else {
													System.out.println("\nYa tiene a el jugador "
															+ jugadorescogido.getNombre() + " en su equipo\n");
												}
												System.out.println(
														"\nLe quedan " + String.valueOf(equipoFantasia.getPresupuesto())
																+ " dolares\n");
											} else {
												System.out.println(
														"No tienes suficiente presupuesto para este jugador\nFaltan "
																+ String.valueOf(jugadorescogido.getValor()
																		- equipoFantasia.getPresupuesto())
																+ " dolares\n");
											}
										}
										while (jugadoresfantasy.size() < 15) {

											mercado.mostrarJugadores(Posicion.DELANTERO);
											ArrayList<Jugador> jugadoresMercado = mercado
													.getJugadoresporPosicion(Posicion.DELANTERO);
											int posicionjugador = Integer.parseInt(input(
													"Porfavor escoga "
															+ String.valueOf(15 - equipoFantasia.getJugadores().size())
															+ " delanteros"));

											Jugador jugadorescogido = jugadoresMercado.get(posicionjugador - 1);
											if (equipoFantasia.getPresupuesto() >= jugadorescogido.getValor()) {
												if (jugadoresfantasy.contains(jugadorescogido) == false) {
													user.comprarJugador(jugadorescogido);
													jugadoresfantasy.add(jugadorescogido);
												} else {
													System.out.println("\nYa tiene a el jugador "
															+ jugadorescogido.getNombre() + " en su equipo\n");
												}
												System.out.println(
														"\nLe quedan " + String.valueOf(equipoFantasia.getPresupuesto())
																+ " dolares\n");
											} else {
												System.out.println(
														"No tienes suficiente presupuesto para este jugador\nFaltan "
																+ String.valueOf(jugadorescogido.getValor()
																		- equipoFantasia.getPresupuesto())
																+ " dolares\n");
											}

										}
										temporadaActual.addEquipoFantasy(equipoFantasia);
										System.out.println("\nSe ha creado con exito el equipo: " + nombreEquipo);
									}
								}
							}

							else if (opcion == 2) {

								if (user.getEquipo() == null) {
									System.out.println(
											"No tiene un equipos de fantasia por favor cree uno antes de ir al mercado");
								} else {
									EquipoFantasia equipofantasy = user.getEquipo();
									ArrayList<Jugador> jugadoresequipofantasy = equipofantasy.getJugadores();
									System.out.println("Jugadores del Equipo de Fantasia");
									for (int i = 0; i < jugadoresequipofantasy.size(); i++) {
										Jugador jugador = jugadoresequipofantasy.get(i);
										String nombreplayer = jugador.getNombre();
										int valor = jugador.getValor();
										System.out.println(String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
									}
									int posicionjugador = Integer.parseInt(input(
											"Que jugador le gustaria vender"));
									Jugador jugadoravender = jugadoresequipofantasy.get(posicionjugador - 1);
									Posicion posicion = jugadoravender.getPosicion();
									user.venderJugador(jugadoravender);

									System.out.println("Bienvenido al Mercado de jugadores");
									Mercado mercado = temporadaActual.getMercado();
									ArrayList<Jugador> jugadoresmercado = mercado.getJugadoresporPosicion(posicion);
									mercado.mostrarJugadores(posicion);
									int posicionjugadorcompra = Integer.parseInt(input(
											"Que jugador le gustaria comprar"));
									Jugador jugadoracomprar = jugadoresmercado.get(posicionjugadorcompra - 1);
									user.comprarJugador(jugadoracomprar);
									System.out.println("Jugadores del Equipo de Fantasia");
									jugadoresequipofantasy = equipofantasy.getJugadores();
									for (int i = 0; i < jugadoresequipofantasy.size(); i++) {
										Jugador jugador = jugadoresequipofantasy.get(i);
										String nombreplayer = jugador.getNombre();
										int valor = jugador.getValor();
										System.out.println(String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
									}
								}
							}

							else if (opcion == 3) {
								EquipoFantasia equipofantasy = user.getEquipo();
								ArrayList<Jugador> jugadoresequipofantasy = equipofantasy.getJugadores();
								if (equipofantasy.getAlineacion() != null) {
									Alineacion alineacion = equipofantasy.getAlineacion();
									System.out.println("Selecione una opcion: \n");
									int opcionAlineacion = Integer
											.parseInt(input(
													"1. Crear Nueva Alineacion\n2. Cambiar Sustituto\n3. Cambiar Capitan"));
									if (opcionAlineacion == 1) {
										System.out.println("Jugadores del Equipo de Fantasia");
										equipofantasy.crearAlineacion(jugadoresequipofantasy);
										equipofantasy.setSusArquero(null);
										equipofantasy.setSusDefensa(null);
										equipofantasy.setSusDelantero(null);
										equipofantasy.setSusMedio(null);
										while (equipofantasy.getSusDefensa() == null) {
											ArrayList<Jugador> defensasfantasy = equipofantasy
													.getJugadoresPosicion(Posicion.DEFENSA);
											for (int i = 0; i < defensasfantasy.size(); i++) {
												Jugador jugador = defensasfantasy.get(i);
												String nombreplayer = jugador.getNombre();
												int valor = jugador.getValor();
												System.out.println(
														String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
											}

											int num_defensa = Integer.parseInt(input("Escoga su defensa suplente: "));
											Jugador defensa_sus = defensasfantasy.get(num_defensa - 1);
											alineacion.Sustituir(defensa_sus);
										}
										while (equipofantasy.getSusArquero() == null) {
											ArrayList<Jugador> arquerosfantasy = equipofantasy
													.getJugadoresPosicion(Posicion.PORTERO);
											for (int i = 0; i < arquerosfantasy.size(); i++) {
												Jugador jugador = arquerosfantasy.get(i);
												String nombreplayer = jugador.getNombre();
												int valor = jugador.getValor();
												System.out.println(
														String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
											}

											int num_arquero = Integer.parseInt(input("Escoga su portero suplente: "));
											Jugador arquero_sus = arquerosfantasy.get(num_arquero - 1);
											alineacion.Sustituir(arquero_sus);
										}
										while (equipofantasy.getSusDelantero() == null) {
											ArrayList<Jugador> delanterosfantasy = equipofantasy
													.getJugadoresPosicion(Posicion.DELANTERO);
											for (int i = 0; i < delanterosfantasy.size(); i++) {
												Jugador jugador = delanterosfantasy.get(i);
												String nombreplayer = jugador.getNombre();
												int valor = jugador.getValor();
												System.out.println(
														String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
											}

											int num_delantero = Integer
													.parseInt(input("Escoga su delantero suplente: "));
											Jugador delantero_sus = delanterosfantasy.get(num_delantero - 1);
											alineacion.Sustituir(delantero_sus);
										}
										while (equipofantasy.getSusMedio() == null) {
											ArrayList<Jugador> mediosfantasy = equipofantasy
													.getJugadoresPosicion(Posicion.MEDIOCAMPISTA);
											for (int i = 0; i < mediosfantasy.size(); i++) {
												Jugador jugador = mediosfantasy.get(i);
												String nombreplayer = jugador.getNombre();
												int valor = jugador.getValor();
												System.out.println(
														String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
											}

											int num_medio = Integer
													.parseInt(input("Escoga su mediocampista suplente: "));
											Jugador medio_sus = mediosfantasy.get(num_medio - 1);
											alineacion.Sustituir(medio_sus);
										}
										while (alineacion.getCapitan() == null) {
											System.out.println("Jugadores del Equipo de Fantasia");
											for (int i = 0; i < jugadoresequipofantasy.size(); i++) {
												Jugador jugador = jugadoresequipofantasy.get(i);
												String nombreplayer = jugador.getNombre();
												int valor = jugador.getValor();
												System.out.println(
														String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
											}
											int num = Integer
													.parseInt(input("Escoga su capitan: "));
											Jugador capitan = jugadoresequipofantasy.get(num - 1);
											alineacion.setCapitan(capitan);
										}
										System.out.println(alineacion.checkAlineacioncompleta());

									}

									else if (opcionAlineacion == 2) {

										System.out.println("Escoga la posicion del sustituto al cual quiere cambiar: ");
										int poooos = Integer.parseInt(
												input("\n1. PORTERO\n2. DEFENSA\n3. MEDIOCAMPISTA\n4. DELANTERO"));

										if (poooos == 1) {
											ArrayList<Jugador> arquerosfantasy = equipofantasy
													.getJugadoresPosicion(Posicion.PORTERO);
											for (int i = 0; i < arquerosfantasy.size(); i++) {
												Jugador jugador = arquerosfantasy.get(i);
												String nombreplayer = jugador.getNombre();
												int valor = jugador.getValor();
												System.out.println(
														String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
											}

											int num_arquero = Integer.parseInt(input("Escoga su portero suplente: "));
											Jugador arquero_sus = arquerosfantasy.get(num_arquero - 1);
											alineacion.Sustituir(arquero_sus);
										}

										else if (poooos == 2) {
											ArrayList<Jugador> defensasfantasy = equipofantasy
													.getJugadoresPosicion(Posicion.DEFENSA);
											for (int i = 0; i < defensasfantasy.size(); i++) {
												Jugador jugador = defensasfantasy.get(i);
												String nombreplayer = jugador.getNombre();
												int valor = jugador.getValor();
												System.out.println(
														String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
											}

											int num_defensa = Integer.parseInt(input("Escoga su defensa suplente: "));
											Jugador defensa_sus = defensasfantasy.get(num_defensa - 1);
											alineacion.Sustituir(defensa_sus);
											System.out.println("Jugadores del Equipo de Fantasia");
											for (int i = 0; i < jugadoresequipofantasy.size(); i++) {
												Jugador jugador = jugadoresequipofantasy.get(i);
												String nombreplayer = jugador.getNombre();
												int valor = jugador.getValor();
												System.out.println(
														String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
											}
											int num = Integer
													.parseInt(input("Escoga su capitan: "));
											Jugador capitan = jugadoresequipofantasy.get(num - 1);
											alineacion.setCapitan(capitan);
										}

										else if (poooos == 3) {
											ArrayList<Jugador> mediosfantasy = equipofantasy
													.getJugadoresPosicion(Posicion.MEDIOCAMPISTA);
											for (int i = 0; i < mediosfantasy.size(); i++) {
												Jugador jugador = mediosfantasy.get(i);
												String nombreplayer = jugador.getNombre();
												int valor = jugador.getValor();
												System.out.println(
														String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
											}

											int num_medio = Integer
													.parseInt(input("Escoga su mediocampista suplente: "));
											Jugador medio_sus = mediosfantasy.get(num_medio - 1);
											alineacion.Sustituir(medio_sus);
										}

										else if (poooos == 4) {
											ArrayList<Jugador> delanterosfantasy = equipofantasy
													.getJugadoresPosicion(Posicion.DELANTERO);
											for (int i = 0; i < delanterosfantasy.size(); i++) {
												Jugador jugador = delanterosfantasy.get(i);
												String nombreplayer = jugador.getNombre();
												int valor = jugador.getValor();
												System.out.println(
														String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
											}

											int num_delantero = Integer
													.parseInt(input("Escoga su delantero suplente: "));
											Jugador delantero_sus = delanterosfantasy.get(num_delantero - 1);
											alineacion.Sustituir(delantero_sus);
										}

										else {
											System.out.println("Escoga una opcion valida");
										}
									}

									else if (opcionAlineacion == 3) {
										System.out.println("Jugadores del Equipo de Fantasia");
										for (int i = 0; i < jugadoresequipofantasy.size(); i++) {
											Jugador jugador = jugadoresequipofantasy.get(i);
											String nombreplayer = jugador.getNombre();
											int valor = jugador.getValor();
											System.out.println(
													String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
										}
										int num = Integer
												.parseInt(input("Escoga su capitan: "));
										Jugador capitan = jugadoresequipofantasy.get(num - 1);
										alineacion.setCapitan(capitan);
									}

								} else {
									System.out.println("Jugadores del Equipo de Fantasia");
									equipofantasy.crearAlineacion(jugadoresequipofantasy);
									Alineacion alineacion = equipofantasy.getAlineacion();
									while (equipofantasy.getSusDefensa() == null) {
										ArrayList<Jugador> defensasfantasy = equipofantasy
												.getJugadoresPosicion(Posicion.DEFENSA);
										for (int i = 0; i < defensasfantasy.size(); i++) {
											Jugador jugador = defensasfantasy.get(i);
											String nombreplayer = jugador.getNombre();
											int valor = jugador.getValor();
											System.out.println(
													String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
										}

										int num_defensa = Integer.parseInt(input("Escoga su defensa suplente: "));
										Jugador defensa_sus = defensasfantasy.get(num_defensa - 1);
										alineacion.Sustituir(defensa_sus);
									}
									while (equipofantasy.getSusArquero() == null) {
										ArrayList<Jugador> arquerosfantasy = equipofantasy
												.getJugadoresPosicion(Posicion.PORTERO);
										for (int i = 0; i < arquerosfantasy.size(); i++) {
											Jugador jugador = arquerosfantasy.get(i);
											String nombreplayer = jugador.getNombre();
											int valor = jugador.getValor();
											System.out.println(
													String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
										}

										int num_arquero = Integer.parseInt(input("Escoga su portero suplente: "));
										Jugador arquero_sus = arquerosfantasy.get(num_arquero - 1);
										alineacion.Sustituir(arquero_sus);
									}
									while (equipofantasy.getSusDelantero() == null) {
										ArrayList<Jugador> delanterosfantasy = equipofantasy
												.getJugadoresPosicion(Posicion.DELANTERO);
										for (int i = 0; i < delanterosfantasy.size(); i++) {
											Jugador jugador = delanterosfantasy.get(i);
											String nombreplayer = jugador.getNombre();
											int valor = jugador.getValor();
											System.out.println(
													String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
										}

										int num_delantero = Integer
												.parseInt(input("Escoga su delantero suplente: "));
										Jugador delantero_sus = delanterosfantasy.get(num_delantero - 1);
										alineacion.Sustituir(delantero_sus);
									}
									while (equipofantasy.getSusMedio() == null) {
										ArrayList<Jugador> mediosfantasy = equipofantasy
												.getJugadoresPosicion(Posicion.MEDIOCAMPISTA);
										for (int i = 0; i < mediosfantasy.size(); i++) {
											Jugador jugador = mediosfantasy.get(i);
											String nombreplayer = jugador.getNombre();
											int valor = jugador.getValor();
											System.out.println(
													String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
										}

										int num_medio = Integer
												.parseInt(input("Escoga su mediocampista suplente: "));
										Jugador medio_sus = mediosfantasy.get(num_medio - 1);
										alineacion.Sustituir(medio_sus);
										while (alineacion.getCapitan() == null) {
											System.out.println("Jugadores del Equipo de Fantasia");
											for (int i = 0; i < jugadoresequipofantasy.size(); i++) {
												Jugador jugador = jugadoresequipofantasy.get(i);
												String nombreplayer = jugador.getNombre();
												int valor = jugador.getValor();
												System.out.println(
														String.valueOf(i + 1) + ".||" + nombreplayer + "||" + valor);
											}
											int num = Integer
													.parseInt(input("Escoga su capitan: "));
											Jugador capitan = jugadoresequipofantasy.get(num - 1);
											alineacion.setCapitan(capitan);
										}
									}
									System.out.println(alineacion.checkAlineacioncompleta());
								}
							} else if (opcion == 4) {
								continuarParticipante = false;
								System.out.println("Se ha cerrado sesion\n");
							}

						}
					}

				} else if (opcionUsuario == 2) {
					resultado = logIn(nombre, contrasena, Tipo_Usuario.ADMINISTRADOR);
					System.out.println(resultado);
					if (resultado.equals("\nLogIn Valido")) {

						// Menu para el administrador
						System.out.println("\nBienvenido administrador " + nombre);
						Administrador admin = findAdministrador.get(nombre);
						boolean continuarAdministrador = true;

						while (continuarAdministrador) {
							opcion = Integer.parseInt(input("Seleccione una opcion valida"));
							if (opcion > 3 || opcion < 0) {
								System.out.println("Opcion invalida, seleccione una opcion valida");
							}

							else if (opcion == 1) {
								/*
								 * String nombreTemp = input("Ingrese el nombre de la temporada");
								 * String filePartidos =
								 * input("Ingrese el nombre de el archivo de la temporada");
								 * String fileEquipos = input(
								 * "Ingrese el nombre de el archivo de los equipos de la temporada");
								 * String fileJugadores = input(
								 * "Ingrese el nombre de el archivo de los jugadores de la temporada");
								 * 
								 * int presupuesto = Integer
								 * .parseInt(input("Ingrese el presupuesto para los equipos de esta temporada"))
								 * ;
								 * Temporada temporada = admin.crearTemporada(nombreTemp, presupuesto,
								 * filePartidos,
								 * fileEquipos,
								 * fileJugadores);
								 * setTemporada(temporada);
								 * temporada.crearMercado();
								 */}

							else if (opcion == 2) {

								String fechapartido = input("Ingrese la fecha en la que desea buscar el partido");
								String localpartido = input(
										"Ingrese la abreviacion de el equipo local que juega en esa fecha");
								String horaPartido = input("Ingrese la hora a la que inicia el partido");
								String nombreFilePartido = input("Ingrese el nombre de el archivo del partido");
								String nombrePartido = horaPartido + localpartido;
								Fecha fecha = temporadaActual.getFecha(fechapartido);
								Partido partido = fecha.getPartido(nombrePartido);
								// admin.finalizarPartido(partido, nombrePartido, nombreFilePartido, fecha);
								// partido.setfileReporte(nombreFilePartido);

								// admin.finalizarPartido(partido, nombrePartido, nombreFilePartido, fecha);
								// partido.setfileReporte(nombreFilePartido);
								ArrayList<EquipoFantasia> lista_fantasy = Temporada.getEquiposFantasy();
								if (lista_fantasy != null) {
									System.out.println(lista_fantasy.size());
									for (int c = 0; c < lista_fantasy.size(); c++) {
										EquipoFantasia equipo_fantasy = lista_fantasy.get(c);
										System.out.println(equipo_fantasy.getNombre());
										Alineacion all = equipo_fantasy.getAlineacion();
										all.jugarPartido(partido, fecha);
										int puntos_equipo = 0;
										PriorityQueue<Pair> pq = equipo_fantasy.getRankingJugadores();
										Iterator<Pair> value = pq.iterator();
										while (value.hasNext()) {
											Pair pair = value.next();
											Jugador ppplayer = (Jugador) pair.getValue();
											puntos_equipo += pair.getKey();
											System.out.println(ppplayer.getNombre() + " " + pair.getKey());
										}
										Pair pair_equipo = new Pair(puntos_equipo, equipo_fantasy);
										temporadaActual.addEquipoFantasyRanking(pair_equipo);
										PriorityQueue<Pair> pq2 = temporadaActual.getRankingEquipoFantasia();
										while (!pq2.isEmpty()) {
											Pair sapo = pq2.poll();
											System.out.println(sapo.getValue() + " " + sapo.getKey());
										}
									}
								}
							}

							else if (opcion == 3) {
								continuarAdministrador = false;
								System.out.println("Se ha cerrado sesion\n");
							}

						}
					}
					// FINAL DE MENU
				}
			}

			else if (opcion == 3)

			{
				continuar = false;
				System.out.println("Gracias por usar Fantasia V1");

			}
		} // acaba el while aca

	}

	public static String input(String mensaje) {
		try {
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		} catch (IOException e) {
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}

}