package model;

import util.ValidacionException;
import util.Validador;

public class Tour {
    private String nombre;
    private String lugar;
    private int precio;

    public Tour() {

    }

    public Tour(String nombre, String lugar, int precio)
            throws ValidacionException {

        Validador.validarTextoVacio(nombre, "nombre");
        Validador.validarTextoVacio(lugar, "lugar");
        Validador.validarNumeroPositivo(precio, "precio");

        this.nombre = nombre;
        this.lugar = lugar;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre)
            throws ValidacionException {

        Validador.validarTextoVacio(nombre, "nombre");

        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar)
            throws ValidacionException {

        Validador.validarTextoVacio(lugar, "lugar");

        this.lugar = lugar;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio)
            throws ValidacionException {

        Validador.validarNumeroPositivo(precio, "precio");

        this.precio = precio;
    }

    @Override
    public String toString() {
        return nombre + " - " + lugar + " - $" + precio;
    }
}