package com.tancheng.carbonchain.activities.upload;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.bean.MediaBean;
import com.tancheng.carbonchain.utils.VideoInfo;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取所有的视频类
 */
public class ActivityUploadVideos extends AppCompatActivity {

    //定义一个List来存放所以的mp3文件，你可以存放路径也可以存放文件名
    private List<String> mp3List = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_videoes);
        initView();
    }

    private void initView() {
//        getVideoFile();
        getAllVideoInfos();
    }


    private void getVideoFile(final List<VideoInfo> list, File file)
    {   // 获得视频文件
        file.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                // sdCard找到视频名称
                String name = file.getName();

                int i = name.indexOf('.');
                if (i != -1) {
                    name = name.substring(i);
                    if (name.equalsIgnoreCase(".mp4")
                            || name.equalsIgnoreCase(".3gp")
                            || name.equalsIgnoreCase(".wmv")
                            || name.equalsIgnoreCase(".ts")
                            || name.equalsIgnoreCase(".rmvb")
                            || name.equalsIgnoreCase(".mov")
                            || name.equalsIgnoreCase(".m4v")
                            || name.equalsIgnoreCase(".avi")
                            || name.equalsIgnoreCase(".m3u8")
                            || name.equalsIgnoreCase(".3gpp")
                            || name.equalsIgnoreCase(".3gpp2")
                            || name.equalsIgnoreCase(".mkv")
                            || name.equalsIgnoreCase(".flv")
                            || name.equalsIgnoreCase(".divx")
                            || name.equalsIgnoreCase(".f4v")
                            || name.equalsIgnoreCase(".rm")
                            || name.equalsIgnoreCase(".asf")
                            || name.equalsIgnoreCase(".ram")
                            || name.equalsIgnoreCase(".mpg")
                            || name.equalsIgnoreCase(".v8")
                            || name.equalsIgnoreCase(".swf")
                            || name.equalsIgnoreCase(".m2v")
                            || name.equalsIgnoreCase(".asx")
                            || name.equalsIgnoreCase(".ra")
                            || name.equalsIgnoreCase(".ndivx")
                            || name.equalsIgnoreCase(".xvid"))
                    {


                        VideoInfo vi = new VideoInfo();
                        vi.setDisplayName(file.getName());
                        vi.setPath(file.getAbsolutePath());
                        vi.setVideoDuration(file.getUsableSpace());
                        Log.e("ccc",file.getAbsolutePath()  + "AbsolutePath   >>>  ");
                        Log.e("ccc",file.getName()  + "Name   >>>   ");
                        Log.e("ccc",file.getFreeSpace()  + "FreeSpace   >>>   ");
                        Log.e("ccc",file.getTotalSpace()  + "TotalSpace   >>> ");
                        Log.e("ccc",file.getUsableSpace()  + "UsableSpace   >>>   ");

                        list.add(vi);
                        return true;
                    }
                } else if (file.isDirectory()) {
                    getVideoFile(list, file);
                }
                return false;
            }
        });
    }


    /**
     * 获取手机中所有视频的信息
     */
    private void getAllVideoInfos(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,List<MediaBean>> allPhotosTemp = new HashMap<>();//所有照片
                Uri mImageUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                String[] proj = { MediaStore.Video.Thumbnails._ID
                        , MediaStore.Video.Thumbnails.DATA
                        ,MediaStore.Video.Media.DURATION
                        ,MediaStore.Video.Media.SIZE
                        ,MediaStore.Video.Media.DISPLAY_NAME
                        ,MediaStore.Video.Media.DATE_MODIFIED};
                Cursor mCursor = getContentResolver().query(mImageUri,
                        proj,
                        MediaStore.Video.Media.MIME_TYPE + "=?",
                        new String[]{"video/mp4"},
                        MediaStore.Video.Media.DATE_MODIFIED+" desc");
                if(mCursor!=null){
                    while (mCursor.moveToNext()) {
                        // 获取视频的路径
                        int videoId = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Video.Media._ID));
                        String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DATA));
                        int duration = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                        long size = mCursor.getLong(mCursor.getColumnIndex(MediaStore.Video.Media.SIZE))/1024; //单位kb
                        if(size<0){
                            //某些设备获取size<0，直接计算
                            Log.e("dml","this video size < 0 " + path);
                            size = new File(path).length()/1024;
                        }
                        String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                        long modifyTime = mCursor.getLong(mCursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED));//暂未用到

                        //提前生成缩略图，再获取：http://stackoverflow.com/questions/27903264/how-to-get-the-video-thumbnail-path-and-not-the-bitmap
                        MediaStore.Video.Thumbnails.getThumbnail(getContentResolver(), videoId, MediaStore.Video.Thumbnails.MICRO_KIND, null);
                        String[] projection = { MediaStore.Video.Thumbnails._ID, MediaStore.Video.Thumbnails.DATA};
                        Cursor cursor = getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI
                                , projection
                                , MediaStore.Video.Thumbnails.VIDEO_ID + "=?"
                                , new String[]{videoId+""}
                                , null);
                        String thumbPath = "";
                        while (cursor.moveToNext()){
                            thumbPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
                        }
                        cursor.close();
                        // 获取该视频的父路径名
                        String dirPath = new File(path).getParentFile().getAbsolutePath();
                        //存储对应关系
                        if (allPhotosTemp.containsKey(dirPath)) {
                            List<MediaBean> data = allPhotosTemp.get(dirPath);
                            data.add(new MediaBean(path,thumbPath,duration,size,displayName));
                            continue;
                        } else {
                            List<MediaBean> data = new ArrayList<>();
                            data.add(new MediaBean(path,thumbPath,duration,size,displayName));
                            allPhotosTemp.put(dirPath,data);
                        }
                    }
                    mCursor.close();
                }
                //更新界面
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //...
                    }
                });
            }
        }).start();
    }


}
