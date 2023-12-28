package VetTrack;

/**
 * Clase que representa un Usuario de tipo Administrador en el sistema de VetTrack.
 */
public class Administrador extends Usuario {

	private String cadenaInicioSesion;
	
    // Constructor que utiliza los setters de la clase base (Usuario) para inicializar los campos.
    public Administrador(int idUsuario, String cadenaInicioSesion, String nombreUsuario, String contrasena) {
        super(idUsuario, nombreUsuario, contrasena, "Administrador");
        
        setCadenaInicioSesion(cadenaInicioSesion);
        
    }

	//=========================================================================

    private void setCadenaInicioSesion(String cadena) { this.cadenaInicioSesion = cadena;}
    public String getCadenaInicioSesion() {return this.cadenaInicioSesion;}
    
}
