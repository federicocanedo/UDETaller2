package src.cliente;

import src.cliente.grafica.minivans.ListarMinivansVentana;
import src.cliente.grafica.minivans.RegistrarMinivanVentana;
import src.cliente.grafica.paseos.ListarPaseosVentana;
import src.cliente.grafica.paseos.RegistrarPaseoVentana;
import src.cliente.grafica.boletos.ListarBoletosVentana;
import src.cliente.grafica.boletos.RegistrarBoletoVentana;
import src.configuracion.ArchivoConfiguracion;
import src.fachada.IFachada;
import src.logica.exception.PersistenciaException;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MainVentana extends JFrame {
    
    private IFachada fachada;
    
    public MainVentana() {
        conectarServidor();
        initComponents();
    }

    private void conectarServidor() {
        try {
            ArchivoConfiguracion config = ArchivoConfiguracion.getInstancia();
            String rmiUrl = String.format("//%s:%s/%s",
                    config.getIpServidor(),
                    config.getPuertoServidor(),
                    config.getNombreServidor());
            
            System.out.println("Conectando a servicio RMI en: " + rmiUrl);
            this.fachada = (IFachada) Naming.lookup(rmiUrl);
            System.out.println("Conexión exitosa al servicio RMI");
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            JOptionPane.showMessageDialog(this,
                "Error al conectar con el servidor: " + e.getMessage(),
                "Error de Conexión",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void initComponents() {
        setTitle("Sistema de Gestión de Paseos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(null);

        // Panel principal con GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Título
        JLabel titleLabel = new JLabel("Menú Principal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        // Sección Minivans
        addSectionLabel(mainPanel, "Minivans", 1, gbc);
        addButton(mainPanel, "Registrar Minivan", 2, gbc,
                () -> new RegistrarMinivanVentana().setVisible(true));
        addButton(mainPanel, "Listar Minivans", 3, gbc,
                () -> new ListarMinivansVentana().setVisible(true));

        // Sección Paseos
        addSectionLabel(mainPanel, "Paseos", 4, gbc);
        addButton(mainPanel, "Registrar Paseo", 5, gbc,
                () -> new RegistrarPaseoVentana().setVisible(true));
        addButton(mainPanel, "Listar Paseos", 6, gbc,
                () -> new ListarPaseosVentana().setVisible(true));

        // Sección Boletos
        addSectionLabel(mainPanel, "Boletos", 7, gbc);
        addButton(mainPanel, "Registrar Boleto", 8, gbc,
                () -> new RegistrarBoletoVentana().setVisible(true));
        addButton(mainPanel, "Listar Boletos", 9, gbc,
                () -> new ListarBoletosVentana().setVisible(true));

        // Sección Persistencia
        addSectionLabel(mainPanel, "Persistencia de Datos", 10, gbc);
        addButton(mainPanel, "Guardar Datos", 11, gbc, () -> {
            try {
                fachada.guardarDatos();
                JOptionPane.showMessageDialog(this, 
                    "Datos guardados exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (RemoteException | PersistenciaException e) {
                JOptionPane.showMessageDialog(this,
                    "Error al guardar los datos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        addButton(mainPanel, "Recuperar Datos", 12, gbc, () -> {
            try {
                fachada.recuperarDatos();
                JOptionPane.showMessageDialog(this, 
                    "Datos recuperados exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (RemoteException | PersistenciaException e) {
                JOptionPane.showMessageDialog(this,
                    "Error al recuperar los datos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // Agregar panel principal
        add(mainPanel);
    }

    private void addSectionLabel(JPanel panel, String text, int gridy, GridBagConstraints gbc) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = gridy;
        panel.add(label, gbc);
    }

    private void addButton(JPanel panel, String text, int gridy, GridBagConstraints gbc, Runnable action) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 40));
        button.addActionListener(e -> action.run());
        gbc.gridy = gridy;
        panel.add(button, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainVentana mainVentana = new MainVentana();
            mainVentana.setVisible(true);
        });
    }
}