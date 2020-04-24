package com.tancheng.carbonchain.bean;

public class MediaBean {
    private String path;
    private String thumbPath;
    private int duration;
    private long size;
    private String displayName;

    public MediaBean(String path, String thumbPath, int duration, long size, String displayName) {
        this.path = path;
        this.thumbPath = thumbPath;
        this.duration = duration;
        this.size = size;
        this.displayName = displayName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
