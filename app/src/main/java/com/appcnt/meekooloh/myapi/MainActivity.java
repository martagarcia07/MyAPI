package com.appcnt.meekooloh.myapi;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.ClientProtocolException;

public class MainActivity extends Activity implements OnClickListener {
    public String retunnumfromAsyncTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //findViewById(R.id.my_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View arg0) {
        Button b = (Button) findViewById(R.id.my_button);
        //b.setClickable(false);
        String API_KEY = "668e6f325c05a568";
        String email = "bart@fullcontact.com";
        String urlString = "https://api.fullcontact.com/v2/person.json?" + "email=" + email + "&apiKey=" + API_KEY;

        TextView textView = (TextView) findViewById(R.id.my_edit);
        textView.setText("");
        new RequestTask(this).execute(urlString);
        Log.d("marta","caca");

    }
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
            MainActivity mainactivity = (MainActivity) Asyntaskcontext;
            mainactivity.retunnumfromAsyncTask = result;
            TextView textView = (TextView) findViewById(R.id.my_edit);
            textView.setText(retunnumfromAsyncTask);
            textView.setMovementMethod(new ScrollingMovementMethod());

            super.onPostExecute(result);
        }
    }
}

