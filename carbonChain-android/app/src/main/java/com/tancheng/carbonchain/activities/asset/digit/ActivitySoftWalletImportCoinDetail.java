package com.tancheng.carbonchain.activities.asset.digit;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.NoScrollViewPager;
import com.tancheng.carbonchain.utils.dropdowntablayout.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActivitySoftWalletImportCoinDetail extends AppCompatActivity {

    private TabLayout mTabLayout;
    private NoScrollViewPager mViewPager;
    private TextView mTvStartImport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_wallet_import_coin_detail);
        findViews();
        inits();
        setOnClickListeners();
    }

    private void findViews(){
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setCanScroll(false);
        mTvStartImport = findViewById(R.id.tv_start_import);
    }

    private void inits(){
        List<Fragment> fragments = new ArrayList<>();
        FragmentImportWalletMnemonics fragmentImportWalletMnemonics = new FragmentImportWalletMnemonics();
        FragmentImportWalletPrivateKey fragmentImportWalletPrivateKey = new FragmentImportWalletPrivateKey();

        fragments.add(fragmentImportWalletMnemonics);
        fragments.add(fragmentImportWalletPrivateKey);
        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),fragments));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText(R.string.asset_add_soft_wallet_import_mnemonics_tab_text);
        mTabLayout.getTabAt(1).setText(R.string.asset_add_soft_wallet_import_private_key_tab_text);
    }

    private void setOnClickListeners(){
        mTvStartImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitySoftWalletImportCoinDetail.this, ActivitySoftWalletCreateSuccess.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
