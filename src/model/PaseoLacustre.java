package model;

public class PaseoLacustre extends ServicioTuristico {
    private String tipoEmbarcacion;

    public PaseoLacustre() {
        super();
    }

    public PaseoLacustre(String nombre, int duracionHoras, String tipoEmbarcacion) {
        super(nombre, duracionHoras);
        this.tipoEmbarcacion = tipoEmbarcacion;
    }

    public String getTipoEmbarcacion() {
        return tipoEmbarcacion;
    }

    public void setTipoEmbarcacion(String tipoEmbarcacion) {
        this.tipoEmbarcacion = tipoEmbarcacion;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("=== PASEO LACUSTRE ===");
        System.out.println("Nombre: " + getNombre());
        System.out.println("Duración: " + getDuracionHoras() + " horas");
        System.out.println("Tipo de embarcación: " + tipoEmbarcacion);
    }

    @Override
    public String toString() {
        return "Paseo Lacustre{" +
                super.toString() +
                ", Tipo de Embarcacion='" + tipoEmbarcacion + '\'' +
                '}';
    }

    @Override
    public String mostrarResumen() {
        return super.mostrarResumen() + " | Embarcación: " + tipoEmbarcacion;
    }
}
