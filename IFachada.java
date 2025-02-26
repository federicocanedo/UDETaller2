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

    void guardarDatos() throws RemoteException;

    void recuperarDatos() throws RemoteException;

    void registrarMinivan(VOMinivan minivan) throws RemoteException;

    VOMinivan[] listarMinivans() throws RemoteException;

    void registrarPaseo(VOPaseo voPaseo) throws RemoteException, ArgumentoInvalidoException;

    VOPaseo[] listarPaseosDeMinivan(String matricula) throws RemoteException, EntidadNoExisteException;

    VOPaseo[] listarPaseosPorDestino(String destino) throws RemoteException;

    VOPaseo[] listarPaseosPorDisponibilidadBoletos(int cantidadBoletos) throws RemoteException;
}