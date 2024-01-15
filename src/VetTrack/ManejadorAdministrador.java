package VetTrack;

import java.awt.GridLayout;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

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

	public void imprimirContenidoCeldasYChoices(List<JTextField> celdas, List<String> idsParaVentas, List<JComboBox<String>> choices, List<String> tiposParaVentas) {
		StringBuilder contenido = new StringBuilder();

		for (int i = 0; i < celdas.size(); i++) {

			idsParaVentas.add(celdas.get(i).getText());
			tiposParaVentas.add(String.valueOf(choices.get(i).getSelectedItem()));

			contenido.append("Celda ").append(i + 1).append(": ");
			contenido.append(celdas.get(i).getText());
			contenido.append(", Tipo: ").append(choices.get(i).getSelectedItem());
			contenido.append("\n");
		}

	}

	public void crearServicio(JTextField textFieldNombreServicio, JTextField textFieldPrecioServicio, JTextArea textAreaDescripcionServicio) {
		try {
			int maximo = buscarMaximo("Servicio");

			Map<String, Object> valores = new HashMap<>();

			valores.put("idServicio", maximo+1);
			valores.put("nombre", textFieldNombreServicio.getText().intern());
			valores.put("precio", Float.parseFloat(textFieldPrecioServicio.getText().intern()));
			valores.put("descripcionServicio", textAreaDescripcionServicio.getText().intern());


			conexion.agregarFilaATabla("Servicio", valores);

			JOptionPane.showMessageDialog(null, "El servicio se ha añadido correctamente");

			//Para borrar parametros
			textFieldNombreServicio.setText("");
			textFieldPrecioServicio.setText("");
			textAreaDescripcionServicio.setText("");

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void addMascotaCliente(int idUsuario) {

		JPanel panel = new JPanel(new GridLayout(0, 2));

		JTextField campoNombre = new JTextField();
		JTextField campoEspecie = new JTextField();
		JTextField campoRaza = new JTextField();
		JTextField campofecha = new JTextField();

		panel.add(new JLabel("Nombre:"));
		panel.add(campoNombre);
		panel.add(new JLabel("Especie:"));
		panel.add(campoEspecie);
		panel.add(new JLabel("Raza:"));
		panel.add(campoRaza);
		panel.add(new JLabel("FechaNacimiento:"));
		panel.add(campofecha);



		int resultado = JOptionPane.showConfirmDialog(null, panel, "Ingrese Informacion", JOptionPane.OK_CANCEL_OPTION);

		if (resultado == JOptionPane.OK_OPTION) {

			if (campoEspecie.getText().intern()!="" && campofecha.getText().intern() != "" && campoRaza.getText().intern() != "" && campoNombre.getText().intern() != "") {
				String fechaCita = campofecha.getText();

				String[] partesFecha = fechaCita.split("/");

				String anio = partesFecha[2];
				String mes = partesFecha[1];
				String dia = partesFecha[0];

				String fechaFinal = anio + "-" + mes + "-" + dia;

				try {
					int maximo = buscarMaximo("Mascota");


					Map<String, Object> valores = new HashMap<>();

					valores.put("idMascota", maximo);
					valores.put("nombre", campoNombre.getText().intern());
					valores.put("especie", campoEspecie.getText().intern());
					valores.put("raza", campoRaza.getText().intern());
					valores.put("fechaNacimiento", java.sql.Date.valueOf(fechaFinal));
					valores.put("idUsuario", idUsuario);

					conexion.agregarFilaATabla("Mascota", valores);

					List<Map<String, Object>> filaCliente = conexion.obtenerFilasDeTabla("Cliente", Arrays.asList("numMascotas"), "idUsuario = ?", idUsuario);

					int nuevoNumMascotas = (int) filaCliente.get(0).get("numMascotas") + 1;

					conexion.actualizarFila("Cliente", "numMascotas", nuevoNumMascotas, "idUsuario", idUsuario);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				JOptionPane.showMessageDialog(null, "Hay campos vacios");
			}


		}
	}

	public List<Object> cogerDatosBorrar(String username) throws Exception {

		if (username.equals("")) {
			return null;
		}

		List<List<Object>> userList = conexion.listar("Usuario");

		for (List<Object> user : userList) {
			if (user.get(1).toString().equals(username)) {
				return user;
			}
		}

		return null;

	}

	public void buscarUsuario(JTextField textUserBuscar, JTextPane panelTextUser, JTextPane textPaneCitasPrevias, JTextPane textPaneCitasFuturas) {
		try {

			List<Object> cadena = cogerDatosBorrar(textUserBuscar.getText().intern());

			if (cadena == null) {
				textUserBuscar.setText("");
				panelTextUser.setText("");
				textPaneCitasPrevias.setText("");
				textPaneCitasFuturas.setText("");
				JOptionPane.showMessageDialog(null, "No se ha encontrado ningun usuario con ese nombre");
			} else {
				String datosUser = " ID del Cliente: "+ cadena.get(0).toString() + 
						"\n Username: "+cadena.get(1).toString()+
						"\n Rol: "+cadena.get(3).toString();
				String mascClient = "";

				if (cadena.get(3).toString().intern() != "Administrador") {
					mascClient = "\n Mascotas que tiene el cliente:\n" + 
							"______________________________________________________\n";

					List<String> mascotas = recTodasMascotas((int) cadena.get(0));

					for (String masc : mascotas) {
						mascClient += mascRecDatos(masc, (int) cadena.get(0));
					}

				}

				panelTextUser.setText(datosUser + mascClient);

				textPaneCitasPrevias.setText(recCitasUsuario(Integer.parseInt(cadena.get(0).toString()), "<"));
				textPaneCitasFuturas.setText(recCitasUsuario(Integer.parseInt(cadena.get(0).toString()), ">"));
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void agregarMascota(JTextField textUserBuscar, JTextPane panelTextUser) {
		try {



			List<Object> cadena = cogerDatosBorrar(textUserBuscar.getText().intern());

			if (cadena == null) {
				textUserBuscar.setText("");
				panelTextUser.setText("");
				JOptionPane.showMessageDialog(null, "No se ha encontrado ningun usuario con ese nombre");
			} else {

				if (cadena.get(3).toString().intern() == "Cliente") {

					addMascotaCliente(Integer.parseInt(cadena.get(0).toString().intern()));

				} else {
					JOptionPane.showMessageDialog(null, "Estas seleccionando un Administrador");
				}

			}



		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	//este
	//Puede haber solamente 2, > y <
	//	">" ---> es para mayor
	//	"<" ---> es para menor
	public String recCitasUsuario(int id, String caracter) throws Exception{

		List<Map<String, Object>> citas = this.conexion.obtenerFilasDeTabla( "Cita", Arrays.asList("idCita", "fechaCita", "horaCita", "descripcionCita", "idMascota"), "idUsuario = ? AND fechaCita "+caracter+" ?", id, Date.valueOf(LocalDate.now()));

		StringBuffer stringBuffer = new StringBuffer("");
		int i = 1;

		for(Map<String, Object> unaCita : citas) {

			String nombreMascota = this.conexion.obtenerDatoDeTabla("Mascota", "nombre", "idMascota", unaCita.get("idMascota"));

			stringBuffer.append(" Cita #"+i);

			stringBuffer.append("\n IdCita: " + unaCita.get("idCita"));
			stringBuffer.append("\n Fecha de la cita: " + new SimpleDateFormat("dd / MM / yyyy").format(unaCita.get("fechaCita")));
			stringBuffer.append("\n Hora de la cita: " + new SimpleDateFormat("HH:mm").format(unaCita.get("horaCita")));
			stringBuffer.append("\n Nombre de la mascota: " + nombreMascota );
			stringBuffer.append("\n Descripcion:\n " + unaCita.get("descripcionCita"));
			stringBuffer.append("\n===============================================\n");

			i++;

		}

		return stringBuffer.toString();

	}

	//este
	public ArrayList<String> recTodasMascotas(int id) throws NumberFormatException, Exception {

		int numMascotas = Integer.parseInt(this.conexion.obtenerDatoDeTabla("Cliente", "numMascotas", "idUsuario", id));

		List<String> lista = this.conexion.obtenerDatosDeTablaLista("Mascota", "nombre", "idUsuario", id);

		ArrayList<String> mascotasLista = new ArrayList<String>();

		for(int i = 0; i < numMascotas; i++) {

			mascotasLista.add(lista.get(i).toString().intern());

		}

		return mascotasLista;

	}

	//este
	public String mascRecDatos(String nombreMascota, int idUsuario) throws Exception {

		List<String> columnas = Arrays.asList("idMascota", "especie", "raza", "fechaNacimiento");
		String condicion = "nombre = ? AND idUsuario = ?";

		Object[] parametros = {nombreMascota, idUsuario};

		List<Map<String, Object>> resultados = this.conexion.obtenerFilasDeTabla("Mascota", columnas, condicion, parametros);

		if (!resultados.isEmpty()) {
			Map<String, Object> datosMascota = resultados.get(0);

			return    " ID de la mascota: " + String.valueOf(datosMascota.get("idMascota")) + 
					"\n Nombre: "+ nombreMascota +
					"\n Especie: " + String.valueOf(datosMascota.get("especie")) +
					"\n Raza: " +  String.valueOf(datosMascota.get("raza")) + 
					"\n Fecha de nacimiento: " + String.valueOf(new SimpleDateFormat("dd / MM / yyyy").format(datosMascota.get("fechaNacimiento"))) + 
					"\n=============================================\n";

		}

		return "";
	}

	public int idMascotaRecuperar(String nombreMascota, int idUsuario) throws Exception{

		List<String> columnas = Arrays.asList("idMascota", "especie", "raza", "fechaNacimiento");
		String condicion = "nombre = ? AND idUsuario = ?";

		Object[] parametros = {nombreMascota, idUsuario};

		List<Map<String, Object>> resultados = this.conexion.obtenerFilasDeTabla("Mascota", columnas, condicion, parametros);

		if (!resultados.isEmpty()) {
			Map<String, Object> datosMascota = resultados.get(0);

			return (int) datosMascota.get("idMascota");
		}

		return -1;
	}

	public void crearArticulo(JTextField textNombreArticulo, JTextField textPrecioArticulo, JTextField textMarcaArticulo, JTextArea textAreaArticulo) {


		try {
			int maximo = buscarMaximo("Articulo");

			Map<String, Object> valores = new HashMap<>();

			valores.put("idArticulo", maximo);
			valores.put("nombre", textNombreArticulo.getText().intern());
			valores.put("marca", textMarcaArticulo.getText().intern());
			valores.put("precio", Float.parseFloat(textPrecioArticulo.getText().intern()));
			valores.put("descripcionArticulo", textAreaArticulo.getText().intern());

			conexion.agregarFilaATabla("Articulo", valores);

			JOptionPane.showMessageDialog(null, "El articulo se ha añadido correctamente");

			//Para borrar parametros
			textNombreArticulo.setText("");
			textMarcaArticulo.setText("");
			textPrecioArticulo.setText("");
			textAreaArticulo.setText("");

		} catch (Exception e1) {
			e1.printStackTrace();
		}


	}

}
