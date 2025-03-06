package src.logica.minivan;
import src.logica.paseo.VOPaseo;

import java.io.Serializable;

public class VOMinivan implements Serializable {
    private VOPaseo[] paseos;
    private String matricula;
    private int capacidad;
    private String marca;
    private int cantAsignados;
    private String modelo;

    public VOMinivan(String matricula, int capacidad, String marca, String modelo, VOPaseo[] paseos) {
        this.matricula = matricula;
        this.capacidad = capacidad;
        this.marca = marca;
        this.paseos = paseos;
        this.modelo = modelo;
        this.cantAsignados = paseos.length;
    }

    public VOMinivan(String matricula, int capacidad, String marca, String modelo) {
        this.matricula = matricula;
        this.capacidad = capacidad;
        this.marca = marca;
        this.modelo = modelo;
        this.cantAsignados = 0;
    }

    public VOPaseo[] getPaseos() {
        return paseos;
    }

    public void setPaseos(VOPaseo[] paseos) {
        this.paseos = paseos;
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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCantAsignados() {
        return cantAsignados;
    }

    public void setCantAsignados(int cantAsignados) {
        this.cantAsignados = cantAsignados;
    }
}