package com.miti.meeti.mitiutil.network;

import android.os.AsyncTask;
import android.util.Log;


import com.miti.meeti.mitiutil.Logging.Mlog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class POSTRequest extends AsyncTask<String,Void, RequestHelper> {

    public static final String REQUEST_METHOD = "POST";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    public static final String Domain="https://api.meeti.club:8000";
    public POSTRequest(){

    }
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }
    @Override
    protected RequestHelper doInBackground(String... strings) {
        String url=Domain+"/"+strings[0];
        Mlog.e("Post url->",url);
        String result="";
        String MitiCookie="";
        try {
            URL myUrl=new URL(url);
            HttpURLConnection connection=(HttpURLConnection)myUrl.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            if(strings.length>2) {
                connection.addRequestProperty("Miti-Cookie", strings[2]);
                Mlog.e("in post request",strings[2]);
            }
            connection.connect();
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            Mlog.e("in POSTRequest",strings[1]);
            wr.writeBytes(strings[1]);
            wr.flush();
            wr.close();
            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String inputLine;
            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }
            reader.close();
            streamReader.close();
            result = stringBuilder.toString();
            MitiCookie=connection.getHeaderField("Miti-Cookie");
//            Log.e("Cookie",MitiCookie);
        } catch (Exception e) {
            Mlog.e(e);
            Mlog.printStackTrace(e,500);
            result=null;
        }
        RequestHelper requestHelper=new RequestHelper(MitiCookie,result);
        Mlog.e("POst request wala data");
        new Mlog<RequestHelper>().e1(requestHelper);
        return requestHelper;
    }

    @Override
    protected void onPostExecute(RequestHelper result){
        super.onPostExecute(result);
    }
}

