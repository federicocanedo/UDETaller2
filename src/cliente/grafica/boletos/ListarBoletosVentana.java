package src.cliente.grafica.boletos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import src.logica.boletos.VOBoleto;
import src.logica.boletos.VOBoletoEspecial;

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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
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

        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Campo para ID de paseo
        JLabel lblPaseoId = new JLabel("ID Paseo:");
        txtPaseoId = new JTextField(15);
        
        // Checkboxes para tipo de boleto
        chkComunes = new JCheckBox("Mostrar Boletos Comunes");
        chkEspeciales = new JCheckBox("Mostrar Boletos Especiales");
        chkComunes.setSelected(true);
        chkEspeciales.setSelected(true);
        
        // Agregar componentes al panel de filtros
        panelFiltros.add(lblPaseoId);
        panelFiltros.add(txtPaseoId);
        panelFiltros.add(chkComunes);
        panelFiltros.add(chkEspeciales);
        
        // Botón de actualizar
        btnActualizar = new JButton("Buscar Boletos");
        btnActualizar.addActionListener(e -> actualizarLista());

        // Panel para el botón
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.add(btnActualizar);

        // Configurar el layout
        setLayout(new BorderLayout());
        add(panelFiltros, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);

        // Mostrar mensaje inicial
        modeloTabla.setRowCount(0);
        modeloTabla.addRow(new Object[]{"", "Ingrese un ID de paseo y presione Buscar Boletos", "", "", ""});
    }

    public void actualizarLista() {
        String paseoId = txtPaseoId.getText().trim();
        if (paseoId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un ID de paseo");
            return;
        }
        
        try {
            controlador.listarBoletos(paseoId, chkComunes.isSelected(), chkEspeciales.isSelected());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al listar boletos: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarBoletos(VOBoleto[] boletos) {
        modeloTabla.setRowCount(0);
        for (VOBoleto boleto : boletos) {
            Object[] fila = new Object[] {
                boleto.getId(),
                boleto.getP_nombre(),
                boleto.getP_edad(),
                boleto.getP_numCelular(),
                boleto instanceof VOBoletoEspecial ? 
                    ((VOBoletoEspecial) boleto).getDescuento() : ""
            };
            modeloTabla.addRow(fila);
        }
    }
} 