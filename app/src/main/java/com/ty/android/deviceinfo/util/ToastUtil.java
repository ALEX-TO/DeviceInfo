package com.ty.android.deviceinfo.util;

import android.widget.Toast;

//import com.androidadvance.topsnackbar.TSnackbar;
import com.ty.android.deviceinfo.MyApplication;

/**
 * Created by To on 2017/11/22.
 */

public class ToastUtil {

    public static void show(String content){
        Toast.makeText(MyApplication.getAppContext(), content, Toast.LENGTH_SHORT).show();
    }


}
