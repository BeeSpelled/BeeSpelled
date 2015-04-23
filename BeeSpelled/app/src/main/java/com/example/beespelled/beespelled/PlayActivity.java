package com.example.beespelled.beespelled;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.List;


public class PlayActivity extends ActionBarActivity {
    String selected = null;
    int mastery = 5;
    SeekBar masteryFlex = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        try {
            selectList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
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

    public void homeButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void quizButton(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        Bundle b = new Bundle();
        b.putString("currList", selected);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void studyButton(View view) {
        Intent intent = new Intent(this, StudyActivity.class);
        Bundle b = new Bundle();
        b.putString("currList", selected);
        b.putInt("mastery", mastery);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void optionButton(View view) {
        //Toast.makeText(view.getContext(), "options unimplemented", 3).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options");
        LayoutInflater inflater = this.getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog_play_option, null);

        masteryFlex = (SeekBar) convertView.findViewById(R.id.masteryFlexSeekBar);

        masteryFlex.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 5;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress + 1;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // Don't Need. Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                mastery = progressChanged;
                Toast.makeText(PlayActivity.this,"Mastery changed to:"+progressChanged, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(convertView);
        final AlertDialog dialog = builder.create();

        dialog.show();
    }

    public void selectListButton(View view) {
        //Toast.makeText(view.getContext(), "options unimplemented", 3).show();
        try {
            selectList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void viewStatsButton(View view) {
        WordList currList;
        String stats = "";
        try {
            stats = Data_Static.getListStats(getApplicationContext(), selected, 10);
        } catch (IOException e) {
            stats = "Failed to fetch stats.";
            e.printStackTrace();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Current List Statistics");
        LayoutInflater inflater = this.getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog_quizstats, null);
        builder.setView(convertView);
        final AlertDialog dialog = builder.create();
        dialog.setMessage(stats);
        dialog.show();
    }

    public  void clearHistoryButton(View view) {
        try {
            Data_Static.clearWordHistory(getApplicationContext(), selected);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectList() throws IOException {
        final List<String> lists = Data_Static.getNonEmptyListNames(getApplicationContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.select_list_dialog_title);
        LayoutInflater inflater = this.getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog_select, null);
        builder.setView(convertView);
        ListView lv = (ListView) convertView.findViewById(R.id.select);
        final AlertDialog dialog = builder.create();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lists);
        lv.setAdapter(adapter);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                selected = adapter.getItem(position);
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
