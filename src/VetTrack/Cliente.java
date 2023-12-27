package VetTrack;

import java.util.ArrayList;

/**
 * Clase que representa un Usuario de tipo Cliente en el sistema de VetTrack.
 */
public class Cliente extends Usuario {

	private String NombreCompleto; // Nombre completo del cliente.
	private String DNI;            // Número de documento de identidad del cliente.
	private String telefono;       // Número de teléfono del cliente.

    private ArrayList<Venta> historialCompras;				//Historial de Compras que ha realizado el cliente
    
	//=========================================================================

    // Constructor que utiliza los setters de la clase base (Usuario) para inicializar los campos.
    public Cliente(int idUsuario, String NombreCompleto, String DNI, String telefono, String nombreUsuario, String contrasena, ArrayList<Venta> historialCompras) {
        super(idUsuario, nombreUsuario, contrasena, "Cliente");
        
        setNombreCompleto(NombreCompleto);
        setTelefono(telefono);
        setDNI(DNI);
        setHistorialCompras(historialCompras);
    }
    
	public String getNombreCompleto() { return NombreCompleto; }
	public void setNombreCompleto(String nombreCompleto) { NombreCompleto = nombreCompleto; }

	public String getDNI() { return DNI; }
	public void setDNI(String DNI) { this.DNI = DNI; }

	public String getTelefono() { return telefono; }
	public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public ArrayList<Venta> getHistorialCompras() { return historialCompras; }
    public void setHistorialCompras(ArrayList<Venta> historialCompras) { this.historialCompras = historialCompras; }

}
