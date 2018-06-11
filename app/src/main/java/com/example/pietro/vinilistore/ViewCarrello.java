package com.example.pietro.vinilistore;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pietro.vinilistore.MongoDB.Carrello.Carrello;
import com.example.pietro.vinilistore.MongoDB.Common;
import com.example.pietro.vinilistore.MongoDB.Prodotto.Prodotto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ViewCarrello extends AppCompatActivity {


    Button ordina;
    TextView totale;
    String idUtente = "5b09a8dffb6fc0292d6ed802"; //da passare con il boundle
    ListView lstView;
    Carrello c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrello);
        totale = findViewById(R.id.totale);
        lstView = findViewById(R.id.personalCart);
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
            CustomAdapter adapter = new CustomAdapter(getApplicationContext(), prodotti);
            lstView.setAdapter(adapter);
            int tot = 0;
            for (int i = 0; i < prodotti.size(); i++) {
                tot += prodotti.get(i).getPrezzo();
            }
            totale.setText(tot + "€");
            pd.dismiss();
        }
    }

    public static List<Prodotto> fromCarrelloToProdotti(Carrello c) {
        String urlString = Common.getAddressAPIProdotti();
        List<Prodotto> ris = new ArrayList<>();
        String[] ca = c.getIdProdotto();
        for (int i = 0; i < ca.length; i++) {
            Prodotto p = HTTPDataHandler.GetProdottoFromID(urlString, ca[i]);
            ris.add(p);
        }
        return ris;
    }


}