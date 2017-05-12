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

    private static final int SHAKE_SENSITIVITY = 3;
    private int shakeCounter = 0;

    private int xShake = 0;
    private int yShake = 0;
    private int zShake = 0;
    float x = 0;
    float y = 0;
    float z = 0;
    float xPrevious = 0;
    float yPrevious = 0;
    float zPrevious = 0;

    private float acceleration = SensorManager.GRAVITY_EARTH;

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
        xPrevious = x; yPrevious = y; zPrevious = z;
        x = event.values[0]; y = event.values[1]; z = event.values[2];
        float accelerationPrevious = acceleration;
        acceleration = (float) Math.sqrt((double) (x * x + y * y + z * z));
        if (acceleration - accelerationPrevious > SHAKE_SENSITIVITY) {
            final int moveDec = 3;
            if (Math.abs(xPrevious - x) > moveDec && Math.abs(yPrevious - y) < moveDec && Math.abs(zPrevious - z) < moveDec) {
                ++xShake;
            }
            if (Math.abs(xPrevious - x) < moveDec && Math.abs(yPrevious - y) > moveDec && Math.abs(zPrevious - z) < moveDec) {
                ++yShake;
            }
            if (Math.abs(xPrevious - x) < moveDec && Math.abs(yPrevious - y) < moveDec && Math.abs(zPrevious - z) > moveDec) {
                ++zShake;
            }
        }


        acelX.setText(String.format(getString(R.string.acceleration_x), x, xShake));
        acelY.setText(String.format(getString(R.string.acceleration_y), y, yShake));
        acelZ.setText(String.format(getString(R.string.acceleration_z), z, zShake));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
