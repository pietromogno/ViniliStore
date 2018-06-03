package com.example.pietro.vinilistore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pietro.vinilistore.MongoDB.Common;
import com.example.pietro.vinilistore.MongoDB.Prodotto.Prodotto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreHome extends AppCompatActivity {

    ListView lstView;
    List<Prodotto> prodotti ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_home);
        lstView =findViewById(R.id.listProd);
        new GetData().execute(Common.getAddressAPIProdotti());
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent dettaglio = new Intent(view.getContext(),DettaglioProdotto.class);
                Bundle b = new Bundle();
                Prodotto prodotto = prodotti.get(i);
                //System.out.println(prodotto.toString());
                b.putSerializable("prodotti", (Serializable) prodotti.get(i));
                dettaglio.putExtras(b);
                startActivity(dettaglio);
            }
        });
    }



    class GetData extends AsyncTask<String,Void,String>{
        ProgressDialog pd= new ProgressDialog(StoreHome.this);
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pd.setTitle("Caricamento...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String stream = null;
            String urlString = args[0];
            HTTPDataHandler http= new HTTPDataHandler();
            stream = http.GetHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Prodotto>>(){}.getType();
            prodotti =gson.fromJson(s,listType);
            CustomAdapter adapter = new CustomAdapter(getApplicationContext(),prodotti);
            lstView.setAdapter(adapter);
            pd.dismiss();
        }

    }
}
