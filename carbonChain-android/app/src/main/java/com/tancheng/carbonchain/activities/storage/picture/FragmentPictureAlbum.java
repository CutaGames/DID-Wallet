package com.tancheng.carbonchain.activities.storage.picture;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;

/**
 * Created by gll on 2020/2/13.
 */

public class FragmentPictureAlbum extends Fragment {
    private static final Boolean DEBUG = true;
    private static final String TAG = "FragmentPictureAlbum";

    private RelativeLayout mPictureAlbumFolder1;
    private RelativeLayout mPictureAlbumFolder2;
    private RelativeLayout mPictureAlbumFolder3;
    private RelativeLayout mPictureAlbumFolder4;
    private RelativeLayout mPictureAlbumFolder5;
    private RelativeLayout mPictureAlbumFolder6;
    private RelativeLayout mPictureAlbumFolder7;
    private RelativeLayout mPictureAlbumFolder8;
    private RelativeLayout mPictureAlbumFolder9;

    public FragmentPictureAlbum() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture_album, container,false);
        findViews(view);
        setOnClickListeners();
        return view;
    }

    private void findViews(View view){
        mPictureAlbumFolder1 = (RelativeLayout)view.findViewById(R.id.picture_album_folder_1);
        mPictureAlbumFolder2 = (RelativeLayout)view.findViewById(R.id.picture_album_folder_2);
        mPictureAlbumFolder3 = (RelativeLayout)view.findViewById(R.id.picture_album_folder_3);
        mPictureAlbumFolder4 = (RelativeLayout)view.findViewById(R.id.picture_album_folder_4);
        mPictureAlbumFolder5 = (RelativeLayout)view.findViewById(R.id.picture_album_folder_5);
        mPictureAlbumFolder6 = (RelativeLayout)view.findViewById(R.id.picture_album_folder_6);
        mPictureAlbumFolder7 = (RelativeLayout)view.findViewById(R.id.picture_album_folder_7);
        mPictureAlbumFolder8 = (RelativeLayout)view.findViewById(R.id.picture_album_folder_8);
        mPictureAlbumFolder9 = (RelativeLayout)view.findViewById(R.id.picture_album_folder_9);
    }

    private void setOnClickListeners(){
        mPictureAlbumFolder1.setOnClickListener(onClickListener);
        mPictureAlbumFolder2.setOnClickListener(onClickListener);
        mPictureAlbumFolder3.setOnClickListener(onClickListener);
        mPictureAlbumFolder4.setOnClickListener(onClickListener);
        mPictureAlbumFolder5.setOnClickListener(onClickListener);
        mPictureAlbumFolder6.setOnClickListener(onClickListener);
        mPictureAlbumFolder7.setOnClickListener(onClickListener);
        mPictureAlbumFolder8.setOnClickListener(onClickListener);
        mPictureAlbumFolder9.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (DEBUG) Log.d(TAG, "click picture album folder");
            Intent intent = new Intent();
            intent.setClass(getActivity(), ActivityPictureAlbumDetail.class);
            startActivity(intent);
        }
    };
}
