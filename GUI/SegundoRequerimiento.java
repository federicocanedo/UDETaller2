package GUI;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import logica.minivan.VOMinivan;
import logica.IFachada;

public class SegundoRequerimiento extends JFrame {

    private IFachada fachada;
    private JTable tableMinivans;
    private JButton btnListarMinivans;

    public SegundoRequerimiento(IFachada fachada) {
        this.fachada = fachada;
        initialize();
    }

    private void initialize() {
        setTitle("Listado de Minivans");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout
        setLayout(new BorderLayout());

        // Crear la tabla
        tableMinivans = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableMinivans);
        add(scrollPane, BorderLayout.CENTER);

        // Crear el botón de "Listar Minivans"
        btnListarMinivans = new JButton("Listar Minivans");
        btnListarMinivans.addActionListener(e -> listarMinivans());
        add(btnListarMinivans, BorderLayout.SOUTH);
    }

    private void listarMinivans() {
        try {
            // Llamar a la fachada para obtener las minivans
            VOMinivan[] minivans = fachada.listarMinivans();

            // Datos para la tabla
            String[] columnNames = {"Matrícula", "Marca", "Modelo", "Cantidad Asientos", "Paseos Asignados"};
            Object[][] data = new Object[minivans.length][5];

            // Llenar los datos de la tabla
            for (int i = 0; i < minivans.length; i++) {
                data[i][0] = minivans[i].getMatricula();
                data[i][1] = minivans[i].getMarca();
                data[i][2] = minivans[i].getModelo();
                data[i][3] = minivans[i].getCapacidad();
                data[i][4] = minivans[i].getCantAsignados();
            }

            // Crear el modelo de tabla
            tableMinivans.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));

        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(this, "Error al listar las minivans: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
