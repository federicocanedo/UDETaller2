package persistencia;

import logica.paseo.Paseos;
import logica.minivan.Minivans;
import java.io.Serializable;

public class VORespaldo implements Serializable {
    private Paseos paseos;
    private Minivans minivans;

    public VORespaldo(Paseos paseos, Minivans minivans) {
        this.paseos = paseos;
        this.minivans = minivans;
    }

    public Paseos getPaseos() {
        return paseos;
    }

    public void setPaseos(Paseos paseos) {
        this.paseos = paseos;
    }

    public Minivans getMinivans() {
        return minivans;
    }

    public void setMinivans(Minivans minivans) {
        this.minivans = minivans;
    }
}
