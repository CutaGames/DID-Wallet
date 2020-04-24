package com.tancheng.carbonchain.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.tancheng.carbonchain.R;

public class CustomEditText extends EditText {

    private Context mContext;
    private int psdSize = 6;
    private int frameWidth ;//密码输入框的边框宽度px
    private int roundRadius ;//遮挡密码的圆的半径
    private int frameColor = 0xff333333;//密码输入框边框的颜色
    private int psdRoundWidth ;//密码的宽度
    private int psdRoundColor = 0xff333333;//密码的颜色
    private int frameLineWidth = 1;
    private int totalWidth;//控件的总宽度
    private int totalHight;//控件的总高度
    private Paint mPaint;//边框画笔
    private Paint psdPaint;//密码画笔

    private int inputPsdSize;//输入密码的个数



    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }



    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CustomInputPsdView);
        psdSize = typedArray.getInt(R.styleable.CustomInputPsdView_psdSize, psdSize);
        frameWidth = typedArray.getDimensionPixelSize(R.styleable.CustomInputPsdView_frameWidth, frameWidth);
        roundRadius = typedArray.getDimensionPixelSize(R.styleable.CustomInputPsdView_roundRadius, roundRadius);
        frameColor = typedArray.getColor(R.styleable.CustomInputPsdView_frameColor, frameColor);
        psdRoundWidth = typedArray.getDimensionPixelSize(R.styleable.CustomInputPsdView_psdRoundWidth, psdRoundColor);
        psdRoundColor = typedArray.getColor(R.styleable.CustomInputPsdView_psdRoundColor, psdRoundColor);
        typedArray.recycle();

        //初始化边框画笔
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(frameWidth);
        mPaint.setAntiAlias(true);

        //初始化密码画笔
        psdPaint = new Paint();
        psdPaint.setStyle(Paint.Style.FILL);
        psdPaint.setColor(psdRoundColor);
        psdPaint.setStrokeWidth(psdRoundWidth);
        psdPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画一个黑色矩形遮住显示的文字
        mPaint.setColor(frameColor);
        canvas.drawRoundRect(new RectF(0, 0, totalWidth, totalHight),roundRadius, roundRadius, mPaint);
        //再画一个白色矩形这种上面的矩形内部，形成假的边框
        mPaint.setColor(Color.WHITE);
        canvas.drawRoundRect(new RectF(frameLineWidth, frameLineWidth,
                totalWidth - frameLineWidth, totalHight - frameLineWidth),roundRadius,roundRadius, mPaint);
        //画分割线
        int lineStartAndEndX = totalWidth / psdSize;
        int lineStartY = 0;
        int lineEndY = totalHight;
        mPaint.setColor(frameColor);
        mPaint.setStrokeWidth(frameLineWidth);
        for (int i = 1; i < psdSize; i++) {
            canvas.drawLine(lineStartAndEndX, lineStartY, lineStartAndEndX, lineEndY, mPaint);
            lineStartAndEndX += totalWidth / psdSize;
        }

        //画密码圆
        int roundX = totalWidth / psdSize / 2;
        int roundY = totalHight / 2;
        if (inputPsdSize > 0) {
            for (int i = 0; i < inputPsdSize; i++) {
                canvas.drawCircle(roundX, roundY, psdRoundWidth, psdPaint);
                roundX += totalWidth / psdSize;
            }
        }

    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        totalWidth = w;
        totalHight = h;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (text.length() <= psdSize){
            inputPsdSize = text.length();
        }
        invalidate();
    }


}
