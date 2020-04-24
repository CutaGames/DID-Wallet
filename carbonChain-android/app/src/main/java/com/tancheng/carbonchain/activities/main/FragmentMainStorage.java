package com.tancheng.carbonchain.activities.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.storage.document.ActivityDocumentOverView;
import com.tancheng.carbonchain.activities.storage.mp3.ActivityMp3OverView;
import com.tancheng.carbonchain.activities.storage.mycollect.ActivityMyCollect;
import com.tancheng.carbonchain.activities.storage.pubspace.ActivityPubSpace;
import com.tancheng.carbonchain.activities.storage.secure.ActivitySecure;
import com.tancheng.carbonchain.activities.storage.secure.ActivitySecureUnlock;
import com.tancheng.carbonchain.activities.storage.transfer.ActivityTransfer;
import com.tancheng.carbonchain.activities.storage.phonebackup.ActivityPhoneBackup;
import com.tancheng.carbonchain.activities.storage.picture.ActivityPictureOverView;
import com.tancheng.carbonchain.activities.storage.ActivityRecycle;
import com.tancheng.carbonchain.activities.storage.ActivitySearch;
import com.tancheng.carbonchain.activities.storage.video.ActivityVideoOverView;
import com.tancheng.carbonchain.activities.upload.ActivityUpload;

/**
 * Created by gll on 2020/2/8.
 */

public class FragmentMainStorage extends Fragment {

    private static final Boolean DEBUG = true;
    private static final String TAG = "FragmentMainStorage";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_storage, null);
        setOnClickListeners(view);
        return view;
    }

    private void setOnClickListeners(View view){
        ImageView searchIcon = view.findViewById(R.id.search_icon);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG) Log.d(TAG, "click search icon");
                Intent intent = new Intent();
                intent.setClass(getActivity(), ActivitySearch.class);
                startActivity(intent);
            }
        });

        ImageView uploadIcon = view.findViewById(R.id.upload_icon);
        uploadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG) Log.d(TAG, "click upload icon");
                Intent intent = new Intent();
                intent.setClass(getActivity(), ActivityUpload.class);
                startActivity(intent);
            }
        });

        RelativeLayout recycleLayout = view.findViewById(R.id.main_storage_recycle_layout);
        recycleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click trash layout");
                Intent intent = new Intent();
                intent.setClass(getActivity(), ActivityRecycle.class);
                startActivity(intent);
            }
        });

        RelativeLayout pictureLayout = view.findViewById(R.id.main_storage_picture_layout);
        pictureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click picture layout");
                Intent intent = new Intent();
                intent.setClass(getActivity(), ActivityPictureOverView.class);
                startActivity(intent);
            }
        });

        RelativeLayout videoLayout = view.findViewById(R.id.main_storage_video_layout);
        videoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click video layout");
                Intent intent = new Intent();
                intent.setClass(getActivity(), ActivityVideoOverView.class);
                startActivity(intent);
            }
        });

        RelativeLayout phoneBackUpLayout = view.findViewById(R.id.main_storage_phone_backup_layout);
        phoneBackUpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click phone backup layout");
                Intent intent = new Intent();
                intent.setClass(getActivity(), ActivityPhoneBackup.class);
                startActivity(intent);
            }
        });

        RelativeLayout transferLayout = view.findViewById(R.id.main_storage_transfer_layout);
        transferLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click transfer layout");
                Intent intent = new Intent();
                intent.setClass(getActivity(), ActivityTransfer.class);
                startActivity(intent);
            }
        });

        RelativeLayout mp3Layout = view.findViewById(R.id.main_storage_mp3_layout);
        mp3Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click mp3 layout");
                Intent intent = new Intent();
                intent.setClass(getActivity(), ActivityMp3OverView.class);
                startActivity(intent);
            }
        });

        RelativeLayout documentLayout = view.findViewById(R.id.main_storage_document_layout);
        documentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click document layout");
                Intent intent = new Intent();
                intent.setClass(getActivity(), ActivityDocumentOverView.class);
                startActivity(intent);
            }
        });

        RelativeLayout myCollectLayout = view.findViewById(R.id.main_storage_my_collect_layout);
        myCollectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click my collect layout");
                Intent intent = new Intent();
                intent.setClass(getActivity(), ActivityMyCollect.class);
                startActivity(intent);
            }
        });

        RelativeLayout pubSpaceLayout = view.findViewById(R.id.main_storage_pub_space_layout);
        pubSpaceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click pub space layout");
                Intent intent = new Intent();
                intent.setClass(getActivity(), ActivityPubSpace.class);
                startActivity(intent);
            }
        });

        RelativeLayout secureLayout = view.findViewById(R.id.main_storage_secure_layout);
        secureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click secure layout");
                Intent intent = new Intent();
                intent.setClass(getActivity(), ActivitySecureUnlock.class);
                startActivity(intent);
            }
        });




    }
}
