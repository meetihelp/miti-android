package com.miti.meeti.MitiExecutors.MitiRunnables;

import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.RequestHelper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.miti.meeti.mitiutil.network.POSTRequest.CONNECTION_TIMEOUT;
import static com.miti.meeti.mitiutil.network.POSTRequest.Domain;
import static com.miti.meeti.mitiutil.network.POSTRequest.READ_TIMEOUT;
import static com.miti.meeti.mitiutil.network.POSTRequest.REQUEST_METHOD;

public class SimplePOST {
    public RequestHelper execute(String ...strings){
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
}
