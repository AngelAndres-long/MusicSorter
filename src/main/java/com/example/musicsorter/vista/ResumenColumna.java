package com.example.musicsorter.vista;

import com.example.musicsorter.modelo.Cancion;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.List;

public class ResumenColumna{

    public static VBox crear(List<Cancion> canciones,String columna){
        VBox caja=new VBox(4);
        if(esNumerica(columna)){
            double[] valores=obtenerNumericos(canciones,columna);
            caja.getChildren().add(hacerHistograma(valores));
        }else if(esBooleana(columna)){
            caja.getChildren().add(hacerPastel(canciones));
        }else{
            caja.getChildren().add(hacerResumenTexto(canciones,columna));
        }
        return caja;
    }

    public static boolean esNumerica(String columna){
        return columna.equals("danceability")||columna.equals("energy")||columna.equals("key")||
                columna.equals("loudness")||columna.equals("mode")||columna.equals("speechiness")||
                columna.equals("acousticness")||columna.equals("instrumentalness")||columna.equals("liveness")||
                columna.equals("valence")||columna.equals("tempo")||columna.equals("time_signature")||
                columna.equals("spotify_track_popularity")||columna.equals("peak_position")||
                columna.equals("weeks_on_chart")||columna.equals("confidence_score")||
                columna.equals("spotify_track_duration_ms");
    }

    public static boolean esBooleana(String columna){
        return columna.equals("spotify_track_explicit");
    }

    public static double[] obtenerNumericos(List<Cancion> canciones,String columna){
        double[] valores=new double[canciones.size()];
        for(int i=0;i<canciones.size();i++){
            valores[i]=obtenerNumerico(canciones.get(i),columna);
        }
        return valores;
    }

    public static double obtenerNumerico(Cancion c,String col){
        switch(col){
            case "danceability": return c.getDanceability();
            case "energy": return c.getEnergy();
            case "key": return c.getKey();
            case "loudness": return c.getLoudness();
            case "mode": return c.getMode();
            case "speechiness": return c.getSpeechiness();
            case "acousticness": return c.getAcousticness();
            case "instrumentalness": return c.getInstrumentalness();
            case "liveness": return c.getLiveness();
            case "valence": return c.getValence();
            case "tempo": return c.getTempo();
            case "time_signature": return c.getTimeSignature();
            case "spotify_track_popularity": return c.getPopularity();
            case "peak_position": return c.getPeakPosition();
            case "weeks_on_chart": return c.getWeeksOnChart();
            case "confidence_score": return c.getConfidence();
            case "spotify_track_duration_ms": return c.getDuration();
            default: return 0;
        }
    }

    static Node hacerHistograma(double[] valores){
        if(valores.length==0) return new Label("");
        double min=valores[0],max=valores[0];
        for(double v:valores){
            if(v<min) min=v;
            if(v>max) max=v;
        }
        int cajas=15;
        int[] cuenta=new int[cajas];
        double ancho=(max-min)/cajas;
        for(double v:valores){
            int indice=ancho>0?(int)((v-min)/ancho):0;
            if(indice>=cajas) indice=cajas-1;
            cuenta[indice]++;
        }
        int mayor=0;
        for(int c:cuenta) if(c>mayor) mayor=c;

        HBox barras=new HBox(1);
        barras.setAlignment(Pos.BOTTOM_LEFT);
        barras.setPrefHeight(40);
        for(int i=0;i<cajas;i++){
            double alto=mayor>0?40.0*cuenta[i]/mayor:0;
            if(cuenta[i]>0&&alto<1) alto=1;
            Rectangle barra=new Rectangle(5,alto);
            barra.setFill(Color.web("#4285f4"));
            barras.getChildren().add(barra);
        }
        Label limites=new Label(String.format("%.1f a %.1f",min,max));
        limites.setStyle("-fx-font-size:9px;");
        return new VBox(1,barras,limites);
    }

    static Node hacerPastel(List<Cancion> canciones){
        int verdaderos=0,falsos=0;
        for(Cancion c:canciones){
            if(c.isExplicit()) verdaderos++;
            else falsos++;
        }
        PieChart pastel=new PieChart();
        PieChart.Data trueData=new PieChart.Data("true",verdaderos);
        PieChart.Data falseData=new PieChart.Data("false",falsos);
        pastel.getData().addAll(trueData,falseData);
        pastel.setLabelsVisible(false);
        pastel.setLegendVisible(false);
        pastel.setAnimated(false);
        pastel.setPrefSize(60,50);
        pastel.setMaxSize(60,50);

        pastel.applyCss();
        pastel.layout();
        if(trueData.getNode()!=null) trueData.getNode().setStyle("-fx-pie-color:#1a73e8;");
        if(falseData.getNode()!=null) falseData.getNode().setStyle("-fx-pie-color:#8ab4f8;");

        return pastel;
    }

    static Node hacerResumenTexto(List<Cancion> canciones,String columna){
        HashMap<String,Integer> cuentas=new HashMap<>();
        for(Cancion c:canciones){
            String valor=obtenerTexto(c,columna);
            if(valor==null||valor.isEmpty()) valor="[null]";
            cuentas.put(valor,cuentas.getOrDefault(valor,0)+1);
        }
        String primero="",segundo="";
        int cuentaPrimero=0,cuentaSegundo=0;
        for(String valor:cuentas.keySet()){
            int cuenta=cuentas.get(valor);
            if(cuenta>cuentaPrimero){
                segundo=primero;
                cuentaSegundo=cuentaPrimero;
                primero=valor;
                cuentaPrimero=cuenta;
            }else if(cuenta>cuentaSegundo){
                segundo=valor;
                cuentaSegundo=cuenta;
            }
        }
        int total=canciones.size();
        VBox caja=new VBox(1);
        caja.setPrefHeight(45);
        caja.getChildren().add(hacerLinea(primero,cuentaPrimero,total));
        if(!segundo.isEmpty()) caja.getChildren().add(hacerLinea(segundo,cuentaSegundo,total));
        if(cuentas.size()>2){
            Label mas=new Label("+"+(cuentas.size()-2)+" más");
            mas.setStyle("-fx-font-size:8px; -fx-text-fill:#666;");
            caja.getChildren().add(mas);
        }
        return caja;
    }

    static String obtenerTexto(Cancion c,String col){
        switch(col){
            case "song_id": return c.getSongId();
            case "performer": return c.getPerformer();
            case "song": return c.getSong();
            case "spotify_genre": return c.getGenre();
            case "spotify_track_album": return c.getAlbum();
            case "chart_success_tier": return c.getChartTier();
            case "hit_longevity_profile": return c.getLongevity();
            case "acoustic_mood_cluster": return c.getMood();
            case "musical_era": return c.getEra();
            default: return "";
        }
    }

    static Label hacerLinea(String texto,int cantidad,int total){
        if(texto.length()>12) texto=texto.substring(0,12)+"...";
        int porcentaje=Math.round(100f*cantidad/total);
        Label linea=new Label(texto+" "+porcentaje+"%");
        linea.setStyle("-fx-font-size:9px;");
        return linea;
    }
}