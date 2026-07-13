package data;

import model.ExcursionCultural;
import model.PaseoLacustre;
import model.RutaGastronomica;
import model.ServicioTuristico;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorServicios {

    private List<ServicioTuristico> servicios;

    public GestorServicios() {
        this.servicios = new ArrayList<>();
        inicializarServicios();
    }

    private void inicializarServicios() {
        // Crear dos ExcursioneseCulturales
        servicios.add(new ExcursionCultural("Excursión a Fuerte Aconcagua", 3, "Fuerte Aconcagua"));
        servicios.add(new ExcursionCultural("Recorrido histórico por Llanguihue", 4, "Patrimonio Cultural Local"));

        // Crear dos Paseos Lacustres
        servicios.add(new PaseoLacustre("Paseo en catamarán por Lago Llanguihue", 2, "Catamarán"));
        servicios.add(new PaseoLacustre("Navegación en bote turístico", 3, "Bote Turístico"));

        // Crear dos Rutas Gastronómicas
        servicios.add(new RutaGastronomica("Ruta de los sabores locales", 5, 4));
        servicios.add(new RutaGastronomica("Tour gastronómico por restaurantes tradicionales", 6, 5));
    }

    public List<ServicioTuristico> obtenerTodosLosServicios() {
        return new ArrayList<>(servicios);
    }

    public List<ServicioTuristico> crearServicios() {
        return obtenerTodosLosServicios();
    }

    public void agregarServicio(ServicioTuristico servicio) {
        if (servicio != null) {
            servicios.add(servicio);
        }
    }

    public void eliminarServicio(String nombre) {
        servicios.removeIf(s -> s.getNombre().equals(nombre));
    }

    public ServicioTuristico buscarPorNombre(String nombre) {
        return servicios.stream()
                .filter(s -> s.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

    public List<ExcursionCultural> obtenerExcursionesculturales() {
        return servicios.stream()
                .filter(s -> s instanceof ExcursionCultural)
                .map(s -> (ExcursionCultural) s)
                .collect(Collectors.toList());
    }

    public List<PaseoLacustre> obtenerPaseosLacustres() {
        return servicios.stream()
                .filter(s -> s instanceof PaseoLacustre)
                .map(s -> (PaseoLacustre) s)
                .collect(Collectors.toList());
    }

    public List<RutaGastronomica> obtenerRutasGastronomicas() {
        return servicios.stream()
                .filter(s -> s instanceof RutaGastronomica)
                .map(s -> (RutaGastronomica) s)
                .collect(Collectors.toList());
    }

    public int obtenerTotalServicios() {
        return servicios.size();
    }

    public void mostrarTodosLosServicios() {
        System.out.println("\n=== CATÁLOGO DE SERVICIOS TURÍSTICOS ===\n");
        if (servicios.isEmpty()) {
            System.out.println("No hay servicios disponibles.");
            return;
        }

        for (ServicioTuristico servicio : servicios) {
            servicio.mostrarInformacion();
            System.out.println();
        }
        System.out.println("Total de servicios: " + obtenerTotalServicios());
    }
}
