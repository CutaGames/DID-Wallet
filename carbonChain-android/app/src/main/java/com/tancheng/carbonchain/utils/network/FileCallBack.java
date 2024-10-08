package com.tancheng.carbonchain.utils.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * created by tc_collin on 2020/4/13 10:26
 * Description: 文件下载请求回调
 * version: 1.0
 */
public abstract class FileCallBack<T> extends BaseCallBack<T> {

    private Context mContext;
    private ProgressDialog mProgressDialog;

    public FileCallBack(Context context) {
        mContext = context;
        initDialog();
    }

    private void initDialog(){
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("下载中...");
        mProgressDialog.setCanceledOnTouchOutside(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(100);
    }

    private void hideDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void OnRequestBefore(Request request) {
        mProgressDialog.show();
    }

    @Override
    protected void onFailure(Call call, IOException e) {
        hideDialog();
    }

    @Override
    protected void onSuccess(Call call, Response response, T t) {
        Log.e("lgz", "onSuccess: >>>>>>>>>>>>>");
        hideDialog();
    }

    @Override
    protected void onEror(Call call, int statusCode, Exception e) {
        hideDialog();
    }

    @Override
    protected void inProgress(int progress, long total, int id) {
        Log.e("lgz", "inProgress: >>>>>>>>>>>>>"+progress);
        mProgressDialog.setProgress(progress);

    }
}
