package src.cliente;

import src.cliente.ventanas.minivans.ListarMinivansVentana;
import src.cliente.ventanas.paseos.ListarPaseosVentana;
import src.cliente.ventanas.boletos.ListarBoletosVentana;

public class MainVentana {

    public static void main(String[] args) {
        // Crear y mostrar ventana de minivans
        ListarMinivansVentana ventanaMinivans = new ListarMinivansVentana();
        ventanaMinivans.setVisible(true);
        
        // Crear y mostrar ventana de paseos
        ListarPaseosVentana ventanaPaseos = new ListarPaseosVentana();
        ventanaPaseos.setVisible(true);
        
        // Crear y mostrar ventana de boletos
        ListarBoletosVentana ventanaBoletos = new ListarBoletosVentana();
        ventanaBoletos.setVisible(true);
    }

}