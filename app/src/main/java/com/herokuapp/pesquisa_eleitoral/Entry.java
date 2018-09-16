package com.herokuapp.pesquisa_eleitoral;

public class Entry {
    public Entry(){
        candidato = -1;
        rejeita = -1;
        classe_social = -1;
        genero = -1;
        criado_em = "";
    }
    int candidato;
    int rejeita;
    int classe_social;
    int genero;
    String criado_em;
}
