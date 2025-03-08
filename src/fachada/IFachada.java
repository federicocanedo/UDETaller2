package src.fachada;

import src.logica.boletos.VOBoleto;
import src.logica.exception.ArgumentoInvalidoException;
import src.logica.exception.EntidadNoExisteException;
import src.logica.exception.EntidadYaExisteException;
import src.logica.exception.PersistenciaException;
import src.logica.minivan.VOMinivan;
import src.logica.paseo.VOPaseo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IFachada extends Remote {

    void guardarDatos() throws RemoteException, PersistenciaException;

    void recuperarDatos() throws RemoteException, PersistenciaException;

    void registrarMinivan(VOMinivan minivan) throws RemoteException, ArgumentoInvalidoException, EntidadYaExisteException;

    VOMinivan[] listarMinivans() throws RemoteException;

    void registrarPaseo(VOPaseo voPaseo) throws RemoteException, ArgumentoInvalidoException;

    VOPaseo[] listarPaseosDeMinivan(String matricula) throws RemoteException, EntidadNoExisteException;

    VOPaseo[] listarPaseosPorDestino(String destino) throws RemoteException;

    VOPaseo[] listarPaseosPorDisponibilidadBoletos(int cantidadBoletos) throws RemoteException;

    public void venderBoleto(String paseoId, VOBoleto boleto) throws RemoteException, ArgumentoInvalidoException, EntidadNoExisteException;

    VOBoleto[] listarBoletosDePaseo(String paseoId, boolean comun, boolean especiales) throws RemoteException, EntidadNoExisteException;

    int calcularMontoRecaudadoPaseo(String paseoId) throws RemoteException, EntidadNoExisteException;
}