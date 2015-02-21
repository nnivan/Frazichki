package com.example.ivan.frazichki;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ViewPhraseAcrtivity extends ActionBarActivity {


    public static final String PREFS_NAME = "PhrasesLocation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_phrase_acrtivity);

        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        final ListView listview = (ListView) findViewById(R.id.listview);
        int length = settings.getInt("PhrasesCount", 0);
        String[] values = new String[length] ;

        Toast.makeText(this, length + "!", Toast.LENGTH_LONG).show();


        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < length; ++i) {
            String phrase = settings.getString("Phrase" + i, "");
            values[i] = phrase;
            list.add(phrase);
        }


        //final StableArrayAdapter adapter = new StableArrayAdapter(this,android.R.layout.list_item, list);
        final YourAdapter adapter = new YourAdapter(this, values);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        list.remove(item);
                        adapter.notifyDataSetChanged();
                        view.setAlpha(1);
                    }
                });
            }

        });
    }

    public void clickedButton1(View v) {
        Intent intent = new Intent(this, ViewPhraseAcrtivity.class);
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("PhrasesCount", (int) 0);
        editor.apply();
        startActivity(intent);

        //Toast.makeText(this, "You can add phrases!", Toast.LENGTH_LONG).show();

    }

    // FIXME: taka ne se pravi
    private class YourAdapter extends BaseAdapter {

        Context context;
        String[] data;
        private LayoutInflater inflater = null;

        public YourAdapter(Context context, String[] data) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.data = data;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi = convertView;
            if (vi == null)
                vi = inflater.inflate(R.layout.list_item, null);
            TextView text = (TextView) vi.findViewById(R.id.firstLine);
            text.setText(data[position]);
            return vi;
        }
    }

    /*private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }*/



}
