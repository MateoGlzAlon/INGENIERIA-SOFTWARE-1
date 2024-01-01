package VetTrack;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class InterfazAdministrador {

	public JFrame frame;
	private JFrame addUsers;
	private JFrame delUsers;
	private Interfaz interfaz;
	private Color colorOriginalBton;

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
		
		frame.setTitle("Panel de control de " + this.interfaz.verDatosUsuarioActivo().getNombreUsuario());
		
		frame.setBounds(300, 300, 1200, 900);
		frame.setVisible(true);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
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
				//Modificar ver perfil
				
				/*try {
					List<Object> datosUsuario = interfaz.recupPosTablaCorrespondiente("Administrador", 
							interfaz.recogerIdUsuario(interfaz.verDatosUsuarioActivo().getNombreUsuario(), interfaz.verDatosUsuarioActivo().getContrasena()));
					JOptionPane.showMessageDialog(null, "Id del usuario: "+interfaz.verDatosUsuarioActivo().getIdUsuario() + ""
							+ "\nUsuario: "+interfaz.verDatosUsuarioActivo().getNombreUsuario()+""
							+ "\nContraseña: "+interfaz.verDatosUsuarioActivo().getContrasena() + ""
							+ "\nRol: "+ interfaz.verDatosUsuarioActivo().getRol() + ""
							+ "\nDatos:\n"+ datosUsuario.toString());
				} catch (Exception e1) {
					e1.printStackTrace();
				}*/
				
				JOptionPane.showMessageDialog(null, "Id del usuario: "+interfaz.verDatosUsuarioActivo().getIdUsuario() + ""
						+ "\nUsuario: "+interfaz.verDatosUsuarioActivo().getNombreUsuario()+""
						+ "\nContraseña: "+interfaz.verDatosUsuarioActivo().getContrasena() + ""
						+ "\nRol: "+ interfaz.verDatosUsuarioActivo().getRol());
				
			}
		});
		
		botVerPerfil.setBounds(10, 11, 136, 51);
		frame.getContentPane().add(botVerPerfil);
		
		JButton botCrearUsuario = new JButton("Crear Usuario...");
		botCrearUsuario.setBounds(202, 11, 176, 51);
		frame.getContentPane().add(botCrearUsuario);
		botCrearUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				crearUsuario();
			}
		});
		
		JButton botQuitarUsuario = new JButton("Eliminar Usuario...");
		botQuitarUsuario.setBounds(377, 11, 176, 51);
		frame.getContentPane().add(botQuitarUsuario);
		botQuitarUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminarUsuario();
			}
		});
		
		JLabel labText = new JLabel("Hola, "+ this.interfaz.verDatosUsuarioActivo().getNombreUsuario());
		labText.setBounds(563, 11, 324, 59);
		labText.setFont(new Font("Arial", Font.PLAIN, 30));
		labText.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(labText);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				confirmarSalir(frame);
			}
		});
		
		//No tira el cambiar de color el toggle
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
	
	private void crearUsuario() {
		
		JTextField txtUsuario;
	    JPasswordField txtPassword;
	    JComboBox<String> cboTipoUsuario;
	    JTextField txtNombreCompleto, txtTelefono, txtDNI;
		
		this.addUsers = new JFrame();
		this.addUsers.setTitle("Añadir usuarios...");
		
		this.addUsers.setSize(500, 250);
		this.addUsers.setVisible(true);
		this.addUsers.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addUsers.setLocationRelativeTo(null);
		this.addUsers.setResizable(false);
		
        txtUsuario = new JTextField();
        txtPassword = new JPasswordField();
        cboTipoUsuario = new JComboBox<>(new String[]{
        		"",
        		"Administrador", 
        		"Cliente"
        		}
        );
        
        txtNombreCompleto = new JTextField();
        txtTelefono = new JTextField();
        txtDNI = new JTextField();

        this.addUsers.getContentPane().setLayout(new GridLayout(0, 4));

        this.addUsers.getContentPane().add(createRightAlignedLabel("Usuario:"));
        this.addUsers.getContentPane().add(txtUsuario);
        
        this.addUsers.getContentPane().add(createRightAlignedLabel("Nombre del usuario:"));
        this.addUsers.getContentPane().add(txtNombreCompleto);

        this.addUsers.getContentPane().add(createRightAlignedLabel("Contraseña:"));
        this.addUsers.getContentPane().add(txtPassword);
        
        this.addUsers.getContentPane().add(createRightAlignedLabel("Telefono:"));
        this.addUsers.getContentPane().add(txtTelefono);

        this.addUsers.getContentPane().add(createRightAlignedLabel("Rol:"));
        this.addUsers.getContentPane().add(cboTipoUsuario);

        this.addUsers.getContentPane().add(createRightAlignedLabel("DNI:"));
        this.addUsers.getContentPane().add(txtDNI);

        txtNombreCompleto.setVisible(false);
        txtTelefono.setVisible(false);
        txtDNI.setVisible(false);

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
        
        for(int i = 0; i<3;i++) {
            this.addUsers.getContentPane().add(new JPanel());
        }

        JButton btnAdd = new JButton("Añadir");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Aqui falta poder añadir");
            }
        });
        this.addUsers.getContentPane().add(btnAdd);

        this.addUsers.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				borrarFrame(addUsers);
			}
		});
        
    }
	
	private void eliminarUsuario() {
		
		JTextField txtNombre = new JTextField();
		JLabel labelUser = new JLabel("Usuario:");
		labelUser.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.delUsers = new JFrame();
		this.delUsers.setTitle("Borrar Usuario...");
		
		this.delUsers.setSize(500, 250);
		this.delUsers.setVisible(true);
		this.delUsers.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.delUsers.setLocationRelativeTo(null);
		this.delUsers.setResizable(false);
		
		this.delUsers.getContentPane().setLayout(new GridLayout(0, 4));
		
		for (int i = 0; i<5; i++) {
			this.delUsers.getContentPane().add(new JPanel());
		}
		
		this.delUsers.getContentPane().add(labelUser);
		this.delUsers.getContentPane().add(txtNombre);
		
		for (int i = 0; i<3; i++) {
			this.delUsers.getContentPane().add(new JPanel());
		}
		
		JButton btnAdd = new JButton("Buscar");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	
                	String cadena = interfaz.cogerDatosBorrar(txtNombre.getText().toString().intern());
                	
                	if (cadena.intern() == "No se ha encontrado el usuario") {
                    	JOptionPane.showMessageDialog(null, "No se ha encontrado ningun usuario con ese nombre");
                    }else {
                    	int confirmacion = JOptionPane.showConfirmDialog(delUsers, cadena, "Confirmar",
                				JOptionPane.YES_NO_OPTION);

                		if (confirmacion == JOptionPane.YES_OPTION) {
                			JOptionPane.showMessageDialog(null, "Aqui falta poder borrar");
                		}
                    }
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
                
            }
        });
        this.delUsers.getContentPane().add(btnAdd);

        this.delUsers.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				borrarFrame(delUsers);
			}
		});
        
        for (int i = 0; i<5; i++) {
			this.delUsers.getContentPane().add(new JPanel());
		}
		
	}

    private JLabel createRightAlignedLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }
    
    private void borrarFrame(JFrame frameBorrar) {
    	frameBorrar.dispose();
    }
}
