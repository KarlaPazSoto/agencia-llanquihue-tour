package app;

import data.GestorEntidades;
import paneles.PanelPersonas;
import paneles.PanelResumen;
import paneles.PanelServicios;
import paneles.PanelTours;
import service.PersonaService;
import service.ServicioService;
import service.TourService;

import javax.swing.*;


/**
 * Ventana principal de la aplicación Llanquihue Tour.
 *
 * Su responsabilidad es inicializar servicios,
 * cargar datos y administrar los paneles.
 */
public class VentanaPrincipal extends JFrame {


    private TourService tourService;
    private PersonaService personaService;
    private ServicioService servicioService;

    private GestorEntidades gestor;


    public VentanaPrincipal(){


        setTitle("Agencia Llanquihue Tour");

        setSize(900, 700);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        inicializarServicios();

        crearInterfaz();

    }

    private void inicializarServicios(){

        /*
         * Orden importante:
         * 1) Servicios
         * 2) Tours
         * 3) Personas
         * porque los tours pueden tener servicios
         * asociados y los clientes pueden tener tours.
         */

        servicioService = new ServicioService();

        servicioService.cargarServicios();

        tourService = new TourService(servicioService);

        tourService.cargarTours();

        personaService = new PersonaService();

        personaService.cargarPersonas(tourService);

        gestor = new GestorEntidades();


        actualizarGestor();

    }


    private void actualizarGestor(){


        gestor.limpiar();

        gestor.agregarMultiples(tourService.getTours());

        gestor.agregarMultiples(personaService.getPersonas());

        gestor.agregarMultiples(servicioService.getServicios());

    }


    private void crearInterfaz(){


        JTabbedPane pestanias = new JTabbedPane();

        pestanias.addTab("Tours", new PanelTours(
                        tourService,
                        personaService,
                        servicioService,
                        gestor
                )
        );

        pestanias.addTab("Personas", new PanelPersonas(
                        personaService,
                        tourService
                )
        );

        pestanias.addTab("Servicios", new PanelServicios(
                        servicioService,
                        tourService
                )
        );

        pestanias.addTab("Resumen", new PanelResumen(
                        gestor
                )
        );

        add(pestanias);

    }

}