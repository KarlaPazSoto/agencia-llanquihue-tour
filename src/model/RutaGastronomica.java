package model;

public class RutaGastronomica extends ServicioTuristico {
    private int numeroParadas;

    public RutaGastronomica() {
        super();
    }

    public RutaGastronomica(String nombre, int duracionHoras, int numeroParadas) {
        super(nombre, duracionHoras);
        this.numeroParadas = numeroParadas;
    }

    public int getNumeroParadas() {
        return numeroParadas;
    }

    public void setNumeroParadas(int numeroParadas) {
        this.numeroParadas = numeroParadas;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("=== RUTA GASTRONÓMICA ===");
        System.out.println("Nombre: " + getNombre());
        System.out.println("Duración: " + getDuracionHoras() + " horas");
        System.out.println("Número de paradas: " + numeroParadas);
    }

    @Override
    public String toString() {
        return "Ruta Gastronomica{" +
                super.toString() +
                ", Número de Paradas=" + numeroParadas +
                '}';
    }

    @Override
    public String mostrarResumen() {
        return super.mostrarResumen() + " | Paradas: " + numeroParadas;
    }
}
