import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Proveedores extends JFrame implements ActionListener {

    private JTextField txtIdProveedor, txtNombreProveedor, txtDireccion, txtNumTelefonoContacto;
    private JButton btnGuardar, btnCerrar;

    public Proveedores() {
        // Configuración de la ventana
        setTitle("\uD83D\uDC64 Proveedores");  // Emoji para el icono de proveedores
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("icono.png").getImage());  // Reemplaza "icono.png" con la ruta de tu ícono

        // Configura el fondo con color #0CABA8
        getContentPane().setBackground(new Color(12, 171, 168));

        // Crear componentes
        txtIdProveedor = new JTextField(10);
        txtNombreProveedor = new JTextField(20);
        txtDireccion = new JTextField(20);
        txtNumTelefonoContacto = new JTextField(15);

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
        add(createLabel("\uD83D\uDC64 ID_proveedor:"));
        add(txtIdProveedor);
        add(createLabel("\uD83D\uDC65 Nombre_proveedor:"));
        add(txtNombreProveedor);
        add(createLabel("\uD83D\uDCC2 Direccion:"));
        add(txtDireccion);
        add(createLabel("\uD83D\uDCDE Numero_telefono_contacto:"));
        add(txtNumTelefonoContacto);
        add(btnGuardar);
        add(btnCerrar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGuardar) {
            guardarProveedor();
        } else if (e.getSource() == btnCerrar) {
            cerrarVentana();
        }
    }

    private void guardarProveedor() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/bd_discoteca";
        String usuario = "root";
        String contraseña = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            String consulta = "INSERT INTO Proveedores (ID_proveedor, Nombre_proveedor, Direccion, Numero_telefono_contacto) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, Integer.parseInt(txtIdProveedor.getText()));
            statement.setString(2, txtNombreProveedor.getText());
            statement.setString(3, txtDireccion.getText());
            statement.setString(4, txtNumTelefonoContacto.getText());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Inserción exitosa");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo insertar el proveedor");
            }

            statement.close();
            conexion.close();

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void cerrarVentana() {
        // Cierra la interfaz actual (Proveedores)
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
            Proveedores interfazProveedores = new Proveedores();
            interfazProveedores.setVisible(true);
        });
    }
}
