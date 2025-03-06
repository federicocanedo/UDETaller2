package controladores;

import logica.*;
import logica.minivan.VOMinivan;
import GUI.PrimerRequerimiento;

public class ControladorMinivan {
    private PrimerRequerimiento vista;
    private IFachada fachada;

    public ControladorMinivan(PrimerRequerimiento vista, IFachada fachada) {
        this.vista = vista;
        this.fachada = fachada;

        this.vista.addRegistrarListener(e -> registrarMinivan());
    }

    private void registrarMinivan() {
        String matricula = vista.getMatricula();
        String marca = vista.getMarca();
        String modelo = vista.getModelo();
        int asientos = vista.getAsientos();

        if (matricula.isEmpty() || marca.isEmpty() || modelo.isEmpty()) {
            vista.mostrarMensaje("Todos los campos son obligatorios.");
            return;
        }
/// ESTO DEBERIA SER UNA EXCEPCION
        if (asientos <= 0) {
            vista.mostrarMensaje("La cantidad de asientos debe ser mayor que cero.");
            return;
        }

        try {
            VOMinivan nuevaMinivan = new VOMinivan(matricula, asientos, modelo, marca);
            fachada.registrarMinivan(nuevaMinivan);
            vista.mostrarMensaje("Miniván registrada exitosamente.");
        } catch (Exception e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        }
    }
}
