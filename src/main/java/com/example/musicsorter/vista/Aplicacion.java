package com.example.musicsorter.vista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación.
 */
public class Aplicacion extends Application{

    @Override
    public void start(Stage stage){
        PantallaPrincipal pantalla=new PantallaPrincipal();
        Scene scene=new Scene(pantalla,1100,700);
        stage.setTitle("Music Sorter - Práctica 6");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}