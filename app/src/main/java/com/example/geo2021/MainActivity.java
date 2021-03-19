package com.example.geo2021;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.geo2021.game.GameSettingsDialog;
import com.example.geo2021.study.StudyActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViewById(R.id.study).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(MainActivity.this, StudyActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new GameSettingsDialog().show(MainActivity.this);
            }
        });
    }
}
