package model;

public class ExcursionCultural extends ServicioTuristico {
    private String lugarHistorico;

    public ExcursionCultural() {
        super();
    }

    public ExcursionCultural(String nombre, int duracionHoras, String lugarHistorico) {
        super(nombre, duracionHoras);
        this.lugarHistorico = lugarHistorico;
    }

    public String getLugarHistorico() {
        return lugarHistorico;
    }

    public void setLugarHistorico(String lugarHistorico) {
        this.lugarHistorico = lugarHistorico;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("=== EXCURSIÓN CULTURAL ===");
        System.out.println("Nombre: " + getNombre());
        System.out.println("Duración: " + getDuracionHoras() + " horas");
        System.out.println("Lugar histórico: " + lugarHistorico);
    }

    @Override
    public String toString() {
        return "Excursion Cultural{" +
                super.toString() +
                ", Lugar Historico='" + lugarHistorico + '\'' +
                '}';
    }

    @Override
    public String mostrarResumen() {
        return super.mostrarResumen() + " | Lugar: " + lugarHistorico;
    }

}
