package VetTrack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

import java.util.Base64;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

import Exceptions.DBException;

public class InterfazAdministrador {

	public JFrame frame;
	private Interfaz interfaz;
	private Color colorOriginalBton;
	private JTextField textUserBuscar;
	private JTextField textNombreArticulo;
	private JTextField textPrecioArticulo;
	private JTextField textUserCita;
	private JTextField textFechaCita;
	private JTextField textHoraCita;
	private JTextField textMascCita;
	private JTextField textMarcaArticulo;
	private JTextPane textPaneCitasPrevias;
	private JTextPane textPaneCitasFuturas;
	private JTextPane panelTextUser;
	private JTextArea textAreaArticulo;
	private JTextPane panelDescrCita;

	/**
	 * Create the application.
	 */
	public InterfazAdministrador(Interfaz interfaz) {
		this.interfaz = interfaz;		
		initialize_admin();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize_admin() {
		frame = new JFrame();

		frame.setTitle("Panel de control de " + this.interfaz.getUser().getNombreUsuario());

		frame.setBounds(300, 300, 1200, 900);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton botCerrarSesion = new JButton("Cerrar Sesion");
		botCerrarSesion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botCerrarSesion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int confirmacion = JOptionPane.showConfirmDialog(frame, "Quieres cerrar sesion?", "Confirmar", JOptionPane.YES_NO_OPTION);

				if (confirmacion == JOptionPane.YES_OPTION) {
					interfaz.frame.setVisible(true);
					interfaz.setText();
					frame.dispose(); //Lo que hace el dispose es que borra en memoria todo el frame
				}
			}
		});

		colorOriginalBton = botCerrarSesion.getBackground();

		botCerrarSesion.setBounds(1038, 11, 136, 59);
		frame.getContentPane().add(botCerrarSesion);

		JToggleButton botModoNoct = new JToggleButton("Modo Nocturno");
		botModoNoct.setFont(new Font("Tahoma", Font.PLAIN, 12));

		botModoNoct.setBounds(897, 29, 131, 23);
		frame.getContentPane().add(botModoNoct);

		JButton botVerPerfil = new JButton("Ver Perfil");
		botVerPerfil.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botVerPerfil.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				verPerfil();
			}
		});

		botVerPerfil.setBounds(10, 11, 136, 51);
		frame.getContentPane().add(botVerPerfil);

		JButton botCrearUsuario = new JButton("Crear Usuario...");
		botCrearUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botCrearUsuario.setBounds(202, 11, 176, 51);
		frame.getContentPane().add(botCrearUsuario);
		botCrearUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createUser();
			}
		});

		JButton botQuitarUsuario = new JButton("Eliminar Usuario...");
		botQuitarUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botQuitarUsuario.setBounds(377, 11, 176, 51);
		frame.getContentPane().add(botQuitarUsuario);
		botQuitarUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteUser();
			}
		});

		JLabel labText = new JLabel("Hola, " + this.interfaz.getUser().getNombreUsuario());
		labText.setBounds(563, 11, 324, 59);
		labText.setFont(new Font("Arial", Font.PLAIN, 30));
		labText.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(labText);


		panelTextUser = new JTextPane();
		panelTextUser.setBounds(830, 219, 340, 631);
		panelTextUser.setEditable(false);
		panelTextUser.setBorder(new LineBorder(Color.BLACK, 1));

		JScrollPane scrollPaneMascotas = new JScrollPane(panelTextUser);
		scrollPaneMascotas.setBounds(830, 220, 340, 631); // Ajustado el tamaño del JScrollPane
		scrollPaneMascotas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneMascotas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.getContentPane().add(scrollPaneMascotas);


		textPaneCitasPrevias = new JTextPane();
		textPaneCitasPrevias.setEditable(false);
		textPaneCitasPrevias.setBorder(new LineBorder(Color.BLACK, 1));

		JScrollPane scrollPaneCitasPrevias = new JScrollPane(textPaneCitasPrevias);
		scrollPaneCitasPrevias.setBounds(440, 188, 351, 303); // Ajustado el tamaño del JScrollPane
		scrollPaneCitasPrevias.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCitasPrevias.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.getContentPane().add(scrollPaneCitasPrevias);


		textPaneCitasFuturas = new JTextPane();
		textPaneCitasFuturas.setBounds(440, 547, 351, 303);
		textPaneCitasFuturas.setEditable(false);
		textPaneCitasFuturas.setBorder(new LineBorder(Color.BLACK, 1));

		JScrollPane scrollPaneCitasFuturas = new JScrollPane(textPaneCitasFuturas);
		scrollPaneCitasFuturas.setBounds(440, 548, 351, 303); // Ajustado el tamaño del JScrollPane
		scrollPaneCitasFuturas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCitasFuturas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.getContentPane().add(scrollPaneCitasFuturas);

		JButton botBuscarUser = new JButton("Buscar");
		botBuscarUser.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botBuscarUser.setBounds(940, 186, 100, 23);
		frame.getContentPane().add(botBuscarUser);
		botBuscarUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarUsuario();
			}
		});

		textUserBuscar = new JTextField();
		textUserBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textUserBuscar.setBounds(830, 186, 100, 20);
		frame.getContentPane().add(textUserBuscar);
		textUserBuscar.setColumns(10);

		JLabel labUserBuscar = new JLabel("Buscar Usuario:");
		labUserBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labUserBuscar.setBounds(830, 161, 159, 14);
		frame.getContentPane().add(labUserBuscar);

		JLabel lblNewLabel = new JLabel("Añadir artículo");
		lblNewLabel.setBounds(161, 150, 203, 31);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(15, 195, 80, 14);
		frame.getContentPane().add(lblNewLabel_1);

		textNombreArticulo = new JTextField();
		textNombreArticulo.setBounds(100, 192, 296, 20);
		frame.getContentPane().add(textNombreArticulo);
		textNombreArticulo.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Precio:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(15, 229, 70, 14);
		frame.getContentPane().add(lblNewLabel_2);

		textPrecioArticulo = new JTextField();
		textPrecioArticulo.setBounds(100, 226, 296, 20);
		frame.getContentPane().add(textPrecioArticulo);
		textPrecioArticulo.setColumns(10);

		textAreaArticulo = new JTextArea();
		textAreaArticulo.setBorder(new LineBorder(Color.BLACK, 1));
		textAreaArticulo.setBounds(100, 302, 296, 119);

		frame.getContentPane().add(textAreaArticulo);

		JLabel lblNewLabel_3 = new JLabel("Descripción:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(15, 307, 80, 14);
		frame.getContentPane().add(lblNewLabel_3);

		JButton botCrearArticulo = new JButton("Crear");
		botCrearArticulo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botCrearArticulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				crearArticulo();

			}
		});
		botCrearArticulo.setBounds(308, 432, 89, 23);
		frame.getContentPane().add(botCrearArticulo);

		JLabel lblNewLabel_4 = new JLabel("Crear cita");
		lblNewLabel_4.setBounds(180, 522, 211, 39);

		lblNewLabel_4.setFont(new Font("Arial", Font.PLAIN, 20));

		frame.getContentPane().add(lblNewLabel_4);

		textUserCita = new JTextField();
		textUserCita.setBounds(40, 597, 152, 20);
		frame.getContentPane().add(textUserCita);
		textUserCita.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Nombre Usuario:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(40, 572, 152, 14);
		frame.getContentPane().add(lblNewLabel_5);

		textFechaCita = new JTextField();
		textFechaCita.setBounds(245, 597, 152, 20);
		frame.getContentPane().add(textFechaCita);
		textFechaCita.setColumns(10);

		JLabel lblNewLabel_6 = new JLabel("Fecha cita: (dd/mm/aaaa)");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_6.setBounds(245, 572, 152, 14);
		frame.getContentPane().add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Hora cita: (hh:mm)");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_7.setBounds(245, 628, 152, 14);
		frame.getContentPane().add(lblNewLabel_7);

		textHoraCita = new JTextField();
		textHoraCita.setBounds(245, 653, 152, 20);
		frame.getContentPane().add(textHoraCita);
		textHoraCita.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("Nombre mascota:");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_8.setBounds(40, 628, 152, 14);
		frame.getContentPane().add(lblNewLabel_8);

		textMascCita = new JTextField();
		textMascCita.setBounds(40, 653, 152, 20);
		frame.getContentPane().add(textMascCita);
		textMascCita.setColumns(10);

		panelDescrCita = new JTextPane();
		panelDescrCita.setBorder(new LineBorder(Color.BLACK, 1));
		panelDescrCita.setBounds(40, 704, 356, 64);

		frame.getContentPane().add(panelDescrCita);

		JButton botCrearCita = new JButton("Crear");
		botCrearCita.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botCrearCita.setBounds(308, 779, 89, 23);
		frame.getContentPane().add(botCrearCita);
		botCrearCita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				crearCita();

			}
		});

		JLabel lblNewLabel_9 = new JLabel("Citas futuras");
		lblNewLabel_9.setBounds(440, 502, 351, 34);
		lblNewLabel_9.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblNewLabel_9);

		JLabel lblNewLabel_10 = new JLabel("Citas Previas");
		lblNewLabel_10.setBounds(440, 136, 351, 34);
		lblNewLabel_10.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblNewLabel_10);

		JLabel lblNewLabel_11 = new JLabel("Marca:");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_11.setBounds(15, 264, 70, 14);
		frame.getContentPane().add(lblNewLabel_11);

		textMarcaArticulo = new JTextField();
		textMarcaArticulo.setBounds(100, 261, 296, 20);
		frame.getContentPane().add(textMarcaArticulo);
		textMarcaArticulo.setColumns(10);

		JLabel lblNewLabel_12 = new JLabel("Descripción:");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_12.setBounds(40, 684, 89, 14);
		frame.getContentPane().add(lblNewLabel_12);

		JButton botActualizarUserBuscar = new JButton("Actualizar");
		botActualizarUserBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botActualizarUserBuscar.setBounds(940, 152, 100, 23);
		frame.getContentPane().add(botActualizarUserBuscar);

		JButton botAddMasc = new JButton("Añadir Mascota");
		botAddMasc.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botAddMasc.setBounds(1044, 186, 130, 23);
		frame.getContentPane().add(botAddMasc);

		JButton botonNuevaVenta = new JButton("Nueva venta");
		botonNuevaVenta.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botonNuevaVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarVenta();
			}
		});
		botonNuevaVenta.setBounds(1044, 152, 130, 23);
		frame.getContentPane().add(botonNuevaVenta);


		ImageIcon icon = new ImageIcon("etc/IMAGENES/logo_VetTrack.png");
		Image image = icon.getImage();
		Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel labelIcono = new JLabel(scaledIcon);
		labelIcono.setBounds(30, 75, 100, 100);
		frame.getContentPane().add(labelIcono);


		botAddMasc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agregarMascota();
			}
		});

		botActualizarUserBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarUsuario();
			}
		});

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				confirmarSalir(frame);
			}
		});

		botModoNoct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modoNocturno(botModoNoct);
			}
		});

	}

	private void verPerfil() {

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

				JOptionPane.showMessageDialog(null, interfaz.getUser().getContrasena().toString(), "Ver Contraseña", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JLabel labelUsername = new JLabel("Username: " +this.interfaz.getUser().getNombreUsuario());
		JLabel labelPasswd = new JLabel("Password: " + getAsteriscos(this.interfaz.getUser().getContrasena()));
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

	private String getAsteriscos(String passwd) {

		int ast = passwd.length();

		StringBuffer cadena = new StringBuffer("");

		for(int i = 0; i<ast; i++) {
			cadena.append("*");
		}

		return cadena.toString();

	}

	private void addMascotaCliente(int idUsuario) {

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

					interfaz.getConexion().agregarFilaATabla("Mascota", valores);

					List<Map<String, Object>> filaCliente = interfaz.getConexion().obtenerFilasDeTabla("Cliente", Arrays.asList("numMascotas"), "idUsuario = ?", idUsuario);

					int nuevoNumMascotas = (int) filaCliente.get(0).get("numMascotas") + 1;

					interfaz.getConexion().actualizarFila("Cliente", "numMascotas", nuevoNumMascotas, "idUsuario", idUsuario);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				JOptionPane.showMessageDialog(null, "Hay campos vacios");
			}


		}
	}

	private int buscarMaximo(String cadena) throws DBException {

		List<List<Object>> mascotaTotal = interfaz.getConexion().listar("Mascota");
		int maximo = 0;

		for (List<Object> user : mascotaTotal) {
			if ((int) user.get(0) > maximo) {
				maximo = (int) user.get(0);
			}
		}

		maximo++;

		return maximo;

	}

	private void modoNocturno(JToggleButton botModoNoct) {

		Component[] components = frame.getContentPane().getComponents();

		for (Component component : components) {
			if (component instanceof JButton) {

				JButton boton = (JButton) component;
				boton.setBackground(botModoNoct.isSelected() ? new Color(150, 150, 150) : this.colorOriginalBton);

			} else if (component instanceof JLabel) {
				JLabel labl = (JLabel) component;
				labl.setForeground(botModoNoct.isSelected() ? Color.white : Color.black);
			}
		}
		frame.getContentPane().setBackground(botModoNoct.isSelected() ? new Color(50, 50, 50) : this.colorOriginalBton);

	}

	private void confirmarSalir(JFrame frame) {
		int confirmacion = JOptionPane.showConfirmDialog(frame, "Quieres salir de la aplicacion?", "Confirmar", JOptionPane.YES_NO_OPTION);

		if (confirmacion == JOptionPane.YES_OPTION) {

			this.interfaz.frame.dispose();
			System.exit(0); //es parecido al exit(1) de C

		}
	}

	private void buscarUsuario() {
		try {

			List<Object> cadena = interfaz.cogerDatosBorrar(textUserBuscar.getText().intern());

			if (cadena == null) {
				textUserBuscar.setText("");
				panelTextUser.setText("");
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

	private String mascRecDatos(String nombreMascota, int idUsuario) throws Exception {

		List<String> columnas = Arrays.asList("idMascota", "especie", "raza", "fechaNacimiento");
		String condicion = "nombre = ? AND idUsuario = ?";

		Object[] parametros = {nombreMascota, idUsuario};

		List<Map<String, Object>> resultados = this.interfaz.getConexion().obtenerFilasDeTabla("Mascota", columnas, condicion, parametros);

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

	private ArrayList<String> recTodasMascotas(int id) throws NumberFormatException, Exception {

		int numMascotas = Integer.parseInt(this.interfaz.getConexion().obtenerDatoDeTabla("Cliente", "numMascotas", "idUsuario", id));

		List<String> lista = this.interfaz.getConexion().obtenerDatosDeTablaLista("Mascota", "nombre", "idUsuario", id);

		ArrayList<String> mascotasLista = new ArrayList<String>();

		for(int i = 0; i < numMascotas; i++) {

			mascotasLista.add(lista.get(i).toString().intern());

		}

		return mascotasLista;

	}

	private void createUser() {

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

		int resultado = JOptionPane.showConfirmDialog(this.frame, panel, "Agregar Usuario", JOptionPane.OK_CANCEL_OPTION);

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
							interfaz.getConexion().agregarFilaATabla("Usuario", valores);

							Map<String, Object> valoresCliente = new HashMap<>();

							valoresCliente.put("idUsuario", maximo);
							valoresCliente.put("dni", txtDNI.getText().intern());
							valoresCliente.put("telefono", txtTelefono.getText().intern());

							String cadenasep[] = txtNombreCompleto.getText().intern().split(" ");

							valoresCliente.put("nombre", cadenasep[0]);
							valoresCliente.put("apellidos", String.join(" ", Arrays.copyOfRange(cadenasep, 1, cadenasep.length)));
							valoresCliente.put("numMascotas", 0);

							interfaz.getConexion().agregarFilaATabla("Cliente", valoresCliente);

							JOptionPane.showMessageDialog(null, "Se ha añadido al cliente correctamente");

						}

					} else if (cboTipoUsuario.getSelectedItem().toString().intern()== "Administrador") {

						int maximo = buscarMaximo("Usuario");

						Map<String, Object> valores = new HashMap<>();

						valores.put("idUsuario", maximo);
						valores.put("nombreUsuario", txtUsuario.getText().intern());
						valores.put("contraseña", new String(txtPassword.getPassword()).intern());
						valores.put("rol", cboTipoUsuario.getSelectedItem().toString().intern());
						interfaz.getConexion().agregarFilaATabla("Usuario", valores);

						Map<String, Object> valoresAdministrador = new HashMap<>();

						valoresAdministrador.put("idUsuario", maximo);
						valoresAdministrador.put("cadenaInicioSesion", new String(Base64.getEncoder().encode(txtUsuario.getText().intern().getBytes())));

						interfaz.getConexion().agregarFilaATabla("Administrador", valoresAdministrador);

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

	private void deleteUser() {

		JPanel panel = new JPanel(new GridLayout(0, 2));

		JTextField txtNombre = new JTextField();

		panel.add(new JLabel("Usuario:"));
		panel.add(txtNombre);

		int resultado = JOptionPane.showConfirmDialog(null, panel, "Borrar Usuario", JOptionPane.OK_CANCEL_OPTION);

		if (resultado == JOptionPane.OK_OPTION) {

			try {

				List<Object> cadena = interfaz.cogerDatosBorrar(txtNombre.getText().toString().intern());

				if (cadena == null) {
					JOptionPane.showMessageDialog(null, "No se ha encontrado ningun usuario con ese nombre");
				}else {
					int confirmacion = JOptionPane.showConfirmDialog(null, "ID: "+ cadena.get(0).toString() + "\nUsername: "+cadena.get(1).toString()+"\nRol: "+cadena.get(3).toString(), "Confirmar",
							JOptionPane.YES_NO_OPTION);

					if (confirmacion == JOptionPane.YES_OPTION) {

						if (cadena.get(3).toString().intern()=="Cliente") {
							//comprobar si tiene mascotas
							if (Integer.parseInt(interfaz.getConexion().obtenerDatoDeTabla("Cliente", "numMascotas", "idUsuario", Integer.parseInt(cadena.get(0).toString()))) != 0) {
								interfaz.getConexion().eliminarFilaDeTabla("Mascota", "idUsuario", Integer.parseInt(cadena.get(0).toString()));
							}

						}

						interfaz.getConexion().eliminarFilaDeTabla(cadena.get(3).toString().intern(), "idUsuario", Integer.parseInt(cadena.get(0).toString()));
						interfaz.getConexion().eliminarFilaDeTabla("Usuario", "idUsuario", Integer.parseInt(cadena.get(0).toString()));
						JOptionPane.showMessageDialog(null, "El usuario "+cadena.get(1).toString()+" se ha eliminado correctamente");
					}
				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}



	}

	//Puede haber solamente 2, > y <
	//	">" ---> es para mayor
	//	"<" ---> es para menor
	private String recCitasUsuario(int id, String caracter) throws Exception{

		List<Map<String, Object>> citas = this.interfaz.getConexion().obtenerFilasDeTabla( "Cita", Arrays.asList("idCita", "fechaCita", "horaCita", "descripcionCita", "idMascota"), "idUsuario = ? AND fechaCita "+caracter+" ?", id, Date.valueOf(LocalDate.now()));

		StringBuffer stringBuffer = new StringBuffer("");
		int i = 1;

		for(Map<String, Object> unaCita : citas) {

			String nombreMascota = this.interfaz.getConexion().obtenerDatoDeTabla("Mascota", "nombre", "idMascota", unaCita.get("idMascota"));

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


	private void crearArticulo() {
		if (textAreaArticulo.getText().intern() != "" && textPrecioArticulo.getText().intern() != "" && textNombreArticulo.getText().intern() != "" 
				&& textMarcaArticulo.getText().intern() != "" && Float.parseFloat(textPrecioArticulo.getText().intern())>0) {

			try {
				int maximo = buscarMaximo("Articulo");

				Map<String, Object> valores = new HashMap<>();

				valores.put("idArticulo", maximo+1);
				valores.put("nombre", textNombreArticulo.getText().intern());
				valores.put("marca", textMarcaArticulo.getText().intern());
				valores.put("precio", Float.parseFloat(textPrecioArticulo.getText().intern()));
				valores.put("descripcionArticulo", textAreaArticulo.getText().intern());

				interfaz.getConexion().agregarFilaATabla("Articulo", valores);

				JOptionPane.showMessageDialog(null, "El articulo se ha añadido correctamente");

				//Para borrar parametros
				textNombreArticulo.setText("");
				textMarcaArticulo.setText("");
				textPrecioArticulo.setText("");
				textAreaArticulo.setText("");

			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}else {
			JOptionPane.showMessageDialog(null, "No puede haber un campo vacio ni precio menor de 0");
		}
	}


	private void crearCita() {
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
				List<Object> user = interfaz.cogerDatosBorrar(textUserCita.getText().intern());

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

						valores.put("idCita", maximo + 1);
						valores.put("idUsuario", Integer.parseInt(user.get(0).toString()));
						valores.put("fechaCita", java.sql.Date.valueOf(fechaFinal));

						String horaCita = textHoraCita.getText() + ":00";

						valores.put("horaCita", java.sql.Time.valueOf(horaCita));
						valores.put("idMascota", idMascota);
						valores.put("descripcionCita", panelDescrCita.getText());

						interfaz.getConexion().agregarFilaATabla("Cita", valores);

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

	private int idMascotaRecuperar(String nombreMascota, int idUsuario) throws Exception{

		List<String> columnas = Arrays.asList("idMascota", "especie", "raza", "fechaNacimiento");
		String condicion = "nombre = ? AND idUsuario = ?";

		Object[] parametros = {nombreMascota, idUsuario};

		List<Map<String, Object>> resultados = this.interfaz.getConexion().obtenerFilasDeTabla("Mascota", columnas, condicion, parametros);

		if (!resultados.isEmpty()) {
			Map<String, Object> datosMascota = resultados.get(0);

			return (int) datosMascota.get("idMascota");
		}

		return -1;
	}

	private void agregarMascota() {
		try {

			if(textUserBuscar.getText().intern() != "") {

				List<Object> cadena = interfaz.cogerDatosBorrar(textUserBuscar.getText().intern());

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

			} else {
				JOptionPane.showMessageDialog(null, "No hay un usuario seleccionado");
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}


	private void agregarVenta() {






	}
}
