package logica.minivan;

import logica.paseo.Paseos;

import java.io.Serializable;

public class Minivan implements Serializable {
    private String matricula;
    private int capacidad;
    private int plazasOcupadas;
    private Paseos paseos;

    public Minivan(String matricula, int capacidad, int plazasOcupadas, Paseos paseos) {
        this.matricula = matricula;
        this.capacidad = capacidad;
        this.plazasOcupadas = plazasOcupadas;
        this.paseos = paseos;
    }

    public Minivan(String matricula, int capacidad, int plazasOcupadas) {
        this.matricula = matricula;
        this.capacidad = capacidad;
        this.plazasOcupadas = plazasOcupadas;
        this.paseos = new Paseos();
    }

    public Minivan(VOMinivan vo) {
        this.matricula = vo.getMatricula();
        this.capacidad = vo.getCapacidad();
        this.plazasOcupadas = vo.getPlazasOcupadas();
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

    public int getPlazasOcupadas() {
        return plazasOcupadas;
    }

    public void setPlazasOcupadas(int plazasOcupadas) {
        this.plazasOcupadas = plazasOcupadas;
    }

    public VOMinivan getVO() {
        return new VOMinivan(
                this.matricula,
                this.capacidad,
                this.plazasOcupadas
        );
    }
}