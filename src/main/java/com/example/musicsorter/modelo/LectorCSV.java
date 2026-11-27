package com.example.musicsorter.modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Lee el archivo CSV completo (33 columnas).
 */
public class LectorCSV{

    public static List<Cancion> cargarCanciones(){
        List<Cancion> canciones=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new InputStreamReader(
                LectorCSV.class.getResourceAsStream("/datos/billboard_hot_100_spotify_audio_features_enriched.csv")))){

            String linea;
            br.readLine();
            while((linea=br.readLine())!=null){
                String[] c=linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if(c.length>=33){
                    try{
                        canciones.add(new Cancion(
                                c[0],c[1],c[2],c[3],c[4],c[5],
                                parseDouble(c[6]),Boolean.parseBoolean(c[7]),c[8],
                                parseDouble(c[9]),parseDouble(c[10]),parseDouble(c[11]),
                                parseDouble(c[12]),parseDouble(c[13]),parseDouble(c[14]),
                                parseDouble(c[15]),parseDouble(c[16]),parseDouble(c[17]),
                                parseDouble(c[18]),parseDouble(c[19]),parseDouble(c[20]),
                                parseDouble(c[21]),parseDouble(c[22]),parseDouble(c[23]),
                                c[24],c[25],c[26],c[27],c[28],c[29],c[30],c[31],parseDouble(c[32])
                        ));
                    }catch(Exception ignored){}
                }
            }
        }catch(Exception e){
            System.err.println("Error CSV: "+e.getMessage());
        }
        return canciones;
    }

    private static double parseDouble(String s){
        try{
            return Double.parseDouble(s.trim());
        }catch(Exception e){
            return 0;
        }
    }
}