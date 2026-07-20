package service;

import model.Cliente;
import model.Persona;
import model.ServicioTuristico;
import model.Tour;
import util.ValidacionException;

import java.io.*;
import java.util.ArrayList;

/**
 * Servicio encargado de administrar los tours
 */
public class TourService {

    private ArrayList<Tour> tours;
    private ServicioService servicioService;
    private final String ARCHIVO = "resources/tours.txt";

    /**
     * Constructor.
     *
     * @param servicioService servicio encargado de administrar
     *                        los servicios turísticos.
     */
    public TourService(ServicioService servicioService) {
        this.servicioService = servicioService;
        tours = new ArrayList<>();
    }

    /**
     * Obtiene la lista de tours
     *
     * @return lista de tours
     */
    public ArrayList<Tour> getTours() {

        return tours;
    }

    /**
     * Busca un tour por nombre.
     *
     * @param nombre nombre del tour
     * @return tour encontrado o null
     */
    public Tour buscarPorNombre(String nombre) {

        int indice = buscarIndiceTour(nombre);

        if (indice == -1) {
            return null;
        }

        return tours.get(indice);
    }

    /**
     * Verifica si ya existe un tour con ese nombre
     *
     * @param nombre nombre del tour
     * @return true si existe
     */
    private boolean existeTour(String nombre) {
        return buscarPorNombre(nombre) != null;
    }

    /**
     * Busca la posición de un tour según su nombre.
     *
     * @param nombre nombre del tour
     * @return índice del tour o -1 si no existe
     */
    private int buscarIndiceTour(String nombre) {

        for (int i = 0; i < tours.size(); i++) {

            if (tours.get(i).getNombre().equalsIgnoreCase(nombre)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Agrega un nuevo tour
     *
     * @param tour tour a agregar
     * @return true si fue agregado
     */
    public boolean agregarTour(Tour tour) {

        if (existeTour(tour.getNombre())) {
            return false;
        }

        tours.add(tour);
        guardarTours();
        return true;
    }

    /**
     * Modifica un tour existente.
     *
     * @param nombreOriginal nombre actual del tour
     * @param nuevoNombre nuevo nombre
     * @param nuevoLugar nuevo lugar
     * @param nuevoPrecio nuevo precio
     * @param nuevoServicio nuevo servicio turístico (puede ser null)
     * @return true si fue modificado correctamente
     */
    public boolean modificarTour(
            String nombreOriginal,
            String nuevoNombre,
            String nuevoLugar,
            int nuevoPrecio,
            ServicioTuristico nuevoServicio) {

        Tour tour = buscarPorNombre(nombreOriginal);

        if (tour == null) {
            return false;
        }

        // Si cambia el nombre, verificar que no exista otro igual
        if (!nombreOriginal.equalsIgnoreCase(nuevoNombre)
                && existeTour(nuevoNombre)) {
            return false;
        }

        try {

            tour.setNombre(nuevoNombre);
            tour.setLugar(nuevoLugar);
            tour.setPrecio(nuevoPrecio);
            tour.setServicio(nuevoServicio);

            guardarTours();

            return true;

        } catch (ValidacionException e) {

            System.out.println(
                    "Error al modificar tour: "
                            + e.getMessage()
            );

            return false;
        }
    }

    /**
     * Elimina un tour siempre que no esté asociado
     * a ningún cliente.
     *
     * @param nombre nombre del tour
     * @param personaService servicio de personas
     * @return true si fue eliminado
     */
    public boolean eliminarTour(
            String nombre,
            PersonaService personaService) {

        Tour tour = buscarPorNombre(nombre);

        if (tour == null) {
            return false;
        }

        for (Persona persona : personaService.getPersonas()) {

            if (persona instanceof Cliente cliente) {

                if (cliente.getTourReservado().equals(tour)) {
                    return false;
                }
            }
        }

        tours.remove(tour);
        guardarTours();

        return true;
    }

    /**
     * Busca tours por lugar.
     *
     * @param lugar lugar a buscar
     * @return lista de tours encontrados
     */
    public ArrayList<Tour> buscarPorLugar(String lugar) {

        ArrayList<Tour> resultado = new ArrayList<>();

        for (Tour tour : tours) {

            if (tour.getLugar().toLowerCase().contains(lugar.toLowerCase())) {
                resultado.add(tour);
            }
        }

        return resultado;
    }

    /**
     * Muestra todos los tours registrados.
     */
    public void mostrarTours() {

        if (tours.isEmpty()) {

            System.out.println("No existen tours registrados.");
            return;
        }

        for (Tour tour : tours) {
            System.out.println(tour);
        }
    }

    /**
     * Muestra los tours numerados.
     * Útil para selección desde menú.
     */
    public void mostrarToursNumerados() {

        if (tours.isEmpty()) {

            System.out.println("No existen tours registrados.");
            return;
        }

        for (int i = 0; i < tours.size(); i++) {

            System.out.println(
                    (i + 1) + ". " + tours.get(i)
            );
        }
    }

    /**
     * Obtiene un tour según su posición en la lista.
     *
     * @param indice posición del tour
     * @return tour encontrado o null
     */
    public Tour obtenerTour(int indice) {

        if (indice < 0 || indice >= tours.size()) {
            return null;
        }

        return tours.get(indice);
    }

    /**
     * Carga los tours desde el archivo y vuelve a asociar
     * el servicio turístico correspondiente, si existe.
     */
    public void cargarTours() {

        tours.clear();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(ARCHIVO))) {

            String linea;

            while ((linea = reader.readLine()) != null) {

                String[] datos = linea.split(";");

                if (datos.length < 3) {
                    continue;
                }

                String nombre = datos[0];
                String lugar = datos[1];
                int precio = Integer.parseInt(datos[2]);

                ServicioTuristico servicio = null;

                if (datos.length > 3) {

                    String nombreServicio = datos[3];

                    if (!nombreServicio.equalsIgnoreCase("Ninguno")) {
                        servicio = servicioService.buscarPorNombre(nombreServicio);
                    }
                }

                Tour tour = new Tour(
                        nombre,
                        lugar,
                        precio,
                        servicio
                );

                tours.add(tour);
            }

        } catch (IOException | ValidacionException e) {

            System.out.println(
                    "Error al cargar tours: "
                            + e.getMessage()
            );
        }
    }

    /**
     * Guarda los tours en el archivo.
     */
    public void guardarTours() {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(ARCHIVO))) {

            for (Tour tour : tours) {

                String linea = tour.getNombre() + ";" +
                        tour.getLugar() + ";" +
                        tour.getPrecio();

                if (tour.getServicio() != null) {
                    linea += ";" + tour.getServicio().getNombre();
                }

                writer.write(linea);

                writer.newLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error al guardar tours: "
                            + e.getMessage()
            );
        }
    }

    public ArrayList<Tour> buscarDesdePrecio(int precio) {

        ArrayList<Tour> resultado = new ArrayList<>();

        for (Tour tour : tours) {

            if (tour.getPrecio() >= precio) {
                resultado.add(tour);
            }
        }

        return resultado;
    }

    public ArrayList<Tour> buscarHastaPrecio(int precio) {

        ArrayList<Tour> resultado = new ArrayList<>();

        for (Tour tour : tours) {

            if (tour.getPrecio() <= precio) {
                resultado.add(tour);
            }
        }

        return resultado;
    }

    public boolean hayTours() {
        return !tours.isEmpty();
    }

    public int cantidadTours() {
        return tours.size();
    }
}