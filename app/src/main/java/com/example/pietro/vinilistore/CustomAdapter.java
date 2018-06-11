package com.example.pietro.vinilistore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pietro.vinilistore.MongoDB.Prodotto.Prodotto;

import java.io.InputStream;
import java.util.List;

public class CustomAdapter extends BaseAdapter{

    private Context mContext;
    private List<Prodotto> lstProdotti;
    ImageView mImageView;
    TextView mTextView,mTextPrezzo;
    Bitmap bitM;

    public CustomAdapter(Context mContext, List<Prodotto> lstProdotti) {
        this.mContext = mContext;
        this.lstProdotti = lstProdotti;
    }

    @Override
    public int getCount() {
        return lstProdotti.size();
    }

    @Override
    public Object getItem(int position) {
        return lstProdotti.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.custom_layout,null);
        mTextView = v.findViewById(R.id.titolo);
        mTextPrezzo = v.findViewById(R.id.txtPrezzo);
        mImageView = v.findViewById(R.id.imageView);
        new GetImageFromURL(mImageView).execute(lstProdotti.get(position).getUrlFoto());
        mTextView.setText(lstProdotti.get(position).getNomeProdotto());
        mTextPrezzo.setText(lstProdotti.get(position).getPrezzo()+"€");
        return v;
    }

}


