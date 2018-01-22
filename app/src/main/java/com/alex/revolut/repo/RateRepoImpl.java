package com.alex.revolut.repo;

import android.support.annotation.NonNull;

import com.alex.revolut.model.Rates;
import com.alex.revolut.utils.Utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RateRepoImpl implements RateRepo {
    private static final int PERIOD = 1;
    @NonNull
    private final RateApi requestInterface;

    private String baseRate = "EUR";

    public RateRepoImpl() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);
        requestInterface = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(Utils.getDomainUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(Utils.getGson()))
                .build().create(RateApi.class);
    }

    @Override
    public Observable<Rates> getRates() {
        return Observable.interval(PERIOD, TimeUnit.SECONDS)
                .flatMap(n -> requestInterface.getRates(baseRate))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void setBaseRate(String baseRate) {
        this.baseRate = baseRate;
    }
}
