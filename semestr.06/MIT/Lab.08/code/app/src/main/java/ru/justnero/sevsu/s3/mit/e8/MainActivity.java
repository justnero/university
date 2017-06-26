package ru.justnero.sevsu.s3.mit.e8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends Activity implements View.OnClickListener {

    Switch switchTimeDate;
    TimePicker timePicker;
    DatePicker datePicker;
    Button btnSet;
    EditText etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeView();
        try {
            stopService(new Intent(this, MyService.class));
        } catch (Exception e) {

        }
        startService(new Intent(this, MyService.class));
    }

    private void InitializeView() {
        switchTimeDate = (Switch) findViewById(R.id.switchTimeDate);
        switchTimeDate.setOnClickListener(this);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        datePicker = (DatePicker) findViewById(R.id.datePicker);

        btnSet = (Button) findViewById(R.id.btnSet);
        btnSet.setOnClickListener(this);

        etMessage = (EditText) findViewById(R.id.etMessage);

        timePicker.setVisibility(View.VISIBLE);
        datePicker.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSet: {
                if (switchTimeDate.isChecked()) {
                    MyService.list.add(new Alarm(timePicker.getHour() * 100 + timePicker.getMinute(), null, etMessage.getText().toString()));
                } else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                    MyService.list.add(new Alarm(0, calendar, etMessage.getText().toString()));
                }
                break;
            }
            case R.id.switchTimeDate: {
                if (switchTimeDate.isChecked()) {
                    timePicker.setVisibility(View.VISIBLE);
                    datePicker.setVisibility(View.INVISIBLE);
                } else {
                    timePicker.setVisibility(View.INVISIBLE);
                    datePicker.setVisibility(View.VISIBLE);
                }
                break;
            }
        }
    }
}