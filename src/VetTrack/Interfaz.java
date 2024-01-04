package VetTrack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.SwingConstants;

public class Interfaz {

	// Usuario
	private Usuario user; // Para hacer un singleton

	public JFrame frame;
	private JTextField textUser;
	private JPasswordField textPasswd;
	private ConexionBD conexion = ConexionBD.getInstance();

	/**
	 * Create the application.
	 */
	public Interfaz() {
		initialize();
	}

	public Usuario getUser() {
		return this.user;
	}
	
	public ConexionBD getConexion() {
		return this.conexion;
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 650, 500);
		frame.setTitle("Login");

		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				try {
					confirmarSalir(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 634, 461);
		frame.getContentPane().add(panel);

		panel.setBackground(new Color(50, 50, 50));
		panel.setLayout(null);


		JLabel textMain = new JLabel("VetTrack");
		textMain.setHorizontalAlignment(SwingConstants.CENTER);
		textMain.setBounds(219, 133, 209, 47);
		textMain.setForeground(Color.white);
		textMain.setFont(new Font("Arial", Font.PLAIN, 40));
		panel.add(textMain);

		JLabel labelTextUser = new JLabel("Username:");
		labelTextUser.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelTextUser.setBounds(112, 215, 82, 14);
		labelTextUser.setForeground(Color.white);
		panel.add(labelTextUser);

		textUser = new JTextField();
		textUser.setBounds(204, 212, 300, 20);
		panel.add(textUser);
		textUser.setColumns(10);

		JLabel labelTextPass = new JLabel("Password:");
		labelTextPass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelTextPass.setBounds(114, 270, 80, 14);
		labelTextPass.setForeground(Color.white);
		panel.add(labelTextPass);

		textPasswd = new JPasswordField();
		textPasswd.setBounds(204, 267, 300, 20);
		panel.add(textPasswd);

		JButton botonAccept = new JButton("Aceptar");
		botonAccept.setBounds(404, 323, 100, 23);
		botonAccept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					comprobarUsuarioYContraseña(textUser.getText(), new String(textPasswd.getPassword()), frame);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(botonAccept);

		ImageIcon icon = new ImageIcon("etc/IMAGENES/logo_VetTrack.png");
		
		Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        ImageIcon scaledIcon = new ImageIcon(scaledImage);
		
		
		JLabel labelIcono = new JLabel(scaledIcon);
		labelIcono.setBounds(490, 40, 100, 100);
		panel.add(labelIcono);

	}

	private void comprobarUsuarioYContraseña(String usuario, String passwd, JFrame frame) {
		try {

			int idUsuarioEncontrado = recogerIdUsuario(usuario, passwd);

			// Si se encuentra el usuario, continuar
			if (idUsuarioEncontrado != -1) {
				
				String rolUsuario = conexion.obtenerDatoDeTabla("Usuario", "rol", "idUsuario", idUsuarioEncontrado);

				this.user = new Usuario(idUsuarioEncontrado, usuario, passwd, rolUsuario);

				if (rolUsuario.equals("Cliente")) {
					frame.setVisible(false);

					InterfazCliente frameImp = new InterfazCliente(this);

				} else if (rolUsuario.equals("Administrador")) {
					frame.setVisible(false);

					InterfazAdministrador frameImp = new InterfazAdministrador(this);

				}
			} else {
				JOptionPane.showMessageDialog(null, "Las credenciales son incorrectas");
				setText();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al comprobar usuario y contraseña: " + e.getMessage());
		}
	}


	public int recogerIdUsuario(String usuario, String passwd) throws Exception{
		// Obtener lista de usuarios
		List<List<Object>> userList = conexion.listar("Usuario");

		// Buscar el usuario por usuario y contraseña
		for (List<Object> user : userList) {
			if (user.get(1).toString().equals(usuario) && user.get(2).toString().equals(passwd)) {
				return (int) user.get(0);
			}
		}

		return -1;
	}

	/*
	 * Aqui sencillamente lo que vamos a hacer es devolver la lista con los parametros de la DB
	 */
	public List<Object> recupPosTablaCorrespondiente(String rolUsuario, int idUsuarioEncontrado) throws Exception {
		// Obtener datos específicos según el rol
		List<List<Object>> userDataList = conexion.listar(rolUsuario);

		// Buscar la posición del usuario en la tabla correspondiente
		int posTablaDatos = 0;

		for (int i = 0; i < userDataList.size(); i++) {
			if (userDataList.get(i).get(0).toString().equals(String.valueOf(idUsuarioEncontrado))) {
				posTablaDatos = i;
				break;
			}
		}

		return conexion.listar(rolUsuario).get(posTablaDatos);

	}

	public List<Object> cogerDatosBorrar(String username) throws Exception {

		if (username == "") {
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
	
	public ArrayList<String> recTodasMascotas(int id) throws NumberFormatException, Exception {
		
		int numMascotas = Integer.parseInt(conexion.obtenerDatoDeTabla("Cliente", "numMascotas", "idUsuario", id));

		List<String> lista = conexion.obtenerDatosDeTablaLista("Mascota", "nombre", "idUsuario", id);
		
		ArrayList<String> mascotasLista = new ArrayList<String>();

		for(int i = 0; i < numMascotas; i++) {

			mascotasLista.add(lista.get(i).toString().intern());

		}
		
		return mascotasLista;

	}
	
	public int idMascotaRecuperar(String nombreMascota, int idUsuario) throws Exception{
		
		List<String> columnas = Arrays.asList("idMascota", "especie", "raza", "fechaNacimiento");
		String condicion = "nombre = ? AND idUsuario = ?";

		Object[] parametros = {nombreMascota, idUsuario};

		List<Map<String, Object>> resultados = conexion.obtenerFilasDeTabla("Mascota", columnas, condicion, parametros);

		if (!resultados.isEmpty()) {
			Map<String, Object> datosMascota = resultados.get(0);
			
			return (int) datosMascota.get("idMascota");
		}
		
		return -1;
	}
	
	public String mascRecDatos(String nombreMascota, int idUsuario) throws Exception {

		List<String> columnas = Arrays.asList("idMascota", "especie", "raza", "fechaNacimiento");
		String condicion = "nombre = ? AND idUsuario = ?";

		Object[] parametros = {nombreMascota, idUsuario};

		List<Map<String, Object>> resultados = conexion.obtenerFilasDeTabla("Mascota", columnas, condicion, parametros);

		if (!resultados.isEmpty()) {
			Map<String, Object> datosMascota = resultados.get(0);
			
			return "\nID: " + String.valueOf(datosMascota.get("idMascota")) + "\nNombre: "+ nombreMascota +"\nEspecie: " + String.valueOf(datosMascota.get("especie")) + "\nRaza: " + String.valueOf(datosMascota.get("raza")) + "\nFecha de nacimiento: " + String.valueOf(datosMascota.get("fechaNacimiento")) + "\n-------------------\n";

		}

		return "";
	}
	

	private void confirmarSalir(JFrame frame) throws Exception {
		int confirmacion = JOptionPane.showConfirmDialog(frame, "Quieres salir de la aplicacion?", "Confirmar",
				JOptionPane.YES_NO_OPTION);

		if (confirmacion == JOptionPane.YES_OPTION) {
			ConexionBD.getInstance().cerrarConexion();

			frame.dispose();
			System.exit(0);
		}
	}



	public void setText() {
		textPasswd.setText(null);
		textUser.setText(null);
	}

	public Usuario verDatosUsuarioActivo() {
		return this.user;
	}


}
