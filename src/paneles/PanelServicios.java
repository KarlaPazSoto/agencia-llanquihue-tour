package paneles;

import model.*;
import service.ServicioService;
import service.TourService;
import util.ValidacionException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


/**
 * Panel encargado de gestionar servicios turísticos.
 */
public class PanelServicios extends JPanel {

    private ServicioService servicioService;
    private TourService tourService;

    private JComboBox<String> cmbTipo;

    private JTextField txtNombre;
    private JTextField txtDuracion;
    private JTextField txtDato;

    private JLabel lblDato;

    private JButton btnModificar;
    private JButton btnEliminar;

    private String servicioEnEdicion = null;

    public PanelServicios(ServicioService servicioService, TourService tourService){

        this.servicioService = servicioService;
        this.tourService = tourService;

        crearPanel();

    }


    private void crearPanel(){

        setLayout(new BorderLayout(10,10));

        setBorder(BorderFactory.createEmptyBorder(10,
                10,
                10,
                10
                )
        );

        JPanel formulario = new JPanel(new GridLayout(5,2,10,10));

        formulario.setBorder(
                BorderFactory.createTitledBorder("Gestión de Servicios")
        );

        cmbTipo = new JComboBox<>(
                new String[]{
                        "Paseo Lacustre",
                        "Excursión Cultural",
                        "Ruta Gastronómica"
                }
                );


        txtNombre = new JTextField();

        txtDuracion = new JTextField();

        lblDato = new JLabel("Tipo embarcación:");

        txtDato = new JTextField();

        formulario.add(new JLabel("Tipo:"));

        formulario.add(cmbTipo);

        formulario.add(new JLabel("Nombre:"));

        formulario.add(txtNombre);

        formulario.add(new JLabel("Duración (horas):"));

        formulario.add(txtDuracion);

        formulario.add(lblDato);

        formulario.add(txtDato);

        add(formulario, BorderLayout.NORTH);

        JPanel botones = new JPanel();

        JButton btnAgregar = new JButton("Agregar");

        JButton btnModificar = new JButton("Modificar");

        JButton btnEliminar = new JButton("Eliminar");

        JButton btnMostrar = new JButton("Mostrar");

        botones.add(btnAgregar);
        botones.add(btnMostrar);
        botones.add(btnModificar);
        botones.add(btnEliminar);

        add(botones, BorderLayout.SOUTH);

        cmbTipo.addActionListener(e -> cambiarEtiqueta());

        btnAgregar.addActionListener(e -> agregarServicio());

        btnMostrar.addActionListener(e -> mostrarServicios());

        btnModificar.addActionListener(e -> modificarServicio());

        btnEliminar.addActionListener(e -> eliminarServicio());

    }


    private void cambiarEtiqueta(){

        String tipo = (String)cmbTipo.getSelectedItem();

        if(tipo.equals("Paseo Lacustre")){

            lblDato.setText("Tipo embarcación:");

        }else if(tipo.equals("Excursión Cultural")){

            lblDato.setText("Lugar histórico:");

        }else{

            lblDato.setText("Número paradas:");

        }

    }


    private void agregarServicio(){

        try {

            String nombre = txtNombre.getText().trim();

            int duracion = Integer.parseInt(txtDuracion.getText());

            String dato = txtDato.getText().trim();

            ServicioTuristico servicio;

            String tipo = (String)cmbTipo.getSelectedItem();

            if(tipo.equals("Paseo Lacustre")){

                servicio = new PaseoLacustre(
                        nombre,
                        duracion,
                        dato
                );

            }else if(tipo.equals("Excursión Cultural")){

                servicio = new ExcursionCultural(
                        nombre,
                        duracion,
                        dato
                );

            }else{

                servicio = new RutaGastronomica(
                        nombre,
                        duracion,
                        Integer.parseInt(dato)
                );

            }

            if(servicioService.agregarServicio(servicio)){

                JOptionPane.showMessageDialog(this,
                        "Servicio agregado correctamente"
                );

                limpiarCampos();

            }else{

                JOptionPane.showMessageDialog(this,
                        "El servicio ya existe",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

            }


        }catch(NumberFormatException ex){

            JOptionPane.showMessageDialog(this,
                    "Datos numéricos inválidos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }


    private void mostrarServicios(){

        String[] columnas = {
                "Tipo",
                "Nombre",
                "Duración",
                "Dato"
        };

        DefaultTableModel modelo = new DefaultTableModel(
                columnas,
                0
        );

        for(ServicioTuristico servicio : servicioService.getServicios()){

            String dato="";

            if(servicio instanceof PaseoLacustre paseo){

                dato = paseo.getTipoEmbarcacion();

            }else if(servicio instanceof ExcursionCultural excursion){

                dato = excursion.getLugarHistorico();

            }else if(servicio instanceof RutaGastronomica ruta){

                dato = String.valueOf(ruta.getNumeroParadas());

            }

            modelo.addRow(new Object[]{
                    servicio.getClass().getSimpleName(),
                    servicio.getNombre(),
                    servicio.getDuracionHoras()+" h",
                    dato
            }
            );

        }

        JTable tabla = new JTable(modelo);

        JOptionPane.showMessageDialog(this,
                new JScrollPane(tabla),
                "Servicios registrados",
                JOptionPane.PLAIN_MESSAGE
        );

    }


    private void modificarServicio(){

        // Si no hay servicio seleccionado, primero cargamos uno
        if(servicioEnEdicion == null){

            JComboBox<String> combo = crearComboServicios();

            int resultado = JOptionPane.showConfirmDialog(this,
                    combo,
                    "Seleccione servicio a modificar",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if(resultado != JOptionPane.OK_OPTION){
                return;
            }

            servicioEnEdicion = (String) combo.getSelectedItem();

            if(servicioEnEdicion == null){
                return;
            }

            cargarDatosServicio(servicioEnEdicion);

            JOptionPane.showMessageDialog(this,
                    "Datos cargados.\nModifique los campos y presione nuevamente Modificar para guardar.",
                    "Modificar servicio",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        // Segundo click del botón: guardar cambios

        try{
            int duracion = Integer.parseInt(txtDuracion.getText());

            boolean resultado = servicioService.modificarServicio(
                    servicioEnEdicion,
                    txtNombre.getText(),
                    duracion,
                    txtDato.getText()
            );

            if(resultado){

                JOptionPane.showMessageDialog(this,
                        "Servicio modificado correctamente"
                );

                servicioEnEdicion = null;

                limpiarCampos();

            }else{

                JOptionPane.showMessageDialog(this,
                        "No se pudo modificar el servicio",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

            }

        }catch(NumberFormatException e){

            JOptionPane.showMessageDialog(this,
                    "La duración debe ser numérica",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }

    private void eliminarServicio(){


        JComboBox<String> combo = crearComboServicios();


        int resultado = JOptionPane.showConfirmDialog(this,
                combo,
                "Seleccione servicio a eliminar",
                JOptionPane.OK_CANCEL_OPTION
        );


        if(resultado != JOptionPane.OK_OPTION){
            return;
        }

        String servicio = (String)combo.getSelectedItem();

        if(servicio == null){
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Eliminar servicio " + servicio + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if(respuesta != JOptionPane.YES_OPTION){
            return;
        }

        boolean eliminado = servicioService.eliminarServicio(
                servicio,
                tourService
        );

        if(eliminado){

            JOptionPane.showMessageDialog(this,
                    "Servicio eliminado correctamente"
            );

            limpiarCampos();

        }else{

            JOptionPane.showMessageDialog(this,
                    "No se puede eliminar. Puede estar asociado a un tour.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }


    private JComboBox<String> crearComboServicios(){

        JComboBox<String> combo = new JComboBox<>();

        for(ServicioTuristico servicio : servicioService.getServicios()){

            combo.addItem(servicio.getNombre());

        }

        return combo;
    }


    private void limpiarCampos(){

        txtNombre.setText("");

        txtDuracion.setText("");

        txtDato.setText("");

    }


    private void cargarDatosServicio(String nombreServicio){

        for(ServicioTuristico servicio : servicioService.getServicios()){

            if(servicio.getNombre().equalsIgnoreCase(nombreServicio)){

                txtNombre.setText(servicio.getNombre());

                txtDuracion.setText(String.valueOf(servicio.getDuracionHoras()));

                if(servicio instanceof PaseoLacustre paseo){

                    txtDato.setText(paseo.getTipoEmbarcacion());

                }else if(servicio instanceof ExcursionCultural excursion){

                    txtDato.setText(excursion.getLugarHistorico());

                }else if(servicio instanceof RutaGastronomica ruta){

                    txtDato.setText(String.valueOf(ruta.getNumeroParadas()));
                }

                break;
            }
        }
    }

}