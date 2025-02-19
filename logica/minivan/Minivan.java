package logica.minivan;

import logica.paseo.Paseos;
import logica.paseo.VOPaseo;
import logica.exception.EntidadYaExisteException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Minivan implements Serializable {
    private String matricula;
    private int capacidad;
    private String modelo;
    private String marca;
    private Paseos paseos;

    // Constructor desde un objeto VOMinivan
    public Minivan(VOMinivan vo) {
        this.matricula = vo.getMatricula();
        this.capacidad = vo.getCapacidad();
        this.modelo = vo.getModelo();
        this.marca = vo.getMarca();
        this.paseos = new Paseos();

        // Agregar paseos desde VOMinivan
        List<VOPaseo> listaPaseos = vo.getPaseos();
        if (listaPaseos != null) {
            for (VOPaseo voPaseo : listaPaseos) {
                try {
                    this.paseos.insertarPaseo(voPaseo);
                } catch (EntidadYaExisteException e) {
                    System.err.println("Error: Paseo duplicado - " + voPaseo.getId());
                }
            }
        }
    }

    // Constructor con parámetros
    public Minivan(String mat, int cap, String model, String marc, Paseos paseo) {
        this.matricula = mat;
        this.modelo = model;
        this.marca = marc;
        this.capacidad = cap;
        this.paseos = paseo;
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

    public Paseos getPaseos() {
        return paseos;
    }

    public void setPaseos(Paseos paseos) {
        this.paseos = paseos;
    }

    // Devuelve una versión simplificada del objeto
    public VOMinivan getVO() {
        List<VOPaseo> listaPaseos = Arrays.asList(this.paseos.listarPaseos());
        return new VOMinivan(
                this.matricula,
                this.capacidad,
                this.modelo,
                this.marca,
                listaPaseos
        );
    }
}
