package logica.boletos;

import java.io.Serializable;

public class VOBoleto implements Serializable {
    private int id;
    private String p_nombre;
    private int p_edad;
    private String p_numCelular;
    private BoletoEspecial boletoEspecial;  // Renombrado para claridad


    public VOBoleto(int id, String p_nombre, int p_edad, String p_numCelular, BoletoEspecial boletoEspecial) {
        this.id = id;
        this.p_nombre = p_nombre;
        this.p_edad = p_edad;
        this.p_numCelular = p_numCelular;
        this.boletoEspecial = boletoEspecial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getP_nombre() {
        return p_nombre;
    }

    public void setP_nombre(String p_nombre) {
        this.p_nombre = p_nombre;
    }

    public int getP_edad() {
        return p_edad;
    }

    public void setP_edad(int p_edad) {
        this.p_edad = p_edad;
    }

    public String getP_numCelular() {
        return p_numCelular;
    }

    public void setP_numCelular(String p_numCelular) {
        this.p_numCelular = p_numCelular;
    }

    public BoletoEspecial getBoletoEspecial() {
        return boletoEspecial;
    }

    public void setBoletoEspecial(BoletoEspecial boletoEspecial) {
        this.boletoEspecial = boletoEspecial;
    }
}
