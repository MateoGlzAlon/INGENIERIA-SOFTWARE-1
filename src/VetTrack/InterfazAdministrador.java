package VetTrack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.SQLException;
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
import javax.swing.JDialog;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

import java.util.Base64;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.time.format.DateTimeFormatter;

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
	private JTextArea textAreaDescripcionServicio;

	private List<JTextField> celdas;
	private List<JComboBox<String>> choices;

	private ConexionBD conexion = ConexionBD.getInstance();

	private List<String> idsParaVentas;
	private List<String> tiposParaVentas;

	private JTextArea textAreaDesc;
	private String descripcionAux;
	private List<JTextArea> descripAux;
	private JTextField textFieldNombreServicio;
	private JTextField textFieldPrecioServicio;


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
		scrollPaneCitasFuturas.setBounds(440, 548, 351, 303);
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
		lblNewLabel.setBounds(159, 90, 203, 31);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 127, 80, 14);
		frame.getContentPane().add(lblNewLabel_1);

		textNombreArticulo = new JTextField();
		textNombreArticulo.setBounds(95, 124, 296, 20);
		frame.getContentPane().add(textNombreArticulo);
		textNombreArticulo.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Precio:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(10, 161, 70, 14);
		frame.getContentPane().add(lblNewLabel_2);

		textPrecioArticulo = new JTextField();
		textPrecioArticulo.setBounds(95, 158, 296, 20);
		frame.getContentPane().add(textPrecioArticulo);
		textPrecioArticulo.setColumns(10);

		textAreaArticulo = new JTextArea();
		textAreaArticulo.setBorder(new LineBorder(Color.BLACK, 1));
		textAreaArticulo.setBounds(95, 234, 296, 64);

		frame.getContentPane().add(textAreaArticulo);

		JLabel lblNewLabel_3 = new JLabel("Descripción:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(10, 239, 80, 14);
		frame.getContentPane().add(lblNewLabel_3);

		JButton botCrearArticulo = new JButton("Crear");
		botCrearArticulo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botCrearArticulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				crearArticulo();

			}
		});
		botCrearArticulo.setBounds(302, 308, 89, 23);
		frame.getContentPane().add(botCrearArticulo);

		JLabel lblNewLabel_4 = new JLabel("Crear cita");
		lblNewLabel_4.setBounds(180, 522, 145, 39);

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
		lblNewLabel_11.setBounds(10, 196, 70, 14);
		frame.getContentPane().add(lblNewLabel_11);

		textMarcaArticulo = new JTextField();
		textMarcaArticulo.setBounds(95, 193, 296, 20);
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

				try {
					if(!textUserBuscar.getText().equals("")){
						mostrarCatalogoConVenta();
					} else {
						JOptionPane.showMessageDialog(null, "No has seleccionado un usuario", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DBException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});


		botonNuevaVenta.setBounds(1044, 152, 130, 23);
		frame.getContentPane().add(botonNuevaVenta);


		ImageIcon icon = new ImageIcon("etc/IMAGENES/logo_VetTrack.png");
		Image image = icon.getImage();
		Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel labelIcono = new JLabel(scaledIcon);
		labelIcono.setBounds(440, 72, 100, 100);
		frame.getContentPane().add(labelIcono);

		JLabel lblAadirServicio = new JLabel("Añadir servicio");
		lblAadirServicio.setFont(new Font("Arial", Font.PLAIN, 20));
		lblAadirServicio.setBounds(159, 332, 203, 31);
		frame.getContentPane().add(lblAadirServicio);

		JLabel lblNewLabel_1_1 = new JLabel("Nombre:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(10, 369, 80, 14);
		frame.getContentPane().add(lblNewLabel_1_1);

		textFieldNombreServicio = new JTextField();
		textFieldNombreServicio.setColumns(10);
		textFieldNombreServicio.setBounds(95, 366, 296, 20);
		frame.getContentPane().add(textFieldNombreServicio);

		JLabel lblNewLabel_2_1 = new JLabel("Precio:");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_1.setBounds(10, 403, 70, 14);
		frame.getContentPane().add(lblNewLabel_2_1);

		textFieldPrecioServicio = new JTextField();
		textFieldPrecioServicio.setColumns(10);
		textFieldPrecioServicio.setBounds(95, 400, 296, 20);
		frame.getContentPane().add(textFieldPrecioServicio);

		JLabel lblNewLabel_3_1 = new JLabel("Descripción:");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3_1.setBounds(10, 436, 80, 14);
		frame.getContentPane().add(lblNewLabel_3_1);

		textAreaDescripcionServicio = new JTextArea();
		textAreaDescripcionServicio.setBorder(new LineBorder(Color.BLACK, 1));
		textAreaDescripcionServicio.setBounds(95, 436, 296, 64);
		frame.getContentPane().add(textAreaDescripcionServicio);

		JButton botCrearServicio = new JButton("Crear");
		botCrearServicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				crearServicio();

			}
		});
		botCrearServicio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botCrearServicio.setBounds(302, 513, 89, 23);
		frame.getContentPane().add(botCrearServicio);


		celdas = new ArrayList<>();
		choices = new ArrayList<>();
		idsParaVentas = new ArrayList<>();
		tiposParaVentas = new ArrayList<>();
		textAreaDesc = new JTextArea();
		descripAux = new ArrayList<>();


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

	//este
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

	//este
	private int buscarMaximo(String tabla) throws DBException {

		List<List<Object>> mascotaTotal = conexion.listar(tabla);
		int maximo = 0;

		for (List<Object> user : mascotaTotal) {
			if ((int) user.get(0) > maximo) {
				maximo = (int) user.get(0);
			}
		}

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

	//este
	private String mascRecDatos(String nombreMascota, int idUsuario) throws Exception {

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

	//este
	private ArrayList<String> recTodasMascotas(int id) throws NumberFormatException, Exception {

		int numMascotas = Integer.parseInt(this.conexion.obtenerDatoDeTabla("Cliente", "numMascotas", "idUsuario", id));

		List<String> lista = this.conexion.obtenerDatosDeTablaLista("Mascota", "nombre", "idUsuario", id);

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
	
	
	//este
	//Puede haber solamente 2, > y <
	//	">" ---> es para mayor
	//	"<" ---> es para menor
	private String recCitasUsuario(int id, String caracter) throws Exception{

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


	private void crearArticulo() {
		if (textAreaArticulo.getText().intern() != "" && textPrecioArticulo.getText().intern() != "" && textNombreArticulo.getText().intern() != "" 
				&& textMarcaArticulo.getText().intern() != "" && Float.parseFloat(textPrecioArticulo.getText().intern())>0) {

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

	//este
	private int idMascotaRecuperar(String nombreMascota, int idUsuario) throws Exception{

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


	public void mostrarCatalogoConVenta() throws SQLException, DBException {
		// Crear un panel principal
		JPanel panel = new JPanel(new BorderLayout());

		// Crear un JTextArea para mostrar el catálogo
		JTextArea textArea = new JTextArea(10, 30);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setText(this.conexion.catalogoToString());
		textArea.setEditable(false);
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 14));

		// Crear un JScrollPane para el JTextArea
		JScrollPane catalogoScrollPane = new JScrollPane(textArea);
		catalogoScrollPane.setPreferredSize(new Dimension(500, 600));

		// Crear un JPanel para las celdas y los JComboBox
		JPanel ventaPanel = new JPanel();
		JLabel labelNumArt = new JLabel("Número de artículos");

		// Crear un JComboBox con opciones del 1 al 9
		JComboBox<String> choice = new JComboBox<>();
		for (int i = 1; i <= 9; i++) {
			choice.addItem(String.valueOf(i));
		}

		ventaPanel.setLayout(new BoxLayout(ventaPanel, BoxLayout.Y_AXIS));
		ventaPanel.add(Box.createVerticalGlue()); // Espacio en la parte superior

		// Centrar verticalmente las etiquetas y el JComboBox
		JPanel labelPanel = new JPanel();
		labelPanel.add(labelNumArt);
		ventaPanel.add(labelPanel);

		JPanel choicePanel = new JPanel();
		choicePanel.add(choice);
		ventaPanel.add(choicePanel);

		// Añadir un ActionListener al JComboBox para manejar cambios de selección
		choice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Obtener el número seleccionado
				int numeroCeldas = Integer.parseInt((String) choice.getSelectedItem());

				// Limpiar la lista de celdas y la lista de JComboBox
				celdas.clear();
				choices.clear();

				// Limpiar los componentes existentes en el ventaPanel
				ventaPanel.removeAll();

				// Agregar nuevas celdas y JComboBox al panel y las listas
				for (int i = 0; i < numeroCeldas; i++) {
					JTextField textField = new JTextField(20);
					celdas.add(textField);

					JComboBox<String> comboBox = new JComboBox<>();
					comboBox.addItem("Articulo");
					comboBox.addItem("Servicio");
					choices.add(comboBox);

					JPanel celdaPanel = new JPanel();
					celdaPanel.add(comboBox);
					celdaPanel.add(textField);

					ventaPanel.add(celdaPanel);
				}

				JTextArea textAreaDesc = new JTextArea("", 1, 10);
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
				textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
				textArea.setAlignmentY(Component.TOP_ALIGNMENT);

				descripAux.add(textAreaDesc);
				ventaPanel.add(textAreaDesc);

				Window window = SwingUtilities.getWindowAncestor(ventaPanel);
				if (window instanceof JDialog) {
					JDialog dialog = (JDialog) window;
					dialog.pack();
				}

				ventaPanel.revalidate();
				ventaPanel.repaint();

			}
		});

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, catalogoScrollPane, ventaPanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(500);

		int result = JOptionPane.showConfirmDialog(panel, splitPane, "Catálogo de productos y Nueva venta", JOptionPane.OK_CANCEL_OPTION);

		// Verificar si se hizo clic en OK
		if (result == JOptionPane.OK_OPTION) {
			//TODO COnseguir descripcion de alguna manera
			descripcionAux = descripAux.get(0).getText();
			imprimirContenidoCeldasYChoices();
			procesarVentas();
			descripAux.clear();
		}
	}

	//este
	public void imprimirContenidoCeldasYChoices() {
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


	public void procesarVentas() throws DBException {

		for (int i = 0; i < idsParaVentas.size();i++) {

			LocalDate fechaActual = LocalDate.now();

			// Imprimir la fecha actual en un formato específico
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


	public void crearServicio() {

		if (textFieldNombreServicio .getText().intern() != "" && textFieldPrecioServicio.getText().intern() != "" && textAreaDescripcionServicio.getText().intern() != "" 
				&& Float.parseFloat(textFieldPrecioServicio.getText().intern())>0) {

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

		} else {
			JOptionPane.showMessageDialog(null, "No puede haber un campo vacio ni precio menor de 0");
		}

	}
}
