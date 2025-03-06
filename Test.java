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

public class Test {
    public static void main(String[] args) throws RemoteException {
        Paseos paseos = new Paseos();
        Minivans minivans = new Minivans();

//        src.Fachada.Fachada fachada = new src.Fachada.Fachada();

        IFachada fachada = null;
        try {
            fachada = (IFachada) Naming.lookup("//localhost:1099/fachada");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Requerimiento 1
        /* Registro de minivan: Registrar los datos de una miniván,
        para que quede disponible para futuras asignaciones de paseos.
        Se debe verificar que no haya otra minivan ya registrada con la misma matrícula y
        que su cantidad de asientos sea mayor que cero.
        */
        fachada.registrarMinivan(
                new VOMinivan("ABC123", 7, "RolsRoice", "juanpere")
        );
        /*
        Requerimiento 2
        Listado general de minivanes:
        Obtener un listado conteniendo los siguientes datos de todas las minivanes registradas:
        matrícula, marca, modelo, cantidad de asientos y cantidad de paseos asignados.
        Este listado se brindará ordenado alfanuméricamente por matrícula
         */
        System.out.println("Listar minivans");
        for (VOMinivan minivan : fachada.listarMinivans()) {
            System.out.println(
                    "Matricula: " + minivan.getMatricula() +
                            "\nMarca: " + minivan.getMarca() +
                            "\nModelo: " + minivan.getModelo() +
                            "\nCantidad de asientos: " + minivan.getCapacidad() +
                            "\nCantidad de paseos asignados: " + minivan.getPaseos().length
            );
        }

        /*
        Requerimiento 3
        Registro de paseo:
        Registrar en el sistema los datos de un paseo:
        código, destino, hora de partida, hora de regreso y precio base.
        Al momento del registro, el sistema asignará en forma automática el paseo a la primera minivan
        que no tenga ningún otro paseo asignado entre esa hora de partida y esa hora de regreso.
        Además, tomará la cantidad de asientos de la minivan asignada para setear la cantidad máxima de boletos vendibles para el nuevo paseo.
        Se debe verificar que el precio base sea mayor que cero. Si no hay ninguna minivan con disponibilidad horaria,
        se producirá un error y el registro será cancelado.
         */
        fachada.registrarPaseo(
                new VOPaseo(
                        "195",
                        "Playa del Cerro",
                        LocalTime.of(9, 0),
                        LocalTime.of(12, 0),
                        100
                )
        );

        /* Requerimiento 4
        Listado de paseos asignados a una minivan:
        Dada la matrícula de una minivan, obtener un listado de todos sus paseos asignados.
        Este listado se brindará ordenado alfanuméricamente por código de paseo.
        De cada paseo se listará código, destino, hora de partida, hora de regreso, precio base,
        cantidad máxima de boletos vendibles y
        cantidad de boletos disponibles (o sea, cantidad máxima boletos vendibles menos la cantidad de boletos vendidos hasta el momento).
        Si no existe ninguna minivan registrada con esa matrícula, se producirá un error.
        */
        System.out.println("\n\nListar paseos por minivan:");
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

        /*
        Requerimiento 5
        Listado de paseos por destino:
        Dado un destino, obtener un listado de todos los paseos ofrecidos hacia dicho destino.
        Este listado se brindará ordenado alfanuméricamente por código de paseo.
        De cada paseo se listará código, destino, hora de partida, hora de regreso, precio base,
        cantidad máxima de boletos vendibles y cantidad de boletos disponibles
        (o sea, cantidad máxima boletos vendibles menos cantidad de boletos vendidos hasta el momento).
        */
        System.out.println("\n\nListar paseos por destino:");
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

        /*
        Requerimiento 6
        Listado de paseos por disponibilidad de boletos:
        Dada una cantidad de boletos, obtener un listado de todos los paseos que aún tienen al menos dicha cantidad de boletos disponibles para la venta.
        Este listado se brindará ordenado alfanuméricamente por código de paseo.
        De cada paseo se listará código, destino, hora de partida, hora de regreso, precio base,
        cantidad máxima de boletos vendibles y cantidad de boletos disponibles
        (o sea, cantidad máxima boletos vendibles menos cantidad de boletos vendidos hasta el momento).
        */
        System.out.println("\n\nListar paseos por cantidad de boletos disponibles:");
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



        /*
        Requerimiento 7
        Venta de boleto:
        Dados un código de paseo y los datos de un turista(nombre, edad, número de celular),
        registrar la venta de un boleto para ese turista en dicho paseo.
        El número del nuevo boleto será definido automáticamente por el sistema y
        será consecutivo al número del último boleto vendido hasta el momento para dicho paseo.
        Dependiendo de la documentación presentada por el turista,
        el vendedor decidirá en ese momento si le vende un boleto especial e ingresará el valor del descuento a otorgar
        (que también será definido por el vendedor al momento del registro).
        A los efectos del sistema es irrelevante verificar si el turista ya había viajado anteriormente o no,
        dado que no interesa mantener un historial de boletos vendidos ni de turistas a lo largo de los días. Se debe verificar que la edad,
        el número de celular y (si corresponde) el descuento sean mayores que cero. Si no existe ningún paseo registrado con ese código,
        o si ya se ha vendido la cantidad máxima de boletos vendibles para el paseo, se producirá un error y la venta será cancelada.
        */


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


        /*
        Requerimiento 8
        Listado de boletos vendidos para un paseo:
        Dados un código de paseo y un tipo de boleto (común o especial),
        obtener un listado de todos los boletos de ese tipo vendidos para dicho paseo.
        Este listado se brindará ordenado por número de boleto de menor a mayor. De cada boleto se listará número de boleto,
        nombre y edad del pasajero y número de celular. Si el boleto es especial, se listará además el valor del descuento correspondiente.
        Si no existe ningún paseo registrado con ese código, se producirá un error.
        */
        System.out.println("\n\nListar boletos comunes de paseo:");
        for (VOBoleto b : fachada.listarBoletosDePaseo("195", true, false)) {
            System.out.println(
                    "id: " + b.getId()
                            + "\nNombre: " + b.getP_nombre()
                            + "\nEdad: " + b.getP_edad()
                            + "\nNumero de celular: " + b.getP_numCelular()
            );
        }

        System.out.println("\n\nListar boletos especiales de paseo:");
        for (VOBoleto b : fachada.listarBoletosDePaseo("195", false, true)) {
            System.out.println(
                    "id: " + ((VOBoletoEspecial) b).getId()
                            + "\nNombre: " + ((VOBoletoEspecial) b).getP_nombre()
                            + "\nEdad: " + ((VOBoletoEspecial) b).getP_edad()
                            + "\nNumero de celular: " + ((VOBoletoEspecial) b).getP_numCelular()
                            + "\nDescuento: " + ((VOBoletoEspecial) b).getDescuento()
            );
        }

        /* Requerimiento 10
        Respaldo de datos:
        Respaldar en disco todos los datos de la aplicación.
        Este requerimiento podrá ejecutarse toda vez que algún vendedor lo considere oportuno,
        especialmente luego de haber ejecutado funcionalidades que produzcan cambios en la información del sistema.
        Todos los datos se respaldarán juntos en un único archivo binario en disco (ubicado en el Servidor Central),
        para luego poder ser recuperados a memoria en una próxima ejecución.
        */
        fachada.guardarDatos();

        /*
        Requerimiento 11
        Recuperación de datos:
        Recuperar a memoria todos los datos de la aplicación almacenados en disco.
        Este requerimiento será ejecutado automáticamente en el servidor central cada vez que el sistema inicie su ejecución
        (no será disparado por ningún usuario que explícitamente solicite su ejecución).
        */

        Fachada fachada2 = new Fachada();
        fachada2.recuperarDatos();
        System.out.println("\n\nRecuperar datos fachada 2");
        for (VOMinivan minivan : fachada.listarMinivans()) {
            System.out.println(
                    "Matricula: " + minivan.getMatricula() +
                            "\nMarca: " + minivan.getMarca() +
                            "\nModelo: " + minivan.getModelo() +
                            "\nCantidad de asientos: " + minivan.getCapacidad() +
                            "\nCantidad de paseos asignados: " + minivan.getPaseos().length
            );
        }

    }
}