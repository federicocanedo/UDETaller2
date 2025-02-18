package logica.paseo;

import logica.exception.EntidadYaExisteException;

import java.time.LocalTime;
import java.util.TreeMap;

public class Paseos extends TreeMap<String, Paseo> {

    public void insertarPaseo(VOPaseo paseo) throws EntidadYaExisteException {
        if (this.containsKey(paseo.getId())) {
            throw new EntidadYaExisteException("Paseo with id " + paseo.getId() + " already exists.");
        }
        this.put(paseo.getId(), new Paseo(paseo));
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