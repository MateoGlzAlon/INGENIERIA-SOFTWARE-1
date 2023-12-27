package VetTrack;

/**
 * Clase que representa a un Usuario en el sistema de 	VetTrack.
 */



public class Usuario {

	private int idUsuario;         // Identificador único del usuario. LATER: PODEMOS QUITAR ESTO Y USAR SOLO EL NOMBRE DE USUARIO
	
	private String nombreUsuario;  // Nombre de usuario para iniciar sesión.
	private String contrasena;     // Contraseña del usuario para iniciar sesión.
	
	private String rolUsuario; // Puede ser tanto "Administrador" como "Cliente"

	//=========================================================================
	
	//Constructor
	public Usuario(int idUsuario, String nombreUsuario, String contrasena, String rol) {
		setIdUsuario(idUsuario);
		setNombreUsuario(nombreUsuario);
		setContrasena(contrasena);
		setRol(rol);
	}
	

	public int getIdUsuario() { return idUsuario; }
	public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

	public String getNombreUsuario() { return nombreUsuario; }
	public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

	public String getContrasena() { return contrasena; }
	public void setContrasena(String contrasena) { this.contrasena = contrasena; }

	
	public String getRol() { return this.rolUsuario; }
	public void setRol(String rol) {this.rolUsuario = rol;}
	
}
