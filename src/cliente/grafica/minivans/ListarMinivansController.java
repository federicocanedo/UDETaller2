package src.cliente.grafica.minivans;

import src.configuracion.ArchivoConfiguracion;
import src.fachada.*;
import src.logica.minivan.VOMinivan;
import src.logica.exception.SinConexionException;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;

public class ListarMinivansController {
    private IFachada fachada;
    private final ListarMinivansVentana ventana;

    public ListarMinivansController(ListarMinivansVentana ventana) {
        this.ventana = ventana;
        this.fachada = null;
        conectarServidor();
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
            JOptionPane.showMessageDialog(ventana,
                "Error al conectar con el servidor: " + e.getMessage(),
                "Error de Conexión",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void listarMinivans() throws Exception {
        if (this.fachada == null) {
            throw new SinConexionException();
        }
        try {
            VOMinivan[] minivans = this.fachada.listarMinivans();
            ventana.mostrarMinivans(minivans);
        } catch (RemoteException e) {
            throw new Exception("Error al obtener la lista de minivans: " + e.getMessage());
        }
    }
} 