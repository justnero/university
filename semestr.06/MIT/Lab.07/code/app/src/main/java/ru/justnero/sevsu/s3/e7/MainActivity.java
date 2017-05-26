package ru.justnero.sevsu.s3.e7;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainActivity activity;
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

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        InitializeView();

        PopupWindowInitialize();
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(POPUP_WINDOW_SHOW)) {
            POPUP_WINDOW_IS_SHOW = mSettings.getBoolean(POPUP_WINDOW_SHOW, true);
        }

        findViewById(R.id.rootRelativeLayout).post(new Runnable() {
            public void run() {
                if (POPUP_WINDOW_IS_SHOW) {
                    popupWindow.showAsDropDown(btnDownload, 90, 90);
                }
            }
        });
    }

    private void InitializeView() {
        relativeLayout = (RelativeLayout) findViewById(R.id.rootRelativeLayout);
        btnDownload = (Button) findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(this);
        btnOpen = (Button) findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener(this);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
    }

    private void PopupWindowInitialize() {
        lInflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = lInflater.inflate(R.layout.popup_layout, null, false);
        popupView.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
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

    public boolean haveStoragePermission() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Log.e("Permission error", "You have permission");
            return true;
        } else {

            Log.e("Permission error", "You have asked for permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            DownloadFile();
        }
    }


    private void InitializeDownload(String url) {
        Uri Download_Uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
        request.setDescription("Download file");
        request.setTitle("Lab.07");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "photo.jpg");

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadReference = manager.enqueue(request);
    }

    private void DownloadFile() {
        String url = "https://scontent-frt3-1.cdninstagram.com/t51.2885-15/e35/15099595_1245503628875534_5402097839276818432_n.jpg";
        InitializeDownload(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDownload: {
                if (haveStoragePermission()) {
                    DownloadFile();
                }
                break;
            }
            case R.id.btnOpen: {
                Uri uri = downloadManager.getUriForDownloadedFile(downloadReference);
                if (uri == null) {
                    Toast.makeText(this, "File was not downloaded!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intentOpenFile = new Intent(Intent.ACTION_VIEW);
                intentOpenFile.setData(uri);
                intentOpenFile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentOpenFile);
                break;
            }
            case R.id.btnDelete: {
                Uri uri = downloadManager.getUriForDownloadedFile(downloadReference);
                if (uri == null) {
                    Toast.makeText(this, "File was not downloaded!", Toast.LENGTH_SHORT).show();
                    return;
                }
                downloadManager.remove(downloadReference);
                Toast.makeText(this, "File was deleted", Toast.LENGTH_SHORT).show();

                break;
            }
        }
    }
}
