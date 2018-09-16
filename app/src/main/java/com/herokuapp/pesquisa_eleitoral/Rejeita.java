
package com.herokuapp.pesquisa_eleitoral;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Rejeita extends AppCompatActivity {

    Button button_rejeita;
    RadioGroup grupo_rejeita;

    void next(){
        int rejeita_id = grupo_rejeita.getCheckedRadioButtonId();
        RadioButton rejeita_button = findViewById(rejeita_id);
        int rejeita = grupo_rejeita.indexOfChild(rejeita_button);

        Entries.current.rejeita = rejeita;

        Intent intent = new Intent(this, Finish.class);
        startActivity(intent);
        MainActivity.activities.add(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejeita);


        grupo_rejeita = findViewById(R.id.group_rejeita);
        button_rejeita = findViewById(R.id.button_go_rejeita);
        button_rejeita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
    }
}
