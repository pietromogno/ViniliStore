package com.example.pietro.vinilistore.MongoDB;

import com.example.pietro.vinilistore.MongoDB.Prodotto.Prodotto;
import com.example.pietro.vinilistore.MongoDB.Utente.Utente;

public class Common {

    private static String DB_NAME = "vinili_store_db";
    public static String API_KEY="7KHwIrKtJsgnjRrCErYH24TUWQcPrriG";    //key per pietro_mogno

    public static String getAddressSingle(Utente utente){
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME,"UTENTI");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("/"+utente.getId().get$oid()+"?apiKey="+API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddressAPIUtenti(){
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME,"UTENTI");  //https://api.mlab.com/api/1/databases/
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?apiKey="+API_KEY);
        return stringBuilder.toString();
    }



    public static String getAddressSingle(Prodotto prodotto){
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME,"PRODOTTI");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("/"+prodotto.getId().get$oid()+"?apiKey="+API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddressAPIProdotti(){
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME,"PRODOTTI");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?apiKey="+API_KEY);
        return stringBuilder.toString();
    }

}
