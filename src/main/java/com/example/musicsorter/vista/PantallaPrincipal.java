package com.example.musicsorter.vista;

import com.example.musicsorter.controlador.ManejadorDatos;
import com.example.musicsorter.modelo.Cancion;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Comparator;

/**
 * Pantalla principal de la aplicación.
 */
public class PantallaPrincipal extends BorderPane{

    private ManejadorDatos manejador;
    private TableView<Cancion> tabla;
    private TextArea resultados;

    public PantallaPrincipal(){
        manejador=new ManejadorDatos();

        //Tabla de canciones
        tabla=new TableView<>();
        TableColumn<Cancion,String> colCancion=new TableColumn<>("Canción");
        colCancion.setCellValueFactory(c->new javafx.beans.property.SimpleStringProperty(c.getValue().getCancion()));
        TableColumn<Cancion,String> colArtista=new TableColumn<>("Artista");
        colArtista.setCellValueFactory(c->new javafx.beans.property.SimpleStringProperty(c.getValue().getArtista()));
        TableColumn<Cancion,Double> colPop=new TableColumn<>("Popularidad");
        colPop.setCellValueFactory(c->new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getPopularidad()));

        tabla.getColumns().addAll(colCancion,colArtista,colPop);
        tabla.getItems().addAll(manejador.getCanciones());
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Panel de controles
        VBox controles=new VBox(15);
        controles.setPadding(new Insets(20));
        controles.setStyle("-fx-background-color:#1e1e1e;");
        controles.setPrefWidth(280);

        Label titulo=new Label("Algoritmos de Ordenamiento");
        titulo.setStyle("-fx-text-fill:white; -fx-font-size:16px; -fx-font-weight:bold;");

        ComboBox<String> comboAlgoritmo=new ComboBox<>();
        comboAlgoritmo.getItems().addAll(manejador.getAlgoritmos());
        comboAlgoritmo.setPromptText("Selecciona algoritmo");
        comboAlgoritmo.setPrefWidth(240);

        ComboBox<String> comboCriterio=new ComboBox<>();
        comboCriterio.getItems().addAll("Popularidad","Danceability","Energy","Tempo");
        comboCriterio.setPromptText("Ordenar por");
        comboCriterio.setPrefWidth(240);

        Button btnOrdenar=new Button("Ordenar y medir tiempo");
        btnOrdenar.setPrefWidth(240);
        btnOrdenar.setStyle("-fx-background-color:#3a3a3a; -fx-text-fill:white;");

        resultados=new TextArea();
        resultados.setEditable(false);
        resultados.setPrefHeight(200);
        resultados.setStyle("-fx-control-inner-background:#2a2a2a; -fx-text-fill:white;");

        btnOrdenar.setOnAction(e->{
            String algoritmo=comboAlgoritmo.getValue();
            String criterio=comboCriterio.getValue();
            if(algoritmo==null||criterio==null){
                resultados.setText("Selecciona un algoritmo y un criterio.");
                return;
            }

            Comparator<Cancion> comparador=switch(criterio){
                case "Danceability"->Comparator.comparingDouble(Cancion::getDanceability);
                case "Energy"->Comparator.comparingDouble(Cancion::getEnergy);
                case "Tempo"->Comparator.comparingDouble(Cancion::getTempo);
                default->Comparator.comparingDouble(Cancion::getPopularidad);
            };

            long tiempo=manejador.medirTiempo(algoritmo,comparador);
            resultados.setText("Algoritmo: "+algoritmo+"\nCriterio: "+criterio+"\nTiempo: "+tiempo+" ms");
        });

        controles.getChildren().addAll(titulo,comboAlgoritmo,comboCriterio,btnOrdenar,new Label("Resultados:"),resultados);
        controles.setAlignment(Pos.TOP_CENTER);

        this.setCenter(tabla);
        this.setRight(controles);
        this.setStyle("-fx-background-color:#121212;");
    }
}