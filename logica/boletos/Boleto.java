package logica.boletos;

import java.io.Serializable;

public class Boleto implements Serializable {
    private int id;
    private String p_nombre;
    private int p_edad;
    private String p_numCelular;
    
/// CREAR UN NUEVO OBJETO BOLETO ESPECIAL????

    public Boleto(VOBoleto vo) {
        this.id = vo.getId();
        this.p_nombre = vo.getP_nombre();
        this.p_edad = vo.getP_edad();
        this.p_numCelular = vo.getP_numCelular();
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

    public VOBoleto getVO() {
        return new VOBoleto(
            this.id,
            this.p_nombre,
            this.p_edad,
            this.p_numCelular
        );
    }
}
