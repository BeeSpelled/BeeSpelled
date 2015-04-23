package com.example.beespelled.beespelled;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudyActivity extends SpellActivity {
    public int wordsIndex;
    public List<String> wrongs = new ArrayList<String>();
    public List<String> attempts = new ArrayList<String>();
    public int mastery;

    @Override
    public void config() {
        wordsIndex = 0;
        Bundle b = getIntent().getExtras();
        setListName(b.getString("currList"));
        mastery = b.getInt("mastery");
        setLayout(R.layout.activity_study);
        setMenu(R.menu.menu_study);
        try {
            words = Data_Static.filterWordList(getApplicationContext(), getListName(), mastery);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateHistory() {
        /*
        if(!checkAttempt()) {
            wrongs.add(getCurrWord());
            attempts.add(getAttempt());
        }
        */
        try {
            Data_Static.newWordAttempt(getApplicationContext(), getCurrWord(), checkAttempt());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setNextWord() {
        wordsIndex++;
        if(wordsIndex < words.size()) {
            setCurrWord(words.get(wordsIndex));
        }
        else { // end of list
            wordsIndex = 0;
            try {
                words = Data_Static.filterWordList(getApplicationContext(), getListName(), mastery);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (words.size() == 0) { // all words mastered
                Toast.makeText(StudyActivity.this, "increasing mastery", Toast.LENGTH_SHORT).show();
                mastery++;
                try {
                    words = Data_Static.filterWordList(getApplicationContext(), getListName(), mastery);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Collections.shuffle(words);
            setCurrWord(words.get(0));
        }
    }

    @Override
    public void showStats() {

    }

    public void statsButton(View view) {
        String stats = "";
        try {
            stats = Data_Static.getListStats(getApplicationContext(), getListName(), 10);
        } catch (IOException e) {
            stats = "Failed to fetch stats.";
            e.printStackTrace();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selected List's Statistics");
        LayoutInflater inflater = this.getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog_quizstats, null);
        builder.setView(convertView);
        final AlertDialog dialog = builder.create();
        TextView tv = (TextView) convertView.findViewById(R.id.message);
        tv.setText(stats);
        convertView.findViewById(R.id.OKbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }
}
