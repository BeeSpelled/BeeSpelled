package com.example.beespelled.beespelled;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;


public class ListsActivity extends ListViewActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        getSupportActionBar().setTitle("Your Lists");

        try {
            showItems();
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

    @Override
    public void addItem(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.create_list_dialog_title);
        LayoutInflater inflater = this.getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_lists,null);
        builder.setView(view);
        final View finalView = view;
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText name = (EditText) finalView.findViewById(R.id.listName);
                String nameText = name.getText().toString();
                try {
                    if (!Data_Static.createList(getApplicationContext(), nameText)){
                        //Error
                        Log.e("ListsActivity", "Couldn't create list");
//                        Log.ERROR()
                    }

                    showItems();
                    startWordsActivity(nameText);
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

    @Override
    public void backButton(View view) {

    }

    @Override
    public void showItems() throws IOException {
        //final List<String> lists = Data_Static.getListNames(getApplicationContext());
        ListView listView = (ListView) findViewById(R.id.lists);
        final ListAdapter adapter = new ListAdapter(this);
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String selected = adapter.getItem(position);
                startWordsActivity(selected);
            }
        });
    }

    public void startWordsActivity(String listName){
        Intent intent = new Intent(getApplicationContext(), WordsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("list", listName);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
