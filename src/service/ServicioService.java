package service;

import model.ServicioTuristico;
import model.PaseoLacustre;
import model.ExcursionCultural;
import model.RutaGastronomica;
import java.io.*;
import java.util.ArrayList;

/**
 * Servicio encargado de administrar los servicios turísticos
 */
public class ServicioService {

    private ArrayList<ServicioTuristico> servicios;
    private final String ARCHIVO = "resources/servicios.txt";

    /**
     * Constructor
     */
    public ServicioService() {
        servicios = new ArrayList<>();
    }

    /**
     * Obtiene la lista de servicios
     *
     * @return lista de servicios
     */
    public ArrayList<ServicioTuristico> getServicios() {
        return servicios;
    }

    /**
     * Busca un servicio por nombre
     */
    public ServicioTuristico buscarPorNombre(String nombre) {
        for (ServicioTuristico s : servicios) {
            if (s.getNombre().equalsIgnoreCase(nombre)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Verifica si existe un servicio
     */
    private boolean existeServicio(String nombre) {
        return buscarPorNombre(nombre) != null;
    }

    /**
     * Agrega un nuevo servicio
     */
    public boolean agregarServicio(ServicioTuristico servicio) {
        if (servicio == null || existeServicio(servicio.getNombre())) {
            return false;
        }
        servicios.add(servicio);
        guardarServicios();
        return true;
    }

    /**
     * Muestra todos los servicios registrados
     */
    public void mostrarServicios() {
        if (servicios.isEmpty()) {
            System.out.println("No existen servicios turísticos registrados.");
            return;
        }

        for (ServicioTuristico servicio : servicios) {
            servicio.mostrarInformacion();
            System.out.println();
        }
    }

    /**
     * Muestra los servicios numerados
     */
    public void mostrarServiciosNumerados() {
        if (servicios.isEmpty()) {
            System.out.println("No existen servicios turísticos registrados.");
            return;
        }

        for (int i = 0; i < servicios.size(); i++) {
            System.out.println((i + 1) + ". " + servicios.get(i).getNombre());
        }
    }

    /**
     * Obtiene un servicio por índice
     */
    public ServicioTuristico obtenerServicio(int indice) {
        if (indice < 0 || indice >= servicios.size()) {
            return null;
        }
        return servicios.get(indice);
    }

    /**
     * Carga los servicios desde el archivo
     */
    public void cargarServicios() {
        servicios.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(";");

                if (datos.length < 3) {
                    continue;
                }

                String tipo = datos[0];
                String nombre = datos[1];
                int duracion = Integer.parseInt(datos[2]);

                ServicioTuristico servicio = null;

                if (tipo.equalsIgnoreCase("PASEO_LACUSTRE")) {
                    String embarcacion = datos.length > 3 ? datos[3] : "Bote";
                    servicio = new PaseoLacustre(nombre, duracion, embarcacion);

                } else if (tipo.equalsIgnoreCase("EXCURSION_CULTURAL")) {
                    String lugar = datos.length > 3 ? datos[3] : "Lugar desconocido";
                    servicio = new ExcursionCultural(nombre, duracion, lugar);

                } else if (tipo.equalsIgnoreCase("RUTA_GASTRONOMICA")) {
                    int paradas = datos.length > 3 ? Integer.parseInt(datos[3]) : 3;
                    servicio = new RutaGastronomica(nombre, duracion, paradas);
                }

                if (servicio != null) {
                    servicios.add(servicio);
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar servicios: " + e.getMessage());
        }
    }

    /**
     * Guarda los servicios en el archivo
     */
    public void guardarServicios() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (ServicioTuristico servicio : servicios) {
                String linea = generarLinaServicio(servicio);
                writer.write(linea);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar servicios: " + e.getMessage());
        }
    }

    /**
     * Genera una línea de servicio para persistencia
     */
    private String generarLinaServicio(ServicioTuristico servicio) {
        StringBuilder sb = new StringBuilder();

        if (servicio instanceof PaseoLacustre paseo) {
            sb.append("PASEO_LACUSTRE;")
                    .append(paseo.getNombre()).append(";")
                    .append(paseo.getDuracionHoras()).append(";")
                    .append(paseo.getTipoEmbarcacion());

        } else if (servicio instanceof ExcursionCultural excursion) {
            sb.append("EXCURSION_CULTURAL;")
                    .append(excursion.getNombre()).append(";")
                    .append(excursion.getDuracionHoras()).append(";")
                    .append(excursion.getLugarHistorico());

        } else if (servicio instanceof RutaGastronomica ruta) {
            sb.append("RUTA_GASTRONOMICA;")
                    .append(ruta.getNombre()).append(";")
                    .append(ruta.getDuracionHoras()).append(";")
                    .append(ruta.getNumeroParadas());
        }

        return sb.toString();
    }

    /**
     * Cantidad de servicios
     */
    public int cantidadServicios() {
        return servicios.size();
    }

    /**
     * Verifica si hay servicios
     */
    public boolean hayServicios() {
        return !servicios.isEmpty();
    }
}
