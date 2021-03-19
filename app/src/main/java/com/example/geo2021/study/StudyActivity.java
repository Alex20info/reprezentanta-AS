package com.example.geo2021.study;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geo2021.Action;
import com.example.geo2021.Helper;
import com.example.geo2021.R;

public class StudyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_activity);

     showContinents();
    }


        void showContinents() {
            RecyclerView recyclerView=findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            ContinentsAdapter adapter=new ContinentsAdapter();
            adapter.setItems(Helper.getContinents());

            adapter.setOnItemSelected(new Action<String>() {
                @Override
                public void perform(String args) {
                    Intent intent = new Intent(StudyActivity.this, CountriesActivity.class);
                    intent.putExtra(CountriesActivity.CONTINENT_KEY, args);
                    startActivity(intent);
                }
            });

        recyclerView.setAdapter(adapter);

    }
}
