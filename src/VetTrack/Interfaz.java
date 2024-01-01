package VetTrack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

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

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		panel.setBackground(new Color(50, 50, 50));

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel textMain = new JLabel("Login");
		textMain.setForeground(Color.white);
		textMain.setFont(new Font("Arial", Font.PLAIN, 40));
		GridBagConstraints gbc_textMain = new GridBagConstraints();
		gbc_textMain.gridwidth = 2;
		gbc_textMain.insets = new Insets(0, 0, 5, 5);
		gbc_textMain.gridx = 5;
		gbc_textMain.gridy = 3;
		panel.add(textMain, gbc_textMain);

		JLabel labelTextUser = new JLabel("Username:");
		labelTextUser.setForeground(Color.white);
		GridBagConstraints gbc_labelTextUser = new GridBagConstraints();
		gbc_labelTextUser.insets = new Insets(0, 0, 5, 5);
		gbc_labelTextUser.gridx = 4;
		gbc_labelTextUser.gridy = 5;
		panel.add(labelTextUser, gbc_labelTextUser);

		textUser = new JTextField();
		GridBagConstraints gbc_textUser = new GridBagConstraints();
		gbc_textUser.gridwidth = 8;
		gbc_textUser.insets = new Insets(0, 0, 5, 5);
		gbc_textUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_textUser.gridx = 5;
		gbc_textUser.gridy = 5;
		panel.add(textUser, gbc_textUser);
		textUser.setColumns(10);

		JLabel labelTextPass = new JLabel("Password:");
		labelTextPass.setForeground(Color.white);
		GridBagConstraints gbc_labelTextPass = new GridBagConstraints();
		gbc_labelTextPass.anchor = GridBagConstraints.EAST;
		gbc_labelTextPass.insets = new Insets(0, 0, 5, 5);
		gbc_labelTextPass.gridx = 4;
		gbc_labelTextPass.gridy = 7;
		panel.add(labelTextPass, gbc_labelTextPass);

		textPasswd = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 8;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 5;
		gbc_passwordField.gridy = 7;
		panel.add(textPasswd, gbc_passwordField);

		JButton botonAccept = new JButton("Aceptar");
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

		GridBagConstraints gbc_botonAccept = new GridBagConstraints();
		gbc_botonAccept.insets = new Insets(0, 0, 5, 5);
		gbc_botonAccept.gridx = 12;
		gbc_botonAccept.gridy = 9;
		panel.add(botonAccept, gbc_botonAccept);
	}

	private void comprobarUsuarioYContraseña(String usuario, String passwd, JFrame frame) {
	    try {
	    	
	    	//Lo he movido a otro metodo para poder usarlo mas tarde
	        int idUsuarioEncontrado = recogerIdUsuario(usuario, passwd);

	        // Si se encuentra el usuario, continuar
	        if (idUsuarioEncontrado != -1) {
	            // Obtener el rol del usuario
	            String rolUsuario = conexion.obtenerDatoDeTabla("Usuario", "rol", "idUsuario", idUsuarioEncontrado);

	            //recogerDatosTablaUsuarioCorresp(rolUsuario, idUsuarioEncontrado);

	            this.user = new Usuario(idUsuarioEncontrado, usuario, passwd, rolUsuario);

	            if (rolUsuario.equals("Cliente")) {
	                frame.setVisible(false);

//	                JOptionPane.showMessageDialog(null, "Has iniciado sesión como Cliente (" + usuario + ")");
	                InterfazCliente frameImp = new InterfazCliente(this);
	                
	            } else if (rolUsuario.equals("Administrador")) {
	                frame.setVisible(false);

	                JOptionPane.showMessageDialog(null, "Has iniciado sesión como Administrador (" + usuario + ")");
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
	
	public String cogerDatosBorrar(String username) throws Exception {
		
		if (username == "") {
			return "No se ha encontrado el usuario";
		}
		
		List<List<Object>> userList = conexion.listar("Usuario");

        for (List<Object> user : userList) {
            if (user.get(1).toString().equals(username)) {
                return "ID: "+ user.get(0).toString() + "\nUsername: "+user.get(1).toString()+"\nRol: "+user.get(3).toString();
            }
        }
		
		return "No se ha encontrado el usuario";
		
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
