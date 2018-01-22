package com.ty.android.deviceinfo.util;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.ty.android.deviceinfo.R;

public class SnackBarUtils {

    public static void showTopSnackBar(View view, String content, int height, int type) {
        TSnackbar snackbar = TSnackbar.make(view, content, TSnackbar.LENGTH_SHORT);
//        snackbar.setMaxWidth(100);


        View snackbarView = snackbar.getView();
        TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);

        if (type == 0) {
            // 所有权限授权成功
            snackbar.setIconLeft(R.drawable.ic_ok, 16);
            snackbarView.setBackgroundResource(R.drawable.snackbar_background_ok);
        } else if (type == 1) {
            // 复制成功
            snackbar.setIconLeft(R.drawable.ic_info, 16);
            snackbarView.setBackgroundResource(R.drawable.snackbar_background_info);
        } else if(type == 2) {
            // 禁止权限
            snackbar.setIconLeft(R.drawable.ic_error, 16);
            snackbarView.setBackgroundResource(R.drawable.snackbar_background_error);
        } else if(type == 3) {
            // 再次按返回键退出应用
            snackbar.setIconLeft(R.drawable.ic_warning, 16);
            snackbarView.setBackgroundResource(R.drawable.snackbar_background_warning);
        }

        snackbarView.setAlpha((float) 0.9);
        snackbarView.setFitsSystemWindows(true);
        snackbarView.setClickable(false);

//        snackbarView.setMinimumHeight(height + height / 4);


        LogUtil.log("Height>>>>>>>>>>>>>" + height);


        textView.setTextColor(Color.WHITE);
        textView.setTextSize(13);
        textView.setGravity(Gravity.CENTER);

        snackbar.show();
    }

    public static void showSnackBar(View view, String content){
        Snackbar.make(view, content, Snackbar.LENGTH_SHORT).show();
    }
}
