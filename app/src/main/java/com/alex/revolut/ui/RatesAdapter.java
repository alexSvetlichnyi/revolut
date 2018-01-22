package com.alex.revolut.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alex.revolut.BR;
import com.alex.revolut.R;
import com.alex.revolut.model.Rate;
import com.alex.revolut.model.Rates;
import com.alex.revolut.viewmodel.RateItemViewModel;

import java.util.LinkedList;
import java.util.List;


public class RatesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final MainActivityCallback callback;
    @NonNull
    private final List<RateItemViewModel> items = new LinkedList<>();

    RatesAdapter(@NonNull MainActivityCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewDataBinding dataBinding = ((RateItemViewHolder) holder).dataBinding;
        dataBinding.setVariable(BR.viewModel, items.get(position));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewDataBinding binder = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.rate_item, parent, false);
            return new RateItemViewHolder(binder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setRates(@NonNull Rates rates) {
        if (items.isEmpty()) {
            createRates(rates);
        } else {
            updateRates(rates);
        }
    }

    private void updateRates(@NonNull Rates rates) {
        for (RateItemViewModel viewModel : items) {
            for (Rate rate : rates.getRates()) {
                if (viewModel.getRateCode().equals(rate.getCode())) {
                    viewModel.updateRateWithNewOne(rate.getValue());
                    break;
                }
            }
        }
    }

    private void createRates(@NonNull Rates rates) {
        for (Rate rate : rates.getRates()) {
            RateItemViewModel rateItemViewModel = new RateItemViewModel(rate, callback);
            items.add(rateItemViewModel);
        }
        notifyDataSetChanged();
    }

    public void setRateValue(String value) {
        for (int i = 0; i < items.size(); i++) {
            Double amount = Double.valueOf(value);
            items.get(i).updateRateWithAmount(amount);
        }
    }

    public void setSelectedRate(RateItemViewModel rate) {
        int oldIndex = items.indexOf(rate);
        if (oldIndex == 0) {
            return;
        }
        items.remove(rate);
        items.add(0, rate);
        notifyItemMoved(oldIndex, 0);
        double amount = rate.getRate().getValue() * rate.getAmount();
        for (int i = 0; i < items.size(); i++) {
            items.get(i).updateRateWithAmount(amount);
        }
    }

    public static class RateItemViewHolder extends RecyclerView.ViewHolder {

        final ViewDataBinding dataBinding;

        RateItemViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            this.dataBinding = dataBinding;
        }
    }
}
