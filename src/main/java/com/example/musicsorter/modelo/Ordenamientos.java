package com.example.musicsorter.modelo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Contiene los algoritmos de ordenamiento solicitados.
 */
public class Ordenamientos{

    //QuickSort
    public static void quickSort(Cancion[] arr,int bajo,int alto,Comparator<Cancion> comparador){
        if(bajo<alto){
            int pi=particion(arr,bajo,alto,comparador);
            quickSort(arr,bajo,pi-1,comparador);
            quickSort(arr,pi+1,alto,comparador);
        }
    }

    private static int particion(Cancion[] arr,int bajo,int alto,Comparator<Cancion> comparador){
        Cancion pivote=arr[alto];
        int i=bajo-1;
        for(int j=bajo;j<alto;j++){
            if(comparador.compare(arr[j],pivote)<=0){
                i++;
                intercambiar(arr,i,j);
            }
        }
        intercambiar(arr,i+1,alto);
        return i+1;
    }

    //Shell Sort
    public static void shellSort(Cancion[] arr,Comparator<Cancion> comparador){
        int n=arr.length;
        for(int gap=n/2;gap>0;gap/=2){
            for(int i=gap;i<n;i++){
                Cancion temp=arr[i];
                int j;
                for(j=i;j>=gap&&comparador.compare(arr[j-gap],temp)>0;j-=gap){
                    arr[j]=arr[j-gap];
                }
                arr[j]=temp;
            }
        }
    }

    //Selección Directa
    public static void seleccionDirecta(Cancion[] arr,Comparator<Cancion> comparador){
        int n=arr.length;
        for(int i=0;i<n-1;i++){
            int minIdx=i;
            for(int j=i+1;j<n;j++){
                if(comparador.compare(arr[j],arr[minIdx])<0){
                    minIdx=j;
                }
            }
            intercambiar(arr,i,minIdx);
        }
    }

    //MergeSort
    public static void mergeSort(Cancion[] arr,int izq,int der,Comparator<Cancion> comparador){
        if(izq<der){
            int medio=(izq+der)/2;
            mergeSort(arr,izq,medio,comparador);
            mergeSort(arr,medio+1,der,comparador);
            merge(arr,izq,medio,der,comparador);
        }
    }

    private static void merge(Cancion[] arr,int izq,int medio,int der,Comparator<Cancion> comparador){
        int n1=medio-izq+1;
        int n2=der-medio;
        Cancion[] L=new Cancion[n1];
        Cancion[] R=new Cancion[n2];

        System.arraycopy(arr,izq,L,0,n1);
        System.arraycopy(arr,medio+1,R,0,n2);

        int i=0,j=0,k=izq;
        while(i<n1&&j<n2){
            if(comparador.compare(L[i],R[j])<=0){
                arr[k]=L[i];
                i++;
            }else{
                arr[k]=R[j];
                j++;
            }
            k++;
        }
        while(i<n1){
            arr[k]=L[i];
            i++;
            k++;
        }
        while(j<n2){
            arr[k]=R[j];
            j++;
            k++;
        }
    }

    //Arrays.sort()
    public static void sortJava(Cancion[] arr,Comparator<Cancion> comparador){
        Arrays.sort(arr,comparador);
    }

    //Arrays.parallelSort()
    public static void parallelSortJava(Cancion[] arr,Comparator<Cancion> comparador){
        Arrays.parallelSort(arr,comparador);
    }

    private static void intercambiar(Cancion[] arr,int i,int j){
        Cancion temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }
}