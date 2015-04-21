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
import java.util.concurrent.CopyOnWriteArrayList;

import android.util.Log;

public class Data_Static {

    private static final String LISTS_PATH = "lists";
    private static final String WORDS_PATH = "words";
    private static final String PRONUNCIATIONS_PATH = "pronunciations";
    private static final String CLASS = Data_Static.class.getSimpleName();

    public static void initializeData(Context c) throws IOException{
        File fileSystemChecker = getPath(c, LISTS_PATH);
//        Log.d(CLASS, fileSystemChecker.getAbsolutePath());
        if (!fileSystemChecker.exists()) fileSystemChecker.mkdir();

        fileSystemChecker = getPath(c, WORDS_PATH);
        if (!fileSystemChecker.exists()) fileSystemChecker.mkdir();

        fileSystemChecker = getPath(c, PRONUNCIATIONS_PATH);
        if (!fileSystemChecker.exists()) fileSystemChecker.mkdir();

        File path = getPath(c, "");
        String[] files = path.list();
//        Log.d(CLASS, path.getAbsolutePath());
        for (String i : files) Log.d(CLASS, i);
    }

    public static void deleteAllData(Context c) throws IOException{
        File[] files = getPath(c, LISTS_PATH).listFiles();
        for (File f : files) {
            Log.d(CLASS + "deleteAllData", "Deleting" + LISTS_PATH + "/" + f.getName());
            f.delete();
        }

        files = getPath(c, WORDS_PATH).listFiles();
        for (File f: files) {
            Log.d(CLASS + "deleteAllData", "Deleting" + WORDS_PATH + "/" + f.getName());
            f.delete();
        }

        files = getPath(c, PRONUNCIATIONS_PATH).listFiles();
        for (File f : files) {
            Log.d(CLASS + "deleteAllData", "Deleting" + PRONUNCIATIONS_PATH + "/" + f.getName());
            f.delete();
        }

        Log.d(CLASS + " deleteAllData", "Lists " + String.valueOf(getPath(c, LISTS_PATH).delete()));
        Log.d(CLASS + " deleteAllData", "Words " + String.valueOf(getPath(c, WORDS_PATH).delete()));
        Log.d(CLASS + " deleteAllData", "Pronunciations " + String.valueOf(getPath(c, PRONUNCIATIONS_PATH).delete()));
    }


    public static List<String> getListNames(Context c) {
        try {
            String[] things = getPath(c, LISTS_PATH).list();
            List<String> path = Arrays.asList(things);
            Log.d(CLASS + " getListNames", "READING THE FOLLOWING LIST NAMES: ");
            for (String item : things) Log.d(CLASS + " getListNames", "\t" + item);
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

    public static void createWord(Context c, String word) throws IOException{
        if (!wordExists(c, word)){writeWord(c, new Word(word));}
        else {
            Word w = readWord(c, word);
            w.incRefs();
            writeWord(c, w);
        }
    }

    public static void createWords(Context c, String[] words) throws IOException{
        for (String word : words) createWord(c, word);
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

    public static void deleteWordFromList(Context c, String list, String word) throws IOException{
        WordList wList = readWordList(c, list); //read in list
        wList.getWords().remove(word); //remove the word
        writeWordList(c, wList); //write the list back to file
        Word w = readWord(c, word); //read in word
        if (w.decRefs() <= 0){ //If this is last reference to that word...
            getPath(c, WORDS_PATH, word).delete(); //Delete the word file
        }
        else writeWord(c, w); //else, write it back to file
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

    public static List<String> filterWordList(Context c, String list, int mastery){
        //TODO THOMAS
        return null;
    }

    public static void newWordAttempt(Context c, String word, boolean correct){
        //TODO THOMAS

    }

    public static void changeWord(){
        //TODO Thomas

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
