package src.logica.boletos;

import java.io.Serializable;

public class VOBoletoEspecial extends VOBoleto implements Serializable {
    private double descuento;

    public VOBoletoEspecial(int id, String p_nombre, int p_edad, String p_numCelular, double descuento) {
        super(id, p_nombre, p_edad, p_numCelular);
        this.descuento = descuento;
    }
    public VOBoletoEspecial(String p_nombre, int p_edad, String p_numCelular, double descuento) {
        super(p_nombre, p_edad, p_numCelular);
        this.descuento = descuento;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
}
