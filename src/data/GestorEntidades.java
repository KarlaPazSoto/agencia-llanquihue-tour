package data;

import model.Registrable;
import model.Tour;
import model.Persona;
import model.Cliente;
import model.GuiaTuristico;
import model.ServicioTuristico;
import model.PaseoLacustre;
import model.ExcursionCultural;
import model.RutaGastronomica;
import java.util.ArrayList;

/**
 * Gestor de entidades genérico que administra todas las entidades registrables del sistema.
 * Utiliza una colección genérica para trabajar con cualquier clase que implemente Registrable.
 */
public class GestorEntidades {

    private ArrayList<Registrable> entidades;

    /**
     * Constructor
     */
    public GestorEntidades() {
        entidades = new ArrayList<>();
    }

    /**
     * Agrega una entidad a la colección
     *
     * @param entidad entidad a agregar
     */
    public void agregarEntidad(Registrable entidad) {
        if (entidad != null) {
            entidades.add(entidad);
        }
    }

    /**
     * Agrega múltiples entidades
     *
     * @param listaEntidades lista de entidades a agregar
     */
    public void agregarMultiples(ArrayList<? extends Registrable> listaEntidades) {
        if (listaEntidades != null) {
            entidades.addAll(listaEntidades);
        }
    }

    /**
     * Obtiene la cantidad total de entidades
     *
     * @return cantidad de entidades
     */
    public int cantidadEntidades() {
        return entidades.size();
    }

    /**
     * Muestra resumen de todas las entidades con lógica diferenciada por tipo
     */
    public void mostrarResumenGeneral() {
        if (entidades.isEmpty()) {
            System.out.println("No hay entidades registradas.");
            return;
        }

        System.out.println("\n====== RESUMEN DE ENTIDADES ======");

        int tourCount = 0;
        int personaCount = 0;
        int servicioCount = 0;

        for (Registrable entidad : entidades) {
            mostrarInfoEntidad(entidad);

            if (entidad instanceof Tour) {
                tourCount++;
            } else if (entidad instanceof Persona) {
                personaCount++;
            } else if (entidad instanceof ServicioTuristico) {
                servicioCount++;
            }
        }

        System.out.println("\n====== ESTADÍSTICAS ======");
        System.out.println("Total de tours: " + tourCount);
        System.out.println("Total de personas: " + personaCount);
        System.out.println("Total de servicios: " + servicioCount);
        System.out.println("Total de entidades: " + entidades.size());
        System.out.println("==============================\n");
    }

    /**
     * Muestra la información de una entidad con lógica diferenciada según su tipo
     */
    private void mostrarInfoEntidad(Registrable entidad) {
        if (entidad instanceof Tour tour) {
            System.out.println("[TOUR] " + entidad.mostrarResumen());

        } else if (entidad instanceof Cliente cliente) {
            System.out.println("[CLIENTE] " + entidad.mostrarResumen());

        } else if (entidad instanceof GuiaTuristico guia) {
            System.out.println("[GUÍA] " + entidad.mostrarResumen());

        } else if (entidad instanceof Persona persona) {
            System.out.println("[PERSONA] " + entidad.mostrarResumen());

        } else if (entidad instanceof PaseoLacustre paseo) {
            System.out.println("[PASEO LACUSTRE] " + entidad.mostrarResumen());

        } else if (entidad instanceof ExcursionCultural excursion) {
            System.out.println("[EXCURSIÓN CULTURAL] " + entidad.mostrarResumen());

        } else if (entidad instanceof RutaGastronomica ruta) {
            System.out.println("[RUTA GASTRONÓMICA] " + entidad.mostrarResumen());

        } else if (entidad instanceof ServicioTuristico servicio) {
            System.out.println("[SERVICIO] " + entidad.mostrarResumen());

        } else {
            System.out.println("[ENTIDAD] " + entidad.mostrarResumen());
        }
    }

    /**
     * Filtra y muestra solo los tours
     */
    public void mostrarSoloTours() {
        System.out.println("\n====== TOURS ======");
        int count = 0;

        for (Registrable entidad : entidades) {
            if (entidad instanceof Tour tour) {
                System.out.println(++count + ". " + entidad.mostrarResumen());
            }
        }

        if (count == 0) {
            System.out.println("No hay tours registrados.");
        }
    }

    /**
     * Filtra y muestra solo las personas
     */
    public void mostrarSoloPersonas() {
        System.out.println("\n====== PERSONAS ======");
        int count = 0;

        for (Registrable entidad : entidades) {
            if (entidad instanceof Persona persona) {
                System.out.println(++count + ". " + entidad.mostrarResumen());
            }
        }

        if (count == 0) {
            System.out.println("No hay personas registradas.");
        }
    }

    /**
     * Filtra y muestra solo los servicios
     */
    public void mostrarSoloServicios() {
        System.out.println("\n====== SERVICIOS TURÍSTICOS ======");
        int count = 0;

        for (Registrable entidad : entidades) {
            if (entidad instanceof ServicioTuristico servicio) {
                System.out.println(++count + ". " + entidad.mostrarResumen());
            }
        }

        if (count == 0) {
            System.out.println("No hay servicios registrados.");
        }
    }

    /**
     * Obtiene la lista de todas las entidades
     *
     * @return lista de entidades
     */
    public ArrayList<Registrable> getEntidades() {
        return new ArrayList<>(entidades);
    }

    /**
     * Limpia todas las entidades
     */
    public void limpiar() {
        entidades.clear();
    }
}
