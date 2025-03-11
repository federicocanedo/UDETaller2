package src.logica.minivan;

import java.util.TreeMap;
import src.logica.exception.EntidadYaExisteException;

public class Minivans extends TreeMap<String, Minivan> {

    public void insertarMinivan(VOMinivan minivan) throws EntidadYaExisteException {
        if (this.containsKey(minivan.getMatricula())) {
            throw new EntidadYaExisteException("La minivan con matrícula " + minivan.getMatricula() + " ya existe.");
        }
        this.put(minivan.getMatricula(), new Minivan(minivan));
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