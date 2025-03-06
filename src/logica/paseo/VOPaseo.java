package src.logica.paseo;

import src.logica.boletos.VOBoleto;

import java.io.Serializable;
import java.time.LocalTime;

public class VOPaseo implements Serializable {
    private VOBoleto[] boletos;
    private String id;
    private String destino;
    private LocalTime horaPartida;
    private LocalTime horaRegreso;
    private int precioBase;

    private int cantMaxBoletos;

    public VOBoleto[] getBoletos() {
        return boletos;
    }

    public VOPaseo(
        String id,
        String destino,
        LocalTime horaPartida,
        LocalTime horaRegreso,
        int precioBase,
        int cantMaxBoletos,
        VOBoleto[] boletos
    ) {
        this.id = id;
        this.destino = destino;
        this.horaPartida = horaPartida;
        this.horaRegreso = horaRegreso;
        this.precioBase = precioBase;
        this.boletos = boletos;
        this.cantMaxBoletos = cantMaxBoletos;
    }

    public VOPaseo(
            String id,
            String destino,
            LocalTime horaPartida,
            LocalTime horaRegreso,
            int precioBase
    ) {
        this.id = id;
        this.destino = destino;
        this.horaPartida = horaPartida;
        this.horaRegreso = horaRegreso;
        this.precioBase = precioBase;
    }

    public String getId() {
        return id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalTime getHoraPartida() {
        return horaPartida;
    }

    public void setHoraPartida(LocalTime horaPartida) {
        this.horaPartida = horaPartida;
    }

    public LocalTime getHoraRegreso() {
        return horaRegreso;
    }

    public void sethoraRegreso(LocalTime horaRegreso) {
        this.horaRegreso = horaRegreso;
    }

    public int getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(int precioBase) {
        this.precioBase = precioBase;
    }

    public int getCantMaxBoletos() {
        return cantMaxBoletos;
    }

    public void setCantMaxBoletos(int cantMaxBoletos) {
        this.cantMaxBoletos = cantMaxBoletos;
    }
}
