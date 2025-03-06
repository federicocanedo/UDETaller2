package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CuartoRequerimiento extends JFrame {
    private JTextField txtMatricula;
    private JButton btnBuscar;
    private JTextArea txtResultado;

    public CuartoRequerimiento() {
        setTitle("Listado de Paseos por Minivan");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior con campo de matrícula y botón
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout());

        panelSuperior.add(new JLabel("Matrícula:"));
        txtMatricula = new JTextField(10);
        panelSuperior.add(txtMatricula);

        btnBuscar = new JButton("Buscar");
        panelSuperior.add(btnBuscar);

        add(panelSuperior, BorderLayout.NORTH);

        // Área de resultados
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        add(new JScrollPane(txtResultado), BorderLayout.CENTER);
    }

    public String getMatricula() {
        return txtMatricula.getText().trim();
    }

    public void mostrarResultado(String texto) {
        txtResultado.setText(texto);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void addBuscarListener(ActionListener listener) {
        btnBuscar.addActionListener(listener);
    }
}
