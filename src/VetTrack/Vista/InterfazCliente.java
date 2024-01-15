package VetTrack.Vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.Choice;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Font;

import Exceptions.DBException;
import VetTrack.Controlador.ManejadorCliente;

public class InterfazCliente {

	public JFrame frmInterfazDelCliente;
	private Interfaz interfaz;
	private Color colorOriginalBton;

	private Choice choiceMascotas;

	private JTextField textFieldIdMascota;
	private JTextField textFieldNombreMascota;
	private JTextField textFieldEspecieMascota;
	private JTextField textFieldRazaMascota;
	private JTextField textFieldFechaNacimientoMascota;
	private JTextField textFieldIdUsuarioMascota;
	private JTextPane textPaneCitasPrevias;
	private JTextPane textPaneCitasFuturas;
	private JTextArea textPaneComprasRealizadas;
	private JButton botonActualizarCitas;

	private ManejadorCliente manejadorCliente;

	/**
	 * Create the application.
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public InterfazCliente(Interfaz interfaz) throws NumberFormatException, Exception {
		this.interfaz = interfaz;

		initialize_cliente();
		manejadorCliente.rellenarComprasPrevias(interfaz, textPaneComprasRealizadas);
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	private void initialize_cliente() throws NumberFormatException, Exception {

		manejadorCliente = new ManejadorCliente();

		frmInterfazDelCliente = new JFrame();
		frmInterfazDelCliente.setTitle("Interfaz del Cliente: " + this.interfaz.getUser().getNombreUsuario());

		frmInterfazDelCliente.setBounds(300, 300, 1200, 900);
		frmInterfazDelCliente.setVisible(true);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frmInterfazDelCliente.setLocationRelativeTo(null);
		frmInterfazDelCliente.setResizable(false);

		frmInterfazDelCliente.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frmInterfazDelCliente.getContentPane().setLayout(null);

		JButton botCerrarSesion = new JButton("Cerrar Sesión");
		botCerrarSesion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int confirmacion = JOptionPane.showConfirmDialog(frmInterfazDelCliente, "Quieres cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION);

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
					manejadorCliente.mostrarPerfil(interfaz);
				} catch (Exception e1) {
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
		textFieldIdMascota.setBackground(new Color(255, 255, 255));
		textFieldIdMascota.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldIdMascota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldIdMascota.setEditable(false);
		textFieldIdMascota.setColumns(15);

		textFieldNombreMascota = new JTextField();
		textFieldNombreMascota.setBackground(new Color(255, 255, 255));
		textFieldNombreMascota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldNombreMascota.setEditable(false);
		textFieldNombreMascota.setColumns(15);

		textFieldEspecieMascota = new JTextField();
		textFieldEspecieMascota.setBackground(new Color(255, 255, 255));
		textFieldEspecieMascota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldEspecieMascota.setEditable(false);
		textFieldEspecieMascota.setColumns(15);

		textFieldRazaMascota = new JTextField();
		textFieldRazaMascota.setBackground(new Color(255, 255, 255));
		textFieldRazaMascota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldRazaMascota.setEditable(false);
		textFieldRazaMascota.setColumns(15);

		textFieldFechaNacimientoMascota = new JTextField();
		textFieldFechaNacimientoMascota.setBackground(new Color(255, 255, 255));
		textFieldFechaNacimientoMascota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldFechaNacimientoMascota.setEditable(false);
		textFieldFechaNacimientoMascota.setColumns(15);

		textFieldIdUsuarioMascota = new JTextField();
		textFieldIdUsuarioMascota.setBackground(new Color(255, 255, 255));
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
							manejadorCliente.mostrarDatosMascota(interfaz, choiceMascotas, textFieldIdMascota, textFieldNombreMascota,textFieldEspecieMascota, textFieldRazaMascota, textFieldFechaNacimientoMascota, textFieldIdUsuarioMascota);

							manejadorCliente.rellenarCitasPasadas(interfaz, choiceMascotas, textPaneCitasPrevias);
							manejadorCliente.rellenarCitasFuturas(interfaz, choiceMascotas, textPaneCitasFuturas);
						} catch (Exception e1) {
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

		textPaneComprasRealizadas = new JTextArea();

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
					manejadorCliente.rellenarComprasPrevias(interfaz, textPaneComprasRealizadas);
				} catch (Exception e1) {
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
		labelIcono.setBounds(30, 111, 175, 175);
		frmInterfazDelCliente.getContentPane().add(labelIcono);

		botonActualizarCitas = new JButton("ACTUALIZAR");
		botonActualizarCitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					if(choiceMascotas.getSelectedIndex() != 0) {
						manejadorCliente.rellenarCitasPasadas(interfaz, choiceMascotas, textPaneCitasPrevias);
						manejadorCliente.rellenarCitasFuturas(interfaz, choiceMascotas, textPaneCitasFuturas);	
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		botonActualizarCitas.setBounds(975, 97, 180, 23);
		frmInterfazDelCliente.getContentPane().add(botonActualizarCitas);

		JToggleButton botonCatalogo = new JToggleButton("Ver catálogo");
		botonCatalogo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		botonCatalogo.setBounds(400, 208, 180, 60);
		frmInterfazDelCliente.getContentPane().add(botonCatalogo);

		botonCatalogo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					manejadorCliente.mostrarCatalogo();
				} catch (DBException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		choiceMascotas.add("");
		manejadorCliente.establecerMascotas(interfaz, choiceMascotas);

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

			} else if (component instanceof JLabel) {
				JLabel labl = (JLabel) component;
				labl.setForeground(botModoNoct.isSelected() ? Color.white : Color.black);

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

}
