package logica.minivan;

import java.util.TreeMap;
import logica.exception.EntidadYaExisteException;

public class Minivans extends TreeMap<String, Minivan> {

    public void insertarMinivan(String matricula, int capacidad, int plazasOcupadas) throws EntidadYaExisteException {
        if (this.containsKey(matricula)) {
            throw new EntidadYaExisteException("La minivan con matrícula " + matricula + " ya existe.");
        }
        Minivan minivan = new Minivan(matricula, capacidad, plazasOcupadas);
        this.put(matricula, minivan);
    }

    public void insertarMinivan(Minivan minivan) throws EntidadYaExisteException {
        if (this.containsKey(minivan.getMatricula())) {
            throw new EntidadYaExisteException("La minivan con matrícula " + minivan.getMatricula() + " ya existe.");
        }
        this.put(minivan.getMatricula(), minivan);
    }

    public VOMinivan[] listarMinivans() {
        int size = this.size();
        VOMinivan[] voMinivans = new VOMinivan[size];
        int index = 0;
        for (Minivan minivan : this.values()) {
            voMinivans[index++] = minivan.getVO();
        }
        return voMinivans;
    }

    public Minivan buscarMinivan(String matricula) {
        return this.get(matricula);
    }
}