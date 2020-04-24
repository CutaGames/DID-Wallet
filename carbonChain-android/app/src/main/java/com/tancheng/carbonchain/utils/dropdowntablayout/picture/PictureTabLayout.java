package com.tancheng.carbonchain.utils.dropdowntablayout.picture;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tancheng.carbonchain.R;

import java.util.List;

/**
 * Created by gll on 2020/2/13.
 */

public class PictureTabLayout extends TabLayout {
    private String TAG = getClass().getSimpleName();
    private Context mContext;
    public PictureTabLayout(Context context) {
        this(context, null);
    }

    public PictureTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PictureTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    private SparseArray sparseArray = new SparseArray<Integer>();
    private TextView mTabTextTime;
    private ImageView mTabImageTime;
    private TextView mTabTextAlbum;
    private View mTabTimeBg;
    private View mTabAlbumBg;

    public void setUpTitle( List<List<String>> titleList){
        for (int i=0; i< titleList.size(); i++){
//            addTab(newTab().setText(titleList.get(i).get(0)));//默认=取第一个作为首次加载显示的tab名字
            getTabAt(i).setCustomView(getTabView(i, titleList.get(i)));
            sparseArray.put(i, 0);
        }
    }

    /**
     * 得到自定义的tabItem
     * @param position
     * @return
     */
    public View getTabView(final int position, final List<String> titleList){
        final View view = LayoutInflater.from(mContext).inflate(R.layout.custom_tab_layout_picture_item_tab, null);
        final TextView tabText = view.findViewById(R.id.tab_text);

        tabText.setTextColor(getResources().getColor(R.color.drop_down_tab_layout_text_color_false));

        ImageView tabImg = view.findViewById(R.id.tab_img);
        View tabBg = view.findViewById(R.id.tab_bg);
        LinearLayout textAndImg = view.findViewById(R.id.tab_text_img_layout);
        if(titleList.size() == 1) {
            //右边 专辑 tab
            tabImg.setVisibility(GONE);
            tabText.setText(titleList.get(0));
            mTabTextAlbum = tabText;
            mTabAlbumBg = tabBg;
        }else{
            //左边 筛选 tab
            tabText.setText(getResources().getString(R.string.drop_down_tab_layout_item_text_pre) + titleList.get(0));
            mTabTextTime = tabText;
            mTabImageTime = tabImg;
            mTabTimeBg = tabBg;
            textAndImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(position);
                    showPopupwindow(tabText,position,view, titleList);
                }
            });
        }
        return view;
    }

    public void changeTabColor(int tabPosition){
        Log.d(TAG,"tabPosition=" + tabPosition);
        if(tabPosition == 0){
            mTabTextTime.setTextColor(getResources().getColor(R.color.drop_down_tab_layout_text_color_true));
            mTabTextAlbum.setTextColor(getResources().getColor(R.color.drop_down_tab_layout_text_color_false));
            mTabImageTime.setImageResource(R.mipmap.arrow_down_select_true);
            mTabTimeBg.setVisibility(View.VISIBLE);
            mTabAlbumBg.setVisibility(View.GONE);
        }else{
            mTabTextTime.setTextColor(getResources().getColor(R.color.drop_down_tab_layout_text_color_false));
            mTabTextAlbum.setTextColor(getResources().getColor(R.color.drop_down_tab_layout_text_color_true));
            mTabImageTime.setImageResource(R.mipmap.arrow_down_select_false);
            mTabTimeBg.setVisibility(View.GONE);
            mTabAlbumBg.setVisibility(View.VISIBLE);
        }
    }

    private PopupWindow popupWindow;
    private void showPopupwindow(final TextView tabText, final int tabPosition, View v, final List<String> titleList) {
        if(popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }else {
            WindowManager wm  = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.custom_tab_layout_popup_window, null);
            ListView listview = (ListView) inflate.findViewById(R.id.listview);

            listview.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return titleList.size();
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
                        view = LayoutInflater.from(mContext).inflate(R.layout.custom_tab_layout_item_dance_category,null);
                    }else {
                        view = convertView;
                    }
                    final TextView name= (TextView) view.findViewById(R.id.dance_name);


                    name.setText(titleList.get(position));

                    if(position == (int)sparseArray.get(tabPosition)) {
                        //ivSelected.setVisibility(View.VISIBLE);
                        name.setTextColor(getResources().getColor(R.color.drop_down_tab_layout_text_color_true));
                    }else{
                        name.setTextColor(getResources().getColor(R.color.drop_down_tab_layout_text_color_false));
                        //ivSelected.setVisibility(View.INVISIBLE);
                    }

                    view.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sparseArray.setValueAt(tabPosition, position);

                            mViewPager.setCurrentItem(tabPosition);
                            tabText.setText(getResources().getString(R.string.drop_down_tab_layout_item_text_pre) + titleList.get(position));
                            tabText.setTextColor(getResources().getColor(R.color.drop_down_tab_layout_text_color_true));
                            onTabSelectedListener.selected(tabPosition, position);

                            //关闭窗口
                            popupWindow.dismiss();

                        }
                    });

                    return view;
                }
            });

            popupWindow = new PopupWindow(inflate, wm.getDefaultDisplay().getWidth()/2, ViewPager.LayoutParams.WRAP_CONTENT);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);

            int[] location = new int[2];
            v.getLocationOnScreen(location);
//            int x = wm.getDefaultDisplay().getWidth()/4 * 2;
            int x = 0;
            int y = location[1] + v.getHeight();
            popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, x, y);
        }
    }


    private ViewPager mViewPager;
    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        super.setupWithViewPager(viewPager);
        mViewPager = viewPager;
    }

    public interface OnTabSelectedListener{
        void selected(int tabPosition, int listPosition);
    }
    private OnTabSelectedListener onTabSelectedListener;

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener){
        this.onTabSelectedListener = onTabSelectedListener;
    }

    public void dismissIfNeed(){
        if(popupWindow != null){
            popupWindow.dismiss();
        }
    }
}
