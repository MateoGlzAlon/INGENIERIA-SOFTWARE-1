package VetTrack;

import java.util.List;

public class Main {

	// Nota para Mateo: no se si te has equivocado o algo, pero tienes en la funcion super que le envias a la clase padre (Usuario) "super(idUsuario, NombreCompleto, DNI);"
	// 					de la clase Administrador, no dberia de ser idUsuario, nombre de usuario y contraseña?
	
    public static void main(String[] args) {
        try {
            // Crear instancia de ArticuloDAO
            ArticuloDAO articuloDAO = new ArticuloDAO();
            
            System.out.println(articuloDAO.obtenerArticulo(1).getDescripcion());
            
            // Listar todos los artículos
            List<Articulo> listaArticulos = articuloDAO.listar();

            // Mostrar los artículos en la consola
            for (Articulo articulo : listaArticulos) {
                System.out.println("ID: " + articulo.getIdArticulo());
                System.out.println("Nombre: " + articulo.getNombre());
                System.out.println("Descripción: " + articulo.getDescripcion());
                System.out.println("Marca: " + articulo.getMarca());
                System.out.println("=============================");
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage()); // Cuidado luego con poner esto porque nos dijo el de practicas que no le gusta los exception genericos
        }
    }
    
    //Ignorar (login)
    //Aqui tenemos que coger el usuario que el user ha puesto + la passwd
    public static boolean logon(String user, String passwd) {
    	
    	boolean log = false;
    	/*
    	//Falta la forma de sacar la List<Usuario>
    	List<Usuario> user;
    	
    	for (Usuario usuario : listaUsuarios){
    	
    		//Comprueba si el usuario y la contraseña son las mismas
    		if(user == listaUsuarios.getNombreUsuario() && passwd == listaUsuarios.getContrasena()){
    			log = true;
    			break; //Si a eva no le gusta esto podemos hacer otra cosa, pero asi evitamos mirar mas usuarios (mas rapido)
    		}
    	
    	}
    	
    	*/
    	return log; //Luego en la interfaz si no es lo esperado se da un mensaje de que esta mal o algo
    }
    
    
    
    //Esto es lo que tenemos que poner para que desde el Main se inicialice la ventana 
    /*
    import java.awt.EventQueue;
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz window = new Interfaz();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
     */
    
    
}
