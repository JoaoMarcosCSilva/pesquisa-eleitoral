package com.herokuapp.pesquisa_eleitoral;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Entries {

    public static String[] str_renda     = {"Acima de 20 Salários Mínimos","De 10 a 20 Salários Mínimos","De 4 a 10 Salários Mínimos","De 2 a 4 Salários Mínimos","Até 2 Salários Mínimos", "Prefiro não responder"};
    public static String[] str_sexo      = {"Masculino","Feminino"};
    public static String[] str_candidato = {"Álvaro Dias (Podemos) - 19","Cabo Daciolo (Patriota) - 51","Ciro Gomes (PDT) - 12","Eymael (DC) - 27","Fernando Haddad (PT) - 13","Geraldo Alckmin (PSDB) - 45","Guilherme Boulos (PSOL) - 50","Henrique Meirelles (MDB) - 15","Jair Bolsonaro (PSL) - 17","João Amoêdo (Novo) - 30","João Goulart Filho (PPL) - 54","Marina Silva (Rede) - 18","Vera Lúcia (PSTU) - 16", "Branco/Nulo", "Não sei/Não quero responder"};

    public static Entry current;


    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static EntryFile entradas;

    public static String loadFromFile(){
        if (!isExternalStorageReadable())return "";
        String data = "";


        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "resultados_pesquisa.txt");

            FileReader freader = new FileReader(file);
            BufferedReader breader = new BufferedReader(freader);

            String sCurrentLine;

            while ((sCurrentLine = breader.readLine()) != null) {
                data += (sCurrentLine);
            }
            Gson gson = new Gson();
            entradas = gson.fromJson(data, EntryFile.class);

            return data;

        }catch(Exception e){
            Log.d("io","File reading error: " + e.getMessage());
            entradas = new EntryFile(MainActivity.username);
            return "";
        }
    }
    private static void savetofile () {
        if (!isExternalStorageWritable())return;
        String data = "";

        Gson gson = new Gson();
        data = gson.toJson(entradas);


        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            path.mkdirs();
            File file = new File(path, "resultados_pesquisa.txt");
            FileWriter fwriter = new FileWriter(file);
            BufferedWriter bwriter = new BufferedWriter(fwriter);
            bwriter.write(data);
            bwriter.close();

        }catch(Exception e){
            Log.d("io","File writing error: " + e.getMessage());
        }
    }
    public static void save(){
        entradas.data.info.add(current);
        current = new Entry();

        for (Activity a : MainActivity.activities){
            a.finish();
        }

        savetofile();

        MainActivity.reset();
    }
    public static void erase(){
        current = new Entry();
    }
}
