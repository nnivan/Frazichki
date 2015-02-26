package com.ivan.frazichki;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PhraseModel pm = PhraseModel.createInstance(this,"phrase.txt");

        //final Context context = this;


        //TODO TUK E BUGAVOTO

        /*TextView text = (TextView) findViewById(R.id.firstLine);

        String message = new String("My dog also likes eating sausage");

        ArrayList<StringBuilder> t;
            t = PhraseExtraction.makePhrase(this, message);

        StringBuilder builder = new StringBuilder();
        for(StringBuilder s : t){
            builder.append(s + "\n");
        }
        text.setText(builder.toString());*/
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