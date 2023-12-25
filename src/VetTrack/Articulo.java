package VetTrack;

/**
 * Clase que representa un Articulo en el sistema de VetTrack.
 */
public class Articulo {

	private int idArticulo;        // Identificador único del artículo.
	private String nombre;         // Nombre del artículo.
	private String descripcion;    // Descripción del artículo.
	private String marca;          // Marca del artículo.
	
	//=========================================================================

	public int getIdArticulo() { return idArticulo;  }
	public void setidArticulo(int idArticulo) { this.idArticulo = idArticulo; }

	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }

	public String getDescripcion() { return descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

	public String getMarca() { return marca; }
	public void setRaza(String marca) { this.marca = marca; }

}