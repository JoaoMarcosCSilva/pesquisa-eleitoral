package com.herokuapp.pesquisa_eleitoral;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Candidato extends AppCompatActivity {

    Button button_candidato;
    RadioGroup grupo_candidato;

    void next(){
        int candidato_id = grupo_candidato.getCheckedRadioButtonId();
        RadioButton candidato_button = findViewById(candidato_id);
        int candidato = grupo_candidato.indexOfChild(candidato_button);

        Entries.current.candidato = candidato;

        Intent intent = new Intent(this, Rejeita.class);
        startActivity(intent);
        MainActivity.activities.add(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidato);

        grupo_candidato = findViewById(R.id.group_candidato);
        button_candidato = findViewById(R.id.button_go_candidato);
        button_candidato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
    }


}
