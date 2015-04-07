package com.example.beespelled.beespelled;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;

import java.io.IOException;
import java.util.List;

public class ListAdapter extends BaseAdapter{
    List<String> list;
    private ListsActivity a;

<<<<<<< HEAD
    public ListAdapter(ListsActivity c) throws IOException {
        a = c;
        list = Data_Static.getListNames(a.getApplicationContext());
=======
    public ListAdapter(Context c, List <WordList>lists) throws IOException { //switch lists type to List<String>
        context=c;
        d = new Data(context); //won't need
        list = d.readNames(lists); //change to list = lists
>>>>>>> 250bdce3f6dedc4879c07ee63374849cff590c98
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        if(arg1==null)
        {
            LayoutInflater inflater = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = inflater.inflate(R.layout.listview_item, arg2,false);
        }

        TextView listName = (TextView)arg1.findViewById(R.id.listViewText);
        final String name = list.get(arg0);
        listName.setText(name);

        View ellipsis = arg1.findViewById(R.id.listViewImage);
        ellipsis.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
<<<<<<< HEAD
                AlertDialog.Builder builder = new AlertDialog.Builder(a);
                final CharSequence[] cs = a.getResources().getTextArray(R.array.ellipsis_array);
                builder.setTitle(R.string.options)
                        .setItems(R.array.ellipsis_array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch(which){
                                    case 1:
                                        try {
                                            Data_Static.deleteList(a.getApplicationContext(), name);
                                            a.showItems();
                                        }
                                        catch (IOException e){
                                            e.printStackTrace();
                                        }
                                        break;
                                    default:
                                        break;
                                }

                                Log.d("NULL", cs[0].toString() );
=======
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.options)
                        .setItems(R.array.ellipsis_array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
>>>>>>> 250bdce3f6dedc4879c07ee63374849cff590c98
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });
        return arg1;
    }

}