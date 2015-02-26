package com.ivan.frazichki;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

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

        ///
        String[] words = new String[wordsCount];

        HashMap<Integer, ArrayList<Integer>> tree = new HashMap<Integer, ArrayList<Integer>>();
        for (int i = 0; i < wordsCount; i++) {
            tree.put(i, new ArrayList<Integer>());
        }



        /*ArrayList<Integer>[] tree = new ArrayList[wordsCount];
        for(int i=0;i<wordsCount;i++){
            tree[i] = new ArrayList<Integer>();
        }*/


        for (int i = 0; i < pairsCount; i++) {

            int oldnewLineIndex = newLineIndex+1;
            newLineIndex = input.indexOf("\n", oldnewLineIndex);
            buffer = input.substring(oldnewLineIndex, newLineIndex);
            //Log.e("PhraseExtraction", buffer);

            int end1, end2, end3, end4;

            end1 = buffer.indexOf(" ");
            end2 = buffer.indexOf(" ", end1+1);
            end3 = buffer.indexOf(" ", end2 + 1);
            end4 = buffer.length();

            String aStr, bStr;
            int aInt, bInt;

            aStr = buffer.substring(0, end1);
            aInt = Integer.parseInt(buffer.substring(end1 + 1, end2));
            bStr = buffer.substring(end2, end3);
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
            if (tree.get(p).size() < 2) {
                phraseInt.add(new ArrayList<Integer>(p));
                //phraseInt.get(phraseInt.size()).add(p);
                ///phrases.add(words[p]);
                used[p] = true;
                recPhrase(p,tree,words,used);
                used[p] = false;
            }
        }

        for(int i=0;i<phraseInt.size();i++){
            Collections.sort(phraseInt.get(i));
            phrases.add(new StringBuilder());
            for(int j : phraseInt.get(i)){
                phrases.get(i).append(words[j]);
            }
        }

    }

    private static void recPhrase(int x, HashMap<Integer, ArrayList<Integer>> tree, String[] words,boolean used[]){
        int copyOf = phraseInt.size()-1;
        ///int copyOf = phrases.size()-1;
        for(int i=0;i<tree.get(x).size();i++){
            int k = tree.get(x).get(i);

            if(tree.get(x).size()<=tree.get(k).size() && !used[k]){

                ArrayList<Integer> buff = new ArrayList<Integer>(phraseInt.get(copyOf));
                buff.add(k);
                phraseInt.add(buff);

                //phrases.add(phrases.get(copyOf) +" "+ words[k]);
                used[k] = true;
                recPhrase(k,tree,words,used);
                used[k] = false;
            }
        }
    }


    public static String compiler(String args, Context context) /*throws FileNotFoundException */{


        String retu = "5 6\n" +
                "dog 2 My 1\n" +
                "likes 4 dog 2\n" +
                "likes 4 also 3\n" +
                "likes 4 eating 5\n" +
                "eating 5 sausage 6\n";
        return retu;


/*

        //FIX
        //String uri = "android.resource://" + context.getPackageName() + "/raw/english/englishPCFG.ser";
        //Uri uri = Uri.parse("android.resource://com.ivan/raw/englishPCFG.ser");
        //Uri.parse("android.resource://com.ivan/english/englishPCFG.ser");

        //Uri uri = Uri.parse("android.resource://com.ivan/" + R.raw.englishPCFG_ser);

        //String uri = "android.resource://com.ivan/" + R.raw.englishpcfg;
        String uri = new String();
        try {
            uri = context.getAssets().openFd("englishpcfg.gz").getFileDescriptor().toString();
        }catch (IOException e){
            Log.e("PhraseExtraction",e.toString());
        }
        //Uri path = Uri.parse("file:///android_asset/englishpcfg.gz");

        //String uri = path.toString();

        //String grammar = args.length > 0 ? args[0] : uri;
        String grammar = uri;
        //String outputFile = args[1];
        String sent2 = args;

        Log.e("compiler", grammar);


        //PrintWriter pw = new PrintWriter(new File(outputFile));
        StringBuilder ret = new StringBuilder();

        String[] options = { "-maxLength", "80", "-retainTmpSubcategories" };
        LexicalizedParser lp = LexicalizedParser.loadModel(grammar, options);
        TreebankLanguagePack tlp = lp.getOp().langpack();
        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();

        // String sent2 =
        // "This is a slightly longer and more complex sentence requiring tokenization.";

        Tokenizer<? extends HasWord> toke = tlp.getTokenizerFactory().getTokenizer(new StringReader(sent2));
        List<? extends HasWord> sentence = toke.tokenize();

        Tree parse = lp.parse(sentence);

        GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
        List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();

        //pw.println(tdl.size() + " " + sentence.size());
        ret.append(tdl.size() + " " + sentence.size() + "\r\n");
        for (TypedDependency dp : tdl) {
            dp.toString();
            String govStr = dp.gov().toString().replace('-', ' ');
            String govDep = dp.dep().toString().replace('-', ' ');
            //pw.println(govStr + " " + govDep);
            ret.append((govStr + " " + govDep) + "\r\n");
        }

        return ret.toString();

        //pw.close();

        // system("java -jar ivan.jar out.txt \"This is my s\".");

*/
    }


    public static ArrayList<StringBuilder> makePhrase(Context context, String sentence){
        buildPhrase(compiler(sentence, context));

        return phrases;
    }

}
