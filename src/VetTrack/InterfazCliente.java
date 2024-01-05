package VetTrack;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.Choice;
import java.awt.GridLayout;
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.awt.Font;

public class InterfazCliente {

	public JFrame frmInterfazDelCliente;
	private Interfaz interfaz;
	private Color colorOriginalBton;

	private ConexionBD conexion = ConexionBD.getInstance();

	private Choice choiceMascotas;

	private JTextField textFieldIdMascota;
	private JTextField textFieldNombreMascota;
	private JTextField textFieldEspecieMascota;
	private JTextField textFieldRazaMascota;
	private JTextField textFieldFechaNacimientoMascota;
	private JTextField textFieldIdUsuarioMascota;
	private JTextPane textPaneCitasPrevias;
	private JTextPane textPaneCitasFuturas;
	private JTextPane textPaneComprasRealizadas;
	private JButton botonActualizarCitas;

	/**
	 * Create the application.
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public InterfazCliente(Interfaz interfaz) throws NumberFormatException, Exception {
		this.interfaz = interfaz;

		initialize_cliente();
		rellenarComprasPrevias();

	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	private void initialize_cliente() throws NumberFormatException, Exception {
		frmInterfazDelCliente = new JFrame();
		frmInterfazDelCliente.setTitle("Interfaz del Cliente: " + this.interfaz.getUser().getNombreUsuario());

		frmInterfazDelCliente.setBounds(300, 300, 1200, 900);
		frmInterfazDelCliente.setVisible(true);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frmInterfazDelCliente.setLocationRelativeTo(null);
		frmInterfazDelCliente.setResizable(false);

		frmInterfazDelCliente.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frmInterfazDelCliente.getContentPane().setLayout(null);

		JButton botCerrarSesion = new JButton("Cerrar Sesion");
		botCerrarSesion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int confirmacion = JOptionPane.showConfirmDialog(frmInterfazDelCliente, "Quieres cerrar sesion?", "Confirmar", JOptionPane.YES_NO_OPTION);

				if (confirmacion == JOptionPane.YES_OPTION) {
					interfaz.frame.setVisible(true);
					interfaz.setText();
					frmInterfazDelCliente.dispose(); //Lo que hace el dispose es que borra en memoria todo el frame
				}
			}
		});
		//		botCerrarSesion.setForeground(Color.red);
		colorOriginalBton = botCerrarSesion.getBackground();

		botCerrarSesion.setBounds(1038, 10, 136, 60);
		frmInterfazDelCliente.getContentPane().add(botCerrarSesion);

		JToggleButton botModoNoct = new JToggleButton("Modo Nocturno");

		botModoNoct.setBounds(897, 10, 131, 60);
		frmInterfazDelCliente.getContentPane().add(botModoNoct);

		JButton botVerPerfil = new JButton("Ver Perfil");
		botVerPerfil.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Esto es por ahora
				try {
					mostrarPerfil();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		botVerPerfil.setBounds(10, 11, 136, 51);
		frmInterfazDelCliente.getContentPane().add(botVerPerfil);

		JPanel panelDatosMascotas = new JPanel(new GridLayout(0, 4, 10, 10)); // 3 filas, 4 columnas
		panelDatosMascotas.setBounds(645, 135, 510, 150);
		frmInterfazDelCliente.getContentPane().add(panelDatosMascotas);		

		JLabel labelIdMascota = new JLabel("IdMascota: ");
		labelIdMascota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelIdMascota.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel labelNombreMascota = new JLabel("Nombre: ");
		labelNombreMascota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelNombreMascota.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel labelEspecieMascota = new JLabel("Especie: ");
		labelEspecieMascota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelEspecieMascota.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel labelRazaMascota = new JLabel("Raza: ");
		labelRazaMascota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelRazaMascota.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel labelFechaNacimientoMascota = new JLabel("Fecha Nacimiento: ");
		labelFechaNacimientoMascota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLabel labelIdUsuarioMascota = new JLabel("Nombre Dueño: ");
		labelIdUsuarioMascota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelIdUsuarioMascota.setHorizontalAlignment(SwingConstants.LEFT);

		// JTextFields no editables y con 15 columnas
		textFieldIdMascota = new JTextField();
		textFieldIdMascota.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldIdMascota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldIdMascota.setEditable(false);
		textFieldIdMascota.setColumns(15);

		textFieldNombreMascota = new JTextField();
		textFieldNombreMascota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldNombreMascota.setEditable(false);
		textFieldNombreMascota.setColumns(15);

		textFieldEspecieMascota = new JTextField();
		textFieldEspecieMascota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldEspecieMascota.setEditable(false);
		textFieldEspecieMascota.setColumns(15);

		textFieldRazaMascota = new JTextField();
		textFieldRazaMascota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldRazaMascota.setEditable(false);
		textFieldRazaMascota.setColumns(15);

		textFieldFechaNacimientoMascota = new JTextField();
		textFieldFechaNacimientoMascota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldFechaNacimientoMascota.setEditable(false);
		textFieldFechaNacimientoMascota.setColumns(15);

		textFieldIdUsuarioMascota = new JTextField();
		textFieldIdUsuarioMascota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldIdUsuarioMascota.setEditable(false);
		textFieldIdUsuarioMascota.setColumns(15);

		// Agregar componentes al panel
		panelDatosMascotas.add(labelIdMascota);
		panelDatosMascotas.add(textFieldIdMascota);
		panelDatosMascotas.add(labelNombreMascota);
		panelDatosMascotas.add(textFieldNombreMascota);
		panelDatosMascotas.add(labelEspecieMascota);
		panelDatosMascotas.add(textFieldEspecieMascota);
		panelDatosMascotas.add(labelRazaMascota);
		panelDatosMascotas.add(textFieldRazaMascota);
		panelDatosMascotas.add(labelFechaNacimientoMascota);
		panelDatosMascotas.add(textFieldFechaNacimientoMascota);
		panelDatosMascotas.add(labelIdUsuarioMascota);
		panelDatosMascotas.add(textFieldIdUsuarioMascota);

		choiceMascotas = new Choice();
		choiceMascotas.setBounds(645, 97, 250, 30);

		choiceMascotas.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if(choiceMascotas.getSelectedIndex()!= 0) {
						// Llamas al método que deseas ejecutar
						try {
							mostrarDatosMascota();
							rellenarCitasPasadas();
							rellenarCitasFuturas();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						textFieldIdMascota.setText("");
						textFieldNombreMascota.setText("");
						textFieldEspecieMascota.setText("");
						textFieldRazaMascota.setText("");
						textFieldFechaNacimientoMascota.setText("");
						textFieldIdUsuarioMascota.setText("");
						textPaneCitasPrevias.setText("");
					}
				}
			}
		});


		frmInterfazDelCliente.getContentPane().add(choiceMascotas);

		textPaneCitasPrevias = new JTextPane();
		textPaneCitasPrevias.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textPaneCitasPrevias.setBounds(673, 350, 225, 500);
		textPaneCitasPrevias.setBorder(new LineBorder(Color.BLACK, 1));
		textPaneCitasPrevias.setEditable(false);

		JScrollPane scrollPaneCitasPrevias = new JScrollPane(textPaneCitasPrevias);
		scrollPaneCitasPrevias.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCitasPrevias.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCitasPrevias.setBounds(640, 335, 515, 225);

		frmInterfazDelCliente.getContentPane().add(scrollPaneCitasPrevias);

		textPaneCitasFuturas = new JTextPane();
		textPaneCitasFuturas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textPaneCitasFuturas.setEditable(false);
		textPaneCitasFuturas.setBorder(new LineBorder(Color.BLACK, 1));
		textPaneCitasFuturas.setBounds(674, 625, 500, 225);

		JScrollPane scrollPaneCitasFuturas = new JScrollPane(textPaneCitasFuturas);
		scrollPaneCitasFuturas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCitasFuturas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCitasFuturas.setBounds(640, 610, 515, 225);

		frmInterfazDelCliente.getContentPane().add(scrollPaneCitasFuturas);

		JLabel labelCitasPrevias = new JLabel("CITAS PREVIAS");
		labelCitasPrevias.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelCitasPrevias.setHorizontalAlignment(SwingConstants.CENTER);
		labelCitasPrevias.setBounds(822, 310, 150, 14);
		frmInterfazDelCliente.getContentPane().add(labelCitasPrevias);

		JLabel labelCitasFuturas = new JLabel("CITAS FUTURAS");
		labelCitasFuturas.setHorizontalAlignment(SwingConstants.CENTER);
		labelCitasFuturas.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelCitasFuturas.setBounds(822, 585, 150, 14);
		frmInterfazDelCliente.getContentPane().add(labelCitasFuturas);

		JScrollPane scrollPaneComprasRealizadas = new JScrollPane((Component) null);
		scrollPaneComprasRealizadas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneComprasRealizadas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneComprasRealizadas.setBounds(30, 335, 550, 500);
		frmInterfazDelCliente.getContentPane().add(scrollPaneComprasRealizadas);

		textPaneComprasRealizadas = new JTextPane();
		textPaneComprasRealizadas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textPaneComprasRealizadas.setEditable(false);
		textPaneComprasRealizadas.setBorder(new LineBorder(Color.BLACK, 1));
		scrollPaneComprasRealizadas.setViewportView(textPaneComprasRealizadas);

		JLabel labelComprasPrevias = new JLabel("COMPRAS PREVIAS");
		labelComprasPrevias.setHorizontalAlignment(SwingConstants.CENTER);
		labelComprasPrevias.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelComprasPrevias.setBounds(217, 310, 150, 14);
		frmInterfazDelCliente.getContentPane().add(labelComprasPrevias);

		JButton botonActualizarCompras = new JButton("ACTUALIZAR");
		botonActualizarCompras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					rellenarComprasPrevias();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		botonActualizarCompras.setBounds(400, 308, 180, 23);
		frmInterfazDelCliente.getContentPane().add(botonActualizarCompras);



		ImageIcon icon = new ImageIcon("etc/IMAGENES/logo_VetTrack.png");

		Image image = icon.getImage();
		Image scaledImage = image.getScaledInstance(175, 175, Image.SCALE_SMOOTH);

		ImageIcon scaledIcon = new ImageIcon(scaledImage);


		JLabel labelIcono = new JLabel(scaledIcon);
		labelIcono.setBounds(42, 73, 175, 175);
		frmInterfazDelCliente.getContentPane().add(labelIcono);

		botonActualizarCitas = new JButton("ACTUALIZAR");
		botonActualizarCitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					rellenarCitasPasadas();
					rellenarCitasFuturas();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		botonActualizarCitas.setBounds(975, 97, 180, 23);
		frmInterfazDelCliente.getContentPane().add(botonActualizarCitas);

		choiceMascotas.add("");
		establecerMascotas();

		frmInterfazDelCliente.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				confirmarSalir(frmInterfazDelCliente);
			}
		});


		//El togle no cambia de color 
		botModoNoct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				modoNocturno(botModoNoct);

			}
		});

	}

	private void modoNocturno(JToggleButton botModoNoct) {

		Component[] components = frmInterfazDelCliente.getContentPane().getComponents();

		for (Component component : components) {
			if (component instanceof JButton) {

				JButton boton = (JButton) component;
				boton.setBackground(botModoNoct.isSelected() ? new Color(150, 150, 150) : this.colorOriginalBton);

			}
		}

		frmInterfazDelCliente.getContentPane().setBackground(botModoNoct.isSelected() ? new Color(50, 50, 50) : this.colorOriginalBton);

	}


	private void confirmarSalir(JFrame frame) {
		int confirmacion = JOptionPane.showConfirmDialog(frame, "Quieres salir de la aplicacion?", "Confirmar", JOptionPane.YES_NO_OPTION);

		//Para comprobar si dice que si o si no
		if (confirmacion == JOptionPane.YES_OPTION) {

			//Esto es para cerrar la aplicacion
			//frame.dispose(); //Cierra el frame
			this.interfaz.frame.dispose();
			System.exit(0); //es parecido al exit(1) de C

		}
	}



	public void establecerMascotas() throws NumberFormatException, Exception {

		int numMascotas = Integer.parseInt(conexion.obtenerDatoDeTabla("Cliente", "numMascotas", "idUsuario", interfaz.getUser().getIdUsuario()));

		int idUsuario = interfaz.getUser().getIdUsuario();

		List<String> lista = conexion.obtenerDatosDeTablaLista("Mascota", "nombre", "idUsuario", idUsuario);

		for(int i = 0; i < numMascotas; i++) {

			choiceMascotas.add(lista.get(i));

		}

	}

	public void mostrarDatosMascota() throws Exception {
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
			textFieldFechaNacimientoMascota.setText(String.valueOf(datosMascota.get("fechaNacimiento")));
			textFieldIdUsuarioMascota.setText(dueno);
		}
	}





	public void mostrarPerfil() throws Exception {
		// Obtener el usuario activo una sola vez
		Usuario usuarioActivo = interfaz.verDatosUsuarioActivo();

		// Obtener todos los datos necesarios en una sola consulta
		List<Map<String, Object>> resultados = conexion.obtenerFilasDeTabla("Cliente", Arrays.asList("nombre", "apellidos", "dni", "telefono"), "idUsuario = ?", usuarioActivo.getIdUsuario());

		Map<String, Object> datosCliente = resultados.isEmpty() ? Collections.emptyMap() : resultados.get(0);

		int idUsuario = usuarioActivo.getIdUsuario();
		String nombreUsuario = usuarioActivo.getNombreUsuario();
		String contrasena = usuarioActivo.getContrasena();
		String rolUsuario = usuarioActivo.getRol();
		String nombre = String.valueOf(datosCliente.get("nombre"));
		String apellidos = String.valueOf(datosCliente.get("apellidos"));
		String dni = String.valueOf(datosCliente.get("dni"));
		String telefono = String.valueOf(datosCliente.get("telefono"));

		// Mostrar la información del perfil
		JOptionPane.showMessageDialog(null,
				"Id del usuario: " + idUsuario + ""
						+ "\nUsuario: " + nombreUsuario + ""
						+ "\nContraseña: " + contrasena + ""
						+ "\nRol: " + rolUsuario
						+ "\nNombre: " + nombre
						+ "\nApellidos: " + apellidos
						+ "\nDNI: " + dni
						+ "\nTelefono: " + telefono,
						"Perfil del usuario", 
						JOptionPane.INFORMATION_MESSAGE);
	}



	public void rellenarCitasPasadas() throws Exception {
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



	public void rellenarCitasFuturas() throws Exception {
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



	public void rellenarComprasPrevias() throws Exception {
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
}
