package com.example.pietro.vinilistore;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pietro.vinilistore.MongoDB.Carrello.Carrello;
import com.example.pietro.vinilistore.MongoDB.Common;
import com.example.pietro.vinilistore.MongoDB.Prodotto.Prodotto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ViewCarrello extends AppCompatActivity {


    Button ordina;
    TextView totale;
    String idUtente;
    ListView lstView;
    Carrello c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrello);
        ordina= findViewById(R.id.btnOrdina);
        totale = findViewById(R.id.totale);
        lstView = findViewById(R.id.personalCart);
        Bundle bundleObject = getIntent().getExtras();
        idUtente = bundleObject.getString("idUtente");
        new GetData().execute(Common.getAddressAPICarrello());
    }


    class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog pd = new ProgressDialog(ViewCarrello.this);
        List<Prodotto> prodotti;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Carico il tuo carrello...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String stream = null;
            String urlString = args[0];
            HTTPDataHandler http = new HTTPDataHandler();
            c = http.GetHTTPDataCarrello(urlString, idUtente);
            Gson gson = new Gson();
            Type listType = new TypeToken<Carrello>() {
            }.getType();
            prodotti = fromCarrelloToProdotti(c);
            System.out.println(prodotti);

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (prodotti != null) {    //se l' utente corrente non ha un carrello non creo l' adapter
                CustomAdapter adapter = new CustomAdapter(getApplicationContext(), prodotti);
                lstView.setAdapter(adapter);
            }
            int tot = 0;
            for (int i = 0; i < prodotti.size(); i++) {
                tot += prodotti.get(i).getPrezzo();
            }
            totale.setText(tot + "€");  //se vuoto segna 0 euro
            pd.dismiss();
            if(tot==0) {
                Toast t = Toast.makeText(getApplicationContext(), "Il tuo carrello è vuoto", Toast.LENGTH_SHORT);     //segnalo che il carrello è vuoto
                t.show();
            }else {
                ordina.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new OrdinaCarrello().execute(Common.getAddressAPICarrello());
                    }
                });
            }
        }
    }

    public static List<Prodotto> fromCarrelloToProdotti(Carrello c) {
        String urlString = Common.getAddressAPIProdotti();
        List<Prodotto> ris = new ArrayList<>();
        if (c != null) {       //controllo se esiste un carrello per l' utente corrente
            String[] ca = c.getIdProdotto();
            for (int i = 0; i < ca.length; i++) {
                Prodotto p = HTTPDataHandler.GetProdottoFromID(urlString, ca[i]);
                ris.add(p);
            }
        }
        return ris;
    }

    class OrdinaCarrello extends AsyncTask<String, Void, String> {

        ProgressDialog pd = new ProgressDialog(ViewCarrello.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Invio Ordine...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String stream = null;
            String urlString = args[0];
            HTTPDataHandler http = new HTTPDataHandler();
            try {
                http.effettuaOrdine(urlString, idUtente);
            } catch (IOException e) {
                e.printStackTrace();
            }
            http.scriviSuStoricoOrdini(idUtente,totale.getText().toString());
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            new GetData().execute(Common.getAddressAPICarrello());
            Toast.makeText(getApplicationContext(),"Ordine eseguito correttamente",Toast.LENGTH_LONG).show();
            pd.dismiss();
        }

    }

}
