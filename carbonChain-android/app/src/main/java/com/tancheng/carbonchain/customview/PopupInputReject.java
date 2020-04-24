package com.tancheng.carbonchain.customview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tancheng.carbonchain.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 解除设备绑定弹框
 * Created by Administrator on 2019/4/3.
 */

public class PopupInputReject extends BasePopupWindow implements View.OnClickListener {
    private TextView tvCancel, tvOK;
    private EditText etContent;
    private PopupRejectInteface popupRejectInteface;
    private String content;
    public PopupInputReject(Context context, PopupRejectInteface popupReject) {
        super(context);
        popupRejectInteface=popupReject;
        tvCancel = findViewById(R.id.tvCancel);
        tvOK = findViewById(R.id.tvOK);
        etContent = findViewById(R.id.etContent);
        setAutoShowInputMethod(etContent, true);
        setPopupGravity(Gravity.CENTER);
        bindEvent();
    }

    private void bindEvent()
    {
        tvCancel.setOnClickListener(this);
        tvOK.setOnClickListener(this);
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                content=s.toString();
            }
        });
    }

    @Override
    public Animator onCreateShowAnimator()
    {
        return getDefaultSlideFromBottomAnimationSet();
    }


    @Override
    public View onCreateContentView()
    {
        return createPopupById(R.layout.popup_device_unbinding_layout);
    }

    @Override
    public Animator onCreateDismissAnimator()
    {
        AnimatorSet set = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            set = new AnimatorSet();
            if (getDisplayAnimateView() != null) {
                set.playTogether(
                        ObjectAnimator.ofFloat(getDisplayAnimateView(), "translationY", 0, 250).setDuration(400),
                        ObjectAnimator.ofFloat(getDisplayAnimateView(), "alpha", 1, 0.4f).setDuration(250 * 3 / 2));
            }
        }
        return set;
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.tvCancel:
                dismiss();
                break;
            case R.id.tvOK:
               if (!TextUtils.isEmpty(content)){
                   popupRejectInteface.onComplete(content);
               }else {
                   popupRejectInteface.onComplete("");
               }
               dismiss();
                break;
            default:
                break;
        }
    }

    public interface  PopupRejectInteface {
        void onComplete(String content);
    }

}
