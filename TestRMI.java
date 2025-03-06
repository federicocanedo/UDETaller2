import src.configuracion.ArchivoConfiguracion;
import src.fachada.Fachada;
import src.fachada.IFachada;
import src.logica.boletos.VOBoleto;
import src.logica.boletos.VOBoletoEspecial;
import src.logica.minivan.Minivans;
import src.logica.minivan.VOMinivan;
import src.logica.paseo.Paseos;
import src.logica.paseo.VOPaseo;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.time.LocalTime;

public class TestRMI {
    public static void main(String[] args) throws RemoteException {
        Paseos paseos = new Paseos();
        Minivans minivans = new Minivans();

        IFachada fachada = null;
        try {
            ArchivoConfiguracion config = ArchivoConfiguracion.getInstancia();
            String rmiUrl = String.format("//%s:%s/%s",
                    config.getIpServidor(),
                    config.getPuertoServidor(),
                    config.getNombreServidor());
            
            System.out.println("Conectando a servicio RMI en: " + rmiUrl);
            fachada = (IFachada) Naming.lookup(rmiUrl);
            System.out.println("Conexión exitosa al servicio RMI");
        } catch (Exception e) {
            System.out.println("Error al conectar con el servicio RMI: " + e.getMessage());
            return;
        }

        // Requerimiento 1: Registro de minivan
        try {
            fachada.registrarMinivan(
                    new VOMinivan("ABC123", 7, "RolsRoice", "juanpere")
            );
            System.out.println("Minivan registrada exitosamente");
        } catch (Exception e) {
            System.out.println("Error al registrar minivan: " + e.getMessage());
        }

        // Requerimiento 2: Listado general de minivanes
        System.out.println("\nListar minivans:");
        try {
            for (VOMinivan minivan : fachada.listarMinivans()) {
                System.out.println(
                        "Matricula: " + minivan.getMatricula() +
                                "\nMarca: " + minivan.getMarca() +
                                "\nModelo: " + minivan.getModelo() +
                                "\nCantidad de asientos: " + minivan.getCapacidad() +
                                "\nCantidad de paseos asignados: " + minivan.getCantAsignados()
                );
            }
        } catch (Exception e) {
            System.out.println("Error al listar minivans: " + e.getMessage());
        }

        // Requerimiento 3: Registro de paseo
        try {
            fachada.registrarPaseo(
                    new VOPaseo(
                            "195",
                            "Playa del Cerro",
                            LocalTime.of(9, 0),
                            LocalTime.of(12, 0),
                            100
                    )
            );
            System.out.println("\nPaseo registrado exitosamente");
        } catch (Exception e) {
            System.out.println("Error al registrar paseo: " + e.getMessage());
        }

        // Requerimiento 4: Listado de paseos por minivan
        System.out.println("\nListar paseos por minivan:");
        try {
            for (VOPaseo paseo : fachada.listarPaseosDeMinivan("ABC123")) {
                System.out.println(
                        "Codigo: " + paseo.getId() +
                                "\nDestino: " + paseo.getDestino() +
                                "\nHora de partida: " + paseo.getHoraPartida() +
                                "\nHora de regreso: " + paseo.getHoraRegreso() +
                                "\nPrecio base: " + paseo.getPrecioBase() +
                                "\nCantidad de boletos disponibles: " + (paseo.getCantMaxBoletos() - paseo.getBoletos().length)
                );
            }
        } catch (Exception e) {
            System.out.println("Error al listar paseos por minivan: " + e.getMessage());
        }

        // Requerimiento 5: Listado de paseos por destino
        System.out.println("\nListar paseos por destino:");
        try {
            for (VOPaseo paseo : fachada.listarPaseosPorDestino("Playa del Cerro")) {
                System.out.println(
                        "Codigo: " + paseo.getId() +
                                "\nDestino: " + paseo.getDestino() +
                                "\nHora de partida: " + paseo.getHoraPartida() +
                                "\nHora de regreso: " + paseo.getHoraRegreso() +
                                "\nPrecio base: " + paseo.getPrecioBase() +
                                "\nCantidad de boletos disponibles: " + (paseo.getCantMaxBoletos() - paseo.getBoletos().length)
                );
            }
        } catch (Exception e) {
            System.out.println("Error al listar paseos por destino: " + e.getMessage());
        }

        // Requerimiento 6: Listado de paseos por disponibilidad
        System.out.println("\nListar paseos por disponibilidad de boletos:");
        try {
            for (VOPaseo paseo : fachada.listarPaseosPorDisponibilidadBoletos(5)) {
                System.out.println(
                        "Codigo: " + paseo.getId() +
                                "\nDestino: " + paseo.getDestino() +
                                "\nHora de partida: " + paseo.getHoraPartida() +
                                "\nHora de regreso: " + paseo.getHoraRegreso() +
                                "\nPrecio base: " + paseo.getPrecioBase() +
                                "\nCantidad de boletos disponibles: " + (paseo.getCantMaxBoletos() - paseo.getBoletos().length)
                );
            }
        } catch (Exception e) {
            System.out.println("Error al listar paseos por disponibilidad: " + e.getMessage());
        }

        // Requerimiento 7: Venta de boletos
        try {
            VOBoletoEspecial boletoEspecial = new VOBoletoEspecial(
                    "Juan",
                    20,
                    "099123456",
                    5
            );

            VOBoleto boleto = new VOBoleto(
                    "Pere",
                    92,
                    "09001911"
            );

            fachada.venderBoleto("195", boletoEspecial);
            fachada.venderBoleto("195", boleto);
            System.out.println("\nBoletos vendidos exitosamente");
        } catch (Exception e) {
            System.out.println("Error al vender boletos: " + e.getMessage());
        }

        // Requerimiento 8: Listado de boletos por paseo
        System.out.println("\nListar boletos comunes de paseo:");
        try {
            for (VOBoleto b : fachada.listarBoletosDePaseo("195", true, false)) {
                System.out.println(
                        "id: " + b.getId()
                                + "\nNombre: " + b.getP_nombre()
                                + "\nEdad: " + b.getP_edad()
                                + "\nNumero de celular: " + b.getP_numCelular()
                );
            }
        } catch (Exception e) {
            System.out.println("Error al listar boletos comunes: " + e.getMessage());
        }

        System.out.println("\nListar boletos especiales de paseo:");
        try {
            for (VOBoleto b : fachada.listarBoletosDePaseo("195", false, true)) {
                System.out.println(
                        "id: " + b.getId()
                                + "\nNombre: " + b.getP_nombre()
                                + "\nEdad: " + b.getP_edad()
                                + "\nNumero de celular: " + b.getP_numCelular()
                                + "\nDescuento: " + ((VOBoletoEspecial) b).getDescuento()
                );
            }
        } catch (Exception e) {
            System.out.println("Error al listar boletos especiales: " + e.getMessage());
        }
    }
} 