package model;

public abstract class ServicioTuristico implements Registrable {

    private String nombre;
    private int duracionHoras;

    public ServicioTuristico() {
    }

    public ServicioTuristico(String nombre, int duracionHoras) {
        this.nombre = nombre;
        this.duracionHoras = duracionHoras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(int duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public abstract void mostrarInformacion();

    @Override
    public String mostrarResumen() {
        return "Servicio: " + nombre + " | Duración: " + duracionHoras + " horas";
    }

    @Override
    public String toString() {
        return "Servicio Turistico{" +
                "nombre='" + nombre + '\'' +
                ", Duracion Horas=" + duracionHoras +
                '}';
    }
}
