package logica.paseo;

import java.io.Serializable;
import java.time.LocalTime;
import logica.boletos.Boletos;

public class Paseo implements Serializable {
    private String id;
    private String destino;
    private LocalTime horaPartida;
    private LocalTime horaRegreso;
    private int CantMaxBoletos;
    private Boletos boletos;
    //// AGREGAR BOLETO --> COMO HACER HERENCIA ACA
    private int precioBase;

    public Paseo(VOPaseo vo) {
        this.id = vo.getId();
        this.destino = vo.getDestino();
        this.horaPartida = vo.getHoraPartida();
        this.horaRegreso = vo.getHoraRegreso();
        this.precioBase = vo.getPrecioBase();
        this.boletos = new Boletos();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Boletos getBoletos() { return boletos; }

    public void setBoletos(Boletos boletos) { this.boletos = boletos; }

    public VOPaseo getVO() {
        return new VOPaseo(
            this.id,
            this.destino,
            this.horaPartida,
            this.horaRegreso,
            this.precioBase,
            this.boletos.listarBoletos()
        );
    }
}
