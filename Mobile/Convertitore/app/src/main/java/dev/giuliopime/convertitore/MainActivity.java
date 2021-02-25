package dev.giuliopime.convertitore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText inputText;
    TextView risultatoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.inputText);
        risultatoText = findViewById(R.id.risultatoText);

        final Button euroBtn = findViewById(R.id.euroBtn), dollariBtn = findViewById(R.id.dollariBtn);
        euroBtn.setOnClickListener(v -> { converti(Valuta.EURO); });
        dollariBtn.setOnClickListener(v -> { converti(Valuta.DOLLARO); });
    }

    private void converti(Valuta valuta) {
        double rateo = valuta == Valuta.DOLLARO ? 1.2209 : 0.8191;

        double q = Double.parseDouble(inputText.getText().toString());

        risultatoText.setText(new DecimalFormat("#.##").format(q * rateo) + (valuta == Valuta.DOLLARO ? " $" : " €"));
    }
}