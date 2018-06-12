package com.example.pietro.vinilistore;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pietro.vinilistore.MongoDB.Common;
import com.example.pietro.vinilistore.MongoDB.Prodotto.Prodotto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;


public class DettaglioProdotto extends AppCompatActivity {

    TextView nome, artista, genere, descrizione, prezzo, pezzi;
    ImageView mImageView;
    Button aggCarrello;
    Prodotto p;
    String idUtente;    //nel bundle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_prodotto);
        aggCarrello = findViewById(R.id.addToChart);
        Bundle bundleObject = getIntent().getExtras();
        String idProdotto = bundleObject.getString("prodotto");
        String api = Common.getAddressAPIProdotti();

        new GetData().execute(api, idProdotto);


    }


    class GetData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            String stream = null;
            String urlString = args[0];
            String idProdotto = args[1];
            HTTPDataHandler handler = new HTTPDataHandler();
            p = handler.GetProdottoFromID(urlString, idProdotto);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Bundle bundle = getIntent().getExtras();
            idUtente = bundle.getString("idUtente");

            nome = findViewById(R.id.nomeAlbum);
            artista = findViewById(R.id.artistaAlbum);
            genere = findViewById(R.id.genereAlbum);
            descrizione = findViewById(R.id.descrizioneAlbum);
            prezzo = findViewById(R.id.prezzoAlbum);
            pezzi = findViewById(R.id.pezziRimasti);
            mImageView = findViewById(R.id.imgCopertina);
            new GetImageFromURL(mImageView).execute(p.getUrlFoto());
            nome.setText(p.getNomeProdotto());
            artista.setText(p.getNomeArtista());
            genere.setText(p.getGenere());
            descrizione.setText(p.getDescrizione());
            if (p.getPezziDisponibili() > 0) {
                pezzi.setText("Pezzi disponibili : " + p.getPezziDisponibili());
            } else {
                pezzi.setText("Attualmente non disponibile");
            }
            prezzo.setText(p.getPrezzo() + "€");

            aggCarrello.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new toCart().execute();
                }
            });

        }
    }

    class toCart extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            String stream = null;
            if (p.getPezziDisponibili() > 0) {
                HTTPDataHandler http = new HTTPDataHandler();
                try {
                    http.compiniCarrello(Common.getAddressAPICarrello(), idUtente, p.getId().get$oid());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(p.getPezziDisponibili()>0){
                Toast.makeText(getApplicationContext(), "Aggiunto al carrello!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Prodotto attualmente non disponibile", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
