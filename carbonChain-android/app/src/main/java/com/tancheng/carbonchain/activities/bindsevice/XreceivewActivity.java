package com.tancheng.carbonchain.activities.bindsevice;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.customview.mulbaseadapter.MultiItemTypeAdapter;
import com.tancheng.carbonchain.customview.xrecyclerview.XRecyclerView;


import java.util.ArrayList;
import java.util.List;

public class XreceivewActivity extends AppCompatActivity {
    private XRecyclerView xRecyclerView;
    private MultiItemTypeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xreceivew);
        initView();
    }

    private void initView()
    {
        xRecyclerView = findViewById(R.id.xRecyclerView);
//        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        xRecyclerView.setPullRefreshEnabled(false);
//        xRecyclerView.setLoadingMoreEnabled(false);

        //此种方法适用一个Iem有多个布局拼接
//        adapter=new MultiItemTypeAdapter(this,list);
//        adapter.addItemViewDelegate(new ApprovalPayTechnologyFeeDetailOne());
//        adapter.addItemViewDelegate(new ApprovalPayTechnologyFeeDetailTwo());
//        adapter.addItemViewDelegate(new ApprovalPayTechnologyFeeDetailThree());
        xRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

//    public  class ApprovalPayTechnologyFeeDetailOne implements ItemViewDelegate<PayTechnologyFeeDetailBean>
//    {
//        @Override
//        public int getItemViewLayoutId() {
//            return R.layout.pay_technology_fee_detail_one_layout;
//        }
//
//        @Override
//        public boolean isForViewType(PayTechnologyFeeDetailBean item, int position) {
//            return item.getType()==0;
//        }
//
//        @Override
//        public void convert(ViewHolder holder, PayTechnologyFeeDetailBean payTechnologyFeeDetailBean, int position) {
//
//
//        }
//    }
//
//    public  class ApprovalPayTechnologyFeeDetailTwo implements ItemViewDelegate<PayTechnologyFeeDetailBean>
//    {
//        @Override
//        public int getItemViewLayoutId() {
//            return R.layout.pay_technology_fee_detail_two_layout;
//        }
//
//        @Override
//        public boolean isForViewType(PayTechnologyFeeDetailBean item, int position) {
//            return item.getType()==1;
//        }
//
//        @Override
//        public void convert(ViewHolder holder, PayTechnologyFeeDetailBean payTechnologyFeeDetailBean, int position) {
//            SlideListView slideListView=holder.getView(R.id.slideListView);
//            List<String> list=new ArrayList<>();
//            list.add("");
//            list.add("");
//            list.add("");
//            PayTechnologyFeeDetailTwoAdapter payTechnologyFeeDetailTwoAdapter=new PayTechnologyFeeDetailTwoAdapter(ApprovalPayTechnologyFeeDetailActivity.this);
//            payTechnologyFeeDetailTwoAdapter.setList(list);
//            slideListView.setAdapter(payTechnologyFeeDetailTwoAdapter);
//            setListViewHeightBasedOnChildren(slideListView);
//            payTechnologyFeeDetailTwoAdapter.notifyDataSetChanged();
//        }
//    }
//
//    public class ApprovalPayTechnologyFeeDetailThree implements ItemViewDelegate<PayTechnologyFeeDetailBean>
//    {
//
//        @Override
//        public int getItemViewLayoutId() {
//            return R.layout.approval_list_layout;
//        }
//
//        @Override
//        public boolean isForViewType(PayTechnologyFeeDetailBean item, int position) {
//            return item.getType()==2;
//        }
//
//        @Override
//        public void convert(ViewHolder holder, PayTechnologyFeeDetailBean payTechnologyFeeDetailBean, int position) {
//            TextView tvOK=holder.getView(R.id.tvOK);
//            SlideListView slideListView=holder.getView(R.id.slideListView);
//            List<String> strings=new ArrayList<>();
//            strings.add("");
//            strings.add("");
//            strings.add("");
//            ApprovalAdapter approvalAdapter=new ApprovalAdapter(ApprovalPayTechnologyFeeDetailActivity.this);
//            approvalAdapter.setList(strings);
//            slideListView.setAdapter(approvalAdapter);
//            setListViewHeightBasedOnChildren(slideListView);
//            approvalAdapter.notifyDataSetChanged();
//
//            tvOK.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    popupBottomView.showPopupWindow();
//                }
//            });
//        }
//    }
//


//    public class MyAdapter extends CommonAdapter<ForigenSaleOrderContractItemDataBean>
//    {
//        public MyAdapter(Context context, int layoutId, List<ForigenSaleOrderContractItemDataBean> datas) {
//            super(context, layoutId, datas);
//        }
//
//        @Override
//        protected void convert(ViewHolder holder, ForigenSaleOrderContractItemDataBean forigenSaleOrderContractItemDataBean, int position) {
//            TextView tvOne = holder.getView(R.id.tvOne);
//            TextView tvPosition = holder.getView(R.id.tvPosition);
//            TextView tvLine = holder.getView(R.id.tvLine);
//            if (position < 10){
//                tvPosition.setText("0" + position);
//            }else {
//                tvPosition.setText("" + position);
//            }
//            if (contracts != null && contracts.size() > 0){
//                if (position == contracts.size()){
//                    tvLine.setVisibility(View.GONE);
//                }else {
//                    tvLine.setVisibility(View.VISIBLE);
//                }
//            }
//            if (forigenSaleOrderContractItemDataBean != null){
//                String content = forigenSaleOrderContractItemDataBean.getTermContent();
//                tvOne.setText(content);
//            }
//        }
//
//
//    }


}
