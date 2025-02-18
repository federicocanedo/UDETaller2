package logica.paseo;

import logica.exception.EntidadYaExisteException;

import java.time.LocalTime;
import java.util.TreeMap;

public class Paseos extends TreeMap<String, Paseo> {

    public void insertarPaseo(
            String id,
            String destino,
            LocalTime horaPartida,
            LocalTime horaLlegada,
            int precioBase,
            int cantMaxBoletos
    ) throws EntidadYaExisteException {
        if (this.containsKey(id)) {
            throw new EntidadYaExisteException("Paseo with id " + id + " already exists.");
        }
        Paseo paseo = new Paseo(id, destino, horaPartida, horaLlegada, precioBase, cantMaxBoletos);
        this.put(id, paseo);
    }

    public void insertarPaseo(Paseo paseo) throws EntidadYaExisteException {
        if (this.containsKey(paseo.getId())) {
            throw new EntidadYaExisteException("Paseo with id " + paseo.getId() + " already exists.");
        }
        this.put(paseo.getId(), paseo);
    }

    public VOPaseo[] listarPaseos() {
        int size = this.size();
        VOPaseo[] voPaseos = new VOPaseo[size];
        int index = 0;
        for (Paseo paseo : this.values()) {
            voPaseos[index++] = paseo.getVO();
        }
        return voPaseos;
    }
}