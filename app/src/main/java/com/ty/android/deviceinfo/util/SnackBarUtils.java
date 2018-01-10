package com.ty.android.deviceinfo.util;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.androidadvance.topsnackbar.TSnackbar;
import com.ty.android.deviceinfo.MyApplication;
import com.ty.android.deviceinfo.R;

/**
 * Created by To on 2017/12/5.
 */

public class SnackBarUtils {

    public static void showTopSnackBar(View view, String content) {
        TSnackbar snackbar = TSnackbar.make(view, content, TSnackbar.LENGTH_SHORT);
        snackbar.setMaxWidth(10000);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(MyApplication.getAppContext(), R.color.colorAppName));
        snackbarView.setAlpha((float) 0.9);
        snackbar.show();
    }

    public static void showSnackBar(View view, String content){
        Snackbar.make(view, content, Snackbar.LENGTH_SHORT).show();
    }
}
