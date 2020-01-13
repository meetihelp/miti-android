package com.miti.meeti.mitiutil.network;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.Mitigps;
import com.miti.meeti.database.Keyvalue.keyvalue;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageUpload {

    public class UploadImage extends AsyncTask<String, Void, String> {
        //param1 suburl, param2 fileaddress, param3 filename, param4 requestid
        public String url="http://meeti.club:8000/";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected  String doInBackground(String... params){
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("multipart/form-data; boundary=--------------------------539616771520821553345336");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("myFile","logo_foreground.png",
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    new File(params[1])))
                    .build();
            String cookie=MainActivity.cookieViewModel.getCookie1();
            Gson gson=new Gson();
            keyvalue kv=MainActivity.keyvalueViewModel.get("gps");
            @Nullable
            Mitigps gps=new Mitigps();
            if(kv!=null){
                gps=gson.fromJson(kv.mitivalue,Mitigps.class);
            }
            if(gps.longitude==null){
                gps=new Mitigps();
            }
            Request request = new Request.Builder()
                    .url(url+params[0])
                    .method("POST", body)
                    .addHeader("Miti-Cookie", cookie)
                    .addHeader("Access-Type", "Public")
                    .addHeader("ActualFileName", params[2])
                    .addHeader("Format", "png")
                    .addHeader("Latitude", gps.latitude)
                    .addHeader("Longitude", gps.longitude)
                    .addHeader("RequestId", params[3])
                    .addHeader("Content-Type", "multipart/form-data; boundary=--------------------------539616771520821553345336")
                    .build();
            try{
                Response response = client.newCall(request).execute();
                return response.toString();
            }catch (Exception e){
                Log.e("Control",e.toString());
                return null;
            }
        }
    }
}
