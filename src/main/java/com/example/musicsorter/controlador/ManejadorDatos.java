package com.example.musicsorter.controlador;

import com.example.musicsorter.modelo.Cancion;
import com.example.musicsorter.modelo.LectorCSV;
import com.example.musicsorter.modelo.Ordenamientos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Maneja la carga de datos y la medición de tiempos de ordenamiento.
 */
public class ManejadorDatos{

    private List<Cancion> canciones;

    public ManejadorDatos(){
        canciones=LectorCSV.cargarCanciones();
    }

    public List<Cancion> getCanciones(){
        return canciones;
    }

    //Mide el tiempo de un algoritmo de ordenamiento
    public long medirTiempo(String algoritmo,Comparator<Cancion> comparador){
        Cancion[] copia=canciones.toArray(new Cancion[0]);
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
                Ordenamientos.sortJava(copia,comparador);
                break;
            case "Arrays.parallelSort":
                Ordenamientos.parallelSortJava(copia,comparador);
                break;
        }

        long fin=System.nanoTime();
        return (fin-inicio)/1_000_000;//Milisegundos
    }

    public List<String> getAlgoritmos(){
        List<String> lista=new ArrayList<>();
        lista.add("QuickSort");
        lista.add("ShellSort");
        lista.add("SeleccionDirecta");
        lista.add("MergeSort");
        lista.add("Arrays.sort");
        lista.add("Arrays.parallelSort");
        return lista;
    }
}