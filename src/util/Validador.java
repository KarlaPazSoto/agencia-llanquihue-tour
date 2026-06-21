package util;

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
    }
}