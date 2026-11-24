package com.example.musicsorter.modelo;

/**
 * Representa una canción del dataset de Billboard.
 */
public class Cancion{

    private String cancion;
    private String artista;
    private double popularidad;
    private double danceability;
    private double energy;
    private double tempo;

    public Cancion(String cancion,String artista,double popularidad,double danceability,double energy,double tempo){
        this.cancion=cancion;
        this.artista=artista;
        this.popularidad=popularidad;
        this.danceability=danceability;
        this.energy=energy;
        this.tempo=tempo;
    }

    public String getCancion(){
        return cancion;
    }

    public String getArtista(){
        return artista;
    }

    public double getPopularidad(){
        return popularidad;
    }

    public double getDanceability(){
        return danceability;
    }

    public double getEnergy(){
        return energy;
    }

    public double getTempo(){
        return tempo;
    }

    @Override
    public String toString(){
        return cancion+" - "+artista+" (Pop: "+popularidad+")";
    }
}