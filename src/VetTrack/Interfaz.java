package VetTrack;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JPasswordField;

public class Interfaz {

	//usuario
	private Usuario user; //Para hacer un singleton
	
	public JFrame frame;
	private JFrame preguntaExit;
	private JTextField textUser;
	private JPasswordField textPasswd;
	private JButton botonYes;
	private JButton botonNo;
	
	/**
	 * Create the application.
	 */
	public Interfaz() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 650, 500);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setTitle("Login");
		
		//esta parte de codigo es para poder manejar lo de cerrar como tenia yo en mi practica
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				confirmarSalir(frame);
			}
		});
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		panel.setBackground(new Color(50, 50, 50)); //Color gris para la interfaz de login
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel textMain = new JLabel("Login");
		
		textMain.setForeground(Color.white); //Letras blancas
		
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
		
		//Esqueleto boton "aceptar"
		botonAccept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null, "Hello World!!!11!!1");
				comprobarUsuario(textUser.getText(), new String(textPasswd.getPassword()), frame);
			}
		});
		
		GridBagConstraints gbc_botonAccept = new GridBagConstraints();
		gbc_botonAccept.insets = new Insets(0, 0, 5, 5);
		gbc_botonAccept.gridx = 12;
		gbc_botonAccept.gridy = 9;
		panel.add(botonAccept, gbc_botonAccept);
		
		
		
	}
	
	//Aqui falta todavia la comprobacion de usuario/contraseña
	private void comprobarUsuario(String usuario, String passwd, JFrame frame) {
		
		if(usuario.intern() != "" && passwd.intern() != "") {
			
			user = new Usuario(1, "admin", "1234"); //Esto lo hago por ahora para probar cosas
			
			/*
			 * Aqui falta comprobar si los campos pertenece a un usuario de la db
			 */
			
			boolean compr = false;
			
			if (!compr) {
				
				frame.setVisible(false);
				
				//Aqui tendremos que seguramente enviar tambien el usuario con todos los datos como parametro al constructor
				InterfazImportante frameImp = new InterfazImportante(this);
				frameImp.frame.setVisible(true);
				
			} else {
				JOptionPane.showMessageDialog(null, "Las credenciales enviadas no son validas.");
			}
			
		} else {
			JOptionPane.showMessageDialog(null, "Las credenciales enviadas no son validas.");
		}
		
		
	}

	//Esta funcion es para poder manejar si salir o no de la aplicacion
	private void confirmarSalir(JFrame frame) {
        int confirmacion = JOptionPane.showConfirmDialog(frame, "Quieres salir de la aplicacion?", "Confirmar", JOptionPane.YES_NO_OPTION);

        //Para comprobar si dice que si o si no
        if (confirmacion == JOptionPane.YES_OPTION) {
        	
        	//Esto es para cerrar la aplicacion
        	frame.dispose(); //Cierra el frame
            System.exit(0); //es parecido al exit(1) de C
            
        }
    }
	
	//Limpia los campos
	public void setText() {
		textPasswd.setText(null);
		textUser.setText(null);
	}
	
	public Usuario verDatosUsuarioActivo() {
		return this.user;
	}
	
}
