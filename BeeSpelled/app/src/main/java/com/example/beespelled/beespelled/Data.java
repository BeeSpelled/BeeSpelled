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


<<<<<<< HEAD
//    public Data(Context c) throws IOException {
//        context = c;
//        writer = context.openFileOutput("lists.txt", Context.MODE_APPEND);
//        reader = context.openFileInput("lists.txt");
//    }
//
//    public List<String> readNames(List<WordList> lists){
//        List <String> listNames = new ArrayList<>();
//        for(int i=0; i<lists.size();++i){
//            listNames.add(lists.get(i).name);
//        }
//        return listNames;
//    }
//
//    public List<WordList> readLists() throws FileNotFoundException {
//        Scanner s = new Scanner(reader);
//        List <WordList> allLists = new ArrayList<>();
//        while(s.hasNextLine()) {
//            allLists.add(readList(s.nextLine()));
//        }
//        return allLists;
//    }
//
//    public WordList readList(String list){
//        String[] l = list.split(" ");
//        String name = "";
//        Word w;
//        List<Word> words = new ArrayList<>();
//        for(int i=0; i<l.length;i++){
//            if(i==0) name = l[i];
//            else {
//               w = new Word(l[i]);
//               words.add(w);
//            }
//        }
//        return new WordList(name, words);
//    }
//
//    public void writeList(WordList list) throws IOException {
//        writer.write(list.name.getBytes());
//        writer.write(" ".getBytes());
//        List<Word> words = list.getWords();
//        for(int i=0; i<words.size(); i++){
//            writer.write(words.get(i).toString().getBytes());
//            writer.write(" ".getBytes());
//        }
//        writer.write("\n".getBytes());
//    }
//
//    public void writeLists(List<WordList> lists) throws IOException {
//        writer = context.openFileOutput("lists.txt", Context.MODE_PRIVATE);
//        for(int i=0; i<lists.size(); i++){
//            writeList(lists.get(i));
//        }
//        writer = context.openFileOutput("lists.txt", Context.MODE_APPEND);
//    }
//
//    public void updateList(WordList list) throws IOException{
//        List<WordList> lists = readLists();
//        for(int i=0; i<lists.size(); i++){
//            if(list.name.equals(lists.get(i).name)){
//                lists.set(i, list);
//            }
//        }
//        writeLists(lists);
//    }
//
//    public void removeList(WordList list) throws IOException{
//        List<WordList> lists = readLists();
//        for(int i=0; i<lists.size(); i++){
//            if(list.name.equals(lists.get(i).name)){
//                lists.remove(i);
//            }
//        }
//    }
=======
    public Data(Context c) throws IOException {
        context = c;
        writer = context.openFileOutput("lists.txt", Context.MODE_APPEND);
        reader = context.openFileInput("lists.txt");
    }

    public List<String> readNames(List<WordList> lists){
        List <String> listNames = new ArrayList<>();
        for(int i=0; i<lists.size();++i){
            listNames.add(lists.get(i).name);
        }
        return listNames;
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
        writer = context.openFileOutput("lists.txt", Context.MODE_PRIVATE);
        for(int i=0; i<lists.size(); i++){
            writeList(lists.get(i));
        }
        writer = context.openFileOutput("lists.txt", Context.MODE_APPEND);
    }

    public void updateList(WordList list) throws IOException{
        List<WordList> lists = readLists();
        for(int i=0; i<lists.size(); i++){
            if(list.name.equals(lists.get(i).name)){
                lists.set(i, list);
            }
        }
        writeLists(lists);
    }

    public void removeList(WordList list) throws IOException{
        List<WordList> lists = readLists();
        for(int i=0; i<lists.size(); i++){
            if(list.name.equals(lists.get(i).name)){
                lists.remove(i);
            }
        }
    }
>>>>>>> 250bdce3f6dedc4879c07ee63374849cff590c98

}
