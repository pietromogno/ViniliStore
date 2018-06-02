package com.example.pietro.vinilistore;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.pietro.vinilistore.MongoDB.Prodotto.Prodotto;

import java.util.ArrayList;
import java.util.List;

public class StoreHome extends AppCompatActivity {

    ListView lstView;
    List<Prodotto> prodotti = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_home);
        lstView =(ListView)findViewById(R.id.listProd);
    }



    class GetData extends AsyncTask<String,Void,String>{
        ProgressDialog pd= new ProgressDialog(StoreHome.this);

         @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pd.setTitle("Caricamento..");
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}
