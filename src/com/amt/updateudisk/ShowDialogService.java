
package com.amt.updateudisk;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class ShowDialogService extends Service {

    private static final String TAG = "MyReceiver";
    private static Context context;
    private Button yes_btn;
    private Button no_btn;

    private String path = "";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "ShowDialogService.onCreate()");
        context = this;

        SharedPreferences sharedPreferences = context.getSharedPreferences("path",
                Activity.MODE_PRIVATE);
        path = sharedPreferences.getString("path", "/mnt/sda/sda1/update.zip");
        Log.i(TAG, "path.share = " + path);
        showDialog();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 0;
    }

    private Dialog updateDialog;

    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
        View view = inflater.inflate(R.layout.update, null);

        builder.setCancelable(false);
        updateDialog = builder.create();
        updateDialog.setCancelable(false);
        updateDialog.setCanceledOnTouchOutside(true);
        updateDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        // powerDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
        updateDialog.show();
        updateDialog.setContentView(view);
        Window dialogWindow = updateDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        // params.alpha = 0.9f;
        params.width = 660;
        params.height = 300;
        dialogWindow.setAttributes(params);

        yes_btn = (Button) view.findViewById(R.id.yes_dialog);
        no_btn = (Button) view.findViewById(R.id.no_dialog);

        no_btn.requestFocus();

        yes_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        no_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog.dismiss();
                stopSelf();
            }
        });

        OnKeyListener listener = new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    updateDialog.dismiss();
                    stopSelf();
                }
                return false;
            }
        };
        yes_btn.setOnKeyListener(listener);
        no_btn.setOnKeyListener(listener);
        no_btn.requestFocus();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void update() {
        Intent re = new Intent("android.intent.action.MASTER_CLEAR");
        re.putExtra("mount_point", path);
        if (new File(path).exists()) {
            context.sendBroadcast(re);
        }
        updateDialog.dismiss();
        stopSelf();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy ");
        updateDialog.dismiss();
        super.onDestroy();
    }
}
