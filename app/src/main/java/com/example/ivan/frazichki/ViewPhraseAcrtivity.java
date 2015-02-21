package com.example.ivan.frazichki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ViewPhraseAcrtivity extends ActionBarActivity {

    public static final String PREFS_NAME = "PhrasesLocation";
    public static final String PHRASES_COUNT = "PhrasesCount";
    String[] Phrases = new String[1024];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_view_phrase_acrtivity);

        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
        int length = settings.getInt(PHRASES_COUNT, 0);

        String PhrasesAll = "";

        for(int i=0;i<length;i++){
            Phrases[i] = settings.getString("Phrase " + i, "noPhrase");
            PhrasesAll = PhrasesAll + Phrases[i] + "\n" ;
        }


        TextView myText = new TextView(this);
        RelativeLayout myLayout = new RelativeLayout(this);
        myLayout.addView(myText);
        myText.setText(PhrasesAll);
        setContentView(myLayout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_phrase_acrtivity, menu);
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


}
