package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * created by tc_collin on 2020/4/14 20:10
 * Description:
 * version: 1.0
 */
public class TestActivity extends BaseActivity {


    @BindView(R.id.banner)
    MZBannerView banner;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_banner;
    }

    @Override
    public void initDatas() {

        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.bg_banner_btc);
        list.add(R.mipmap.bg_banner_eth);
        // 设置数据
        banner.setPages(list, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
    }

    @Override
    public void configViews() {

    }
    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_test_banner,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }

}
