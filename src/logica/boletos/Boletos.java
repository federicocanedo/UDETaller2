package src.logica.boletos;

import java.util.ArrayList;
import java.util.Arrays;

public class Boletos extends ArrayList<Boleto> {

    public Boleto buscarBoleto(int id) {
        for (Boleto boleto : this) {
            if (boleto.getId() == id) {
                return boleto;
            }
        }
        return null;
    }

    public void agregarBoleto(VOBoleto boleto) {
        VOBoleto boletoCopia;
        if (boleto instanceof VOBoletoEspecial) {
            VOBoletoEspecial boletoEsp = (VOBoletoEspecial) boleto;
            boletoCopia = new VOBoletoEspecial(
                boletoEsp.getP_nombre(),
                boletoEsp.getP_edad(),
                boletoEsp.getP_numCelular(),
                boletoEsp.getDescuento()
            );
        } else {
            boletoCopia = new VOBoleto(
                boleto.getP_nombre(),
                boleto.getP_edad(),
                boleto.getP_numCelular()
            );
        }

        // Asignar nuevo ID
        int nuevoId = size() + 1;
        boletoCopia.setId(nuevoId);
        
        // Crear y agregar el boleto
        if (boletoCopia instanceof VOBoletoEspecial) {
            add(new BoletoEspecial((VOBoletoEspecial) boletoCopia));
        } else {
            add(new Boleto(boletoCopia));
        }
    }

    public VOBoleto[] listarBoletos(boolean comun, boolean especiales) {
        VOBoleto[] voBoletos = new VOBoleto[size()];
        int index = 0;
        for (Boleto boleto : this) {
            if (boleto instanceof BoletoEspecial && especiales) {
                voBoletos[index++] = ((BoletoEspecial) boleto).getVO();
            } else if (!(boleto instanceof BoletoEspecial) && comun) {
                voBoletos[index++] = boleto.getVO();
            }
        }
        return Arrays.copyOf(voBoletos, index);
    }
}
