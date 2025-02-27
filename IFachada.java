import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.List;

import logica.boletos.VOBoleto;
import logica.minivan.VOMinivan;
import logica.paseo.*;

public interface IFachada extends Remote {
    // Métodos que serán accesibles de forma remota
    boolean registrarMinivan(String matricula, String marca, int capacidad) throws RemoteException;
    VOMinivan obtenerMinivan(String matricula) throws RemoteException;
    void listarMinivans() throws RemoteException;
    boolean registrarPaseo(String codigo, String destino, LocalTime horaPartida, LocalTime horaRegreso, int precioBase) throws RemoteException;
    void listarPaseosDeMinivan(String matricula) throws RemoteException;
    void listarPaseosPorDestino(String destino) throws RemoteException;
    void listarPaseosPorDisponibilidadBoletos(int cantidadBoletos) throws RemoteException;
    boolean venderBoleto(String codigoPaseo, String nombreTurista, int edadTurista, String numeroCelular, boolean esBoletoEspecial, double descuento) throws RemoteException;
    List<VOBoleto> listarBoletosVendidos(String codigoPaseo, boolean esBoletoEspecial) throws RemoteException;
}