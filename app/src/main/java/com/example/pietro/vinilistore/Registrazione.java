package com.example.pietro.vinilistore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pietro.vinilistore.MongoDB.Common;

import java.io.IOException;

public class Registrazione extends AppCompatActivity {

    TextView txtNome,txtCognome,txtIndirizzo,txtEmail,txtPsw;
    Button btnRegistati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        txtNome = findViewById(R.id.txtNome);
        txtCognome = findViewById(R.id.txtCognome);
        txtIndirizzo = findViewById(R.id.txtIndirizzo);
        txtEmail = findViewById(R.id.txtEmail);
        txtPsw = findViewById(R.id.txtPsw);
        btnRegistati = findViewById(R.id.btnReg);

        btnRegistati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String nome=txtNome.getText().toString(),cognome=txtCognome.getText().toString(),indirizzo=txtIndirizzo.getText().toString(),email=txtEmail.getText().toString(),psw=txtPsw.getText().toString();
            new PostData(nome,cognome,indirizzo,email,psw).execute(Common.getAddressAPIUtenti());
                Intent home = new Intent(view.getContext(),StoreHome.class);
                startActivity(home);
            }
        });

    }


    class PostData extends AsyncTask<String,String,String>{
        ProgressDialog pd= new ProgressDialog(Registrazione.this);
        String nome,cognome,indirizzo,email,psw;

        public PostData(String nome, String cognome, String indirizzo, String email, String psw) {
            this.nome = nome;
            this.cognome = cognome;
            this.indirizzo = indirizzo;
            this.email = email;
            this.psw = psw;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pd.setTitle("Invio Registrazione...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String urlString = args[0];
            HTTPDataHandler http= new HTTPDataHandler();
            String json = "{\"nome\" : \""+nome+"\",\"cognome\" : \""+cognome+"\",\"email\" : \""+email+"\",\"password\" : \""+psw+"\",\"indirizzo\" : \""+indirizzo+"\"}";
            try {
                http.PostHTTPData(urlString,json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            pd.dismiss();
        }

    }

}
