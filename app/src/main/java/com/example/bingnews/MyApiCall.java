package com.example.bingnews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface MyApiCall {
    @GET("/v7.0/news/search")
    Call<DataModel> makeHttpRequest(@Query("q") String searchTerm, @Header("Ocp-Apim-Subscription-Key") String subscriptionKey);
}
