package VetTrack.Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import javax.swing.JTextPane;
import javax.swing.JTextArea;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import Exceptions.DBException;
import VetTrack.Controlador.ManejadorAdministrador;
import VetTrack.Modelo.ConexionBD;

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

	private String descripcionAux;
	private List<JTextArea> descripAux;
	private JTextField textFieldNombreServicio;
	private JTextField textFieldPrecioServicio;
	
	private ManejadorAdministrador manejAdm;

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
		this.manejAdm = new ManejadorAdministrador();
		
		frame = new JFrame();

		frame.setTitle("Panel de control de Administrador");

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
					frame.dispose(); 
				}
			}
		});

		colorOriginalBton = botCerrarSesion.getBackground();

		botCerrarSesion.setBounds(1038, 11, 136, 59);
		frame.getContentPane().add(botCerrarSesion);

		JToggleButton botModoNoct = new JToggleButton("Modo Nocturno");
		botModoNoct.setFont(new Font("Tahoma", Font.PLAIN, 12));

		botModoNoct.setBounds(897, 11, 131, 59);
		frame.getContentPane().add(botModoNoct);

		JButton botVerPerfil = new JButton("Ver Perfil");
		botVerPerfil.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botVerPerfil.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					manejAdm.verPerfil(interfaz);
				} catch (DBException e1) {
					e1.printStackTrace();
				}
			}
		});

		botVerPerfil.setBounds(10, 11, 136, 51);
		frame.getContentPane().add(botVerPerfil);

		JButton botCrearUsuario = new JButton("Crear Usuario");
		botCrearUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botCrearUsuario.setBounds(167, 11, 140, 50);
		frame.getContentPane().add(botCrearUsuario);
		botCrearUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manejAdm.createUser(frame);
			}
		});

		JButton botQuitarUsuario = new JButton("Eliminar Usuario");
		botQuitarUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botQuitarUsuario.setBounds(310, 11, 140, 50);
		frame.getContentPane().add(botQuitarUsuario);
		botQuitarUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manejAdm.deleteUser();
			}
		});


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
				manejAdm.buscarUsuario(textUserBuscar, panelTextUser, textPaneCitasPrevias, textPaneCitasFuturas);
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

				if (textAreaArticulo.getText().intern() != "" && textPrecioArticulo.getText().intern() != "" && textNombreArticulo.getText().intern() != "" 
						&& textMarcaArticulo.getText().intern() != "" && Float.parseFloat(textPrecioArticulo.getText().intern())>0) {
					
					System.out.println("1 " + textAreaArticulo.getText());
					System.out.println("2 " + textPrecioArticulo.getText());
					System.out.println("3 " + textNombreArticulo.getText());
					System.out.println("4 " + textMarcaArticulo.getText());
					System.out.println("5 " + textPrecioArticulo.getText());
					
					
					manejAdm.crearArticulo(textNombreArticulo, textPrecioArticulo, textMarcaArticulo, textAreaArticulo);
					
				}else {
					JOptionPane.showMessageDialog(null, "No puede haber un campo vacio ni precio menor de 0");
				}
				
				

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

				manejAdm.crearCita(textUserCita, textMascCita, textFechaCita, textHoraCita, panelDescrCita);

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
					e1.printStackTrace();
				} catch (DBException e1) {
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
		labelIcono.setBounds(707, 11, 125, 125);
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

				if (textFieldNombreServicio .getText().intern() != "" && textFieldPrecioServicio.getText().intern() != "" && textAreaDescripcionServicio.getText().intern() != "" 
						&& Float.parseFloat(textFieldPrecioServicio.getText().intern())>0) {

					manejAdm.crearServicio(textFieldNombreServicio,textFieldPrecioServicio, textAreaDescripcionServicio);

				} else {
					JOptionPane.showMessageDialog(null, "No puede haber un campo vacio ni precio menor de 0");
				}

			}
		});
		botCrearServicio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botCrearServicio.setBounds(302, 513, 89, 23);
		frame.getContentPane().add(botCrearServicio);
		
		JButton botModificarContrasena = new JButton("Modificar contraseña");
		botModificarContrasena.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					manejAdm.modificarContraseña(frame);
				} catch (DBException | SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		botModificarContrasena.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botModificarContrasena.setBounds(452, 11, 150, 50);
		frame.getContentPane().add(botModificarContrasena);


		celdas = new ArrayList<>();
		choices = new ArrayList<>();
		idsParaVentas = new ArrayList<>();
		tiposParaVentas = new ArrayList<>();
		descripAux = new ArrayList<>();

		botAddMasc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(textUserBuscar.getText().intern() != "") {
					manejAdm.agregarMascota(textUserBuscar, panelTextUser);
				} else {
					JOptionPane.showMessageDialog(null, "No hay un usuario seleccionado");
				}
			}
		});

		botActualizarUserBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manejAdm.buscarUsuario(textUserBuscar, panelTextUser, textPaneCitasPrevias, textPaneCitasFuturas);
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


	//Este no lo muevo porque es parte de la interfaz
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

		JScrollPane catalogoScrollPane = new JScrollPane(textArea);
		catalogoScrollPane.setPreferredSize(new Dimension(500, 600));

		JPanel ventaPanel = new JPanel();
		JLabel labelNumArt = new JLabel("Número de artículos");

		JComboBox<String> choice = new JComboBox<>();
		for (int i = 1; i <= 9; i++) {
			choice.addItem(String.valueOf(i));
		}

		ventaPanel.setLayout(new BoxLayout(ventaPanel, BoxLayout.Y_AXIS));
		ventaPanel.add(Box.createVerticalGlue()); // Espacio en la parte superior

		JPanel labelPanel = new JPanel();
		labelPanel.add(labelNumArt);
		ventaPanel.add(labelPanel);

		JPanel choicePanel = new JPanel();
		choicePanel.add(choice);
		ventaPanel.add(choicePanel);

		choice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int numeroCeldas = Integer.parseInt((String) choice.getSelectedItem());

				celdas.clear();
				choices.clear();

				ventaPanel.removeAll();

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

		if (result == JOptionPane.OK_OPTION) {
			descripcionAux = descripAux.get(0).getText();
			this.manejAdm.imprimirContenidoCeldasYChoices(celdas, idsParaVentas, choices, tiposParaVentas);
			this.manejAdm.procesarVentas(idsParaVentas, tiposParaVentas , descripcionAux, textUserBuscar);
			descripAux.clear();
		}
	}
	
}
