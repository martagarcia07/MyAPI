package com.appcnt.meekooloh.myapi;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by marta on 18/09/16.
 */
public class LongRunningGetIO extends AsyncTask<Void, Void, String> {
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
    protected String doInBackground(Void... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String API_KEY="668e6f325c05a568";
        String email = "bart@fullcontact.com";
        HttpGet httpGet = new HttpGet("https://api.fullcontact.com/v2/person.json?" + "email=" + email +  "&apiKey=" +API_KEY);
        String text = null;
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            text = getASCIIContentFromEntity(entity);
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return text;
    }

    protected void onPostExecute(String results) {

        if (results!=null) {
//            EditText et = (EditText)findViewById(R.id.my_edit);
 //           et.setText(results);
        Log.d("marta", "error");
        }
        Log.d("resultad",results);
        //Button b = (Button)findViewById(R.id.my_button);
        //b.setClickable(true);


    }
}