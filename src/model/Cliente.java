package model;

import util.ValidacionException;
import util.Validador;

/**
 * Representa a un cliente de la agencia
 */
public class Cliente extends Persona{

    //atributo privado (propio del cliente)
    private Tour tourReservado;

    /**
     * Constructor vacio
     */
    public Cliente() {

    }

    /**
     * Constructor con parametros
     *
     * @param nombre nombre del cliente
     * @param rut rut del cliente
     * @param direccion direccion del cliente
     * @param telefono telefono del cliente
     * @param tourReservado tour reservado por el cliente
     * @throws ValidacionException si algun dato es invalido
     */
    public Cliente(String nombre, String rut, Direccion direccion,
                   String telefono, Tour tourReservado)
            throws ValidacionException {

        super(nombre, rut, direccion, telefono);

        if (tourReservado == null) {
            throw new ValidacionException(
                    "Debe seleccionar un tour."
            );
        }

        this.tourReservado = tourReservado;
    }

    /**
     * Obtiene el tour reservado por el cliente
     *
     * @return tour reservado
     */
    public Tour getTourReservado() {
        return tourReservado;
    }

    /**
     * Modifica el tour reservado por el cliente
     *
     * @param tourReservado nuevo tour reservado
     */
    public void setTourReservado(Tour tourReservado)
            throws ValidacionException {

        if (tourReservado == null) {
            throw new ValidacionException(
                    "Debe seleccionar un tour."
            );
        }

        this.tourReservado = tourReservado;
    }

    /**
     * Devuelve la informacion del cliente y el tour reservado en formato de texto legible
     *
     * @return informcion del cliente y el tour
     */
    @Override
    public String toString() {
        return "Cliente{" +
                super.toString() +
                ", tourReservado:'" + tourReservado.getNombre() + '\'' +
                '}';
    }
}