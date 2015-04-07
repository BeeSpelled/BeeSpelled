package com.example.beespelled.beespelled;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.util.List;
import android.util.Log;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            Data_Static.initializeData(getApplicationContext());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void playButton(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }

    public void optionsButton(View view) {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    public void wordsButton(View view) {
        Intent intent = new Intent(this, ListsActivity.class);
        startActivity(intent);
    }
}
