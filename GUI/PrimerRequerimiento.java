package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PrimerRequerimiento extends JFrame {
    private JTextField txtMatricula, txtMarca, txtModelo, txtAsientos;
    private JButton btnRegistrar;
    
    public PrimerRequerimiento() {
        setTitle("Registro de Miniván");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240)); // Fondo gris claro
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // Estilos de fuente
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        // Componentes
        JLabel lblMatricula = new JLabel("Matrícula:");
        lblMatricula.setFont(labelFont);
        panel.add(lblMatricula, gbc);

        txtMatricula = new JTextField(15);
        txtMatricula.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(txtMatricula, gbc);

        gbc.gridx = 0;
        JLabel lblMarca = new JLabel("Marca:");
        lblMarca.setFont(labelFont);
        panel.add(lblMarca, gbc);

        txtMarca = new JTextField(15);
        txtMarca.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(txtMarca, gbc);

        gbc.gridx = 0;
        JLabel lblModelo = new JLabel("Modelo:");
        lblModelo.setFont(labelFont);
        panel.add(lblModelo, gbc);

        txtModelo = new JTextField(15);
        txtModelo.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(txtModelo, gbc);

        gbc.gridx = 0;
        JLabel lblAsientos = new JLabel("Asientos:");
        lblAsientos.setFont(labelFont);
        panel.add(lblAsientos, gbc);

        txtAsientos = new JTextField(15);
        txtAsientos.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(txtAsientos, gbc);

        // Botón estilizado
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setBackground(new Color(100, 180, 255)); // Azul claro
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        btnRegistrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistrar.setBackground(new Color(70, 160, 230));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistrar.setBackground(new Color(100, 180, 255));
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(btnRegistrar, gbc);

        // Agregar panel al frame
        add(panel);
    }

    public String getMatricula() {
        return txtMatricula.getText();
    }

    public String getMarca() {
        return txtMarca.getText();
    }

    public String getModelo() {
        return txtModelo.getText();
    }

    public int getAsientos() {
        try {
            return Integer.parseInt(txtAsientos.getText());
        } catch (NumberFormatException e) {
            return -1; // Indica que el número no es válido
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void addRegistrarListener(ActionListener listener) {
        btnRegistrar.addActionListener(listener);
    }
}
