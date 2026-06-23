package service;

import model.Tour;
import util.ValidacionException;

import java.io.*;
import java.util.ArrayList;

/**
 * Servicio encargado de administrar los tours.
 */
public class TourService {

    private ArrayList<Tour> tours;
    private final String ARCHIVO = "resources/tours.txt";

    /**
     * Constructor.
     */
    public TourService() {
        tours = new ArrayList<>();
    }

    /**
     * Obtiene la lista de tours.
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

        for (Tour tour : tours) {

            if (tour.getNombre().equalsIgnoreCase(nombre)) {
                return tour;
            }
        }

        return null;
    }

    /**
     * Verifica si ya existe un tour con ese nombre.
     *
     * @param nombre nombre del tour
     * @return true si existe
     */
    private boolean existeTour(String nombre) {
        return buscarPorNombre(nombre) != null;
    }

    /**
     * Agrega un nuevo tour.
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

    public void cargarTours() {

        tours.clear();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(ARCHIVO))) {

            String linea;

            while ((linea = reader.readLine()) != null) {

                String[] datos = linea.split(";");

                if (datos.length != 3) {
                    continue;
                }

                String nombre = datos[0];
                String lugar = datos[1];
                int precio = Integer.parseInt(datos[2]);

                Tour tour = new Tour(
                        nombre,
                        lugar,
                        precio
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

    public void guardarTours() {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(ARCHIVO))) {

            for (Tour tour : tours) {

                writer.write(
                        tour.getNombre() + ";" +
                                tour.getLugar() + ";" +
                                tour.getPrecio()
                );

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