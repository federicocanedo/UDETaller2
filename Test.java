import logica.exception.ArgumentoInvalidoException;
import logica.exception.EntidadNoExisteException;
import logica.exception.EntidadYaExisteException;
import logica.exception.PersistenciaException;
import logica.minivan.Minivan;
import logica.minivan.Minivans;
import logica.minivan.VOMinivan;
import logica.paseo.Paseo;
import logica.paseo.Paseos;
import logica.paseo.VOPaseo;
import persistencia.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;

public class Test {
    public static void main(String[] args) {
        Paseos paseos = new Paseos();
        Minivans minivans = new Minivans();

        try {
            VOMinivan voMinivan1 = new VOMinivan("ABC123", 7, "RolsRoice");
            minivans.insertarMinivan(voMinivan1);
            VOMinivan voMinivan2 = new VOMinivan("XYZ789", 9, "RolsRoice");
            minivans.insertarMinivan(voMinivan2);
        } catch (EntidadYaExisteException e) {
            System.out.println(e.getMessage());
        }

        try {
            VOPaseo voPaseo1 = new VOPaseo(
                    "195",
                    "Playa del Cerro",
                    LocalTime.of(9, 0),
                    LocalTime.of(12, 0),
                    100
            );
            paseos.insertarPaseo(voPaseo1);

            VOPaseo voPaseo2 = new VOPaseo(
                    "405",
                    "Casavalle",
                    LocalTime.of(10, 0),
                    LocalTime.of(14, 0),
                    102
            );
            paseos.insertarPaseo(voPaseo2);
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

    public void guardarDatos(VORespaldo respaldo) {
        Persistencia persistencia = new Persistencia();

        try {
            persistencia.respaldar("respaldo.dat", respaldo);
        } catch (PersistenciaException e) {
            System.out.println(e.getMessage());
        }
    }

    public VORespaldo recuperarDatos() {
        Persistencia persistencia = new Persistencia();

        try {
            return persistencia.recuperar("respaldo.dat");
        } catch (PersistenciaException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void registrarMinivan(Minivans minivans, VOMinivan minivan) {
        try {
            minivans.insertarMinivan(minivan);
        } catch (EntidadYaExisteException e) {
            System.out.println(e.getMessage());
        }
    }

    public VOMinivan[] listarMinivans(Minivans minivans) {
        VOMinivan[] listadoMinivans = minivans.listarMinivans();
        Arrays.sort(listadoMinivans, new Comparator<VOMinivan>() {
            @Override
            public int compare(VOMinivan m1, VOMinivan m2) {
                return m1.getMatricula().compareTo(m2.getMatricula());
            }
        });
        return listadoMinivans;
    }

    public void registrarPaseo(Paseos paseos, VOPaseo voPaseo, Minivans minivans) throws ArgumentoInvalidoException {
        if (voPaseo.getPrecioBase() <= 0) {
            throw new ArgumentoInvalidoException("El precio base debe ser mayor que cero."); // TODO: Cambiar a excepción propia
        }

        Minivan minivanDisponible = null;
        for (Minivan minivan : minivans.values()) {
            boolean disponible = true;
            for (Paseo paseo : minivan.getPaseos().values()) {
                if (voPaseo.getHoraPartida().isBefore(paseo.getHoraRegreso()) &&
                    voPaseo.getHoraRegreso().isAfter(paseo.getHoraPartida())) {
                    disponible = false;
                    break;
                }
            }
            if (disponible) {
                minivanDisponible = minivan;
                break;
            }
        }

        if (minivanDisponible == null) {
            throw new ArgumentoInvalidoException("No hay ninguna minivan con disponibilidad horaria.");
        }

        Paseo nuevoPaseo = new Paseo(voPaseo);
        paseos.insertarPaseo(voPaseo);
        minivanDisponible.getPaseos().put(nuevoPaseo.getId(), nuevoPaseo);
    }

    public VOPaseo[] listarPaseosDeMinivan(Minivans minivans, String matricula) throws EntidadNoExisteException {
        Minivan minivan = minivans.get(matricula);
        if (minivan == null) {
            throw new EntidadNoExisteException("No existe una minivan con matrícula " + matricula);
        }

        VOPaseo[] listadoPaseos = minivan.getPaseos().listarPaseos();
        for (VOPaseo voPaseo : listadoPaseos) {
            voPaseo.setBoletosDisponibles(minivan.getCapacidad() - voPaseo.getBoletos().length);
        }
        return listadoPaseos;
    }

    public VOPaseo[] listarPaseosPorDestino(Paseos paseos, String destino) {
        VOPaseo[] listadoPaseos = paseos.listarPaseos();
        VOPaseo[] paseosFiltrados = new VOPaseo[listadoPaseos.length];
        int index = 0;
        for (VOPaseo voPaseo : listadoPaseos) {
            if (voPaseo.getDestino().equals(destino)) {
                paseosFiltrados[index++] = voPaseo;
            }
        }
        return Arrays.copyOf(paseosFiltrados, index);
    }

    public VOPaseo[] listarPaseosDisponibles(Minivans minivans) {
        VOPaseo[] listadoPaseos = new VOPaseo[0];
        for (Minivan minivan : minivans.values()) {
            for (Paseo paseo : minivan.getPaseos().values()) {
                if (paseo.getBoletos().size() < minivan.getCapacidad()) {
                    listadoPaseos = Arrays.copyOf(listadoPaseos, listadoPaseos.length + 1);
                    listadoPaseos[listadoPaseos.length - 1] = paseo.getVO();
                }
            }
        }
        return listadoPaseos;
    }
}