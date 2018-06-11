package com.example.pietro.vinilistore.MongoDB.Carrello;

import com.example.pietro.vinilistore.MongoDB.Prodotto.Id;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Carrello {

    @SerializedName("_id")
    @Expose
    private transient com.example.pietro.vinilistore.MongoDB.Prodotto.Id id;

    @SerializedName("idUtente")
    @Expose
    private String idUtente;

    @SerializedName("data")
    @Expose
    private String data;

    @SerializedName("idProdotti")
    @Expose
    private String[] idProdotto;


    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(String idUtente) {
        this.idUtente = idUtente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String[] getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(String[] idProdotto) {
        this.idProdotto = idProdotto;
    }
}
