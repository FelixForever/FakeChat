package com.wixappsite.fakechat.fakechat;

import android.app.Application;
import android.telephony.TelephonyManager;

public class App extends Application {
    public static String IMEI;

    //public static boolean ok=false;
    @Override
    public void onCreate() {
        super.onCreate();
        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        IMEI=tm.getDeviceId();
       // Log.e("imei", "ddd");
//        SystemClock.sleep(TimeUnit.SECONDS.toMillis(2));
    }
}
