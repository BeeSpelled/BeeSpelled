package com.example.beespelled.beespelled;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends SpellActivity {
    public int wordsIndex;
    public List<String> wrongs = new ArrayList<String>();
    public List<String> attempts = new ArrayList<String>();

    @Override
    public void config() {
        wordsIndex = 0;
        Bundle b = getIntent().getExtras();
        setListName(b.getString("currList"));
        setLayout(R.layout.activity_quiz);
        setMenu(R.menu.menu_quiz);
        try {
            words = Data_Static.getWordsByList(getApplicationContext(), getListName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateHistory() {
        try {
            Data_Static.newWordAttempt(getApplicationContext(), getCurrWord(), checkAttempt());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!checkAttempt()) {
            wrongs.add(getCurrWord());
            attempts.add(getAttempt());
        }
    }

    @Override
    public void setNextWord() {
        wordsIndex++;
        if(wordsIndex < words.size()) {
            setCurrWord(words.get(wordsIndex));
        }
        else { // end of list, show stats
            showStats();
        }
    }

    @Override
    public void showStats() {
        sayWord.shutdown(); // end text to speech engine
        int numWrong = wrongs.size();
        int numRight = words.size() - numWrong;
        String statText = "You got " + Integer.toString(numRight) + "/" + Integer.toString(words.size()) + " correct.\n";
        if (wrongs.size() > 0) {
            if (wrongs.size() == 1) { statText += "The word you got wrong is: \n"; }
            else { statText += "These are the words you got wrong: \n"; }
            int i = 0;
            String currWrong;
            String currAttempt;
            while (i < wrongs.size()) {
                currWrong = wrongs.get(i);
                currAttempt = attempts.get(i);
                statText += "   " + String.format("%1s", currWrong) + "\n";
                statText += "   - You input: " + currAttempt;
                if (i < wrongs.size()) {
                    statText += "\n\n";
                    i++;
                }
            }
        }
        else { statText += "Congratulations!"; }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quiz Summary");
        LayoutInflater inflater = this.getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog_quizstats, null);
        builder.setView(convertView);
        final AlertDialog dialog = builder.create();
        //dialog.setMessage(statText);
        TextView tv = (TextView) convertView.findViewById(R.id.message);
        tv.setText(statText);
        convertView.findViewById(R.id.OKbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        dialog.show();
    }

    public void goBack() {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }
}
