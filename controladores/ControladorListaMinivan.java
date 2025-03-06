package controladores;

import GUI.SegundoRequerimiento;
import logica.IFachada;

import java.rmi.RemoteException;

public class ControladorListaMinivan {

    private SegundoRequerimiento vista;
    private IFachada fachada;

    public ControladorListaMinivan(SegundoRequerimiento vista, IFachada fachada) {
        this.vista = vista;
        this.fachada = fachada;
    }

    public void iniciar() {
        vista.setVisible(true);
    }

    // Este es el método que se invoca desde la vista para listar las minivans
    public void listarMinivans() {
        try {
            // Obtener las minivans desde la fachada y pasarlas a la vista
        	fachada.listarMinivans();
        } catch (RemoteException e) {
            System.out.println("Error al obtener las minivans: " + e.getMessage());
        }
    }
}
