package VetTrack;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDAO extends ConexionBD {

	public List<Articulo> listar() throws Exception {
		List<Articulo> listaArticulos = new ArrayList<>();

		try {
			// Abrir la conexión antes de realizar la operación en la base de datos
			this.abrirConexion();

			String query = "SELECT * FROM Articulo";
			PreparedStatement st = this.getConexion().prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Articulo articulo = new Articulo();
				articulo.setidArticulo(rs.getInt("id"));
				articulo.setNombre(rs.getString("nombre"));
				articulo.setDescripcion(rs.getString("descripcion"));
				articulo.setMarca(rs.getString("marca"));
				listaArticulos.add(articulo);
			}
		} catch (Exception e) {
			throw new Exception("Error al listar articulos: " + e.getMessage());
		} finally {
			// Asegúrate de cerrar la conexión, independientemente de si hubo una excepción o no
			this.cerrarConexion();
		}

		return listaArticulos;
	}


	public Articulo obtenerArticulo(int id) throws Exception {
		String nombre = null;
		String marca = null;
		String descripcion = null;
		Articulo aux = null;

		try {
			// Abrir la conexión antes de realizar la operación en la base de datos
			this.abrirConexion();

			String query = "SELECT nombre, descripcion, marca FROM Articulo WHERE id = ?";
			PreparedStatement st = this.getConexion().prepareStatement(query);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				// Solo obtén el nombre si hay resultados en el conjunto de resultados
				nombre = rs.getString("nombre");
				System.out.println("Nombre del artículo: " + nombre);
				descripcion = rs.getString("descripcion");
				System.out.println("Descripcion del artículo: " + descripcion);
				marca = rs.getString("Marca");
				System.out.println("Marca del artículo: " + marca);
				
				aux = new Articulo(id, nombre, descripcion, marca);
			} else {
				// Podrías lanzar una excepción o manejar el caso en que no se encuentre ningún artículo
				System.out.println("No se encontró ningún artículo con el ID: " + id);
			}

		} catch (Exception e) {
			throw new Exception("Error al obtener el artículo: " + e.getMessage());
		} finally {
			// Asegúrate de cerrar la conexión, independientemente de si hubo una excepción o no
			this.cerrarConexion();
		}

		return aux;
	}



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
}
