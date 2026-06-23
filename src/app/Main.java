package app;

import model.Cliente;
import model.Direccion;
import model.GuiaTuristico;
import model.Persona;
import model.Tour;
import service.PersonaService;
import service.TourService;
import util.Validador;
import util.ValidacionException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        TourService tourService = new TourService();
        tourService.cargarTours();

        PersonaService personaService = new PersonaService();
        personaService.cargarPersonas(tourService);

        int opcion;

        do {

            mostrarMenu();
            opcion = leerEntero();

            switch (opcion) {

                case 1:
                    registrarCliente(personaService, tourService);
                    break;

                case 2:
                    registrarGuia(personaService);
                    break;

                case 3:
                    registrarTour(tourService);
                    break;

                case 4:
                    personaService.mostrarPersonas();
                    break;

                case 5:
                    tourService.mostrarTours();
                    break;

                case 6:
                    buscarPersona(personaService);
                    break;

                case 7:
                    menuFiltros(tourService);
                    break;

                case 8:
                    System.out.println("Programa finalizado.");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 8);
    }

    private static void mostrarMenu() {

        System.out.println("\n===== LLANQUIHUE TOUR APP =====");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Registrar guía turístico");
        System.out.println("3. Registrar tour");
        System.out.println("4. Ver todas las personas");
        System.out.println("5. Ver todos los tours");
        System.out.println("6. Buscar persona por RUT");
        System.out.println("7. Filtrar tours");
        System.out.println("8. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int leerEntero() {

        while (true) {

            try {
                return Integer.parseInt(scanner.nextLine());
            }

            catch (NumberFormatException e) {
                System.out.print("Debe ingresar un número: ");
            }
        }
    }

    private static void registrarCliente(
            PersonaService personaService,
            TourService tourService) {

        try {

            if (!tourService.hayTours()) {

                System.out.println(
                        "Debe registrar al menos un tour antes de crear clientes."
                );
                return;
            }

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("RUT: ");
            String rut = scanner.nextLine();

            System.out.print("Calle: ");
            String calle = scanner.nextLine();

            System.out.print("Número: ");
            int numero = leerEntero();

            System.out.print("Comuna: ");
            String comuna = scanner.nextLine();

            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine();

            System.out.println("\nTours disponibles:");

            tourService.mostrarToursNumerados();

            System.out.print("Seleccione un tour: ");
            int opcionTour = leerEntero();

            if (opcionTour < 1 ||
                    opcionTour > tourService.cantidadTours()) {

                System.out.println("Tour inválido.");
                return;
            }

            Tour tourSeleccionado =
                    tourService.obtenerTour(opcionTour - 1);

            Direccion direccion =
                    new Direccion(
                            calle,
                            numero,
                            comuna
                    );

            Cliente cliente =
                    new Cliente(
                            nombre,
                            rut,
                            direccion,
                            telefono,
                            tourSeleccionado
                    );

            if (personaService.agregarPersona(cliente)) {

                System.out.println(
                        "Cliente registrado correctamente."
                );

            } else {

                System.out.println(
                        "Ya existe una persona con ese RUT."
                );
            }

        } catch (ValidacionException e) {

            System.out.println(e.getMessage());
        }
    }

    private static void registrarGuia(
            PersonaService personaService) {

        try {

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("RUT: ");
            String rut = scanner.nextLine();

            System.out.print("Calle: ");
            String calle = scanner.nextLine();

            System.out.print("Número: ");
            int numero = leerEntero();

            System.out.print("Comuna: ");
            String comuna = scanner.nextLine();

            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine();

            System.out.print("Idioma: ");
            String idioma = scanner.nextLine();

            Direccion direccion =
                    new Direccion(
                            calle,
                            numero,
                            comuna
                    );

            GuiaTuristico guia =
                    new GuiaTuristico(
                            nombre,
                            rut,
                            direccion,
                            telefono,
                            idioma
                    );

            if (personaService.agregarPersona(guia)) {

                System.out.println(
                        "Guía registrado correctamente."
                );

            } else {

                System.out.println(
                        "Ya existe una persona con ese RUT."
                );
            }

        } catch (ValidacionException e) {

            System.out.println(e.getMessage());
        }
    }

    private static void registrarTour(
            TourService tourService) {

        try {

            System.out.print("Nombre del tour: ");
            String nombre = scanner.nextLine();

            System.out.print("Lugar: ");
            String lugar = scanner.nextLine();

            System.out.print("Precio: ");
            int precio = leerEntero();

            Tour tour =
                    new Tour(
                            nombre,
                            lugar,
                            precio
                    );

            if (tourService.agregarTour(tour)) {

                System.out.println(
                        "Tour registrado correctamente."
                );

            } else {

                System.out.println(
                        "Ya existe un tour con ese nombre."
                );
            }

        } catch (ValidacionException e) {

            System.out.println(e.getMessage());
        }
    }

    private static void buscarPersona(
            PersonaService personaService) {

        try {

            System.out.print("Ingrese RUT: ");
            String rut = scanner.nextLine();

            Validador.validarRut(rut);

            Persona persona =
                    personaService.buscarPorRut(rut);

            if (persona == null) {

                System.out.println(
                        "No se encontró ninguna persona."
                );

            } else {

                System.out.println(persona);
            }

        } catch (ValidacionException e) {

            System.out.println(e.getMessage());
        }
    }

    private static void menuFiltros(
            TourService tourService) {

        int opcion;

        do {

            System.out.println("\n===== FILTRAR TOURS =====");
            System.out.println("1. Tours desde un precio");
            System.out.println("2. Tours hasta un precio");
            System.out.println("3. Tours por lugar");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {

                case 1:

                    System.out.print(
                            "Precio mínimo: "
                    );

                    int precioMinimo =
                            leerEntero();

                    mostrarResultadosTours(
                            tourService.buscarDesdePrecio(
                                    precioMinimo
                            )
                    );

                    break;

                case 2:

                    System.out.print(
                            "Precio máximo: "
                    );

                    int precioMaximo =
                            leerEntero();

                    mostrarResultadosTours(
                            tourService.buscarHastaPrecio(
                                    precioMaximo
                            )
                    );

                    break;

                case 3:

                    System.out.print(
                            "Lugar: "
                    );

                    String lugar =
                            scanner.nextLine();

                    mostrarResultadosTours(
                            tourService.buscarPorLugar(
                                    lugar
                            )
                    );

                    break;

                case 4:
                    break;

                default:
                    System.out.println(
                            "Opción inválida."
                    );
            }

        } while (opcion != 4);
    }

    private static void mostrarResultadosTours(
            ArrayList<Tour> tours) {

        if (tours.isEmpty()) {

            System.out.println(
                    "No se encontraron tours."
            );

            return;
        }

        for (Tour tour : tours) {

            System.out.println(tour);
        }
    }
}
