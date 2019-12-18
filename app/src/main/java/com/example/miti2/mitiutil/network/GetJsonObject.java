package com.example.miti2.mitiutil.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class GetJsonObject {
//    private String[] Key={"Phone","Password"};
//    private String[] Value={"9304116447","mypassword"};
//
//    public GetJsonObject(String[] key, String[] value) {
////        this.Key=Key;
////        this.Value=Value;
//    }

    public String getJson(String[] Key,String[] Value) throws JSONException {
        int length=Key.length;
        JSONObject json=new JSONObject();
        for(int i=0;i<length;i++){
            json.put(Key[i],Value[i]);
        }

        return json.toString();
    }

    public int getIntValue(String result,String key){
        Log.e("Control",result);
        int value;
        try {
            if(result!=null) {
                JSONObject json = new JSONObject(result);
                value = json.getInt(key);
//                Log.e("Control",value);
                return value;
            }
            else{
                return 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getStringValue(String result, String key){
        Log.e("Control",result);
        String value;
        try {
            if(result!=null) {
                JSONObject json = new JSONObject(result);
                value = json.getString(key);
//                Log.e("Control",value);
                return value;
            }
            else{
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
