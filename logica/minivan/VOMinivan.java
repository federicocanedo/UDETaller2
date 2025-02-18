package logica.minivan;

import java.io.Serializable;

public class VOMinivan implements Serializable {
    private String matricula;
    private int capacidad;
    private String marca;

    public VOMinivan(String matricula, int capacidad, String marca) {
        this.matricula = matricula;
        this.capacidad = capacidad;
        this.marca = marca;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}