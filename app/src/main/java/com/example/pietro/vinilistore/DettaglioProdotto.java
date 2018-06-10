package com.example.pietro.vinilistore;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pietro.vinilistore.MongoDB.Prodotto.Prodotto;


public class DettaglioProdotto extends AppCompatActivity {

    TextView nome,artista,genere,descrizione,prezzo;
    ImageView mImageView;
    Button aggCarrello;
    Prodotto p ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_prodotto);
        aggCarrello=findViewById(R.id.addToChart);
        Bundle bundleObject = getIntent().getExtras();
        p = (Prodotto) bundleObject.getSerializable("prodotti");
        System.out.println(p.toString());


        nome = findViewById(R.id.nomeAlbum);
        artista = findViewById(R.id.artistaAlbum);
        genere=findViewById(R.id.genereAlbum);
        descrizione=findViewById(R.id.descrizioneAlbum);
        prezzo=findViewById(R.id.prezzoAlbum);
        mImageView = findViewById(R.id.imgCopertina);
        new GetImageFromURL(mImageView).execute(p.getUrlFoto());
        nome.setText(p.getNomeProdotto());
        artista.setText(p.getNomeArtista());
        genere.setText(p.getGenere());
        descrizione.setText(p.getDescrizione());
        prezzo.setText(p.getPrezzo()+"€");
    }


}
