package com.herokuapp.pesquisa_eleitoral;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Finish extends AppCompatActivity {
    Button button_confirm;

    TextView txt_sexo;
    TextView txt_renda;
    TextView txt_candidato;
    TextView txt_rejeita;

    void confirm(){
        MainActivity.activities.add(this);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        Entries.current.criado_em = formatter.format(date);
        Entries.save();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        txt_sexo = findViewById(R.id.txt_sexo);
        txt_sexo.setText("Sexo: " + Entries.str_sexo[Entries.current.genero]);

        txt_renda = findViewById(R.id.txt_renda);
        txt_renda.setText("Renda Familiar Mensal: " + Entries.str_renda[Entries.current.classe_social]);

        txt_candidato = findViewById(R.id.txt_candidato);
        txt_candidato.setText("Candidato: " + Entries.str_candidato[Entries.current.candidato]);


        txt_rejeita = findViewById(R.id.txt_rejeita);

        LinearLayout layout = findViewById(R.id.layout_finish);

        if (Entries.current.rejeita.size() == 0){
            txt_rejeita.setText("Rejeição: Nenhuma");
        }else{
            txt_rejeita.setText(Entries.current.rejeita.size() == 1 ?  "Rejeição:" : "Rejeições:");
            for (int i : Entries.current.rejeita){
                TextView txt = new TextView(this);
                txt.setText(Entries.str_candidato[i]);
                txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                layout.addView(txt);
            }

        }

        button_confirm = new Button(this);
        button_confirm.setText("CONFIRMAR");
        button_confirm.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });
        layout.addView(button_confirm);
    }
}
