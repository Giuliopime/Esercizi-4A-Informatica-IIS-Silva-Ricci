package com.example.generatoreprofilo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nomeF = findViewById(R.id.fNome);
        EditText cognomeF = findViewById(R.id.fCognome);
        EditText fData = findViewById(R.id.fData);
        RadioButton maschioRB = findViewById(R.id.rbMaschio);
        RadioButton femminaRB = findViewById(R.id.rbFemmina);
        RadioButton altroRB = findViewById(R.id.rbAltro);
        CheckBox sportCB = findViewById(R.id.cbSport);
        CheckBox musicaCB = findViewById(R.id.cbMusica);
        CheckBox arteCB = findViewById(R.id.cbArte);
        CheckBox culturaCB = findViewById(R.id.cbCultura);
        CheckBox altroCB = findViewById(R.id.cbAltro);
        EditText altroF = findViewById(R.id.fAltro);
        TextView errorF = findViewById(R.id.errorF);

        fData.setShowSoftInputOnFocus(false);
        fData.setOnClickListener(v -> {
            Calendar mcurrentDate = Calendar.getInstance();
            int mYear = mcurrentDate.get(Calendar.YEAR);
            int mMonth = mcurrentDate.get(Calendar.MONTH);
            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog mDatePicker=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    fData.setText(dayOfMonth + "/" + month + "/" + year);
                }
            }, mYear, mMonth, mDay);
            mDatePicker.setTitle("Data nascita");
            mDatePicker.show();
        });

        altroCB.setOnCheckedChangeListener((buttonView, isChecked) ->
            altroF.setVisibility(isChecked ? View.VISIBLE : View.GONE)
        );

        Button btn = findViewById(R.id.btnGenera);
        btn.setOnClickListener(v -> {
            String nome = nomeF.getText().toString();
            String cognome = cognomeF.getText().toString();
            String data = fData.getText().toString();

            String sesso = maschioRB.isChecked() ? "m" : femminaRB.isChecked() ? "f" : altroRB.isChecked() ? "a" : null;
            ArrayList<Interessi> interessi = new ArrayList<>();
            String altriInteressi = null;
            if (sportCB.isChecked())
                interessi.add(Interessi.SPORT);
            if (musicaCB.isChecked())
                interessi.add(Interessi.MUSICA);
            if (arteCB.isChecked())
                interessi.add(Interessi.ARTE);
            if (culturaCB.isChecked())
                interessi.add(Interessi.CULTURA);
            if (altroCB.isChecked())
                altriInteressi = altroF.getText().toString();


            // Controlli
            if (nome.isEmpty())
                errorF.setText("Inserisci un nome");
            else if (cognome.isEmpty())
                errorF.setText("Inserisci un cognome");
            else if (data.isEmpty())
                errorF.setText("Inserisci la data di nascita");
            else if (sesso == null)
                errorF.setText("Scegli un sesso");

            // Creazione Profilo e passaggio alla nuova pagina
            else {
                Profilo profilo = new Profilo(nome, cognome, sesso, data, interessi, altriInteressi);
                startActivity(new Intent(this, ProfiloActivity.class).putExtra("profilo", profilo));
            }
        });
    }
}