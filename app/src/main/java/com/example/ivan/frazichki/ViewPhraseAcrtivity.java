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


        final List<Phrase> list = new ArrayList<Phrase>();

        try {
            InputStream inputStream = openFileInput("phrases.txt");
            ArrayList<String> bandWidth = new ArrayList<String>();

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(
                        inputStream);
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    //list.add(receiveString);
                    int indexOfDelimiter = receiveString.indexOf('@');
                    String phrase = receiveString.substring(0, indexOfDelimiter);
                    String translation = receiveString.substring(indexOfDelimiter+1, receiveString.length());
                    list.add(new Phrase(phrase,translation));
                }

                /*or(String str : bandWidth)
                    stringBuilder.append(str + "\n");*/

                //PhrasesAll = stringBuilder.toString();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.i("File not found", e.toString());
        } catch (IOException e) {
            Log.i("Can not read file:", e.toString());
        }


        /*for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }*/

        /*for(int i=0; i < list.size() ;i++){
            values[i] = list[i];
        }*/


        //final StableArrayAdapter adapter = new StableArrayAdapter(this,android.R.layout.list_item, list);
        final YourAdapter adapter = new YourAdapter(this, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,int position, long id) {
                Log.e("onItemClickek", Integer.toString(list.size()));
                /*view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        list.remove(item);
                        adapter.notifyDataSetChanged();
                        view.setAlpha(1);
                    }
                });*/
            }

        });
    }

    public void clickedButton1(View v) {
        Intent intent = new Intent(this, ViewPhraseAcrtivity.class);
        File file = new File(this.getFilesDir(), "phrases.txt");
        file.delete();
        this.onResume();
        //startActivity(intent);
    }


    // FIXME: taka ne se pravi
    private class YourAdapter extends BaseAdapter {

        Context context;
        List<Phrase> data;
        private LayoutInflater inflater = null;
        boolean b[];

        public YourAdapter(Context context, List<Phrase> data) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.data = data;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            b = new boolean[data.size()];
            Arrays.fill(b, true);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data.get(position);
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
                    intent.putExtra(ChangePhraseActivity.EXTRA_PHRASE,data.get(position).getPhrase());
                    intent.putExtra(ChangePhraseActivity.EXTRA_TRANSLATION,data.get(position).getTranslation());
                    context.startActivity(intent);
                }
            });

            if(b[position]) {
                text.setText(data.get(position).getPhrase());
            }else{
                text.setText(data.get(position).getTranslation());
            }
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    b[position] = !b[position];
                    if(b[position]) {
                        ((TextView) v).setText(data.get(position).getPhrase());
                    }else{
                        ((TextView) v).setText(data.get(position).getTranslation());
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
