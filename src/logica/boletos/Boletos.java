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
        boleto.setId(size() + 1);
        if (boleto instanceof VOBoletoEspecial) {
            add(new BoletoEspecial((VOBoletoEspecial) boleto));
            return;
        }
        add(new Boleto(boleto));
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
