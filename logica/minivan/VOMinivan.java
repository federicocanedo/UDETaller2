package logica.minivan;
import logica.paseo.VOPaseo;

import java.io.Serializable;

public class VOMinivan implements Serializable {
    private VOPaseo[] paseos;
    private String matricula;
    private int capacidad;
    private String marca;
    private int cantAsignados;

    public VOMinivan(String matricula, int capacidad, String marca, VOPaseo[] paseos) {
        this.matricula = matricula;
        this.capacidad = capacidad;
        this.marca = marca;
        this.paseos = paseos;
        this.cantAsignados = paseos.length;
    }

    public VOMinivan(String matricula, int capacidad, String marca) {
        this.matricula = matricula;
        this.capacidad = capacidad;
        this.marca = marca;
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

    public int getCantAsignados() {
        return cantAsignados;
    }

    public void setCantAsignados(int cantAsignados) {
        this.cantAsignados = cantAsignados;
    }
}