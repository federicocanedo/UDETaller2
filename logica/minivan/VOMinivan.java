package logica.minivan;

import logica.paseo.VOPaseo;
import java.io.Serializable;
import java.util.List;

public class VOMinivan implements Serializable {
    private String matricula;
    private int capacidad;
    private String modelo;
    private String marca;
    private List<VOPaseo> paseos;

    // Constructor
    public VOMinivan(String matricula, int capacidad, String modelo, String marca, List<VOPaseo> paseos) {
        this.matricula = matricula;
        this.capacidad = capacidad;
        this.modelo = modelo;
        this.marca = marca;
        this.paseos = paseos;
    }

    // Getters y Setters
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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public List<VOPaseo> getPaseos() {
        return paseos;
    }

    public void setPaseos(List<VOPaseo> paseos) {
        this.paseos = paseos;
    }
}
