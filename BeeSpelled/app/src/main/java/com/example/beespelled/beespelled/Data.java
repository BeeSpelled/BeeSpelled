package com.example.beespelled.beespelled;


import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Data {
    FileOutputStream writer;
    FileInputStream reader;
    private Context context;


    public Data(Context c) throws IOException {
        context = c;
        writer = context.openFileOutput("lists.txt", Context.MODE_APPEND);
        reader = context.openFileInput("lists.txt");
    }

    public List<WordList> readLists() throws FileNotFoundException {
        Scanner s = new Scanner(reader);
        List <WordList> allLists = new ArrayList<>();
        while(s.hasNextLine()) {
            allLists.add(readList(s.nextLine()));
        }
        return allLists;
    }

    public WordList readList(String list){
        String[] l = list.split(" ");
        String name = "";
        Word w;
        List<Word> words = new ArrayList<>();
        for(int i=0; i<l.length;i++){
            if(i==0) name = l[i];
            else {
               w = new Word(l[i]);
               words.add(w);
            }
        }
        return new WordList(name, words);
    }

    public void writeList(WordList list) throws IOException {
        writer.write(list.name.getBytes());
        writer.write(" ".getBytes());
        List<Word> words = list.getWords();
        for(int i=0; i<words.size(); i++){
            writer.write(words.get(i).toString().getBytes());
            writer.write(" ".getBytes());
        }
        writer.write("\n".getBytes());
    }

    public void writeLists(List<WordList> lists) throws IOException {
        for(int i=0; i<lists.size(); i++){
            writeList(lists.get(i));
        }
    }

}
