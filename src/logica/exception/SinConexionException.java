package src.logica.exception;

public class SinConexionException extends Exception {
    public SinConexionException() {
        super("No hay conexión con el servidor");
    }

    public SinConexionException(String message) {
        super(message);
    }
} 