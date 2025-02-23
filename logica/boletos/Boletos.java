package logica.boletos;

import logica.exception.EntidadNoExisteException;
import logica.exception.EntidadYaExisteException;

import java.util.ArrayList;
import java.util.List;

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
        if (buscarBoleto(boleto.getId()) != null) {
            throw new EntidadYaExisteException("El boleto ya existe");
        }
        add(new Boleto(boleto));
    }

    public void eliminarBoleto(int id) {
        Boleto boleto = buscarBoleto(id);
        if (boleto == null) {
            throw new EntidadNoExisteException("El boleto no existe");
        }
        remove(boleto);
    }

    public VOBoleto[] listarBoletos() {
        VOBoleto[] voBoletos = new VOBoleto[size()];
        int index = 0;
        for (Boleto boleto : this) {
            voBoletos[index++] = boleto.getVO();
        }
        return voBoletos;
    }
    public List<Boleto> getBoletos() {
        return this;
    }
}
