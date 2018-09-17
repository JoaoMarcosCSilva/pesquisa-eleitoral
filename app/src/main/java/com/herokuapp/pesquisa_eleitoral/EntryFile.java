package com.herokuapp.pesquisa_eleitoral;

public class EntryFile {
    public Collection data;
    public EntryFile(String username) {
        data = new Collection(username);
    }
}
