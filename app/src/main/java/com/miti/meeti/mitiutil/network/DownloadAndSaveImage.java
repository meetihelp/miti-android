package com.miti.meeti.mitiutil.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class DownloadAndSaveImage extends AsyncTask<String, Void, Boolean> {
    public String Path;
    public DownloadAndSaveImage(String path) {
        this.Path = path;
    }

    protected Boolean doInBackground(String... urls) {

        final OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(urls[0])
                .build();

        Response response = null;
        Bitmap mIcon11 = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.isSuccessful()) {
            try {
                mIcon11 = BitmapFactory.decodeStream(response.body().byteStream());
                FileOutputStream out = new FileOutputStream(Path);
                mIcon11.compress(Bitmap.CompressFormat.JPEG, 100,out);
                return true;
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

        }
        return false;
    }

}

