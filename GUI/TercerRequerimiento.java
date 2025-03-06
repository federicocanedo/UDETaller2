package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalTime;

public class TercerRequerimiento extends JFrame {
    private JTextField txtCodigo, txtDestino, txtHoraPartida, txtHoraRegreso, txtPrecioBase;
    private JButton btnRegistrar;

    public TercerRequerimiento() {
        // Configuración del JFrame
        setTitle("Registro de Paseo");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal con GridLayout (filas x 2 columnas)
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5)); // 6 filas, 2 columnas, con espaciado

        // Crear los componentes
        JLabel lblCodigo = new JLabel("Código:");
        txtCodigo = new JTextField(10);

        JLabel lblDestino = new JLabel("Destino:");
        txtDestino = new JTextField(10);

        JLabel lblHoraPartida = new JLabel("Hora Partida:");
        txtHoraPartida = new JTextField(10);

        JLabel lblHoraRegreso = new JLabel("Hora Regreso:");
        txtHoraRegreso = new JTextField(10);

        JLabel lblPrecioBase = new JLabel("Precio Base:");
        txtPrecioBase = new JTextField(10);

        btnRegistrar = new JButton("Registrar");

        // Agregar componentes al panel
        panel.add(lblCodigo);
        panel.add(txtCodigo);

        panel.add(lblDestino);
        panel.add(txtDestino);

        panel.add(lblHoraPartida);
        panel.add(txtHoraPartida);

        panel.add(lblHoraRegreso);
        panel.add(txtHoraRegreso);

        panel.add(lblPrecioBase);
        panel.add(txtPrecioBase);

        // Panel para centrar el botón
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnRegistrar);

        // Agregar los paneles al frame
        add(panel, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);
    }

    // Métodos para obtener los datos del formulario
    public String getCodigo() {
        return txtCodigo.getText();
    }

    public String getDestino() {
        return txtDestino.getText();
    }

    public LocalTime getHoraPartida() {
        return LocalTime.parse(txtHoraPartida.getText(), java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
    }

    public LocalTime getHoraRegreso() {
        return LocalTime.parse(txtHoraRegreso.getText(), java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
    }

    public double getPrecioBase() {
        return Double.parseDouble(txtPrecioBase.getText());
    }

    // Método para mostrar un mensaje en la ventana
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Método para agregar el ActionListener al botón "Registrar"
    public void addRegistrarListener(ActionListener listener) {
        btnRegistrar.addActionListener(listener);
    }
}
