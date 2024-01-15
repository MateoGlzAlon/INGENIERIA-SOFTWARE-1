package VetTrack.Controlador;

import java.util.Date;
import java.sql.Time;

public class Cita {
	private int idCita;
    private int idUsuario;
    private int idMascota;
    private Date fechaCita;
    private Time horaCita;
    private String descripcion;

    public Cita(int idCita, int idUsuario, Date fechaCita, Time horaCita, int idMascota, String descripcion) {

    	setIdCita(idCita);
    	setIdUsuario(idUsuario);
    	setFechaCita(fechaCita);
    	setHoraCita(horaCita);
    	setIdMascota(idMascota);
    	setDescripcion(descripcion);

    }

    public int getIdUsuario() {return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public Date getFechaCita() { return fechaCita; }
    public void setFechaCita(Date fechaCita) { this.fechaCita = fechaCita; }

    public Time getHoraCita() { return horaCita; }
    public void setHoraCita(Time horaCita) { this.horaCita = horaCita; }

	public String getDescripcion() { return descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

	public int getIdMascota() { return idMascota; }
	public void setIdMascota(int idMascota) { this.idMascota = idMascota; }

	public int getIdCita() { return idCita; }
	public void setIdCita(int idCita) { this.idCita = idCita; }
}
