package com.alex.revolut.model;

import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

public class Rates {
    private String base;
    private String date;
    @NonNull
    private List<Rate> ratesArray = new LinkedList<>();

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @NonNull
    public List<Rate> getRates() {
        return ratesArray;
    }

    public void setRates(@NonNull List<Rate> rates) {
        this.ratesArray = rates;
    }
}
