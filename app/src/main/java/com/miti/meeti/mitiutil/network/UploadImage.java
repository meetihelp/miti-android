package com.miti.meeti.mitiutil.network;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.Mitigps;
import com.miti.meeti.database.Keyvalue.keyvalue;
import com.miti.meeti.mitiutil.Logging.Mlog;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


    public class UploadImage extends AsyncTask<String, Void, String> {
        //param0 suburl, param1 fileaddress, param2 filename, param3 requestid, param4 public
        public String url="https://api.meeti.club:9000/";
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
            String cookie=MainActivity.MeetiCookie;
            Mlog.e("upoad image","ka params");
            Mlog.e(params);
            @Nullable
            Request request = new Request.Builder()
                    .url(url+params[0])
                    .method("POST", body)
                    .addHeader("Miti-Cookie", cookie)
                    .addHeader("Access-Type", params[4])
                    .addHeader("Actual-Filename", params[2])
                    .addHeader("Format", "jpg")
                    .addHeader("Latitude", MainActivity.Latitude)
                    .addHeader("Longitude", MainActivity.Longitude)
                    .addHeader("Request-Id", params[3])
                    .addHeader("Content-Type", "multipart/form-data; boundary=--------------------------539616771520821553345336")
                    .build();
            try{
                Mlog.e("upoad image","ka try block");
                Response response = client.newCall(request).execute();
                String respo=response.body().string().toString();
                Mlog.e("upload image","ka response",respo);
                return respo;
            }catch (Exception e){
                Log.e("Control",e.toString());
                return null;
            }
        }
    }

