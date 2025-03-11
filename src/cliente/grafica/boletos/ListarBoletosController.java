package src.cliente.grafica.boletos;

import src.configuracion.ArchivoConfiguracion;
import src.fachada.*;
import src.logica.boletos.VOBoleto;
import src.logica.exception.EntidadNoExisteException;
import src.logica.exception.SinConexionException;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;

public class ListarBoletosController {
    private IFachada fachada;
    private final ListarBoletosVentana ventana;

    public ListarBoletosController(ListarBoletosVentana ventana) {
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

    public void listarBoletos(String paseoId, boolean comunes, boolean especiales) throws Exception {
        if (this.fachada == null) {
            throw new SinConexionException();
        }
        try {
            VOBoleto[] boletos = this.fachada.listarBoletosDePaseo(paseoId, comunes, especiales);
            ventana.mostrarBoletos(boletos);
        } catch (RemoteException e) {
            throw new Exception("Error al obtener la lista de boletos: " + e.getMessage());
        } catch (EntidadNoExisteException e) {
            throw new Exception("No se encontraron boletos para el paseo: " + paseoId);
        }
    }
} 