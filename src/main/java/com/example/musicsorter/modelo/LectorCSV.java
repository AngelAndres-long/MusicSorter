package com.example.musicsorter.modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Lee el archivo CSV y carga las canciones.
 */
public class LectorCSV{

    public static List<Cancion> cargarCanciones(){
        List<Cancion> canciones=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new InputStreamReader(
                LectorCSV.class.getResourceAsStream("/datos/billboard_hot_100_spotify_audio_features_enriched.csv")))){

            String linea;
            br.readLine();//Saltar encabezado
            while((linea=br.readLine())!=null){
                String[] columnas=linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if(columnas.length>=22){
                    try{
                        String artista=columnas[1].trim();
                        String cancion=columnas[2].trim();
                        double danceability=columnas[9].trim().isEmpty()?0:Double.parseDouble(columnas[9].trim());
                        double energy=columnas[10].trim().isEmpty()?0:Double.parseDouble(columnas[10].trim());
                        double tempo=columnas[19].trim().isEmpty()?0:Double.parseDouble(columnas[19].trim());
                        double popularidad=columnas[21].trim().isEmpty()?0:Double.parseDouble(columnas[21].trim());

                        canciones.add(new Cancion(cancion,artista,popularidad,danceability,energy,tempo));
                    }catch(Exception e){
                        //Fila inválida, se omite
                    }
                }
            }
        }catch(Exception e){
            System.err.println("Error al leer el CSV: "+e.getMessage());
        }
        return canciones;
    }
}