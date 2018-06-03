package com.example.pietro.vinilistore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

public class GetImageFromURL extends AsyncTask<String,Void,Bitmap> {

    ImageView imgView;
    Bitmap bitM;

    public GetImageFromURL(ImageView imgV){
        this.imgView=  imgV;
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        String myURL=url[0];
        bitM = null;
        try{
            InputStream srt = new java.net.URL(myURL).openStream();
            bitM = BitmapFactory.decodeStream(srt);
        } catch (Exception e){
            e.printStackTrace();
        }
        return bitM;
    }

    @Override
    protected void onPostExecute(Bitmap btmp){
        super.onPostExecute(btmp);
        imgView.setImageBitmap(btmp);
    }


}