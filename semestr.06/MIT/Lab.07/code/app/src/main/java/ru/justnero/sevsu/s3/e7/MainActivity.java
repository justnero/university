package ru.justnero.sevsu.s3.e7;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences mSettings;
    DownloadManager downloadManager;
    private long downloadReference;

    final String APP_PREFERENCES = "settingsPopupWindow";

    String POPUP_WINDOW_SHOW = "popupShow";
    boolean POPUP_WINDOW_IS_SHOW = true;

    View popupView;
    Button btnDownload;
    Button btnOpen;
    Button btnDelete;
    RelativeLayout relativeLayout;
    PopupWindow popupWindow;
    LayoutInflater lInflater;
    TextView textView;
    EditText etID;

    String path = "Android/data//com.example.angai.mit7/files/Documents";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeView();

        PopupWindowInitialize();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (POPUP_WINDOW_IS_SHOW) {
                    popupWindow.showAsDropDown(textView, 90, 90);
                }
            }
        });
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(POPUP_WINDOW_SHOW)) {
            POPUP_WINDOW_IS_SHOW = mSettings.getBoolean(POPUP_WINDOW_SHOW, true);
        }

        findViewById(R.id.rootRelativeLayout).post(new Runnable() {
            public void run() {
                if (POPUP_WINDOW_IS_SHOW) {
                    popupWindow.showAsDropDown(textView, 90, 90);
                }
            }
        });
    }

    private void InitializeView() {
        relativeLayout = (RelativeLayout) findViewById(R.id.rootRelativeLayout);
        textView = (TextView) findViewById(R.id.textView);
        btnDownload = (Button) findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(this);
        btnOpen = (Button) findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener(this);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        etID = (EditText) findViewById(R.id.nbFile);
    }

    private void PopupWindowInitialize() {
        lInflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = lInflater.inflate(R.layout.popup_layout, null, false);
        ((Button) popupView.findViewById(R.id.btnOk)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mSettings.edit();
                boolean isShow = !((CheckBox) popupView.findViewById(R.id.popupCheckbox)).isChecked();
                editor.putBoolean(POPUP_WINDOW_SHOW, isShow);
                POPUP_WINDOW_IS_SHOW = isShow;
                editor.apply();
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    private void InitializeDownload(String Url) {
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Uri Download_Uri = Uri.parse(Url);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);

        request.setTitle("My Data Download");
        request.setDescription("Android Data download using DownloadManager.");

        request.setDestinationInExternalFilesDir(this, getApplicationContext().getCacheDir().getPath(), etID.getText().toString() + ".pdf");


        try {
            downloadReference = downloadManager.enqueue(request);
        } catch (Exception e) {
            Log.d("TAG", e.getMessage());
            Toast.makeText(this, "File not Found!", Toast.LENGTH_SHORT).show();
        }

    }

    private void DownloadFile(String Url) {
        InitializeDownload(Url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDownload: {
                final String fileUrl = "http://ntv.ifmo.ru/file/journal/" + etID.getText().toString() + ".pdf";
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DownloadFile(fileUrl);
                    }
                });
                thread.start();
                break;
            }
            case R.id.btnOpen: {
                String fileName = etID.getText().toString() + ".pdf";

                try {
                    downloadManager.openDownloadedFile(downloadReference);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                File file = new File(getApplicationContext().getCacheDir().getPath(), fileName);

                Toast.makeText(this, "" + file.isFile(), Toast.LENGTH_SHORT).show();
                Uri uri = downloadManager.getUriForDownloadedFile(downloadReference);

                Intent intentOpenFile = new Intent(Intent.ACTION_VIEW);
                intentOpenFile.setDataAndType(uri, "application/pdf");
                intentOpenFile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentOpenFile);
                break;
            }

            case R.id.btnDelete: {
                boolean b = false;

                Uri uri = downloadManager.getUriForDownloadedFile(downloadReference);

                File file = new File(uri.getPath());
                boolean wasDeleted = file.delete();
                Toast.makeText(this, (wasDeleted) ? "File was deleted" : "ERROR", Toast.LENGTH_SHORT).show();

                break;
            }
        }
    }
}
