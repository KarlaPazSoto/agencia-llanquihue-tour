package model;

/**
 * Interfaz que define el contrato común para todas las entidades gestionables del sistema.
 */
public interface Registrable {

    /**
     * Devuelve un resumen de la entidad con su información principal.
     *
     * @return resumen de la entidad
     */
    String mostrarResumen();
}
