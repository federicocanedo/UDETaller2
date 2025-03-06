package src.logica.minivan;

import src.logica.paseo.Paseos;

import java.io.Serializable;

public class Minivan implements Serializable {
    private String matricula;
    private int capacidad;
    private String marca;
    private Paseos paseos;
    private String modelo;


    public Minivan(VOMinivan vo) {
        this.matricula = vo.getMatricula();
        this.capacidad = vo.getCapacidad();
        this.marca = vo.getMarca();
        this.paseos = new Paseos();
        this.modelo = vo.getModelo();
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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
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
                this.marca,
                this.modelo,
                this.paseos.listarPaseos()
        );
    }
}