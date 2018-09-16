package com.herokuapp.pesquisa_eleitoral;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String username = "";
    public static ArrayList<Activity> activities;
    private static Activity me;
    private LinearLayout layout;
    public static TextView username_label;
    protected void printLabel (String s){
        TextView tv = new TextView(this);
        tv.setText(s);
        layout.addView(tv);
    }

    public static void reset(){
        me.finish();
        me.startActivity(me.getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        me = this;
        int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }

        activities = new ArrayList<Activity>();

        layout = findViewById(R.id.main_layout);

        //checks if username exists (if don't, redirects the activity)

        File username_file = new File(getFilesDir(), "username");
        if (username_file.exists()){
            try{
                FileReader freader = new FileReader(username_file);
                BufferedReader breader = new BufferedReader(freader);
                username = breader.readLine();

            }catch (Exception e){
                printLabel(e.getMessage());
            }

        }else {
            try {
                Intent intent = new Intent(this, Username.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                Log.d("activity", e.getMessage());
            }
        }
        Entries.loadFromFile();
        TextView username_label = findViewById(R.id.username_label);

        username_label = findViewById(R.id.username_label);
        username_label.setText(username + " - " + Entries.entradas.info.size());

        Button entry_button = findViewById(R.id.new_entry_button);
        Button submit_button = findViewById(R.id.submit_button);

        entry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInterview();
            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

    }
    private void startInterview(){
        Intent intent = new Intent(this, Interview.class);
        startActivity(intent);
    }
    private void submit(){
        Intent intent = new Intent(this, Submit.class);
        startActivity(intent);
    }
}
