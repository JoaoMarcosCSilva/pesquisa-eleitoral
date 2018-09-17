package com.herokuapp.pesquisa_eleitoral;

import android.util.Log;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
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

import java.io.IOException;

public class Kinto {

    static HttpTransport httpTransport = new NetHttpTransport();
    static JsonFactory jsonFactory = new GsonFactory();
    static String lastError = "";



    public static HttpResponse post_data(String url,String host, String data, String auth){
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


            auth = Base64.encodeBase64String(auth.getBytes());

            httpHeaders.setAccept("application/json");
            httpHeaders.setAcceptEncoding("gzip, deflate");
            httpHeaders.setAuthorization("Basic " + auth);
            httpHeaders.set("Connection","keep-alive");
            httpHeaders.set("Host", host);
            httpHeaders.setUserAgent("com.herokuapp.pesquisa_eletoral");

            httpRequest.setHeaders(httpHeaders);

            HttpResponse httpResponse = httpRequest.execute();

            return httpResponse;
        }catch (Exception e){
            lastError = e.getMessage();
            return null;
        }
    }
}
