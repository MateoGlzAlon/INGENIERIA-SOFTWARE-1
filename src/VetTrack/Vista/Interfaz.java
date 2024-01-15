package VetTrack.Vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

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

import VetTrack.Controlador.Usuario;
import VetTrack.Modelo.ConexionBD;

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

			if (idUsuarioEncontrado != -1) {

				String rolUsuario = conexion.obtenerDatoDeTabla("Usuario", "rol", "idUsuario", idUsuarioEncontrado);

				this.user = new Usuario(idUsuarioEncontrado, usuario, passwd, rolUsuario);

				if (rolUsuario.equals("Cliente")) {
					frame.setVisible(false);
				} else if (rolUsuario.equals("Administrador")) {
					frame.setVisible(false);
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

		for (List<Object> user : userList) {
			if (user.get(1).toString().equals(usuario) && user.get(2).toString().equals(passwd)) {
				return (int) user.get(0);
			}
		}

		return -1;
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

}
