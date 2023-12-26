package VetTrack;

/**
 * Clase que representa una Cita en el sistema de VetTrack.
 */ 
public class Cita {

    private Fecha fechaCita;      // Fecha de la cita.
    private Hora horaCita;        // Hora de la cita.
    private int idCliente;        // Identificador del cliente asociado a la cita.
    private int pasaporteMascota; // Número de pasaporte de la mascota asociada a la cita.

    //=========================================================================
    
    public Cita(Fecha fecha, Hora hora, int idC, int passport) {
    	
    	setFechaCita(fecha);
    	setHoraCita(hora);
    	setIdCliente(idC);
    	setPasaporteMascota(passport);

    }
    

    public Fecha getFechaCita() { return fechaCita; }
    public void setFechaCita(Fecha fechaCita) { this.fechaCita = fechaCita; }

    public Hora getHoraCita() { return horaCita; }
    public void setHoraCita(Hora horaCita) { this.horaCita = horaCita; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getPasaporteMascota() { return pasaporteMascota; }
    public void setPasaporteMascota(int pasaporteMascota) { this.pasaporteMascota = pasaporteMascota; }

}
