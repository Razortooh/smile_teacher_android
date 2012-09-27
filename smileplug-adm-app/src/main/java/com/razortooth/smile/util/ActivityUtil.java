package com.razortooth.smile.util;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

public class ActivityUtil {

    public static final Activity getRoot(Activity activity) {
        Activity ctx = activity;
        Activity parent;

        while ((parent = ctx.getParent()) != null) {
            ctx = parent;
        }

        return ctx;
    }

    public static void showLongToast(Context context, int stringId) {
        showLongToast(context, context.getString(stringId));
    }

    public static void showLongToast(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public static Display getDisplaySize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }

}
