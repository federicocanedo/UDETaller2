package logica.paseo;

import java.io.Serializable;
import java.time.LocalTime;

public class VOPaseo implements Serializable {
    private String id;
    private String destino;
    private LocalTime horaPartida;
    private LocalTime horaLlegada;
    private int cantMaxBoletos;
    private int precioBase;

    public VOPaseo(
        String id,
        String destino,
        LocalTime horaPartida,
        LocalTime horaLlegada,
        int cantMaxBoletos,
        int precioBase
    ) {
        this.id = id;
        this.destino = destino;
        this.horaPartida = horaPartida;
        this.horaLlegada = horaLlegada;
        this.cantMaxBoletos = cantMaxBoletos;
        this.precioBase = precioBase;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public LocalTime getHoraPartida() { return horaPartida; }
    public void setHoraPartida(LocalTime horaPartida) { this.horaPartida = horaPartida; }

    public LocalTime getHoraLlegada() { return horaLlegada; } 
    public void setHoraLlegada(LocalTime horaLlegada) { this.horaLlegada = horaLlegada; }

    public int getCantMaxBoletos() { return cantMaxBoletos; }
    public void setCantMaxBoletos(int cantMaxBoletos) { this.cantMaxBoletos = cantMaxBoletos; }  

    public int getPrecioBase() { return precioBase; }
  
    public void setPrecioBase(int precioBase) { this.precioBase = precioBase; }
  
    public LocalTime getHoraRegreso() {
        return horaRegreso;
    }

    public void sethoraRegreso(LocalTime horaRegreso) {
        this.horaRegreso = horaRegreso;
    }
    
    public int getCantMaxBoletos() {
        return CantMaxBoletos;
    }

    public void CantMaxBoletos(int CantMaxBoletos) {
        this.CantMaxBoletos = CantMaxBoletos;
    }

    public int getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(int precioBase) {
        this.precioBase = precioBase;
    }
}
