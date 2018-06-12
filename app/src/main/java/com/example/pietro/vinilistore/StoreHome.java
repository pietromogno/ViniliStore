package com.example.pietro.vinilistore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pietro.vinilistore.MongoDB.Carrello.Carrello;
import com.example.pietro.vinilistore.MongoDB.Common;
import com.example.pietro.vinilistore.MongoDB.Prodotto.Prodotto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StoreHome extends AppCompatActivity {

    ListView lstView;
    List<Prodotto> prodotti ;
    ArrayList<String> u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_home);
        Bundle bundleObj = getIntent().getExtras();
        u = bundleObj.getStringArrayList("utente");
        lstView =findViewById(R.id.listProd);
        new GetData().execute(Common.getAddressAPIProdotti());
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent dettaglio = new Intent(view.getContext(),DettaglioProdotto.class);
                Bundle b = new Bundle();
                b.putSerializable("prodotto", prodotti.get(i).getId().get$oid());
                b.putSerializable("idUtente",u.get(3));
                dettaglio.putExtras(b);
                startActivity(dettaglio);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_store_home,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_cart:
                Bundle bundleObj = getIntent().getExtras();
                u = bundleObj.getStringArrayList("utente");

                    Intent cart = new Intent(getApplicationContext(), ViewCarrello.class);
                    Bundle d = new Bundle();
                    String id = u.get(3);
                    d.putSerializable("idUtente", id);     // porto nell' carrello l' ID utente loggato
                    cart.putExtras(d);
                    startActivity(cart);

                    return true;

            case R.id.action_profile:
                Intent personal = new Intent(getApplicationContext(),AreaPersonale.class);
                Bundle bundleObject = getIntent().getExtras();
                u = bundleObject.getStringArrayList("utente");
                Bundle b = new Bundle();
                b.putSerializable("utente",u);     // porto nell' area personale l' utente loggato
                personal.putExtras(b);
                startActivity(personal);
                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
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
