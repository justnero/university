package ru.justnero.sevsu.s3.mit.lab04;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener, View.OnClickListener {

    GestureLibrary gLib;
    GestureOverlayView gestures;
    TextView tvResult;
    EditText etResult;
    Button btnResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = (TextView) findViewById(R.id.tvResult);
        etResult = (EditText) findViewById(R.id.etResult);
        btnResult = (Button) findViewById(R.id.btnResult);

        btnResult.setOnClickListener(this);

        gLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!gLib.load()) {
            finish();
        }

        gestures = (GestureOverlayView) findViewById(R.id.gestureOverlayView1);
        gestures.addOnGesturePerformedListener(this);
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        //Создаёт ArrayList c загруженными из gestures жестами
        ArrayList<Prediction> predictions = gLib.recognize(gesture);
        if (predictions.size() > 0) {
            Log.d("Lab.04", predictions.toString());
            //если загружен хотябы один жест из gestures
            Prediction prediction = predictions.get(0);
            if (prediction.score > 1.0) {
                switch (prediction.name) {
                    case "0": {
                        etResult.setText(etResult.getText().toString() + 0);
                        break;
                    }
                    case "1": {
                        etResult.setText(etResult.getText().toString() + 1);
                        break;
                    }
                    case "2": {
                        etResult.setText(etResult.getText().toString() + 2);
                        break;
                    }
                    case "3": {
                        etResult.setText(etResult.getText().toString() + 3);
                        break;
                    }
                    case "4": {
                        etResult.setText(etResult.getText().toString() + 4);
                        break;
                    }
                    case "5": {
                        etResult.setText(etResult.getText().toString() + 5);
                        break;
                    }
                    case "6": {
                        etResult.setText(etResult.getText().toString() + 6);
                        break;
                    }
                    case "7": {
                        etResult.setText(etResult.getText().toString() + 7);
                        break;
                    }
                    case "8": {
                        etResult.setText(etResult.getText().toString() + 8);
                        break;
                    }
                    case "9": {
                        etResult.setText(etResult.getText().toString() + 9);
                        break;
                    }
                    case "+": {
                        etResult.setText(etResult.getText().toString() + "+");
                        break;
                    }
                    case "-": {
                        etResult.setText(etResult.getText().toString() + "-");
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnResult: {
                String res = "";
                String s = etResult.getText().toString();
                try {
                    etResult.setText(etResult.getText().toString() + " = " + String.valueOf(eval(s)));
                } catch (Exception ex) {
                    etResult.setText(ex.getMessage());
                }

                break;
            }
        }
    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}
