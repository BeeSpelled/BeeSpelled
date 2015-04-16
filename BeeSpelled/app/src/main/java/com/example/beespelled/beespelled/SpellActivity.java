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
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

public abstract class SpellActivity extends ActionBarActivity implements OnInitListener {
    private String attempt;
    public String getAttempt() { return attempt;}
    public void setAttempt(String attempt) { this.attempt = attempt; }
    public TextToSpeech sayWord;

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
        sayWord = new TextToSpeech(this, this);
        config(); // subclass specific configuration
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

    @Override
    public void onInit(int i) { } // for text to speech

    public boolean checkAttempt() { return getAttempt().equals(getCurrWord()); } // is the attempt correct?

    public void spellKeyboard(View view) { // spell word with keyboard
        EditText attemptEdit = null;
        attemptEdit = (EditText)this.findViewById(R.id.keyboardInput);
        setAttempt(attemptEdit.getText().toString());
        attemptEdit.setText("",null);
        processSpell();
    }

    public void spellVoice (View view) { // spell word with voice
        //TODO
        Toast.makeText(view.getContext(), "not implemented", Toast.LENGTH_SHORT).show();
    }

    public void processSpell(){ // after inputting with voice or keyboard
        updateHistory();
        setNextWord();
    }

    public void hearWord(View view) { // text to speech
        if (!getCurrWord().equals(null)) {
            sayWord.speak(getCurrWord(),TextToSpeech.QUEUE_FLUSH, null);
            //Toast.makeText(view.getContext(), getCurrWord(), 3).show(); // uncomment if testing without volume
        }
    }

    // Abstract Methods
    public abstract void updateHistory();
    public abstract void setNextWord();
    public abstract void config();
    public abstract void showStats();
}