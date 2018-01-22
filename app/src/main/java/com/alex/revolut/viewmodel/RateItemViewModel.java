package com.alex.revolut.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.alex.revolut.BR;
import com.alex.revolut.model.Rate;
import com.alex.revolut.ui.MainActivityCallback;
import com.alex.revolut.utils.Utils;

import java.util.Currency;

public class RateItemViewModel extends BaseObservable {
    private static final double DEFAULT_AMOUNT = 100;
    @NonNull
    private final Rate rate;
    @NonNull
    private final MainActivityCallback callback;
    private double amount;

    public RateItemViewModel(@NonNull Rate rate, @NonNull MainActivityCallback callback) {
        this.callback = callback;
        this.rate = rate;
        this.amount = DEFAULT_AMOUNT;
    }

    @Bindable
    public String getRateValue() {
        return Utils.getValueFormat(rate.getValue() * amount);
    }

    @Bindable
    public String getRateCode() {
        return rate.getCode();
    }

    @Bindable
    public String getRateDescription() {
        return Currency.getInstance(rate.getCode()).getDisplayName();
    }

    @Bindable
    public int getFlagImage() {
        return Utils.getFlagSource(getRateCode());
    }

    public void setRateValue(String rateValue) {
        if (TextUtils.isEmpty(rateValue)) {
            callback.setRateValue("0");
        } else if (!rateValue.equals(getRateValue())) {
            callback.setRateValue(rateValue);
        }
    }

    @SuppressWarnings("unused")
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            callback.setSelectedRate(this);
        }
    }

    public void updateRateWithAmount(double amount) {
        this.amount = amount;
        notifyPropertyChanged(BR.rateValue);
    }

    public void updateRateWithNewOne(double newRate) {
        this.rate.setValue(newRate);
        notifyPropertyChanged(BR.rateValue);
    }

    public double getAmount() {
        return amount;
    }

    @NonNull
    public Rate getRate() {
        return rate;
    }

}
