package com.example.musicsorter.vista;

import com.example.musicsorter.controlador.ManejadorDatos;
import com.example.musicsorter.modelo.Cancion;
import com.example.musicsorter.modelo.Ordenamientos;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PantallaPrincipal extends BorderPane{

    private ManejadorDatos manejador;
    private TableView<Cancion> tabla;
    private CheckBox[] checks;
    private String[] algoritmos={"QuickSort","ShellSort","SeleccionDirecta","MergeSort","Arrays.sort","Arrays.parallelSort"};
    private String columnaSeleccionada="spotify_track_popularity";
    private VBox headerSeleccionado=null;
    private Label lblSeleccionado=null;
    private List<Cancion> ordenOriginal;

    public PantallaPrincipal(){
        manejador=new ManejadorDatos();
        ordenOriginal=new ArrayList<>(manejador.getCanciones());

        HBox barraSuperior=new HBox(12);
        barraSuperior.setPadding(new Insets(10));
        barraSuperior.setAlignment(Pos.CENTER_LEFT);
        barraSuperior.setStyle("-fx-background-color:#1a1a2e;");

        Label lblTitulo=new Label("Music Sorter");
        lblTitulo.setStyle("-fx-text-fill:white; -fx-font-size:16; -fx-font-weight:bold;");

        HBox checksBox=new HBox(8);
        checksBox.setAlignment(Pos.CENTER);
        checks=new CheckBox[algoritmos.length];
        for(int i=0;i<algoritmos.length;i++){
            checks[i]=new CheckBox(algoritmos[i]);
            checks[i].setStyle("-fx-text-fill:white;");
            checks[i].setSelected(true);
            checksBox.getChildren().add(checks[i]);
        }

        Button btnOrdenarMedir=new Button("Ordenar y medir");
        btnOrdenarMedir.setStyle("-fx-background-color:#0f3460; -fx-text-fill:white; -fx-padding:6 14;");
        btnOrdenarMedir.setOnAction(e->ordenarYMedir());

        Button btnRestaurar=new Button("Restaurar");
        btnRestaurar.setStyle("-fx-background-color:#2d6a4f; -fx-text-fill:white; -fx-padding:6 14;");
        btnRestaurar.setOnAction(e->restaurar());

        barraSuperior.getChildren().addAll(lblTitulo,checksBox,btnOrdenarMedir,btnRestaurar);

        tabla=new TableView<>();
        tabla.setStyle("-fx-background-color:#0f0f23;");
        crearColumnas();
        tabla.getItems().addAll(manejador.getCanciones());

        Label lblEstado=new Label("Canciones: "+manejador.getCanciones().size()+" | Columna: "+columnaSeleccionada);
        lblEstado.setMaxWidth(Double.MAX_VALUE);
        lblEstado.setStyle("-fx-background-color:#16213e; -fx-text-fill:white; -fx-padding:8;");

        this.setTop(barraSuperior);
        this.setCenter(tabla);
        this.setBottom(lblEstado);
        this.setStyle("-fx-background-color:#0f0f23;");
    }

    private void restaurar(){
        tabla.getItems().clear();
        tabla.getItems().addAll(ordenOriginal);
        actualizarEstado("Orden original restaurado");
    }

    private void crearColumnas(){
        String[] nombres={"song_id","performer","song","spotify_genre","spotify_track_id","spotify_track_preview_url","spotify_track_duration_ms","spotify_track_explicit","spotify_track_album","danceability","energy","key","loudness","mode","speechiness","acousticness","instrumentalness","liveness","valence","tempo","time_signature","spotify_track_popularity","peak_position","weeks_on_chart","first_week","last_week","chart_success_tier","hit_longevity_profile","acoustic_mood_cluster","musical_era","evidence_span","cot_rationale","confidence_score"};

        for(String nombre:nombres){
            TableColumn<Cancion,String> col=new TableColumn<>();
            col.setPrefWidth(140);
            col.setSortable(false);

            VBox header=new VBox(2);
            header.setAlignment(Pos.CENTER);
            header.setStyle("-fx-cursor:hand; -fx-padding:4;");

            Label lblNombre=new Label(nombre);
            lblNombre.setStyle("-fx-font-weight:bold; -fx-font-size:10px;");

            VBox zonaGrafica=ResumenColumna.crear(manejador.getCanciones(),nombre);
            zonaGrafica.setStyle("-fx-cursor:hand; -fx-background-color:rgba(66,133,244,0.15); -fx-background-radius:4;");
            zonaGrafica.setPadding(new Insets(2));

            final String n=nombre;
            final VBox headerRef=header;
            final Label lblRef=lblNombre;

            zonaGrafica.setOnMouseClicked(e->{
                e.consume();
                mostrarZoomGrafica(n);
            });

            header.setOnMouseClicked(e->{
                columnaSeleccionada=n;
                if(headerSeleccionado!=null){
                    headerSeleccionado.setStyle("-fx-cursor:hand; -fx-padding:4;");
                    lblSeleccionado.setStyle("-fx-font-weight:bold; -fx-font-size:10px;");
                }
                headerRef.setStyle("-fx-cursor:hand; -fx-padding:4; -fx-background-color:#1a73e8;");
                lblRef.setStyle("-fx-font-weight:bold; -fx-font-size:10px; -fx-text-fill:white;");
                headerSeleccionado=headerRef;
                lblSeleccionado=lblRef;
                actualizarEstado("Columna: "+columnaSeleccionada);
            });

            header.getChildren().addAll(lblNombre,zonaGrafica);
            col.setGraphic(header);
            col.setCellValueFactory(c->new SimpleStringProperty(obtenerValor(c.getValue(),n)));
            tabla.getColumns().add(col);
        }
    }

    private void actualizarEstado(String texto){
        Label lbl=((Label)this.getBottom());
        lbl.setText("Canciones: "+manejador.getCanciones().size()+" | "+texto);
    }

    private void mostrarZoomGrafica(String columna){
        Stage stage=new Stage();
        stage.setTitle("Distribución de "+columna);

        VBox contenido=new VBox(15);
        contenido.setPadding(new Insets(20));
        contenido.setAlignment(Pos.CENTER);
        contenido.setStyle("-fx-background-color:#1a1a2e;");

        Label titulo=new Label(columna);
        titulo.setStyle("-fx-text-fill:white; -fx-font-size:18; -fx-font-weight:bold;");

        Node graficaGrande;
        if(ResumenColumna.esNumerica(columna)){
            double[] valores=ResumenColumna.obtenerNumericos(manejador.getCanciones(),columna);
            graficaGrande=crearHistogramaGrande(valores);
        }else if(ResumenColumna.esBooleana(columna)){
            graficaGrande=crearPastelGrande();
        }else{
            graficaGrande=crearResumenTextoGrande(columna);
        }

        contenido.getChildren().addAll(titulo,graficaGrande);

        Scene scene=new Scene(contenido,700,500);
        scene.getStylesheets().add("data:text/css,.chart-pie-label{-fx-fill:white;}");
        stage.setScene(scene);
        stage.show();
    }

    private VBox crearResumenTextoGrande(String columna){
        VBox caja=new VBox(5);
        caja.setAlignment(Pos.CENTER);
        caja.setStyle("-fx-background-color:#16213e; -fx-padding:15; -fx-background-radius:8;");

        java.util.HashMap<String,Integer> cuentas=new java.util.HashMap<>();
        for(Cancion c:manejador.getCanciones()){
            String valor="";
            switch(columna){
                case "performer": valor=c.getPerformer(); break;
                case "song": valor=c.getSong(); break;
                case "spotify_genre": valor=c.getGenre(); break;
                case "spotify_track_album": valor=c.getAlbum(); break;
                case "chart_success_tier": valor=c.getChartTier(); break;
                case "hit_longevity_profile": valor=c.getLongevity(); break;
                case "acoustic_mood_cluster": valor=c.getMood(); break;
                case "musical_era": valor=c.getEra(); break;
                default: valor=c.getSongId(); break;
            }
            if(valor==null||valor.isEmpty()) valor="[null]";
            cuentas.put(valor,cuentas.getOrDefault(valor,0)+1);
        }

        java.util.List<java.util.Map.Entry<String,Integer>> lista=new java.util.ArrayList<>(cuentas.entrySet());
        lista.sort((a,b)->b.getValue()-a.getValue());

        int total=manejador.getCanciones().size();
        int mostrar=Math.min(10,lista.size());
        for(int i=0;i<mostrar;i++){
            String texto=lista.get(i).getKey();
            if(texto.length()>25) texto=texto.substring(0,25)+"...";
            int pct=Math.round(100f*lista.get(i).getValue()/total);
            Label lbl=new Label(texto+" - "+lista.get(i).getValue()+" ("+pct+"%)");
            lbl.setStyle("-fx-text-fill:white; -fx-font-size:13;");
            caja.getChildren().add(lbl);
        }
        if(lista.size()>10){
            Label mas=new Label("... y "+(lista.size()-10)+" más");
            mas.setStyle("-fx-text-fill:#aaa; -fx-font-size:12;");
            caja.getChildren().add(mas);
        }
        return caja;
    }

    private Node crearHistogramaGrande(double[] valores){
        if(valores.length==0) return new Label("Sin datos");
        double min=valores[0],max=valores[0];
        for(double v:valores){
            if(v<min) min=v;
            if(v>max) max=v;
        }
        int cajas=20;
        int[] cuenta=new int[cajas];
        double ancho=(max-min)/cajas;
        for(double v:valores){
            int indice=ancho>0?(int)((v-min)/ancho):0;
            if(indice>=cajas) indice=cajas-1;
            cuenta[indice]++;
        }
        int mayor=0;
        for(int c:cuenta) if(c>mayor) mayor=c;

        HBox barras=new HBox(2);
        barras.setAlignment(Pos.BOTTOM_CENTER);
        barras.setPrefHeight(200);
        for(int i=0;i<cajas;i++){
            double alto=mayor>0?200.0*cuenta[i]/mayor:0;
            if(cuenta[i]>0&&alto<2) alto=2;
            Rectangle barra=new Rectangle(25,alto);
            barra.setFill(Color.web("#4285f4"));
            barras.getChildren().add(barra);
        }
        Label limites=new Label(String.format("Min: %.2f   Max: %.2f",min,max));
        limites.setStyle("-fx-text-fill:white; -fx-font-size:14;");
        return new VBox(10,barras,limites);
    }

    private Node crearPastelGrande(){
        int verdaderos=0,falsos=0;
        for(Cancion c:manejador.getCanciones()){
            if(c.isExplicit()) verdaderos++;
            else falsos++;
        }
        PieChart pastel=new PieChart();
        PieChart.Data trueData=new PieChart.Data("true ("+verdaderos+")",verdaderos);
        PieChart.Data falseData=new PieChart.Data("false ("+falsos+")",falsos);
        pastel.getData().addAll(trueData,falseData);
        pastel.setLabelsVisible(true);
        pastel.setLegendVisible(true);
        pastel.setPrefSize(400,350);

        pastel.applyCss();
        pastel.layout();
        if(trueData.getNode()!=null) trueData.getNode().setStyle("-fx-pie-color:#1a73e8;");
        if(falseData.getNode()!=null) falseData.getNode().setStyle("-fx-pie-color:#8ab4f8;");

        return pastel;
    }

    private String obtenerValor(Cancion c,String col){
        switch(col){
            case "song_id": return c.getSongId();
            case "performer": return c.getPerformer();
            case "song": return c.getSong();
            case "spotify_genre": return c.getGenre();
            case "spotify_track_id": return c.getTrackId();
            case "spotify_track_preview_url": return c.getPreviewUrl();
            case "spotify_track_duration_ms": return String.format("%.0f",c.getDuration());
            case "spotify_track_explicit": return String.valueOf(c.isExplicit());
            case "spotify_track_album": return c.getAlbum();
            case "danceability": return String.format("%.2f",c.getDanceability());
            case "energy": return String.format("%.2f",c.getEnergy());
            case "key": return String.format("%.0f",c.getKey());
            case "loudness": return String.format("%.2f",c.getLoudness());
            case "mode": return String.format("%.0f",c.getMode());
            case "speechiness": return String.format("%.2f",c.getSpeechiness());
            case "acousticness": return String.format("%.2f",c.getAcousticness());
            case "instrumentalness": return String.format("%.4f",c.getInstrumentalness());
            case "liveness": return String.format("%.2f",c.getLiveness());
            case "valence": return String.format("%.2f",c.getValence());
            case "tempo": return String.format("%.2f",c.getTempo());
            case "time_signature": return String.format("%.0f",c.getTimeSignature());
            case "spotify_track_popularity": return String.format("%.0f",c.getPopularity());
            case "peak_position": return String.format("%.0f",c.getPeakPosition());
            case "weeks_on_chart": return String.format("%.0f",c.getWeeksOnChart());
            case "first_week": return c.getFirstWeek();
            case "last_week": return c.getLastWeek();
            case "chart_success_tier": return c.getChartTier();
            case "hit_longevity_profile": return c.getLongevity();
            case "acoustic_mood_cluster": return c.getMood();
            case "musical_era": return c.getEra();
            case "evidence_span": return c.getEvidence();
            case "cot_rationale": return c.getRationale();
            case "confidence_score": return String.format("%.2f",c.getConfidence());
            default: return "";
        }
    }

    private double obtenerNumerico(Cancion c,String col){
        switch(col){
            case "spotify_track_duration_ms": return c.getDuration();
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
            default: return 0;
        }
    }

    private void ordenarYMedir(){
        if(columnaSeleccionada==null){
            mostrarAlerta("Selecciona una columna (clic en el nombre)");
            return;
        }

        boolean alguno=false;
        for(CheckBox check:checks){
            if(check.isSelected()) alguno=true;
        }
        if(!alguno){
            mostrarAlerta("Selecciona al menos un algoritmo");
            return;
        }

        int totalReal=manejador.getCanciones().size();
        Cancion[] muestra=manejador.getCanciones().toArray(new Cancion[0]);
        Comparator<Cancion> comp=Comparator.comparingDouble(c->obtenerNumerico(c,columnaSeleccionada));

        Cancion[] ordenada=muestra.clone();
        Ordenamientos.quickSort(ordenada,0,ordenada.length-1,comp);
        tabla.getItems().clear();
        tabla.getItems().addAll(ordenada);
        actualizarEstado("Ordenado por "+columnaSeleccionada+" ("+totalReal+" canciones)");

        CategoryAxis xAxis=new CategoryAxis();
        NumberAxis yAxis=new NumberAxis();
        yAxis.setLabel("Tiempo (ms)");
        BarChart<String,Number> chart=new BarChart<>(xAxis,yAxis);
        chart.setTitle("Comparación ordenando por "+columnaSeleccionada);
        chart.setLegendVisible(false);
        chart.setPrefHeight(300);

        XYChart.Series<String,Number> serie=new XYChart.Series<>();
        StringBuilder sb=new StringBuilder();
        sb.append("Resultados para: ").append(columnaSeleccionada).append("\n");
        sb.append("Total: ").append(totalReal).append(" canciones\n\n");
        sb.append(String.format("%-20s %15s %12s\n","Método","Nanosegundos","Milisegundos"));
        sb.append("\n");

        for(int i=0;i<checks.length;i++){
            if(checks[i].isSelected()){
                long nano=medirTiempoNano(algoritmos[i],comp,muestra);
                double ms=nano/1_000_000.0;
                serie.getData().add(new XYChart.Data<>(algoritmos[i],ms));
                sb.append(String.format("%-20s %15d %12.2f\n",algoritmos[i],nano,ms));
            }
        }

        chart.getData().add(serie);

        TextArea area=new TextArea(sb.toString());
        area.setEditable(false);
        area.setStyle("-fx-font-family:monospace; -fx-font-size:13;");
        area.setPrefHeight(180);

        VBox box=new VBox(10,chart,area);
        box.setPadding(new Insets(15));
        VBox.setVgrow(chart,Priority.ALWAYS);

        Stage stage=new Stage();
        stage.setTitle("Tiempos - "+columnaSeleccionada);
        stage.setScene(new Scene(box,700,550));
        stage.show();
    }

    private long medirTiempoNano(String algoritmo,Comparator<Cancion> comparador,Cancion[] datos){
        Cancion[] copia=datos.clone();
        long inicio=System.nanoTime();
        switch(algoritmo){
            case "QuickSort":
                Ordenamientos.quickSort(copia,0,copia.length-1,comparador);
                break;
            case "ShellSort":
                Ordenamientos.shellSort(copia,comparador);
                break;
            case "SeleccionDirecta":
                Ordenamientos.seleccionDirecta(copia,comparador);
                break;
            case "MergeSort":
                Ordenamientos.mergeSort(copia,0,copia.length-1,comparador);
                break;
            case "Arrays.sort":
                java.util.Arrays.sort(copia,comparador);
                break;
            case "Arrays.parallelSort":
                java.util.Arrays.parallelSort(copia,comparador);
                break;
        }
        return System.nanoTime()-inicio;
    }

    private void mostrarAlerta(String mensaje){
        Alert alert=new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}