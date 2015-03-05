package com.ivan.frazichki;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;


public class AddPhreseActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "PhrasesLocation";
    public static final String PHRASES_COUNT = "PhrasesCount";

    final Activity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phrese);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_phrese, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void clickedButton1(View v) {
        Intent intent = new Intent(this, MainActivity.class);

        EditText editText = (EditText) findViewById(R.id.editext1);
        String message = editText.getText().toString();

        AddPhraseFromSentenceTask addPhraseFromSentenceTask = new AddPhraseFromSentenceTask();
        addPhraseFromSentenceTask.execute(message);

        context.finish();
    }
}
