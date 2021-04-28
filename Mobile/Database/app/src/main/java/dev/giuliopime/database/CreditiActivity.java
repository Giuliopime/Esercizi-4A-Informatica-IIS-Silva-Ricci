package dev.giuliopime.database;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CreditiActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crediti);

        findViewById(R.id.btnHome).setOnClickListener(v -> startActivity(new Intent(CreditiActivity.this, MainActivity.class)));
    }
}
