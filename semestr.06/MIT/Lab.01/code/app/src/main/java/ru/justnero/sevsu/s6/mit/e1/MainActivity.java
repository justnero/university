package ru.justnero.sevsu.s6.mit.e1;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView text = (TextView) findViewById(R.id.textView);
        final Button button = (Button) findViewById(R.id.textBtn);
        final int count[] = {1};
        button.setOnClickListener(v -> text.setText(String.format("Сделал что-то %d раз", count[0]++)));
    }
}
