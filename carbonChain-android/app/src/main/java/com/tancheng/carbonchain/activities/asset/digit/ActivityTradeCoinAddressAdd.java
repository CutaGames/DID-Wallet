package com.tancheng.carbonchain.activities.asset.digit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityTradeCoinAddressAdd extends Activity {

    private Button mBtnSave;
    private RelativeLayout mSelectCoinLayout;

    private PopupWindow mSelectCoinPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_coin_address_add);
        findViews();
        setOnClickListeners();
    }

    private void findViews(){
        mBtnSave = findViewById(R.id.btn_save);
        mSelectCoinLayout = findViewById(R.id.add_addr_select_coin);
    }

    private void setOnClickListeners(){
        // save
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // select coin
        mSelectCoinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectCoinPopWindow(view);
            }
        });
    }

    private void showSelectCoinPopWindow(View view){
        final List<String> itemList = new ArrayList<>();
        itemList.add(getString(R.string.coin_name_btc));
        itemList.add(getString(R.string.coin_name_usdt));
        itemList.add(getString(R.string.coin_name_xrp));
        itemList.add(getString(R.string.coin_name_eth));
        itemList.add(getString(R.string.coin_name_eos));
        itemList.add(getString(R.string.coin_name_ltc));
        if(mSelectCoinPopupWindow != null && mSelectCoinPopupWindow.isShowing()) {
            mSelectCoinPopupWindow.dismiss();
            mSelectCoinPopupWindow = null;
        }else {
            WindowManager windowManager  = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            View inflate = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tab_layout_popup_window, null);
            ListView listview = (ListView) inflate.findViewById(R.id.listview);

            listview.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return itemList.size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {
                    View view;
                    if(convertView == null) {
                        view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tab_layout_item_dance_category,null);
                    }else {
                        view = convertView;
                    }
                    final TextView name= (TextView) view.findViewById(R.id.dance_name);

                    name.setText(itemList.get(position));

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //点击事件

                            //关闭窗口
                            mSelectCoinPopupWindow.dismiss();

                        }
                    });

                    return view;
                }
            });

            mSelectCoinPopupWindow = new PopupWindow(inflate, view.getWidth(), ViewPager.LayoutParams.WRAP_CONTENT);
            mSelectCoinPopupWindow.setOutsideTouchable(true);

            int[] location = new int[2];
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1] + view.getHeight();
            mSelectCoinPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, x, y);
        }
    }

    @Override
    protected void onDestroy() {
        if(mSelectCoinPopupWindow != null){
            mSelectCoinPopupWindow.dismiss();
            mSelectCoinPopupWindow = null;
        }
        super.onDestroy();
    }
}
