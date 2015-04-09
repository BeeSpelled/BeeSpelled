package com.example.beespelled.beespelled;


import java.io.Serializable;

public class Word implements Serializable {
    public String text;
    public int attempts;
    public int successes;
    public Boolean[] history;
    public short refs;
    
    public Word(String word){
        text=word;
        attempts=0;
        successes=0;
        history=new Boolean[]{};
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

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public int getSuccesses() {
        return successes;
    }

    public void setSuccesses(int successes) {
        this.successes = successes;
    }

    public Boolean[] getHistory() {
        return history;
    }

    public void setHistory(Boolean[] history) {
        this.history = history;
    }

    public int getRefs() {
        return refs;
    }

    public void incRefs(){ refs++;}

    public int decRefs(){ return refs--;}


}
