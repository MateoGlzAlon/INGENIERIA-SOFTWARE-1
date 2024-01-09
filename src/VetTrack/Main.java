package VetTrack;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

	private ConexionBD conexion;

	private void setUpDatabase() throws Exception {
		
		conexion = ConexionBD.getInstance();

		try (Scanner sqlScanner = new Scanner(new File("src/VetTrack/BaseDeDatos/VetTrack.sql"))) {
			Statement sentencia = conexion.getConexion().createStatement();

			sqlScanner.useDelimiter(";");

			// Ejecutar cada sentencia SQL del archivo
			while (sqlScanner.hasNext()) {
				String instruccionSql = sqlScanner.next().trim();
				if (!instruccionSql.isEmpty()) {
					sentencia.execute(instruccionSql);
				}
			}
			
			sentencia.close();

			
			//ZONA DE PRUEBAS
			//================================================================================================================================
			
//			System.out.println(conexion.listar("Articulo").toString());
//			
//			System.out.println("El dato es: " + conexion.obtenerDatoDeTabla("Articulo", "Nombre", "idArticulo", 2));
//			
//	        Map<String, Object> valoresNuevaFila = new HashMap<>();
//	        valoresNuevaFila.put("idArticulo", 1);
//	        valoresNuevaFila.put("nombre", "Articulo1");
//	        valoresNuevaFila.put("descripcionArticulo", "descArt1");
//			
//			conexion.agregarFilaATabla("Articulo", valoresNuevaFila);
//			
//			System.out.println(conexion.listar("Articulo").toString());
//
//			
//			System.out.println("El dato es: " + conexion.obtenerDatoDeTabla("Articulo", "Nombre", "idArticulo", 1));
//			
//			conexion.eliminarFilaDeTabla("Articulo", "idArticulo", 1);
//			
//			System.out.println(conexion.listar("Articulo").toString());
//			
//			System.out.println("El dato 2 es: " + conexion.obtenerDatoDeTabla("Articulo", "Nombre", "idArticulo", 1));
//
//			
			
			//================================================================================================================================
			
		} catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error de SQL: " + e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
	    ConexionBD.getInstance().abrirConexion();

	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                Main main = new Main();
	                main.setUpDatabase(); // Llamar al método para configurar la base de datos
	                Interfaz window = new Interfaz();
	                window.frame.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}

}
