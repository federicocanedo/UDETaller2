package src.cliente.ventanas.boletos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ListarBoletosVentana extends JFrame {
    private final ListarBoletosController controlador;
    private JTable tablaBoletos;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar;
    
    // Filtros
    private JTextField txtPaseoId;
    private JCheckBox chkComunes;
    private JCheckBox chkEspeciales;
    
    public ListarBoletosVentana() {
        this.controlador = new ListarBoletosController(this);
        inicializarVentana();
    }

    private void inicializarVentana() {
        setTitle("Listado de Boletos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Crear el modelo de la tabla
        String[] columnas = {"ID", "Nombre", "Edad", "Número Celular", "Descuento"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Crear la tabla
        tablaBoletos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaBoletos);
        scrollPane.setPreferredSize(new Dimension(750, 500));

        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Campo para ID de paseo
        txtPaseoId = new JTextField(10);
        JLabel lblPaseoId = new JLabel("ID Paseo:");
        
        // Checkboxes para tipo de boleto
        chkComunes = new JCheckBox("Comunes");
        chkEspeciales = new JCheckBox("Especiales");
        chkComunes.setSelected(true);
        chkEspeciales.setSelected(true);
        
        // Agregar componentes al panel de filtros
        panelFiltros.add(lblPaseoId);
        panelFiltros.add(txtPaseoId);
        panelFiltros.add(chkComunes);
        panelFiltros.add(chkEspeciales);
        
        // Botón de actualizar
        btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> actualizarLista());

        // Configurar el layout
        Container contenedor = getContentPane();
        contenedor.setLayout(new BorderLayout());
        contenedor.add(panelFiltros, BorderLayout.NORTH);
        contenedor.add(scrollPane, BorderLayout.CENTER);
        contenedor.add(btnActualizar, BorderLayout.SOUTH);
        
        // Actualizar lista inicial
        actualizarLista();
    }

    public void actualizarLista() {
        String paseoId = txtPaseoId.getText().trim();
        if (paseoId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un ID de paseo");
            return;
        }
        
        controlador.listarBoletos(paseoId, chkComunes.isSelected(), chkEspeciales.isSelected());
    }

    public void setDatosTabla(Object[][] datos) {
        modeloTabla.setRowCount(0);
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
    }
} 