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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ListsActivity extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        try {
            showLists();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lists, menu);
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
        builder.setTitle(R.string.create_list_dialog_title);
        LayoutInflater inflater = this.getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_lists,null);
        builder.setView(view);
        final View finalView = view;
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText name = (EditText) finalView.findViewById(R.id.listName);
                EditText words = (EditText) finalView.findViewById(R.id.listText);
                String nameText = name.getText().toString();
                String[] wordsText = words.getText().toString().split(" ");
                WordList list = new WordList(nameText, new ArrayList<Word>());
                list.addWords(wordsText);
                try {
                    Data d = new Data(getApplicationContext());
                    d.writeList(list);
                    showLists();
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

    public void showLists() throws IOException {
        Data d = new Data(getApplicationContext());
        final List<WordList> lists = d.readLists();
        ListView listView = (ListView) findViewById(R.id.lists);
        final ListAdapter adapter = new ListAdapter(getApplicationContext(), lists);
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView parent, View view, int position, long id){
                String selected = adapter.getItem(position);
                WordList list = new WordList(null,null);
                for(int i=0; i<lists.size();++i){
                    if(selected.equals(lists.get(i).name)) list = lists.get(i);
                }
                Intent intent = new Intent(view.getContext(), WordsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", list);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}
