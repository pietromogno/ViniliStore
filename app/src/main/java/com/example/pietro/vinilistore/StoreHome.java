package com.example.pietro.vinilistore;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mToggle;
    TextView grunge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_home);
        Bundle bundleObj = getIntent().getExtras();
        u = bundleObj.getStringArrayList("utente");
        lstView =findViewById(R.id.listProd);
        Bundle bObj=getIntent().getExtras();
        String genere=bObj.getString("genere");
        if(genere==null)genere="";
        new GetData().execute(Common.getAddressAPIProdotti()+genere);
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

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight

                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                Intent home = new Intent(getApplicationContext(), StoreHome.class);
                                Bundle b = new Bundle();
                                Bundle bb = new Bundle();
                                bb.putSerializable("utente", u);
                                home.putExtras(bb);
                                startActivity(home);
                                finish();
                                return true;
                            case R.id.nav_grunge:
                                Intent grungeHome = new Intent(getApplicationContext(), StoreHome.class);
                                b = new Bundle();
                                b.putString("genere", "&q={\"genere\":\"Grunge\"}");
                                grungeHome.putExtras(b);
                                bb = new Bundle();
                                bb.putSerializable("utente", u);
                                grungeHome.putExtras(bb);
                                startActivity(grungeHome);
                                finish();
                                return true;
                            case R.id.nav_rock:
                                Intent rockHome = new Intent(getApplicationContext(), StoreHome.class);
                                b = new Bundle();
                                b.putString("genere", "&q={\"genere\":\"Rock\"}");
                                rockHome.putExtras(b);
                                bb = new Bundle();
                                bb.putSerializable("utente", u);
                                rockHome.putExtras(bb);
                                startActivity(rockHome);
                                finish();
                                return true;
                            case R.id.nav_punk:
                                Intent punkHome = new Intent(getApplicationContext(), StoreHome.class);
                                b = new Bundle();
                                b.putString("genere", "&q={\"genere\":\"Punk\"}");
                                punkHome.putExtras(b);
                                bb = new Bundle();
                                bb.putSerializable("utente", u);
                                punkHome.putExtras(bb);
                                startActivity(punkHome);
                                finish();
                                return true;
                            case R.id.nav_blues:
                                Intent bluesHome = new Intent(getApplicationContext(), StoreHome.class);
                                b = new Bundle();
                                b.putString("genere", "&q={\"genere\":\"Blues\"}");
                                bluesHome.putExtras(b);
                                bb = new Bundle();
                                bb.putSerializable("utente", u);
                                bluesHome.putExtras(bb);
                                startActivity(bluesHome);
                                finish();
                                return true;
                            case R.id.nav_metal:
                                Intent metalHome = new Intent(getApplicationContext(), StoreHome.class);
                                b = new Bundle();
                                b.putString("genere", "&q={\"genere\":\"Metal\"}");
                                metalHome.putExtras(b);
                                bb = new Bundle();
                                bb.putSerializable("utente", u);
                                metalHome.putExtras(bb);
                                startActivity(metalHome);
                                finish();
                                return true;

                            case R.id.nav_sale:
                                Intent saleHome = new Intent(getApplicationContext(), StoreHome.class);
                                b = new Bundle();
                                b.putString("genere", "&q={\"sconto\":true}");
                                saleHome.putExtras(b);
                                bb = new Bundle();
                                bb.putSerializable("utente", u);
                                saleHome.putExtras(bb);
                                startActivity(saleHome);
                                finish();
                                return true;

                            default:
                        }
                    return true;}
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

            case R.id.action_cart:      //carrello
                Bundle bundleObj = getIntent().getExtras();
                u = bundleObj.getStringArrayList("utente");

                    Intent cart = new Intent(getApplicationContext(), ViewCarrello.class);
                    Bundle d = new Bundle();
                    String id = u.get(3);
                    d.putSerializable("idUtente", id);     // porto nell' carrello l' ID utente loggato
                    cart.putExtras(d);
                    startActivity(cart);

                    return true;

            case R.id.action_profile:   //profilo
                Intent personal = new Intent(getApplicationContext(),AreaPersonale.class);
                Bundle bundleObject = getIntent().getExtras();
                u = bundleObject.getStringArrayList("utente");
                Bundle b = new Bundle();
                b.putSerializable("utente",u);     // porto nell' area personale l' utente loggato
                personal.putExtras(b);
                startActivity(personal);
                return true;

            default:
                if(mToggle.onOptionsItemSelected(item)){    //apri navigationDrawer
                    return true;
                }

                return super.onOptionsItemSelected(item);

        }
    }   //gestione pulsanti action bar


    class GetData extends AsyncTask<String,Void,String>{
        ProgressDialog pd= new ProgressDialog(StoreHome.this);
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pd.setTitle("Caricamento catalogo...");
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
    }        //caricamento del catalogo
}
