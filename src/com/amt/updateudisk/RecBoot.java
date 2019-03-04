
package com.amt.updateudisk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RecBoot extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.i(TAG, "Intent.ACTION_BOOT_COMPLETED");
            // SystemProperties.set("ro.build.id","00100299010844101503");
            // String mac = SystemProperties.get("persist.sys.net.mac");
            // String writemac = mac.replace(":", "");
            // SystemProperties.set("ro.mac",writemac);
        }
    }

}
