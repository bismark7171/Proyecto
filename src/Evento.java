import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Evento extends JFrame implements ActionListener {

    private JTextField txtId, txtNombre, txtFechaHora, txtDescripcion, txtArtistasDJ, txtCapacidadMaxima, txtPrecioEntrada, txtEstado;
    private JButton btnGuardar, btnCerrar;

    public Evento() {
        // Configuración de la ventana
        setTitle("\uD83C\uDFB6 Evento");  // Emoji para el icono de evento
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("icono.png").getImage());  // Reemplaza "icono.png" con la ruta de tu ícono

        // Configura el fondo con color #0CABA8
        getContentPane().setBackground(new Color(12, 171, 168));

        // Crear componentes
        txtId = new JTextField(10);
        txtNombre = new JTextField(20);
        txtFechaHora = new JTextField(20);
        txtDescripcion = new JTextField(20);
        txtArtistasDJ = new JTextField(20);
        txtCapacidadMaxima = new JTextField(10);
        txtPrecioEntrada = new JTextField(10);
        txtEstado = new JTextField(20);

        btnGuardar = new JButton("Guardar");
        btnCerrar = new JButton("Cerrar");

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
        add(createLabel("\uD83C\uDFB6 ID_evento:"));
        add(txtId);
        add(createLabel("\uD83D\uDCDD Nombre_evento:"));
        add(txtNombre);
        add(createLabel("\uD83D\uDD56 Fecha_hora_evento (AAAA-MM-DD HH:MM:SS):"));
        add(txtFechaHora);
        add(createLabel("\uD83D\uDCDD Descripcion_evento:"));
        add(txtDescripcion);
        add(createLabel("\uD83D\uDD0E Artistas_DJ_invitados:"));
        add(txtArtistasDJ);
        add(createLabel("\uD83D\uDCB0 Capacidad_maxima_evento:"));
        add(txtCapacidadMaxima);
        add(createLabel("\uD83D\uDCB5 Precio_entrada:"));
        add(txtPrecioEntrada);
        add(createLabel("\uD83D\uDD0E Estado_evento:"));
        add(txtEstado);
        add(btnGuardar);
        add(btnCerrar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGuardar) {
            guardarEvento();
        } else if (e.getSource() == btnCerrar) {
            cerrarVentana();
        }
    }

    private void guardarEvento() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/bd_discoteca";
        String usuario = "root";
        String contraseña = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            String consulta = "INSERT INTO Eventos (ID_evento, Nombre_evento, Fecha_hora_evento, Descripcion_evento, Artistas_DJ_invitados, Capacidad_maxima_evento, Precio_entrada, Estado_evento) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, Integer.parseInt(txtId.getText()));
            statement.setString(2, txtNombre.getText());
            statement.setString(3, txtFechaHora.getText());
            statement.setString(4, txtDescripcion.getText());
            statement.setString(5, txtArtistasDJ.getText());
            statement.setInt(6, Integer.parseInt(txtCapacidadMaxima.getText()));
            statement.setBigDecimal(7, new java.math.BigDecimal(txtPrecioEntrada.getText()));
            statement.setString(8, txtEstado.getText());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Inserción exitosa");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo insertar el evento");
            }

            statement.close();
            conexion.close();

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void cerrarVentana() {
        // Cierra la interfaz actual (Evento)
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
            Evento interfazEvento = new Evento();
            interfazEvento.setVisible(true);
        });
    }
}
