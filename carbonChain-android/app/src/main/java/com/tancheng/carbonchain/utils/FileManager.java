package com.tancheng.carbonchain.utils;


import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    //定义一个List来存放所以的mp3文件，你可以存放路径也可以存放文件名
    List<String> mp3List = new ArrayList<String>();


    private void getVideoFile(final List<VideoInfo> list, File file) {// 获得视频文件

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
     *
     * @param groupPath  如果你想获取SDcard下面的所以mp3文件你就填sdcard路径
     * 用的是递归的方式获取
     */
    public void getSDcardFile(File groupPath){
        //循环获取sdcard目录下面的目录和文件
        for(int i=0; i< groupPath.listFiles().length; i++){
            File childFile = groupPath.listFiles()[i];

            //假如是目录的话就继续调用getSDcardFile（）将childFile作为参数传递的方法里面
            if(childFile.isDirectory()){
                getSDcardFile(childFile);
            }else{
                //如果是文件的话，判断是不是以.mp3结尾，是就加入到List里面
                if(childFile.toString().endsWith(".mp3")){
                    mp3List.add(childFile.getName());

                    //打印文件的文件名
                    System.out.println(childFile.getName());
                    //打印文件的路径
                    System.out.println(childFile.getAbsolutePath());
                }
            }
        }
    }



}
