package VetTrack;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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

		return maximo + 1;

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

			valores.put("idServicio", maximo);
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
							"_____________________________________________\n";

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


		System.out.println("==1 " + textAreaArticulo.getText());
		System.out.println("2 " + textPrecioArticulo.getText());
		System.out.println("3 " + textNombreArticulo.getText());
		System.out.println("4 " + textMarcaArticulo.getText());
		System.out.println("5 " + textPrecioArticulo.getText());
		
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
	
	
	public void verPerfil(Interfaz interfaz) throws DBException {
		
		JPanel panel = new JPanel(new BorderLayout());

		JPanel panelInfo = new JPanel(new GridLayout(0, 2));

		JPanel panelImagen = new JPanel();

		ImageIcon icon = new ImageIcon("etc/IMAGENES/pfp.png");
		Image image = icon.getImage();
		Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel labelImagen = new JLabel(scaledIcon);
		panelImagen.add(labelImagen);

		JButton btnVerPass = new JButton("Ver");

		btnVerPass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					JOptionPane.showMessageDialog(null, conexion.obtenerDatoDeTabla("Usuario", "contraseña", "nombreUsuario", interfaz.getUser().getNombreUsuario()), "Ver Contraseña", JOptionPane.INFORMATION_MESSAGE);
				} catch (HeadlessException | DBException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});

		JLabel labelUsername = new JLabel("Username: " +interfaz.getUser().getNombreUsuario());
		JLabel labelPasswd = new JLabel("Password: " + getAsteriscos(conexion.obtenerDatoDeTabla("Usuario", "contraseña", "nombreUsuario", interfaz.getUser().getNombreUsuario())));
		JLabel labelRol = new JLabel("Rol: Administrador");

		panelInfo.add(labelUsername);
		panelInfo.add(new JLabel());
		panelInfo.add(labelPasswd);
		panelInfo.add(btnVerPass);
		panelInfo.add(labelRol);

		panel.add(panelImagen, BorderLayout.WEST);
		panel.add(panelInfo, BorderLayout.CENTER);

		JOptionPane.showMessageDialog(null, panel, "Ver Perfil", JOptionPane.INFORMATION_MESSAGE);

	}
	
	
	public void createUser(JFrame frame) {

		JPanel panel = new JPanel(new GridLayout(0, 2));

		JTextField txtUsuario;
		JPasswordField txtPassword;
		JComboBox<String> cboTipoUsuario;
		JTextField txtNombreCompleto, txtTelefono, txtDNI;

		txtUsuario = new JTextField();
		txtPassword = new JPasswordField();
		cboTipoUsuario = new JComboBox<>(new String[]{
				"",
				"Administrador", 
				"Cliente"
		});

		txtNombreCompleto = new JTextField();
		txtTelefono = new JTextField();
		txtDNI = new JTextField();

		cboTipoUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cboTipoUsuario.getSelectedItem().toString().intern() == "Cliente") {

					txtNombreCompleto.setVisible(true);
					txtTelefono.setVisible(true);
					txtDNI.setVisible(true);
				} else {
					txtNombreCompleto.setVisible(false);
					txtTelefono.setVisible(false);
					txtDNI.setVisible(false);
				}
			}
		});

		panel.add(new JLabel("Usuario:"));
		panel.add(txtUsuario);

		panel.add(new JLabel("Contraseña:"));
		panel.add(txtPassword);

		panel.add(new JLabel("Rol:"));
		panel.add(cboTipoUsuario);

		panel.add(new JLabel("NombreCompleto:"));
		panel.add(txtNombreCompleto);

		panel.add(new JLabel("Telefono:"));
		panel.add(txtTelefono);

		panel.add(new JLabel("DNI:"));
		panel.add(txtDNI);

		int resultado = JOptionPane.showConfirmDialog(frame, panel, "Agregar Usuario", JOptionPane.OK_CANCEL_OPTION);

		if (resultado == JOptionPane.OK_OPTION) {
			try {

				if (txtUsuario.getText().intern()!= "" && txtPassword.getPassword().toString().intern() != "" && cboTipoUsuario.getSelectedItem().toString().intern()!= "") {

					if(cboTipoUsuario.getSelectedItem().toString().intern()== "Cliente" && txtNombreCompleto.getText().intern()!= "" 
							&& txtTelefono.getText().intern() != "" && txtDNI.getText().intern() != "") {

						if (txtDNI.getText().intern().length() != 9 || txtTelefono.getText().intern().length() != 9) {
							JOptionPane.showMessageDialog(null, "ERROR: El telefono o DNI deben de tener de maximo 9 caracteres");
						} else {

							int maximo = buscarMaximo("Usuario");

							Map<String, Object> valores = new HashMap<>();

							valores.put("idUsuario", maximo);
							valores.put("nombreUsuario", txtUsuario.getText().intern());
							valores.put("contraseña", new String(txtPassword.getPassword()).intern());
							valores.put("rol", cboTipoUsuario.getSelectedItem().toString().intern());
							conexion.agregarFilaATabla("Usuario", valores);

							Map<String, Object> valoresCliente = new HashMap<>();

							valoresCliente.put("idUsuario", maximo);
							valoresCliente.put("dni", txtDNI.getText().intern());
							valoresCliente.put("telefono", txtTelefono.getText().intern());

							String cadenasep[] = txtNombreCompleto.getText().intern().split(" ");

							valoresCliente.put("nombre", cadenasep[0]);
							valoresCliente.put("apellidos", String.join(" ", Arrays.copyOfRange(cadenasep, 1, cadenasep.length)));
							valoresCliente.put("numMascotas", 0);

							conexion.agregarFilaATabla("Cliente", valoresCliente);

							JOptionPane.showMessageDialog(null, "Se ha añadido al cliente correctamente");

						}

					} else if (cboTipoUsuario.getSelectedItem().toString().intern()== "Administrador") {

						int maximo = buscarMaximo("Usuario");

						Map<String, Object> valores = new HashMap<>();

						valores.put("idUsuario", maximo);
						valores.put("nombreUsuario", txtUsuario.getText().intern());
						valores.put("contraseña", new String(txtPassword.getPassword()).intern());
						valores.put("rol", cboTipoUsuario.getSelectedItem().toString().intern());
						conexion.agregarFilaATabla("Usuario", valores);

						Map<String, Object> valoresAdministrador = new HashMap<>();

						valoresAdministrador.put("idUsuario", maximo);
						valoresAdministrador.put("cadenaInicioSesion", new String(Base64.getEncoder().encode(txtUsuario.getText().intern().getBytes())));

						conexion.agregarFilaATabla("Administrador", valoresAdministrador);

						JOptionPane.showMessageDialog(null, "Se ha añadido al administrador correctamente");

					}else {
						JOptionPane.showMessageDialog(null, "Revisa los campos, no se puede añadir al usuario");
					}

				}else {
					JOptionPane.showMessageDialog(null, "No puede haber campos vacios");
				}



			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}
	
	
	
	public void deleteUser() {

		JPanel panel = new JPanel(new GridLayout(0, 2));

		JTextField txtNombre = new JTextField();

		panel.add(new JLabel("Usuario:"));
		panel.add(txtNombre);

		int resultado = JOptionPane.showConfirmDialog(null, panel, "Borrar Usuario", JOptionPane.OK_CANCEL_OPTION);

		if (resultado == JOptionPane.OK_OPTION) {

			try {

				List<Object> cadena = cogerDatosBorrar(txtNombre.getText().toString().intern());

				if (cadena == null) {
					JOptionPane.showMessageDialog(null, "No se ha encontrado ningun usuario con ese nombre");
				}else {
					int confirmacion = JOptionPane.showConfirmDialog(null, "ID: "+ cadena.get(0).toString() + "\nUsername: "+cadena.get(1).toString()+"\nRol: "+cadena.get(3).toString(), "Confirmar",
							JOptionPane.YES_NO_OPTION);

					if (confirmacion == JOptionPane.YES_OPTION) {

						if (cadena.get(3).toString().intern()=="Cliente") {
							//comprobar si tiene mascotas
							if (Integer.parseInt(conexion.obtenerDatoDeTabla("Cliente", "numMascotas", "idUsuario", Integer.parseInt(cadena.get(0).toString()))) != 0) {
								conexion.eliminarFilaDeTabla("Mascota", "idUsuario", Integer.parseInt(cadena.get(0).toString()));
							}

						}

						conexion.eliminarFilaDeTabla(cadena.get(3).toString().intern(), "idUsuario", Integer.parseInt(cadena.get(0).toString()));
						conexion.eliminarFilaDeTabla("Usuario", "idUsuario", Integer.parseInt(cadena.get(0).toString()));
						JOptionPane.showMessageDialog(null, "El usuario "+cadena.get(1).toString()+" se ha eliminado correctamente");
					}
				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}

	}
	
	
	
	public void crearCita(JTextField textUserCita, JTextField textMascCita, JTextField textFechaCita, JTextField textHoraCita, JTextPane panelDescrCita) {
		String fechaFinal = "";

		if (!textFechaCita.getText().equals("")) {
			String fechaCita = textFechaCita.getText();

			String[] partesFecha = fechaCita.split("/");

			// Obtener día, mes y año
			String anio = partesFecha[2];
			String mes = partesFecha[1];
			String dia = partesFecha[0];

			fechaFinal = anio + "-" + mes + "-" + dia;

		}

		if (!textUserCita.getText().isEmpty() && !textMascCita.getText().isEmpty() && !textFechaCita.getText().isEmpty()
				&& !textHoraCita.getText().isEmpty() && !panelDescrCita.getText().isEmpty()) {

			try {
				List<Object> user = cogerDatosBorrar(textUserCita.getText().intern());

				int idMascota = -1;

				if (user != null) {

					boolean compr = false;

					ArrayList<String> listaMasc = recTodasMascotas(Integer.parseInt(user.get(0).toString()));

					for (String mascota : listaMasc) {

						if (textMascCita.getText().intern().equals(mascota.intern())) {

							compr = true;

							idMascota = idMascotaRecuperar(mascota, Integer.parseInt(user.get(0).toString()));

						}

					}

					if (compr) {

						int maximo = buscarMaximo("Cita");

						Map<String, Object> valores = new HashMap<>();

						valores.put("idCita", maximo);
						valores.put("idUsuario", Integer.parseInt(user.get(0).toString()));
						valores.put("fechaCita", java.sql.Date.valueOf(fechaFinal));

						String horaCita = textHoraCita.getText() + ":00";

						valores.put("horaCita", java.sql.Time.valueOf(horaCita));
						valores.put("idMascota", idMascota);
						valores.put("descripcionCita", panelDescrCita.getText());

						conexion.agregarFilaATabla("Cita", valores);

						JOptionPane.showMessageDialog(null, "La cita se ha añadido correctamente");

					} else {
						JOptionPane.showMessageDialog(null, "La mascota no existe");
					}

				} else {
					JOptionPane.showMessageDialog(null, "El usuario no existe");
				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} else {
			JOptionPane.showMessageDialog(null, "No puede haber un campo vacío o la fecha es incorrecta");
		}
	}
	
	
	public void modificarContraseña(JFrame frame) throws DBException, SQLException {

	    JPanel panel = new JPanel(new GridLayout(0, 2));

	    JTextField txtNombreUsuario = new JTextField();
	    JPasswordField txtOldPassword = new JPasswordField();
	    JPasswordField txtNewPassword = new JPasswordField();
	    JPasswordField txtRepetirNewPassword = new JPasswordField();

	    panel.add(new JLabel("Usuario:"));
	    panel.add(txtNombreUsuario);

	    panel.add(new JLabel("Contraseña antigua:"));
	    panel.add(txtOldPassword);

	    panel.add(new JLabel("Nueva contraseña:"));
	    panel.add(txtNewPassword);

	    panel.add(new JLabel("Repetir nueva contraseña:"));
	    panel.add(txtRepetirNewPassword);

	    int resultado = JOptionPane.showConfirmDialog(frame, panel, "Modificar Contraseña", JOptionPane.OK_CANCEL_OPTION);

	    if (resultado == JOptionPane.OK_OPTION) {

	        String nombreUsuario = txtNombreUsuario.getText();

	        if (conexion.existeEnLaTabla("Usuario", "nombreUsuario", nombreUsuario)) {

	            String obtenerDatoDeTabla = conexion.obtenerDatoDeTabla("Usuario", "contraseña", "nombreUsuario", nombreUsuario);

	            if (Arrays.equals(obtenerDatoDeTabla.toCharArray(), txtOldPassword.getPassword())) {

	                String nuevaContraseña = new String(txtNewPassword.getPassword());
	                String repetirNuevaContraseña = new String(txtRepetirNewPassword.getPassword());

	                if (nuevaContraseña.equals(repetirNuevaContraseña)) {

	                    conexion.actualizarDatoEnTabla("Usuario", "contraseña", nuevaContraseña, "nombreUsuario", nombreUsuario);
	                    JOptionPane.showMessageDialog(null, "La contraseña ha sido actualizada");

	                } else {
	                    JOptionPane.showMessageDialog(null, "Las contraseñas nuevas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
	                }

	            } else {
	                JOptionPane.showMessageDialog(null, "Contraseña antigua incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
	            }

	        } else {
	            JOptionPane.showMessageDialog(null, "El usuario que intentas modificar no existe", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}
	
}
