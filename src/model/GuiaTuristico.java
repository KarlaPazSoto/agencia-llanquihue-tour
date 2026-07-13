package model;

import util.ValidacionException;
import util.Validador;

/**
 * Representa a un guia turistico de la agencia
 */
public class GuiaTuristico extends Persona{
    //atributo privado propio del guia turistico
    private String idioma;

    /**
     * Constructor vacio
     */
    public GuiaTuristico(){

    }

    /**
     * Constructor con parametros
     *
     * @param nombre nombre del guia turistico
     * @param rut rut del guia turistico
     * @param direccion direccion del guia turistico
     * @param telefono telefono del guia turistico
     * @param idioma del guia turistico
     * @throws ValidacionException si algun dato es invalido
     */
    public GuiaTuristico(String nombre, String rut, Direccion direccion, String telefono, String idioma) throws ValidacionException {
        super(nombre, rut, direccion, telefono);

        Validador.validarTextoVacio(idioma, "idioma");

        this.idioma = idioma;
    }

    /**
     * Obtiene el idioma que maneja el guia turistico
     *
     * @return idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Modifica el idioma que maneja el guia turistico
     *
     * @param idioma nuevo idioma
     */
    public void setIdioma(String idioma)
            throws ValidacionException {

        Validador.validarTextoVacio(
                idioma,
                "idioma"
        );

        this.idioma = idioma;
    }

    /**
     * Devuelve la informacion del guia turistico en formato de texto legible
     *
     * @return informacion de guia turistico
     */
    @Override
    public String toString() {
        return "Guia Turistico{" +
                super.toString() +
                ", idioma:'" + idioma + '\'' +
                '}';
    }

    @Override
    public String mostrarResumen() {
        return "Guía: " + getNombre() + " | RUT: " + getRut() +
               " | Idioma: " + idioma + " | Teléfono: " + getTelefono();
    }
}