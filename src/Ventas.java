import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Ventas extends JFrame implements ActionListener {

    private JTextField txtIdVenta, txtFechaHoraVenta, txtMontoTotalVenta;
    private JComboBox<String> cbClientes, cbEmpleados;
    private JButton btnGuardar, btnCerrar;

    public Ventas() {
        // Configuración de la ventana
        setTitle("\uD83D\uDCB3 Ventas");  // Emoji para el icono de ventas
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("icono.png").getImage());  // Reemplaza "icono.png" con la ruta de tu ícono

        // Configura el fondo con color #0CABA8
        getContentPane().setBackground(new Color(12, 171, 168));

        // Crear componentes
        txtIdVenta = new JTextField(10);
        txtFechaHoraVenta = new JTextField(20);
        txtMontoTotalVenta = new JTextField(10);
        cbClientes = new JComboBox<>();
        cbEmpleados = new JComboBox<>();

        btnGuardar = new JButton("\uD83D\uDCBE Guardar");  // Emoji para el icono de disco
        btnCerrar = new JButton("\uD83D\uDD34 Cerrar");    // Emoji para el icono de cerrar

        // Estilo de los botones
        btnGuardar.setBackground(new Color(36, 138, 61)); // Verde
        btnCerrar.setBackground(new Color(192, 57, 43));  // Rojo
        btnGuardar.setForeground(Color.WHITE);
        btnCerrar.setForeground(Color.WHITE);

        // Configurar la fuente para admitir emojis
        setUIFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));

        // Agregar oyentes de eventos a los botones
        btnGuardar.addActionListener(this);
        btnCerrar.addActionListener(this);

        // Configurar el diseño de la interfaz
        setLayout(new GridLayout(7, 2));
        add(createLabel("\uD83D\uDCB3 ID_venta:"));
        add(txtIdVenta);
        add(createLabel("\uD83D\uDD56 Fecha_hora_venta (AAAA-MM-DD HH:MM:SS):"));
        add(txtFechaHoraVenta);
        add(createLabel("\uD83D\uDCB8 Monto_total_venta:"));
        add(txtMontoTotalVenta);
        add(createLabel("\uD83D\uDC65 Cliente:"));
        add(cbClientes);
        add(createLabel("\uD83D\uDC68 Empleado:"));
        add(cbEmpleados);
        add(btnGuardar);
        add(btnCerrar);

        cargarClientes();
        cargarEmpleados();
    }

    private void cargarClientes() {
        // Cargar datos de clientes desde la base de datos
        String jdbcUrl = "jdbc:mysql://localhost:3306/bd_discoteca";
        String usuario = "root";
        String contraseña = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            String consulta = "SELECT ID_cliente, Nombre_cliente FROM Clientes";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idCliente = resultSet.getInt("ID_cliente");
                String nombreCliente = resultSet.getString("Nombre_cliente");
                cbClientes.addItem(idCliente + " - " + nombreCliente);
            }

            resultSet.close();
            statement.close();
            conexion.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarEmpleados() {
        // Cargar datos de empleados desde la base de datos
        String jdbcUrl = "jdbc:mysql://localhost:3306/bd_discoteca";
        String usuario = "root";
        String contraseña = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            String consulta = "SELECT ID_empleado, Nombre_empleado FROM Empleados";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idEmpleado = resultSet.getInt("ID_empleado");
                String nombreEmpleado = resultSet.getString("Nombre_empleado");
                cbEmpleados.addItem(idEmpleado + " - " + nombreEmpleado);
            }

            resultSet.close();
            statement.close();
            conexion.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGuardar) {
            guardarVenta();
        } else if (e.getSource() == btnCerrar) {
            cerrarVentana();
        }
    }

    private boolean validarFormatoFechaHora(String fechaHora) {
        try {
            // Intenta analizar la cadena de fecha y hora en un objeto Timestamp
            Timestamp.valueOf(fechaHora);
            return true;
        } catch (IllegalArgumentException e) {
            // Si ocurre una excepción, la cadena no tiene el formato correcto
            return false;
        }
    }

    private void guardarVenta() {
        String fechaHoraVenta = txtFechaHoraVenta.getText();

        // Validar el formato de la fecha y hora antes de intentar insertarlo en la base de datos
        if (!validarFormatoFechaHora(fechaHoraVenta)) {
            JOptionPane.showMessageDialog(this, "El formato de fecha y hora es incorrecto. Use AAAA-MM-DD HH:MM:SS");
            return; // Salir del método si el formato es incorrecto
        }

        String jdbcUrl = "jdbc:mysql://localhost:3306/bd_discoteca";
        String usuario = "root";
        String contraseña = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            String consulta = "INSERT INTO Ventas (ID_venta, ID_cliente, ID_empleado, Fecha_hora_venta, Monto_total_venta) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, Integer.parseInt(txtIdVenta.getText()));

            // Obtener el ID del cliente seleccionado
            String clienteSeleccionado = cbClientes.getSelectedItem().toString();
            int idCliente = Integer.parseInt(clienteSeleccionado.split(" - ")[0]);
            statement.setInt(2, idCliente);

            // Obtener el ID del empleado seleccionado
            String empleadoSeleccionado = cbEmpleados.getSelectedItem().toString();
            int idEmpleado = Integer.parseInt(empleadoSeleccionado.split(" - ")[0]);
            statement.setInt(3, idEmpleado);

            statement.setString(4, fechaHoraVenta);
            statement.setBigDecimal(5, new java.math.BigDecimal(txtMontoTotalVenta.getText()));

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Inserción exitosa");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo insertar la venta");
            }

            statement.close();
            conexion.close();

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void cerrarVentana() {
        // Cierra la interfaz actual (Ventas)
        this.dispose();
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.BLACK);
        return label;
    }

    private static void setUIFont(Font font) {
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            if (UIManager.get(key) instanceof Font) {
                UIManager.put(key, font);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Ventas interfazVentas = new Ventas();
            interfazVentas.setVisible(true);
        });
    }
}
