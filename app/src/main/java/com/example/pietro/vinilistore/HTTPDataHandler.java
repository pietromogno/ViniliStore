package com.example.pietro.vinilistore;

import android.os.Environment;

import com.example.pietro.vinilistore.MongoDB.Carrello.Carrello;
import com.example.pietro.vinilistore.MongoDB.Prodotto.Prodotto;
import com.example.pietro.vinilistore.MongoDB.Utente.Utente;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class HTTPDataHandler {

    static String stream = null;

    public HTTPDataHandler() {
    }

    public String GetHTTPData(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //controllo stato connessione
            if (urlConnection.getResponseCode() == 200) {
                //codice risposta = 200 ===> connessione ok
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                //lettura del BufferedInputStream
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null)
                    sb.append(line);
                stream = sb.toString();
                urlConnection.disconnect();
            } else {

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }   //per lettura prodotti sulle liste

    public Carrello GetHTTPDataCarrello(String urlString, String idUtente) {
        stream = "";
        Carrello carrello = new Carrello();
        String query = "&q={\"idUtente\":\"" + idUtente + "\"}";
        Gson gson = new Gson();
        try {
            URL url = new URL(urlString + query);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //controllo stato connessione
            if (urlConnection.getResponseCode() == 200) {
                //codice risposta = 200 ===> connessione ok
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                //lettura del BufferedInputStream
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null)
                    sb.append(line);
                stream = sb.toString();
                String subStream = stream.substring(1, stream.length() - 2);
                urlConnection.disconnect();
                carrello = gson.fromJson(subStream, Carrello.class);

            } else {
                carrello = null;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return carrello;
    }   //per prendere un carrello esistente

    public static Prodotto GetProdottoFromID(String urlString, String idProdotto) {
        Prodotto p = new Prodotto();
        String query = "&q={\"_id\":{\"$oid\":\"" + idProdotto + "\"}}";
        Gson gson = new Gson();
        try {
            URL url = new URL(urlString + query);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //controllo stato connessione
            if (urlConnection.getResponseCode() == 200) {
                //codice risposta = 200 ===> connessione ok
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                //lettura del BufferedInputStream
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null)
                    sb.append(line);
                stream = sb.toString();
                String subStream = stream.substring(1, stream.length() - 2);
                urlConnection.disconnect();
                p = gson.fromJson(subStream, Prodotto.class);
                System.out.println(p);
            } else {

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }   //IDprodotto restituisce un oggetto Prodotto, usato nel carrello e nell dettaglio prodotto

    public void PostHTTPData(String urlString, String json) throws IOException {
        String urlParameters = json;
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        String request = urlString;
        URL url = new URL(request);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("data", json);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("charset", "utf-8");
        conn.setUseCaches(false);
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(postData);
        }
        int i = conn.getResponseCode();
        System.out.println(i);
    }   //per registrazione

    public Utente getPersonaFromEmail(String urlString, String query) {
        Utente utente = new Utente();
        Gson gson = new Gson();
        try {
            URL url = new URL(urlString + query);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //controllo stato connessione
            if (urlConnection.getResponseCode() == 200) {
                //codice risposta = 200 ===> connessione ok
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                //lettura del BufferedInputStream
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null)
                    sb.append(line);
                stream = sb.toString();
                String subStream = stream.substring(1, stream.length() - 2);
                urlConnection.disconnect();
                utente = gson.fromJson(subStream, Utente.class);

            } else {

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return utente;
    }   //data l' email restituisce un oggetto utente , per area personale

    public void compiniCarrello(String urlString, String idUtente, String idProdotto) throws IOException {
        String urlParameters = "";
        if (GetHTTPDataCarrello(urlString, idUtente) != null) {  //se l' utente ha già un carrello aggiungo in coda l' idProdotto , prendo quello esistente
            stream = "";
            Carrello carrello = new Carrello();
            String query = "&q={\"idUtente\":\"" + idUtente + "\"}";
            Gson gson = new Gson();
            try {
                URL url = new URL(urlString + query);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //controllo stato connessione
                if (urlConnection.getResponseCode() == 200) {
                    //codice risposta = 200 ===> connessione ok
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    //lettura del BufferedInputStream
                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null)
                        sb.append(line);
                    stream = sb.toString();
                    String subStream = stream.substring(1, stream.length() - 2);
                    urlConnection.disconnect();
                    urlParameters = subStream.substring(0, subStream.length() - 3);
                    urlParameters += "\",\"" + idProdotto + "\"]}"; //aggiungo in coda in nuovo prodotto
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            String request = urlString;
            URL url = new URL(request + query + "u=true");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("data", urlParameters);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(postData);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int i = conn.getResponseCode();
            System.out.println(i);


        } else {  //se l' utente non ha già un carrello creo un nuovo carrello a nome di idUtente nel DB e carico il primo prodotto
            Date d = new Date();
            urlParameters = "{\"idUtente\" : \"" + idUtente + "\",\"data\" : \"" + d + "\",\"idProdotti\" : [\"" + idProdotto + "\"]}";
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            String request = urlString;
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("data", urlParameters);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(postData);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int i = conn.getResponseCode();
            System.out.println(i);
        }
    }   //crea un carrello per l' utente selezionato o aggiunge un prodotto se il carrello già esiste

    public void effettuaOrdine(String urlString, String idUtente) throws IOException {
        String query = "&q={\"idUtente\":\"" + idUtente + "\"}";
        String urlParameters = "{}";
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        String request = urlString;
        URL url = new URL(request + query + "u=true");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("data", urlParameters);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("charset", "utf-8");
        conn.setUseCaches(false);
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(postData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = conn.getResponseCode();
        System.out.println(i);
    }   //svuota il carrello dal db, tale record non viene del tutto cancellato, se possibile da migliorare. // al termine dell' ordine viene creato se non esiste un file di log nominato idUtente con data e totale dell' ordine


    public void scriviSuStoricoOrdini(String idUtente, String totale) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
        Date now = new Date();
        String fileName = idUtente + ".txt";
        try {
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/log");
            if (!dir.exists()) {
                root.mkdirs();
            }
            File file = new File(root, fileName);

            FileWriter writer = new FileWriter(file, true);
            writer.append(formatter.format(now) + "\t" + totale + "\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   //scrive un file se non già esistente lo storico ordini

    public List<String> leggiStoricoOrdini(String idUtente) throws IOException {
        List<String> ris = new ArrayList<>();
        FileReader f;
        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath());

        String fileName = idUtente + ".txt";
        File file = new File(dir, fileName);

        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            ris.add(sc.nextLine());
        }
        return ris;
    }   //mostra lo storico ordini del utente
}

