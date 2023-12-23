package VetTrack;

/**
 * Clase que representa a un Usuario en el sistema de 	VetTrack.
 */
public class Usuario {

	private int idUsuario;         // Identificador único del usuario. LATER: PODEMOS QUITAR ESTO Y USAR SOLO EL NOMBRE DE USUARIO
	private String NombreCompleto; // Nombre completo del usuario.
	private String DNI;            // Número de documento de identidad del usuario.
	private String telefono;       // Número de teléfono del usuario.

	private String nombreUsuario;  // Nombre de usuario para iniciar sesión.
	private String contrasena;     // Contraseña del usuario para iniciar sesión.

	//=========================================================================

	
	//Constructor
	public Usuario(int idUsuario, String NombreCompleto, String DNI, String telefono, String nombreUsuario, String contrasena) {
		setIdUsuario(idUsuario);
		setNombreCompleto(NombreCompleto);
		setDNI(DNI);
		setTelefono(telefono);
		setNombreUsuario(nombreUsuario);
		setContrasena(contrasena);
	}
	

	public int getIdUsuario() { return idUsuario; }
	public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

	public String getNombreCompleto() { return NombreCompleto; }
	public void setNombreCompleto(String nombreCompleto) { NombreCompleto = nombreCompleto; }

	public String getDNI() { return DNI; }
	public void setDNI(String DNI) { this.DNI = DNI; }

	public String getTelefono() { return telefono; }
	public void setTelefono(String telefono) { this.telefono = telefono; }

	public String getNombreUsuario() { return nombreUsuario; }
	public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

	public String getContrasena() { return contrasena; }
	public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
