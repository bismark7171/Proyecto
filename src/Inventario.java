import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Inventario extends JFrame implements ActionListener {

    private JTextField txtIdInventario, txtFechaRegistro;
    private JComboBox<String> cbProductos;
    private JButton btnGuardar, btnCerrar;

    public Inventario() {
        // Configuración de la ventana
        setTitle("\uD83D\uDCC2 Inventario");  // Emoji para el icono de inventario
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("icono.png").getImage());  // Reemplaza "icono.png" con la ruta de tu ícono

        // Configura el fondo con color #0CABA8
        getContentPane().setBackground(new Color(12, 171, 168));

        // Crear componentes
        txtIdInventario = new JTextField(10);
        txtFechaRegistro = new JTextField(20);
        cbProductos = new JComboBox<>();

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
        setLayout(new GridLayout(4, 2));
        add(createLabel("\uD83D\uDCC2 ID_inventario:"));
        add(txtIdInventario);
        add(createLabel("\uD83C\uDF4E Producto:"));
        add(cbProductos);
        add(createLabel("\uD83D\uDDD3 Fecha_registro (AAAA-MM-DD):"));
        add(txtFechaRegistro);
        add(btnGuardar);
        add(btnCerrar);

        cargarProductos();
    }

    private void cargarProductos() {
        // Lógica para cargar datos de productos desde la base de datos
        String jdbcUrl = "jdbc:mysql://localhost:3306/bd_discoteca";
        String usuario = "root";
        String contraseña = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            String consulta = "SELECT ID_producto, Nombre_producto FROM Productos";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idProducto = resultSet.getInt("ID_producto");
                String nombreProducto = resultSet.getString("Nombre_producto");
                cbProductos.addItem(idProducto + " - " + nombreProducto);
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
            guardarInventario();
        } else if (e.getSource() == btnCerrar) {
            cerrarVentana();
        }
    }

    private void guardarInventario() {
        String idInventarioText = txtIdInventario.getText();

        // Verificar si el campo de texto está vacío
        if (idInventarioText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa un valor para el ID de inventario");
            return;
        }

        // Convertir el texto a un entero
        int idInventario;
        try {
            idInventario = Integer.parseInt(idInventarioText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID de inventario debe ser un número entero");
            return;
        }

        // Continuar con el proceso de guardado
        String jdbcUrl = "jdbc:mysql://localhost:3306/bd_discoteca";
        String usuario = "root";
        String contraseña = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            String consulta = "INSERT INTO Inventario (ID_inventario, ID_producto, Fecha_registro) " +
                    "VALUES (?, ?, ?)";

            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, idInventario);

            // Obtener el ID del producto seleccionado
            int idProducto = Integer.parseInt(cbProductos.getSelectedItem().toString().split(" - ")[0]);
            statement.setInt(2, idProducto);

            statement.setString(3, txtFechaRegistro.getText());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Inserción exitosa");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo insertar en el inventario");
            }

            statement.close();
            conexion.close();

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void cerrarVentana() {
        // Cierra la interfaz actual (Inventario)
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
            Inventario interfazInventario = new Inventario();
            interfazInventario.setVisible(true);
        });
    }
}
