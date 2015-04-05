package com.example.beespelled.beespelled;


import java.io.Serializable;

public class Word implements Serializable {
    public String text;
    public int attempts;
    public int successes;
    public Boolean[] history;
    
    public Word(String word){
        System.out.println("Word " + word);
        text=word;
        attempts=0;
        successes=0;
        history=new Boolean[]{};
        System.out.println("text " + text);
    }

    @Override
    public String toString() {
        return text;
    }
}
