package VetTrack;

import java.util.List;
import java.awt.EventQueue;

public class Main {
	
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

	// Nota para Mateo: no se si te has equivocado o algo, pero tienes en la funcion super que le envias a la clase padre (Usuario) "super(idUsuario, NombreCompleto, DNI);"
	// 					de la clase Administrador, no dberia de ser idUsuario, nombre de usuario y contraseña?
	/*
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
    }*/
    
}
