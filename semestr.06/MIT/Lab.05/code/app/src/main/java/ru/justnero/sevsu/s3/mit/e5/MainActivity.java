package ru.justnero.sevsu.s3.mit.e5;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnUpdate, btnAdd, btnShow;
    NumberPicker nbValue;
    EditText etKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAdd = (Button) findViewById(R.id.btnAddNew);
        btnShow = (Button) findViewById(R.id.btnShow);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnShow.setOnClickListener(this);

        nbValue = (NumberPicker) findViewById(R.id.npAge);
        etKey = (EditText) findViewById(R.id.etName);

        Db db = new Db(getApplicationContext());
        SQLiteDatabase table = db.getWritableDatabase();

        nbValue.setMaxValue(100);
        nbValue.setMinValue(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShow: {
                Intent intent = new Intent(this, Table.class);
                startActivity(intent);
                break;
            }

            case R.id.btnAddNew: {
                DbHelper.Add(this, etKey.getText().toString(), nbValue.getValue());
                break;
            }

            case R.id.btnUpdate: {
                DbHelper.UpdateLast(this, "views", 20);
                break;
            }
        }
    }
}
