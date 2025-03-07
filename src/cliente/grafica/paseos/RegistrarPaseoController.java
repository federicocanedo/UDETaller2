package src.cliente.grafica.paseos;

import src.configuracion.ArchivoConfiguracion;
import src.fachada.*;
import src.logica.paseo.VOPaseo;
import src.logica.exception.SinConexionException;
import src.logica.exception.ArgumentoInvalidoException;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;
import java.time.LocalTime;

public class RegistrarPaseoController {
    private IFachada fachada;
    private final RegistrarPaseoVentana ventana;

    public RegistrarPaseoController(RegistrarPaseoVentana ventana) {
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

    public void registrarPaseo() {
        String id = ventana.getId();
        String destino = ventana.getDestino();
        LocalTime horaPartida = ventana.getHoraPartida();
        LocalTime horaRegreso = ventana.getHoraRegreso();
        int precioBase = ventana.getPrecioBase();

        if (id.isEmpty() || destino.isEmpty()) {
            ventana.mostrarMensaje("Los campos ID y Destino son obligatorios.");
            return;
        }

        if (horaPartida == null || horaRegreso == null) {
            ventana.mostrarMensaje("Las horas deben tener el formato HH:mm (ejemplo: 14:30)");
            return;
        }

        if (precioBase <= 0) {
            ventana.mostrarMensaje("El precio base debe ser mayor que cero.");
            return;
        }

        if (horaRegreso.isBefore(horaPartida)) {
            ventana.mostrarMensaje("La hora de regreso debe ser posterior a la hora de partida.");
            return;
        }

        try {
            if (this.fachada == null) {
                throw new SinConexionException();
            }
            
            VOPaseo nuevoPaseo = new VOPaseo(id, destino, horaPartida, horaRegreso, precioBase);
            this.fachada.registrarPaseo(nuevoPaseo);
            ventana.mostrarMensaje("Paseo registrado exitosamente.");
            
            // Limpiar campos después del registro exitoso
            ventana.limpiarCampos();
        } catch (RemoteException e) {
            ventana.mostrarMensaje("Error al registrar el paseo: " + e.getMessage());
        } catch (ArgumentoInvalidoException e) {
            ventana.mostrarMensaje("Error: " + e.getMessage());
        } catch (Exception e) {
            ventana.mostrarMensaje("Error inesperado: " + e.getMessage());
        }
    }
}