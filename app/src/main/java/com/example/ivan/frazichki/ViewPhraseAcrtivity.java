package com.example.ivan.frazichki;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class ViewPhraseAcrtivity extends ActionBarActivity {


    public static final String PREFS_NAME = "PhrasesLocation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_phrase_acrtivity);

    }

    @Override
    protected void onResume() {
        super.onResume();

        final ListView listview = (ListView) findViewById(R.id.listview);

        PhraseModel pm = PhraseModel.getInstance();
        //final List<Phrase> list = pm.getList();

        final YourAdapter adapter = new YourAdapter(this, pm);
        listview.setAdapter(adapter);

    }

    public void clickedButton1(View v) {
        Intent intent = new Intent(this, ViewPhraseAcrtivity.class);
        PhraseModel pm = PhraseModel.getInstance();
        pm.removeAll();
        this.onResume();
        //startActivity(intent);
    }


    // FIXME: taka ne se pravi
    private class YourAdapter extends BaseAdapter {

        Context context;
        PhraseModel data;
        private LayoutInflater inflater = null;
        boolean b[];

        public YourAdapter(Context context, PhraseModel data) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.data = data;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            b = new boolean[data.getList().size()];
            Arrays.fill(b, true);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.getList().size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data.getList().get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi = convertView;
            if (vi == null) {
                vi = inflater.inflate(R.layout.list_item, null);
            }
            TextView text = (TextView) vi.findViewById(R.id.firstLine);
            ImageView icon = (ImageView) vi.findViewById(R.id.icon);

            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChangePhraseActivity.class);
                    intent.putExtra(ChangePhraseActivity.EXTRA_PHRASE,data.getList().get(position).getPhrase());
                    intent.putExtra(ChangePhraseActivity.EXTRA_TRANSLATION,data.getList().get(position).getTranslation());
                    context.startActivity(intent);
                }
            });

            if(b[position]) {
                text.setText(data.getList().get(position).getPhrase());
            }else{
                text.setText(data.getList().get(position).getTranslation());
            }
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    b[position] = !b[position];
                    if(b[position]) {
                        ((TextView) v).setText(data.getList().get(position).getPhrase());
                    }else{
                        ((TextView) v).setText(data.getList().get(position).getTranslation());
                    }
                }
            });
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
