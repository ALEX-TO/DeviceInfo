package com.ty.android.deviceinfo.util;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.ty.android.deviceinfo.MyApplication;
import com.ty.android.deviceinfo.R;

/**
 * Created by To on 2017/12/5.
 */

public class SnackBarUtils {

    public static void showTopSnackBar(View view, String content, int height, int type) {
        TSnackbar snackbar = TSnackbar.make(view, content, TSnackbar.LENGTH_SHORT);
        snackbar.setMaxWidth(10000);

        View snackbarView = snackbar.getView();
        if (type == 0) {
            // 所有权限授权成功
            snackbarView.setBackgroundColor(ContextCompat.getColor(MyApplication.getAppContext(), R.color.colorGreed));
        } else if (type == 1) {
            // 复制成功
            snackbarView.setBackgroundColor(ContextCompat.getColor(MyApplication.getAppContext(), R.color.colorYellow));
        } else if(type == 2) {
            // 禁止权限
            snackbarView.setBackgroundColor(ContextCompat.getColor(MyApplication.getAppContext(), R.color.colorRed));
        }

        snackbarView.setAlpha((float) 0.9);
        snackbarView.setMinimumHeight(height);

        LogUtil.log("Height>>>>>>>>>>>>>" + height);

        TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(13);
        textView.setGravity(Gravity.CENTER);
        snackbar.show();
    }

    public static void showSnackBar(View view, String content){
        Snackbar.make(view, content, Snackbar.LENGTH_SHORT).show();
    }
}
