package com.example.beespelled.beespelled;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Word implements Serializable {
    public String text;
    public int attempts;
    public int successes;
    public List<Boolean> history;
    public short refs;
    
    public Word(String word){
        text=word;
        attempts=0;
        successes=0;
        history=new ArrayList<Boolean>();
        refs = 1;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAttempts() {
        return attempts;
    }

    public void incAttempts() {
        this.attempts++;
    }

    public int getSuccesses() {
        return successes;
    }

    public void incSuccesses() { this.successes++; }

    public List<Boolean> getHistory() {
        return history;
    }

    public void addToHistory() {
        return; //TODO THOMAS
    }

    public int getRefs() {
        return refs;
    }

    public void incRefs(){ refs++;}

    public int decRefs(){ return refs--;}


}
