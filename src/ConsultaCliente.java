import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ConsultaCliente extends JFrame implements ActionListener {

    private JTextField txtIdBuscar, txtNombre, txtNumIdentificacion, txtFechaNacimiento, txtGenero, txtDireccion, txtNumTelefono, txtCorreo;
    private JButton btnBuscar, btnModificar, btnEliminar, btnCerrar;

    public ConsultaCliente() {
        // Configuración de la ventana
        setTitle("\uD83D\uDC65 Consulta y Modificación de Clientes");  // Emoji para el icono de cliente
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configura el fondo con color #0CABA8
        getContentPane().setBackground(new Color(12, 171, 168));

        // Crear componentes
        txtIdBuscar = new JTextField(15);
        txtNombre = new JTextField(20);
        txtNumIdentificacion = new JTextField(15);
        txtFechaNacimiento = new JTextField(10);
        txtGenero = new JTextField(1);
        txtDireccion = new JTextField(20);
        txtNumTelefono = new JTextField(15);
        txtCorreo = new JTextField(20);

        btnBuscar = new JButton("\uD83D\uDD0D Buscar");  // Emoji para el icono de lupa
        btnModificar = new JButton("\uD83D\uDC65 Modificar");  // Emoji para el icono de cliente
        btnEliminar = new JButton("\uD83D\uDDD1 Eliminar");  // Emoji para el icono de papelera
        btnCerrar = new JButton("\u2190 Cerrar");  // Emoji para la flecha izquierda

        // Estilo de los botones
        btnBuscar.setBackground(new Color(46, 134, 193)); // Azul oscuro
        btnModificar.setBackground(new Color(36, 138, 61)); // Verde
        btnEliminar.setBackground(new Color(192, 57, 43));  // Rojo
        btnCerrar.setBackground(new Color(255, 193, 7));  // Amarillo
        btnBuscar.setForeground(Color.WHITE);
        btnModificar.setForeground(Color.WHITE);
        btnEliminar.setForeground(Color.WHITE);
        btnCerrar.setForeground(Color.WHITE);

        // Configurar la fuente para admitir emojis
        setUIFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));

        // Agregar oyentes de eventos a los botones
        btnBuscar.addActionListener(this);
        btnModificar.addActionListener(this);
        btnEliminar.addActionListener(this);
        btnCerrar.addActionListener(this);

        // Configurar el diseño de la interfaz
        setLayout(new GridLayout(20, 2));
        add(createLabel("\uD83D\uDC65 ID_cliente a buscar:"));
        add(txtIdBuscar);
        add(btnBuscar);
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
        add(btnModificar);
        add(btnEliminar);
        add(btnCerrar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBuscar) {
            buscarCliente();
        } else if (e.getSource() == btnModificar) {
            modificarCliente();
        } else if (e.getSource() == btnEliminar) {
            eliminarCliente();
        } else if (e.getSource() == btnCerrar) {
            volverAlMainInterfaz();
        }
    }

    private void buscarCliente() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/bd_discoteca";
        String usuario = "root";
        String contraseña = "";

        try {
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            // Buscar Cliente
            String consultaBuscar = "SELECT * FROM Clientes WHERE ID_cliente = ?";
            PreparedStatement statementBuscar = conexion.prepareStatement(consultaBuscar);
            statementBuscar.setInt(1, Integer.parseInt(txtIdBuscar.getText()));
            ResultSet resultadoBuscar = statementBuscar.executeQuery();

            // Mostrar los resultados en la interfaz
            if (resultadoBuscar.next()) {
                txtNombre.setText(resultadoBuscar.getString("Nombre_cliente"));
                txtNumIdentificacion.setText(resultadoBuscar.getString("Numero_identificacion"));
                txtFechaNacimiento.setText(resultadoBuscar.getString("Fecha_nacimiento"));
                txtGenero.setText(resultadoBuscar.getString("Genero"));
                txtDireccion.setText(resultadoBuscar.getString("Direccion"));
                txtNumTelefono.setText(resultadoBuscar.getString("Numero_telefono"));
                txtCorreo.setText(resultadoBuscar.getString("Correo_electronico"));
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado");
            }

            // Cerrar recursos
            statementBuscar.close();
            conexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void modificarCliente() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/bd_discoteca";
        String usuario = "root";
        String contraseña = "";

        try {
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            // Modificar Cliente
            String consultaModificar = "UPDATE Clientes SET Nombre_cliente = ?, Numero_identificacion = ?, Fecha_nacimiento = ?, Genero = ?, Direccion = ?, Numero_telefono = ?, Correo_electronico = ? WHERE ID_cliente = ?";
            PreparedStatement statementModificar = conexion.prepareStatement(consultaModificar);
            statementModificar.setString(1, txtNombre.getText());
            statementModificar.setString(2, txtNumIdentificacion.getText());
            statementModificar.setString(3, txtFechaNacimiento.getText());
            statementModificar.setString(4, txtGenero.getText());
            statementModificar.setString(5, txtDireccion.getText());
            statementModificar.setString(6, txtNumTelefono.getText());
            statementModificar.setString(7, txtCorreo.getText());
            statementModificar.setInt(8, Integer.parseInt(txtIdBuscar.getText()));

            int filasModificadas = statementModificar.executeUpdate();

            // Mostrar mensaje de éxito
            if (filasModificadas > 0) {
                JOptionPane.showMessageDialog(this, "Cliente modificado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo modificar el cliente");
            }

            // Cerrar recursos
            statementModificar.close();
            conexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void eliminarCliente() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/bd_discoteca";
        String usuario = "root";
        String contraseña = "";

        try {
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            // Eliminar Cliente
            String consultaEliminar = "DELETE FROM Clientes WHERE ID_cliente = ?";
            PreparedStatement statementEliminar = conexion.prepareStatement(consultaEliminar);
            statementEliminar.setInt(1, Integer.parseInt(txtIdBuscar.getText()));
            int filasEliminadas = statementEliminar.executeUpdate();

            // Mostrar mensaje de éxito
            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente");
                // Limpiar los campos después de eliminar
                txtNombre.setText("");
                txtNumIdentificacion.setText("");
                txtFechaNacimiento.setText("");
                txtGenero.setText("");
                txtDireccion.setText("");
                txtNumTelefono.setText("");
                txtCorreo.setText("");
                txtIdBuscar.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el cliente");
            }

            // Cerrar recursos
            statementEliminar.close();
            conexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void volverAlMainInterfaz() {
        this.dispose();  // Cierra la interfaz actual (ConsultaCliente)
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
            ConsultaCliente consultaCliente = new ConsultaCliente();
            consultaCliente.setVisible(true);
        });
    }
}
