package src.cliente.ventanas.boletos;

import src.configuracion.ArchivoConfiguracion;
import src.fachada.*;
import src.logica.boletos.VOBoleto;
import src.logica.boletos.VOBoletoEspecial;
import src.logica.exception.EntidadNoExisteException;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ListarBoletosController {
    private static IFachada fachada = null;
    private final ListarBoletosVentana ventana;

    public ListarBoletosController(ListarBoletosVentana ventana) {
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

    public void listarBoletos(String paseoId, boolean comunes, boolean especiales) {
        try {
            VOBoleto[] boletos = fachada.listarBoletosDePaseo(paseoId, comunes, especiales);
            mostrarBoletosEnTabla(boletos);
        } catch (RemoteException | EntidadNoExisteException e) {
            e.printStackTrace();
        }
    }

    private void mostrarBoletosEnTabla(VOBoleto[] boletos) {
        Object[][] datos = new Object[boletos.length][5];
        
        for (int i = 0; i < boletos.length; i++) {
            VOBoleto boleto = boletos[i];
            datos[i][0] = boleto.getId();
            datos[i][1] = boleto.getP_nombre();
            datos[i][2] = boleto.getP_edad();
            datos[i][3] = boleto.getP_numCelular();
            datos[i][4] = boleto instanceof VOBoletoEspecial ? 
                    ((VOBoletoEspecial) boleto).getDescuento() : "";
        }
        
        ventana.setDatosTabla(datos);
    }
} 