package com.example.geo2021.repository;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.geo2021.Action;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CountryRepository {

    InputStream dowload(String url) throws IOException {
        HttpURLConnection connection= (HttpURLConnection) new URL(url).openConnection();
        return connection.getInputStream();
    }
    @SuppressLint("StaticFieldLeak")
    public void getCountries(final String region, final Action<List<Country>> callback){
        new AsyncTask<Void, Void, List<Country>>() {
            @Override
            protected List<Country> doInBackground(Void... voids) {
                try {
                    InputStream stream= dowload("https://restcountries.eu/rest/v2/region/europe" + region + "?fields=name;capital;alpha2Code;flag");
                    return Json.getList(stream, null,Country.class);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<Country> countries) {
                callback.perform(countries);
                super.onPostExecute(countries);
            }
        }.execute();

    }
    @SuppressLint("StaticFieldLeak")
    public void getCountryDetails(final String countryCode, Action<CountryDetalis> callback){
        new AsyncTask<Void, Void, CountryDetalis>() {
            @Override
            protected CountryDetalis doInBackground(Void... voids) {
                try {
                    InputStream stream= dowload("https://restcountries.eu/rest/v2/alpha/"+ countryCode+ "?fields=name;capital;alpha2Code;flag;area,population,borders;timezones");
                    return Json.getItem(stream,null,CountryDetalis.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(CountryDetalis countryDetails) {
                super.onPostExecute(countryDetails);
            }
        }.execute();

    }

}
