package logica.minivan;

import logica.exception.EntidadYaExisteException;
import java.util.TreeMap;

public class Minivans {
    private TreeMap<String, Minivan> minivans;

    public Minivans() {
        this.minivans = new TreeMap<>();
    }

    public void insertarMinivan(VOMinivan voMinivan) throws EntidadYaExisteException {
        String matricula = voMinivan.getMatricula();
        if (minivans.containsKey(matricula)) {
            throw new EntidadYaExisteException("La minivan con matrícula " + matricula + " ya existe.");
        }
        minivans.put(matricula, new Minivan(voMinivan));
    }

    public VOMinivan[] listarMinivans() {
        return minivans.values().stream()
                .map(Minivan::getVO)
                .toArray(VOMinivan[]::new);
    }

    public Minivan buscarMinivan(String matricula) {
        return minivans.get(matricula);
    }

	public void put(String matricula, Minivan nuevaMinivan) {
		// TODO Auto-generated method stub
		
	}

	public boolean containsKey(String matricula) {
		// TODO Auto-generated method stub
		return false;
	}

	public Minivan get(String matricula) {
		// TODO Auto-generated method stub
		return null;
	}
}
