package VetTrack;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JScrollBar;

public class InterfazCliente {

	public JFrame frame;
	private Interfaz interfaz;
	private Color colorOriginalBton;

	/**
	 * Create the application.
	 */
	public InterfazCliente(Interfaz interfaz) {
		this.interfaz = interfaz;
		
		initialize_cliente();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize_cliente() {
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
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				confirmarSalir(frame);
			}
		});
		
		
		//El togle no fcambia de color 
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

}
