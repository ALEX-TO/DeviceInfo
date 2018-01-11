package com.ty.android.deviceinfo.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.ty.android.deviceinfo.R;
import com.ty.android.deviceinfo.util.DeviceUtil;

public class AboutActivity extends BaseActionBarActivity {

    private TextView tv_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary), false);

        tv_version = findViewById(R.id.tv_version);

        tv_version.setText(DeviceUtil.getVersion(getApplicationContext()));

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }
}
