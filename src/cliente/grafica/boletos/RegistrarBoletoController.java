package src.cliente.grafica.boletos;

import src.configuracion.ArchivoConfiguracion;
import src.fachada.*;
import src.logica.boletos.VOBoleto;
import src.logica.boletos.VOBoletoEspecial;
import src.logica.exception.ArgumentoInvalidoException;
import src.logica.exception.EntidadNoExisteException;
import src.logica.exception.SinConexionException;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;

public class RegistrarBoletoController {
    private IFachada fachada;
    private final RegistrarBoletoVentana ventana;

    public RegistrarBoletoController(RegistrarBoletoVentana ventana) {
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
        }
    }

    public void registrarBoleto(String paseoId, String nombre, int edad, String numCelular, boolean esEspecial, int descuento) {
        try {
            if (this.fachada == null) {
                throw new SinConexionException();
            }

            if (edad <= 0) {
                ventana.mostrarError("La edad debe ser mayor que cero");
                return;
            }

            if (numCelular == null || numCelular.trim().isEmpty()) {
                ventana.mostrarError("El número de celular no puede estar vacío");
                return;
            }

            VOBoleto boleto;
            if (esEspecial) {
                if (descuento <= 0) {
                    ventana.mostrarError("El descuento debe ser mayor que cero");
                    return;
                }
                boleto = new VOBoletoEspecial(nombre, edad, numCelular, descuento);
            } else {
                boleto = new VOBoleto(nombre, edad, numCelular);
            }

            this.fachada.venderBoleto(paseoId, boleto);
            ventana.mostrarExito("Boleto registrado exitosamente");
            ventana.limpiarCampos();

        } catch (RemoteException e) {
            ventana.mostrarError("Error de conexión con el servidor: " + e.getMessage());
        } catch (Exception e) {
            ventana.mostrarError("Error: " + e.getMessage());
        }
    }
} 