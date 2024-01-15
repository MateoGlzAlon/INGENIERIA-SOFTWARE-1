package VetTrack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Exceptions.DBException;

public class ConexionBD {

	private static ConexionBD instancia; 
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

	public void abrirConexion() throws DBException {
		if (!dataBaseURL.isEmpty() && !user.isEmpty() && !pass.isEmpty() && !driverName.isEmpty()) {
			try {
				Class.forName(this.driverName);
				this.conexion = DriverManager.getConnection(this.dataBaseURL, this.user, this.pass);
			} catch (Exception e) {
				throw new DBException("Al abrir la base de datos " + e.getMessage());
			}
		} 
	}

	public void cerrarConexion() throws DBException {
		try {
			if (this.conexion != null && !this.conexion.isClosed()) {
				this.conexion.close();
			}
		} catch (Exception e) {
			throw new DBException("Al cerrar la conexión de la base de datos. " + e.getMessage());
		}
	}

	public Connection getConexion() {
		return this.conexion;
	}


	public String obtenerDatoDeTabla(String nombreTabla, String nombreColumnaExtraer, String nombreColumnaComparar, Object condicion) throws DBException {
		String resultado = null;

		try {
			// Construir la consulta SQL
			String query = "SELECT " + nombreColumnaExtraer + " FROM " + nombreTabla + " WHERE " + nombreColumnaComparar + " = ?";

			// Preparar la declaración SQL
			try (PreparedStatement st = this.getConexion().prepareStatement(query)) {

				if (condicion instanceof Integer) {
					st.setInt(1, (Integer) condicion);
				} else if (condicion instanceof String) {
					st.setString(1, (String) condicion);
				} else {
					// Agregar otras conversiones según sea necesario
					throw new IllegalArgumentException("Tipo de dato no compatible para el parámetro condicion");
				}


				// Ejecutar la consulta
				try (ResultSet rs = st.executeQuery()) {
					// Verificar si hay resultados
					if (rs.next()) {
						// Obtener el resultado de la columna
						resultado = rs.getString(nombreColumnaExtraer);
					} else {
						// No se encontró la fila con la condición proporcionada
						throw new DBException("No se encontró la fila con la condición proporcionada.");
					}
				}
			}
		} catch (Exception e) {
			throw new DBException("Error al obtener dato de la tabla: ");
		}

		return resultado;
	}

	public String obtenerDatoDeTablaConDosCondiciones(String nombreTabla, String nombreColumnaExtraer, String nombreColumnaComparar1, Object condicion1, String nombreColumnaComparar2, Object condicion2) throws DBException { 	    

		String resultado = null;

		try {
			// Construir la consulta SQL con dos condiciones
			String query = "SELECT " + nombreColumnaExtraer + " FROM " + nombreTabla + 
					" WHERE " + nombreColumnaComparar1 + " = ? AND " + nombreColumnaComparar2 + " = ?";

			// Preparar la declaración SQL
			try (PreparedStatement st = this.getConexion().prepareStatement(query)) {

				if (condicion1 instanceof Integer) {
					st.setInt(1, (Integer) condicion1);
				} else if (condicion1 instanceof String) {
					st.setString(1, (String) condicion1);
				} else {
					throw new IllegalArgumentException("Tipo de dato no compatible para el parámetro condicion1");
				}

				if (condicion2 instanceof Integer) {
					st.setInt(2, (Integer) condicion2);
				} else if (condicion2 instanceof String) {
					st.setString(2, (String) condicion2);
				} else {
					throw new IllegalArgumentException("Tipo de dato no compatible para el parámetro condicion2");
				}

				// Ejecutar la consulta
				try (ResultSet rs = st.executeQuery()) {
					// Verificar si hay resultados
					if (rs.next()) {
						// Obtener el resultado de la columna
						resultado = rs.getString(nombreColumnaExtraer);
					} else {
						// No se encontró la fila con las condiciones proporcionadas
						throw new DBException("No se encontró la fila con las condiciones proporcionadas.");
					}
				}
			}
		} catch (Exception e) {
			// Capturar cualquier excepción y lanzarla hacia arriba
			throw new DBException("Error al obtener dato de la tabla");
		}

		return resultado;
	}


	public List<String> obtenerDatosDeTablaLista(String nombreTabla, String nombreColumnaExtraer, String nombreColumnaComparar, Object condicion) throws DBException {
		List<String> resultados = new ArrayList<>();

		try {
			// Construir la consulta SQL
			String query = "SELECT " + nombreColumnaExtraer + " FROM " + nombreTabla + " WHERE " + nombreColumnaComparar + " = ?";

			// Preparar la declaración SQL
			try (PreparedStatement st = this.getConexion().prepareStatement(query)) {
				// Configurar el valor del parámetro en la consulta
				if (condicion instanceof Integer) {
					st.setInt(1, (Integer) condicion);
				} else if (condicion instanceof String) {
					st.setString(1, (String) condicion);
				} else {
					// Agregar otras conversiones según sea necesario
					throw new IllegalArgumentException("Tipo de dato no compatible para el parámetro condicion");
				}

				// Ejecutar la consulta
				try (ResultSet rs = st.executeQuery()) {
					// Recorrer los resultados y agregarlos a la lista
					while (rs.next()) {
						resultados.add(rs.getString(nombreColumnaExtraer));
					}
				}
			}
		} catch (Exception e) {
			throw new DBException("Error al obtener datos de la tabla");
		}

		return resultados;
	}


	public List<String> obtenerDatosDeTablaListaDosCondiciones(String nombreTabla, String nombreColumnaExtraer, String nombreColumnaComparar1, Object condicion1, String nombreColumnaComparar2, Object condicion2) throws DBException {
		List<String> resultados = new ArrayList<>();

		try {
			// Construir la consulta SQL con dos condiciones (AND)
			String query = "SELECT " + nombreColumnaExtraer + " FROM " + nombreTabla +
					" WHERE " + nombreColumnaComparar1 + " = ? AND " + nombreColumnaComparar2 + " = ?";

			// Preparar la declaración SQL
			try (PreparedStatement st = this.getConexion().prepareStatement(query)) {
				// Configurar los valores de los parámetros en la consulta
				if (condicion1 instanceof Integer) {
					st.setInt(1, (Integer) condicion1);
				} else if (condicion1 instanceof String) {
					st.setString(1, (String) condicion1);
				} else {
					throw new IllegalArgumentException("Tipo de dato no compatible para el parámetro condicion1");
				}

				if (condicion2 instanceof Integer) {
					st.setInt(2, (Integer) condicion2);
				} else if (condicion2 instanceof String) {
					st.setString(2, (String) condicion2);
				} else {
					throw new IllegalArgumentException("Tipo de dato no compatible para el parámetro condicion2");
				}

				// Ejecutar la consulta
				try (ResultSet rs = st.executeQuery()) {
					// Recorrer los resultados y agregarlos a la lista
					while (rs.next()) {
						resultados.add(rs.getString(nombreColumnaExtraer));
					}
				}
			}
		} catch (Exception e) {
			// Capturar cualquier excepción y lanzarla hacia arriba
			throw new DBException("Error al obtener datos de la tabla");
		}

		return resultados;
	}

	public void agregarFilaATabla(String nombreTabla, Map<String, Object> valores) throws DBException {
		try {
			StringBuilder queryBuilder = new StringBuilder("INSERT INTO ");
			queryBuilder.append(nombreTabla).append(" (");

			for (String columna : valores.keySet()) {
				queryBuilder.append(columna).append(", ");
			}
			queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length()); // Eliminar la coma final
			queryBuilder.append(") VALUES (");

			for (int i = 0; i < valores.size(); i++) {
				queryBuilder.append("?, ");
			}
			queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length()); // Eliminar la coma final
			queryBuilder.append(")");

			PreparedStatement st = this.getConexion().prepareStatement(queryBuilder.toString());

			int index = 1;
			for (Object valor : valores.values()) {
				if (valor instanceof Integer) {
					st.setInt(index, (Integer) valor);
				} else if (valor instanceof String) {
					st.setString(index, (String) valor);
				}  else if (valor instanceof java.sql.Date) {
					st.setDate(index, (java.sql.Date) valor);
				} else if (valor instanceof java.sql.Time) {
					st.setTime(index, (java.sql.Time) valor);
				} else if (valor instanceof Float) {
					st.setFloat(index, (Float) valor);
				} else {
					throw new IllegalArgumentException("Tipo de entidad no compatible para la inserción");
				}
				index++;
			}

			// Ejecutar la consulta
			st.executeUpdate();
		} catch (Exception e) {
			throw new DBException("Error al agregar fila: " + e.getMessage());
		}
	}



	public <T> void eliminarFilaDeTabla(String nombreTabla, String nombreColumnaComparar, T condicion) throws DBException {
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
			throw new DBException("Error al eliminar: " + e.getMessage());
		} 
	}


	public List<List<Object>> listar(String nombreTabla) throws DBException {
		List<List<Object>> listaRegistros = new ArrayList<>();

		try {

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
			throw new DBException("Error al listar registros: " + e.getMessage());
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

		return false; 
	}



	public List<Cita> obtenerCitasPasadas(int idMascota) {
		List<Cita> citasPasadas = new ArrayList<>();

		try {
			// Obtener la fecha y hora actual
			Calendar calendario = Calendar.getInstance();
			java.sql.Date fechaActual = new java.sql.Date(calendario.getTime().getTime());

			// Crear la consulta SQL para obtener citas pasadas
			String query = "SELECT * FROM Cita WHERE fechaCita < ? AND idMascota = ?";

			// Preparar la declaración SQL
			try (PreparedStatement st = conexion.prepareStatement(query)) {
				// Establecer la fecha actual como parámetro en la consulta
				st.setDate(1, fechaActual);
				st.setInt(2, idMascota);

				// Ejecutar la consulta
				try (ResultSet rs = st.executeQuery()) {
					// Procesar los resultados
					while (rs.next()) {
						int idUsuario = rs.getInt("idUsuario");
						int idCita = rs.getInt("idCita");
						Date fechaCita = rs.getDate("fechaCita");
						Time horaCita = rs.getTime("horaCita");
						String descripcion = rs.getString("descripcionCita");


						// Crear un objeto Cita y agregarlo a la lista
						Cita cita = new Cita(idCita, idUsuario, fechaCita, horaCita, idMascota, descripcion);
						citasPasadas.add(cita);	
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return citasPasadas;
	}


	public List<Cita> obtenerCitasFuturas(int idMascota) {
		List<Cita> citasPasadas = new ArrayList<>();

		try {
			// Obtener la fecha y hora actual
			Calendar calendario = Calendar.getInstance();
			java.sql.Date fechaActual = new java.sql.Date(calendario.getTime().getTime());

			// Crear la consulta SQL para obtener citas pasadas
			String query = "SELECT * FROM Cita WHERE fechaCita > ? AND idMascota = ?";

			// Preparar la declaración SQL
			try (PreparedStatement st = conexion.prepareStatement(query)) {
				st.setDate(1, fechaActual);
				st.setInt(2, idMascota);

				// Ejecutar la consulta
				try (ResultSet rs = st.executeQuery()) {
					// Procesar los resultados
					while (rs.next()) {
						int idUsuario = rs.getInt("idUsuario");
						int idCita = rs.getInt("idCita");
						Date fechaCita = rs.getDate("fechaCita");
						Time horaCita = rs.getTime("horaCita");
						String descripcion = rs.getString("descripcionCita");

						Cita cita = new Cita(idCita, idUsuario, fechaCita, horaCita, idMascota, descripcion);
						citasPasadas.add(cita);	
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return citasPasadas;
	}



	public List<Map<String, Object>> obtenerFilasDeTabla(String nombreTabla, List<String> columnas, String condicion, Object... parametros) throws SQLException {

		List<Map<String, Object>> resultados = new ArrayList<>();

		try (PreparedStatement statement = construirConsulta(nombreTabla, columnas, condicion)) {
			configurarParametros(statement, parametros);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Map<String, Object> fila = new HashMap<>();
					for (String columna : columnas) {
						fila.put(columna, resultSet.getObject(columna));
					}
					resultados.add(fila);
				}
			}
		}

		return resultados;
	}

	public List<Map<String, Object>> obtenerTodasLasFilasDeTabla(String nombreTabla, List<String> columnas) throws SQLException {

		List<Map<String, Object>> resultados = new ArrayList<>();

		try (PreparedStatement statement = construirConsulta(nombreTabla, columnas, null)) {
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Map<String, Object> fila = new HashMap<>();
					for (String columna : columnas) {
						fila.put(columna, resultSet.getObject(columna));
					}
					resultados.add(fila);
				}
			}
		}

		return resultados;
	}


	private PreparedStatement construirConsulta(String nombreTabla, List<String> columnas, String condicion) throws SQLException {
		String columnasStr = String.join(", ", columnas);
		String query = "SELECT " + columnasStr + " FROM " + nombreTabla;

		if (condicion != null && !condicion.isEmpty()) {
			query += " WHERE " + condicion;
		}

		return conexion.prepareStatement(query);
	}

	private void configurarParametros(PreparedStatement statement, Object... parametros) throws SQLException {
		for (int i = 0; i < parametros.length; i++) {
			statement.setObject(i + 1, parametros[i]);
		}
	}


	public boolean actualizarFila(String nombreTabla, String nombreColumna, Object nuevoValor, String columnaCondicion, Object valorCondicion) {
		try {
			// Construir la consulta SQL
			String sql = "UPDATE " + nombreTabla +
					" SET " + nombreColumna + " = ?" +
					" WHERE " + columnaCondicion + " = ?";

			// Preparar la declaración SQL
			try (PreparedStatement preparedStatement = this.getConexion().prepareStatement(sql)) {
				// Establecer los parámetros en la consulta SQL
				preparedStatement.setObject(1, nuevoValor);
				preparedStatement.setObject(2, valorCondicion);

				// Ejecutar la consulta SQL
				int filasAfectadas = preparedStatement.executeUpdate();

				if (filasAfectadas > 0) {
					return true;
				} 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String catalogoToString() throws SQLException {
		List<Map<String, Object>> resultadosArticulo = obtenerTodasLasFilasDeTabla("Articulo", Arrays.asList("idArticulo", "nombre", "marca", "precio", "descripcionArticulo"));

		StringBuilder catalogoArticulos = new StringBuilder();
		
		catalogoArticulos.append("<<ARTICULOS>>\n\n");

		for (Map<String, Object> fila : resultadosArticulo) {
			
			StringBuilder aux = new StringBuilder();
			
			for (Map.Entry<String, Object> entry : fila.entrySet()) {

				String columna = entry.getKey();
				Object valor = entry.getValue();

				switch (columna) {
				case "idArticulo":
					catalogoArticulos.append(" ID del Artículo").append(": ").append(valor).append("\n");
					break;
				case "marca":
					aux.append(" Marca").append(": ").append(valor).append("\n");
					break;	
				case "precio":
					aux.append(" Precio").append(": ").append(valor).append("€\n");
					break;
				case "descripcionArticulo":
					aux.append(" Descripción").append(": ").append(valor).append("\n");
					break;	
				case "nombre":
					catalogoArticulos.append(" Nombre").append(": ").append(valor).append("\n").append(aux.toString());
					break;

				}

			}
			catalogoArticulos.append("==============================================\n"); // Separador horizontal
		}
		
		
		
		List<Map<String, Object>> resultadosServicio = obtenerTodasLasFilasDeTabla("Servicio", Arrays.asList("idServicio", "nombre", "precio", "descripcionServicio"));

		StringBuilder catalogoServicios = new StringBuilder();
		
		catalogoServicios.append("<<SERVICIOS>>\n\n");

		for (Map<String, Object> fila : resultadosServicio) {
			
			StringBuilder aux = new StringBuilder();
			
			for (Map.Entry<String, Object> entry : fila.entrySet()) {

				String columna = entry.getKey();
				Object valor = entry.getValue();

				switch (columna) {
				case "idServicio":
					catalogoServicios.append(" ID del Artículo").append(": ").append(valor).append("\n");
					break;
				case "precio":
					aux.append(" Precio").append(": ").append(valor).append("€\n");
					break;
				case "descripcionServicio":
					aux.append(" Descripción").append(": ").append(valor).append("\n");
					break;	
				case "nombre":
					catalogoServicios.append(" Nombre").append(": ").append(valor).append("\n").append(aux.toString());
					break;

				}

			}
			catalogoServicios.append("==============================================\n"); // Separador horizontal
		}


		return catalogoServicios.toString() + catalogoArticulos.toString();
	}
}
