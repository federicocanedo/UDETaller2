import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Comparator;

import logica.Monitor;
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
import persistencia.Persistencia;
import persistencia.VORespaldo;

public class Fachada extends UnicastRemoteObject implements IFachada {
    Minivans minivans;
    Paseos paseos;
    Monitor monitor;

    protected Fachada() throws RemoteException {
        this.minivans = new Minivans();
        this.paseos = new Paseos();
        this.monitor = new Monitor();
    }

    public void guardarDatos(VORespaldo respaldo) {
        Persistencia persistencia = new Persistencia();
        this.monitor.comienzoEscritura();
        try {
            persistencia.respaldar("respaldo.dat", respaldo);
            this.monitor.terminoEscritura();
        } catch (PersistenciaException e) {
            System.out.println(e.getMessage());
            this.monitor.terminoEscritura();
        }
    }

    public VORespaldo recuperarDatos() {
        Persistencia persistencia = new Persistencia();
        this.monitor.comienzoLectura();

        try {
            VORespaldo respaldo = persistencia.recuperar("respaldo.dat");
            this.monitor.terminoLectura();
            return respaldo;
        } catch (PersistenciaException e) {
            System.out.println(e.getMessage());
            this.monitor.terminoLectura();
            return null;
        }
    }

    public void registrarMinivan(Minivans minivans, VOMinivan minivan) {
        this.monitor.comienzoEscritura();
        try {
            minivans.insertarMinivan(minivan);
            this.monitor.terminoEscritura();
        } catch (EntidadYaExisteException e) {
            System.out.println(e.getMessage());
            this.monitor.terminoEscritura();
        }
    }

    public VOMinivan[] listarMinivans(Minivans minivans) {
        this.monitor.comienzoLectura();

        VOMinivan[] listadoMinivans = minivans.listarMinivans();
        Arrays.sort(listadoMinivans, new Comparator<VOMinivan>() {
            @Override
            public int compare(VOMinivan m1, VOMinivan m2) {
                return m1.getMatricula().compareTo(m2.getMatricula());
            }
        });
        this.monitor.terminoLectura();
        return listadoMinivans;
    }

    public void registrarPaseo(Paseos paseos, VOPaseo voPaseo, Minivans minivans) throws ArgumentoInvalidoException {
        if (voPaseo.getPrecioBase() <= 0) {
            throw new ArgumentoInvalidoException("El precio base debe ser mayor que cero.");
        }

        this.monitor.comienzoEscritura();

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
            this.monitor.terminoEscritura();
            throw new ArgumentoInvalidoException("No hay ninguna minivan con disponibilidad horaria.");
        }

        Paseo nuevoPaseo = new Paseo(voPaseo);
        paseos.insertarPaseo(voPaseo);
        minivanDisponible.getPaseos().put(nuevoPaseo.getId(), nuevoPaseo);
        this.monitor.terminoEscritura();
    }

    public VOPaseo[] listarPaseosDeMinivan(Minivans minivans, String matricula) throws EntidadNoExisteException {
        this.monitor.comienzoLectura();

        Minivan minivan = minivans.get(matricula);
        if (minivan == null) {
            this.monitor.terminoLectura();
            throw new EntidadNoExisteException("No existe una minivan con matrícula " + matricula);
        }

        VOPaseo[] listadoPaseos = minivan.getPaseos().listarPaseos();
        for (VOPaseo voPaseo : listadoPaseos) {
            voPaseo.setBoletosDisponibles(minivan.getCapacidad() - voPaseo.getBoletos().length);
        }
        this.monitor.terminoLectura();
        return listadoPaseos;
    }

    public VOPaseo[] listarPaseosPorDestino(Paseos paseos, String destino) {
        this.monitor.comienzoLectura();
        VOPaseo[] listadoPaseos = paseos.listarPaseos();
        VOPaseo[] paseosFiltrados = new VOPaseo[listadoPaseos.length];
        int index = 0;
        for (VOPaseo voPaseo : listadoPaseos) {
            if (voPaseo.getDestino().equals(destino)) {
                paseosFiltrados[index++] = voPaseo;
            }
        }
        this.monitor.terminoLectura();
        return Arrays.copyOf(paseosFiltrados, index);
    }

    public VOPaseo[] listarPaseosDisponibles(Minivans minivans) {
        this.monitor.comienzoLectura();
        VOPaseo[] listadoPaseos = new VOPaseo[0];
        for (Minivan minivan : minivans.values()) {
            for (Paseo paseo : minivan.getPaseos().values()) {
                if (paseo.getBoletos().size() < minivan.getCapacidad()) {
                    listadoPaseos = Arrays.copyOf(listadoPaseos, listadoPaseos.length + 1);
                    listadoPaseos[listadoPaseos.length - 1] = paseo.getVO();
                }
            }
        }
        this.monitor.terminoLectura();
        return listadoPaseos;
    }
}