package com.tancheng.carbonchain.activities.asset.wallet.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.IndicatorViewPager;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.base.BaseFragment;

import java.util.List;

import static android.support.v4.view.PagerAdapter.POSITION_NONE;

/**
 * created by tc_collin on 2020/4/3
 * Description: 导入钱包
 * version: 1.0
 */

public class LoadWalletPageFragmentAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private String[] tabNams;

    private List<BaseFragment> fragmentList;
    private FragmentManager fm;
    private Context mContext;

    private LayoutInflater inflater;

    public LoadWalletPageFragmentAdapter(Context context, FragmentManager fragmentManager, List<BaseFragment> fragmentList,String[] tabNams) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
        this.fm = fm;
        this.mContext = context;
        this.tabNams = tabNams;
        inflater = LayoutInflater.from(context);
    }

//    public OrderPageFragmentAdapter(Context context, FragmentManager fm, List<BaseFragment> fragmentList) {
//        super();
//        this.fragmentList = fragmentList;
//        this.fm = fm;
//        this.mContext = context;
//    }


    @Override
    public int getCount() {
        return fragmentList.size();
    }


    @Override
    public Fragment getFragmentForPage(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.load_wallet_indicator_tab, container, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(tabNams[position]);
        return textView;
    }

    public void setFragments(List<BaseFragment> fragments) {
        if (this.fragmentList != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragmentList) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragmentList = fragments;
        notifyDataSetChanged();
    }
}
