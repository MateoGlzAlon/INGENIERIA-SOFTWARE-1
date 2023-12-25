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

    public void eliminar(Usuario usuario) throws Exception {
        try {
            this.abrirConexion();
            String query = "DELETE FROM usuar"
            		+ "ios WHERE idUsuario = ?";
            PreparedStatement st = this.getConexion().prepareStatement(query);
            st.setInt(1, usuario.getIdUsuario());
            st.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Error al eliminar un usuario: " + e.getMessage());
        } finally {
            this.cerrarConexion();
        }
    }
}
