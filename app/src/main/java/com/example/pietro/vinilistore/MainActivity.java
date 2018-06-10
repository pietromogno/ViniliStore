package com.example.pietro.vinilistore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pietro.vinilistore.MongoDB.Common;
import com.example.pietro.vinilistore.MongoDB.Utente.Utente;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnRegistrati;
    TextView tEmail, tPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tEmail = findViewById(R.id.userEmail);
        tPsw = findViewById(R.id.userPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = tEmail.getText().toString();
                String password = tPsw.getText().toString();
                Login L  = (Login) new Login(email, password).execute(Common.getAddressAPIUtenti());
                //if (L.logOK==true) {
                //    Intent home = new Intent(view.getContext(), StoreHome.class);
                  //  startActivity(home);
                //}else{
                  //  Toast.makeText(getApplicationContext(),"username o password errate",20);
                //}
            }
        });

        btnRegistrati = (Button) findViewById(R.id.btnRegistrati);
        btnRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registrazione = new Intent(view.getContext(), Registrazione.class);
                startActivity(registrazione);
            }
        });
    }


    class Login extends AsyncTask<String, String, String> {
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        String email, psw;
        Utente u;
        boolean logOK;

        public Login(String email, String psw) {
            this.email = email;
            this.psw = psw;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pd.setTitle("Login...");
            //pd.show();
        }

        @Override
        protected String doInBackground(String... args) {

            String urlString = args[0];
            HTTPDataHandler http = new HTTPDataHandler();
            String json = "&q={\"email\" : \"" + email + "\"}";

                u = http.getPersonaFromEmail(urlString, json);
            //if(u.getPassword()==psw){
            //    Intent home = new Intent(getApplicationContext(), StoreHome.class);
            //    startActivity(home);
         //   }else if(u==null || u.getPassword()!=psw){
          //      Toast.makeText(getApplicationContext(),"username o password errate",20);
       //  }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(u.getPassword()==psw) {
                Intent home = new Intent(getApplicationContext(), StoreHome.class);
                startActivity(home);
            }
        }

    }
}
