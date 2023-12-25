package VetTrack;

import java.util.List;

public class Main {

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
            System.err.println("Error: " + e.getMessage());
        }
    }
}
