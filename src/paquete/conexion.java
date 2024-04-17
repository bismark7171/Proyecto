package paquete;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class conexion {

    public static void main(String[] args) {
        // Configuración de la conexión
        String jdbcUrl = "jdbc:mysql://localhost:3306/bd_discoteca";
        String usuario = "root";
        String contraseña = "";

        try {
            // Paso 1: Registrar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Paso 2: Establecer la conexión
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            // Paso 3: Crear una declaración
            Statement statement = conexion.createStatement();

            // Ejemplo de consulta SELECT
            String consulta = "SELECT * FROM clientes";
            ResultSet resultSet = statement.executeQuery(consulta);

            // Paso 4: Procesar los resultados
            while (resultSet.next()) {
                // Obtener datos de la fila actual
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");

                // Procesar los datos (en este ejemplo, simplemente imprimirlos)
                System.out.println("ID: " + id + ", Nombre: " + nombre);
            }

            // Paso 5: Cerrar la conexión
            resultSet.close();
            statement.close();
            conexion.close();

            // Mensaje de conexión exitosa
            System.out.println("Conexión exitosa");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
