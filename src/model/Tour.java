package model;

import util.ValidacionException;
import util.Validador;

/**
 * Representa un tour turístico disponible
 * para reserva dentro del sistema.
 */
public class Tour implements Registrable {

    private String nombre;
    private String lugar;
    private int precio;
    private ServicioTuristico servicio;

    /**
     * Constructor vacío.
     */
    public Tour() {

    }

    /**
     * Constructor con parámetros.
     *
     * @param nombre nombre del tour
     * @param lugar lugar donde se realiza
     * @param precio precio del tour
     * @throws ValidacionException si los datos son inválidos
     */
    public Tour(String nombre,String lugar, int precio)
            throws ValidacionException {

        this(nombre, lugar, precio, null);
    }

    /**
     * Constructor con parámetros incluyendo servicio.
     *
     * @param nombre nombre del tour
     * @param lugar lugar donde se realiza
     * @param precio precio del tour
     * @param servicio servicio turístico asociado
     * @throws ValidacionException si los datos son inválidos
     */
    public Tour(String nombre,
                String lugar,
                int precio,
                ServicioTuristico servicio)
            throws ValidacionException {

        Validador.validarTextoVacio(nombre, "nombre");
        Validador.validarTextoVacio(lugar, "lugar");
        Validador.validarNumeroPositivo(precio, "precio");

        this.nombre = nombre;
        this.lugar = lugar;
        this.precio = precio;
        this.servicio = servicio;
    }

    /**
     * Obtiene el nombre del tour.
     *
     * @return nombre del tour
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del tour.
     *
     * @param nombre nuevo nombre
     * @throws ValidacionException si es vacío
     */
    public void setNombre(String nombre)
            throws ValidacionException {

        Validador.validarTextoVacio(nombre, "nombre");

        this.nombre = nombre;
    }

    /**
     * Obtiene el lugar donde se realiza el tour.
     *
     * @return lugar
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * Modifica el lugar del tour.
     *
     * @param lugar nuevo lugar
     * @throws ValidacionException si es vacío
     */
    public void setLugar(String lugar)
            throws ValidacionException {

        Validador.validarTextoVacio(lugar, "lugar");

        this.lugar = lugar;
    }

    /**
     * Obtiene el precio del tour.
     *
     * @return precio
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * Modifica el precio del tour.
     *
     * @param precio nuevo precio
     * @throws ValidacionException si es inválido
     */
    public void setPrecio(int precio)
            throws ValidacionException {

        Validador.validarNumeroPositivo(precio, "precio");

        this.precio = precio;
    }

    /**
     * Obtiene el servicio turístico asociado al tour.
     *
     * @return servicio turístico
     */
    public ServicioTuristico getServicio() {
        return servicio;
    }

    /**
     * Modifica el servicio turístico del tour.
     *
     * @param servicio nuevo servicio turístico
     */
    public void setServicio(ServicioTuristico servicio) {
        this.servicio = servicio;
    }

    /**
     * Devuelve la información del tour
     * en formato legible.
     *
     * @return información del tour
     */

    @Override
    public String mostrarResumen() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("Tour: ").append(nombre)
               .append(" | Lugar: ").append(lugar)
               .append(" | Precio: $").append(precio);
        if (servicio != null) {
            resumen.append(" | Servicio: ").append(servicio.getNombre());
        }
        return resumen.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombre).append(" - ").append(lugar).append(" - $").append(precio);
        if (servicio != null) {
            sb.append(" (").append(servicio.getNombre()).append(")");
        }
        return sb.toString();
    }
}