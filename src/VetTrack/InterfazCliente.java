package VetTrack;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JScrollBar;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import java.awt.Choice;
import java.awt.GridLayout;



public class InterfazCliente {

	public JFrame frame;
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

	/**
	 * Create the application.
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public InterfazCliente(Interfaz interfaz) throws NumberFormatException, Exception {
		this.interfaz = interfaz;

		initialize_cliente();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	private void initialize_cliente() throws NumberFormatException, Exception {
		frame = new JFrame();
		frame.setTitle("Panel de control de " + this.interfaz.verDatosUsuarioActivo().getNombreUsuario());

		frame.setBounds(300, 300, 1200, 900);
		frame.setVisible(true);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		//		frame.getContentPane().setBackground(new Color(150, 150, 150));

		JButton botCerrarSesion = new JButton("Cerrar Sesion");
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
		//		botCerrarSesion.setForeground(Color.red);
		colorOriginalBton = botCerrarSesion.getBackground();

		botCerrarSesion.setBounds(1038, 11, 136, 59);
		frame.getContentPane().add(botCerrarSesion);

		JToggleButton botModoNoct = new JToggleButton("Modo Nocturno");

		botModoNoct.setBounds(897, 29, 131, 23);
		frame.getContentPane().add(botModoNoct);

		JButton botVerPerfil = new JButton("Ver Perfil");
		botVerPerfil.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Esto es por ahora
				JOptionPane.showMessageDialog(null, "Id del usuario: "+interfaz.verDatosUsuarioActivo().getIdUsuario() + ""
						+ "\nUsuario: "+interfaz.verDatosUsuarioActivo().getNombreUsuario()+""
						+ "\nContraseña: "+interfaz.verDatosUsuarioActivo().getContrasena() + ""
						+ "\nRol: "+ interfaz.verDatosUsuarioActivo().getRol());
			}
		});
		botVerPerfil.setBounds(10, 11, 136, 51);
		frame.getContentPane().add(botVerPerfil);

		JPanel panelDatosMascotas = new JPanel(new GridLayout(0, 4, 10, 10)); // 3 filas, 4 columnas
		panelDatosMascotas.setBounds(674, 350, 500, 500);
		frame.getContentPane().add(panelDatosMascotas);		

		JLabel labelIdMascota = new JLabel("IdMascota: ");
		JLabel labelNombreMascota = new JLabel("Nombre: ");
		JLabel labelEspecieMascota = new JLabel("Especie: ");
		JLabel labelRazaMascota = new JLabel("Raza: ");
		JLabel labelFechaNacimientoMascota = new JLabel("Fecha Nacimiento: ");
		JLabel labelIdUsuarioMascota = new JLabel("Nombre Dueño: ");

		// JTextFields no editables y con 15 columnas
		textFieldIdMascota = new JTextField();
		textFieldIdMascota.setEditable(false);
		textFieldIdMascota.setColumns(15);

		textFieldNombreMascota = new JTextField();
		textFieldNombreMascota.setEditable(false);
		textFieldNombreMascota.setColumns(15);

		textFieldEspecieMascota = new JTextField();
		textFieldEspecieMascota.setEditable(false);
		textFieldEspecieMascota.setColumns(15);

		textFieldRazaMascota = new JTextField();
		textFieldRazaMascota.setEditable(false);
		textFieldRazaMascota.setColumns(15);

		textFieldFechaNacimientoMascota = new JTextField();
		textFieldFechaNacimientoMascota.setEditable(false);
		textFieldFechaNacimientoMascota.setColumns(15);

		textFieldIdUsuarioMascota = new JTextField();
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


		JLabel lblNewLabel = new JLabel("Mascotas");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(820, 281, 100, 20);
		frame.getContentPane().add(lblNewLabel);

		choiceMascotas = new Choice();
		choiceMascotas.setBounds(675, 324, 200, 200);

		choiceMascotas.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if(choiceMascotas.getSelectedIndex()!= 0) {
						// Llamas al método que deseas ejecutar
						try {
							mostrarDatosMascota();
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
					}
				}
			}
		});


		frame.getContentPane().add(choiceMascotas);

		choiceMascotas.add("");
		establecerMascotas();

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				confirmarSalir(frame);
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

		Component[] components = frame.getContentPane().getComponents();

		for (Component component : components) {
			if (component instanceof JButton) {

				JButton boton = (JButton) component;
				boton.setBackground(botModoNoct.isSelected() ? new Color(150, 150, 150) : this.colorOriginalBton);

			}

			//            if (component instanceof JToggleButton) {
			//            	
			//            	JToggleButton toggle = (JToggleButton) component;
			//            	
			//            	toggle.setBackground(botModoNoct.isSelected() ? new Color(150, 150, 150) : this.colorOriginalBton);
			//            	
			//            }

		}

		frame.getContentPane().setBackground(botModoNoct.isSelected() ? new Color(50, 50, 50) : this.colorOriginalBton);



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

		System.out.println("numMasc: " + numMascotas);

		int idUsuario = interfaz.getUser().getIdUsuario();

		List<String> lista = conexion.obtenerDatosDeTablaLista("Mascota", "nombre", "idUsuario", idUsuario);

		for(int i = 0; i < numMascotas; i++) {

			choiceMascotas.add(lista.get(i));

		}

	}

	public void mostrarDatosMascota() throws Exception {

		String nombreMascota = choiceMascotas.getSelectedItem();

		int idUsuario = interfaz.getUser().getIdUsuario();


		String idMascota = conexion.obtenerDatoDeTablaConDosCondiciones("Mascota", "idMascota", "nombre", nombreMascota, "idUsuario", idUsuario);
		String especie = conexion.obtenerDatoDeTablaConDosCondiciones("Mascota", "especie", "nombre", nombreMascota, "idUsuario", idUsuario);
		String raza = conexion.obtenerDatoDeTablaConDosCondiciones("Mascota", "raza", "nombre", nombreMascota, "idUsuario", idUsuario);
		String fechaNacimiento = conexion.obtenerDatoDeTablaConDosCondiciones("Mascota", "fechaNacimiento", "nombre", nombreMascota, "idUsuario", idUsuario);
		String dueno = conexion.obtenerDatoDeTabla("Cliente", "nombre", "idUsuario", idUsuario);

		textFieldIdMascota.setText(idMascota);
		textFieldNombreMascota.setText(nombreMascota);
		textFieldEspecieMascota.setText(especie);
		textFieldRazaMascota.setText(raza);
		textFieldFechaNacimientoMascota.setText(fechaNacimiento);
		textFieldIdUsuarioMascota.setText(dueno);

		System.out.println("Id mascota: " + idMascota);



	}

}
