package com.example.beespelled.beespelled;

import android.support.v7.app.ActionBarActivity;
import android.view.View;

import java.io.IOException;

/**
 * Created by Tmlewallen on 4/7/15.
 */
public abstract class ListViewActivity extends ActionBarActivity {

    public abstract void addItem(View view);

    public abstract void backButton(View view);

    public abstract void showItems() throws IOException;
}