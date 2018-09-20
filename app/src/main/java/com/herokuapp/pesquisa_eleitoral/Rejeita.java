
package com.herokuapp.pesquisa_eleitoral;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class Rejeita extends AppCompatActivity {

    Button button_rejeita;
    Button button_naoresponde;
    LinearLayout layout_candidatos;

    void next(){

        ArrayList<Integer> rejeicoes = new ArrayList<Integer>();

        for (int i = 0; i < layout_candidatos.getChildCount(); i++){
            CheckBox cb = (CheckBox) layout_candidatos.getChildAt(i);
            if (cb.isChecked()){
                rejeicoes.add(i);
            }
        }

        Entries.current.rejeita = rejeicoes;



        Intent intent = new Intent(this, Finish.class);
        startActivity(intent);
        MainActivity.activities.add(this);
    }

    private void next_nao(){

        ArrayList<Integer> rejeicoes = new ArrayList<Integer>();

        rejeicoes.add(Entries.str_candidato.length-1);

        Entries.current.rejeita = rejeicoes;



        Intent intent = new Intent(this, Finish.class);
        startActivity(intent);
        MainActivity.activities.add(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejeita);


        button_naoresponde = findViewById(R.id.button_naoresponde_rejeita);
        button_rejeita = findViewById(R.id.button_go_rejeita);

        button_rejeita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        button_naoresponde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_nao();
            }
        });

        layout_candidatos = findViewById(R.id.layout_botoes_rejeita);
    }
}
