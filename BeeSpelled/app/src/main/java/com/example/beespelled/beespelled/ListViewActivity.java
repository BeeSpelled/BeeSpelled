package com.example.beespelled.beespelled;

import android.support.v7.app.ActionBarActivity;
import android.view.View;

import java.io.IOException;


public abstract class ListViewActivity extends ActionBarActivity {

    public abstract void addItem(View view);

    public abstract void homeButton(View view);

    public abstract void showItems() throws IOException;

}
