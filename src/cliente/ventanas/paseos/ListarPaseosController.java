package src.cliente.ventanas.paseos;

import src.configuracion.ArchivoConfiguracion;
import src.fachada.*;
import src.logica.exception.EntidadNoExisteException;
import src.logica.paseo.VOPaseo;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;

public class ListarPaseosController {
    private static IFachada fachada = null;
    private final ListarPaseosVentana ventana;

    public ListarPaseosController(ListarPaseosVentana ventana) {
        this.ventana = ventana;
        try {
            ArchivoConfiguracion config = ArchivoConfiguracion.getInstancia();
            String rmiUrl = String.format("//%s:%s/%s",
                    config.getIpServidor(),
                    config.getPuertoServidor(),
                    config.getNombreServidor());
            
            System.out.println("Conectando a servicio RMI en: " + rmiUrl);
            fachada = (IFachada) Naming.lookup(rmiUrl);
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
        if (fachada == null) {
            throw new Exception("No hay conexión con el servidor");
        }
        try {
            VOPaseo[] paseos = fachada.listarPaseosPorDestino(""); // Lista vacía para obtener todos
            mostrarPaseosEnTabla(paseos);
        } catch (RemoteException e) {
            throw new Exception("Error al obtener la lista de paseos: " + e.getMessage());
        }
    }

    public void listarPaseosPorMatricula(String matricula) throws Exception {
        if (fachada == null) {
            throw new Exception("No hay conexión con el servidor");
        }
        try {
            VOPaseo[] paseos = fachada.listarPaseosDeMinivan(matricula);
            mostrarPaseosEnTabla(paseos);
        } catch (RemoteException e) {
            throw new Exception("Error al obtener paseos por matrícula: " + e.getMessage());
        } catch (EntidadNoExisteException e) {
            throw new Exception("No se encontraron paseos para la matrícula: " + matricula);
        }
    }

    public void listarPaseosPorDestino(String destino) throws Exception {
        if (fachada == null) {
            throw new Exception("No hay conexión con el servidor");
        }
        try {
            VOPaseo[] paseos = fachada.listarPaseosPorDestino(destino);
            mostrarPaseosEnTabla(paseos);
        } catch (RemoteException e) {
            throw new Exception("Error al obtener paseos por destino: " + e.getMessage());
        }
    }

    public void listarPaseosPorDisponibilidad(int cantidadBoletos) throws Exception {
        if (fachada == null) {
            throw new Exception("No hay conexión con el servidor");
        }
        try {
            VOPaseo[] paseos = fachada.listarPaseosPorDisponibilidadBoletos(cantidadBoletos);
            mostrarPaseosEnTabla(paseos);
        } catch (RemoteException e) {
            throw new Exception("Error al obtener paseos por disponibilidad: " + e.getMessage());
        }
    }

    private void mostrarPaseosEnTabla(VOPaseo[] paseos) {
        Object[][] datos = new Object[paseos.length][7];
        
        for (int i = 0; i < paseos.length; i++) {
            VOPaseo paseo = paseos[i];
            datos[i][0] = paseo.getId();
            datos[i][1] = paseo.getDestino();
            datos[i][2] = paseo.getHoraPartida();
            datos[i][3] = paseo.getHoraRegreso();
            datos[i][4] = paseo.getPrecioBase();
            datos[i][5] = paseo.getCantMaxBoletos();
            datos[i][6] = paseo.getCantMaxBoletos() - paseo.getBoletos().length;
        }
        
        ventana.setDatosTabla(datos);
    }
} 