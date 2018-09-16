package com.herokuapp.pesquisa_eleitoral;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Interview extends AppCompatActivity {

    RadioGroup group_gender;
    RadioGroup group_class;

    Button button_go;


    void next(){
        int gender_id = group_gender.getCheckedRadioButtonId();
        RadioButton gender_button = findViewById(gender_id);
        int gender = group_gender.indexOfChild(gender_button);

        int class_id = group_class.getCheckedRadioButtonId();
        RadioButton class_button = findViewById(class_id);
        int classe = group_class.indexOfChild(class_button);

        Entries.current.classe_social = classe;
        Entries.current.genero = gender;

        Intent intent = new Intent(this, Candidato.class);
        startActivity(intent);
        MainActivity.activities.add(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);

        button_go = findViewById(R.id.button_go);

        group_gender = findViewById(R.id.group_gender);
        group_class = findViewById(R.id.group_class);

        button_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        Entries.erase();
    }
}
