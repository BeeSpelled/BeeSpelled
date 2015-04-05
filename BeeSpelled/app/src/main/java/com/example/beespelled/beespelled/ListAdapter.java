package com.example.beespelled.beespelled;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class ListAdapter extends BaseAdapter{
    Data d;
    List<String> list;
    private Context context;

    public ListAdapter(Context c, List <WordList>lists) throws IOException {
        context=c;
        d = new Data(context);
        list = d.readNames(lists);
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = inflater.inflate(R.layout.listview_item, arg2,false);
        }

        TextView listName = (TextView)arg1.findViewById(R.id.listViewText);

        String name = list.get(arg0);

        listName.setText(name);

        return arg1;
    }

}
