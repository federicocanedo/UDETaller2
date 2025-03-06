package src.cliente.ventanas.minivans;

import src.configuracion.ArchivoConfiguracion;
import src.fachada.*;
import src.logica.minivan.VOMinivan;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ListarMinivansController {
    private static IFachada fachada = null;
    private final ListarMinivansVentana ventana;

    public ListarMinivansController(ListarMinivansVentana ventana) {
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
            e.printStackTrace();
        }
    }

    public void listarMinivans() {
        try {
            VOMinivan[] minivans = fachada.listarMinivans();
            Object[][] datos = new Object[minivans.length][5];
            
            for (int i = 0; i < minivans.length; i++) {
                VOMinivan minivan = minivans[i];
                datos[i][0] = minivan.getMatricula();
                datos[i][1] = minivan.getMarca();
                datos[i][2] = minivan.getModelo();
                datos[i][3] = minivan.getCapacidad();
                datos[i][4] = minivan.getCantAsignados();
            }
            
            ventana.setDatosTabla(datos);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
} 