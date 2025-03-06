package logica;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Servidor {

    public static void main (String [] args)
    {
        try
        {
            LocateRegistry.createRegistry(1099);
            Fachada fachada = new Fachada();
            System.out.println ("Antes de publicarlo");
            Naming.rebind("//localhost:1099/fachada", fachada);
            System.out.println ("Luego de publicarlo");
        }
        catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}