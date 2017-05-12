package ru.justnero.sevsu.s3.mit.e5;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;

class AdapterOption extends BaseAdapter {


    private ArrayList<Option> arrayList = new ArrayList<>();
    private LayoutInflater lInflater;

    AdapterOption(Context context) {
        Log.d("Lab.05", "AdapterOption constructor");
        arrayList = DbHelper.getTableData(context);
        Log.d("Lab.05", "AdapterOption constructor" + arrayList.size());

        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.toArray(new Option[arrayList.size()])[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.listview_element, parent, false);
        }
        Log.d("Lab.05", "getView");
        Option option = arrayList.toArray(new Option[arrayList.size()])[position];

        ((TextView) view.findViewById(R.id.listViewKey)).setText("Key: " + option.key);
        ((TextView) view.findViewById(R.id.listViewValue)).setText("Value: " + option.value);

        return view;
    }
}
