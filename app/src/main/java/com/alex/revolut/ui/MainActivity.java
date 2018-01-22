package com.alex.revolut.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.alex.revolut.R;
import com.alex.revolut.databinding.ActivityMainBinding;
import com.alex.revolut.repo.RateRepo;
import com.alex.revolut.repo.RateRepoImpl;
import com.alex.revolut.utils.Utils;
import com.alex.revolut.viewmodel.RateItemViewModel;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements MainActivityCallback {

    @NonNull
    private final RateRepo rateRepo = new RateRepoImpl();
    @NonNull
    private final RatesAdapter adapter = new RatesAdapter(this);
    @Nullable
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupRecycleView(binding);
    }

    @Override
    public void setSelectedRate(RateItemViewModel rate) {
        rateRepo.setBaseRate(rate.getRateCode());
        adapter.setSelectedRate(rate);
    }

    @Override
    public void setRateValue(String value) {
        adapter.setRateValue(value);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Utils.isDeviceOffline(this)) {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            return;
        }
        disposable = rateRepo.getRates().subscribe(adapter::setRates, throwable -> {
            //Do nothing, just log the error
            Log.d("Network", throwable.getLocalizedMessage());
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void setupRecycleView(ActivityMainBinding binding) {
        final RecyclerView recyclerView = binding.rvCurrencies;
        recyclerView.setAdapter(adapter);
    }
}
