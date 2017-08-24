package com.test.teravin.latihanteravin.model.api;


import com.test.teravin.latihanteravin.model.api.pojo.Example;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by TEAM on 24/08/2017.
 * Happy Coding
 */

public interface EndpointRequest {

    @GET("/3/movie/popular")
    Observable<Example> getDataPopular(@Query("api_key") String api_key,
                                       @Query("language") String language,
                                       @Query("page") int page);






}
