import src.configuracion.ArchivoConfiguracion;
import src.fachada.Fachada;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Servidor {

    public static void main (String [] args)
    {
        try
        {
            ArchivoConfiguracion config = ArchivoConfiguracion.getInstancia();
            int puerto = Integer.parseInt(config.getPuertoServidor());
            
            LocateRegistry.createRegistry(puerto);
            Fachada fachada = new Fachada();
            
            String rmiUrl = String.format("//%s:%s/%s",
                    config.getIpServidor(),
                    config.getPuertoServidor(),
                    config.getNombreServidor());
            
            System.out.println("Publicando servicio RMI en: " + rmiUrl);
            Naming.rebind(rmiUrl, fachada);
            System.out.println("Servicio RMI publicado exitosamente");
        }
        catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
