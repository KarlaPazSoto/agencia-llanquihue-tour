package model;

import util.ValidacionException;
import util.Validador;

/**
 * Representa a una persona dentro del sistema de la agencia de turismo
 */
public class Persona {

    //atributos privados
    private String nombre;
    private String rut;
    private Direccion direccion;
    private String telefono;

    /**
     * Constructor vacio
     */
    public Persona() {

    }

    /**
     * Constructor con parametros
     *
     * @param nombre nombre de la persona
     * @param rut rut de la persona
     * @param direccion direccion de la persona
     * @param telefono teléfono de la persona
     * @throws ValidacionException si los datos son invalidos
     */
    public Persona(String nombre, String rut, Direccion direccion, String telefono)
            throws ValidacionException {

        Validador.validarTextoVacio(nombre, "nombre");
        Validador.validarRut(rut);
        Validador.validarDireccion(direccion);
        Validador.validarTelefono(telefono);

        this.nombre = nombre;
        this.rut = rut;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    /**
     * Obtiene el nombre de la persona
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre de la persona
     *
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) throws ValidacionException {

        Validador.validarTextoVacio(nombre, "nombre");

        this.nombre = nombre;
    }

    /**
     * Obtiene el rut de la persona
     *
     * @return rut
     */
    public String getRut() {
        return rut;
    }

    /**
     * Modifica el rut de la persona
     *
     * @param rut nuevo rut
     */
    public void setRut(String rut) throws ValidacionException {

        Validador.validarRut(rut);

        this.rut = rut;
    }

    /**
     * Obtiene la direccion de la persona
     *
     * @return direccion
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * Modifica la direccion de la persona
     *
     * @param direccion nueva direccion
     */
    public void setDireccion(Direccion direccion)
            throws ValidacionException {

        Validador.validarDireccion(direccion);

        this.direccion = direccion;
    }

    /**
     * Obtiene el telefono de la persona
     *
     * @return telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Modifica el telefono de la persona
     *
     * @param telefono nuevo telefono
     */
    public void setTelefono(String telefono)
            throws ValidacionException {

        Validador.validarTelefono(telefono);

        this.telefono = telefono;
    }

    /**
     * Devuelve la informacion de la persona en formato de texto legible
     *
     * @return informacion de la persona
     */
    @Override
    public String toString() {
        return  "nombre:'" + nombre + '\'' +
                ", rut:'" + rut + '\'' +
                ", direccion: " + direccion + '\'' +
                ", telefono: " + telefono;
    }
}