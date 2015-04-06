package com.example.beespelled.beespelled;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;


import java.util.List;


public class WordsActivity extends ActionBarActivity {

    WordList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        list = (WordList)bundle.getSerializable("list");
        showWords(list);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_words, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addButton(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(list.name);
        LayoutInflater inflater = this.getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_words,null);
        builder.setView(view);
        final View finalView = view;
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText words = (EditText) finalView.findViewById(R.id.wordText);
                String[] wordsText = words.getText().toString().split(" ");
                list.addWords(wordsText);
                try {
                    Data d = new Data(getApplicationContext());
                    d.updateList(list);
                    //d.writeList(list);
                    showWords(list);
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void backButton(View view){

    }

    public void showWords(WordList list) {
        final List<Word> wordList = list.words;
        ListView listView = (ListView) findViewById(R.id.words);
        final WordsAdapter adapter = new WordsAdapter(this, wordList);
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView parent, View view, int position, long id){

            }
        });
    }

}
