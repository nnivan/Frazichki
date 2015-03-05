package com.ivan.frazichki;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddPhraseFromSentenceTask extends AsyncTask<String, Void, List<StringBuilder>> {

    private List<StringBuilder> phrases = new ArrayList<>();

    private List<List<Integer>> phraseInt = new ArrayList<>(1024);

    protected List<StringBuilder> doInBackground(String... params) {
        List<StringBuilder> phrases = new ArrayList<>();

        for(String sent : params) {
            List<StringBuilder> currPhrases = makePhrase(sent);
            phrases.addAll(currPhrases);
        }

        return phrases;
    }

    protected void onPostExecute(List<StringBuilder> phrases) {

        PhraseModel pm = PhraseModel.getInstance();

        for(StringBuilder s : phrases){
            pm.addNewWord(new Phrase(s.toString(), s.toString() + "1"));
        }

    }

    public String getLexicalDAG(String sentence) {
        sentence = sentence.replace(" ", "%20");
        String dag = httpGet("http://marinshalamanov.com/PhrasesServer/Parse?s=%22" + sentence + "%22");
        return dag;
    }

    public String httpGet(String url) {

        String result = "";

        HttpClient httpclient = new DefaultHttpClient();

        HttpResponse httpResponse = null;
        try {
            httpResponse = httpclient.execute(new HttpGet(url));
            //Thread.sleep(1000);
            result = EntityUtils.toString(httpResponse.getEntity());
        } catch (IOException e) {
            return result;
        }
        return result;
    }


    public void buildPhrase(String input) {


        input = input.replace("\r","");

        int wordsCount, pairsCount;
        String buffer;
        int newLineIndex = input.indexOf("\n");

        buffer = input.substring(0, newLineIndex);

        int spaceIndex = buffer.indexOf(" ");

        pairsCount = Integer.parseInt(buffer.substring(0, spaceIndex));
        wordsCount = Integer.parseInt(buffer.substring(spaceIndex + 1, buffer.length()));

        String[] words = new String[wordsCount];

        Map<Integer, List<Integer>> tree = new HashMap<>();
        for (int i = 0; i < wordsCount; i++) {
            tree.put(i, new ArrayList<Integer>());
        }

        for (int i = 0; i < pairsCount; i++) {

            int oldnewLineIndex = newLineIndex+1;
            newLineIndex = input.indexOf("\n", oldnewLineIndex);
            buffer = input.substring(oldnewLineIndex, newLineIndex);

            int end1, end2, end3, end4;

            end1 = buffer.indexOf(" ");
            end2 = buffer.indexOf(" ", end1+1);
            end3 = buffer.indexOf(" ", end2 + 1);
            end4 = buffer.length();

            String aStr, bStr;
            int aInt, bInt;

            aStr = buffer.substring(0, end1);
            aInt = Integer.parseInt(buffer.substring(end1 + 1, end2));
            bStr = buffer.substring(end2+1, end3);
            bInt = Integer.parseInt(buffer.substring(end3 + 1, end4));
            aInt--;
            bInt--;

            if (aInt >= 0 && bInt >= 0){
                words[aInt] = new String(aStr);
                words[bInt] = new String(bStr);

                tree.get(aInt).add(bInt);
                tree.get(bInt).add(aInt);
            }


        }

        boolean used[] = new boolean[wordsCount];
        Arrays.fill(used, false);

        for (int p = 0; p < wordsCount; p++) {
            if (tree.get(p).size() == 1) {
                phraseInt.add(new ArrayList<Integer>());
                phraseInt.get(phraseInt.size()-1).add(p);
                used[p] = true;
                recPhrase(p,tree,words,used);
                used[p] = false;
            }
        }



        for(int i=0;i<phraseInt.size();i++){
            Collections.sort(phraseInt.get(i));
            phrases.add(new StringBuilder());
            for(int j : phraseInt.get(i)){
                phrases.get(i).append(words[j] + " ");
            }
        }

    }

    private void recPhrase(int x, Map<Integer, List<Integer>> tree, String[] words,boolean used[]){
        int copyOf = phraseInt.size()-1;
        for(int i=0;i<tree.get(x).size();i++){
            int k = tree.get(x).get(i);

            if(tree.get(x).size()<=tree.get(k).size() && !used[k]){

                List<Integer> buff = new ArrayList<>(phraseInt.get(copyOf));
                buff.add(k);
                phraseInt.add(buff);

                used[k] = true;
                recPhrase(k,tree,words,used);
                used[k] = false;
            }
        }
    }

    public List<StringBuilder> makePhrase(String sentence){
        phrases = new ArrayList<>();
        phraseInt = new ArrayList<>(1024);

        String lexicalDAG = getLexicalDAG(sentence);
        buildPhrase(lexicalDAG);

        return phrases;
    }
}