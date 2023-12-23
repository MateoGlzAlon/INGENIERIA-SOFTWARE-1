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

    // Getter y Setter para el día
    public int getDia() {
        return dia;
    }

    public void setDia(int dia) throws Exception {
        // Verifica si el número del día es válido para el mes actual
        if (dia > 0 && dia <= obtenerDiasEnMes(getMes())) {
            this.dia = dia;
        } else {
            throw new Exception("El número del día no es correcto");
        }
    }

    // Getter y Setter para el mes
    public int getMes() {
        return mes;
    }

    public void setMes(int mes) throws Exception {
        // Verifica si el número del mes es válido (entre 1 y 12)
        if (mes > 0 && mes <= 12) {
            this.mes = mes;
        } else {
            throw new Exception("El número del mes no es correcto");
        }
    }

    // Getter y Setter para el año
    public int getAno() {
        return ano;
    }

    public void setAno(int ano) throws Exception {
        // Verifica si el número del año es válido (mayor o igual a 0)
        if (ano >= 0) {
            this.ano = ano;
        } else {
            throw new Exception("El número del año no es correcto");
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

    /**
     * Comprueba si la fecha actual es posterior a otra fecha dada.
     * @param otraFecha La fecha con la que se compara.
     * @return true si la fecha actual es posterior a otraFecha, false en caso contrario.
     */
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

    /**
     * Comprueba si la fecha actual es anterior a otra fecha dada.
     * @param otraFecha La fecha con la que se compara.
     * @return true si la fecha actual es anterior a otraFecha, false en caso contrario.
     */
    public boolean esFechaAnterior(Fecha otraFecha) {
        if (this.getAno() < otraFecha.getAno()) {
            return true;
        } else if (this.getAno() == otraFecha.getAno()) {
            if (this.getMes() < otraFecha.get
