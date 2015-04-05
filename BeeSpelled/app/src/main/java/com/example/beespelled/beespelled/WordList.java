package com.example.beespelled.beespelled;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WordList implements Serializable{
    public String name;
    public List<Word> words;

    public WordList(String n, List<Word> w){
        name=n;
        words=w;
    }

    public String getName(){
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public void setWords(List<Word> w) {
        words = w;
    }

    public List<Word> getWords(){
        return words;
    }

    public void addWord(String word){
        words.add(new Word(word));
    }

    public void addWords(String[] w){
        for (String aW : w) {
            addWord(aW);
        }
    }
}
