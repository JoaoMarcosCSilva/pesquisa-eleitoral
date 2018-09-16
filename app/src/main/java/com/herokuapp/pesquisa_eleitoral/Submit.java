package com.herokuapp.pesquisa_eleitoral;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandler;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.ExponentialBackOff;


public class Submit extends AppCompatActivity {

    int maxAttemts = 3;

    LinearLayout layout;

    HttpTransport httpTransport = new NetHttpTransport();
    JsonFactory jsonFactory = new GsonFactory();


    String post = "/v1/buckets/pesquisa/collections/" + MainActivity.username + "/records";
    String host = "kinto.dev.mozaws.net";

    String address = "https://" + host + post;
    String authorization = "";
    long content_length = 0;

    class uploadThread extends Thread {

        HttpResponse postData (String url, String username, String password, String data){
            HttpRequestInitializer httpRequestInitializer = new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest httpRequest) throws IOException {
                    httpRequest.setParser(new JsonObjectParser(jsonFactory));
                }
            };
            HttpRequestFactory requestFactory = httpTransport.createRequestFactory(httpRequestInitializer);
            GenericUrl genericUrl = new GenericUrl(url);
            try{
                HttpRequest httpRequest = requestFactory.buildPostRequest(genericUrl, ByteArrayContent.fromString("application/json", data));

                HttpHeaders httpHeaders = httpRequest.getHeaders();

                authorization = username + ":" + password;
                authorization = Base64.encodeBase64String(authorization.getBytes());

                httpHeaders.setAccept("application/json");
                httpHeaders.setAcceptEncoding("gzip, deflate");
                httpHeaders.setAuthorization("Basic " + authorization);
                httpHeaders.set("Connection","keep-alive");
                httpHeaders.setContentLength(content_length);
                httpHeaders.setContentType("application/json");
                httpHeaders.set("Host", host);
                httpHeaders.setUserAgent("com.herokuapp.pesquisa_eletoral");

                httpRequest.setHeaders(httpHeaders);

                HttpResponse httpResponse = httpRequest.execute();



                return httpResponse;
            }catch (Exception e){
                printLabel(e.getMessage());
                return null;
            }
        }
        void upload(int counter){
            if (counter >= maxAttemts){
                printLabel("Max attempts reached. Upload failed.");
                return;
            }

            HttpResponse httpResponse = postData(address, MainActivity.username, MainActivity.username, Entries.loadFromFile());

            try {
                int code = httpResponse.getStatusCode();
                if (code != 201){
                    String message = httpResponse.getStatusMessage();
                    printLabel(code + ": " + message);
                    upload(counter + 1);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Resultados enviados com sucesso", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }catch(Exception e){
                printLabel(e.getMessage());
            }
        }

        public void run(){
            upload(0);
        }
    }

    void printLabel(final String s){
        final Context context = this;
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                TextView t = new TextView(context);
                t.setText(s);
                layout.addView(t);

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        layout = findViewById(R.id.layout_submit);
        new uploadThread().start();

    }


}
