package src.cliente.grafica.minivans;

import javax.swing.*;
import java.awt.*;

public class RegistrarMinivanVentana extends JFrame {
    private final RegistrarMinivanController controlador;
    private JTextField txtMatricula;
    private JTextField txtMarca;
    private JTextField txtModelo;
    private JTextField txtAsientos;
    private JButton btnRegistrar;

    public RegistrarMinivanVentana() {
        this.controlador = new RegistrarMinivanController(this);
        inicializarVentana();
    }

    private void inicializarVentana() {
        setTitle("Registrar Minivan");
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
        panelPrincipal.add(new JLabel("Matrícula:"), gbc);
        
        gbc.gridx = 1;
        txtMatricula = new JTextField(20);
        panelPrincipal.add(txtMatricula, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelPrincipal.add(new JLabel("Marca:"), gbc);
        
        gbc.gridx = 1;
        txtMarca = new JTextField(20);
        panelPrincipal.add(txtMarca, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelPrincipal.add(new JLabel("Modelo:"), gbc);
        
        gbc.gridx = 1;
        txtModelo = new JTextField(20);
        panelPrincipal.add(txtModelo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelPrincipal.add(new JLabel("Cantidad de Asientos:"), gbc);
        
        gbc.gridx = 1;
        txtAsientos = new JTextField(20);
        panelPrincipal.add(txtAsientos, gbc);

        // Botón de registro
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnRegistrar = new JButton("Registrar Minivan");
        btnRegistrar.addActionListener(e -> controlador.registrarMinivan());
        panelPrincipal.add(btnRegistrar, gbc);

        add(panelPrincipal);
    }

    public String getMatricula() {
        return txtMatricula.getText().trim();
    }

    public String getMarca() {
        return txtMarca.getText().trim();
    }

    public String getModelo() {
        return txtModelo.getText().trim();
    }

    public int getAsientos() {
        try {
            return Integer.parseInt(txtAsientos.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtMatricula.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtAsientos.setText("");
    }
}
