package com.herokuapp.pesquisa_eleitoral;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.Buffer;

public class Username extends AppCompatActivity {
    LinearLayout layout;

    protected void printLabel (String s){
        TextView tv = new TextView(this);
        tv.setText(s);
        layout.addView(tv);
    }

    private void launch(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        layout = findViewById(R.id.username_layout);
        final Button username_button = findViewById(R.id.username_button);
        final EditText username_text = findViewById(R.id.username_text);



        username_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String username = username_text.getText().toString();
                    File username_file = new File(getFilesDir(), "username");
                    FileWriter fwriter = new FileWriter(username_file);
                    BufferedWriter bwriter = new BufferedWriter(fwriter);
                    bwriter.write(username);
                    bwriter.close();

                    launch();
                }catch(Exception e){
                    printLabel(e.getMessage());
                }

            }
        });

    }
}
