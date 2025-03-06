package src.logica.boletos;

import java.io.Serializable;

public class BoletoEspecial extends Boleto implements Serializable {
    private double descuento;

    public BoletoEspecial(VOBoletoEspecial boleto) {
        super(boleto);
        this.descuento = boleto.getDescuento();
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public VOBoletoEspecial getVO() {
        return new VOBoletoEspecial(
            this.getId(),
            this.getP_nombre(),
            this.getP_edad(),
            this.getP_numCelular(),
            this.descuento
        );
    }
}
