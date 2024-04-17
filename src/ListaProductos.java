import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListaProductos extends JFrame {

    private JTextArea txtResultados;

    public ListaProductos() {
        setTitle("\uD83C\uDF7E Consulta de Productos");  // Título de la ventana
        setSize(600, 400);  // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Cierre de la aplicación al cerrar la ventana
        setIconImage(new ImageIcon("icono.png").getImage());  // Icono de la aplicación

        getContentPane().setBackground(new Color(12, 171, 168));  // Fondo de la ventana

        txtResultados = new JTextArea(10, 40);  // Área de texto para mostrar los resultados
        txtResultados.setEditable(false);  // Hacer el área de texto no editable
        txtResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));  // Establecer la fuente del texto

        JButton btnConsultar = new JButton("\uD83D\uDCB3 Consultar");  // Botón para consultar productos
        btnConsultar.setBackground(new Color(41, 128, 185));  // Fondo azul del botón
        btnConsultar.setForeground(Color.WHITE);  // Texto blanco del botón

        JButton btnCerrar = new JButton("\uD83D\uDD34 Cerrar");  // Botón para cerrar la ventana
        btnCerrar.setBackground(new Color(192, 57, 43));  // Fondo rojo del botón
        btnCerrar.setForeground(Color.WHITE);  // Texto blanco del botón

        // Asociar acciones a los botones mediante expresiones lambda
        btnConsultar.addActionListener(e -> consultarProductos());
        btnCerrar.addActionListener(e -> cerrarVentana());

        JPanel panelBotones = new JPanel();  // Panel para contener los botones
        panelBotones.add(btnConsultar);
        panelBotones.add(btnCerrar);

        setLayout(new BorderLayout());  // Establecer el diseño de la ventana como BorderLayout
        add(new JScrollPane(txtResultados), BorderLayout.CENTER);  // Agregar el área de texto con barra de desplazamiento
        add(panelBotones, BorderLayout.SOUTH);  // Agregar el panel de botones en la parte inferior
    }

    private void consultarProductos() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/bd_discoteca";
        String usuario = "root";
        String contraseña = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Cargar el controlador JDBC
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);  // Establecer conexión con la base de datos

            String consulta = "SELECT * FROM Productos";  // Consulta SQL para seleccionar todos los productos
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultSet = statement.executeQuery();  // Ejecutar la consulta

            StringBuilder resultados = new StringBuilder("Resultados:\n\n");  // Crear un StringBuilder para almacenar los resultados
            while (resultSet.next()) {
                resultados.append("ID Producto: ").append(resultSet.getInt("ID_producto")).append("\n")
                        .append("Nombre: ").append(resultSet.getString("Nombre_producto")).append("\n")
                        .append("Categoría: ").append(resultSet.getString("Categoria_producto")).append("\n")
                        .append("Precio Unitario: Bs.").append(resultSet.getBigDecimal("Precio_unitario")).append("\n")
                        .append("------------------------------------\n");
            }

            txtResultados.setText(resultados.toString());  // Mostrar los resultados en el área de texto

            resultSet.close();
            statement.close();
            conexion.close();  // Cerrar recursos

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void cerrarVentana() {
        this.dispose();  // Cerrar la ventana
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ListaProductos consultaProductos = new ListaProductos();
            consultaProductos.setVisible(true);
        });
    }
}
