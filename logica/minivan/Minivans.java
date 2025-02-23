package logica.minivan;
import logica.*;
import logica.paseo.*;
import logica.minivan.*;
import java.time.LocalTime;
import java.util.TreeMap;
import logica.exception.EntidadYaExisteException;

public class Minivans extends TreeMap<String, Minivan> {

    // Método para insertar una minivan
    public void insertarMinivan(VOMinivan minivan) throws EntidadYaExisteException {
        if (this.containsKey(minivan.getMatricula())) {
            throw new EntidadYaExisteException("La minivan con matrícula " + minivan.getMatricula() + " ya existe.");
        }
        this.put(minivan.getMatricula(), new Minivan(minivan));
    }

    // Método para listar todas las minivanes con la cantidad de paseos asignados
    public VOMinivan[] listarMinivans() {
        int size = this.size();
        VOMinivan[] voMinivans = new VOMinivan[size];
        int index = 0;

        // Recorremos las minivanes en el diccionario (ya están ordenadas por matrícula)
        for (Minivan minivan : this.values()) {
            // Obtenemos los datos de la minivan
            String matricula = minivan.getMatricula();
            String marca = minivan.getMarca();
            int capacidad = minivan.getCapacidad();

            // Calculamos la cantidad de paseos asignados
            int cantidadPaseos = minivan.getPaseosAsignados().size();

            // Creamos un nuevo VOMinivan con los datos y la cantidad de paseos
            VOMinivan voMinivan = new VOMinivan(matricula, capacidad, marca);
            voMinivan.setCantidadPaseos(cantidadPaseos);

            // Lo agregamos al arreglo
            voMinivans[index++] = voMinivan;
        }

        return voMinivans;
    }

    // Método para buscar una minivan por matrícula
    public Minivan buscarMinivan(String matricula) {
        return this.get(matricula);
    }
    
    


}