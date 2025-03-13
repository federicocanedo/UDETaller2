package src.cliente.grafica.boletos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrarBoletoVentana extends JFrame {
    private RegistrarBoletoController controlador;
    
    private JTextField txtPaseoId;
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JTextField txtCelular;
    private JCheckBox chkEspecial;
    private JTextField txtDescuento;
    private JButton btnRegistrar;
    private JButton btnCancelar;

    public RegistrarBoletoVentana() {
        this.controlador = new RegistrarBoletoController(this);
        initComponents();
    }

    private void initComponents() {
        setTitle("Registrar Boleto / Register Ticket");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Paseo ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Código de Paseo:"), gbc);
        gbc.gridx = 1;
        txtPaseoId = new JTextField(20);
        panel.add(txtPaseoId, gbc);

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panel.add(txtNombre, gbc);

        // Edad
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Edad:"), gbc);
        gbc.gridx = 1;
        txtEdad = new JTextField(20);
        panel.add(txtEdad, gbc);

        // Celular
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Celular:"), gbc);
        gbc.gridx = 1;
        txtCelular = new JTextField(20);
        panel.add(txtCelular, gbc);

        // Boleto Especial
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        chkEspecial = new JCheckBox("Boleto Especial");
        panel.add(chkEspecial, gbc);

        // Descuento
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Descuento:"), gbc);
        gbc.gridx = 1;
        txtDescuento = new JTextField(20);
        txtDescuento.setEnabled(false);
        panel.add(txtDescuento, gbc);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        btnRegistrar = new JButton("Registrar");
        btnCancelar = new JButton("Cancelar");
        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        add(panel);

        // Event Listeners
        chkEspecial.addActionListener(e -> {
            txtDescuento.setEnabled(chkEspecial.isSelected());
            if (!chkEspecial.isSelected()) {
                txtDescuento.setText("");
            }
        });

        btnRegistrar.addActionListener(e -> {
            try {
                String paseoId = txtPaseoId.getText().trim();
                String nombre = txtNombre.getText().trim();
                
                if (paseoId.isEmpty() || nombre.isEmpty()) {
                    mostrarError("El código de paseo y el nombre son obligatorios");
                    return;
                }

                int edad;
                try {
                    edad = Integer.parseInt(txtEdad.getText().trim());
                } catch (NumberFormatException ex) {
                    mostrarError("La edad debe ser un número válido");
                    return;
                }

                String celular = txtCelular.getText().trim();
                boolean esEspecial = chkEspecial.isSelected();
                int descuento = 0;

                if (esEspecial) {
                    try {
                        descuento = Integer.parseInt(txtDescuento.getText().trim());
                    } catch (NumberFormatException ex) {
                        mostrarError("El descuento debe ser un número válido");
                        return;
                    }
                }

                controlador.registrarBoleto(paseoId, nombre, edad, celular, esEspecial, descuento);
            } catch (Exception ex) {
                mostrarError("Error al procesar los datos: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void limpiarCampos() {
        txtPaseoId.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        txtCelular.setText("");
        chkEspecial.setSelected(false);
        txtDescuento.setText("");
        txtDescuento.setEnabled(false);
    }
} 