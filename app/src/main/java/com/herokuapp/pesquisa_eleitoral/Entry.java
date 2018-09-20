package com.herokuapp.pesquisa_eleitoral;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Entry {
    public Entry(){
        candidato = -1;
        rejeita = new ArrayList<Integer>();
        classe_social = -1;
        genero = -1;
        criado_em = "";
    }

    int candidato;
    ArrayList<Integer> rejeita;
    int classe_social;
    int genero;
    String criado_em;
}
