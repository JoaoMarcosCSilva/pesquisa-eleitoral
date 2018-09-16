package com.herokuapp.pesquisa_eleitoral;

import java.util.ArrayList;

public class Collection {
    public
    ArrayList<Entry> info;
    public
    String criador;
    public Collection(String username){
        info = new ArrayList<Entry>();
        criador = username;
    }
}

