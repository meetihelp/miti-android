package com.example.miti2.ui.utility;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class POSTRequest extends AsyncTask<String,Void,String> {

    public static final String REQUEST_METHOD = "POST";
    public static final int READ_TIMEOUT = 2000;
    public static final int CONNECTION_TIMEOUT = 2000;
    public static final String Domain="http://10.147.142.4:9000";
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... strings) {
        String url=Domain+"/"+strings[0];
        String result="";
        try {
            URL myUrl=new URL(url);
            HttpURLConnection connection=(HttpURLConnection)myUrl.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.connect();
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
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
        } catch (Exception e) {
            Log.e("Control",e.getMessage());
//            e.printStackTrace();
            result=null;
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
}

