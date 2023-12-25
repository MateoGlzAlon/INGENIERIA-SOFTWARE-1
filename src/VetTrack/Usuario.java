package VetTrack;

/**
 * Clase que representa a un Usuario en el sistema de 	VetTrack.
 */
public class Usuario {

	private int idUsuario;         // Identificador único del usuario. LATER: PODEMOS QUITAR ESTO Y USAR SOLO EL NOMBRE DE USUARIO
	
	private String nombreUsuario;  // Nombre de usuario para iniciar sesión.
	private String contrasena;     // Contraseña del usuario para iniciar sesión.

	//=========================================================================

	
	//Constructor
	public Usuario(int idUsuario, String nombreUsuario, String contrasena) {
		setIdUsuario(idUsuario);
		setNombreUsuario(nombreUsuario);
		setContrasena(contrasena);
	}
	

	public int getIdUsuario() { return idUsuario; }
	public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

	public String getNombreUsuario() { return nombreUsuario; }
	public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

	public String getContrasena() { return contrasena; }
	public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
