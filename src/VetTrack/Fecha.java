package VetTrack;

/**
 * Clase que representa una Fecha en el sistema VetTrack.
 */
public class Fecha {

	private int dia;  // Día de la fecha.
	private int mes;  // Mes de la fecha.
	private int ano;  // Año de la fecha.

	//=========================================================================

	// Constructor que inicializa los campos de la clase.
	public Fecha(int dia, int mes, int ano) {

		this.ano = ano;
		this.mes = mes;
		this.dia = dia;

	}

	public int getDia() { return dia; }
	public void setDia(int dia) throws Exception {
		if (dia >0 && dia <= obtenerDiasEnMes(getMes())) {
			this.dia = dia;
		} else {
			throw new Exception("El numero del dia no es correcto");	
		}
	}

	public int getMes() { return mes; }
	public void setMes(int mes) throws Exception {
		if (mes >0 && mes <=12) {
			this.mes = mes;
		} else {
			throw new Exception("El numero del mes no es correcto");	
		}	
	}

	public int getAno() { return ano; }
	public void setAno(int ano) throws Exception {
		
		if (ano >=0) {
			this.ano = ano;
		} else {
			throw new Exception("El numero del mes no es correcto");	
		}
	}


	// Método para obtener el número de días en un mes.
	public static int obtenerDiasEnMes(int mes) {
		switch (mes) {
		case 1:  // Enero
		case 3:  // Marzo
		case 5:  // Mayo
		case 7:  // Julio
		case 8:  // Agosto
		case 10: // Octubre
		case 12: // Diciembre
			return 31;
		case 4:  // Abril
		case 6:  // Junio
		case 9:  // Septiembre
		case 11: // Noviembre
			return 30;
		case 2:  // Febrero
			return 28;  // LATER: A LO MEJOR DEBERIAMOS CONSIDERAR AÑOS BISIESTOS
		default:
			throw new IllegalArgumentException("El mes debe estar en el rango de 1 a 12.");
		}
	}

	public boolean esFechaPosterior(Fecha otraFecha) {
	    if (this.getAno() > otraFecha.getAno()) {
	        return true;
	    } else if (this.getAno() == otraFecha.getAno()) {
	        if (this.getMes() > otraFecha.getMes()) {
	            return true;
	        } else if (this.getMes() == otraFecha.getMes()) {
	            return this.getDia() > otraFecha.getDia();
	        }
	    }
	    return false;
	}
	
	public boolean esFechaAnterior(Fecha otraFecha) {
	    if (this.getAno() < otraFecha.getAno()) {
	        return true;
	    } else if (this.getAno() == otraFecha.getAno()) {
	        if (this.getMes() < otraFecha.getMes()) {
	            return true;
	        } else if (this.getMes() == otraFecha.getMes()) {
	            return this.getDia() < otraFecha.getDia();
	        }
	    }
	    return false;
	}
	
	public boolean esFechaIgual(Fecha otraFecha) {
	    return this.getAno() == otraFecha.getAno() && this.getMes() == otraFecha.getMes() && this.getDia() == otraFecha.getDia();
	}

}
