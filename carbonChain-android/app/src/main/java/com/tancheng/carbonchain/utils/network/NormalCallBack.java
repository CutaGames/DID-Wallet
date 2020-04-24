package com.tancheng.carbonchain.utils.network;

import android.content.Context;

import com.tancheng.carbonchain.activities.asset.wallet.ui.view.loadView.LoadingView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * created by collin on 2020/4/13 10:22
 * Description:
 * version: 1.0
 */
public abstract class NormalCallBack<T> extends BaseCallBack<T> {

    public NormalCallBack() {
    }

    @Override
    protected void OnRequestBefore(Request request) {

    }

    @Override
    protected void onFailure(Call call, IOException e) {

    }

    @Override
    protected void onResponse(Response response) {

    }

}
