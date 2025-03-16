package com.leif2k.movies.trailer_pogo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {

    @SerializedName("docs")
    private List<ObjectResponse> objectResponses;


    public TrailerResponse(List<ObjectResponse> objectResponses) {
        this.objectResponses = objectResponses;
    }


    public List<ObjectResponse> getObjectResponses() {
        return objectResponses;
    }


    @Override
    public String toString() {
        return "TrailerResponse{" +
                "objectResponses=" + objectResponses +
                '}';
    }
}
