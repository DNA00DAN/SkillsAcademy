package intgo.skillsacademy;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by danie on 2017/02/28.
 */

public class CustomResultAdapter extends BaseAdapter {
    Context context;
    List<String> results;
    LayoutInflater inflter;
    Typeface myTypeFace;

    public CustomResultAdapter(Context applicationContext, List<String> tests, Typeface myTypeFace) {
        this.context = applicationContext;
        this.results = tests;
        this.myTypeFace = myTypeFace;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(results.get(i));
        names.setTypeface(myTypeFace);

        return view;
    }
}
