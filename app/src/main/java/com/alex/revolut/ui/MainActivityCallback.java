package com.alex.revolut.ui;

import com.alex.revolut.viewmodel.RateItemViewModel;

public interface MainActivityCallback {
    void setSelectedRate(RateItemViewModel rate);
    void setRateValue(String value);
}
