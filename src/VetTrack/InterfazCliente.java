package VetTrack;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Choice;
import java.awt.GridLayout;

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
		frmInterfazDelCliente = new JFrame();
		frmInterfazDelCliente.setTitle("Interfaz del Cliente: " + this.interfaz.getUser().getNombreUsuario());

		frmInterfazDelCliente.setBounds(300, 300, 1200, 900);
		frmInterfazDelCliente.setVisible(true);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frmInterfazDelCliente.setLocationRelativeTo(null);
		frmInterfazDelCliente.setResizable(false);

		frmInterfazDelCliente.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frmInterfazDelCliente.getContentPane().setLayout(null);

		//		frame.getContentPane().setBackground(new Color(150, 150, 150));

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

		botCerrarSesion.setBounds(1038, 11, 136, 59);
		frmInterfazDelCliente.getContentPane().add(botCerrarSesion);

		JToggleButton botModoNoct = new JToggleButton("Modo Nocturno");

		botModoNoct.setBounds(897, 29, 131, 23);
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
		panelDatosMascotas.setBounds(645, 165, 500, 150);
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
		choiceMascotas.setBounds(673, 139, 250, 30);

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
							rellenarComprasPrevias();
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
		textPaneCitasPrevias.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textPaneCitasPrevias.setBounds(673, 350, 225, 500);
		textPaneCitasPrevias.setBorder(new LineBorder(Color.BLACK, 1));
		textPaneCitasPrevias.setEditable(false);

        JScrollPane scrollPaneCitasPrevias = new JScrollPane(textPaneCitasPrevias);
        scrollPaneCitasPrevias.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneCitasPrevias.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneCitasPrevias.setBounds(645, 350, 500, 225);
        
        frmInterfazDelCliente.getContentPane().add(scrollPaneCitasPrevias);
		
		textPaneCitasFuturas = new JTextPane();
		textPaneCitasFuturas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textPaneCitasFuturas.setEditable(false);
		textPaneCitasFuturas.setBorder(new LineBorder(Color.BLACK, 1));
		textPaneCitasFuturas.setBounds(674, 625, 500, 225);
		
        JScrollPane scrollPaneCitasFuturas = new JScrollPane(textPaneCitasFuturas);
        scrollPaneCitasFuturas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneCitasFuturas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneCitasFuturas.setBounds(645, 625, 500, 225);
        
        frmInterfazDelCliente.getContentPane().add(scrollPaneCitasFuturas);
		
		JLabel labelCitasPrevias = new JLabel("CITAS PREVIAS");
		labelCitasPrevias.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelCitasPrevias.setHorizontalAlignment(SwingConstants.CENTER);
		labelCitasPrevias.setBounds(848, 325, 150, 14);
		frmInterfazDelCliente.getContentPane().add(labelCitasPrevias);
		
		JLabel labelCitasFuturas = new JLabel("CITAS FUTURAS");
		labelCitasFuturas.setHorizontalAlignment(SwingConstants.CENTER);
		labelCitasFuturas.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelCitasFuturas.setBounds(848, 595, 150, 14);
		frmInterfazDelCliente.getContentPane().add(labelCitasFuturas);
		
		JScrollPane scrollPaneComprasRealizadas = new JScrollPane((Component) null);
		scrollPaneComprasRealizadas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneComprasRealizadas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneComprasRealizadas.setBounds(42, 350, 500, 500);
		frmInterfazDelCliente.getContentPane().add(scrollPaneComprasRealizadas);
		
		JTextPane textPaneComprasRealizadas = new JTextPane();
		textPaneComprasRealizadas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textPaneComprasRealizadas.setEditable(false);
		textPaneComprasRealizadas.setBorder(new LineBorder(Color.BLACK, 1));
		scrollPaneComprasRealizadas.setViewportView(textPaneComprasRealizadas);
		
		JLabel labelComprasPrevias = new JLabel("COMPRAS PREVIAS");
		labelComprasPrevias.setHorizontalAlignment(SwingConstants.CENTER);
		labelComprasPrevias.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelComprasPrevias.setBounds(198, 327, 150, 14);
		frmInterfazDelCliente.getContentPane().add(labelComprasPrevias);

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
	}

	
	public void mostrarPerfil() throws Exception {

		int idUsuario = interfaz.verDatosUsuarioActivo().getIdUsuario();
		String nombreUsuario = interfaz.verDatosUsuarioActivo().getNombreUsuario();
		String contrasena = interfaz.verDatosUsuarioActivo().getContrasena();
		String rolUsuario = interfaz.verDatosUsuarioActivo().getRol();
		String nombre = conexion.obtenerDatoDeTabla("Cliente", "nombre", "idUsuario", idUsuario);
		String apellidos = conexion.obtenerDatoDeTabla("Cliente", "apellidos", "idUsuario", idUsuario);
		String dni = conexion.obtenerDatoDeTabla("Cliente", "dni", "idUsuario", idUsuario);
		String telefono = conexion.obtenerDatoDeTabla("Cliente", "telefono", "idUsuario", idUsuario);

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

		List<Cita> citasPasadas = conexion.obtenerCitasPasadas(Integer.parseInt(idMascota));

		textPaneCitasPrevias.setText("");

		String cadenaCitasPrevias = "";
		
		for(int i = 0; i < citasPasadas.size(); i++) {
			
			
			cadenaCitasPrevias += " IdCita: "+ citasPasadas.get(i).getIdCita()+ ""
					+ "\n FechaCita:  "  + new SimpleDateFormat("dd / mm / yyyy").format(citasPasadas.get(i).getFechaCita())
					+ "\n HoraCita: " + new SimpleDateFormat("HH:mm").format(citasPasadas.get(i).getHoraCita())
					+ "\n Descripcion: " + citasPasadas.get(i).getDescripcion();
			cadenaCitasPrevias+= "\n____________________________________________________________________";
		}
		
//		for(int i = 0; i < 20; i++) {
//			cadenaCitasPrevias+="\nholahola hola holalalalaholahoala hola hola";
//		}
		
		textPaneCitasPrevias.setText(cadenaCitasPrevias);
	}
	
	
	public void rellenarCitasFuturas() throws Exception {

		String nombreMascota = choiceMascotas.getSelectedItem();
		int idUsuario = interfaz.getUser().getIdUsuario();
		String idMascota = conexion.obtenerDatoDeTablaConDosCondiciones("Mascota", "idMascota", "nombre", nombreMascota, "idUsuario", idUsuario);

		List<Cita> citasPasadas = conexion.obtenerCitasFuturas(Integer.parseInt(idMascota));

		textPaneCitasFuturas.setText("");

		String cadenaCitasFuturas = "";		
		
		for(int i = 0; i < citasPasadas.size(); i++) {
			cadenaCitasFuturas += " IdCita: "+ citasPasadas.get(i).getIdCita()+ ""
					+ "\n FechaCita:  "  + new SimpleDateFormat("dd / mm / yyyy").format(citasPasadas.get(i).getFechaCita())
					+ "\n HoraCita: " + new SimpleDateFormat("HH:mm").format(citasPasadas.get(i).getHoraCita())
					+ "\n Descripcion: " + citasPasadas.get(i).getDescripcion();
			cadenaCitasFuturas+= "\n____________________________________________________________________";
		}

//		for(int i = 0; i < 20; i++) {
//			cadenaCitasFuturas 	 +="\nholahola hola holalalalaholahoala hola hola";
//		}
		
		textPaneCitasFuturas.setText(cadenaCitasFuturas);

	}
	
	
	public void rellenarComprasPrevias() {
	
		
		
	}
	
}
