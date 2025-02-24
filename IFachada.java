import logica.exception.ArgumentoInvalidoException;
import logica.exception.EntidadNoExisteException;
import logica.minivan.Minivans;
import logica.minivan.VOMinivan;
import logica.paseo.Paseos;
import logica.paseo.VOPaseo;
import persistencia.VORespaldo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IFachada extends Remote {

    void guardarDatos(VORespaldo respaldo) throws RemoteException;

    VORespaldo recuperarDatos() throws RemoteException;

    void registrarMinivan(Minivans minivans, VOMinivan minivan) throws RemoteException;

    VOMinivan[] listarMinivans(Minivans minivans) throws RemoteException;

    void registrarPaseo(Paseos paseos, VOPaseo voPaseo, Minivans minivans) throws RemoteException, ArgumentoInvalidoException;

    VOPaseo[] listarPaseosDeMinivan(Minivans minivans, String matricula) throws RemoteException, EntidadNoExisteException;

    VOPaseo[] listarPaseosPorDestino(Paseos paseos, String destino) throws RemoteException;

    VOPaseo[] listarPaseosDisponibles(Minivans minivans) throws RemoteException;
}