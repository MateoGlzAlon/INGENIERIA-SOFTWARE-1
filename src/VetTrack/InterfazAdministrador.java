package VetTrack;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

import java.util.Base64;

public class InterfazAdministrador {

	public JFrame frame;
	private JFrame addUsers;
	private JFrame delUsers;
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
		
		frame.setTitle("Panel de control de " + this.interfaz.getUser().getNombreUsuario());
		
		frame.setBounds(300, 300, 1200, 900);
		frame.setVisible(true);
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
							interfaz.recogerIdUsuario(interfaz.getUser().getNombreUsuario(), interfaz.getUser().getContrasena()));
					JOptionPane.showMessageDialog(null, "Id del usuario: "+interfaz.getUser().getIdUsuario() + ""
							+ "\nUsuario: "+interfaz.getUser().getNombreUsuario()+""
							+ "\nContraseña: "+interfaz.getUser().getContrasena() + ""
							+ "\nRol: "+ interfaz.getUser().getRol() + ""
							+ "\nDatos:\n"+ datosUsuario.toString());
				} catch (Exception e1) {
					e1.printStackTrace();
				}*/
				
				JOptionPane.showMessageDialog(null, "Id del usuario: "+interfaz.getUser().getIdUsuario() + ""
						+ "\nUsuario: "+interfaz.getUser().getNombreUsuario()+""
						+ "\nContraseña: "+interfaz.getUser().getContrasena() + ""
						+ "\nRol: "+ interfaz.getUser().getRol());
				
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
		
		JLabel labText = new JLabel("Hola, "+ this.interfaz.getUser().getNombreUsuario());
		labText.setBounds(563, 11, 324, 59);
		labText.setFont(new Font("Arial", Font.PLAIN, 30));
		labText.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(labText);
		
		panelTextUser = new JTextPane();
		panelTextUser.setBounds(799, 219, 375, 631);
		panelTextUser.setEditable(false);
		panelTextUser.setBorder(new LineBorder(Color.BLACK, 1));
		frame.getContentPane().add(panelTextUser);
		
		textPaneCitasPrevias = new JTextPane();
		textPaneCitasPrevias.setBounds(410, 188, 351, 303);
		textPaneCitasPrevias.setEditable(false);
		textPaneCitasPrevias.setBorder(new LineBorder(Color.BLACK, 1));
		frame.getContentPane().add(textPaneCitasPrevias);
		
		textPaneCitasFuturas = new JTextPane();
		textPaneCitasFuturas.setBounds(410, 547, 351, 303);
		textPaneCitasFuturas.setEditable(false);
		textPaneCitasFuturas.setBorder(new LineBorder(Color.BLACK, 1));
		frame.getContentPane().add(textPaneCitasFuturas);
		
		JButton botBuscarUser = new JButton("Buscar");
		botBuscarUser.setBounds(1085, 185, 89, 23);
		frame.getContentPane().add(botBuscarUser);
		botBuscarUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarUsuario();
			}
		});
		
		textUserBuscar = new JTextField();
		textUserBuscar.setBounds(799, 186, 274, 20);
		frame.getContentPane().add(textUserBuscar);
		textUserBuscar.setColumns(10);
		
		JLabel labUserBuscar = new JLabel("Buscar Usuario:");
		labUserBuscar.setBounds(799, 161, 159, 14);
		frame.getContentPane().add(labUserBuscar);
		
		JLabel lblNewLabel = new JLabel("Añadir articulo");
		lblNewLabel.setBounds(133, 150, 203, 31);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre");
		lblNewLabel_1.setBounds(22, 195, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		textNombreArticulo = new JTextField();
		textNombreArticulo.setBounds(77, 192, 296, 20);
		frame.getContentPane().add(textNombreArticulo);
		textNombreArticulo.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Precio");
		lblNewLabel_2.setBounds(22, 229, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		textPrecioArticulo = new JTextField();
		textPrecioArticulo.setBounds(77, 226, 296, 20);
		frame.getContentPane().add(textPrecioArticulo);
		textPrecioArticulo.setColumns(10);
		
		JTextArea textAreaArticulo = new JTextArea();
		textAreaArticulo.setBounds(77, 302, 296, 119);

		frame.getContentPane().add(textAreaArticulo);
		
		JLabel lblNewLabel_3 = new JLabel("Descripcion");
		lblNewLabel_3.setBounds(10, 307, 63, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JButton botCrearArticulo = new JButton("Crear");
		botCrearArticulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (textAreaArticulo.getText().intern() != "" && textPrecioArticulo.getText().intern() != "" && textNombreArticulo.getText().intern() != "" 
						&& textMarcaArticulo.getText().intern() != "" && Integer.parseInt(textPrecioArticulo.getText().intern())>0) {
					
					try {
						List<List<Object>> articulosTtal = interfaz.getConexion().listar("Articulo");
						
						int maximo = -1;
						
						for (List<Object> artic : articulosTtal) {
							if ((int) artic.get(0) > maximo) {
								maximo = (int) artic.get(0);
							}
						}
						
						Map<String, Object> valores = new HashMap<>();
						
						valores.put("idArticulo", maximo+1);
						valores.put("nombre", textNombreArticulo.getText().intern());
						valores.put("marca", textMarcaArticulo.getText().intern());
			            valores.put("precio", Float.parseFloat(textPrecioArticulo.getText().intern()));
			            valores.put("descripcionArticulo", textAreaArticulo.getText().intern());
						
			            interfaz.getConexion().agregarFilaATabla("Articulo", valores);
			            
			            JOptionPane.showMessageDialog(null, "El articulo se ha añadido correctamente");
			            
			            //Para borrar parametros
			            textNombreArticulo.setText("");
			            textMarcaArticulo.setText("");
			            textPrecioArticulo.setText("");
			            textAreaArticulo.setText("");
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "No puede haber un campo vacio ni precio menor de 0");
				}
			}
		});
		botCrearArticulo.setBounds(284, 432, 89, 23);
		frame.getContentPane().add(botCrearArticulo);
		
		JLabel lblNewLabel_4 = new JLabel("Crear cita");
		lblNewLabel_4.setBounds(151, 522, 211, 39);
		
		lblNewLabel_4.setFont(new Font("Arial", Font.PLAIN, 20));
		
		frame.getContentPane().add(lblNewLabel_4);
		
		textUserCita = new JTextField();
		textUserCita.setBounds(22, 597, 152, 20);
		frame.getContentPane().add(textUserCita);
		textUserCita.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Nombre Usuario:");
		lblNewLabel_5.setBounds(22, 572, 152, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		textFechaCita = new JTextField();
		textFechaCita.setBounds(226, 597, 152, 20);
		frame.getContentPane().add(textFechaCita);
		textFechaCita.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Fecha cita: (dd/mm/aaaa)");
		lblNewLabel_6.setBounds(226, 572, 152, 14);
		frame.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Hora cita: (hh:mm)");
		lblNewLabel_7.setBounds(226, 628, 152, 14);
		frame.getContentPane().add(lblNewLabel_7);
		
		textHoraCita = new JTextField();
		textHoraCita.setBounds(226, 653, 152, 20);
		frame.getContentPane().add(textHoraCita);
		textHoraCita.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Nombre mascota:");
		lblNewLabel_8.setBounds(22, 628, 152, 14);
		frame.getContentPane().add(lblNewLabel_8);
		
		textMascCita = new JTextField();
		textMascCita.setBounds(22, 653, 152, 20);
		frame.getContentPane().add(textMascCita);
		textMascCita.setColumns(10);
		
		JTextPane panelDescrCita = new JTextPane();
		panelDescrCita.setBounds(22, 704, 356, 64);

		frame.getContentPane().add(panelDescrCita);
		
		JButton botCrearCita = new JButton("Crear");
		botCrearCita.setBounds(284, 779, 89, 23);
		frame.getContentPane().add(botCrearCita);
		botCrearCita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String fechaFinal = "";

			    if (!textFechaCita.getText().equals("")) {
			        String fechaCita = textFechaCita.getText();

			        String[] partesFecha = fechaCita.split("/");

			        // Obtener día, mes y año
			        String anio = partesFecha[2];
			        String mes = partesFecha[1];
			        String dia = partesFecha[0];

			        fechaFinal = anio + "-" + mes + "-" + dia;

			        System.out.println("Fecha: " + fechaFinal);
			    }

			    if (!textUserCita.getText().isEmpty() && !textMascCita.getText().isEmpty() && !textFechaCita.getText().isEmpty()
			            && !textHoraCita.getText().isEmpty() && !panelDescrCita.getText().isEmpty()) {

			        try {
			            List<Object> user = interfaz.cogerDatosBorrar(textUserCita.getText().intern());

			            int idMascota = -1;

			            if (user != null) {

			                boolean compr = false;

			                ArrayList<String> listaMasc = interfaz.recTodasMascotas(Integer.parseInt(user.get(0).toString()));

			                for (String mascota : listaMasc) {

			                    if (textMascCita.getText().intern().equals(mascota.intern())) {

			                        compr = true;

			                        idMascota = interfaz.idMascotaRecuperar(mascota, Integer.parseInt(user.get(0).toString()));

			                    }

			                }

			                if (compr) {

			                    List<List<Object>> citasTtal = interfaz.getConexion().listar("Cita");

			                    int maximo = -1;

			                    for (List<Object> cita : citasTtal) {
			                        if ((int) cita.get(0) > maximo) {
			                            maximo = (int) cita.get(0);
			                        }
			                    }

			                    Map<String, Object> valores = new HashMap<>();

			                    valores.put("idCita", maximo + 1);
			                    valores.put("idUsuario", Integer.parseInt(user.get(0).toString()));
			                    valores.put("fechaCita", java.sql.Date.valueOf(fechaFinal));

			                    String horaCita = textHoraCita.getText() + ":00";

			                    valores.put("horaCita", java.sql.Time.valueOf(horaCita));
			                    valores.put("idMascota", idMascota);
			                    valores.put("descripcionCita", panelDescrCita.getText());

			                    System.out.println("Cita: " + valores.toString());

			                    interfaz.getConexion().agregarFilaATabla("Cita", valores);

			                    JOptionPane.showMessageDialog(null, "La cita se ha añadido correctamente");

			                } else {
			                    JOptionPane.showMessageDialog(null, "La mascota no existe");
			                }

			            } else {
			                JOptionPane.showMessageDialog(null, "El usuario no existe");
			            }

			        } catch (Exception e1) {
			            e1.printStackTrace();
			        }

			    } else {
			        JOptionPane.showMessageDialog(null, "No puede haber un campo vacío o la fecha es incorrecta");
			    }
			}

		});
		
		JLabel lblNewLabel_9 = new JLabel("Citas futuras");
		lblNewLabel_9.setBounds(410, 502, 351, 34);
		lblNewLabel_9.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Citas Previas");
		lblNewLabel_10.setBounds(410, 136, 351, 34);
		lblNewLabel_10.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("Marca:");
		lblNewLabel_11.setBounds(22, 264, 46, 14);
		frame.getContentPane().add(lblNewLabel_11);
		
		textMarcaArticulo = new JTextField();
		textMarcaArticulo.setBounds(77, 261, 296, 20);
		frame.getContentPane().add(textMarcaArticulo);
		textMarcaArticulo.setColumns(10);
		
		JLabel lblNewLabel_12 = new JLabel("Descripcion");
		lblNewLabel_12.setBounds(22, 684, 89, 14);
		frame.getContentPane().add(lblNewLabel_12);
		
		JButton botActualizarUserBuscar = new JButton("Actualizar");
		botActualizarUserBuscar.setBounds(1085, 157, 89, 23);
		frame.getContentPane().add(botActualizarUserBuscar);
		
		JButton botAddMasc = new JButton("Añadir Mascota");
		botAddMasc.setBounds(957, 157, 115, 23);
		frame.getContentPane().add(botAddMasc);
		botAddMasc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					if(textUserBuscar.getText().intern() != "") {
						
						List<Object> cadena = interfaz.cogerDatosBorrar(textUserBuscar.getText().intern());
						
						if (cadena == null) {
			        		textUserBuscar.setText("");
			        		panelTextUser.setText("");
			            	JOptionPane.showMessageDialog(null, "No se ha encontrado ningun usuario con ese nombre");
			            }else {
			            	
			            	if (cadena.get(3).toString().intern() == "Cliente") {
			            		
			            		addMascotaCliente(Integer.parseInt(cadena.get(0).toString().intern()));
			            		
			            	}else {
			            		JOptionPane.showMessageDialog(null, "Estas seleccionando un Administrador");
			            	}
			            	
			            }
						
					}else {
						JOptionPane.showMessageDialog(null, "No hay un usuario seleccionado");
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		botActualizarUserBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarUsuario();
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
	
	private void addMascotaCliente(int idUsuario) {
		/*
		 * PRUEBAS
		 */
		
		JPanel panel = new JPanel(new GridLayout(0, 2));

        JTextField campoNombre = new JTextField();
        JTextField campoEspecie = new JTextField();
        JTextField campoRaza = new JTextField();
        JTextField campofecha = new JTextField();

        panel.add(new JLabel("Nombre:"));
        panel.add(campoNombre);
        panel.add(new JLabel("Especie:"));
        panel.add(campoEspecie);
        panel.add(new JLabel("Raza:"));
        panel.add(campoRaza);
        panel.add(new JLabel("FechaNacimiento:"));
        panel.add(campofecha);
        
        

        int resultado = JOptionPane.showConfirmDialog(null, panel, "Ingrese Informacion", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
        	
        	try {
        		List<List<Object>> mascotaTotal = interfaz.getConexion().listar("Mascota");
        		int maximo = 0;
        		
        		for (List<Object> user : mascotaTotal) {
    				if ((int) user.get(0) > maximo) {
    					maximo = (int) user.get(0);
    				}
    			}
    			
    			maximo++;
        		
        		Map<String, Object> valores = new HashMap<>();
        		
        		valores.put("idMascota", maximo);
        		valores.put("nombre", campoNombre.getText().intern());
        		valores.put("especie", campoEspecie.getText().intern());
        		valores.put("raza", campoRaza.getText().intern());
        		valores.put("fechaNacimiento", java.sql.Date.valueOf(campofecha.getText()));
        		valores.put("idUsuario", idUsuario);
        		
        		interfaz.getConexion().agregarFilaATabla("Mascota", valores);
        		
        		List<Map<String, Object>> filaCliente = interfaz.getConexion().obtenerFilasDeTabla("Cliente", Arrays.asList("numMascotas"), "idUsuario = ?", idUsuario);
        		
        		int nuevoNumMascotas = (int) filaCliente.get(0).get("numMascotas") + 1;
        		
        		interfaz.getConexion().actualizarFila("Cliente", "numMascotas", nuevoNumMascotas, "idUsuario", idUsuario);
        		
        		
			} catch (Exception e) {
				e.printStackTrace();
			}
			
        	
        }
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
	
	private void buscarUsuario() {
		try {
			
			List<Object> cadena = interfaz.cogerDatosBorrar(textUserBuscar.getText().intern());
        	
        	if (cadena == null) {
        		textUserBuscar.setText("");
        		panelTextUser.setText("");
            	JOptionPane.showMessageDialog(null, "No se ha encontrado ningun usuario con ese nombre");
            }else {
            	String datosUser = "ID: "+ cadena.get(0).toString() + "\nUsername: "+cadena.get(1).toString()+"\nRol: "+cadena.get(3).toString();
            	String mascClient = "";
            	
            	if (cadena.get(3).toString().intern() != "Administrador") {
            		mascClient = "\nMascotas que tiene el cliente:\n";
            		
            		List<String> mascotas = interfaz.recTodasMascotas((int) cadena.get(0));
            		
            		for (String masc : mascotas) {
            			mascClient += interfaz.mascRecDatos(masc, (int) cadena.get(0));
            		}
            		
            	}
            	
            	panelTextUser.setText(datosUser + mascClient);
            	
            	textPaneCitasPrevias.setText(recCitasUsuario(Integer.parseInt(cadena.get(0).toString()), "<"));
            	textPaneCitasFuturas.setText(recCitasUsuario(Integer.parseInt(cadena.get(0).toString()), ">"));
            	
            }
		
		
		} catch (Exception e1) {
			e1.printStackTrace();
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
            	try {
					
            		if (txtUsuario.getText().intern()!= "" && txtPassword.getPassword().toString().intern() != "" && cboTipoUsuario.getSelectedItem().toString().intern()!= "") {
            			
            			if(cboTipoUsuario.getSelectedItem().toString().intern()== "Cliente" && txtNombreCompleto.getText().intern()!= "" 
                				&& txtTelefono.getText().intern() != "" && txtDNI.getText().intern() != "") {
                			
                			List<List<Object>> usuariosTotal = interfaz.getConexion().listar("Usuario");
    						
    						int maximo = -1;
    						
    						for (List<Object> user : usuariosTotal) {
    							if ((int) user.get(0) > maximo) {
    								maximo = (int) user.get(0);
    							}
    						}
    						
    						maximo++;
    	            		
    	            		Map<String, Object> valores = new HashMap<>();
    						
    						valores.put("idUsuario", maximo);
    						valores.put("nombreUsuario", txtUsuario.getText().intern());
    						valores.put("contraseña", new String(txtPassword.getPassword()).intern());
    						valores.put("rol", cboTipoUsuario.getSelectedItem().toString().intern());
    						interfaz.getConexion().agregarFilaATabla("Usuario", valores);
    						
    						Map<String, Object> valoresCliente = new HashMap<>();
    						
    						valoresCliente.put("idUsuario", maximo);
    						valoresCliente.put("dni", txtDNI.getText().intern());
    						valoresCliente.put("telefono", txtTelefono.getText().intern());

    						String cadenasep[] = txtNombreCompleto.getText().intern().split(" ");
    						
    						valoresCliente.put("nombre", cadenasep[0]);
    						valoresCliente.put("apellidos", String.join(" ", Arrays.copyOfRange(cadenasep, 1, cadenasep.length)));
    						valoresCliente.put("numMascotas", 0);
    						
    						interfaz.getConexion().agregarFilaATabla("Cliente", valoresCliente);
    						
    						JOptionPane.showMessageDialog(null, "Se ha añadido al cliente correctamente");
                			
                		}else if (cboTipoUsuario.getSelectedItem().toString().intern()== "Administrador") {
                			
                			List<List<Object>> usuariosTotal = interfaz.getConexion().listar("Usuario");
    						
    						int maximo = -1;
    						
    						for (List<Object> user : usuariosTotal) {
    							if ((int) user.get(0) > maximo) {
    								maximo = (int) user.get(0);
    							}
    						}
    						
    						maximo++;
    	            		
    	            		Map<String, Object> valores = new HashMap<>();
    						
    						valores.put("idUsuario", maximo);
    						valores.put("nombreUsuario", txtUsuario.getText().intern());
    						valores.put("contraseña", new String(txtPassword.getPassword()).intern());
    						valores.put("rol", cboTipoUsuario.getSelectedItem().toString().intern());
    						interfaz.getConexion().agregarFilaATabla("Usuario", valores);
    						
    						Map<String, Object> valoresAdministrador = new HashMap<>();
    						
    						valoresAdministrador.put("idUsuario", maximo);
    						valoresAdministrador.put("cadenaInicioSesion", new String(Base64.getEncoder().encode(txtUsuario.getText().intern().getBytes())));
    						
    						interfaz.getConexion().agregarFilaATabla("Administrador", valoresAdministrador);
    						
    						JOptionPane.showMessageDialog(null, "Se ha añadido al administrador correctamente");
                			
                		}else {
                			JOptionPane.showMessageDialog(null, "Revisa los campos, no se puede añadir al usuario");
                		}
            			
            		}else {
            			JOptionPane.showMessageDialog(null, "No puede haber campos vacios");
            		}
            		
            		
            		
				} catch (Exception e2) {
					e2.printStackTrace();
				}
            	
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
                	
                	List<Object> cadena = interfaz.cogerDatosBorrar(txtNombre.getText().toString().intern());
                	
                	if (cadena == null) {
                    	JOptionPane.showMessageDialog(null, "No se ha encontrado ningun usuario con ese nombre");
                    }else {
                    	int confirmacion = JOptionPane.showConfirmDialog(delUsers, "ID: "+ cadena.get(0).toString() + "\nUsername: "+cadena.get(1).toString()+"\nRol: "+cadena.get(3).toString(), "Confirmar",
                				JOptionPane.YES_NO_OPTION);

                		if (confirmacion == JOptionPane.YES_OPTION) {
                			
                			if (cadena.get(3).toString().intern()=="Cliente") {
                				interfaz.getConexion().eliminarFilaDeTabla("Mascota", "idUsuario", Integer.parseInt(cadena.get(0).toString()));
                			}
                			
                			interfaz.getConexion().eliminarFilaDeTabla(cadena.get(3).toString().intern(), "idUsuario", Integer.parseInt(cadena.get(0).toString()));
                			interfaz.getConexion().eliminarFilaDeTabla("Usuario", "idUsuario", Integer.parseInt(cadena.get(0).toString()));
                			JOptionPane.showMessageDialog(null, "El usuario "+cadena.get(1).toString()+" se ha eliminado correctamente");
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
    
    //Puede haber solamente 2, > y <
    //	">" ---> es para mayor
    //	"<" ---> es para menor
    private String recCitasUsuario(int id, String caracter) throws Exception{
    	
    	List<Map<String, Object>> citas = this.interfaz.getConexion().obtenerFilasDeTabla(
    			"Cita", 
    			Arrays.asList("idCita", "fechaCita", "horaCita", "descripcionCita", "idMascota"), 
    			"idUsuario = ? AND fechaCita "+caracter+" ?",
    			id, Date.valueOf(LocalDate.now()));
    	
    	StringBuffer stringBuffer = new StringBuffer("");
    	int i = 1;
    	
    	for(Map<String, Object> unaCita : citas) {
    		
    		stringBuffer.append("Cita #"+i);
    		
    		stringBuffer.append("\nidCita: " + unaCita.get("idCita"));
    		stringBuffer.append("\nFecha de la cita: " + unaCita.get("fechaCita"));
    		stringBuffer.append("\nHora de la cita: " + unaCita.get("horaCita"));
    		stringBuffer.append("\nidMascota: " + unaCita.get("idMascota"));
    		stringBuffer.append("\nDescripcion:\n" + unaCita.get("descripcionCita"));
    		stringBuffer.append("\n----------------------------------------\n\n");
    		
    		i++;
    		
    	}
    	
    	return stringBuffer.toString();
    	
    }
}
