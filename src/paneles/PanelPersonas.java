package paneles;

import model.*;
import service.PersonaService;
import service.TourService;
import util.ValidacionException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


/**
 * Panel encargado de gestionar personas.
 */
public class PanelPersonas extends JPanel {

    private PersonaService personaService;
    private TourService tourService;

    private String personaEnEdicion = null;

    private JComboBox<String> cmbTipo;
    private JComboBox<String> cmbDato;

    private JTextField txtNombre;
    private JTextField txtRut;
    private JTextField txtTelefono;
    private JTextField txtDireccion;

    private JLabel lblDato;

    public PanelPersonas(PersonaService personaService, TourService tourService){

        this.personaService = personaService;
        this.tourService = tourService;

        crearPanel();

    }


    private void crearPanel(){

        setLayout(new BorderLayout(10,10));

        setBorder(BorderFactory.createEmptyBorder(
                10,
                10,
                10,
                10)
        );

        JPanel formulario = new JPanel(
                new GridLayout(6,2,10,10)
        );

        formulario.setBorder(BorderFactory.createTitledBorder("Gestión de Personas"));

        cmbTipo = new JComboBox<>(
                new String[]{
                        "Cliente",
                        "Guía"
                });


        txtNombre = new JTextField();

        txtRut = new JTextField();

        txtTelefono = new JTextField();

        txtDireccion = new JTextField();
        lblDato = new JLabel("Tour:");

        cmbDato = new JComboBox<>();

        formulario.add(new JLabel("Tipo:"));
        formulario.add(cmbTipo);

        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);

        formulario.add(new JLabel("RUT:"));
        formulario.add(txtRut);

        formulario.add(new JLabel("Teléfono:"));
        formulario.add(txtTelefono);

        formulario.add(lblDato);
        formulario.add(cmbDato);

        formulario.add(new JLabel("Dirección (calle,número,comuna):"));

        formulario.add(txtDireccion);

        add(formulario, BorderLayout.NORTH);

        JPanel botones = new JPanel();

        JButton btnAgregar = new JButton("Agregar");

        JButton btnMostrar = new JButton("Mostrar");

        JButton btnModificar = new JButton("Modificar");

        JButton btnEliminar = new JButton("Eliminar");

        botones.add(btnAgregar);
        botones.add(btnMostrar);
        botones.add(btnModificar);
        botones.add(btnEliminar);

        add(botones, BorderLayout.SOUTH);

        cargarDatoInicial();

        cmbTipo.addActionListener(e -> cargarDatoInicial());

        btnAgregar.addActionListener(e -> agregarPersona());

        btnMostrar.addActionListener(e -> mostrarPersonas());

        btnModificar.addActionListener(e -> modificarPersona());

        btnEliminar.addActionListener(e -> eliminarPersona());
    }


    private void cargarDatoInicial(){

        cmbDato.removeAllItems();

        if(cmbTipo.getSelectedItem().equals("Cliente")){

            lblDato.setText("Tour:");

            for(Tour tour : tourService.getTours()){

                cmbDato.addItem(tour.getNombre());

            }

        }else{

            lblDato.setText("Idioma:");

            cmbDato.addItem("Español");
            cmbDato.addItem("Inglés");
            cmbDato.addItem("Portugués");
            cmbDato.addItem("Alemán");
            cmbDato.addItem("Francés");

        }

    }

    private void agregarPersona(){

        try {

            String[] datosDireccion = txtDireccion.getText().split(",");

            if(datosDireccion.length != 3){

                JOptionPane.showMessageDialog(this,
                        "Formato dirección: calle,número,comuna"
                );

                return;

            }

            Direccion direccion = new Direccion(
                    datosDireccion[0].trim(), Integer.parseInt(datosDireccion[1].trim()), datosDireccion[2].trim()
                    );

            Persona persona;

            if(cmbTipo.getSelectedItem().equals("Cliente")){

                Tour tour = tourService.buscarPorNombre(
                        (String)cmbDato.getSelectedItem()
                );

                persona = new Cliente(
                        txtNombre.getText(),
                        txtRut.getText(),
                        direccion,
                        txtTelefono.getText(),
                        tour
                );

            }else{

                persona = new GuiaTuristico(
                        txtNombre.getText(),
                        txtRut.getText(),
                        direccion,
                        txtTelefono.getText(),
                        (String)cmbDato.getSelectedItem()
                );

            }

            if(personaService.agregarPersona(persona)){

                JOptionPane.showMessageDialog(this, "Persona agregada correctamente");

                limpiarCampos();

            }else{

                JOptionPane.showMessageDialog(this,
                        "El RUT ya existe",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

            }

        }catch(NumberFormatException ex){

            JOptionPane.showMessageDialog(this,
                    "Número de dirección inválido",
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


    private void mostrarPersonas(){

        String[] columnas = {
                "Tipo",
                "Nombre",
                "RUT",
                "Teléfono",
                "Dato"
        };

        DefaultTableModel modelo = new DefaultTableModel(
                columnas,
                0
        );

        for(Persona persona : personaService.getPersonas()){

            String tipo;
            String dato;

            if(persona instanceof Cliente cliente){

                tipo ="Cliente";
                dato = cliente.getTourReservado().getNombre();

            }else{

                GuiaTuristico guia = (GuiaTuristico) persona;

                tipo ="Guía";
                dato = guia.getIdioma();

            }

            modelo.addRow(new Object[]{
                    tipo,
                    persona.getNombre(),
                    persona.getRut(),
                    persona.getTelefono(),
                    dato
            }
            );

        }

        JTable tabla = new JTable(modelo);

        JOptionPane.showMessageDialog(this,
                new JScrollPane(tabla),
                "Personas registradas",
                JOptionPane.PLAIN_MESSAGE
        );

    }


    private JComboBox<String> crearComboPersonas(){

        JComboBox<String> combo = new JComboBox<>();

        for(Persona persona : personaService.getPersonas()){

            combo.addItem(
                    persona.getRut()
                            + " - "
                            + persona.getNombre()
            );

        }

        return combo;

    }


    private String obtenerRutSeleccionado(String texto){

        return texto.split(" - ")[0];

    }


    private void cargarDatosPersona(String rut){

        Persona persona = personaService.buscarPorRut(rut);

        if(persona == null){
            return;
        }

        txtNombre.setText(persona.getNombre());

        txtRut.setText(persona.getRut());

        txtTelefono.setText(persona.getTelefono());

        Direccion direccion = persona.getDireccion();

        txtDireccion.setText(
                direccion.getCalle()
                        + ", "
                        + direccion.getNumero()
                        + ", "
                        + direccion.getComuna()
        );

        if(persona instanceof Cliente cliente){

            cmbTipo.setSelectedItem("Cliente");

            cmbDato.setSelectedItem(cliente.getTourReservado().getNombre());

        }else if(persona instanceof GuiaTuristico guia){

            cmbTipo.setSelectedItem("Guía");

            cmbDato.setSelectedItem(guia.getIdioma());

        }

    }

    private void modificarPersona(){

        // PRIMER PASO: seleccionar persona

        if(personaEnEdicion == null){

            JComboBox<String> combo = crearComboPersonas();

            int resultado =
                    JOptionPane.showConfirmDialog(
                            this,
                            combo,
                            "Seleccione persona a modificar",
                            JOptionPane.OK_CANCEL_OPTION
                    );

            if(resultado != JOptionPane.OK_OPTION){
                return;
            }

            String seleccion = (String) combo.getSelectedItem();

            if(seleccion == null){
                return;
            }

            personaEnEdicion = obtenerRutSeleccionado(seleccion);

            cargarDatosPersona(personaEnEdicion);

            JOptionPane.showMessageDialog(this,
                    "Datos cargados.\nModifique los campos y presione nuevamente Modificar para guardar.",
                    "Modificar persona",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;

        }

        // SEGUNDO PASO: guardar modificación

        try{

            String[] datosDireccion = txtDireccion.getText().split(",");

            if(datosDireccion.length != 3){

                JOptionPane.showMessageDialog(this,
                        "Formato dirección: calle, número, comuna",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;

            }

            Direccion direccion =
                    new Direccion(
                            datosDireccion[0].trim(),
                            Integer.parseInt(
                                    datosDireccion[1].trim()
                            ),
                            datosDireccion[2].trim()
                    );


            Persona persona = personaService.buscarPorRut(personaEnEdicion);

            boolean resultado;

            if(persona instanceof Cliente){

                Tour tour = tourService.buscarPorNombre((String)cmbDato.getSelectedItem());

                Cliente cliente =
                        new Cliente(
                                txtNombre.getText(),
                                txtRut.getText(),
                                direccion,
                                txtTelefono.getText(),
                                tour
                        );


                resultado = personaService.modificarPersona(personaEnEdicion, cliente);

            }else{

                GuiaTuristico guia =
                        new GuiaTuristico(
                                txtNombre.getText(),
                                txtRut.getText(),
                                direccion,
                                txtTelefono.getText(),
                                (String)cmbDato.getSelectedItem()
                        );

                resultado = personaService.modificarPersona(personaEnEdicion, guia);

            }

            if(resultado){

                JOptionPane.showMessageDialog(this,
                        "Persona modificada correctamente"
                );

                personaEnEdicion = null;

                limpiarCampos();

            }else{

                JOptionPane.showMessageDialog(this,
                        "No se pudo modificar la persona",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

            }

        }catch(Exception e){

            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }


    private void eliminarPersona(){

        JComboBox<String> combo = crearComboPersonas();

        int resultado =
                JOptionPane.showConfirmDialog(this,
                        combo,
                        "Seleccione persona a eliminar",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if(resultado != JOptionPane.OK_OPTION){
            return;
        }

        String seleccion = (String) combo.getSelectedItem();

        if(seleccion == null){
            return;
        }

        String rut = obtenerRutSeleccionado(seleccion);

        Persona persona = personaService.buscarPorRut(rut);

        if(persona == null){
            return;
        }

        int confirmar =
                JOptionPane.showConfirmDialog(this,
                        "¿Está seguro de eliminar a "
                                + persona.getNombre()
                                + "?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );


        if(confirmar != JOptionPane.YES_OPTION){
            return;
        }

        boolean eliminado = personaService.eliminarPersona(rut);

        if(eliminado){

            JOptionPane.showMessageDialog(this,
                    "Persona eliminada correctamente"
            );

        }else{

            JOptionPane.showMessageDialog(this,
                    "No se pudo eliminar la persona",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }


    private void limpiarCampos(){

        txtNombre.setText("");
        txtRut.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");

    }

}