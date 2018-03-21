package com.linzh.android.newfriendvoice.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View;

import com.linzh.android.newfriendvoice.R;

/**
 * Created by linzh on 2018/3/22.
 */

public class WriteBoardView extends View {

    private int mBoardBackground;//画板颜色
    private int mPaintColor;//画笔颜色
    private int mPaintWidth;//画笔宽度
    private Path mPath;//记录用户绘制的Path
    private Paint mPaint;//画笔

    private Canvas mCanvas;//内存中创建的Canvas
    private Bitmap mBitmap;//缓存绘制的内容

    private int mLastX;
    private int mLastY;

    public WriteBoardView(Context context) {
        super(context, null);
    }

    public WriteBoardView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context, attrs);
    }

    public WriteBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        //初始化Bitmap, Canvas
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        //canvas.drawPath(mPath, mPaint);
        //在onTouch中return true表示要处理当前事件。并且在每一次操作调用invalidate来绘制界面，
        // 我们的onDraw 方法只需要简单的调用drawPath就可以了
        drawPath();
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int)event.getX();
        int touchY = (int)event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mBitmap == null) {
                    mBitmap = Bitmap.createBitmap(mCanvas.getWidth(), mCanvas.getHeight(), Bitmap.Config.ARGB_8888);
                    mCanvas = new Canvas(mBitmap);
                    mCanvas.drawColor(Color.WHITE);
                }
                mLastX = touchX;
                mLastY = touchY;
                mPath.moveTo(touchX, touchY);//重新设置即将出现的线的地点
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(touchX - mLastX);
                int dy = Math.abs(touchY - mLastY);
                if (dx > 3 || dy > 3)
                    mPath.lineTo(touchX, touchY);//连线
                mLastX = touchX;
                mLastY = touchY;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();//通知系统重绘

        return true;//要处理当前事件
    }

    /**
     * 初始化绘制
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WriteBoardView);
        mBoardBackground = typedArray.getColor(R.styleable.WriteBoardView_boardBackground, Color.WHITE);
        mPaintColor =  typedArray.getColor(R.styleable.WriteBoardView_paintColor, Color.BLUE);
        mPaintWidth = typedArray.getDimensionPixelSize(R.styleable.WriteBoardView_paintWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        typedArray.recycle();

        mPaint = new Paint();
        mPath = new Path();
        setBackgroundColor(mBoardBackground);
        mPaint.setColor(mPaintColor);
        mPaint.setStrokeWidth(mPaintWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    /**
     * 清除View上的图案
     */
    public void clearDraw() {
//        if (mBitmap != null) {
//            mBitmap = Bitmap.createBitmap(mCanvas.getWidth(), mCanvas.getHeight(), Bitmap.Config.ARGB_8888);
//            mCanvas = new Canvas(mBitmap);
//            mCanvas.drawColor(Color.WHITE);
//        }
        if (mCanvas != null) {
            mPath.reset();
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            invalidate();
        }
    }

    /**
     * 绘制线条
     */
    private void drawPath() {
        mCanvas.drawPath(mPath, mPaint);
    }
}
