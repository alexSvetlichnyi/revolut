package com.alex.revolut.repo;

import com.alex.revolut.model.Rates;

import io.reactivex.Observable;

public interface RateRepo {
   Observable<Rates> getRates();
   void setBaseRate(String baseRate);
}
