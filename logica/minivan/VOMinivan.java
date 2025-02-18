package logica.minivan;

import java.io.Serializable;

public class VOMinivan implements Serializable {
    private String matricula;
    private int capacidad;
    private int plazasOcupadas;

    public VOMinivan(String matricula, int capacidad, int plazasOcupadas) {
        this.matricula = matricula;
        this.capacidad = capacidad;
        this.plazasOcupadas = plazasOcupadas;
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

    public int getPlazasOcupadas() {
        return plazasOcupadas;
    }

    public void setPlazasOcupadas(int plazasOcupadas) {
        this.plazasOcupadas = plazasOcupadas;
    }
}