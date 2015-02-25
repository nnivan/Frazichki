package com.example.ivan.frazichki;

import android.content.Context;
import android.net.Uri;

import com.example.ivan.frazichki.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class PhraseExtraction {

    public static String compi(String[] args, Context context) throws FileNotFoundException {

        //String uri = "android.resource://" + context.getPackageName() + "/raw/english/englishPCFG.ser";
        //Uri uri = Uri.parse("android.resource://com.example.ivan/raw/englishPCFG.ser");
        //Uri.parse("android.resource://com.example.ivan/english/englishPCFG.ser");

        //Uri uri = Uri.parse("android.resource://com.example.ivan/" + R.raw.englishPCFG_ser);
        String uri = "android.resource://com.example.ivan/" + R.raw.englishPCFG_ser;


        String grammar = args.length > 0 ? args[0] : uri;
        String outputFile = args[1];
        String sent2 = args[2];


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
    }
}
