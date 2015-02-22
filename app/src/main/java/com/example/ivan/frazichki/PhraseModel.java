package com.example.ivan.frazichki;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 2/22/2015.
 */
public class PhraseModel {
    private List<Phrase> list = new ArrayList<Phrase>();
    private Context context;
    private String fileName;

    private static PhraseModel singleton = null;
    public static PhraseModel getInstance () {
        return singleton;
    }
    public static PhraseModel createInstance (Context context, String fileName) {
        if(singleton == null) {
            singleton = new PhraseModel(context, fileName);
        }
        return singleton;
    }

    private PhraseModel(Context context,String fileName) {
        this.fileName = fileName;
        this.context = context;
        try {
            InputStream inputStream = context.openFileInput(fileName);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ((receiveString = bufferedReader.readLine()) != null) {
                    int indexOfDelimiter = receiveString.indexOf('@');
                    String phrase = receiveString.substring(0, indexOfDelimiter);
                    String translation = receiveString.substring(indexOfDelimiter+1, receiveString.length());
                    list.add(new Phrase(phrase,translation));
                }

                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.i("File not found", e.toString());
        } catch (IOException e) {
            Log.i("Can not read file:", e.toString());
        }
    }

    public void addNewWord(Phrase phrase){

        try {
            OutputStreamWriter oswName = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_APPEND));
            oswName.write(phrase.toString());
            oswName.close();
            list.add(phrase);
            //Log.e(getClass().toString(), phrase.toString());
        } catch (IOException e) {
            Log.e("writeToFile", "File write failed: " + e.toString());
        }
    }

    private void updateFile(){
        StringBuilder builder = new StringBuilder();
        for(Phrase p:list){
            builder.append(p.toString());
        }

        try {
            File file = new File(context.getFilesDir(), fileName);
            file.delete();
            OutputStreamWriter oswName = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_APPEND));
            oswName.write(builder.toString());
            oswName.close();
        } catch (IOException e) {
            Log.e("writeToFile", "File write failed: " + e.toString());
        }
    }

    public boolean contains(Phrase phrase){
        return list.contains(phrase);
    }

    public void editPhrase(Phrase phrase, Phrase newPhrase){

        int phraseIndex  = list.indexOf(phrase);
        list.set(phraseIndex,newPhrase);
        updateFile();

    }

    public  void  removePhrase(Phrase phrase){
        list.remove(phrase);
        updateFile();
    }

    public void removeAll(){
        list.clear();
        updateFile();
    }

    public List<Phrase> getList() {
        return list;
    }

}
