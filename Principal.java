import java.time.LocalTime;
import java.util.List;

import logica.boletos.VOBoleto;
import logica.exception.*;
import logica.minivan.*;
import logica.paseo.*;

import java.util.List;

import java.util.List;

public class Principal {
    public static void main(String[] args) {
        // Crear una instancia de la Fachada
        Fachada fachada = new Fachada();

        // Registrar una minivan (esto no se mostrará en la salida)
        fachada.registrarMinivan("MIN001", "Toyota", 10);

        // Registrar un paseo (esto no se mostrará en la salida)
        LocalTime horaPartida = LocalTime.of(8, 0); // 8:00 AM
        LocalTime horaRegreso = LocalTime.of(12, 0); // 12:00 PM
        fachada.registrarPaseo("P0012", "Playa", horaPartida, horaRegreso, 50);

        // Vender boletos comunes y especiales (esto no se mostrará en la salida)
        venderBoleto(fachada, "P0012", "Juan Perez", 25, "12345678", false, 0); // Boleto común
        venderBoleto(fachada, "P0012", "Maria Gomez", 30, "87654321", false, 0); // Boleto común
        venderBoleto(fachada, "P0012", "Carlos Ruiz", 40, "55555555", true, 10); // Boleto especial
        venderBoleto(fachada, "P0012", "Ana Lopez", 17, "99999999", true, 5);    // Boleto especial

        // Listar boletos vendidos para el paseo P0012
        System.out.println("Listado de boletos vendidos para el paseo P0012:");
        try {
            List<VOBoleto> boletosVendidos = fachada.listarBoletosVendidos("P0012", false); // Cambia a true para boletos especiales
            for (VOBoleto boleto : boletosVendidos) {
                System.out.println("Número: " + boleto.getId() +
                                   ", Nombre: " + boleto.getP_nombre() +
                                   ", Edad: " + boleto.getP_edad() +
                                   ", Celular: " + boleto.getP_numCelular() +
                                   (boleto.getBoletoEspecial() != null ? ", Descuento: " + boleto.getBoletoEspecial().getDescuento() : ""));
            }
        } catch (EntidadNoExisteException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método auxiliar para vender un boleto (esto no se mostrará en la salida)
    private static void venderBoleto(Fachada fachada, String codigoPaseo, String nombreTurista, int edadTurista, String numeroCelular, boolean esBoletoEspecial, double descuento) {
        fachada.venderBoleto(codigoPaseo, nombreTurista, edadTurista, numeroCelular, esBoletoEspecial, descuento);
    }
}