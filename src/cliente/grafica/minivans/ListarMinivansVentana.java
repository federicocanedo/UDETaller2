package src.cliente.grafica.minivans;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import src.logica.minivan.VOMinivan;

public class ListarMinivansVentana extends JFrame {
    private final ListarMinivansController controlador;
    private JTable tablaMinivans;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar;

    public ListarMinivansVentana() {
        this.controlador = new ListarMinivansController(this);
        inicializarVentana();
    }

    private void inicializarVentana() {
        setTitle("Listado de Minivans");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Crear el modelo de la tabla
        String[] columnas = {"Matrícula", "Marca", "Modelo", "Capacidad", "Paseos Asignados"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Crear la tabla
        tablaMinivans = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaMinivans);
        scrollPane.setPreferredSize(new Dimension(750, 500));

        // Crear botón de actualizar
        btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> actualizarLista());

        // Configurar el layout
        Container contenedor = getContentPane();
        contenedor.setLayout(new BorderLayout());
        contenedor.add(scrollPane, BorderLayout.CENTER);
        contenedor.add(btnActualizar, BorderLayout.SOUTH);

        // Actualizar lista inicial
        actualizarLista();
    }

    public void actualizarLista() {
        try {
            controlador.listarMinivans();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al listar minivans: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarMinivans(VOMinivan[] minivans) {
        modeloTabla.setRowCount(0);
        for (VOMinivan minivan : minivans) {
            Object[] fila = new Object[] {
                minivan.getMatricula(),
                minivan.getMarca(),
                minivan.getModelo(),
                minivan.getCapacidad(),
                minivan.getCantAsignados()
            };
            modeloTabla.addRow(fila);
        }
    }
} 