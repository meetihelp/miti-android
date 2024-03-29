package com.miti.meeti.mitiutil.network;

import android.os.AsyncTask;
import android.util.Log;

import com.miti.meeti.mitiutil.Logging.Mlog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GETRequest extends AsyncTask<HashMap<String,String>,Void, RequestHelper> {

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    public static final String Domain="https://api.meeti.club:9000";
    public void helper(HashMap<String,String>temp){

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected RequestHelper doInBackground(HashMap<String,String>... Has) {
        HashMap<String,String> Dic=Has[0];
        Set<String> keys = Dic.keySet();
        String url =Domain+Dic.get("url");
        Mlog.e("Get request url",url);
        String result = "";
        String MitiCookie="";
        try {
            URL myUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            for(String key:keys){
                if(key.compareTo("url")!=0){
                    Mlog.e("GET request header setting here");
                    Mlog.e(key,Dic.get(key));
                    connection.addRequestProperty(key,Dic.get(key));
                    Mlog.e("GET request header setting ended");
                }
            }
            connection.connect();
            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            reader.close();
            streamReader.close();
            result = stringBuilder.toString();
            Map<String, List<String>> mapx = connection.getHeaderFields();
            MitiCookie=connection.getHeaderField("Miti-Cookie");
        } catch (Exception e) {
            Log.e("Control","mitiutil/network/getrequest->"+e.toString());
            result = null;
        }
        RequestHelper requestHelper=new RequestHelper(MitiCookie,result);
        return requestHelper;
    }

    @Override
    protected void onPostExecute(RequestHelper result) {
        super.onPostExecute(result);
    }
}
