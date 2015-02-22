package com.example.ivan.frazichki;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class ChangePhraseActivity extends ActionBarActivity {

    public final static String EXTRA_PHRASE = "ChangePhraseActivity.EXTRA_PHRASE";
    public final static String EXTRA_TRANSLATION = "ChangePhraseActivity.EXTRA_TRANSLATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phrase);

        Intent intent = getIntent();
        String phrase = intent.getStringExtra(EXTRA_PHRASE);
        String translation = intent.getStringExtra(EXTRA_TRANSLATION);

        EditText editText1 = (EditText) findViewById(R.id.editext1);
        EditText editText2 = (EditText) findViewById(R.id.editext2);

        editText1.setText(phrase);
        editText2.setText(translation);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_phrase, menu);
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
