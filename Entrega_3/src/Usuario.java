import java.io.Serializable;

public class Usuario implements Serializable {
	protected String nombre;
	protected String contrasena;

	public Usuario(String nombre, String contrasena) {
		super();
		this.nombre = nombre;
		this.contrasena = contrasena;
	}

	/**
	 * Devuelve el nombre del usuario
	 * 
	 * @return El nombre del usuario
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Devuelve la contraseña del usuario
	 * 
	 * @return La contraseña del usuario
	 */
	public String getContrasena() {
		return contrasena;
	}
}
