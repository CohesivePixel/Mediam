package com.example.ben_e.mediam.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ben-e on 29-10-17.
 */

@IgnoreExtraProperties
public class MediaItem {

    public String mediaType;
    public String datum;
    public String startTijd;
    public String eindTijd;
    public String mening;

    public MediaItem(){
        // Default constructor
    }

    public MediaItem(String mediaType, String datum, String startTijd, String eindTijd, String mening){
        this.mediaType = mediaType;
        this.datum = datum;
        this.startTijd = startTijd;
        this.eindTijd = eindTijd;
        this.mening = mening;
    }
}
