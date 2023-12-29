package VetTrack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConexionBD {

	private static ConexionBD instancia; // Instancia única para el Singleton
	private String dataBaseURL;
	private String driverName;
	private String user;
	private String pass;
	private Connection conexion;

	// Constructor privado para evitar instanciación directa
	private ConexionBD() {
		this.dataBaseURL = "jdbc:mysql://viaduct.proxy.rlwy.net:18593/railway?autoReconnect=true&useSSL=true";
		this.driverName = "com.mysql.cj.jdbc.Driver";
		this.user = "root";
		this.pass = "cc3BCfgdba-B3-hGA5EgGHCDHdBd-aC-";
	}

	// Método estático para obtener la única instancia del Singleton
	public static ConexionBD getInstance() {
		if (instancia == null) {
			instancia = new ConexionBD();
		}
		return instancia;
	}

	public void abrirConexion() throws Exception {
		if (dataBaseURL.isEmpty() || user.isEmpty() || pass.isEmpty() || driverName.isEmpty()) {
			System.out.println("Error al crear la conexión (¿están inicializados?) con estos valores:");
			this.mostrarValoresConexion();
		} else {
			try {
				Class.forName(this.driverName);
				this.conexion = DriverManager.getConnection(this.dataBaseURL, this.user, this.pass);
			} catch (Exception e) {
				throw new Exception("Al abrir la base de datos " + e.getMessage());
			}
		}
	}

	public void cerrarConexion() throws Exception {
		try {
			if (this.conexion != null && !this.conexion.isClosed()) {
				this.conexion.close();
				System.out.println("Cierre correcto de la conexión con la base de datos");
			}
		} catch (Exception e) {
			throw new Exception("Al cerrar la conexión de la base de datos. " + e.getMessage());
		}
	}

	public Connection getConexion() {
		return this.conexion;
	}

	private void mostrarValoresConexion() {
		// Implementa el método para mostrar los valores de la conexión si es necesario
	}

	public String obtenerDatoDeTabla(String nombreTabla, String nombreColumnaExtraer, String nombreColumnaComparar, Object condicion) throws Exception {
		String resultado = null;
		
		System.out.println(3);

		try {
			String query = "SELECT " + nombreColumnaExtraer + " FROM " + nombreTabla + " WHERE " + nombreColumnaComparar + " = ?";
			PreparedStatement st = this.getConexion().prepareStatement(query);
			System.out.println(4);

			if (condicion instanceof Integer) {
				st.setInt(1, (Integer) condicion);
			} else if (condicion instanceof String) {
				st.setString(1, (String) condicion);
			} else {
				// Agregar otras conversiones según sea necesario
				throw new IllegalArgumentException("Tipo de dato no compatible para el parámetro id");
			}

			System.out.println(6);

			System.out.println(st.toString());
			
			ResultSet rs = st.executeQuery();
			
			System.out.println(7);


			if (rs.next()) {
				resultado = rs.getString(nombreColumnaExtraer);
				System.out.println(nombreColumnaExtraer + ": " + resultado);
			} else {
				throw new Exception("No se encontró la fila con la condición proporcionada.");
			}
			
			System.out.println(7);

			
		} catch (Exception e) {
			throw new Exception("Error al obtener: " + e.getMessage());
		}

		return resultado;
	}
	
	
	/*
	 *  // Crear un Map con los valores de la nueva fila
        Map<String, Object> valoresNuevaFila = new HashMap<>();
        valoresNuevaFila.put("Nombre", "Nuevo Articulo");
        valoresNuevaFila.put("Descripcion", "Descripción del Nuevo Articulo");
        valoresNuevaFila.put("Marca", "Nueva Marca");

        // Llamar al método para agregar la nueva fila
        conexion.agregarFilaATabla("Articulo", valoresNuevaFila);

        // Mostrar mensaje de éxito
        System.out.println("Nueva fila agregada correctamente a la tabla 'Articulo'")
	 * 
	 */
	
	public void agregarFilaATabla(String nombreTabla, Map<String, Object> valores) throws Exception {
	    try {
	        // Construir la consulta INSERT
	        StringBuilder queryBuilder = new StringBuilder("INSERT INTO ");
	        queryBuilder.append(nombreTabla).append(" (");

	        // Agregar los nombres de las columnas
	        for (String columna : valores.keySet()) {
	            queryBuilder.append(columna).append(", ");
	        }
	        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length()); // Eliminar la coma final
	        queryBuilder.append(") VALUES (");

	        // Agregar los valores
	        for (int i = 0; i < valores.size(); i++) {
	            queryBuilder.append("?, ");
	        }
	        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length()); // Eliminar la coma final
	        queryBuilder.append(")");

	        // Preparar la consulta
	        PreparedStatement st = this.getConexion().prepareStatement(queryBuilder.toString());

	        // Establecer los valores de los parámetros
	        int index = 1;
	        for (Object valor : valores.values()) {
	            if (valor instanceof Integer) {
	                st.setInt(index, (Integer) valor);
	            } else if (valor instanceof String) {
	                st.setString(index, (String) valor);
	            } else {
	                throw new IllegalArgumentException("Tipo de entidad no compatible para la inserción");
	            }
	            index++;
	        }

	        // Ejecutar la consulta
	        st.executeUpdate();
	    } catch (Exception e) {
	        throw new Exception("Error al agregar fila: " + e.getMessage());
	    } finally {
	        // Cerrar recursos si es necesario
	    }
	}



	public <T> void eliminarFilaDeTabla(String nombreTabla, String nombreColumnaComparar, T condicion) throws Exception {
		try {
			String query = "DELETE FROM " + nombreTabla + " WHERE " + nombreColumnaComparar + " = ?";
			PreparedStatement st = this.getConexion().prepareStatement(query);

			if (condicion instanceof Integer) {
				st.setInt(1, (Integer) condicion);
			} else if (condicion instanceof String) {
				st.setString(1, (String) condicion);
			} else {
				throw new IllegalArgumentException("Tipo de entidad no compatible para el método eliminar");
			}

			int filasAfectadas = st.executeUpdate();

			if (filasAfectadas == 0) {
				throw new Exception("No se encontró la fila para eliminar con la condición proporcionada.");
			}
		} catch (Exception e) {
			throw new Exception("Error al eliminar: " + e.getMessage());
		} finally {
			
		}
	}


	public List<List<Object>> listar(String nombreTabla) throws Exception {
        List<List<Object>> listaRegistros = new ArrayList<>();

        try {
            // Abrir la conexión antes de realizar la operación en la base de datos

            String query = "SELECT * FROM " + nombreTabla;
            PreparedStatement st = this.getConexion().prepareStatement(query);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                List<Object> registro = new ArrayList<>();
                // Obtener metadatos sobre las columnas
                int numColumnas = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= numColumnas; i++) {
                    Object valor = rs.getObject(i);
                    registro.add(valor);
                }
                listaRegistros.add(registro);
            }
        } catch (Exception e) {
            throw new Exception("Error al listar registros: " + e.getMessage());
        } finally {
            // Asegúrate de cerrar la conexión, independientemente de si hubo una excepción o no
        	
        }

        return listaRegistros;
    }

	
	public boolean existeEnLaTabla(String nombreTabla, String nombreColumnaComparar, String nombreUsuario) {
	    try {
	        // Construir la consulta SQL
	        String query = "SELECT COUNT(*) FROM " + nombreTabla + " WHERE " + nombreColumnaComparar + " = ?";
	        
	        // Preparar la declaración SQL
	        PreparedStatement st = conexion.prepareStatement(query);
	        
	        // Establecer el valor del parámetro en la consulta
	        st.setString(1, nombreUsuario);
	        
	        // Ejecutar la consulta
	        ResultSet rs = st.executeQuery();
	        
	        // Verificar si hay al menos una fila que coincide con el ID de usuario
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            return count > 0;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return false; // Si hay un error o no se encuentra, se devuelve false por defecto
	}








	/*
	 * 


	public void eliminar(Articulo articulo) throws Exception {
		try {
			this.abrirConexion();
			String query = "DELETE FROM Articulo"
					+ "ios WHERE id = ?";
			PreparedStatement st = this.getConexion().prepareStatement(query);
			st.setInt(1, articulo.getIdArticulo());
			st.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Error al eliminar un articulo: " + e.getMessage());
		} finally {
			this.cerrarConexion();
		}
	}
	 * 
	 */

}
