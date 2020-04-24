package com.tancheng.carbonchain.utils.network;

import android.content.Context;
import com.tancheng.carbonchain.activities.asset.wallet.ui.view.loadView.LoadingView;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * created by collin on 2020/4/13 10:22
 * Description: 带有加载框的回调
 * version: 1.0
 */
public abstract class LoadCallBack<T> extends BaseCallBack<T> {
    private Context context;
    private LoadingView spotsDialog;

    public LoadCallBack(Context context) {
        this.context = context;
        spotsDialog = new LoadingView(context);
    }

    private void showDialog() {
        spotsDialog.start();
    }

    private void hideDialog() {
        if (spotsDialog != null) {
            spotsDialog.stop();
        }
    }

    public void setMsg(String str) {
//        spotsDialog.setMessage(str);
    }

    public void setMsg(int  resId) {
//        spotsDialog.setMessage(context.getString(resId));
    }


    @Override
    protected void OnRequestBefore(Request request) {
        showDialog();

    }

    @Override
    protected void onFailure(Call call, IOException e) {
        hideDialog();
    }

    @Override
    protected void onResponse(Response response) {
        hideDialog();
    }

    @Override
    protected void inProgress(int progress, long total, int id) {

    }
}
