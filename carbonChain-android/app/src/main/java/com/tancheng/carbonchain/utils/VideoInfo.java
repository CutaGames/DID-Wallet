package com.tancheng.carbonchain.utils;

public class VideoInfo {
    /**
     * 视频名称
     */
    private String displayName;
    /**
     * 视频存储路径
     */
    private String path;
    /**
     * 视频时长
     */
    private Long videoDuration;
    /**
     * 视频大小
     */
    private Long videoSize;

    public VideoInfo() {
    }

    public VideoInfo(String displayName, String path, Long videoDuration, Long videoSize) {
        this.displayName = displayName;
        this.path = path;
        this.videoDuration = videoDuration;
        this.videoSize = videoSize;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(Long videoDuration) {
        this.videoDuration = videoDuration;
    }

    public Long getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(Long videoSize) {
        this.videoSize = videoSize;
    }


}
