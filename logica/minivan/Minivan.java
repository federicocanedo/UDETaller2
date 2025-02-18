package logica.minivan;

import logica.paseo.Paseos;

import java.io.Serializable;

public class Minivan implements Serializable {
    private String matricula;
    private int capacidad;
    private String marca;
    private Paseos paseos;


    public Minivan(VOMinivan vo) {
        this.matricula = vo.getMatricula();
        this.capacidad = vo.getCapacidad();
        this.marca = vo.getMarca();
        this.paseos = new Paseos();
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

    
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public Paseos getPaseos() {
        return paseos;
    }

    public void setPaseos(Paseos paseos) {
        this.paseos = paseos;
    }

    public VOMinivan getVO() {
        return new VOMinivan(
                this.matricula,
                this.capacidad,
                this.marca
        );
    }
}