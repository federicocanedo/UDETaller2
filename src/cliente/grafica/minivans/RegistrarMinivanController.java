package src.cliente.grafica.minivans;

import src.configuracion.ArchivoConfiguracion;
import src.fachada.*;
import src.logica.minivan.VOMinivan;
import src.logica.exception.SinConexionException;
import src.logica.exception.ArgumentoInvalidoException;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;

public class RegistrarMinivanController {
    private IFachada fachada;
    private final RegistrarMinivanVentana ventana;

    public RegistrarMinivanController(RegistrarMinivanVentana ventana) {
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

    public void registrarMinivan() {
        String matricula = ventana.getMatricula();
        String marca = ventana.getMarca();
        String modelo = ventana.getModelo();
        int asientos = ventana.getAsientos();

        try {
            if (this.fachada == null) {
                throw new SinConexionException();
            }
            
            VOMinivan nuevaMinivan = new VOMinivan(matricula, asientos, marca, modelo);
            this.fachada.registrarMinivan(nuevaMinivan);
            ventana.mostrarMensaje("Miniván registrada exitosamente.");
            
            // Limpiar campos después del registro exitoso
            ventana.limpiarCampos();
        } catch (RemoteException e) {
            ventana.mostrarMensaje("Error al registrar la miniván: " + e.getMessage());
        } catch (ArgumentoInvalidoException e) {
            ventana.mostrarMensaje("Error: " + e.getMessage());
        } catch (Exception e) {
            ventana.mostrarMensaje("Error inesperado: " + e.getMessage());
        }
    }
}
