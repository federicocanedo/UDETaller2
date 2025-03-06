// ArchivoConfiguracion.java
package src.configuracion;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ArchivoConfiguracion {
    private static ArchivoConfiguracion instancia = null;
    private Properties propiedades;

    private ArchivoConfiguracion() {
        propiedades = new Properties();
        try {
            propiedades.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArchivoConfiguracion getInstancia() {
        if (instancia == null) {
            instancia = new ArchivoConfiguracion();
        }
        return instancia;
    }

    public String getIpServidor() {
        return propiedades.getProperty("rmi.server.ip", "localhost");
    }

    public String getPuertoServidor() {
        return propiedades.getProperty("rmi.server.port", "1099");
    }

    public String getNombreServidor() {
        return propiedades.getProperty("rmi.server.name", "CapaLogica");
    }
}