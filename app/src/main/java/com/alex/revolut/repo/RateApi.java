package com.alex.revolut.repo;


import com.alex.revolut.model.Rates;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface RateApi {
   @GET("/latest")
   Observable<Rates> getRates(@Query("base") String baseRate);
}
