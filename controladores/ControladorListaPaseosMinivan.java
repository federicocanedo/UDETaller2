package controladores;

import GUI.CuartoRequerimiento;
import logica.IFachada;
import logica.exception.EntidadNoExisteException;
import logica.paseo.VOPaseo;

import java.rmi.RemoteException;
import java.util.Arrays;

public class ControladorListaPaseosMinivan {
    private CuartoRequerimiento vista;
    private IFachada fachada;

    public ControladorListaPaseosMinivan(CuartoRequerimiento vista, IFachada fachada) {
        this.vista = vista;
        this.fachada = fachada;
        this.vista.addBuscarListener(e -> listarPaseos());
    }

    public void iniciar() {
        vista.setVisible(true);
    }

    private void listarPaseos() {
        String matricula = vista.getMatricula();
        if (matricula.isEmpty()) {
            vista.mostrarMensaje("Ingrese una matrícula.");
            return;
        }

        try {
            VOPaseo[] paseos = fachada.listarPaseosDeMinivan(matricula);
            if (paseos.length == 0) {
                vista.mostrarResultado("No hay paseos asignados a esta minivan.");
                return;
            }

            // Construir la lista de paseos ordenados
            StringBuilder resultado = new StringBuilder("Paseos asignados:\n");
            Arrays.stream(paseos).sorted((p1, p2) -> p1.getId().compareTo(p2.getId()))
                    .forEach(p -> resultado.append(
                            String.format("Código: %s\nDestino: %s\nSalida: %s\nRegreso: %s\nPrecio: $%d\nBoletos: %d/%d\n\n",
                                    p.getId(), p.getDestino(), p.getHoraPartida(), p.getHoraRegreso(),
                                    p.getPrecioBase(), (p.getCantMaxBoletos() - p.getBoletos().length), p.getCantMaxBoletos())));

            vista.mostrarResultado(resultado.toString());

        } catch (EntidadNoExisteException e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        } catch (RemoteException e) {
            vista.mostrarMensaje("Error de conexión: " + e.getMessage());
        }
    }
}
