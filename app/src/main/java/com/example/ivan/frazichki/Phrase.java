package com.example.ivan.frazichki;

/**
 * Created by Ivan on 2/22/2015.
 */
public class Phrase {
    private String phrase;
    private String translation;

    public Phrase(String phrase, String translation) {
        this.phrase = phrase;
        this.translation = translation;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
