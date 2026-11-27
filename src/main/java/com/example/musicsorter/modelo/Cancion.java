package com.example.musicsorter.modelo;

/**
 * Representa una canción del dataset de Billboard.
 */
public class Cancion{

    private String songId;
    private String performer;
    private String song;
    private String genre;
    private String trackId;
    private String previewUrl;
    private double duration;
    private boolean explicit;
    private String album;
    private double danceability;
    private double energy;
    private double key;
    private double loudness;
    private double mode;
    private double speechiness;
    private double acousticness;
    private double instrumentalness;
    private double liveness;
    private double valence;
    private double tempo;
    private double timeSignature;
    private double popularity;
    private double peakPosition;
    private double weeksOnChart;
    private String firstWeek;
    private String lastWeek;
    private String chartTier;
    private String longevity;
    private String mood;
    private String era;
    private String evidence;
    private String rationale;
    private double confidence;

    public Cancion(String songId,String performer,String song,String genre,String trackId,
                   String previewUrl,double duration,boolean explicit,String album,
                   double danceability,double energy,double key,double loudness,double mode,
                   double speechiness,double acousticness,double instrumentalness,double liveness,
                   double valence,double tempo,double timeSignature,double popularity,
                   double peakPosition,double weeksOnChart,String firstWeek,String lastWeek,
                   String chartTier,String longevity,String mood,String era,
                   String evidence,String rationale,double confidence){
        this.songId=songId;
        this.performer=performer;
        this.song=song;
        this.genre=genre;
        this.trackId=trackId;
        this.previewUrl=previewUrl;
        this.duration=duration;
        this.explicit=explicit;
        this.album=album;
        this.danceability=danceability;
        this.energy=energy;
        this.key=key;
        this.loudness=loudness;
        this.mode=mode;
        this.speechiness=speechiness;
        this.acousticness=acousticness;
        this.instrumentalness=instrumentalness;
        this.liveness=liveness;
        this.valence=valence;
        this.tempo=tempo;
        this.timeSignature=timeSignature;
        this.popularity=popularity;
        this.peakPosition=peakPosition;
        this.weeksOnChart=weeksOnChart;
        this.firstWeek=firstWeek;
        this.lastWeek=lastWeek;
        this.chartTier=chartTier;
        this.longevity=longevity;
        this.mood=mood;
        this.era=era;
        this.evidence=evidence;
        this.rationale=rationale;
        this.confidence=confidence;
    }

    public String getSongId(){
        return songId;
    }

    public String getPerformer(){
        return performer;
    }

    public String getSong(){
        return song;
    }

    public String getGenre(){
        return genre;
    }

    public String getTrackId(){
        return trackId;
    }

    public String getPreviewUrl(){
        return previewUrl;
    }

    public double getDuration(){
        return duration;
    }

    public boolean isExplicit(){
        return explicit;
    }

    public String getAlbum(){
        return album;
    }

    public double getDanceability(){
        return danceability;
    }

    public double getEnergy(){
        return energy;
    }

    public double getKey(){
        return key;
    }

    public double getLoudness(){
        return loudness;
    }

    public double getMode(){
        return mode;
    }

    public double getSpeechiness(){
        return speechiness;
    }

    public double getAcousticness(){
        return acousticness;
    }

    public double getInstrumentalness(){
        return instrumentalness;
    }

    public double getLiveness(){
        return liveness;
    }

    public double getValence(){
        return valence;
    }

    public double getTempo(){
        return tempo;
    }

    public double getTimeSignature(){
        return timeSignature;
    }

    public double getPopularity(){
        return popularity;
    }

    public double getPeakPosition(){
        return peakPosition;
    }

    public double getWeeksOnChart(){
        return weeksOnChart;
    }

    public String getFirstWeek(){
        return firstWeek;
    }

    public String getLastWeek(){
        return lastWeek;
    }

    public String getChartTier(){
        return chartTier;
    }

    public String getLongevity(){
        return longevity;
    }

    public String getMood(){
        return mood;
    }

    public String getEra(){
        return era;
    }

    public String getEvidence(){
        return evidence;
    }

    public String getRationale(){
        return rationale;
    }

    public double getConfidence(){
        return confidence;
    }
}