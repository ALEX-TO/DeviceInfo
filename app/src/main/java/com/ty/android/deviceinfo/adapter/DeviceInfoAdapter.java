package com.ty.android.deviceinfo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ty.android.deviceinfo.R;
import com.ty.android.deviceinfo.entity.DeviceInfo;

import java.util.List;

public class DeviceInfoAdapter extends ArrayAdapter<DeviceInfo>{

    int resourceId;
//    Context mContext;

    public DeviceInfoAdapter(Context context, int resource, List<DeviceInfo> objects) {
        super(context, resource, objects);
        resourceId = resource;
//        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DeviceInfo deviceInfo = getItem(position);

        View view = View.inflate(getContext(), resourceId, null);

        TextView tv_title   = view.findViewById(R.id.tv_title);
        TextView tv_content = view.findViewById(R.id.tv_content);

        tv_title.setText(deviceInfo.getTitle());
        tv_content.setText(deviceInfo.getContent());

        return view;
    }


}
