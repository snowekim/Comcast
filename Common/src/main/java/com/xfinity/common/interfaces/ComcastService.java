package com.xfinity.common.interfaces;

import com.xfinity.common.beans.Topics;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ComcastService {

    /**
     * Method to get the topics for a given request
     * @param queryString The input string to make the request to the web service
     */
    @GET("?format=json")
    Call<Topics> getTopics(@Query("q") String queryString);

}
