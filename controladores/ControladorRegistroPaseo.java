package controladores;

import logica.IFachada;
import logica.exception.ArgumentoInvalidoException;
import logica.paseo.VOPaseo;

import java.rmi.RemoteException;
import java.time.LocalTime;

import GUI.TercerRequerimiento;

public class ControladorRegistroPaseo {
    private TercerRequerimiento vista;
    private IFachada fachada;

    public ControladorRegistroPaseo(TercerRequerimiento vista, IFachada fachada) {
        this.vista = vista;
        this.fachada = fachada;
        this.vista.addRegistrarListener(e -> registrarPaseo());
    }

    public void iniciar() {
        vista.setVisible(true);
    }

    private void registrarPaseo() {
        String codigo = vista.getCodigo();
        String destino = vista.getDestino();
        LocalTime horaPartida = vista.getHoraPartida();
        LocalTime horaRegreso = vista.getHoraRegreso();
        double precioBase = vista.getPrecioBase();

        if (precioBase <= 0) {
            vista.mostrarMensaje("El precio base debe ser mayor que cero.");
            return;
        }

        try {
            VOPaseo voPaseo = new VOPaseo(codigo, destino, horaPartida, horaRegreso, (int) precioBase);
            fachada.registrarPaseo(voPaseo);
            vista.mostrarMensaje("Paseo registrado exitosamente.");
        } catch (ArgumentoInvalidoException e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        } catch (RemoteException e) {
            vista.mostrarMensaje("Error de conexión: " + e.getMessage());
        }
    }
}