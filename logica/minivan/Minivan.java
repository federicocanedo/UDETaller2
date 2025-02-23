package logica.minivan;

import java.time.LocalTime;
import java.util.TreeMap;
import logica.paseo.*;

public class Minivan {
    private String matricula;
    private String marca;
    private int capacidad;
    private TreeMap<String, Paseo> paseosAsignados; // Diccionario de paseos asignados

    public Minivan(VOMinivan voMinivan) {
        this.matricula = voMinivan.getMatricula();
        this.marca = voMinivan.getMarca();
        this.capacidad = voMinivan.getCapacidad();
        this.paseosAsignados = new TreeMap<>();
    }

    // Getters y Setters
    public String getMatricula() {
        return matricula;
    }

    public String getMarca() {
        return marca;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public TreeMap<String, Paseo> getPaseosAsignados() {
        return paseosAsignados;
    }

    // Método para obtener el VO de la minivan
    public VOMinivan getVO() {
        return new VOMinivan(matricula, capacidad, marca);
    }
    
    
    /// REQUERIMIENTO 3
    public boolean estaDisponible(LocalTime horaPartidaNueva, LocalTime horaRegresoNueva) {
        for (Paseo paseo : paseosAsignados.values()) {
            LocalTime horaPartidaExistente = paseo.getHoraPartida();
            LocalTime horaRegresoExistente = paseo.getHoraRegreso();

            // Verificar si hay superposición de horarios
            if (horaPartidaNueva.isBefore(horaRegresoExistente) && horaRegresoNueva.isAfter(horaPartidaExistente)) {
                return false; // Hay superposición, la minivan no está disponible
            }
        }
        return true; // No hay superposición, la minivan está disponible
    }
 // Método para agregar un paseo a la minivan
    public void agregarPaseo(Paseo paseo) {
        if (estaDisponible(paseo.getHoraPartida(), paseo.getHoraRegreso())) {
            paseosAsignados.put(paseo.getId(), paseo);
            System.out.println("Paseo " + paseo.getId() + " asignado a la minivan " + this.matricula);
        } else {
            System.out.println("Error: La minivan " + this.matricula + " no está disponible para el horario del paseo " + paseo.getId());
        }
    }
}