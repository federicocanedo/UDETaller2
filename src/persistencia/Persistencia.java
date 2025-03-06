package src.persistencia;

import src.logica.exception.PersistenciaException;

import java.io.*;

public class Persistencia {

    public void respaldar(String nomArch, VORespaldo vo) throws PersistenciaException {
        try {
            FileOutputStream f = new FileOutputStream(nomArch);
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(vo);
            o.close();
            f.close();
        } catch (IOException e) {
            throw new PersistenciaException("Error al respaldar: " + e.getMessage());
        }
    }

    public VORespaldo recuperar(String nomArch) throws PersistenciaException {
        try {
            FileInputStream f = new FileInputStream(nomArch);
            ObjectInputStream o = new ObjectInputStream(f);

            VORespaldo vo = (VORespaldo) o.readObject();
            o.close();
            f.close();

            return vo;
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenciaException("Error al recuperar: " + e.getMessage());
        }
    }
}
