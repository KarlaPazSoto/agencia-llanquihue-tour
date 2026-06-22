package model;

import util.ValidacionException;
import util.Validador;

public class Direccion {

        //atribuos privados
        private String calle;
        private int numero;
        private String comuna;

        /**
         * Constructor vacio
         */
        public Direccion() {

        }

        /**
         * Construtor con parámetros
         * @param calle nombre de la calle
         * @param numero numero de la direccion
         * @param comuna comuna de residencia
         * @throws ValidacionException si algun dato es invalido
         */
        public Direccion(String calle, int numero, String comuna) throws ValidacionException  {

            Validador.validarTextoVacio(calle, "calle");
            Validador.validarNumeroPositivo(numero, "numero");
            Validador.validarTextoVacio(comuna, "comuna");

            this.calle = calle;
            this.numero  = numero;
            this.comuna = comuna;
        }

        /**
         * Obtiene la calle
         *
         * @return calle
         */
        public String getCalle() {
            return calle;
        }

        /**
         * Modifica la calle
         *
         * @param calle nueva calle
         */
        public void setCalle(String calle) {
            this.calle = calle;
        }

        /**
         * Obtiene el numero de la direccion
         *
         * @return numero
         */
        public int getNumero() {
            return numero;
        }

        /**
         * Modifica el numero
         *
         * @param numero nuevo numero
         */
        public void setNumero(int numero) {
            this.numero = numero;
        }

        /**
         * Obtiene la comuna
         *
         * @return comuna
         */
        public String getComuna() {
            return comuna;
        }

        /**
         * Modifica la comuna
         *
         * @param comuna nueva comuna
         */
        public void setComuna(String comuna) {
            this.comuna = comuna;
        }

        /**
         * Devueve la direccion en formato de texto legible
         *
         * @return informacion de la direccion
         */
        @Override
        public String toString() {
            return  "calle:'" + calle + '\'' +
                    ", numero:" + numero +
                    ", comuna:'" + comuna + '\'';
        }
}