package model;

import utils.ValidacionException;
import utils.Validador;

/**
 * Representa a una persona dentro del sistema de la agencia de turismo
 */
public class Persona {

    //atributos privados
    private String nombre;
    private String rut;
    private Direccion direccion;

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
     * @throws ValidacionException si los datos son invalidos
     */
    public Persona(String nombre, String rut, Direccion direccion) throws ValidacionException {

        Validador.validarTextoVacio(nombre, "nombre");
        Validador.validarRut(rut);

        this.nombre = nombre;
        this.rut = rut;
        this.direccion = direccion;
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
    public void setNombre(String nombre) {
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
    public void setRut(String rut) {
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
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
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
                ", direccion: " + direccion;
    }
}