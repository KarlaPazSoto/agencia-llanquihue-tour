package paneles;

import data.GestorEntidades;
import model.ServicioTuristico;
import model.Tour;
import service.PersonaService;
import service.ServicioService;
import service.TourService;
import util.ValidacionException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


/**
 * Panel encargado de gestionar los Tours.
 */
public class PanelTours extends JPanel {

    private TourService tourService;
    private ServicioService servicioService;
    private PersonaService personaService;

    private JTextField txtNombre;
    private JTextField txtLugar;
    private JTextField txtPrecio;

    private JComboBox<String> cmbServicio;

    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnMostrar;
    private Tour tourSeleccionado;


    public PanelTours(
            TourService tourService,
            PersonaService personaService,
            ServicioService servicioService,
            GestorEntidades gestor
    ) {

        this.tourService = tourService;
        this.servicioService = servicioService;
        this.personaService = personaService;

        crearPanel();

    }


    private void crearPanel() {

        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(
                10,10,10,10)
        );

        JPanel formulario = new JPanel(new GridLayout(4,2,10,10));

        formulario.setBorder(BorderFactory.createTitledBorder(
                "Gestión de Tours")
        );

        txtNombre = new JTextField();
        txtLugar = new JTextField();
        txtPrecio = new JTextField();

        cmbServicio = new JComboBox<>();

        cargarServicios();

        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);

        formulario.add(new JLabel("Lugar:"));
        formulario.add(txtLugar);

        formulario.add(new JLabel("Precio:"));
        formulario.add(txtPrecio);

        formulario.add(new JLabel("Servicio:"));
        formulario.add(cmbServicio);

        add(formulario, BorderLayout.NORTH);

        JPanel botones = new JPanel();

        btnAgregar = new JButton("Agregar");

        btnModificar = new JButton("Modificar");

        btnEliminar = new JButton("Eliminar");

        btnMostrar = new JButton("Mostrar");

        botones.add(btnAgregar);
        botones.add(btnModificar);
        botones.add(btnEliminar);
        botones.add(btnMostrar);

        add(botones, BorderLayout.SOUTH);

        configurarEventos();

    }


    private void cargarServicios(){


        cmbServicio.removeAllItems();

        cmbServicio.addItem("Ninguno");

        for(ServicioTuristico servicio : servicioService.getServicios()){

            cmbServicio.addItem(servicio.getNombre());
        }


    }


    private void configurarEventos(){


        btnAgregar.addActionListener(e -> agregarTour());

        btnModificar.addActionListener(e -> modificarTour());

        btnEliminar.addActionListener(e -> eliminarTour());

        btnMostrar.addActionListener(e -> mostrarTours());

    }


    private void agregarTour(){


        try {

            String nombre = txtNombre.getText().trim();


            String lugar = txtLugar.getText().trim();

            int precio = Integer.parseInt(txtPrecio.getText());

            ServicioTuristico servicio = obtenerServicioSeleccionado();

            Tour tour = new Tour(
                    nombre,
                    lugar,
                    precio,
                    servicio
            );

            if(tourService.agregarTour(tour)){

                JOptionPane.showMessageDialog(this,
                        "Tour agregado correctamente"
                );

                limpiarCampos();

            }else{

                JOptionPane.showMessageDialog(this,
                        "El tour ya existe",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

            }

        }catch(NumberFormatException ex){

            JOptionPane.showMessageDialog(this,
                    "El precio debe ser numérico",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }catch(ValidacionException ex){

            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }

    private JComboBox<Tour> crearComboTours(){


        JComboBox<Tour> combo = new JComboBox<>();

        for(Tour tour : tourService.getTours()){

            combo.addItem(tour);

        }

        return combo;
    }


    private ServicioTuristico obtenerServicioSeleccionado(){


        String nombre = (String)cmbServicio.getSelectedItem();

        if(nombre.equals("Ninguno")){

            return null;

        }

        return servicioService.buscarPorNombre(nombre);

    }


    private void modificarTour(){

        // Primera etapa: seleccionar tour

        if(tourSeleccionado == null){

            JComboBox<Tour> comboTours = crearComboTours();

            int opcion =
                    JOptionPane.showConfirmDialog(this,
                            comboTours,
                            "Seleccione el tour a modificar",
                            JOptionPane.OK_CANCEL_OPTION
                    );


            if(opcion != JOptionPane.OK_OPTION){
                return;
            }

            tourSeleccionado = (Tour) comboTours.getSelectedItem();


            if(tourSeleccionado == null){
                return;
            }

            txtNombre.setText(tourSeleccionado.getNombre());

            txtLugar.setText(tourSeleccionado.getLugar());

            txtPrecio.setText(String.valueOf(tourSeleccionado.getPrecio()));

            if(tourSeleccionado.getServicio() == null){

                cmbServicio.setSelectedIndex(0);

            }else{

                cmbServicio.setSelectedItem(tourSeleccionado.getServicio().getNombre());

            }

            JOptionPane.showMessageDialog(this,
                    "Modifique los datos y presione Modificar nuevamente para guardar"
            );

            return;

        }

        // Segunda etapa: guardar cambios

        try {
            int precio = Integer.parseInt(
                    txtPrecio.getText()
            );

            boolean modificado = tourService.modificarTour(
                    tourSeleccionado.getNombre(),
                    txtNombre.getText(),
                    txtLugar.getText(),
                    precio,
                    obtenerServicioSeleccionado()
            );

            if(modificado){

                JOptionPane.showMessageDialog(this,
                        "Tour modificado correctamente"
                );

                limpiarCampos();

                tourSeleccionado = null;

            }else{

                JOptionPane.showMessageDialog(this,
                        "No se pudo modificar el tour",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

            }

        }catch(NumberFormatException ex){

            JOptionPane.showMessageDialog(this,
                    "El precio debe ser numérico",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }


    private void eliminarTour(){


        JComboBox<Tour> comboTours = crearComboTours();

        int opcion = JOptionPane.showConfirmDialog(this,
                        comboTours,
                        "Seleccione el tour a eliminar",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if(opcion != JOptionPane.OK_OPTION){

            return;

        }

        Tour tour = (Tour) comboTours.getSelectedItem();

        if(tour == null){

            return;

        }

        int confirmar = JOptionPane.showConfirmDialog(this,
                        "¿Está seguro de eliminar el tour?\n"
                                + tour.getNombre(),
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );


        if(confirmar != JOptionPane.YES_OPTION){

            return;

        }

        boolean eliminado = tourService.eliminarTour(tour.getNombre(), personaService);

        if(eliminado){

            JOptionPane.showMessageDialog(this,
                    "Tour eliminado correctamente"
            );

        }else{

            JOptionPane.showMessageDialog(this,
                    "No se puede eliminar porque está asociado a un cliente",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }


    private void mostrarTours(){


        String[] columnas = {
                "Nombre",
                "Lugar",
                "Precio",
                "Servicio"
        };

        DefaultTableModel modelo = new DefaultTableModel(
                        columnas, 0
                );

        for(Tour tour : tourService.getTours()){

            modelo.addRow(new Object[]{
                            tour.getNombre(),
                            tour.getLugar(),
                            "$"+tour.getPrecio(),
                            tour.getServicio()!=null
                                    ? tour.getServicio().getNombre()
                                    : "Ninguno"
            }
            );

        }

        JTable tabla = new JTable(modelo);

        JOptionPane.showMessageDialog(this, new JScrollPane(tabla),
                "Tours registrados",
                JOptionPane.PLAIN_MESSAGE
        );

    }


    private void limpiarCampos(){

        txtNombre.setText("");
        txtLugar.setText("");
        txtPrecio.setText("");
        cmbServicio.setSelectedIndex(0);

        tourSeleccionado = null;

    }

}