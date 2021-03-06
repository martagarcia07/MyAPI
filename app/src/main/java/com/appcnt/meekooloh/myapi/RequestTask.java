package com.appcnt.meekooloh.myapi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.ClientProtocolException;

/**
 * Created by marta on 20/09/16.
 */
class RequestTask extends AsyncTask<String, String, String> {
    protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
        InputStream in = entity.getContent();
        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n>0) {
            byte[] b = new byte[4096];
            n =  in.read(b);
            if (n>0) out.append(new String(b, 0, n));
        }
        return out.toString();
    }
    @Override
    protected String doInBackground(String... uri) {
        String responseString = null;
        String API_KEY = "668e6f325c05a568";
        String email = "bart@fullcontact.com";
        String urlString = "https://api.fullcontact.com/v2/person.json?" + "email=" + email +  "&apiKey=" +API_KEY;
        //String result=
        try {
            String myurl = urlString;
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                // Do normal input or output stream reading
                int status = conn.getResponseCode();

                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line+"\n");
                        }
                        br.close();
                        //System.out.println("mensaje = " + sb);
                        return sb.toString();
                }
            }
            else {
                String response = "FAILED"; // See documentation for more info on response handling
                Log.d("marta", response);
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        return responseString;
    }
    private final Context Asyntaskcontext;

    public RequestTask(Context context){
        Asyntaskcontext = context;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("marta",result);
        MainActivity mainactivity = (MainActivity) Asyntaskcontext;
        mainactivity.retunnumfromAsyncTask = result;
        super.onPostExecute(result);

    }

}