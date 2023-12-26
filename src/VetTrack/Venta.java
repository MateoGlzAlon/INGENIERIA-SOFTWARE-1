package VetTrack;

import java.util.ArrayList;

/**
 * Clase que representa una Venta en el sistema de VetTrack.
 */
public class Venta {
    
    private int idVenta;               				// Identificador único de la venta.
    private int idUsuario;             				// Identificador del usuario asociado a la venta. LATER: PODRIAMOS USAR EL NOMBRE DE USUARIO
    private ArrayList<Integer> articulosVendidos;  	// Lista de identificadores de artículos vendidos.

    //=========================================================================
    
    //Constructor
    public Venta(int id, int idUsuario, ArrayList<Integer> articulosV) {
    	setIdVenta(id);
    	setIdUsuario(idUsuario);
    	setArticulosVendidos(articulosV);
    }
    
    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public ArrayList<Integer> getArticulosVendidos() { return articulosVendidos; }
    public void setArticulosVendidos(ArrayList<Integer> articulosVendidos) { this.articulosVendidos = articulosVendidos; }

}
