package logica;

import controladores.*;
import java.rmi.RemoteException;
import GUI.*;
import logica.boletos.VOBoleto;
import logica.minivan.VOMinivan;
import logica.paseo.VOPaseo;
import java.time.LocalTime;

public class Test {
    public static void main(String[] args) {
        try {
            // Instanciar la fachada localmente
            IFachada fachada = new Fachada(); 

            // --- REGISTRAR MINIVANS ---
            VOMinivan minivan1 = new VOMinivan("ABC123", 15, "Hiace", "Toyota");
            VOMinivan minivan2 = new VOMinivan("XYZ789", 12, "Sprinter", "Mercedes");

            fachada.registrarMinivan(minivan1);
            fachada.registrarMinivan(minivan2);

            // --- REGISTRAR PASEOS ---
            VOPaseo paseo1 = new VOPaseo("P001", "Montevideo", LocalTime.of(10, 0), LocalTime.of(14, 0), 100, 10, new VOBoleto[0]);
            VOPaseo paseo2 = new VOPaseo("P002", "Colonia", LocalTime.of(9, 0), LocalTime.of(13, 0), 120, 8, new VOBoleto[0]);

            fachada.registrarPaseo(paseo1);
            fachada.registrarPaseo(paseo2);



            System.out.println("Paseos registrados y asignados a la minivan.");

            // --- MOSTRAR VENTANA DEL CUARTO REQUERIMIENTO ---
            CuartoRequerimiento ventanaListaPaseos = new CuartoRequerimiento();
            ControladorListaPaseosMinivan controladorListaPaseos = new ControladorListaPaseosMinivan(ventanaListaPaseos, fachada);

            ventanaListaPaseos.setVisible(true); // Mostrar la ventana

        } catch (RemoteException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
