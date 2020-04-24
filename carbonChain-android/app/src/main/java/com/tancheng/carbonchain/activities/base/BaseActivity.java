package com.tancheng.carbonchain.activities.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.ui.view.loadView.CustomDialog;
import com.tancheng.carbonchain.activities.bindsevice.ScreenSaverActivity;
import com.tancheng.carbonchain.utils.NetWorkHelper;
import com.tancheng.carbonchain.utils.PermissionsConstans;
import com.tancheng.carbonchain.utils.UserPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends AppCompatActivity {

    public static boolean isActive; //全局变量
    private UserPreference preference;
    protected Map<Integer, String> urlmap;
    private CustomDialog dialog;//进度条
    protected Activity mContext;
    private Unbinder unbinder;
    public Toolbar mCommonToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 1f)
                .init();
        mContext = this;
        unbinder = ButterKnife.bind(this);
        if (mCommonToolbar != null) {
            ImmersionBar.with(this)
                    .titleBar(mCommonToolbar, false)
                    .transparentStatusBar()
                    .statusBarDarkFont(true, 1f)
                    .navigationBarColor(R.color.black)
                    .statusBarDarkFont(true)
                    .statusBarColor(R.color.colorPrimary)  //指定状态栏颜色,根据情况是否设置
                    .init();

            setSupportActionBar(mCommonToolbar);
        }
        initDatas();
        configViews();

        preference = UserPreference.getUserPreference(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            urlmap = new ArrayMap<Integer, String>();
        }
    }

    @Override
    protected void onResume() {
//        if (!isActive) {
//            //app 从后台唤醒，进入前台
//            isActive = true;
//            Log.i("ACTIVITY", "程序从后台唤醒");
//            if (!TextUtils.isEmpty(preference.getPincode())){
//                startActivity(new Intent(getApplicationContext(), ScreenSaverActivity.class));
//            }
//        }
        super.onResume();
    }


    @Override
    protected void onStop() {
        if (!isAppOnForeground()) {
            //app 进入后台
            isActive = false;//记录当前已经进入后台
            Log.i("ACTIVITY", "程序进入后台");
        }
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        unbinder.unbind();
        dismissDialog();
    }

    // dialog
    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(this);
            dialog.setCancelable(true);
        }
        return dialog;
    }

    public void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    public void showDialog(String progressTip) {
        getDialog().show();
        if (progressTip != null) {
            getDialog().setTvProgress(progressTip);
        }
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }


    /**
     * okhttp 网络请求添加请求头
     *
     * @param context
     * @param url
     * @param mTag
     * @param map
     */
    protected void okHttpPost(Context context, String url, final Integer mTag, Map<String, Object> map, HttpHeaders headers)
    {
        if (urlmap != null) {
            String o = (String) map.get(mTag);
            if (TextUtils.isEmpty(o)) {
                urlmap.put(mTag, url);
            } else {
                httpRequestData(mTag, null, "正在加载数据请稍等...", "0");
                return;
            }
        }
        if (TextUtils.isEmpty(url) || map == null) {
            httpRequestData(mTag, null, "请求参数为空", "0");
            return;
        }
        if (!NetWorkHelper.isNetworkAvailable(context)) {
            httpRequestData(mTag, null, "无网络连接", "0");
            return;
        }
        Gson gson=new Gson();
        String mapJson=gson.toJson(map);
        PostRequest<String> request= OkGo.<String>post(url)
                .upJson(mapJson)
                .headers(headers)
                .tag(context);
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            request.params(entry.getKey(), entry.getValue() + "");
        }
        request.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

//                JcLog.e(activityName + mTag, "====" + response.body());
                JSONObject jsonObject = null;
                String jsondata = "";
                String msg = "";
                int status = 0;
                try {
                    jsonObject = new JSONObject(response.body());


                    status = jsonObject.getInt("status");
                    jsondata = jsonObject.getString("data");
                    msg = jsonObject.getString("msg");

                    JSONObject object =  jsonObject.getJSONObject("serverResponse");
                    status = Integer.parseInt(object.getString("status"));
                    msg = object.getString("msg");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 0) {
                    httpRequestData(mTag, null, msg, "0");
                } else if (status == 1) {
                    httpRequestData(mTag, jsondata, msg, "1");
                }
                if (urlmap != null) {
                    urlmap.remove(mTag);
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e("aaa", "请求错误");

                httpRequestData(mTag, null, "请求错误", "0");
                if (urlmap != null) {
                    urlmap.remove(mTag);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (urlmap != null) {
                    urlmap.remove(mTag);
                }
            }
        });
    }


    /**
     * 接口返回json数据
     *
     * @param mTag
     * @param data
     * @param mag
     * @param stauts
     */
    protected void httpRequestData(Integer mTag, String data, String mag, String stauts)
    {
        if (stauts.equals("1")) {
//            dismissLoading();
//            Toast.makeText(this, mag, Toast.LENGTH_SHORT).show();
            Log.e("aaaa", "aaaa" + mTag);
            return;
        }
    }


    /**
     * APP是否处于前台唤醒状态
     *
     * @return
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * 为子类提供一个权限检查方法
     *
     * @param permissions
     * @return
     */
    protected boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 权限请求申请
     *
     * @param requestCode 请求码
     * @param permissions 权限
     */
    protected void requestPermission(int requestCode, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionsConstans.WRITE_STORAGE_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doSDCardPermission();
                } else {
                    //TODO 提示用户权限未授予
                    Toast.makeText(BaseActivity.this, "WRITE_EXTERNAL_STORAGE 权限未开启", Toast.LENGTH_SHORT).show();
                }
                break;
            case PermissionsConstans.SECH_PHONE_CODE:
                if (permissions != null && permissions.length == 2) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                            doCallPhone();
                        } else {
                            //TODO 提示用户权限未授予
                            Toast.makeText(BaseActivity.this, "WRITE_EXTERNAL_STORAGE PERMISSION", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //TODO 提示用户权限未授予
                        Toast.makeText(BaseActivity.this, "READ_PHONE_STATE NO PERMISSION", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        doCallPhone();
                    } else {
                        //TODO 提示用户权限未授予
                        Toast.makeText(BaseActivity.this, "READ_PHONE_STATE NO PERMISSION", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case PermissionsConstans.INSEPCT_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doCameraPermission();
                } else {
                    //TODO 提示用户权限未授予
                    Toast.makeText(BaseActivity.this, "CAMERA NO PERMISSION", Toast.LENGTH_SHORT).show();
                }
            case PermissionsConstans.LOCATION_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doLocationPermission();
                } else {
                    //TODO 提示用户权限未授予
                    showToast("ACCESS_COARSE_LOCATION NO PERMISSION");
                }
                break;
            case PermissionsConstans.PERMISSIONS_ALL:
                if (permissions != null) {

                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        doAllPermission();
                    } else {
                        showToast("RECORD_AUDIO NO PERMISSION");
                        return;
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 默认的相机权限处理
     */
    protected void doCameraPermission() {
    }

    /**
     * 默认的全部权限处理
     */
    protected void doAllPermission() {
    }

    protected void doLocationPermission() {
    }

    /**
     * 默认的写SD权限处理
     */
    protected void doSDCardPermission() {
    }

    /**
     * 默认的打电话处理
     */
    protected void doCallPhone() {
    }

    /**
     * Toast
     *
     * @param msg
     */
    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 返回视图文件id
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化数据
     */
    public abstract void initDatas();

    /**
     * 对各种控件进行设置、适配、填充数据
     */
    public abstract void configViews();

}
