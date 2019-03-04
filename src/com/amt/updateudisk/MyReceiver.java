
package com.amt.updateudisk;

import java.io.File;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";
    private String path = "/mnt/sda/sda1/";
    private String updatePath = "";
    private String updateName = "update.zip";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("path",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.i(TAG, "intent.getAction() = " + intent.getAction());

        if (intent.getAction().equals(Intent.ACTION_MEDIA_MOUNTED)) {
            Log.i(TAG, "MyReceiver.Intent.ACTION_MEDIA_MOUNTED");
            if (intent.getData().getPath().length() > 0) {
                path = intent.getData().getPath() + "/";
            }
            Log.i(TAG, "MyReceiver.path = " + path);
            updatePath = path + updateName;
            if (new File(updatePath).exists()) {
                Intent intent2 = new Intent(context, ShowDialogService.class);
                editor.putString("path", updatePath);
                editor.commit();
                context.startService(intent2);
            }
        }

        if (intent.getAction().equals(Intent.ACTION_MEDIA_UNMOUNTED)) {
            Log.i(TAG, "MyReceiver.Intent.ACTION_MEDIA_UNMOUNTED");
            Intent intent2 = new Intent(context, ShowDialogService.class);
            context.stopService(intent2);
        }
    }
}
