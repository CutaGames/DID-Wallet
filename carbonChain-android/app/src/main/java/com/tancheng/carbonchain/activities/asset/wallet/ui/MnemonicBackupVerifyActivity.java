package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.domain.VerifyMnemonicWordTag;
import com.tancheng.carbonchain.activities.asset.wallet.ui.adapter.MnemonicWordsAdapter;
import com.tancheng.carbonchain.activities.asset.wallet.ui.adapter.MnemonicSelectedWordsAdapter;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.db.gen.WalletDaoUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by collin on 2020/3/27 14:28
 * Description: 验证助记词
 * version: 1.0
 */
public class MnemonicBackupVerifyActivity extends BaseActivity {

    private static final int VERIFY_SUCCESS_RESULT = 2202;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_selected)
    RecyclerView rvSelected;
    @BindView(R.id.rv_mnemonic)
    RecyclerView rvMnemonic;
    private String walletMnemonic;

    private List<VerifyMnemonicWordTag> mnemonicWords;

    private List<String> selectedMnemonicWords;

    private MnemonicWordsAdapter verifyBackupMenmonicWordsAdapter;
    private MnemonicSelectedWordsAdapter verifyBackupSelectedMnemonicWordsAdapter;
    private long walletId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_verify_mnemonic_backup;
    }

    @Override
    public void initDatas() {
        walletId = getIntent().getLongExtra("walletId", -1);
        walletMnemonic = getIntent().getStringExtra("walletMnemonic");

        LogUtils.d("VerifyMnemonicBackUp", "walletMnemonic:" + walletMnemonic);

        String[] words = walletMnemonic.split("\\s+");
        mnemonicWords = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            VerifyMnemonicWordTag verifyMnemonicWordTag = new VerifyMnemonicWordTag();
            verifyMnemonicWordTag.setMnemonicWord(words[i]);
            mnemonicWords.add(verifyMnemonicWordTag);
        }
        // 乱序
        Collections.shuffle(mnemonicWords);

        // 未选中单词
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        rvMnemonic.setLayoutManager(layoutManager);
        verifyBackupMenmonicWordsAdapter = new MnemonicWordsAdapter(R.layout.list_item_mnemoic, mnemonicWords);
        rvMnemonic.setAdapter(verifyBackupMenmonicWordsAdapter);

        // 已选中单词
        FlexboxLayoutManager layoutManager2 = new FlexboxLayoutManager(this);
        layoutManager2.setFlexWrap(FlexWrap.WRAP);
        layoutManager2.setAlignItems(AlignItems.STRETCH);
        rvSelected.setLayoutManager(layoutManager2);
        selectedMnemonicWords = new ArrayList<>();
        verifyBackupSelectedMnemonicWordsAdapter = new MnemonicSelectedWordsAdapter(R.layout.list_item_mnemoic_selected, selectedMnemonicWords);
        rvSelected.setAdapter(verifyBackupSelectedMnemonicWordsAdapter);
    }

    @Override
    public void configViews() {
        verifyBackupMenmonicWordsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String mnemonicWord = verifyBackupMenmonicWordsAdapter.getData().get(position).getMnemonicWord();
                if (verifyBackupMenmonicWordsAdapter.setSelection(position)) {
                    verifyBackupSelectedMnemonicWordsAdapter.addData(mnemonicWord);
                }
            }
        });


        verifyBackupSelectedMnemonicWordsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                List<VerifyMnemonicWordTag> datas = verifyBackupMenmonicWordsAdapter.getData();
                for (int i = 0; i < datas.size(); i++) {
                    if (TextUtils.equals(datas.get(i).getMnemonicWord(), verifyBackupSelectedMnemonicWordsAdapter.getData().get(position))) {
                        verifyBackupMenmonicWordsAdapter.setUnselected(i);
                        break;
                    }
                }
                verifyBackupSelectedMnemonicWordsAdapter.remove(position);
            }

        });
    }

    @OnClick({R.id.iv_title_left_back,R.id.btn_confirm})
    public void onClick(View view){
      switch (view.getId()){
          case R.id.iv_title_left_back:
              finish();
              break;

          case R.id.btn_confirm:
              LogUtils.d("VerifyMnemonicBackUp", "Click!!");
              List<String> data = verifyBackupSelectedMnemonicWordsAdapter.getData();
              int size = data.size();
              if (size == 12) {
                  StringBuilder stringBuilder = new StringBuilder();
                  for (int i = 0; i < size; i++) {
                      stringBuilder.append(data.get(i));
                      if (i != size - 1) {
                          stringBuilder.append(" ");
                      }
                  }
                  String verifyMnemonic = stringBuilder.toString();
                  String trim = verifyMnemonic.trim();
                  LogUtils.d("VerifyMnemonicBackUp", "verifyMnemonic:" + verifyMnemonic);
                  LogUtils.d("VerifyMnemonicBackUp", "trim:" + trim);
                  if (TextUtils.equals(trim, walletMnemonic)) {
                      ETHWallet walletInfo = WalletDaoUtils.getIntence().getWalletInfo(walletId);
                      if (walletInfo!=null){
                          walletInfo.setBackup(true);
                          WalletDaoUtils.getIntence().updateWallet(walletInfo);
                      }
                      setResult(VERIFY_SUCCESS_RESULT, new Intent());
                      finish();
                  } else {
                      ToastUtils.showShort(R.string.verify_backup_failed);
                  }
              } else {
                  ToastUtils.showShort(R.string.verify_backup_failed);
              }
              break;
      }
    }

}
