package com.tancheng.carbonchain.activities.storage.secure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.tancheng.carbonchain.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gll on 20/2/19.
 */
public class GraphicLockView extends View {
    /**
     * 图形解锁的密码
     * 解锁前，请给该变量赋值为你的密码
     */
    public static String mPassword;

    private Point[][] points = new Point[3][3];  //创建一个3行3列的点数组

    private boolean isInit;   //判断有没有初始化

    private boolean isSelect;  //判断手指第一次按下屏幕有没有选中点

    private boolean isFinishMove;   //表示一次完整的图案绘制是否结束

    private boolean isMoveButNotPoint;   //表示手指在移动,但是并不是九宫格中的点

    private float width, height;   //屏幕宽高

    private static final int MIN_POINT = 4;    //最小能构成密码的点数

    private float offsetsX, offsetsY; //偏移量(在这里偏移量等于大边减去小边再除以2)

    private float bitmapR;   //图片资源的半径

    private float moveX, moveY;  //手势移动的x,y坐标

    private Bitmap bpPointNormal, bpPointPressed, bpPointError;  //点的三种图片

    private Bitmap bpLinePressed, bpLineError;  //线的三种图片

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private List<Point> selectPointList = new ArrayList<>();   //储存按下的点的集合

    private Matrix matrix = new Matrix();  //矩阵,用来处理线的缩放

    private OnGraphicLockListener onGraphicLockListener;   //对外的监听器

    public GraphicLockView(Context context) {
        super(context);
    }

    public GraphicLockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GraphicLockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnGraphicLockListener(OnGraphicLockListener onGraphicLockListener) {
        this.onGraphicLockListener = onGraphicLockListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //初始化，只执行一次即可
        if (!isInit) {
            //初始化点
            initPoints();
        }
        //绘制——将点绘制到画布上
        pointToCanvas(canvas);
        //绘制刷新时，根据点绘制连线
        if (selectPointList.size() > 0) {
            Point startPoint = selectPointList.get(0);
            //绘制九宫格坐标里的点
            for (int i = 0; i < selectPointList.size(); i++) {
                Point endPoint = selectPointList.get(i);
                lineToCanvas(canvas, startPoint, endPoint);
                startPoint = endPoint;
            }
            //绘制九宫格坐标以外的点
            if (isMoveButNotPoint) {
                lineToCanvas(canvas, startPoint, new Point(moveX, moveY));
            }
        }
    }

    /**
     * 初始化点
     */
    private void initPoints() {
        //1.根据屏幕尺寸，计算偏移量
        width = getWidth();
        height = getHeight();
        if (width > height) {   //横屏
            //横屏时只有x坐标有偏移量
            offsetsX = (width - height) / 2;
            width = height;
        } else {   //竖屏
            //竖屏时只有y坐标有偏移量
            offsetsY = (height - width) / 2;
            height = width;
        }
        //2。指定资源文件
        bpPointNormal = BitmapFactory.decodeResource(getResources(), R.mipmap.graphic_unlock_point_normal);
        bpPointPressed = BitmapFactory.decodeResource(getResources(), R.mipmap.graphic_unlock_point_pressed);
        bpPointError = BitmapFactory.decodeResource(getResources(), R.mipmap.graphic_unlock_point_error);
        bpLinePressed = BitmapFactory.decodeResource(getResources(), R.drawable.graphic_unlock_line_pressed);
        bpLineError = BitmapFactory.decodeResource(getResources(), R.drawable.graphic_unlock_line_error);
        //3.点的坐标
        //第一排
        points[0][0] = new Point(offsetsX + width / 4, offsetsY + height / 4);
        points[0][1] = new Point(offsetsX + width / 2, offsetsY + height / 4);
        points[0][2] = new Point(offsetsX + width - width / 4, offsetsY + height / 4);
        //第二排
        points[1][0] = new Point(offsetsX + width / 4, offsetsY + height / 2);
        points[1][1] = new Point(offsetsX + width / 2, offsetsY + height / 2);
        points[1][2] = new Point(offsetsX + width - width / 4, offsetsY + height / 2);
        //第三排
        points[2][0] = new Point(offsetsX + width / 4, offsetsY + height - height / 4);
        points[2][1] = new Point(offsetsX + width / 2, offsetsY + height - height / 4);
        points[2][2] = new Point(offsetsX + width - width / 4, offsetsY + height - height / 4);
        //4.计算图片资源的半径
        bitmapR = bpPointNormal.getWidth() / 2;
        //5.设置密码按键,初始化每个点,设置为1——9
        int index = 1;
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                points[i][j].index = index;
                index++;
            }
        }
        //初始化完成
        isInit = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        moveX = event.getX();
        moveY = event.getY();
        isFinishMove = false;
        isMoveButNotPoint = false;
        Point point = null;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //每次手指按下的时候都表示重新绘制图案
                resetPoint();
                //检查当前按下的点与九宫格中的九个点是否吻合
                point = checkSelectPoint();
                if (point != null) {
                    isSelect = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isSelect) {
                    point = checkSelectPoint();
                    if (point == null) {
                        isMoveButNotPoint = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isFinishMove = true;
                isSelect = false;
                break;
        }
        //选中重复检查
        if (!isFinishMove && isSelect && point != null) {
            if (checkCrossPoint(point)) { //交叉点
                isMoveButNotPoint = true;
            } else {//非交叉点(新的点)
                point.status = Point.STATE_PRESSED;
                selectPointList.add(point);
            }
        }
        //绘制结束
        if (isFinishMove && onGraphicLockListener != null) {
            //绘制不成立
            if (selectPointList.size() == 1) {
                resetPoint();
            } else if (selectPointList.size() < MIN_POINT && selectPointList.size() > 0) { //绘制错误,点不够
                onGraphicLockListener.LockShort();
                errorPoint();
            } else if (selectPointList.size() >= MIN_POINT) {//绘制成功
                StringBuilder strPassword = new StringBuilder();
                for (Point pwdPoint : selectPointList) {
                    strPassword.append(pwdPoint.index);
                }
                if (TextUtils.isEmpty(mPassword)) {
                    onGraphicLockListener.LockSuccess(0, strPassword.toString());
                    correctPoint();
                } else {
                    if (mPassword.equals(strPassword.toString())) {
                        onGraphicLockListener.LockSuccess(1, strPassword.toString());
                        correctPoint();
                    } else {
                        onGraphicLockListener.LockFailure();
                        errorPoint();
                    }
                }

            }
        }
        //刷新view,会调用onDraw方法
        postInvalidate();
        return true;
    }

    /**
     * 检查交叉点
     *
     * @param point 点
     * @return 是否交叉
     */
    private boolean checkCrossPoint(Point point) {
        return selectPointList.contains(point);
    }

    /**
     * 设置绘制不成立
     */
    public void resetPoint() {
        //将点的状态还原
        for (Point point : selectPointList) {
            point.status = Point.STATE_NORMAL;
        }
        selectPointList.clear();
    }

    /**
     * 设置绘制错误,将点的状态还原
     */
    public void errorPoint() {
        for (Point point : selectPointList) {
            point.status = Point.STATE_ERROR;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0x001);
            }
        }).start();
    }

    /**
     * 设置绘制成功,将点的状态还原
     */
    private void correctPoint() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0x001);
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x001) {
                for (Point point : selectPointList) {
                    point.status = Point.STATE_NORMAL;
                }
                selectPointList.clear();
                postInvalidate();
            }
        }
    };

    private Point checkSelectPoint() {
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                Point point = points[i][j];
                if (LockUtil.isCoincide(point.x, point.y, bitmapR, moveX, moveY)) {
                    return point;
                }
            }
        }
        return null;
    }

    /**
     * 将点绘制到画布上
     *
     * @param canvas 画布
     */
    private void pointToCanvas(Canvas canvas) {
        //遍历点的集合
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                Point point = points[i][j];
                if (points[i][j].status == Point.STATE_PRESSED) {
                    canvas.drawBitmap(bpPointPressed, point.x - bitmapR, point.y - bitmapR, mPaint);
                } else if (points[i][j].status == Point.STATE_ERROR) {
                    canvas.drawBitmap(bpPointError, point.x - bitmapR, point.y - bitmapR, mPaint);
                } else {
                    canvas.drawBitmap(bpPointNormal, point.x - bitmapR, point.y - bitmapR, mPaint);
                }
            }
        }
    }

    /**
     * 将线绘制到画布上
     *
     * @param canvas     画布
     * @param startPoint 开始的点
     * @param endPoint   结束的点
     */
    private void lineToCanvas(Canvas canvas, Point startPoint, Point endPoint) {
        float lineLength = (float) LockUtil.twoPointDistance(startPoint, endPoint);
        float degree = LockUtil.getDegrees(startPoint, endPoint);
        canvas.rotate(degree, startPoint.x, startPoint.y);  //旋转
        if (startPoint.status == Point.STATE_PRESSED) {  //按下的状态
            //设置线的缩放比例,在这里线是往一个方向缩放的,即x轴,我们只需要设置x轴的缩放比例即可,y轴默认为1
            matrix.setScale(lineLength / bpLinePressed.getWidth(), 1);
            matrix.postTranslate(startPoint.x - bpLinePressed.getWidth() / 2, startPoint.y - bpLinePressed.getHeight() / 2);
            canvas.drawBitmap(bpLinePressed, matrix, mPaint);
        } else if (startPoint.status == Point.STATE_ERROR) {//错误的状态
            matrix.setScale(lineLength / bpLineError.getWidth(), 1);
            matrix.postTranslate(startPoint.x - bpLineError.getWidth() / 2, startPoint.y - bpLineError.getHeight() / 2);
            canvas.drawBitmap(bpLineError, matrix, mPaint);
        }
        canvas.rotate(-degree, startPoint.x, startPoint.y);  //把旋转的角度转回来
    }

    /**
     * 图案监听器
     */
    public interface OnGraphicLockListener {

        void LockSuccess(int what, String password);//what=0设置密码成功回调；what=1解锁成功回调

        void LockFailure();//解锁失败的回调

        void LockShort();//密码太短的回调
    }
}
