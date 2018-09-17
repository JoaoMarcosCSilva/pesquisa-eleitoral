package com.herokuapp.pesquisa_eleitoral;

public class EntryFile {
    public Collection data;
    public EntryFile(String username) {
        data = new Collection(username);
    }
    public static EntryFile join (EntryFile a, EntryFile b){
        Collection result = new Collection(a.data.criador);
        for(int i = 0; i < a.data.info.size(); i++){
            result.info.add(a.data.info.get(i));
        }
        for(int i = 0; i < b.data.info.size(); i++){
            result.info.add(b.data.info.get(i));
        }
        EntryFile file = new EntryFile(a.data.criador);
        file.data = result;
        return file;
    }
}
