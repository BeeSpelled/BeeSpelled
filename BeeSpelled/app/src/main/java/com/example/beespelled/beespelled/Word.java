package com.example.beespelled.beespelled;


public class Word {
    String text;
    int attempts;
    int successes;
    Boolean[] history;
    
    public Word(String word){
        text=word;
        attempts=0;
        successes=0;
        history=new Boolean[]{};
    }
}
