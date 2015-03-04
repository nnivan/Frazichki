package com.ivan.frazichki;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;


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

        final YourAdapter adapter = new YourAdapter(this, pm);
        listview.setAdapter(adapter);

    }

    public void clickedButton1(View v) {
        Intent intent = new Intent(this, ViewPhraseAcrtivity.class);
        PhraseModel pm = PhraseModel.getInstance();
        pm.removeAll();
        this.onResume();
    }


    private class YourAdapter extends BaseAdapter {

        Context context;
        PhraseModel data;
        private LayoutInflater inflater = null;
        boolean b[];

        public YourAdapter(Context context, PhraseModel data) {
            this.context = context;
            this.data = data;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            b = new boolean[data.getList().size()];
            Arrays.fill(b, true);
        }

        @Override
        public int getCount() {
            return data.getList().size();
        }

        @Override
        public Object getItem(int position) {
            return data.getList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
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

}
