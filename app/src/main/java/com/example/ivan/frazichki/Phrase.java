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

    @Override
    public String toString(){
        return (phrase + "@" + translation + "\r\n");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phrase phrase1 = (Phrase) o;

        if (phrase != null ? !phrase.equals(phrase1.phrase) : phrase1.phrase != null) return false;
        if (translation != null ? !translation.equals(phrase1.translation) : phrase1.translation != null)
            return false;

        return true;
    }


}