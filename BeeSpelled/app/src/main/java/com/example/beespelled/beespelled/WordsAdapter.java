package com.example.beespelled.beespelled;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class WordsAdapter extends BaseAdapter{
    private Context context;
    List<Word> list;

    public WordsAdapter(Context c, List<Word> wordList) {
        list = wordList;
        context=c;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int arg0) {
        return list.get(arg0).toString();
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        if(arg1==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = inflater.inflate(R.layout.listview_item, arg2,false);
        }

        TextView listName = (TextView)arg1.findViewById(R.id.listViewText);
        String name = list.get(arg0).toString();
        listName.setText(name);

        View ellipsis = arg1.findViewById(R.id.listViewImage);
        ellipsis.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.options)
                        .setItems(R.array.ellipsis_array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        return arg1;
    }
}
