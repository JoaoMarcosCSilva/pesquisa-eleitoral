package com.herokuapp.pesquisa_eleitoral;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

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

    LinearLayout layout;

    HttpTransport httpTransport = new NetHttpTransport();
    JsonFactory jsonFactory = new GsonFactory();

    String address = "https://kinto.dev.mozaws.net/v1/buckets/default/collections/tasks/records";
    String post = "/v1/buckets/default/collections/tasks/records";
    String host = "kinto.dev.mozaws.net";

    String authorization = "";
    long content_length = 0;

    void printLabel(String s){
        TextView t = new TextView(this);
        t.setText(s);
        layout.addView(t);
    }

    void upload(int counter){
        try {
            HttpRequestInitializer httpRequestInitializer = new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest httpRequest) throws IOException {
                    httpRequest.setParser(new JsonObjectParser(jsonFactory));
                }
            };

            HttpRequestFactory requestFactory = httpTransport.createRequestFactory(httpRequestInitializer);
            GenericUrl url = new GenericUrl(address);

            HttpContent httpContent;


            HttpRequest httpRequest = requestFactory.buildPostRequest(url, httpContent);

            HttpHeaders httpHeaders = httpRequest.getHeaders();

            authorization = MainActivity.username + ":" + MainActivity.username;
            authorization = new String(Base64.encodeBase64(authorization.getBytes()));

            httpHeaders.setAccept("application/json");
            httpHeaders.setAcceptEncoding("gzip, deflate");
            httpHeaders.setAuthorization(authorization);
            httpHeaders.set("Connection","keep-alive");
            httpHeaders.setContentLength(content_length);
            httpHeaders.setContentType("application/json");
            httpHeaders.set("Host", host);
            httpHeaders.setUserAgent("com.herokuapp.pesquisa_eletoral");

            HttpResponse httpResponse = httpRequest.execute();

            int code = httpResponse.getStatusCode();
            if (code != 401){
                String message = httpResponse.getStatusMessage();
                printLabel(code + ": " + message);
            }
        }catch(Exception e){
            printLabel(e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        layout = findViewById(R.id.layout_submit);
        upload(0);

    }


}
