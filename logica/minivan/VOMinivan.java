package logica.minivan;

import java.io.Serializable;

public class VOMinivan implements Serializable {
    private String matricula;
    private int capacidad;

    public VOMinivan(String matricula, int capacidad) {
        this.matricula = matricula;
        this.capacidad = capacidad;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}