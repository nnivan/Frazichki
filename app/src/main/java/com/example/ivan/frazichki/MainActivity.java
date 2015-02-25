package com.example.ivan.frazichki;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PhraseModel pm = PhraseModel.createInstance(this,"phrase.txt");

        //final Context context = this;


        //TODO TUK E BUGAVOTO

        TextView text = (TextView) findViewById(R.id.firstLine);

        String[] message = new String[]{"My dog also likes eating sausage"};

        String t = null;
        try {
            t = PhraseExtraction.compi(message, this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        text.setText(t);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void clickedButton1(View v) {
        Intent intent = new Intent(this, AddPhreseActivity.class);

        startActivity(intent);

        //Toast.makeText(this, "You can add phrases!", Toast.LENGTH_LONG).show();

    }

    public void clickedButton2(View v) {
        Intent intent = new Intent(this, ViewPhraseAcrtivity.class);

        startActivity(intent);

        //Toast.makeText(this, "You can see your phrases!", Toast.LENGTH_LONG).show();

    }
}
