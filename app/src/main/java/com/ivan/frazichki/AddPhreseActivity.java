package com.ivan.frazichki;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class AddPhreseActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "PhrasesLocation";
    public static final String PHRASES_COUNT = "PhrasesCount";

    private ProgressDialog pd;

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


        AddPhraseFromSentenceTask addPhraseFromSentenceTask = new AddPhraseFromSentenceTask(this);
        addPhraseFromSentenceTask.execute(message);

        pd = ProgressDialog.show(AddPhreseActivity.this, "","Моля изчакайте!", true);

    }

    public void endContext(final String s){
        if(s==null || s.equals("")) {
            pd.dismiss();
            context.finish();
        }else{
            context.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
                }
            });
            //pd.dismiss();
            context.finish();
        }
    }
}
