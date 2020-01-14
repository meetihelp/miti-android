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


public class SendChatImage extends AsyncTask<String, Void, String> {
    //param0 suburl, param1 fileaddress, param2 filename, param3 requestid, param4 public, parms5 createdat,
    // param6 cookie
    public String url="http://meeti.club:8000/";
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected  String doInBackground(String... params){
        Mlog.e("inSendchatImage",params[0],params[1],params[2],params[3]);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=--------------------------539616771520821553345336");
        Mlog.e("inSendchatImage","line37");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("myFile","try.jpeg",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File(params[1])))
                .build();
        Mlog.e("inSendchatImage","line42");
//        String cookie=MainActivity.cookieViewModel.getCookie1();
        Mlog.e("inSendchatImage","line43");
//        Gson gson=new Gson();
//        keyvalue kv=MainActivity.keyvalueViewModel.get("gps");
        Mlog.e("inSendchatImage","line45");
//        @Nullable
//        Mitigps gps=new Mitigps();
//        if(kv!=null){
//            gps=gson.fromJson(kv.mitivalue,Mitigps.class);
//        }
//        if(gps.longitude==null){
//            gps=new Mitigps();
//        }
        Request request = new Request.Builder()
                .url(url+params[0])
                .method("POST", body)
                .addHeader("Miti-Cookie", params[6])
                .addHeader("Access-Type", params[4])
                .addHeader("Actual-Filename", params[2])
                .addHeader("Format", "jpeg")
                .addHeader("Latitude", "1.00556481")
                .addHeader("Longitude", "1.0486194")
                .addHeader("Request-Id", params[3])
                .addHeader("Created-At", params[5])
                .addHeader("Content-Type", "multipart/form-data; boundary=--------------------------539616771520821553345336")
                .build();
        Mlog.e("inSendchatImage","line67");
        try{
            Mlog.e("inSendchatImage","line69");
            Response response = client.newCall(request).execute();
            Mlog.e("inSendchatImage","Success");
            return response.toString();
        }catch (Exception e){
            Mlog.e("inSendchatImage",e.toString());
            return null;
        }
    }
}
