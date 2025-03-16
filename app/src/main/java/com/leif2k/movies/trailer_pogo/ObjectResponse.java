package com.leif2k.movies.trailer_pogo;

import com.google.gson.annotations.SerializedName;

public class ObjectResponse {

    @SerializedName("videos")
    private TrailersList trailersList;

    public ObjectResponse(TrailersList trailersList) {
        this.trailersList = trailersList;
    }

    public TrailersList getTrailersList() {
        return trailersList;
    }

    @Override
    public String toString() {
        return "ObjectResponse{" +
                "trailersList=" + trailersList +
                '}';
    }
}
