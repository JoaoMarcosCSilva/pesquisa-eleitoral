package com.herokuapp.pesquisa_eleitoral;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Renda extends AppCompatActivity {

    Button go_renda;
    RadioGroup group_class;

    void next(){
        int class_id = group_class.getCheckedRadioButtonId();
        RadioButton class_button = findViewById(class_id);
        int classe = group_class.indexOfChild(class_button);

        Entries.current.classe_social = classe;


        Intent intent = new Intent(this, Candidato.class);
        startActivity(intent);
        MainActivity.activities.add(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renda);

        go_renda = findViewById(R.id.button_go_renda);

        group_class = findViewById(R.id.group_class);

        go_renda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
    }
}
