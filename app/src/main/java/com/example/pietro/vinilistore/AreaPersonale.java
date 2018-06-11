package com.example.pietro.vinilistore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AreaPersonale extends AppCompatActivity {

    TextView nome,via,email;
    Button storico,modPsw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_personale);
        Bundle bundleObject = getIntent().getExtras();
        ArrayList<String> datiUtente = bundleObject.getStringArrayList("utente");

        nome=findViewById(R.id.personal_name);
        via=findViewById(R.id.personal_via);
        email=findViewById(R.id.personal_email);

        nome.setText(datiUtente.get(0));
        email.setText(datiUtente.get(1));
        via.setText(datiUtente.get(2));


    }
}
