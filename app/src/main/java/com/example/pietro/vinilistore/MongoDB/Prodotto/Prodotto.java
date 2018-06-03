package com.example.pietro.vinilistore.MongoDB.Prodotto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Prodotto implements Serializable {

    @SerializedName("_id")
    @Expose
    private transient com.example.pietro.vinilistore.MongoDB.Prodotto.Id id;

    @SerializedName("nomeProdotto")
    @Expose
    private String nomeProdotto;

    @SerializedName("nomeArtista")
    @Expose
    private String nomeArtista;

    @SerializedName("genere")
    @Expose
    private String genere;

    @SerializedName("prezzo")
    @Expose
    private Integer prezzo;

    @SerializedName("pezziDisponibili")
    @Expose
    private Integer pezziDisponibili;

    @SerializedName("urlFoto")
    @Expose
    private String urlFoto;

    @SerializedName("descrizione")
    @Expose
    private String descrizione;

    public com.example.pietro.vinilistore.MongoDB.Prodotto.Id getId() {
        return id;
    }

    public void setId(com.example.pietro.vinilistore.MongoDB.Prodotto.Id id) {
        this.id = id;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }

    public String getNomeArtista() {
        return nomeArtista;
    }

    public void setNomeArtista(String nomeArtista) {
        this.nomeArtista = nomeArtista;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public Integer getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Integer prezzo) {
        this.prezzo = prezzo;
    }

    public Integer getPezziDisponibili() {
        return pezziDisponibili;
    }

    public void setPezziDisponibili(Integer pezziDisponibili) {
        this.pezziDisponibili = pezziDisponibili;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
