package com.ivan.frazichki;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.ivan.frazichki.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class PhraseExtraction {

    private static ArrayList<StringBuilder> phrases = new ArrayList<StringBuilder>();
    private static ArrayList<ArrayList<Integer>> phraseInt = new ArrayList<ArrayList<Integer>>(1024);

    public static void buildPhrase(String input) {

        int wordsCount, pairsCount;
        String buffer;
        int newLineIndex = input.indexOf("\n");

        buffer = input.substring(0, newLineIndex);

        int spaceIndex = buffer.indexOf(" ");

        pairsCount = Integer.parseInt(buffer.substring(0, spaceIndex));
        wordsCount = Integer.parseInt(buffer.substring(spaceIndex + 1, buffer.length()));

        String[] words = new String[wordsCount];

        HashMap<Integer, ArrayList<Integer>> tree = new HashMap<Integer, ArrayList<Integer>>();
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

            words[aInt] = new String(aStr);
            words[bInt] = new String(bStr);

            tree.get(aInt).add(bInt);
            tree.get(bInt).add(aInt);
        }

        boolean used[] = new boolean[wordsCount];
        Arrays.fill(used, false);

        for (int p = 0; p < wordsCount; p++) {
            if (tree.get(p).size() == 1) {
                phraseInt.add(new ArrayList<Integer>());
                phraseInt.get(phraseInt.size()-1).add(p);
                Log.e("asdfff", words[p]);
                used[p] = true;
                recPhrase(p,tree,words,used);
                used[p] = false;
            }
        }



        for(int i=0;i<phraseInt.size();i++){
            Collections.sort(phraseInt.get(i));
            phrases.add(new StringBuilder());
            for(int j : phraseInt.get(i)){
                Log.e("help", i + " " + j);
                phrases.get(i).append(words[j] + " ");
            }
        }

    }

    private static void recPhrase(int x, HashMap<Integer, ArrayList<Integer>> tree, String[] words,boolean used[]){
        int copyOf = phraseInt.size()-1;
        for(int i=0;i<tree.get(x).size();i++){
            int k = tree.get(x).get(i);

            if(tree.get(x).size()<=tree.get(k).size() && !used[k]){

                ArrayList<Integer> buff = new ArrayList<Integer>(phraseInt.get(copyOf));
                buff.add(k);
                phraseInt.add(buff);

                used[k] = true;
                recPhrase(k,tree,words,used);
                used[k] = false;
            }
        }
    }


    public static String compiler(String args, Context context) {

        String retu = "5 6\n" +
                "dog 2 My 1\n" +
                "likes 4 dog 2\n" +
                "likes 4 also 3\n" +
                "likes 4 eating 5\n" +
                "eating 5 sausage 6\n";
        return retu;

    }


    public static ArrayList<StringBuilder> makePhrase(Context context, String sentence){

        phrases = new ArrayList<StringBuilder>();
        phraseInt = new ArrayList<ArrayList<Integer>>(1024);
        buildPhrase(compiler(sentence, context));

        return phrases;
    }

}