package logica.boletos;

import logica.exception.EntidadYaExisteException;

import java.util.ArrayList;

public class Boletos extends ArrayList<Boleto> {

    public Boleto buscarBoleto(int id) {
        for (Boleto boleto : this) {
            if (boleto.getId() == id) {
                return boleto;
            }
        }
        return null;
    }

    public void agregarBoleto(Boleto boleto) {
        if (buscarBoleto(boleto.getId()) != null) {
            throw new EntidadYaExisteException("El boleto ya existe");
        }
        add(boleto);
    }

    public void eliminarBoleto(int id) {
        Boleto boleto = buscarBoleto(id);
        if (boleto == null) {
            throw new RuntimeException("El boleto no existe");
        }
        remove(boleto);
    }
}
