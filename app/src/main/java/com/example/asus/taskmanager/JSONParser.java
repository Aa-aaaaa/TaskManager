package com.example.asus.taskmanager;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Maxim on 16.01.2018.
 */

public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // конструктор
    public JSONParser() {

    }

    /**
     * Получить ответ по ссылке в формате json
     * @param url запрашиваемая страница
     * @param method GET or POST
     * @param params параметры, которые необходимо передать
     * @return
     */
    public JSONObject makeHttpRequest(String url, String method,
                                      List<NameValuePair> params, String token) {

        // создаём HTTP запрос
        try {


            if(method == "POST"){
                Log.d("JSONParser", url);
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                if (token != null)
                    httpPost.setHeader("Authorization", "Token " + token);
                Log.d("JSONParser", params.toString());
                if (token != null)
                    Log.d("JSONParser", token);
                Log.d("JSONParser", new UrlEncodedFormEntity(params).toString());
                Log.d("JSONParser", httpPost.toString());

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } else if(method == "GET"){

                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
                if (token != null)
                    httpGet.setHeader("Authorization", "Token " + token);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            } else if (method == "PUT"){
                Log.d("JSONParser", url);
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPut httpPut = new HttpPut(url);
                httpPut.setEntity(new UrlEncodedFormEntity(params));
                if (token != null)
                    httpPut.setHeader("Authorization", "Token " + token);
                Log.d("JSONParser", params.toString());
                if (token != null)
                    Log.d("JSONParser", token);
                Log.d("JSONParser", new UrlEncodedFormEntity(params).toString());
                Log.d("JSONParser", httpPut.toString());

                HttpResponse httpResponse = httpClient.execute(httpPut);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            } else if (method == "DELETE"){
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpDelete httpDelete = new HttpDelete(url);
                if (token != null)
                    httpDelete.setHeader("Authorization", "Token " + token);

                HttpResponse httpResponse = httpClient.execute(httpDelete);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = null;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }


        // пробуем распарсит JSON объект
        try {
            jObj = new JSONObject(json);
            Log.d("JSONParser", json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
            Log.e("JSONParser", json);
        }


        return jObj;

    }
}
