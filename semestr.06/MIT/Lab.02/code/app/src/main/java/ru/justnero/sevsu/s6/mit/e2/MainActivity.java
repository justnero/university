package ru.justnero.sevsu.s6.mit.e2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText number1;
    private EditText number2;
    private EditText result1;
    private EditText result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number1 = (EditText) findViewById(R.id.number1);
        number2 = (EditText) findViewById(R.id.number2);
        result1 = (EditText) findViewById(R.id.result1);
        result2 = (EditText) findViewById(R.id.result2);

        result1.setEnabled(false);
        result2.setEnabled(false);

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recalculate();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        number1.addTextChangedListener(tw);
        number2.addTextChangedListener(tw);
    }

    private void recalculate() {
        String sa = number1.getText().toString();
        String sb = number2.getText().toString();

        result1.getText().clear();
        result2.getText().clear();

        try {
            int a = Integer.parseInt(sa);
            int b = Integer.parseInt(sb);
            int result = a % b;

            result1.getText().append(String.valueOf(result));


            result2.getText().append(Integer.toBinaryString(result));
        } catch (NumberFormatException | ArithmeticException ex) {
            ex.printStackTrace();
        }
    }
}
