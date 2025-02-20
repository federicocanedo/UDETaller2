

import logica.minivan.Minivan;
import logica.minivan.VOMinivan;
import logica.exception.EntidadYaExisteException;
import logica.minivan.Minivans;
import java.util.Map;

public class Fachada {
    private Minivans minivans; 

    public Fachada() {
        this.minivans = new Minivans(); 
    }

    
    public void registrarMinivan(String matricula, int capacidad, String modelo, String marca) {
        
        if (capacidad <= 0) {
            System.err.println("Error: La cantidad de asientos debe ser mayor a cero.");
            return;
        }

        
        if (minivans.containsKey(matricula)) {
            System.err.println("Error: Ya existe una minivan registrada con la matrícula " + matricula);
            return;
        }

       
        VOMinivan vo = new VOMinivan(matricula, capacidad, modelo, marca, null);

      
        Minivan nuevaMinivan = new Minivan(vo);

      
        minivans.put(matricula, nuevaMinivan);
        System.out.println("Minivan registrada exitosamente: " + matricula);
    }
}
