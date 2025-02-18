import logica.exception.EntidadYaExisteException;
import logica.minivan.Minivan;
import logica.minivan.Minivans;
import logica.minivan.VOMinivan;
import logica.paseo.Paseos;
import logica.paseo.VOPaseo;

import java.time.LocalTime;

public class Principal {
    public static void main(String[] args) {
        Paseos paseos = new Paseos();
        try {
            VOPaseo voPaseo1 = new VOPaseo("195", "Playa del Cerro", LocalTime.of(9, 0), LocalTime.of(12, 0), 100);
            paseos.insertarPaseo(voPaseo1);
            VOPaseo voPaseo2 = new VOPaseo("405", "Casavalle", LocalTime.of(10, 0), LocalTime.of(14, 0), 150);
            paseos.insertarPaseo(voPaseo2);
        } catch (EntidadYaExisteException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nLista de Paseos:");
        for (VOPaseo voPaseo : paseos.listarPaseos()) {
            System.out.println(voPaseo.getId() + ": " + voPaseo.getDestino());
        }

        Minivans minivans = new Minivans();
        try {
            VOMinivan voMinivan1 = new VOMinivan("ABC123", 7);
            minivans.insertarMinivan(voMinivan1);
            VOMinivan voMinivan2 = new VOMinivan("XYZ789", 9);
            minivans.insertarMinivan(voMinivan2);
        } catch (EntidadYaExisteException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nLista de Minivans:");
        for (VOMinivan voMinivan : minivans.listarMinivans()) {
            System.out.println(
                    voMinivan.getMatricula() + ": " + voMinivan.getCapacidad() + " capacidad"
            );
        }

        Minivan minivan1 = minivans.buscarMinivan("ABC123");
        if (minivan1 != null) {
            minivan1.setPaseos(paseos);
        }

        System.out.println("\nMinivan ABC123 con Paseos:");
        for (VOPaseo voPaseo : minivan1.getPaseos().listarPaseos()) {
            System.out.println(voPaseo.getId() + ": " + voPaseo.getDestino());
        }
    }
}