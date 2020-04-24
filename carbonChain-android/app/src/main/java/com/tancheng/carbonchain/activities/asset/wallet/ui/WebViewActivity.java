package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.base.BaseActivity;

import butterknife.BindView;

/**
 * @title 网页链接页面
 */
@SuppressLint("JavascriptInterface")
public class WebViewActivity extends BaseActivity {
    @BindView(R.id.wv_banner)
    WebView webView;
    @BindView(R.id.pb_web)
    ProgressBar pbWeb;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_left_back)
    ImageView ivBack;

    String webUrl = "";
    String webTitle = "";

    private static final String INTENT_WEB_URL = "webUrl";
    private static final String INTENT_WEB_Title = "webTitle";

    public static void startAct(Context mContext, String webUrl, String webTitle) {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra(INTENT_WEB_URL, webUrl);
        intent.putExtra(INTENT_WEB_Title, webTitle);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_webview;
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        webUrl = intent.getStringExtra(INTENT_WEB_URL);
        webTitle = intent.getStringExtra(INTENT_WEB_Title);
        tvTitle.setText(webTitle);
        init();
    }

    @Override
    public void configViews() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        webUrl = getIntent().getStringExtra("url") == null ? ""
                : getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title") == null ? ""
                : getIntent().getStringExtra("title");

        LogUtils.i("WebViewActivity: --> " + webUrl);
        //设置webview页面
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pbWeb.setVisibility(View.GONE);
                } else {
                    if (View.INVISIBLE == pbWeb.getVisibility()) {
                        pbWeb.setVisibility(View.VISIBLE);
                    }
                    pbWeb.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult jsResult) {
                final JsResult finalJsResult = jsResult;
                new AlertDialog.Builder(WebViewActivity.this)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        finalJsResult.confirm();
                                    }
                                }).setCancelable(false).create().show();
                return true;
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 设置
        // 缓存模式
        // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        // 开启 database storage API 功能
        webView.getSettings().setDatabaseEnabled(true);

        // 开启 Application Caches 功能
        webView.getSettings().setAppCacheEnabled(true);
        webView.loadUrl(webUrl);
    }

    //
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("qmcf://")) {
                Uri uri = Uri.parse(url);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                webView.loadUrl(webUrl);
                return true;
            }
            //解决 net::ERR_UNKNOWN_URL_SCHEME错误
            if (url.startsWith("weixin://") //微信
                    || url.startsWith("alipays://") //支付宝
                    || url.startsWith("mailto://") //邮件
                    || url.startsWith("tel:")//电话
            ) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            dismissDialog();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showDialog("页面加载中");
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            ToastUtils.showShort("Ssl证书出错！");
            dismissDialog();
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (webUrl.equals(webView.getUrl())) {
//                finish();
//            } else {
//                webView.goBack();
//            }
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
