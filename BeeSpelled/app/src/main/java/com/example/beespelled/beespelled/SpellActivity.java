package com.example.beespelled.beespelled;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public abstract class SpellActivity extends ActionBarActivity {
    private String attempt;
    public String getAttempt() { return attempt;}
    public void setAttempt(String attempt) { this.attempt = attempt; }

    private String listName;
    public String getListName() { return listName; }
    public void setListName(String listName) { this.listName = listName; }

    public List<String> words;

    private String currWord;
    public String getCurrWord() { return currWord; }
    public void setCurrWord(String currWord) { this.currWord = currWord; }

    private int layout;
    public int getLayout() { return layout; }
    public void setLayout(int layout) { this.layout = layout; }

    private int menu;
    public int getMenu() { return menu; }
    public void setMenu(int menu) { this.menu = menu; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        config();
        try {
            words = Data_Static.getWordsByList(getApplicationContext(), getListName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setCurrWord(words.get(0));
        setContentView(getLayout());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(getMenu(), menu);
//        Data_Static.getListNames(this);
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

    public boolean checkAttempt() { return getAttempt().equals(getCurrWord()); }

    public void spellKeyboard(View view) {
        EditText attemptEdit = null;
        attemptEdit = (EditText)this.findViewById(R.id.keyboardInput);
        setAttempt(attemptEdit.getText().toString());
        updateHistory();
        int temp = setNextWord();
        if (temp == 0) {
            showStats();
        }
    }

    public void spellVoice (View view) {
        //TODO
        Toast.makeText(view.getContext(), "not implemented", 3).show();
    }

    public void hearWord(View view) {
        if (!getCurrWord().equals(null)) {
            Toast.makeText(view.getContext(), getCurrWord(), 3).show();
        }
    }

    // Abstract Methods
    public abstract void updateHistory();
    public abstract int setNextWord();
    public abstract void config();
    public abstract void showStats();



}