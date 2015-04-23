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
import java.util.ArrayList;
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

    public static String getListStats(Context c, String list, int recent) throws IOException{
        WordList listObj = readWordList(c, list);
        String stats = "Statistics for " + list + ":\n";
        StringBuilder sb = new StringBuilder(stats);
        for(String word : listObj.getWords()) {
            sb.append(getWordStats(c, word, recent));
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String getWordStats(Context c, String word, int recent){
        String stats = word + ": There are no statistics available for this word";
        try {
            Word w = Data_Static.getWordData(c, word);
            int successes = 0;
            List<Boolean> history = w.getHistory();
            if(recent > history.size()) recent = history.size();
//            history = history.subList(history.size() - recent, history.size()-1);
            while (recent>0) {
                if (recent == history.size()) break;
                else history.remove(0);
            }
            for (int i = 0; i < recent; i++) {
                if (history.get(i) == true) successes++;
            }
            int p = (int)w.getPercentage() * 100;
            if(recent<0) recent = 0;
            stats = "Statistics for " + word + ": " + p + "% success rate with " + successes + " out of the last " + recent + " correct";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stats;
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
            return null;
        }

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

    public static List<String> filterWordList(Context c, String list, int mastery) throws IOException{
        List<Word> words = new ArrayList<Word>();
        WordList listObj = readWordList(c, list);
        for (String word : listObj.getWords()) words.add(readWord(c, word));


        return null;
    }

    public static void newWordAttempt(Context c, String word, boolean correct) throws IOException{
        Word w = readWord(c, word);
        w.addAttempt(correct);
        writeWord(c, w);
    }

    public static void clearWordHistory(Context c, String word) throws IOException{
        Word w = readWord(c, word);
        w.clearHistory();
        writeWord(c, w);
    }

    public static boolean changeWord(Context c, String list, String oldWord, String newWord){
        try { //Wanna catch exception with writing list before we delete old list
            File path = getPath(c, LISTS_PATH, oldWord);
//            File newPath = getPath(c, LISTS_PATH, newWord);
            Word word = readWord(c, oldWord);
            word.setText(newWord);
            WordList listObj = readWordList(c, list);
            listObj.getWords().set(listObj.getWords().indexOf(oldWord), newWord);
            writeWord(c, word);
            writeWordList(c, listObj);
            if (!oldWord.equals(newWord)){path.delete();} //Prevents case of new name == old name from deleting file altogether
            return true;
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
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
