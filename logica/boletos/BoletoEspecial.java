package logica.boletos;

public class BoletoEspecial extends Boleto {
    private String tipoDescuento;

    public BoletoEspecial(int id, String p_nombre, int p_edad, String p_numCelular, String tipoDescuento) {
        super(id, p_nombre, p_edad, p_numCelular);
        
        this.tipoDescuento = tipoDescuento;
    }

    public String getTipoDescuento() 
    {
    	return tipoDescuento; 
    }
    public void setTipoDescuento(String tipoDescuento) 
    { 
    	this.tipoDescuento = tipoDescuento; 
    }
}
