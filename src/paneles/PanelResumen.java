package paneles;

import data.GestorEntidades;
import model.*;

import javax.swing.*;
import java.awt.*;


/**
 * Panel encargado de mostrar un resumen general
 * de las entidades registradas.
 */
public class PanelResumen extends JPanel {


    private GestorEntidades gestor;

    private JTextArea areaTexto;


    public PanelResumen(GestorEntidades gestor){

        this.gestor = gestor;

        crearPanel();

    }


    private void crearPanel(){

        setLayout(
                new BorderLayout(10,10)
        );

        setBorder(BorderFactory.createEmptyBorder(
                10,
                10,
                10,
                10
                )
        );

        areaTexto = new JTextArea();

        areaTexto.setEditable(false);

        areaTexto.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        12
                )
        );

        JScrollPane scroll =
                new JScrollPane(
                        areaTexto
                );

        JButton btnActualizar =
                new JButton(
                        "Actualizar Resumen"
                );

        btnActualizar.addActionListener(e -> actualizarResumen()
        );

        add(scroll, BorderLayout.CENTER);

        add(btnActualizar, BorderLayout.SOUTH);

    }


    private void actualizarResumen(){

        StringBuilder sb = new StringBuilder();

        sb.append(
                "===== RESUMEN GENERAL =====\n\n"
        );

        int tours = 0;
        int personas = 0;
        int servicios = 0;

        for(Registrable entidad : gestor.getEntidades()){

            if(entidad instanceof Tour){

                sb.append("[TOUR] ");

                tours++;

            }else if(entidad instanceof Cliente){

                sb.append("[CLIENTE] ");

                personas++;

            }else if(entidad instanceof GuiaTuristico){

                sb.append("[GUÍA] ");

                personas++;

            }else if(entidad instanceof ServicioTuristico){

                sb.append("[SERVICIO] ");

                servicios++;

            }

            sb.append(entidad.mostrarResumen());

            sb.append("\n");

        }

        sb.append("\n===== ESTADÍSTICAS =====\n");


        sb.append("Tours registrados: ");

        sb.append(tours);

        sb.append("\n");

        sb.append("Personas registradas: ");

        sb.append(personas);

        sb.append("\n");

        sb.append("Servicios registrados: ");

        sb.append(servicios);

        sb.append("\n");

        sb.append("Total entidades: ");

        sb.append(gestor.cantidadEntidades());

        areaTexto.setText(sb.toString());

    }

}