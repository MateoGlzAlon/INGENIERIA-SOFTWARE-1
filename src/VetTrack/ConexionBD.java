package VetTrack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

		try {
			String query = "SELECT " + nombreColumnaExtraer + " FROM " + nombreTabla + " WHERE " + nombreColumnaComparar + " = ?";
			PreparedStatement st = this.getConexion().prepareStatement(query);

			if (condicion instanceof Integer) {
				st.setInt(1, (Integer) condicion);
			} else if (condicion instanceof String) {
				st.setString(1, (String) condicion);
			} else {
				// Agregar otras conversiones según sea necesario
				throw new IllegalArgumentException("Tipo de dato no compatible para el parámetro id");
			}

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				resultado = rs.getString(nombreColumnaExtraer);
				System.out.println(nombreColumnaExtraer + ": " + resultado);
			} else {
				throw new Exception("No se encontró la fila con la condición proporcionada.");
			}

		} catch (Exception e) {
			throw new Exception("Error al obtener: " + e.getMessage());
		}

		return resultado;
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
