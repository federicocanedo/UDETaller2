package src.cliente.grafica.paseos;

import src.configuracion.ArchivoConfiguracion;
import src.fachada.*;
import src.logica.exception.EntidadNoExisteException;
import src.logica.exception.SinConexionException;
import src.logica.paseo.VOPaseo;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;

public class ListarPaseosController {
    private IFachada fachada;
    private final ListarPaseosVentana ventana;

    public ListarPaseosController(ListarPaseosVentana ventana) {
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

    public void listarTodosLosPaseos() throws Exception {
        if (this.fachada == null) {
            throw new SinConexionException();
        }
        try {
            VOPaseo[] paseos = this.fachada.listarPaseosPorDestino(""); // Lista vacía para obtener todos
            ventana.mostrarPaseos(paseos);
        } catch (RemoteException e) {
            throw new Exception("Error al obtener la lista de paseos: " + e.getMessage());
        }
    }

    public void listarPaseosPorMatricula(String matricula) throws Exception {
        if (this.fachada == null) {
            throw new SinConexionException();
        }
        try {
            VOPaseo[] paseos = this.fachada.listarPaseosDeMinivan(matricula);
            ventana.mostrarPaseos(paseos);
        } catch (RemoteException e) {
            throw new Exception("Error al obtener paseos por matrícula: " + e.getMessage());
        } catch (EntidadNoExisteException e) {
            throw new Exception("No se encontraron paseos para la matrícula: " + matricula);
        }
    }

    public void listarPaseosPorDestino(String destino) throws Exception {
        if (this.fachada == null) {
            throw new SinConexionException();
        }
        try {
            VOPaseo[] paseos = this.fachada.listarPaseosPorDestino(destino);
            ventana.mostrarPaseos(paseos);
        } catch (RemoteException e) {
            throw new Exception("Error al obtener paseos por destino: " + e.getMessage());
        }
    }

    public void listarPaseosPorDisponibilidad(int cantidadBoletos) throws Exception {
        if (this.fachada == null) {
            throw new SinConexionException();
        }
        try {
            VOPaseo[] paseos = this.fachada.listarPaseosPorDisponibilidadBoletos(cantidadBoletos);
            ventana.mostrarPaseos(paseos);
        } catch (RemoteException e) {
            throw new Exception("Error al obtener paseos por disponibilidad: " + e.getMessage());
        }
    }
} 