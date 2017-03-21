package ru.justnero.sevsu.s3.mit.e3;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView acelX;
    TextView acelY;
    TextView acelZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acelX = (TextView) findViewById(R.id.acelX);
        acelY = (TextView) findViewById(R.id.acelY);
        acelZ = (TextView) findViewById(R.id.acelZ);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            acelX.setText(String.format(getString(R.string.acceleration_x), event.values[0]));
            acelY.setText(String.format(getString(R.string.acceleration_y), event.values[1]));
            acelZ.setText(String.format(getString(R.string.acceleration_z), event.values[2]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
