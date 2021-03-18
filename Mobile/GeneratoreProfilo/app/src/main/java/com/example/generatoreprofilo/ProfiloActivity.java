package com.example.generatoreprofilo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProfiloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);

        Profilo profilo = (Profilo) getIntent().getSerializableExtra("profilo");
        TextView profiloT = findViewById(R.id.profiloText);
        profiloT.setText(profilo.toString());
    }
}