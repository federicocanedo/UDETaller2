package src.cliente;

import src.cliente.grafica.minivans.ListarMinivansVentana;
import src.cliente.grafica.minivans.RegistrarMinivanVentana;
import src.cliente.grafica.paseos.ListarPaseosVentana;
import src.cliente.grafica.paseos.RegistrarPaseoVentana;
import src.cliente.grafica.boletos.ListarBoletosVentana;

public class MainVentana {

    public static void main(String[] args) {
        // Crear y mostrar ventana de registro de minivans
        RegistrarMinivanVentana ventanaRegistroMinivan = new RegistrarMinivanVentana();
        ventanaRegistroMinivan.setVisible(true);
        
        // Crear y mostrar ventana de minivans
        ListarMinivansVentana ventanaMinivans = new ListarMinivansVentana();
        ventanaMinivans.setVisible(true);
        
        // Crear y mostrar ventana de registro de paseos
        RegistrarPaseoVentana ventanaRegistroPaseo = new RegistrarPaseoVentana();
        ventanaRegistroPaseo.setVisible(true);
        
        // Crear y mostrar ventana de paseos
        ListarPaseosVentana ventanaPaseos = new ListarPaseosVentana();
        ventanaPaseos.setVisible(true);
        
        // Crear y mostrar ventana de boletos
        ListarBoletosVentana ventanaBoletos = new ListarBoletosVentana();
        ventanaBoletos.setVisible(true);
    }

}