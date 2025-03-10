package src.cliente.grafica.paseos;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

public class RegistrarPaseoVentana extends JFrame {
    private final RegistrarPaseoController controlador;
    private JTextField txtId;
    private JTextField txtDestino;
    private JTextField txtHoraPartida;
    private JTextField txtHoraRegreso;
    private JTextField txtPrecioBase;
    private JButton btnRegistrar;

    public RegistrarPaseoVentana() {
        this.controlador = new RegistrarPaseoController(this);
        inicializarVentana();
    }

    private void inicializarVentana() {
        setTitle("Registrar Paseo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos de entrada
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelPrincipal.add(new JLabel("ID:"), gbc);
        
        gbc.gridx = 1;
        txtId = new JTextField(20);
        panelPrincipal.add(txtId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelPrincipal.add(new JLabel("Destino:"), gbc);
        
        gbc.gridx = 1;
        txtDestino = new JTextField(20);
        panelPrincipal.add(txtDestino, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelPrincipal.add(new JLabel("Hora Partida (HH:mm):"), gbc);
        
        gbc.gridx = 1;
        txtHoraPartida = new JTextField(20);
        panelPrincipal.add(txtHoraPartida, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelPrincipal.add(new JLabel("Hora Regreso (HH:mm):"), gbc);
        
        gbc.gridx = 1;
        txtHoraRegreso = new JTextField(20);
        panelPrincipal.add(txtHoraRegreso, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelPrincipal.add(new JLabel("Precio Base:"), gbc);
        
        gbc.gridx = 1;
        txtPrecioBase = new JTextField(20);
        panelPrincipal.add(txtPrecioBase, gbc);

        // Botón de registro
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnRegistrar = new JButton("Registrar Paseo");
        btnRegistrar.addActionListener(e -> controlador.registrarPaseo());
        panelPrincipal.add(btnRegistrar, gbc);

        add(panelPrincipal);
    }

    public String getId() {
        return txtId.getText().trim();
    }

    public String getDestino() {
        return txtDestino.getText().trim();
    }

    public LocalTime getHoraPartida() {
        try {
            String[] partes = txtHoraPartida.getText().trim().split(":");
            return LocalTime.of(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]));
        } catch (Exception e) {
            return null;
        }
    }

    public LocalTime getHoraRegreso() {
        try {
            String[] partes = txtHoraRegreso.getText().trim().split(":");
            return LocalTime.of(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]));
        } catch (Exception e) {
            return null;
        }
    }

    public int getPrecioBase() {
        try {
            return Integer.parseInt(txtPrecioBase.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtId.setText("");
        txtDestino.setText("");
        txtHoraPartida.setText("");
        txtHoraRegreso.setText("");
        txtPrecioBase.setText("");
    }
}
