package com.example.beespelled.beespelled;

import android.content.Context;

import java.io.IOException;
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
        words = new ArrayList<>();
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

    public String getWordStats(Context c, String word, int recent) {
        String stats = "There are no statistics available for this word";
        try {
            Word w = Data_Static.getWordData(c, word);
            int successes = 0;
            List<Boolean> history = w.history;
            while (recent>0) {
                if (recent > history.size()) recent--;
                else if (recent == history.size()) break;
                else history.remove(0);
            }
            for (int i = 0; i < recent; i++) {
                if (history.get(i) == true) successes++;
            }
            if(recent<0) recent = 0;
            stats = "Statistics for " + word + ": " + successes + " out of the last " + recent + " correct";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stats;
    }
}
