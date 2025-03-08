package src.fachada;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Comparator;

import src.logica.Monitor;
import src.logica.boletos.Boleto;
import src.logica.boletos.BoletoEspecial;
import src.logica.boletos.VOBoleto;
import src.logica.boletos.VOBoletoEspecial;
import src.logica.exception.ArgumentoInvalidoException;
import src.logica.exception.EntidadNoExisteException;
import src.logica.exception.EntidadYaExisteException;
import src.logica.exception.PersistenciaException;
import src.logica.minivan.Minivan;
import src.logica.minivan.Minivans;
import src.logica.minivan.VOMinivan;
import src.logica.paseo.Paseo;
import src.logica.paseo.Paseos;
import src.logica.paseo.VOPaseo;
import src.persistencia.Persistencia;
import src.persistencia.VORespaldo;

public class Fachada extends UnicastRemoteObject implements IFachada {
    Minivans minivans;
    Paseos paseos;
    Monitor monitor;

    public Fachada() throws RemoteException {
        this.minivans = new Minivans();
        this.paseos = new Paseos();
        this.monitor = new Monitor();
    }

    public void guardarDatos() throws PersistenciaException {
        Persistencia persistencia = new Persistencia();
        this.monitor.comienzoEscritura();
        try {
            persistencia.respaldar("respaldo.dat", new VORespaldo(this.paseos, this.minivans));
            this.monitor.terminoEscritura();
        } catch (PersistenciaException e) {
            System.out.println(e.getMessage());
            this.monitor.terminoEscritura();
            throw e;
        }
    }

    public void recuperarDatos() throws PersistenciaException {
        Persistencia persistencia = new Persistencia();
        this.monitor.comienzoLectura();

        try {
            VORespaldo respaldo = persistencia.recuperar("respaldo.dat");
            this.monitor.terminoLectura();

            this.monitor.comienzoEscritura();
            this.paseos = respaldo.getPaseos();
            this.minivans = respaldo.getMinivans();
            this.monitor.terminoEscritura();
        } catch (PersistenciaException e) {
            System.out.println(e.getMessage());
            this.monitor.terminoLectura();
            throw e;
        }
    }

    public void registrarMinivan(VOMinivan minivan) throws EntidadYaExisteException, ArgumentoInvalidoException {
        if (minivan.getMatricula().isEmpty() ||  minivan.getMatricula() == null) {
            throw new ArgumentoInvalidoException("La matrícula de la minivan no puede estar vacía.");
        }
        if (this.minivans.get(minivan.getMatricula()) != null) {
            throw new EntidadYaExisteException("Ya existe una minivan con matrícula " + minivan.getMatricula());
        }
        if (minivan.getCapacidad() <= 0) {
            throw new ArgumentoInvalidoException("La capacidad de la minivan debe ser mayor que cero.");
        }

        this.monitor.comienzoEscritura();
        try {
            this.minivans.insertarMinivan(minivan);
            this.monitor.terminoEscritura();
        } catch (EntidadYaExisteException e) {
            System.out.println(e.getMessage());
            this.monitor.terminoEscritura();
            throw e;
        }
    }

    public VOMinivan[] listarMinivans() {
        this.monitor.comienzoLectura();

        VOMinivan[] listadoMinivans = this.minivans.listarMinivans();
        Arrays.sort(listadoMinivans, new Comparator<VOMinivan>() {
            @Override
            public int compare(VOMinivan m1, VOMinivan m2) {
                return m1.getMatricula().compareTo(m2.getMatricula());
            }
        });
        this.monitor.terminoLectura();
        return listadoMinivans;
    }

    public void registrarPaseo(VOPaseo voPaseo) throws ArgumentoInvalidoException {
        if (voPaseo.getPrecioBase() <= 0) {
            throw new ArgumentoInvalidoException("El precio base debe ser mayor que cero.");
        }

        if (this.paseos.paseoExiste(voPaseo.getId())) {
            throw new ArgumentoInvalidoException("Ya existe un paseo con el código " + voPaseo.getId());
        }

        this.monitor.comienzoEscritura();

        Minivan minivanDisponible = null;
        for (Minivan minivan : this.minivans.values()) {
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

        voPaseo.setCantMaxBoletos(minivanDisponible.getCapacidad());
        Paseo paseo = new Paseo(voPaseo);
        this.paseos.put(voPaseo.getId(), paseo);
        minivanDisponible.getPaseos().put(voPaseo.getId(), paseo);
        this.monitor.terminoEscritura();
    }

    public VOPaseo[] listarPaseosDeMinivan(String matricula) throws EntidadNoExisteException {
        this.monitor.comienzoLectura();

        Minivan minivan = this.minivans.get(matricula);
        if (minivan == null) {
            this.monitor.terminoLectura();
            throw new EntidadNoExisteException("No existe una minivan con matrícula " + matricula);
        }

        VOPaseo[] listadoPaseos = minivan.getPaseos().listarPaseos();
        this.monitor.terminoLectura();
        return listadoPaseos;
    }

    public VOPaseo[] listarPaseosPorDestino(String destino) {
        this.monitor.comienzoLectura();
        VOPaseo[] listadoPaseos = this.paseos.listarPaseos();
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

    public VOPaseo[] listarPaseosPorDisponibilidadBoletos(int cantidadBoletos) {
        this.monitor.comienzoLectura();
        VOPaseo[] listadoPaseos = new VOPaseo[0];
        for (Minivan minivan : this.minivans.values()) {
            for (Paseo paseo : minivan.getPaseos().values()) {
                if (minivan.getCapacidad() - paseo.getBoletos().size() > cantidadBoletos) {
                    listadoPaseos = Arrays.copyOf(listadoPaseos, listadoPaseos.length + 1);
                    listadoPaseos[listadoPaseos.length - 1] = paseo.getVO();
                }
            }
        }
        this.monitor.terminoLectura();
        return listadoPaseos;
    }

    public void venderBoleto(String paseoId, VOBoleto boleto) throws ArgumentoInvalidoException, EntidadNoExisteException {
        this.monitor.comienzoEscritura();

        Paseo paseo = this.paseos.get(paseoId);
        if (paseo == null) {
            this.monitor.terminoEscritura();
            throw new EntidadNoExisteException("No existe un paseo con el código " + paseoId);
        }

        boolean paseoEncontrado = false;
        for (Minivan minivan : this.minivans.values()) {
            if (!paseoEncontrado) {
                paseoEncontrado = minivan.getPaseos().paseoExiste(paseoId);
            }
        }
        if (!paseoEncontrado) {
            this.monitor.terminoEscritura();
            throw new EntidadNoExisteException("Paseo " + paseoId + " no está asignado a ninguna minivan");
        }

        if (paseo.getBoletos().size() >= paseo.getCantMaxBoletos()) {
            this.monitor.terminoEscritura();
            throw new ArgumentoInvalidoException("Se ha alcanzado la cantidad máxima de boletos para el paseo " + paseoId);
        }

        if (boleto instanceof VOBoletoEspecial) {
            paseo.getBoletos().add(new BoletoEspecial((VOBoletoEspecial) boleto));
        } else {
            paseo.getBoletos().add(new Boleto(boleto));
        }

        this.monitor.terminoEscritura();
    }

    public VOBoleto[] listarBoletosDePaseo(String paseoId, boolean comun, boolean especiales) throws EntidadNoExisteException {
        this.monitor.comienzoLectura();
        Paseo paseo = this.paseos.get(paseoId);
        if (paseo == null) {
            this.monitor.terminoLectura();
            throw new EntidadNoExisteException("No existe un paseo con el código " + paseoId);
        }
        this.monitor.terminoLectura();
        return paseo.getBoletos().listarBoletos(comun, especiales);
    }

    public int calcularMontoRecaudadoPaseo(String paseoId) throws EntidadNoExisteException {
        this.monitor.comienzoLectura();
        
        Paseo paseo = this.paseos.get(paseoId);
        if (paseo == null) {
            this.monitor.terminoLectura();
            throw new EntidadNoExisteException("No existe un paseo con el código " + paseoId);
        }

        int montoTotal = 0;
        int precioBase = paseo.getPrecioBase();

        for (Boleto boleto : paseo.getBoletos()) {
            if (boleto instanceof BoletoEspecial boletoEsp) {
                // Para boletos especiales, aplicar el descuento específico
                int precioConEdad = boleto.getP_edad() <= 18 ? (int)(precioBase * 0.75) : precioBase;
                montoTotal += precioConEdad - boletoEsp.getDescuento();
            } else {
                // Para boletos normales, solo aplicar descuento por edad
                montoTotal += boleto.getP_edad() <= 18 ? (int)(precioBase * 0.75) : precioBase;
            }
        }

        this.monitor.terminoLectura();
        return montoTotal;
    }
}