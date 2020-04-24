package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.AddressBook;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.ui.view.InputPwdDialog;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.activities.bindsevice.CustomCaptureActivity;
import com.tancheng.carbonchain.db.gen.AddressBookDaoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by tc_collin on 2020/4/2 10:17
 * Description: 添加、编辑地址簿
 * version: 1.0
 */
public class AddressBookAddActivity extends BaseActivity {

    final static int REQUEST_CODE_SCANNER = 100;
    @BindView(R.id.iv_title_left_back)
    ImageView ivTitleLeftBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.sp_wallet_type)
    Spinner spWalletType;
    @BindView(R.id.et_book_name)
    EditText etBookName;
    @BindView(R.id.et_book_address)
    EditText etBookAddress;
    @BindView(R.id.iv_qr_scanner)
    ImageView ivQrScanner;
    @BindView(R.id.et_book_remark)
    EditText etBookRemark;
    @BindView(R.id.btn_create_book)
    TextView btnCreateBook;
    @BindView(R.id.btn_del_book)
    TextView btnDelBook;

    AddressBookDaoUtils bookDaoUtils;
    AddressBook addressBookInfo;
    int walletTypeId = 0;

    public static void startAct(Activity activity, long bookId) {
        Intent intent = new Intent(activity, AddressBookAddActivity.class);
        intent.putExtra("addressBookId", bookId);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_book_add;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("添加地址簿");
        Intent intent = getIntent();
        if (intent != null) {
            long addressBookId = intent.getLongExtra("addressBookId", -1);
            bookDaoUtils = AddressBookDaoUtils.getIntence();
            if (addressBookId > -1) {
                addressBookInfo = bookDaoUtils.getAddressBookInfo(addressBookId);
                if (addressBookInfo != null) {
                    walletTypeId = addressBookInfo.getWalletType();
                    String bookName = addressBookInfo.getName();
                    String bookAddr = addressBookInfo.getAddress();
                    String remark = addressBookInfo.getRemark();
                    etBookName.setText(bookName);
                    etBookAddress.setText(bookAddr);
                    etBookRemark.setText(StringUtils.isEmpty(remark) == true ? "" : remark);
                    btnDelBook.setVisibility(View.VISIBLE);
                    tvTitle.setText("编辑地址簿");
                }
            } else {
                btnDelBook.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void configViews() {
        List<WalletType> walletTypes = WalletType.walletTypeList();
        List<String> items = new ArrayList<>();
        for (WalletType type : walletTypes) {
            items.add(type.name());
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWalletType.setAdapter(adapter);
        spWalletType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                walletTypeId = walletTypes.get(i).getWalletType();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        int selectPosition = -1;
        for (int i = 0; i < walletTypes.size(); i++) {
            WalletType type = walletTypes.get(i);
            if (type.getWalletType() == walletTypeId)
                selectPosition = i;
        }
        if (selectPosition == -1) {
            walletTypeId = walletTypes.get(0).getWalletType();
            selectPosition = 0;
        }
        spWalletType.setSelection(selectPosition);
    }

    @OnClick({R.id.iv_title_left_back, R.id.iv_qr_scanner, R.id.btn_create_book, R.id.btn_del_book})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left_back:
                finish();
                break;
            case R.id.iv_qr_scanner://扫码
                Intent intent = new Intent(mContext, CustomCaptureActivity.class);
                intent.putExtra(CustomCaptureActivity.INTENT_SCANNER_TYPE, CustomCaptureActivity.ADDRESS_SCANNER);
                startActivityForResult(intent, REQUEST_CODE_SCANNER);
                break;
            case R.id.btn_create_book://保存
                String bookAddress = etBookAddress.getText().toString().trim();
                String bookName = etBookName.getText().toString().trim();
                String remark = etBookRemark.getText().toString().trim();
                boolean b = verifyInfo(bookName, bookAddress);
                if (b) {
                    if (addressBookInfo == null) {
                        addressBookInfo = new AddressBook();
                        addressBookInfo.setWalletType(walletTypeId);
                        addressBookInfo.setName(bookName);
                        addressBookInfo.setAddress(bookAddress);
                        addressBookInfo.setRemark(remark);
                        bookDaoUtils.insertBook(addressBookInfo);
                        ToastUtils.showShort("保存成功!");
                    } else {
                        addressBookInfo.setWalletType(walletTypeId);
                        addressBookInfo.setName(bookName);
                        addressBookInfo.setAddress(bookAddress);
                        addressBookInfo.setRemark(remark);
                        bookDaoUtils.updateBook(addressBookInfo);
                        ToastUtils.showShort("修改成功!");
                    }
                    AddressBookActivity.refreshData = true;
                    finish();
                }
                break;
            case R.id.btn_del_book://删除
                if (addressBookInfo != null) {
                    AlertDialog.Builder delDialog = new AlertDialog.Builder(this);
                    delDialog.setTitle("删除提示");
                    delDialog.setMessage("确定要删除该条地址吗？");
                    delDialog.setCancelable(true);
                    delDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            bookDaoUtils.delBook(addressBookInfo.getId());
                            AddressBookActivity.refreshData = true;
                            ToastUtils.showShort("删除成功");
                            finish();
                        }
                    });
                    delDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    delDialog.show();
                }
                break;
        }
    }

    /**
     * 内容验证
     *
     * @param bookName
     * @param bookAddr
     * @return
     */
    private boolean verifyInfo(String bookName, String bookAddr) {
        if (StringUtils.isEmpty(bookName)) {
            ToastUtils.showShort("请输入名称");
            return false;
        }
        if (addressBookInfo == null) {
            AddressBookDaoUtils bookDaoUtils = AddressBookDaoUtils.getIntence();
            boolean isExist = bookDaoUtils.bookNameIsExist(bookName);
            if (isExist) {
                ToastUtils.showShort("该名称已存在!");
                return false;
            }
        }
        if (StringUtils.isEmpty(bookAddr)) {
            ToastUtils.showShort("请输入地址");
            return false;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            if (requestCode == REQUEST_CODE_SCANNER) {
                String stringExtra = data.getStringExtra(CustomCaptureActivity.RESULT_DATA);
                if (!StringUtils.isEmpty(stringExtra)) {
                    etBookAddress.setText(stringExtra);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
