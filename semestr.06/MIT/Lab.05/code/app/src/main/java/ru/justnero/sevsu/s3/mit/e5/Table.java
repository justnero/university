package ru.justnero.sevsu.s3.mit.e5;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class Table extends Activity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        listView = (ListView)findViewById(R.id.listView);
        AdapterOption adapter = new AdapterOption(this);
        listView.setAdapter(adapter);
    }
}
