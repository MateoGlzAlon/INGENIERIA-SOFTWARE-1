package VetTrack;

import java.util.ArrayList;

/**
 * Clase que representa un Usuario de tipo Cliente en el sistema de VetTrack.
 */
public class Cliente extends Usuario {

    private ArrayList<Venta> historialCompras;				//Historial de Compras que ha realizado el cliente
    
	//=========================================================================

    // Constructor que utiliza los setters de la clase base (Usuario) para inicializar los campos.
    public Cliente(int idUsuario, String NombreCompleto, String DNI, String telefono, String nombreUsuario, String contrasena, ArrayList<Venta> historialCompras) {
        super(idUsuario, NombreCompleto, DNI, telefono, nombreUsuario, contrasena);
        
        setHistorialCompras(historialCompras);
    }
    
    public ArrayList<Venta> getHistorialCompras() { return historialCompras; }
    public void setHistorialCompras(ArrayList<Venta> historialCompras) { this.historialCompras = historialCompras; }

}
