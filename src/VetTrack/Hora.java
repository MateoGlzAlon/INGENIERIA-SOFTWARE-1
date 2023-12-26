package VetTrack;

public class Hora {

	private int horas;
	private int minutos;
	private int segundos;
	private String diaNoc;
	
	public Hora(int hora, int minutos, int segundos) {
		setHora(hora, minutos, segundos);
	}
	
	public Hora(String cadena) {
		String[] cadenaLimpia = cadena.split(":");
		
		if (cadenaLimpia.length == 3) {
			setHora(Integer.parseInt(cadenaLimpia[0]), Integer.parseInt(cadenaLimpia[1]), Integer.parseInt(cadenaLimpia[2]));
		}else {
			setHora(0, 0, 0);
		}
	}
	
	public void setHora(int hora, int minutos, int segundos) {
		if (this.horas >= 0 && this.horas < 24 && this.minutos >= 0 && this.minutos < 60 && this.segundos >= 0 && this.segundos < 60) {	
            this.horas = hora;
            this.minutos = minutos;
            this.segundos = segundos;
		}
		
		if (this.horas >= 0 && this.horas <=12) {
			this.diaNoc = "am";
		}else if (this.horas <= 24 && this.horas >=12) {
			this.diaNoc = "pm";
		}
		
	}
	
	public int getHoras() {
		return this.horas;
	}
	
	public int getMinutos() {
		return this.minutos;
	}
	
	public int getSegundos() {
		return this.segundos;
	}
	
	public String toString() {
		return this.horas + ":" + this.minutos + ":" + this.segundos + " " + this.diaNoc;
	}
}
