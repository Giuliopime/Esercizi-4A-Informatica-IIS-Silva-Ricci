package dev.giuliopime.database;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import dev.giuliopime.database.db.DBManager;
import dev.giuliopime.database.db.DBStrings;

public class MainActivity extends AppCompatActivity {

    private DBManager dbManager;
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.lista);
        dbManager = new DBManager(this);

        // Aggiungi i promemoria salvati nel db alla lista
        Cursor c = dbManager.getAllRecords();
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        while(c.moveToNext()){
            adapter.add(c.getString(c.getColumnIndex(DBStrings.FIELD_TITOLO)));
        }

        lista.setAdapter(adapter);

        // Mostra la descrizione del promemoria
        lista.setOnItemClickListener((parent, view, position, id) -> {
            Cursor c1 = dbManager.getAllRecords();
            c1.moveToPosition(position);

            Toast.makeText(MainActivity.this,
                    c1.getString(c1.getColumnIndex(DBStrings.FIELD_DESCRIZIONE)),
                    Toast.LENGTH_LONG).show();
        });

        // Elimina il promemoria
        lista.setOnItemLongClickListener((parent, view, position, id) -> {
            Cursor c1 = dbManager.getAllRecords();
            c1.moveToPosition(position);

            int idR = c1.getInt(c1.getColumnIndex(DBStrings.FIELD_ID));

            adapter.remove((c1.getString(c1.getColumnIndex(DBStrings.FIELD_TITOLO))));
            adapter.notifyDataSetChanged();

            dbManager.delete(idR);

            return true;
        });


        // Eventi bottoni
        FloatingActionButton btnAggiungi = findViewById(R.id.btnAggiungi);
        btnAggiungi.setOnClickListener(view -> nuovoPromemoria());

        FloatingActionButton btnCrediti = findViewById(R.id.btnCrediti);
        btnCrediti.setOnClickListener(view -> apriCrediti());
    }

    private void nuovoPromemoria() {
        startActivity(new Intent(MainActivity.this, NuovoPromemoriaActivity.class));
    }

    private void apriCrediti() {
        startActivity(new Intent(MainActivity.this, CreditiActivity.class));
    }
}