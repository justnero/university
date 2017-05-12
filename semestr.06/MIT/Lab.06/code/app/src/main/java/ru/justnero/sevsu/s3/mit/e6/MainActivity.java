package ru.justnero.sevsu.s3.mit.e6;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener {

    private MainActivity activity;
    private OkHttpClient client = new OkHttpClient();

    final String musicSource = "http://icecast.radiomaximum.cdnvideo.ru/maximum.mp3";
    final String dataSource = "http://maximum.ru/currenttrack.aspx?station=maximum";

    MediaPlayer mediaPlayer;
    AudioManager am;
    Button btnPlay, btnInfo;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        setContentView(R.layout.activity_main);

        am = (AudioManager) getSystemService(AUDIO_SERVICE);

        btnPlay = (Button) findViewById(R.id.btnStopResume);
        btnInfo = (Button) findViewById(R.id.btnInfo);
        btnPlay.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listView);

        refillList();
    }

    private void refillList() {
        DataBase db = new DataBase(this);
        SQLiteDatabase table = db.getWritableDatabase();

        Cursor cursor = table.query(DataBase.TABLE, null, null, null, null, null, null);
        ArrayList<String> arrayList = new ArrayList<>();
        String song;
        String artist;

        if (cursor.moveToFirst()) {
            do {
                song = cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_SONG));
                artist = cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_ARTIST));
                arrayList.add(artist.isEmpty() ? song : artist + " - " + song);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        Collections.reverse(arrayList);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList.toArray(new String[arrayList.size()]));
        listView.setAdapter(adapter);

    }

    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStopResume:
                try {
                    if (mediaPlayer == null) {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(musicSource);
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                        mediaPlayer.setOnPreparedListener(this);
                        mediaPlayer.prepareAsync();
                    } else {
                        releaseMP();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btnInfo:
                new RetrieveSongInfoTask().execute();
                break;
        }

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMP();
    }

    private class RetrieveSongInfoTask extends AsyncTask<Void, Void, Pair<String, String>> {

        private Exception exception;

        protected Pair<String, String> doInBackground(Void... urls) {
            String songArtist = "Unknown";
            String songTitle = "Unknown";

            try {
                Request request = new Request.Builder()
                        .url(activity.dataSource)
                        .build();
                Response response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONObject songInfo = new JSONObject(jsonData).getJSONObject("Current");
                songArtist = songInfo.getString("Artist");
                songTitle = songInfo.getString("Song");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                exception = e;
            }

            return new Pair<>(songArtist, songTitle);
        }

        protected void onPostExecute(Pair<String, String> data) {
            DataBase dataBase = new DataBase(activity);
            SQLiteDatabase table = dataBase.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(DataBase.COLUMN_ARTIST, data.first);
            cv.put(DataBase.COLUMN_SONG, data.second);

            table.insert(DataBase.TABLE, null, cv);

            activity.refillList();
        }
    }

}