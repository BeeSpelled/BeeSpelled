package com.example.beespelled.beespelled;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WordList implements Serializable{
    public String name;
    public List<String> words;

    public WordList(String n, List<String> w){
        name=n;
        words=w;
    }

    public WordList(String n){
        name = n;
        words = new ArrayList<String>();
        name = name;
    }


    public String getName(){
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public void setWords(List<String> w) {
        words = w;
    }

    public List<String> getWords(){
        return words;
    }

    public void addWord(String word){
        words.add(word);
    }

    public void addWords(List<String> w){
        for (String aW : w) {
            addWord(aW);
        }
    }
}
