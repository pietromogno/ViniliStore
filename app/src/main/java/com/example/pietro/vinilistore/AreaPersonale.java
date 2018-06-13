package com.example.pietro.vinilistore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AreaPersonale extends AppCompatActivity {

    TextView nome,via,email;
    Button storico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_personale);
        Bundle bundleObject = getIntent().getExtras();
        final ArrayList<String> datiUtente = bundleObject.getStringArrayList("utente");
        storico=findViewById(R.id.btnStorico);
        nome=findViewById(R.id.personal_name);
        via=findViewById(R.id.personal_via);
        email=findViewById(R.id.personal_email);

        nome.setText(datiUtente.get(0));
        email.setText(datiUtente.get(1));
        via.setText(datiUtente.get(2));

        storico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent storicoOrd = new Intent(view.getContext(), StoricoOrdini.class);
                Bundle b = new Bundle();
                b.putString("idUtente",datiUtente.get(3));
                storicoOrd.putExtras(b);
                startActivity(storicoOrd);
            }
        });
    }
}
