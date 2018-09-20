package com.herokuapp.pesquisa_eleitoral;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.google.gson.Gson;


public class Submit extends AppCompatActivity {

    int maxAttemts = 3;

    LinearLayout layout;



    String post = "/v1/buckets/pesquisa/collections/" + MainActivity.username + "/records";
    String host = "kinto-pesquisa.herokuapp.com";

    String address_col = "https://" + host + "/v1/buckets/pesquisa/collections";
    String address = "https://" + host + post;

    class uploadThread extends Thread {

        void upload(int counter){
            if (counter >= maxAttemts){
                printLabel("Max attempts reached. Upload failed.");
                return;
            }

            CollectionFile collectionFile = new CollectionFile();
            collectionFile.data.id = MainActivity.username;

            String collection_data = new Gson().toJson(collectionFile);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();

            String data = Entries.loadFromFile(formatter.format(date));

            String auth = MainActivity.username + ":" + MainActivity.username;

            HttpResponse responseCollection = Kinto.post_data(address_col, host, collection_data, auth);

            Logger l = Logger.getLogger(HttpTransport.class.getName());
            l.setLevel(Level.ALL);

            HttpResponse httpResponse = Kinto.post_data(address, host, data, auth);




            if (Kinto.lastError != ""){
                printLabel(Kinto.lastError);
            }else{
                int code_col = responseCollection.getStatusCode();
                int code = httpResponse.getStatusCode();


                if (!(code_col == 200 || code_col == 201) && (code != 200 || code != 201)){
                    String message = httpResponse.getStatusMessage();
                        printLabel(code_col + ": " + message);
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
