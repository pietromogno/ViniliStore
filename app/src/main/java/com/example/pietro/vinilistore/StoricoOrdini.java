package com.example.pietro.vinilistore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StoricoOrdini extends AppCompatActivity {

    ListView lstStorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storico_ordini);
        lstStorico = findViewById(R.id.lstStorico);
        HTTPDataHandler http = new HTTPDataHandler();
        Bundle bundleObj = getIntent().getExtras();
        String idUtente = bundleObj.getString("idUtente");
        List<String> ordini = null;
        try {
            ordini = http.leggiStoricoOrdini(idUtente);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ordini == null) {
            ordini = new ArrayList<>();
            ordini.add("Nessun ordine da mostrare");
        }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_layout_storico, R.id.ordine,ordini);
            lstStorico.setAdapter(adapter);
        }
    }
