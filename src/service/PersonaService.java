package service;

import model.Cliente;
import model.Direccion;
import model.GuiaTuristico;
import model.Persona;
import model.Tour;
import util.ValidacionException;


import java.io.*;
import java.util.ArrayList;

/**
 * Servicio encargado de administrar las personas
 */
public class PersonaService {

    private ArrayList<Persona> personas;
    private final String ARCHIVO = "resources/personas.txt";

    public PersonaService() {
        personas = new ArrayList<>();
    }

    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    public Persona buscarPorRut(String rut) {

        for (Persona persona : personas) {

            if (persona.getRut().equalsIgnoreCase(rut)) {
                return persona;
            }
        }

        return null;
    }

    private boolean existeRut(String rut) {
        return buscarPorRut(rut) != null;
    }

    public boolean agregarPersona(Persona persona) {

        if (existeRut(persona.getRut())) {
            return false;
        }

        personas.add(persona);
        guardarPersonas();
        return true;
    }

    public void mostrarPersonas() {

        if (personas.isEmpty()) {

            System.out.println("No existen personas registradas.");
            return;
        }

        for (Persona persona : personas) {
            System.out.println(persona);
        }
    }

    public void guardarPersonas() {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(ARCHIVO))) {

            for (Persona persona : personas) {

                Direccion direccion = persona.getDireccion();

                if (persona instanceof Cliente cliente) {

                    writer.write("CLIENTE;" +
                            cliente.getNombre() + ";" +
                            cliente.getRut() + ";" +
                            direccion.getCalle() + ";" +
                            direccion.getNumero() + ";" +
                            direccion.getComuna() + ";" +
                            cliente.getTelefono() + ";" +
                            cliente.getTourReservado().getNombre()
                    );

                } else if (persona instanceof GuiaTuristico guia) {

                    writer.write("GUIA;" +
                            guia.getNombre() + ";" +
                            guia.getRut() + ";" +
                            direccion.getCalle() + ";" +
                            direccion.getNumero() + ";" +
                            direccion.getComuna() + ";" +
                            guia.getTelefono() + ";" +
                            guia.getIdioma()
                    );
                }

                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar personas: " + e.getMessage());
        }
    }

    public void cargarPersonas(TourService tourService) {

        personas.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {

            String linea;

            while ((linea = reader.readLine()) != null) {

                String[] datos = linea.split(";");

                if (datos.length != 8) {
                    continue;
                }

                String tipo = datos[0];
                String nombre = datos[1];
                String rut = datos[2];
                String calle = datos[3];
                int numero = Integer.parseInt(datos[4]);
                String comuna = datos[5];
                String telefono = datos[6];
                String datoExtra = datos[7];

                Direccion direccion = new Direccion(calle, numero, comuna);

                if (tipo.equalsIgnoreCase("CLIENTE")) {

                    Tour tour = tourService.buscarPorNombre(datoExtra);

                    if (tour != null) {

                        Cliente cliente = new Cliente(
                                nombre,
                                rut,
                                direccion,
                                telefono,
                                tour
                        );

                        personas.add(cliente);
                    }

                } else if (tipo.equalsIgnoreCase("GUIA")) {

                    GuiaTuristico guia = new GuiaTuristico(
                            nombre,
                            rut,
                            direccion,
                            telefono,
                            datoExtra
                    );

                    personas.add(guia);
                }
            }

        } catch (
                IOException |
                ValidacionException |
                NumberFormatException e
        ) {

            System.out.println("Error al cargar personas: " + e.getMessage());
        }
    }

    public boolean hayPersonas() {
        return !personas.isEmpty();
    }


    /**
     * Modifica una persona existente.
     *
     * @param rutOriginal RUT de la persona a modificar
     * @return true si fue modificada correctamente
     */

    public boolean modificarPersona(String rutOriginal, Persona nuevaPersona){

        Persona persona = buscarPorRut(rutOriginal);

        if(persona == null){
            return false;
        }


        int posicion = personas.indexOf(persona);


        personas.set(posicion, nuevaPersona);

        guardarPersonas();

        return true;

    }


    /**
     * Elimina una persona según su RUT.
     *
     * @param rut RUT de la persona
     * @return true si fue eliminada
     */
    public boolean eliminarPersona(String rut) {

        Persona persona = buscarPorRut(rut);

        if (persona == null) {
            return false;
        }

        personas.remove(persona);
        guardarPersonas();

        return true;
    }

}