package com.example.beespelled.beespelled;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends SpellActivity {
    public int wordsIndex = 0;
    public List<String> wrongs = new ArrayList<String>();


    @Override
    public void config() {
        Bundle b = getIntent().getExtras();
        setListName(b.getString("currList"));
        setLayout(R.layout.activity_quiz);
        setMenu(R.menu.menu_quiz);
    }

    @Override
    public void updateHistory() {
        if(!checkAttempt()) {
            wrongs.add(getCurrWord());
        }
    }

    @Override
    public int setNextWord() {
        if(wordsIndex < words.size() - 1) {  //!words.get(wordsIndex + 1).equals(null)) {
            //updateHistory();
            //wrongs.add(getCurrWord()); // TESTING
            wordsIndex++;
            setCurrWord(words.get(wordsIndex));
            return 1;
        }
        else { // end of list, show stats
            return 0;
        }
    }

    public void showStats() {
        int numWrong = wrongs.size();
        int numRight = words.size() - numWrong;
        String statText = "You got " + Integer.toString(numRight) + "/" + Integer.toString(words.size()) + " correct.\n";
        statText += "Theses are the words you got wrong: \n";
        int i = 0;
        String currWrong = wrongs.get(i);
        while( i < wrongs.size()) {//!currWrong.equals(null)) {
            statText += "   " + currWrong;
            i++;
            if (i < wrongs.size()) {
                statText += "\n";
                currWrong = wrongs.get(i);
            }

        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quiz Statistics");
        LayoutInflater inflater = this.getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog_quizstats, null);
        builder.setView(convertView);
        final AlertDialog dialog = builder.create();
        //EditText msg;
        //msg = (EditText)findViewById(R.id.statMessage);
        //msg.setText(statText, );
        dialog.setMessage(statText);
        dialog.show();
    }

    public void placeHolderButton(View view) {
        Toast.makeText(view.getContext(), "not implemented", 3).show();
    }

}
