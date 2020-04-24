package com.tancheng.carbonchain.activities.storage.mp3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.PublicBottomOperationsUtil;

public class ActivityMp3Play extends AppCompatActivity {

    private ViewGroup mViewGroup;
    private PublicBottomOperationsUtil mPublicBottomOperationsUtil;
    private MusicListUtil mMusicListUtil;
    private ImageView mMusicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_play);
        findViews();
        setOnClickListeners();
        initBottomOperationUtil();
    }

    private void findViews(){
        mViewGroup = findViewById(R.id.root_layout);
        mMusicList = findViewById(R.id.mp3_play_control_list);
    }

    private void setOnClickListeners(){
        mMusicList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMusicListUtil.isAddToParentView = true;
                mViewGroup.addView(mMusicListUtil);
            }
        });
    }

    private void initBottomOperationUtil(){
        mPublicBottomOperationsUtil = new PublicBottomOperationsUtil(ActivityMp3Play.this, mViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicBottomOperationsUtil.setLayoutParams(bottomOperationsLayoutParams);
        mPublicBottomOperationsUtil.setTypeMp3();

        mPublicBottomOperationsUtil.setMoreFileName("Close To You.mp3");

        mPublicBottomOperationsUtil.isAddToParentView = true;
        mViewGroup.addView(mPublicBottomOperationsUtil);

        mMusicListUtil = new MusicListUtil(ActivityMp3Play.this, mViewGroup);
        RelativeLayout.LayoutParams musicListLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mMusicListUtil.setLayoutParams(musicListLayoutParams);
    }

    @Override
    public void onBackPressed() {
        if(mPublicBottomOperationsUtil.doBackPressedNotRemoveFromCurrentParent()){

        }else if(mMusicListUtil.doBackPress()){

        }else {
            super.onBackPressed();
        }
    }
}
