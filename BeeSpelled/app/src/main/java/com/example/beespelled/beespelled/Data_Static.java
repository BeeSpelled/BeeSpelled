package com.example.beespelled.beespelled;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import android.util.Log;

public class Data_Static {

    private static final String LISTS_PATH = "lists";
    private static final String WORDS_PATH = "words";
    private static final String PRONUNCIATIONS_PATH = "pronunciations";
    private static final String CLASS = Data_Static.class.getSimpleName();

    public static void initializeData(Context c) throws IOException{
        File fileSystemChecker = getPath(c, LISTS_PATH);
        Log.d(CLASS, fileSystemChecker.getAbsolutePath());
        if (!fileSystemChecker.exists()) fileSystemChecker.mkdir();

        fileSystemChecker = getPath(c, WORDS_PATH);
        if (!fileSystemChecker.exists()) fileSystemChecker.mkdir();

        fileSystemChecker = getPath(c, PRONUNCIATIONS_PATH);
        if (!fileSystemChecker.exists()) fileSystemChecker.mkdir();

        File path = getPath(c, "");
        String[] files = path.list();
        Log.d(CLASS, path.getAbsolutePath());
        for (String i : files) Log.d(CLASS, i);

    }

    public static List<String> getListNames(Context c) {
        try {
            String[] things = getPath(c, LISTS_PATH).list();
            List<String> path = Arrays.asList(things);
            Log.d(CLASS, "READING THE FOLLOWING LIST NAMES: ");
            for (String item : things) Log.d(CLASS, "\t" + item);
            return Arrays.asList(things);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;

    }

    public static List<String> getWordsByList(Context c, String listName) throws IOException{
        WordList list = readWordList(c, listName);
        return list.getWords(); //Will Return Strings
    }

    public static boolean createList(Context c, String listName) throws IOException{
        if (!listExists(c, listName)){
            writeWordList(c, new WordList(listName));
            Log.d(CLASS, "WordList Created And Written: " + listName);
            return true;
        }
        else return false;
    }

    public static boolean createWord(Context c, String word) throws IOException{
        if (!wordExists(c, word)){
            writeWord(c, new Word(word));
            return true;
        }
        else return false;
    }

    public static void addWordToList(Context c, String list, String word) throws IOException{
        WordList listObj = readWordList(c, list);
        listObj.addWord(word);
        writeWordList(c, listObj);
    }

    public static void addWordsToList(Context c, String list, String[] words) throws IOException {
        WordList listObj = readWordList(c, list);
        for (String word : words){
            listObj.addWord(word);
        }
        writeWordList(c, listObj);
    }


    public static Word getWordData(Context c, String word) throws IOException{
        return readWord(c, word);
    }

    public static void deleteList(Context c, String list) throws IOException{
        getPath(c, LISTS_PATH, list).delete();
    }

    public static void changeListName(Context c, String originalName, String newName) throws IOException{
        File path = getPath(c, LISTS_PATH, originalName);
        File newPath = getPath(c, LISTS_PATH, newName);
        WordList list = readWordList(c, originalName);
        list.setName(newName);
        try { //Wanna catch exception with writing list before we delete old list
            writeWordList(c, list);
            if (!originalName.equals(newName)){path.delete();} //Prevents case of new name == old name from deleting file altogether
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    private static boolean listExists(Context c, String list) throws IOException{
        return getPath(c, LISTS_PATH, list).exists();
    }

    private static boolean wordExists(Context c, String word) throws IOException{
        return getPath(c, WORDS_PATH, word).exists();
    }

    private static void writeWord(Context c, Word word) throws IOException{
        File dir = getPath(c, WORDS_PATH, word.toString());
        ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(dir));
        oo.writeObject(word);
        oo.close();
    }

    private static Word readWord(Context c, String name) throws IOException{
        Word word= null;
        File dir = getPath(c,WORDS_PATH,name);
        ObjectInputStream oi = new ObjectInputStream(new FileInputStream(dir));
        try{
            word = (Word)oi.readObject();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        finally{
            oi.close();
        }
        return word;
    }

    private static void writeWordList(Context c, WordList list) throws IOException{
        File dir = getPath(c, LISTS_PATH, list.getName());
        ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(dir));
        oo.writeObject(list);
        oo.close();
    }

    private static WordList readWordList(Context c, String name) throws IOException{
        WordList list = null;
        File dir = getPath(c,LISTS_PATH,name);
        ObjectInputStream oi = new ObjectInputStream(new FileInputStream(dir));
        try{
            list = (WordList)oi.readObject();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        finally{
            oi.close();
        }
        return list;
    }

    private static File getPath(Context c, String dir, String name) throws IOException{
        return new File(c.getFilesDir().getCanonicalPath() + File.separator + dir + File.separator + name);
    }

    private static File getPath(Context c, String dir) throws IOException{
        return new File(c.getFilesDir().getCanonicalPath() + File.separator + dir);
    }
}
