package com.example.beespelled.beespelled;


import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Word implements Serializable {
    public String text;
    public int attempts;
    public int successes;
    public List<Boolean> history;
    public short refs;
    private static final short LENGTHOFHISTORY = 10;
    private static final long serialVersionUID = 2L;
    
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

    public void clearHistory(){
        history = new ArrayList<Boolean>();
        attempts = 0;
        successes = 0;
    }

    public void addAttempt(Boolean success){
        incAttempts();
        if (success) incSuccesses();
        addToHistory(success);
    }

    public double getPercentage(){
        return successes/(attempts * 1.0);
    }

    private void incAttempts() {
        this.attempts++;
    }

//    private int getSuccesses() {
//        return successes;
//    }

    private void incSuccesses() { this.successes++; }

    public List<Boolean> getHistory() {
        return history;
    }

    private void addToHistory(Boolean b) {
        history.add(b);
        if (history.size() > LENGTHOFHISTORY) history.remove(0);
        Log.d("Word", "addToHistory: ");
        Log.d("Word", history.toString());
    }

    public int getRefs() {
        return refs;
    }

    public void incRefs(){ refs++;}

    public int decRefs(){ return refs--;}


}
