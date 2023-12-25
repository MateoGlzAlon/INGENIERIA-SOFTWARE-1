package VetTrack;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {

    private String dataBaseURL;
    private String driverName;
    private String user;
    private String pass;
    private Connection conexion;

    public String getDataBaseURL() {
        return dataBaseURL;
    }

    // Agrega otros getters & setters para los parámetros de la clase

    public ConexionBD() {
        this.dataBaseURL = "jdbc:mysql://viaduct.proxy.rlwy.net:18593/railway?autoReconnect=true&useSSL=true";
        this.driverName = "com.mysql.cj.jdbc.Driver";
        this.user = "root";
        this.pass = "cc3BCfgdba-B3-hGA5EgGHCDHdBd-aC-";
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
}
