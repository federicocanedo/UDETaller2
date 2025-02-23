import logica.*;
import persistencia.*;

import logica.boletos.*;
import logica.exception.*;
import logica.minivan.*;
import logica.paseo.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class IFachada {
	private Paseos diccionarioPaseos = new Paseos();
    // REQUERIMIENTO 1
    private Minivans diccionarioMinivans = new Minivans();

    public boolean registrarMinivan(String matricula, String marca, int capacidad) {
        if (capacidad <= 0) {
            System.out.println("Error: La minivan debe tener al menos 1 asiento.");
            return false;
        }

        try {
            VOMinivan nuevaMinivan = new VOMinivan(matricula, capacidad, marca);
            diccionarioMinivans.insertarMinivan(nuevaMinivan);
            System.out.println("Minivan registrada con éxito: " + matricula);
            return true;
        } catch (EntidadYaExisteException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public VOMinivan obtenerMinivan(String matricula) {
        Minivan minivan = diccionarioMinivans.buscarMinivan(matricula);
        return (minivan != null) ? minivan.getVO() : null;
    }

    // REQUERIMIENTO 2: Listado general de minivanes
    public void listarMinivans() {
        VOMinivan[] listado = diccionarioMinivans.listarMinivans(); // Obtenemos el listado de minivanes

        if (listado.length == 0) {
            System.out.println("No hay minivanes registradas.");
        } else {
            // Recorremos el listado y mostramos los datos de cada minivan
            for (VOMinivan vo : listado) {
                System.out.println("Matrícula: " + vo.getMatricula() + 
                                   ", Marca: " + vo.getMarca() + 
                                   ", Capacidad: " + vo.getCapacidad() + 
                                   ", Paseos Asignados: " + vo.getCantidadPaseos());
            }
        }
    }

    // Métodos para persistencia
    public void guardarDatos(Paseos paseos, Minivans minivans) {
        Persistencia persistencia = new Persistencia();

        try {
            persistencia.respaldar("respaldo.dat", new VORespaldo(paseos, minivans));
        } catch (PersistenciaException e) {
            System.out.println(e.getMessage());
        }
    }

    public VORespaldo recuperarDatos() {
        Persistencia persistencia = new Persistencia();

        try {
            return persistencia.recuperar("respaldo.dat");
        } catch (PersistenciaException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    /// REQUERIMIENTO 3
    public boolean registrarPaseo(String codigo, String destino, LocalTime horaPartida, LocalTime horaRegreso, int precioBase) {
        // Verificar si el paseo ya está registrado
        if (diccionarioPaseos.containsKey(codigo)) {
            System.out.println("Error: El paseo con código " + codigo + " ya está registrado.");
            return false;
        }

        // Verificar que el precio base sea mayor que 0
        if (precioBase <= 0) {
            System.out.println("Error: El precio base debe ser mayor a 0.");
            return false;
        }

        // Buscar una minivan disponible para el horario solicitado
        Minivan minivanDisponible = null;
        for (Minivan minivan : diccionarioMinivans.values()) {
            if (minivan.estaDisponible(horaPartida, horaRegreso)) {
                minivanDisponible = minivan;
                break;
            }
        }

        // Si no hay minivan disponible, mostrar error
        if (minivanDisponible == null) {
            System.out.println("Error: No hay minivans disponibles para el horario solicitado.");
            return false;
        }

        // Crear el paseo y asignarlo a la minivan disponible
        int cantMaxBoletos = minivanDisponible.getCapacidad(); // La capacidad de la minivan es la cantidad máxima de boletos
        VOPaseo nuevoPaseo = new VOPaseo(codigo, destino, horaPartida, horaRegreso, cantMaxBoletos, precioBase);

        try {
            // Registrar el paseo en el diccionario de paseos
            diccionarioPaseos.insertarPaseo(nuevoPaseo);

            // Asignar el paseo a la minivan
            minivanDisponible.agregarPaseo(new Paseo(nuevoPaseo));

            System.out.println("Paseo registrado con éxito y asignado a la minivan: " + minivanDisponible.getMatricula());
            return true;
        } catch (EntidadYaExisteException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }


    // Método auxiliar para contar boletos vendidos de un paseo
    private int contarBoletosVendidos(String idPaseo) {
        Paseo paseo = diccionarioPaseos.get(idPaseo);
        return (paseo != null) ? paseo.getBoletos().size() : 0;
    }
 // REQUERIMIENTO 4: Listado de paseos asignados a una minivan
    public void listarPaseosDeMinivan(String matricula) {
        // Buscar la minivan por matrícula
        Minivan minivan = diccionarioMinivans.buscarMinivan(matricula);

        if (minivan == null) {
            System.out.println("Error: No se encontró una minivan con la matrícula " + matricula);
            return;
        }

        // Obtener los paseos asignados a la minivan
        TreeMap<String, Paseo> paseosAsignados = minivan.getPaseosAsignados();

        if (paseosAsignados.isEmpty()) {
            System.out.println("La minivan " + matricula + " no tiene paseos asignados.");
            return;
        }

        // Recorrer los paseos y mostrar la información
        System.out.println("Paseos asignados a la minivan " + matricula + ":");
        for (Paseo paseo : paseosAsignados.values()) {
            int boletosDisponibles = paseo.getCantMaxBoletos() - paseo.getBoletos().size();
            System.out.println("Código: " + paseo.getId() +
                               ", Destino: " + paseo.getDestino() +
                               ", Hora de Partida: " + paseo.getHoraPartida() +
                               ", Hora de Regreso: " + paseo.getHoraRegreso() +
                               ", Precio Base: " + paseo.getPrecioBase() +
                               ", Máx. Boletos: " + paseo.getCantMaxBoletos() +
                               ", Boletos Disponibles: " + boletosDisponibles);
        }
    }
    ///REQUERIMIENTO 5
    public void listarPaseosPorDestino(String destino) {
        if (diccionarioPaseos.isEmpty()) {
            System.out.println("Error: No hay paseos registrados.");
            return;
        }

        VOPaseo[] listaPaseos = diccionarioPaseos.listarPaseos(); // Obtener todos los paseos
        boolean encontrado = false;

        for (VOPaseo paseo : listaPaseos) {
            if (paseo.getDestino().equalsIgnoreCase(destino)) {
                encontrado = true;
                int boletosDisponibles = paseo.getCantMaxBoletos() - contarBoletosVendidos(paseo.getId());
                System.out.println("Código: " + paseo.getId() +
                                   ", Destino: " + paseo.getDestino() +
                                   ", Hora de Partida: " + paseo.getHoraPartida() +
                                   ", Hora de Regreso: " + paseo.getHoraRegreso() +
                                   ", Precio Base: " + paseo.getPrecioBase() +
                                   ", Máx. Boletos: " + paseo.getCantMaxBoletos() +
                                   ", Boletos Disponibles: " + boletosDisponibles);
            }
        }

        if (!encontrado) {
            System.out.println("El destino ingresado no se encontró en los paseos registrados.");
        }
    }
 // REQUERIMIENTO 6: Listado de paseos por disponibilidad de boletos
    public void listarPaseosPorDisponibilidadBoletos(int cantidadBoletos) {
        if (diccionarioPaseos.isEmpty()) {
            System.out.println("Error: No hay paseos registrados.");
            return;
        }

        // Obtener todos los paseos registrados
        VOPaseo[] paseos = diccionarioPaseos.listarPaseos();
        boolean hayPaseosDisponibles = false;

        System.out.println("\nListado de paseos con al menos " + cantidadBoletos + " boletos disponibles:");
        for (VOPaseo paseo : paseos) {
            // Calcular la cantidad de boletos disponibles
            int boletosDisponibles = paseo.getCantMaxBoletos() - contarBoletosVendidos(paseo.getId());

            // Verificar si el paseo tiene al menos la cantidad de boletos solicitados
            if (boletosDisponibles >= cantidadBoletos) {
                hayPaseosDisponibles = true;
                System.out.println("Código: " + paseo.getId() +
                                   ", Destino: " + paseo.getDestino() +
                                   ", Hora de Partida: " + paseo.getHoraPartida() +
                                   ", Hora de Regreso: " + paseo.getHoraRegreso() +
                                   ", Precio Base: " + paseo.getPrecioBase() +
                                   ", Máx. Boletos: " + paseo.getCantMaxBoletos() +
                                   ", Boletos Disponibles: " + boletosDisponibles);
            }
        }

        // Si no hay paseos con la cantidad de boletos solicitados, mostrar un mensaje
        if (!hayPaseosDisponibles) {
            System.out.println("No hay paseos con al menos " + cantidadBoletos + " boletos disponibles.");
        }
    }
 // REQUERIMIENTO 7
    public boolean venderBoleto(String codigoPaseo, String nombreTurista, int edadTurista, String numeroCelular, boolean esBoletoEspecial, double descuento) {
        // Verificar si el paseo existe
        Paseo paseo = diccionarioPaseos.get(codigoPaseo);
        if (paseo == null) {
            System.out.println("Error: No existe un paseo con el código " + codigoPaseo);
            return false;
        }

        // Verificar si hay boletos disponibles
        int boletosDisponibles = paseo.getCantMaxBoletos() - paseo.getBoletos().size();
        if (boletosDisponibles <= 0) {
            System.out.println("Error: No hay boletos disponibles para el paseo " + codigoPaseo);
            return false;
        }

        // Validar datos del turista
        if (edadTurista <= 0 || numeroCelular == null || numeroCelular.trim().isEmpty()) {
            System.out.println("Error: La edad y el número de celular deben ser válidos.");
            return false;
        }

        // Validar descuento si es boleto especial
        if (esBoletoEspecial && descuento <= 0) {
            System.out.println("Error: El descuento debe ser mayor que 0 para boletos especiales.");
            return false;
        }

        // Calcular el costo del boleto
        double costoBoleto;
        if (esBoletoEspecial) {
            costoBoleto = paseo.getPrecioBase() - descuento;
        } else {
            if (edadTurista <= 18) {
                costoBoleto = paseo.getPrecioBase() * 0.75; // Descuento para menores de 18
            } else {
                costoBoleto = paseo.getPrecioBase(); // Precio base para adultos
            }
        }

        // Crear el boleto
        int numeroBoleto = paseo.getBoletos().size() + 1; // Número consecutivo
        VOBoleto voBoleto;
        if (esBoletoEspecial) {
            voBoleto = new VOBoleto(numeroBoleto, nombreTurista, edadTurista, numeroCelular, new BoletoEspecial(numeroBoleto, nombreTurista, edadTurista, numeroCelular, descuento));
        } else {
            voBoleto = new VOBoleto(numeroBoleto, nombreTurista, edadTurista, numeroCelular, null);
        }

        // Registrar el boleto en el paseo
        paseo.agregarBoleto(voBoleto);

        // Mostrar mensaje de éxito
        System.out.println("Venta exitosa. Boleto número: " + numeroBoleto + ", Costo: " + costoBoleto);
        return true;
    }
    
    ///REQUERIMIENTO 8
    public List<VOBoleto> listarBoletosVendidos(String codigoPaseo, boolean esBoletoEspecial) {
        // Verificar si el paseo existe
        Paseo paseo = diccionarioPaseos.get(codigoPaseo);
        if (paseo == null) {
            System.out.println("Error: No existe un paseo con el código " + codigoPaseo);
            return null;
        }

        // Obtener la lista de boletos del paseo
        List<VOBoleto> listadoVO = new ArrayList<>();
        for (Boleto boleto : paseo.getBoletos().getBoletos()) {
            // Filtrar por tipo de boleto
            if ((esBoletoEspecial && boleto.getBoletoEspecial() != null) ||
                (!esBoletoEspecial && boleto.getBoletoEspecial() == null)) {
                // Crear un VOBoleto para el listado
                VOBoleto voBoleto = new VOBoleto(
                    boleto.getId(),
                    boleto.getP_nombre(),
                    boleto.getP_edad(),
                    boleto.getP_numCelular(),
                    boleto.getBoletoEspecial()
                );
                listadoVO.add(voBoleto);
            }
        }

        // Ordenar el listado por número de boleto (ascendente)
        Collections.sort(listadoVO, Comparator.comparingInt(VOBoleto::getId));

        return listadoVO;
    }

 

	}

