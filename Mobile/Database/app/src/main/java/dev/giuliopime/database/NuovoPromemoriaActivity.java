package dev.giuliopime.database;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import dev.giuliopime.database.db.DBManager;

public class NuovoPromemoriaActivity extends AppCompatActivity {
    private DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuovo_promemoria);

        dbManager = new DBManager(this);

        findViewById(R.id.btnSalva).setOnClickListener(v -> {
            EditText titolo = findViewById(R.id.inputTitolo);
            EditText descrizione = findViewById(R.id.inputDescrizione);

            dbManager.save(titolo.getText().toString(), descrizione.getText().toString());

            startActivity(new Intent(NuovoPromemoriaActivity.this, MainActivity.class));
        });
    }
}
