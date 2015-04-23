package com.example.beespelled.beespelled;

import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

public abstract class SpellActivity extends ActionBarActivity implements OnInitListener {
    protected static final int RESULT_SPEECH = 1;

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
        Collections.shuffle(words);
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
        //TODO Thomas
//        Toast.makeText(view.getContext(), "not implemented", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

        try{
            startActivityForResult(i, RESULT_SPEECH);
        }
        catch (ActivityNotFoundException e){
            Toast t = Toast.makeText(getApplicationContext(), "Oops! Your device doesn't support Speech to Text!", Toast.LENGTH_SHORT);
            t.show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("SpellActivity", "Here");
        switch (requestCode){
            case RESULT_SPEECH:
                if (resultCode == RESULT_OK && data != null){
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    EditText attemptEdit = null;
                    attemptEdit = (EditText)this.findViewById(R.id.keyboardInput);
                    setAttempt(text.get(0));
                    attemptEdit.setText(text.get(0),null);
                    processSpell();
                    for (String item : text) Log.d("SpellActivity", item);
                }

        }
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

    public void quitButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Abstract Methods
    public abstract void updateHistory();
    public abstract void setNextWord();
    public abstract void config();
    public abstract void showStats();
}