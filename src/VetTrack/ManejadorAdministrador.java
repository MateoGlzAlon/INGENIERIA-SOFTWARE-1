package VetTrack;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextField;

import Exceptions.DBException;

public class ManejadorAdministrador {
	
	private ConexionBD conexion = ConexionBD.getInstance();
	
	public String getAsteriscos(String passwd) {

		int ast = passwd.length();

		StringBuffer cadena = new StringBuffer("");

		for(int i = 0; i<ast; i++) {
			cadena.append("*");
		}

		return cadena.toString();

	}
	
	public int buscarMaximo(String tabla) throws DBException {

		List<List<Object>> mascotaTotal = conexion.listar(tabla);
		int maximo = 0;

		for (List<Object> user : mascotaTotal) {
			if ((int) user.get(0) > maximo) {
				maximo = (int) user.get(0);
			}
		}

		return maximo;

	}
	
	public void procesarVentas(List<String> idsParaVentas, List<String> tiposParaVentas ,String descripcionAux, JTextField textUserBuscar) throws DBException {

		for (int i = 0; i < idsParaVentas.size();i++) {

			LocalDate fechaActual = LocalDate.now();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String fechaFormateada = fechaActual.format(formatter);

			int maximo = buscarMaximo("Venta");

			Map<String, Object> valores = new HashMap<>();

			valores.put("idVenta", maximo);
			valores.put("idMismaVenta", 1);
			valores.put("idUsuario", this.conexion.obtenerDatoDeTabla("Usuario", "idUsuario", "nombreUsuario", textUserBuscar.getText()));
			valores.put("tipo", tiposParaVentas.get(i));
			valores.put("idArtServ", idsParaVentas.get(i));
			valores.put("descripcionVenta", descripcionAux);
			valores.put("fechaVenta",  java.sql.Date.valueOf(fechaFormateada));


			this.conexion.agregarFilaATabla("Venta", valores);
		}

	}
	
}
