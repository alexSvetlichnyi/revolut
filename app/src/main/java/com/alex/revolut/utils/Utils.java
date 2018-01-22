package com.alex.revolut.utils;


import android.content.Context;
import android.databinding.BindingAdapter;
import android.net.ConnectivityManager;
import android.widget.ImageView;

import com.alex.revolut.R;
import com.alex.revolut.model.Rates;
import com.alex.revolut.repo.RatesDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    private static final Map<String, Integer> myMap = Collections.unmodifiableMap(
            new HashMap<String, Integer>() {{
                put("EUR", R.mipmap.eur);
                put("AUD", R.mipmap.aud);
                put("BGN", R.mipmap.bgn);
                put("BRL", R.mipmap.brn);
                put("ZAR", R.mipmap.zar);
                put("CAD", R.mipmap.cad);
                put("CHF", R.mipmap.chf);
                put("CNY", R.mipmap.cny);
                put("CZK", R.mipmap.czk);
                put("DKK", R.mipmap.dkk);
                put("GBP", R.mipmap.gbp);
                put("HKD", R.mipmap.hkd);
                put("HRK", R.mipmap.hrk);
                put("HUF", R.mipmap.huf);
                put("IDR", R.mipmap.idr);
                put("ILS", R.mipmap.ils);
                put("INR", R.mipmap.inr);
                put("JPY", R.mipmap.jpy);
                put("KRW", R.mipmap.krw);
                put("MXN", R.mipmap.mxn);
                put("MYR", R.mipmap.myr);
                put("NOK", R.mipmap.nok);
                put("NZD", R.mipmap.nzd);
                put("PHP", R.mipmap.php);
                put("PLN", R.mipmap.pln);
                put("RON", R.mipmap.ron);
                put("RUB", R.mipmap.rub);
                put("SEK", R.mipmap.sek);
                put("SGD", R.mipmap.sgd);
                put("THB", R.mipmap.thb);
                put("TRY", R.mipmap._try);
                put("USD", R.mipmap.usd);
            }});
    private static final String DOMAIN = "https://revolut.duckdns.org";

    public static Gson getGson() {
        return new GsonBuilder().registerTypeAdapter(Rates.class, new RatesDeserializer()).create();
    }

    public static int getFlagSource(String code) {
        Integer id = myMap.get(code);
        return id != null ? id : R.mipmap.ic_launcher;
    }

    public static String getValueFormat(double value) {
        NumberFormat nf = new DecimalFormat("##.##");
        return nf.format(value);
    }

    public static String getDomainUrl() {
        return DOMAIN;
    }

    public static boolean isDeviceOffline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm == null || cm.getActiveNetworkInfo() == null || !cm.getActiveNetworkInfo()
                .isConnectedOrConnecting());
    }

    @BindingAdapter("android:src")
    public static void setImageDrawable(ImageView view, int id) {
        view.setImageResource(id);
    }
}
