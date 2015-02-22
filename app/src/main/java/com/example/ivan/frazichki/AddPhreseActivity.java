package com.example.ivan.frazichki;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;


public class AddPhreseActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "PhrasesLocation";
    public static final String PHRASES_COUNT = "PhrasesCount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phrese);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_phrese, menu);
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

        EditText editText = (EditText) findViewById(R.id.editext1);
        String message = editText.getText().toString();

        File file = new File(this.getFilesDir(), "phrases.txt");

        writeToFile(new Phrase(message,message + "1"));

        /*SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        
        int length = settings.getInt(PHRASES_COUNT, 0);

        editor.putString("Phrase " + length, message);

        editor.putInt(PHRASES_COUNT, length+1);

        editor.apply();

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();*/

    }
    private void writeToFile(Phrase data) {
        String newline = "\r\n";
        try {

            OutputStreamWriter oswName = new OutputStreamWriter(openFileOutput(
                    "phrases.txt", Context.MODE_APPEND));
            oswName.write(data.getPhrase() + "@" + data.getTranslation());
            oswName.write(newline);
            oswName.close();
        } catch (IOException e) {
            Log.e("writeToFile", "File write failed: " + e.toString());
        }
    }
}
