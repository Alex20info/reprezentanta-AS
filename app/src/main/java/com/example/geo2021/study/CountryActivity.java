package com.example.geo2021.study;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geo2021.Action;
import com.example.geo2021.R;
import com.example.geo2021.repository.Country;
import com.example.geo2021.repository.CountryDetalis;
import com.example.geo2021.repository.CountryRepository;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class CountryActivity extends AppCompatActivity {
    public static final  String COUNTRY_KEY="country";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.country_details_activity);
        showCountry((Country) getIntent().getSerializableExtra(COUNTRY_KEY));
    }

    void showCountry(Country country){
        ((TextView) findViewById(R.id.name)).setText(String.format("%s (%s)",country.name,country.alpha2Code));
        ((TextView) findViewById(R.id.capital)).setText(country.capital);
        //SvgLoader.pluck().with(this).load(country.flag, (ImageView) findViewById(R.id.flag));
        new CountryRepository().getCountryDetails(country.alpha2Code, new Action<CountryDetalis>() {

            @Override
            public void perform(CountryDetalis args) {
                showCountryDetails(args);
            }
        });

    }

    void showCountryDetails(CountryDetalis countryDetails){
        ((TextView)findViewById(R.id.area)).setText(String.format("%s km", new DecimalFormat().format(countryDetails.area)));
        ((TextView)findViewById(R.id.population)).setText(new DecimalFormat().format(countryDetails.population));
        startUpdateTime(countryDetails);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        BordersAdapter adapter=new BordersAdapter();
        adapter.setOnItemSelected(new Action<String>() {
            @Override
            public void perform(String args) {
                onBorderItemSelected(args);
            }
        });
        adapter.setItems(countryDetails.borders);
        recyclerView.setAdapter(adapter);
    }

    void onBorderItemSelected(String code){
        new CountryRepository().getCountryDetails(code, new Action<CountryDetalis>() {
            @Override
            public void perform(CountryDetalis args) {
                Intent intent=new Intent(CountryActivity.this,CountryActivity.class);
                intent.putExtra(COUNTRY_KEY,args);
                startActivity(intent);
            }
        });
    }

    void startUpdateTime(final CountryDetalis countryDetalis){
        updateTime(countryDetalis);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startUpdateTime(countryDetalis);
            }
        },1000);
    }

    void updateTime(CountryDetalis details){
        ((TextView) findViewById(R.id.time)).setText(convertTime(details.timezones.get(0)));

    }
    String convertTime(String timeZoneValue){
        TimeZone timeZone= new SimpleTimeZone(getRawOffset(timeZoneValue),timeZoneValue);
        DateFormat dateFormat= new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        dateFormat.setTimeZone(timeZone);
       return String.format("%s(%s)",dateFormat.format(new Date()),timeZoneValue);
    }
    int getRawOffset(String timeZoneOffset){
        int res=0;
        timeZoneOffset=timeZoneOffset.substring(3);
        if(timeZoneOffset.length()==6){
            res+=3600 * Integer.parseInt(timeZoneOffset.substring(1,3));
            res+=60 * Integer.parseInt(timeZoneOffset.substring(4,6));
        }else if(timeZoneOffset.length()==3){
            res+=3600 * Integer.parseInt(timeZoneOffset.substring(1,3));
        }
        res=res*(timeZoneOffset.startsWith("-") ? -1 : 1);
        res=res*1000;
        return res;
    }
}
