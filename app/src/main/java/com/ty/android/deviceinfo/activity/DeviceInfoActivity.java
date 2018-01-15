package com.ty.android.deviceinfo.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.ty.android.deviceinfo.entity.DeviceInfo;
import com.ty.android.deviceinfo.adapter.DeviceInfoAdapter;
import com.ty.android.deviceinfo.PermissionListener;
import com.ty.android.deviceinfo.R;
import com.ty.android.deviceinfo.util.DeviceUtil;
import com.ty.android.deviceinfo.util.SnackBarUtils;

import java.util.ArrayList;
import java.util.List;

public class DeviceInfoActivity extends BaseActivity {

    private String[] imei_imsi = new String[2];

    private String phone_number = "";

    private String networkOperatorName = "";

    private ListView listView;

    private List<DeviceInfo> deviceInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            setContentView(R.layout.activity_device_info2);
            StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary), false);
            listView = findViewById(R.id.list_view_in_coordinatorLayout);
        } else {
            setContentView(R.layout.activity_device_info);
            listView = findViewById(R.id.list_view_in_linearLayout);
        }

        permission();

    }

    /**
     * 获取需要权限的信息
     */
    public void getNeedPermissionInfo() {

        imei_imsi = DeviceUtil.getImeiAndImsi(getApplicationContext());

        phone_number = DeviceUtil.getPhoneNumber(getApplicationContext());

        networkOperatorName = DeviceUtil.getNetworkOperatorName(getApplicationContext());

        setUpListView(true);

    }

    /**
     * 检查权限
     */
    private void permission(){
        requestRunPermisssion(new String[]{Manifest.permission.READ_PHONE_STATE}, new PermissionListener() {
            @Override
            public void onGranted() {

                //所有权限都授权了
                SnackBarUtils.showTopSnackBar(listView, "所有权限都已授权！", DeviceUtil.getTSnackbarHeight(getApplicationContext()), 0);
//                ToastUtil.show("所有权限都已授权！");
//                SnackBarUtils.showTopSnackBar(ll_main, "所有权限都已授权！");

                getNeedPermissionInfo();
//                imei_imsi = DeviceUtil.getImeiAndImsi(getApplicationContext());
//
//                phone_number = DeviceUtil.getPhoneNumber(getApplicationContext());
//
//                networkOperatorName = DeviceUtil.getSimOperatorName(getApplicationContext());
//
//                setUpListView(true);
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for(final String permission : deniedPermission){

                    if(!ActivityCompat.shouldShowRequestPermissionRationale(DeviceInfoActivity.this, permission)){
                        //当拒绝了授权后，为提升用户体验，可以以弹窗的方式引导用户到设置中去进行设置
                        new AlertDialog.Builder(DeviceInfoActivity.this)
                                .setMessage("需要开启权限才能获取相关信息！\n点击“前往开启”，然后点击“权限”，开启相关权限后重新打开应用即可。")
                                .setPositiveButton("前往开启", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //引导用户到设置中去进行设置
                                        DeviceInfoActivity.this.finish();
                                        Intent intent = new Intent();
                                        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                        intent.setData(Uri.fromParts("package", getPackageName(), null));
                                        startActivity(intent);

                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SnackBarUtils.showTopSnackBar(getWindow().getDecorView(), "被拒绝的权限：" + DeviceUtil.getPermissionContent(permission), DeviceUtil.getTSnackbarHeight(getApplicationContext()), 2);
                                    }
                                })
                                .show();
                    }else {
//                    Toast.makeText(DeviceInfoActivity.this, "被拒绝的权限：" + DeviceUtil.getPermissionContent(permission), Toast.LENGTH_SHORT).show();
                        SnackBarUtils.showTopSnackBar(listView, "被拒绝的权限：" + DeviceUtil.getPermissionContent(permission), DeviceUtil.getTSnackbarHeight(getApplicationContext()), 2);
                    }
                }

                setUpListView(false);
            }
        });
    }

    /**
     * 加载ListView
     */
    public void setUpListView(boolean hasGetPermissions) {
//        listView = findViewById(R.id.list_view);
        initDatas(hasGetPermissions);
        DeviceInfoAdapter adapter = new DeviceInfoAdapter(this, R.layout.device_info_item_list, deviceInfo);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                LinearLayout ll_device_info = (LinearLayout) listView.getChildAt(position);
                // getChildAt这个函数是用来获得ListView的Item,它能取到的Item的最大值为当前可见的Item数目
                // 解决方法就是调用adapter的getView方法，这个方法可以获取ListView的所有Item,mListView.getAdapter().getView(i, null, null);
                LinearLayout ll_device_info = (LinearLayout) listView.getAdapter().getView(position, null, null);
                TextView tv_title = ll_device_info.findViewById(R.id.tv_title);
                TextView tv_content = ll_device_info.findViewById(R.id.tv_content);
                String copy_content = tv_title.getText() + ":" + tv_content.getText();
//                DeviceUtil.copyToClipboard(getWindow().getDecorView(), copy_content, DeviceUtil.getTSnackbarHeight(getApplicationContext()));
                DeviceUtil.copyToClipboard(listView, copy_content, DeviceUtil.getTSnackbarHeight(getApplicationContext()));
            }
        });
    }

    /**
     * 初始化ListView数据
     */
    private void initDatas(boolean hasGetPermissions) {
        boolean needScreenShot = false;
        deviceInfo.add(new DeviceInfo("系统版本", DeviceUtil.getOsVersion()));
        deviceInfo.add(new DeviceInfo("设备厂商", DeviceUtil.getPhoneBrand()));
        deviceInfo.add(new DeviceInfo("设备型号", DeviceUtil.getPhoneModel()));
        deviceInfo.add(new DeviceInfo("ROM版本", DeviceUtil.getRomVersion()));
        deviceInfo.add(new DeviceInfo("屏幕信息", DeviceUtil.getScreenInfo(this)));
        if (hasGetPermissions) {
            deviceInfo.add(new DeviceInfo("IMEI", imei_imsi[0]));
            deviceInfo.add(new DeviceInfo("IMSI", imei_imsi[1]));
            if (needScreenShot) {
                deviceInfo.add(new DeviceInfo("手机号码", "+8618512345678"));
            }else {
                deviceInfo.add(new DeviceInfo("手机号码", phone_number));
            }
            deviceInfo.add(new DeviceInfo("运营商名称", networkOperatorName));
        }else {
            deviceInfo.add(new DeviceInfo("IMEI", "未获取权限，无法读取！"));
            deviceInfo.add(new DeviceInfo("IMSI", "未获取权限，无法读取！"));
            deviceInfo.add(new DeviceInfo("手机号码", "未获取权限，无法读取！"));
            deviceInfo.add(new DeviceInfo("运营商名称", "未获取权限，无法读取！"));
        }
        deviceInfo.add(new DeviceInfo("WiFi名称", DeviceUtil.getWiFiInfo(getApplicationContext())[0]));
        deviceInfo.add(new DeviceInfo("IP地址", DeviceUtil.getWiFiInfo(getApplicationContext())[1]));
        deviceInfo.add(new DeviceInfo("总内存", DeviceUtil.getTotalMemory()));
        deviceInfo.add(new DeviceInfo("剩余内存", DeviceUtil.getFreeMemory(getApplicationContext())));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_about) {
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

        }

        return super.onOptionsItemSelected(item);
    }

}
