import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Cliente extends JFrame implements ActionListener {

    private JTextField txtId, txtNombre, txtNumIdentificacion, txtFechaNacimiento, txtGenero, txtDireccion, txtNumTelefono, txtCorreo;
    private JButton btnGuardar, btnCerrar;

    public Cliente() {
        // Configuración de la ventana
        setTitle("\uD83D\uDC65 Cliente");  // Emoji para el icono de cliente
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("icono.png").getImage());  // Reemplaza "icono.png" con la ruta de tu ícono

        // Configura el fondo con color #0CABA8
        getContentPane().setBackground(new Color(12, 171, 168));

        // Crear componentes
        txtId = new JTextField(10);
        txtNombre = new JTextField(20);
        txtNumIdentificacion = new JTextField(15);
        txtFechaNacimiento = new JTextField(10);
        txtGenero = new JTextField(1);
        txtDireccion = new JTextField(20);
        txtNumTelefono = new JTextField(15);
        txtCorreo = new JTextField(20);

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
        setLayout(new GridLayout(9, 2));
        add(createLabel("\uD83D\uDC65 ID_cliente:"));
        add(txtId);
        add(createLabel("\uD83D\uDC64 Nombre_cliente:"));
        add(txtNombre);
        add(createLabel("\uD83D\uDCB3 Numero_identificacion:"));
        add(txtNumIdentificacion);
        add(createLabel("\uD83D\uDCC5 Fecha_nacimiento (AAAA-MM-DD):"));
        add(txtFechaNacimiento);
        add(createLabel("\uD83D\uDC66 Genero:"));
        add(txtGenero);
        add(createLabel("\uD83C\uDFE0 Direccion:"));
        add(txtDireccion);
        add(createLabel("\uD83D\uDCDE Numero_telefono:"));
        add(txtNumTelefono);
        add(createLabel("\uD83D\uDCE7 Correo_electronico:"));
        add(txtCorreo);
        add(btnGuardar);
        add(btnCerrar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGuardar) {
            guardarCliente();
        } else if (e.getSource() == btnCerrar) {
            cerrarVentana();
        }
    }

    // Para guardar los atributos del cliente en la base de datos desde java
    private void guardarCliente() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/bd_discoteca";
        String usuario = "root";
        String contraseña = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            String consulta = "INSERT INTO Clientes (ID_cliente, Nombre_cliente, Numero_identificacion, Fecha_nacimiento, Genero, Direccion, Numero_telefono, Correo_electronico) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            //atributos del cliente
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, Integer.parseInt(txtId.getText()));
            statement.setString(2, txtNombre.getText());
            statement.setString(3, txtNumIdentificacion.getText());
            statement.setDate(4, java.sql.Date.valueOf(txtFechaNacimiento.getText()));
            statement.setString(5, txtGenero.getText());
            statement.setString(6, txtDireccion.getText());
            statement.setString(7, txtNumTelefono.getText());
            statement.setString(8, txtCorreo.getText());

            int filasAfectadas = statement.executeUpdate();
            // logica para confirmar si fue exitosa o no la insercion
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Inserción exitosa");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo insertar el cliente");
            }

            statement.close();
            conexion.close();

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void cerrarVentana() {
        // Cierra la interfaz actual (Cliente)
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
            Cliente interfazCliente = new Cliente();
            interfazCliente.setVisible(true);
        });
    }
}
