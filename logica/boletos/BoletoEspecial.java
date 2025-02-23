package logica.boletos;

public class BoletoEspecial extends Boleto {
	private double descuento;

    public BoletoEspecial(int id, String p_nombre, int p_edad, String p_numCelular, double descuento) {
        super(id, p_nombre, p_edad, p_numCelular);
        this.descuento = descuento;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
}
