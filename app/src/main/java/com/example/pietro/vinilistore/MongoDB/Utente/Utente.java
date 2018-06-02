package com.example.pietro.vinilistore.MongoDB.Utente;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Utente {

    @SerializedName("_id")
    @Expose
    private Id id;

    @SerializedName("nome")
    @Expose
    private String nome;

    @SerializedName("cognome")
    @Expose
    private String cognome;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("indirizzo")
    @Expose
    private String  indirizzo;


    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
}
