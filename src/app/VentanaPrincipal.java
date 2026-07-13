package app;

import data.GestorEntidades;
import model.*;
import service.PersonaService;
import service.TourService;
import service.ServicioService;
import util.ValidacionException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Interfaz gráfica para la gestión de la agencia turística Llanquihue Tour.
 * Permite ingresar y visualizar entidades (Tours, Personas, Servicios).
 */
public class VentanaPrincipal extends JFrame {

    private TourService tourService;
    private PersonaService personaService;
    private ServicioService servicioService;
    private GestorEntidades gestor;

    private JTabbedPane tabbedPane;
    private DefaultTableModel modeloTabla;

    public VentanaPrincipal() {
        setTitle("Agencia Llanquihue Tour");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        inicializarServicios();
        inicializarComponentes();
    }

    /**
     * Inicializa los servicios y carga datos
     */
    private void inicializarServicios() {
        tourService = new TourService();
        tourService.cargarTours();

        personaService = new PersonaService();
        personaService.cargarPersonas(tourService);

        servicioService = new ServicioService();
        servicioService.cargarServicios();

        gestor = new GestorEntidades();
        gestor.agregarMultiples(tourService.getTours());
        gestor.agregarMultiples(personaService.getPersonas());
        gestor.agregarMultiples(servicioService.getServicios());
    }

    /**
     * Crea los componentes gráficos
     */
    private void inicializarComponentes() {
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Tours", crearPanelTours());
        tabbedPane.addTab("Personas", crearPanelPersonas());
        tabbedPane.addTab("Servicios", crearPanelServicios());
        tabbedPane.addTab("Resumen General", crearPanelResumen());

        add(tabbedPane);
    }

    /**
     * Crea panel para gestionar tours
     */
    private JPanel crearPanelTours() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Agregar Tour"));

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();

        JLabel lblLugar = new JLabel("Lugar:");
        JTextField txtLugar = new JTextField();

        JLabel lblPrecio = new JLabel("Precio:");
        JTextField txtPrecio = new JTextField();

        JLabel lblServicio = new JLabel("Servicio (opcional):");
        JComboBox<String> cmbServicio = new JComboBox<>();
        cmbServicio.addItem("Ninguno");
        for (ServicioTuristico s : servicioService.getServicios()) {
            cmbServicio.addItem(s.getNombre());
        }

        inputPanel.add(lblNombre);
        inputPanel.add(txtNombre);
        inputPanel.add(lblLugar);
        inputPanel.add(txtLugar);
        inputPanel.add(lblPrecio);
        inputPanel.add(txtPrecio);
        inputPanel.add(lblServicio);
        inputPanel.add(cmbServicio);

        JButton btnAgregar = new JButton("Agregar Tour");
        btnAgregar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText().trim();
                String lugar = txtLugar.getText().trim();
                int precio = Integer.parseInt(txtPrecio.getText());
                String servicioNombre = (String) cmbServicio.getSelectedItem();

                ServicioTuristico servicio = null;
                if (!servicioNombre.equals("Ninguno")) {
                    servicio = servicioService.buscarPorNombre(servicioNombre);
                }

                Tour tour = new Tour(nombre, lugar, precio, servicio);
                if (tourService.agregarTour(tour)) {
                    gestor.agregarEntidad(tour);
                    JOptionPane.showMessageDialog(this, "Tour agregado correctamente");
                    txtNombre.setText("");
                    txtLugar.setText("");
                    txtPrecio.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "El tour ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Precio debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ValidacionException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnMostrar = new JButton("Mostrar Tours");
        btnMostrar.addActionListener(e -> {
            String[] columnas = {"Nombre", "Lugar", "Precio", "Servicio"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

            for (Tour t : tourService.getTours()) {
                String servicio = t.getServicio() != null ? t.getServicio().getNombre() : "Ninguno";
                modelo.addRow(new Object[]{t.getNombre(), t.getLugar(), "$" + t.getPrecio(), servicio});
            }

            mostrarTabla("Tours", modelo);
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.add(btnAgregar);
        btnPanel.add(btnMostrar);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea panel para gestionar personas
     */
    private JPanel crearPanelPersonas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Agregar Persona"));

        JLabel lblTipo = new JLabel("Tipo:");
        JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"Cliente", "Guía"});

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();

        JLabel lblRut = new JLabel("RUT:");
        JTextField txtRut = new JTextField();

        JLabel lblTelefono = new JLabel("Teléfono:");
        JTextField txtTelefono = new JTextField();

        JLabel lblDato = new JLabel("Tour / Idioma:");
        JComboBox<String> cmbDato = new JComboBox<>();

        JLabel lblDireccion = new JLabel("Dirección (calle, número, comuna):");
        JTextField txtDireccion = new JTextField();

        inputPanel.add(lblTipo);
        inputPanel.add(cmbTipo);
        inputPanel.add(lblNombre);
        inputPanel.add(txtNombre);
        inputPanel.add(lblRut);
        inputPanel.add(txtRut);
        inputPanel.add(lblTelefono);
        inputPanel.add(txtTelefono);
        inputPanel.add(lblDato);
        inputPanel.add(cmbDato);
        inputPanel.add(lblDireccion);
        inputPanel.add(txtDireccion);

        cmbTipo.addActionListener(e -> {
            cmbDato.removeAllItems();
            if (cmbTipo.getSelectedItem().equals("Cliente")) {
                lblDato.setText("Tour:");
                for (Tour t : tourService.getTours()) {
                    cmbDato.addItem(t.getNombre());
                }
            } else {
                lblDato.setText("Idioma:");
                cmbDato.addItem("Español");
                cmbDato.addItem("Inglés");
                cmbDato.addItem("Portugués");
                cmbDato.addItem("Alemán");
                cmbDato.addItem("Francés");
            }
        });

        cmbTipo.setSelectedIndex(0);

        JButton btnAgregar = new JButton("Agregar Persona");
        btnAgregar.addActionListener(e -> {
            try {
                String[] dir = txtDireccion.getText().split(",");
                if (dir.length != 3) {
                    JOptionPane.showMessageDialog(this, "Formato: calle, número, comuna", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String calle = dir[0].trim();
                int numero = Integer.parseInt(dir[1].trim());
                String comuna = dir[2].trim();

                Direccion direccion = new Direccion(calle, numero, comuna);

                if (cmbTipo.getSelectedItem().equals("Cliente")) {
                    Tour tour = tourService.buscarPorNombre((String) cmbDato.getSelectedItem());
                    Cliente cliente = new Cliente(txtNombre.getText(), txtRut.getText(), direccion, txtTelefono.getText(), tour);
                    if (personaService.agregarPersona(cliente)) {
                        gestor.agregarEntidad(cliente);
                        JOptionPane.showMessageDialog(this, "Cliente agregado correctamente");
                    }
                } else {
                    GuiaTuristico guia = new GuiaTuristico(txtNombre.getText(), txtRut.getText(), direccion, txtTelefono.getText(), (String) cmbDato.getSelectedItem());
                    if (personaService.agregarPersona(guia)) {
                        gestor.agregarEntidad(guia);
                        JOptionPane.showMessageDialog(this, "Guía agregado correctamente");
                    }
                }

                txtNombre.setText("");
                txtRut.setText("");
                txtTelefono.setText("");
                txtDireccion.setText("");

            } catch (ValidacionException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnMostrar = new JButton("Mostrar Personas");
        btnMostrar.addActionListener(e -> {
            String[] columnas = {"Tipo", "Nombre", "RUT", "Teléfono", "Dato Extra"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

            for (Persona p : personaService.getPersonas()) {
                String tipo = p instanceof Cliente ? "Cliente" : "Guía";
                String datoExtra = p instanceof Cliente ? ((Cliente) p).getTourReservado().getNombre() : ((GuiaTuristico) p).getIdioma();
                modelo.addRow(new Object[]{tipo, p.getNombre(), p.getRut(), p.getTelefono(), datoExtra});
            }

            mostrarTabla("Personas", modelo);
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.add(btnAgregar);
        btnPanel.add(btnMostrar);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea panel para gestionar servicios
     */
    private JPanel crearPanelServicios() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Agregar Servicio"));

        JLabel lblTipo = new JLabel("Tipo:");
        JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"Paseo Lacustre", "Excursión Cultural", "Ruta Gastronómica"});

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();

        JLabel lblDuracion = new JLabel("Duración (horas):");
        JTextField txtDuracion = new JTextField();

        JLabel lblDato = new JLabel("Embarcación:");
        JTextField txtDato = new JTextField();

        inputPanel.add(lblTipo);
        inputPanel.add(cmbTipo);
        inputPanel.add(lblNombre);
        inputPanel.add(txtNombre);
        inputPanel.add(lblDuracion);
        inputPanel.add(txtDuracion);
        inputPanel.add(lblDato);
        inputPanel.add(txtDato);

        cmbTipo.addActionListener(e -> {
            if (cmbTipo.getSelectedItem().equals("Paseo Lacustre")) {
                lblDato.setText("Tipo Embarcación:");
            } else if (cmbTipo.getSelectedItem().equals("Excursión Cultural")) {
                lblDato.setText("Lugar Histórico:");
            } else {
                lblDato.setText("Número Paradas:");
            }
        });

        JButton btnAgregar = new JButton("Agregar Servicio");
        btnAgregar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText().trim();
                int duracion = Integer.parseInt(txtDuracion.getText());
                String datoExtra = txtDato.getText().trim();

                ServicioTuristico servicio = null;

                if (cmbTipo.getSelectedItem().equals("Paseo Lacustre")) {
                    servicio = new PaseoLacustre(nombre, duracion, datoExtra);
                } else if (cmbTipo.getSelectedItem().equals("Excursión Cultural")) {
                    servicio = new ExcursionCultural(nombre, duracion, datoExtra);
                } else {
                    int paradas = Integer.parseInt(datoExtra);
                    servicio = new RutaGastronomica(nombre, duracion, paradas);
                }

                if (servicioService.agregarServicio(servicio)) {
                    gestor.agregarEntidad(servicio);
                    JOptionPane.showMessageDialog(this, "Servicio agregado correctamente");
                    txtNombre.setText("");
                    txtDuracion.setText("");
                    txtDato.setText("");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnMostrar = new JButton("Mostrar Servicios");
        btnMostrar.addActionListener(e -> {
            String[] columnas = {"Tipo", "Nombre", "Duración", "Dato Especial"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

            for (ServicioTuristico s : servicioService.getServicios()) {
                String tipo = s.getClass().getSimpleName();
                String dato = "";
                if (s instanceof PaseoLacustre p) {
                    dato = p.getTipoEmbarcacion();
                } else if (s instanceof ExcursionCultural ec) {
                    dato = ec.getLugarHistorico();
                } else if (s instanceof RutaGastronomica rg) {
                    dato = String.valueOf(rg.getNumeroParadas());
                }
                modelo.addRow(new Object[]{tipo, s.getNombre(), s.getDuracionHoras() + " h", dato});
            }

            mostrarTabla("Servicios", modelo);
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.add(btnAgregar);
        btnPanel.add(btnMostrar);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea panel de resumen general
     */
    private JPanel crearPanelResumen() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(textArea);

        JButton btnActualizar = new JButton("Actualizar Resumen");
        btnActualizar.addActionListener(e -> {
            gestor.limpiar();
            gestor.agregarMultiples(tourService.getTours());
            gestor.agregarMultiples(personaService.getPersonas());
            gestor.agregarMultiples(servicioService.getServicios());

            StringBuilder sb = new StringBuilder();
            for (Registrable r : gestor.getEntidades()) {
                if (r instanceof Tour) {
                    sb.append("[TOUR] ");
                } else if (r instanceof Cliente) {
                    sb.append("[CLIENTE] ");
                } else if (r instanceof GuiaTuristico) {
                    sb.append("[GUÍA] ");
                } else if (r instanceof ServicioTuristico) {
                    sb.append("[SERVICIO] ");
                }
                sb.append(r.mostrarResumen()).append("\n");
            }

            sb.append("\n===== ESTADÍSTICAS =====\n");
            sb.append("Total de entidades: ").append(gestor.cantidadEntidades()).append("\n");

            textArea.setText(sb.toString());
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(btnActualizar);

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Muestra una tabla emergente
     */
    private void mostrarTabla(String titulo, DefaultTableModel modelo) {
        JFrame frameTabb = new JFrame(titulo);
        frameTabb.setSize(700, 400);
        frameTabb.setLocationRelativeTo(this);

        JTable tabla = new JTable(modelo);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scroll = new JScrollPane(tabla);
        frameTabb.add(scroll);

        frameTabb.setVisible(true);
    }

}
