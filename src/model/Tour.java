package model;

public class Tour {
    private String nombre;
    private String lugar;
    private int precio;

    public Tour() {

    }

    public Tour(String nombre, String lugar, int precio) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String tipo) {
        this.lugar = lugar;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return nombre + " - " + lugar + " - $" + precio;
    }
}