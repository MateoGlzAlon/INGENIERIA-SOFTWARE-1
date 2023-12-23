package VetTrack;

/**
 * Clase que representa a una Mascota en el sistema de VetTrack.
*/

public class Mascota {

	private int numPasaporte;        // Número de pasaporte de la mascota.
	private String nombre;           // Nombre de la mascota.
	private String especie;          // Especie de la mascota.
	private String raza;             // Raza de la mascota.
	private String fechaNacimiento;  // Fecha de nacimiento de la mascota. LATER: Considerar crear una clase 'Fecha'.

	private Cliente dueño;           // Dueño de la mascota.

	//=========================================================================
	
    public int getNumPasaporte() { return numPasaporte;  }
    public void setNumPasaporte(int numPasaporte) { this.numPasaporte = numPasaporte; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public Cliente getDueño() { return dueño; }
    public void setDueño(Cliente dueño) { this.dueño = dueño; }
    
    
    
    
}
