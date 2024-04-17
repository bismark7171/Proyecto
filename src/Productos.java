import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Productos extends JFrame implements ActionListener {

    private JTextField txtIdProducto, txtNombreProducto, txtCategoriaProducto, txtPrecioUnitario;
    private JButton btnGuardar, btnCerrar;

    public Productos() {
        // Configuración de la ventana
        setTitle("\uD83C\uDF7E Productos");  // Emoji para el icono de productos
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("icono.png").getImage());  // Reemplaza "icono.png" con la ruta de tu ícono

        // Configura el fondo con color #0CABA8
        getContentPane().setBackground(new Color(12, 171, 168));

        // Crear componentes
        txtIdProducto = new JTextField(10);
        txtNombreProducto = new JTextField(20);
        txtCategoriaProducto = new JTextField(20);
        txtPrecioUnitario = new JTextField(10);

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
        setLayout(new GridLayout(5, 2));
        add(createLabel("\uD83C\uDF7E ID_producto:"));
        add(txtIdProducto);
        add(createLabel("\uD83D\uDCDD Nombre_producto:"));
        add(txtNombreProducto);
        add(createLabel("\uD83D\uDCB3 Categoria_producto:"));
        add(txtCategoriaProducto);
        add(createLabel("\uD83D\uDCB8 Precio_unitario:"));
        add(txtPrecioUnitario);
        add(btnGuardar);
        add(btnCerrar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGuardar) {
            guardarProducto();
        } else if (e.getSource() == btnCerrar) {
            cerrarVentana();
        }
    }

    private void guardarProducto() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/bd_discoteca";
        String usuario = "root";
        String contraseña = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            String consulta = "INSERT INTO Productos (ID_producto, Nombre_producto, Categoria_producto, Precio_unitario) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, Integer.parseInt(txtIdProducto.getText()));
            statement.setString(2, txtNombreProducto.getText());
            statement.setString(3, txtCategoriaProducto.getText());
            statement.setBigDecimal(4, new java.math.BigDecimal(txtPrecioUnitario.getText()));

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Inserción exitosa");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo insertar el producto");
            }

            statement.close();
            conexion.close();

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void cerrarVentana() {
        // Cierra la interfaz actual (Productos)
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
            Productos interfazProductos = new Productos();
            interfazProductos.setVisible(true);
        });
    }
}
