package src.cliente.grafica.paseos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import src.logica.paseo.VOPaseo;

public class ListarPaseosVentana extends JFrame {
    private final ListarPaseosController controlador;
    private JTable tablaPaseos;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar;
    
    // Filtros
    private JTextField txtMatricula;
    private JTextField txtDestino;
    private JTextField txtCantidadBoletos;
    private JComboBox<String> comboFiltro;
    
    public ListarPaseosVentana() {
        this.controlador = new ListarPaseosController(this);
        inicializarVentana();
    }

    private void inicializarVentana() {
        setTitle("Listado de Paseos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Crear el modelo de la tabla
        String[] columnas = {"ID", "Destino", "Hora Partida", "Hora Regreso", "Precio Base", "Cant. Max Boletos", "Boletos Disponibles"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Crear la tabla
        tablaPaseos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPaseos);
        scrollPane.setPreferredSize(new Dimension(950, 500));

        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Combo para seleccionar tipo de filtro
        String[] opcionesFiltro = {"Todos", "Por Matrícula", "Por Destino", "Por Disponibilidad"};
        comboFiltro = new JComboBox<>(opcionesFiltro);
        comboFiltro.addActionListener(e -> {
            actualizarVisibilidadFiltros();
            actualizarLista(); // Actualizar lista cuando cambia el filtro
        });
        
        // Campos de filtro
        txtMatricula = new JTextField(10);
        txtDestino = new JTextField(10);
        txtCantidadBoletos = new JTextField(5);
        
        // Etiquetas
        JLabel lblMatricula = new JLabel("Matrícula:");
        JLabel lblDestino = new JLabel("Destino:");
        JLabel lblCantidadBoletos = new JLabel("Cantidad Boletos:");
        
        // Agregar componentes al panel de filtros
        panelFiltros.add(new JLabel("Filtrar por:"));
        panelFiltros.add(comboFiltro);
        panelFiltros.add(lblMatricula);
        panelFiltros.add(txtMatricula);
        panelFiltros.add(lblDestino);
        panelFiltros.add(txtDestino);
        panelFiltros.add(lblCantidadBoletos);
        panelFiltros.add(txtCantidadBoletos);
        
        // Botón de actualizar
        btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> actualizarLista());

        // Configurar el layout
        Container contenedor = getContentPane();
        contenedor.setLayout(new BorderLayout());
        contenedor.add(panelFiltros, BorderLayout.NORTH);
        contenedor.add(scrollPane, BorderLayout.CENTER);
        contenedor.add(btnActualizar, BorderLayout.SOUTH);

        // Configurar visibilidad inicial de filtros
        actualizarVisibilidadFiltros();
        
        // Actualizar lista inicial
        actualizarLista();
    }

    private void actualizarVisibilidadFiltros() {
        String filtroSeleccionado = (String) comboFiltro.getSelectedItem();
        txtMatricula.setVisible("Por Matrícula".equals(filtroSeleccionado));
        txtDestino.setVisible("Por Destino".equals(filtroSeleccionado));
        txtCantidadBoletos.setVisible("Por Disponibilidad".equals(filtroSeleccionado));
    }

    public void actualizarLista() {
        try {
            String filtroSeleccionado = (String) comboFiltro.getSelectedItem();
            switch (filtroSeleccionado) {
                case "Por Matrícula":
                    String matricula = txtMatricula.getText().trim();
                    if (matricula.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Por favor ingrese una matrícula");
                        return;
                    }
                    controlador.listarPaseosPorMatricula(matricula);
                    break;
                case "Por Destino":
                    String destino = txtDestino.getText().trim();
                    if (destino.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Por favor ingrese un destino");
                        return;
                    }
                    controlador.listarPaseosPorDestino(destino);
                    break;
                case "Por Disponibilidad":
                    String cantidadStr = txtCantidadBoletos.getText().trim();
                    if (cantidadStr.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Por favor ingrese una cantidad de boletos");
                        return;
                    }
                    try {
                        int cantidad = Integer.parseInt(cantidadStr);
                        if (cantidad <= 0) {
                            JOptionPane.showMessageDialog(this, "La cantidad de boletos debe ser mayor a 0");
                            return;
                        }
                        controlador.listarPaseosPorDisponibilidad(cantidad);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "La cantidad de boletos debe ser un número válido");
                    }
                    break;
                default:
                    controlador.listarTodosLosPaseos();
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al listar paseos: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarPaseos(VOPaseo[] paseos) {
        modeloTabla.setRowCount(0);
        for (VOPaseo paseo : paseos) {
            Object[] fila = new Object[] {
                paseo.getId(),
                paseo.getDestino(),
                paseo.getHoraPartida(),
                paseo.getHoraRegreso(),
                paseo.getPrecioBase(),
                paseo.getCantMaxBoletos(),
                paseo.getCantMaxBoletos() - paseo.getBoletos().length
            };
            modeloTabla.addRow(fila);
        }
    }
} 