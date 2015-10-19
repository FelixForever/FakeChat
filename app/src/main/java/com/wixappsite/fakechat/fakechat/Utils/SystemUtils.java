package com.wixappsite.fakechat.fakechat.Utils;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.util.Locale;

/**
 * Created by Wix on 2015/10/7.
 */
public class SystemUtils {
    private static InputMethodManager manager;
private static String version;
    public static boolean isEn(Activity activity) {
        Locale locale = activity.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("en"))
            return true;
        else
            return false;
    }

    public static void hideKeyboard(Activity activity) {
        if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (activity.getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static String getVersionCode(Activity activity) throws PackageManager.NameNotFoundException {
        PackageManager pm = activity.getPackageManager();//context为当前Activity上下文
        PackageInfo pi = pm.getPackageInfo(activity.getPackageName(), 0);
        version=pi.versionName;
return  version;
    }


}

