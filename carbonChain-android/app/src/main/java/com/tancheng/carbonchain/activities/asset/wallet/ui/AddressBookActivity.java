package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.AddressBook;
import com.tancheng.carbonchain.activities.asset.wallet.ui.adapter.AddressBookAdapter;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.db.gen.AddressBookDaoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import name.quanke.app.libs.emptylayout.EmptyLayout;

/**
 * created by tc_collin on 2020/4/2 10:07
 * Description: 地址簿
 * version: 1.0
 */
public class AddressBookActivity extends BaseActivity {

    private final int REQUEST_CODE_ADD_BOOK_ADDRESS = 100;
    public static boolean refreshData = false;
    @BindView(R.id.iv_title_left_back)
    ImageView ivTitleLeftBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @BindView(R.id.lv_address_book)
    ListView lvAddressBook;
    @BindView(R.id.sr_refreshLayout)
    SwipeRefreshLayout srRefreshLayout;
    @BindView(R.id.emptyLayout)
    EmptyLayout emptyLayout;

    AddressBookDaoUtils bookDaoUtils;
    private List<AddressBook> books;
    private AddressBookAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_book;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (refreshData) {
            refreshData();
            refreshData = false;
        }
    }

    @Override
    public void initDatas() {
        tvTitle.setText("地址簿");
        ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageResource(R.mipmap.ic_add_book);

        emptyLayout.setEmptyViewButtonId(emptyLayout.getEmptyViewButtonId());
        emptyLayout.setShowEmptyButton(true);
        emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddressBookAddActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_BOOK_ADDRESS);
            }
        });
    }

    @Override
    public void configViews() {
        refreshData();
        lvAddressBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(mContext, TokenTransferActivity.class);
                intent.putExtra("address", books.get(position).getAddress());
                setResult(100, intent);
                finish();
            }
        });
        srRefreshLayout.setColorSchemeColors(mContext.getResources().getColor(R.color.colorPrimary));
        srRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                srRefreshLayout.setRefreshing(false);
            }
        });
    }

    @OnClick({R.id.iv_title_left_back, R.id.iv_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left_back:
                finish();
                break;
            case R.id.iv_title_right:
                Intent intent = new Intent(mContext, AddressBookAddActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_BOOK_ADDRESS);
                break;
        }
    }

    private void refreshData() {
        if (bookDaoUtils == null) {
            bookDaoUtils = AddressBookDaoUtils.getIntence();
            books = bookDaoUtils.getBooks();
            adapter = new AddressBookAdapter(mContext, books, R.layout.list_item_address_book);
            lvAddressBook.setAdapter(adapter);
        } else {
            books.clear();
            List<AddressBook> datas = bookDaoUtils.getBooks();
            books.addAll(datas);
            adapter.notifyDataSetChanged();
        }
        if (books.size() == 0) {
            emptyLayout.showEmpty(R.mipmap.ic_no_record, "暂无记录");
            srRefreshLayout.setVisibility(View.GONE);
        } else {
            srRefreshLayout.setVisibility(View.VISIBLE);
            emptyLayout.hide();
        }
    }
}
