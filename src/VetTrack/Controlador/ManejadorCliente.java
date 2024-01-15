package VetTrack.Controlador;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import Exceptions.DBException;
import VetTrack.Modelo.ConexionBD;
import VetTrack.Vista.Interfaz;



public class ManejadorCliente {
	
	private ConexionBD conexion = ConexionBD.getInstance();
	

	public void establecerMascotas(Interfaz interfaz, Choice choiceMascotas) throws NumberFormatException, Exception {

		int numMascotas = Integer.parseInt(conexion.obtenerDatoDeTabla("Cliente", "numMascotas", "idUsuario", interfaz.getUser().getIdUsuario()));
		int idUsuario = interfaz.getUser().getIdUsuario();

		List<String> lista = conexion.obtenerDatosDeTablaLista("Mascota", "nombre", "idUsuario", idUsuario);

		for(int i = 0; i < numMascotas; i++) {

			choiceMascotas.add(lista.get(i));

		}

	}
	
	
	public void mostrarDatosMascota(Interfaz interfaz, Choice choiceMascotas, JTextField textFieldIdMascota, JTextField textFieldNombreMascota, JTextField textFieldEspecieMascota
			, JTextField textFieldRazaMascota, JTextField textFieldFechaNacimientoMascota, JTextField textFieldIdUsuarioMascota ) throws Exception {
		String nombreMascota = choiceMascotas.getSelectedItem();
		int idUsuario = interfaz.getUser().getIdUsuario();

		// Obtener todos los datos de la mascota en una sola fila
		List<String> columnas = Arrays.asList("idMascota", "especie", "raza", "fechaNacimiento");
		String condicion = "nombre = ? AND idUsuario = ?";

		Object[] parametros = {nombreMascota, idUsuario};

		List<Map<String, Object>> resultados = conexion.obtenerFilasDeTabla("Mascota", columnas, condicion, parametros);

		if (!resultados.isEmpty()) {
			Map<String, Object> datosMascota = resultados.get(0);

			// Obtener el dueño de la mascota
			String dueno = conexion.obtenerDatoDeTabla("Cliente", "nombre", "idUsuario", idUsuario);

			// Establecer los datos en los campos correspondientes
			textFieldIdMascota.setText(String.valueOf(datosMascota.get("idMascota")));
			textFieldNombreMascota.setText(nombreMascota);
			textFieldEspecieMascota.setText(String.valueOf(datosMascota.get("especie")));
			textFieldRazaMascota.setText(String.valueOf(datosMascota.get("raza")));
			textFieldFechaNacimientoMascota.setText(new SimpleDateFormat("dd / MM / yyyy").format(datosMascota.get("fechaNacimiento")));
			textFieldIdUsuarioMascota.setText(dueno);
		}
	}
	
	
	
	
	public void mostrarPerfil(Interfaz interfaz) throws Exception {
		// Obtener el usuario activo una sola vez
		Usuario usuarioActivo = interfaz.getUser();

		// Obtener todos los datos necesarios en una sola consulta
		List<Map<String, Object>> resultados = conexion.obtenerFilasDeTabla("Cliente", Arrays.asList("nombre", "apellidos", "dni", "telefono"), "idUsuario = ?", usuarioActivo.getIdUsuario());

		Map<String, Object> datosCliente = resultados.isEmpty() ? Collections.emptyMap() : resultados.get(0);

		String nombre = String.valueOf(datosCliente.get("nombre"));
		String apellidos = String.valueOf(datosCliente.get("apellidos"));
		String dni = String.valueOf(datosCliente.get("dni"));
		String telefono = String.valueOf(datosCliente.get("telefono"));
		
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel panelInfo = new JPanel(new GridLayout(0, 2));
		
		JPanel panelImagen = new JPanel();
		
		ImageIcon icon = new ImageIcon("etc/IMAGENES/pfp.png");
		Image image = icon.getImage();
		Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		
		JLabel labelImagen = new JLabel(scaledIcon);
		panelImagen.add(labelImagen);
		
		JLabel labelUsername = new JLabel("Username: " + interfaz.getUser().getNombreUsuario());
		JLabel labelPasswd = new JLabel("Password: " + getAsteriscos(conexion.obtenerDatoDeTabla("Usuario", "contraseña", "nombreUsuario", interfaz.getUser().getNombreUsuario())));
		JLabel labelRol = new JLabel("Rol: Administrador");

		JLabel labelNombre = new JLabel("Nombre: " + nombre);
		JLabel labelApellidos = new JLabel("Apellidos: " + apellidos);
		JLabel labelDNI = new JLabel("DNI: " + dni);
		JLabel labelTel = new JLabel("Telefono: " + telefono);
		
		JButton btnVerPass = new JButton("Ver");
		
		btnVerPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	try {
					JOptionPane.showMessageDialog(null, conexion.obtenerDatoDeTabla("Usuario", "contraseña", "nombreUsuario", interfaz.getUser().getNombreUsuario()), "Ver Contraseña", JOptionPane.INFORMATION_MESSAGE);
				} catch (HeadlessException | DBException e1) {
					e1.printStackTrace();
				}            }
        });
		
		panelInfo.add(labelUsername);
		panelInfo.add(new JLabel());
        panelInfo.add(labelPasswd);
        panelInfo.add(btnVerPass);
        panelInfo.add(labelRol);
        panelInfo.add(new JLabel());
        panelInfo.add(labelNombre);
        panelInfo.add(new JLabel());
        panelInfo.add(labelApellidos);
        panelInfo.add(new JLabel());
        panelInfo.add(labelDNI);
        panelInfo.add(new JLabel());
		panelInfo.add(labelTel);
		panelInfo.add(new JLabel());
        
        panel.add(panelImagen, BorderLayout.WEST);
        panel.add(panelInfo, BorderLayout.CENTER);
		
		JOptionPane.showMessageDialog(null, panel, "Ver Perfil", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	
	private String getAsteriscos(String passwd) {
		
		int ast = passwd.length();
		
		StringBuffer cadena = new StringBuffer("");
		
		for(int i = 0; i<ast; i++) {
			cadena.append("*");
		}
		
		return cadena.toString();
		
	
	}
	
	
	
	public void rellenarCitasPasadas(Interfaz interfaz, Choice choiceMascotas, JTextPane textPaneCitasPrevias) throws Exception {
		String nombreMascota = choiceMascotas.getSelectedItem();
		int idUsuario = interfaz.getUser().getIdUsuario();
		String idMascota = conexion.obtenerDatoDeTablaConDosCondiciones("Mascota", "idMascota", "nombre", nombreMascota, "idUsuario", idUsuario);

		// Obtener todas las citas pasadas en una sola consulta
		List<Map<String, Object>> filasCitasPasadas = conexion.obtenerFilasDeTabla("Cita", Arrays.asList("idCita", "fechaCita", "horaCita", "descripcionCita"), "idMascota = ? AND fechaCita < ?", idMascota, new Date());

		// Ordenar las citas por fecha en orden descendente (de la más reciente a la más antigua)
		filasCitasPasadas.sort(Comparator.comparing((Map<String, Object> fila) -> (Date) fila.get("fechaCita")).reversed());

		StringBuilder cadenaCitasPrevias = new StringBuilder();

		for (Map<String, Object> fila : filasCitasPasadas) {
			cadenaCitasPrevias.append(" IdCita: ").append(fila.get("idCita"))
			.append("\n FechaCita: ").append(new SimpleDateFormat("dd / MM / yyyy").format(fila.get("fechaCita")))
			.append("\n HoraCita: ").append(new SimpleDateFormat("HH:mm").format(fila.get("horaCita")))
			.append("\n Descripcion: ").append(fila.get("descripcionCita"));
			cadenaCitasPrevias.append("\n=================================================");
		}

		// Establecer el texto en el JTextPane
		textPaneCitasPrevias.setText(cadenaCitasPrevias.toString());
	}
	
	
	public void rellenarCitasFuturas(Interfaz interfaz, Choice choiceMascotas, JTextPane textPaneCitasFuturas) throws Exception {
		String nombreMascota = choiceMascotas.getSelectedItem();
		int idUsuario = interfaz.getUser().getIdUsuario();
		String idMascota = conexion.obtenerDatoDeTablaConDosCondiciones("Mascota", "idMascota", "nombre", nombreMascota, "idUsuario", idUsuario);

		// Obtener todas las citas futuras en una sola consulta
		List<Map<String, Object>> filasCitasFuturas = conexion.obtenerFilasDeTabla("Cita", Arrays.asList("idCita", "fechaCita", "horaCita", "descripcionCita"), "idMascota = ? AND fechaCita >= ?", idMascota, new Date());

		// Ordenar las citas por fecha
		filasCitasFuturas.sort(Comparator.comparing(fila -> (Date) fila.get("fechaCita")));

		StringBuilder cadenaCitasFuturas = new StringBuilder();

		for (Map<String, Object> fila : filasCitasFuturas) {
			cadenaCitasFuturas.append(" IdCita: ").append(fila.get("idCita"))
			.append("\n FechaCita: ").append(new SimpleDateFormat("dd / MM / yyyy").format(fila.get("fechaCita")))
			.append("\n HoraCita: ").append(new SimpleDateFormat("HH:mm").format(fila.get("horaCita")))
			.append("\n Descripcion: ").append(fila.get("descripcionCita"));
			cadenaCitasFuturas.append("\n=================================================");
		}

		// Establecer el texto en el JTextPane
		textPaneCitasFuturas.setText(cadenaCitasFuturas.toString());
	}
	
	
	public void rellenarComprasPrevias(Interfaz interfaz, JTextArea textPaneComprasRealizadas) throws Exception {
		int idUsuario = interfaz.getUser().getIdUsuario();

		// Realizar una sola consulta para obtener todos los datos necesarios
		List<Map<String, Object>> resultadosVenta = conexion.obtenerFilasDeTabla("Venta", Arrays.asList("idVenta", "idMismaVenta", "fechaVenta", "idUsuario", "tipo", "idArtServ", "descripcionVenta"), "idUsuario = ?", idUsuario);
		List<Map<String, Object>> resultadosArticulo = conexion.obtenerTodasLasFilasDeTabla("Articulo", Arrays.asList("idArticulo", "nombre", "marca", "precio", "descripcionArticulo"));
		List<Map<String, Object>> resultadosServicio = conexion.obtenerTodasLasFilasDeTabla("Servicio", Arrays.asList("idServicio", "nombre", "precio", "descripcionServicio"));

		// Invertir el orden de resultadosVenta para que las compras más recientes aparezcan primero
		Collections.reverse(resultadosVenta);

		StringBuilder comprasPrevias = new StringBuilder();

		for (int i = 0; i < resultadosVenta.size(); i++) {
			Map<String, Object> fila = resultadosVenta.get(i);   
			String idVenta = String.valueOf(fila.get("idVenta"));

			comprasPrevias.append(" Id venta: ").append(idVenta).append("\n");

			if (fila.get("tipo").equals("Articulo")) {
				for (Map<String, Object> articulo : resultadosArticulo) {
					if (fila.get("idArtServ").equals(articulo.get("idArticulo"))) {
						comprasPrevias
						.append(" Fecha de la venta: ").append(new SimpleDateFormat("dd / MM / yyyy").format(fila.get("fechaVenta"))).append("\n")
						.append(" Articulo: ").append(articulo.get("nombre")).append("\n")
						.append(" Precio: ").append(articulo.get("precio")).append(" €\n")
						.append(" Descripcion del artículo: ").append(articulo.get("descripcionArticulo")).append("\n");
					}
				}
			} else if (fila.get("tipo").equals("Servicio")) {
				for (Map<String, Object> servicio : resultadosServicio) {
					if (fila.get("idArtServ").equals(servicio.get("idServicio")))
					{
						comprasPrevias.append(" Fecha de la venta: ").append(new SimpleDateFormat("dd / MM / yyyy").format(fila.get("fechaVenta"))).append("\n")
						.append(" Servicio: ").append(servicio.get("nombre")).append("\n")
						.append(" Precio: ").append(servicio.get("precio")).append(" €\n")
						.append(" Descripcion del servicio: ").append(servicio.get("descripcionServicio")).append("\n");
					}
				}
			} 
			comprasPrevias.append("======================================================================\n");
			
			
		}
		
		textPaneComprasRealizadas.setText(comprasPrevias.toString());
	}
	
	
	public void mostrarCatalogo() throws DBException, SQLException {
		// Crea un JTextArea que será colocado en el JOptionPane
		JTextArea textArea = new JTextArea(10, 30);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		// Establece un texto predeterminado
		textArea.setText(conexion.catalogoToString());
		textArea.setEditable(false);
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 14));

		// Crea un JScrollPane para el JTextArea
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 600));

		// Muestra el JOptionPane con el JScrollPane
		JOptionPane.showMessageDialog(null, scrollPane, "Catálogo de productos", JOptionPane.PLAIN_MESSAGE);
	}
	
	
}
