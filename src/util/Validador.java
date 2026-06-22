package util;

import model.Direccion;

public class Validador {

    public static void validarTextoVacio(String texto, String nombreCampo) throws ValidacionException {

        if (texto == null || texto.trim().isEmpty()) {
            throw new ValidacionException(
                    "El campo " + nombreCampo + " no puede estar vacío."
            );
        }
    }

    public static void validarNumeroPositivo(int numero, String nombreCampo) throws ValidacionException {

        if (numero <= 0) {
            throw new ValidacionException(
                    "El campo " + nombreCampo + " debe ser mayor que cero."
            );
        }
    }

    public static void validarRut(String rut) throws ValidacionException {

        validarTextoVacio(rut, "RUT");

        String patron = "^\\d{7,8}-[0-9kK]$";

        if (!rut.matches(patron)) {
            throw new ValidacionException(
                    "El formato del RUT es inválido."
            );
        }

        if (!esRutValido(rut)) {
            throw new ValidacionException(
                    "El dígito verificador del RUT es incorrecto."
            );
        }
    }

    private static boolean esRutValido(String rut) {

        String[] partes = rut.split("-");

        String cuerpoRut = partes[0];
        char dvIngresado = Character.toUpperCase(partes[1].charAt(0));

        char dvCalculado = calcularDigitoVerificador(cuerpoRut);

        return dvIngresado == dvCalculado;
    }

    private static char calcularDigitoVerificador(String cuerpoRut) {

        int suma = 0;
        int multiplicador = 2;

        for (int i = cuerpoRut.length() - 1; i >= 0; i--) {

            suma += Character.getNumericValue(cuerpoRut.charAt(i))
                    * multiplicador;

            multiplicador++;

            if (multiplicador > 7) {
                multiplicador = 2;
            }
        }

        int resto = 11 - (suma % 11);

        if (resto == 11) {
            return '0';
        }

        if (resto == 10) {
            return 'K';
        }

        return Character.forDigit(resto, 10);
    }

    public static void validarDireccion(Direccion direccion)
            throws ValidacionException {

        if (direccion == null) {
            throw new ValidacionException(
                    "La dirección no puede ser nula."
            );
        }
    }


}