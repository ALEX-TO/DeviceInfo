package com.ty.android.deviceinfo.entity;

public class DeviceInfo {
    private String title;

    private String content;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DeviceInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
