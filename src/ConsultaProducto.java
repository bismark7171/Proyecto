import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaProducto extends JFrame implements ActionListener {

    private JComboBox<String> cmbProductos;
    private JTextField txtNuevoPrecio;
    private JButton btnActualizar, btnCerrar;
    private JLabel lblPrecioActual;

    public ConsultaProducto() {
        // Configuración de la ventana
        setTitle("Consulta de Productos");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear componentes
        cmbProductos = new JComboBox<>();
        txtNuevoPrecio = new JTextField(10);
        btnActualizar = new JButton("✨ Actualizar Precio");
        btnCerrar = new JButton("❌ Cerrar");
        lblPrecioActual = new JLabel("Precio Actual: ");

        // Estilo de los botones
        btnActualizar.setBackground(new Color(255, 193, 7)); // Amarillo
        btnCerrar.setBackground(new Color(192, 57, 43));      // Rojo
        btnActualizar.setForeground(Color.BLACK);
        btnCerrar.setForeground(Color.WHITE);

        // Agregar oyentes de eventos a los botones
        btnActualizar.addActionListener(this);
        btnCerrar.addActionListener(this);

        // Configurar el diseño de la interfaz
        setLayout(new GridLayout(4, 2));
        add(new JLabel("Seleccionar Producto:"));
        add(cmbProductos);
        add(new JLabel("Nuevo Precio:"));
        add(txtNuevoPrecio);
        add(lblPrecioActual);
        add(new JLabel());  // Espacio en blanco para mantener el diseño
        add(btnActualizar);
        add(btnCerrar);

        // Llenar el JComboBox con los nombres de los productos
        llenarComboBoxProductos();
    }

    private void llenarComboBoxProductos() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/bd_discoteca";
        String usuario = "root";
        String contraseña = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            String consulta = "SELECT Nombre_producto FROM Productos";
            PreparedStatement statement = conexion.prepareStatement(consulta);

            ResultSet resultSet = statement.executeQuery();

            // Limpiar el JComboBox antes de agregar nuevos elementos
            cmbProductos.removeAllItems();

            // Agregar los nombres de los productos al JComboBox
            while (resultSet.next()) {
                String nombreProducto = resultSet.getString("Nombre_producto");
                cmbProductos.addItem(nombreProducto);
            }

            statement.close();
            resultSet.close();
            conexion.close();

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnActualizar) {
            actualizarPrecio();
        } else if (e.getSource() == btnCerrar) {
            cerrarVentana();
        }
    }

    private void actualizarPrecio() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/bd_discoteca";
        String usuario = "root";
        String contraseña = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            String consultaUpdate = "UPDATE Productos SET Precio_unitario = ? WHERE Nombre_producto = ?";
            String consultaSelect = "SELECT Precio_unitario FROM Productos WHERE Nombre_producto = ?";

            // Realizar la actualización
            PreparedStatement updateStatement = conexion.prepareStatement(consultaUpdate);
            updateStatement.setBigDecimal(1, new BigDecimal(txtNuevoPrecio.getText()));
            updateStatement.setString(2, cmbProductos.getSelectedItem().toString());

            int filasAfectadas = updateStatement.executeUpdate();

            if (filasAfectadas > 0) {
                // Mostrar un mensaje de actualización exitosa

                // Realizar la consulta para obtener el precio actualizado
                PreparedStatement selectStatement = conexion.prepareStatement(consultaSelect);
                selectStatement.setString(1, cmbProductos.getSelectedItem().toString());
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {
                    BigDecimal nuevoPrecio = resultSet.getBigDecimal("Precio_unitario");

                    // Mostrar el precio actualizado y actualizar el JLabel
                    JOptionPane.showMessageDialog(this, "✅ Actualización de precio exitosa.\nPrecio actualizado: " + nuevoPrecio,
                            "Información", JOptionPane.INFORMATION_MESSAGE);

                    lblPrecioActual.setText("Precio Actual: " + nuevoPrecio);  // Actualizar el JLabel
                }

                selectStatement.close();
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo actualizar el precio del producto", "Error", JOptionPane.ERROR_MESSAGE);
            }

            updateStatement.close();
            conexion.close();

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void cerrarVentana() {
        // Cierra la interfaz actual (ConsultaProducto)
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConsultaProducto consultaProducto = new ConsultaProducto();
            consultaProducto.setVisible(true);
        });
    }
}
