package com.galaxy_light.gzh.familyline.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.support.v7.widget.AppCompatTextView;

import com.galaxy_light.gzh.familyline.R;

/**
 * 字母索引栏
 * Created by gzh on 2017-9-21.
 */
public class IndexBar extends AppCompatTextView {
    private String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", "#"};
    private Paint textPaint;//文字画笔
    private Paint bigTextPaint;//放大文字画笔
    private Paint scaleTextPaint;//缩放文字画笔

    private Canvas canvas;
    private int itemHeight;
    private int width;

    private float singleTextHeight;//普通情况下字体大小
    private float scaleWidth;//缩放离原始的宽度
    private float eventY = 0;//滑动的Y
    private int scaleSize = 1;//缩放的倍数
    private int scaleItemCount = 6;//缩放的item数，即开口大小

    private IndexSelectCallBack callBack;//item选择监听器

    public IndexBar(Context context) {
        this(context, null);
    }

    public IndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.IndexBar);
            scaleSize = ta.getInteger(R.styleable.IndexBar_scaleSize, 1);
            scaleItemCount = ta.getInteger(R.styleable.IndexBar_scaleItemCount, 6);
            scaleWidth = ta.getDimensionPixelSize(R.styleable.IndexBar_scaleWidth, dp(100));
            ta.recycle();
        }
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(getCurrentTextColor());
        textPaint.setTextSize(getTextSize());
        textPaint.setTextAlign(Paint.Align.CENTER);
        bigTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bigTextPaint.setColor(getCurrentTextColor());
        bigTextPaint.setTextSize(getTextSize() * (scaleSize + 3));
        bigTextPaint.setTextAlign(Paint.Align.CENTER);
        scaleTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scaleTextPaint.setColor(getCurrentTextColor());
        scaleTextPaint.setTextSize(getTextSize() * (scaleSize + 1));
        scaleTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * 设置数据源
     *
     * @param data 数据源
     */
    public void setDataResource(String[] data) {
        letters = data;
        invalidate();
    }

    /**
     * 设置item选择监听器
     *
     * @param callBack /item选择监听器
     */
    public void setOnStrSelectCallBack(IndexSelectCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 设置字体缩放比例
     *
     * @param scale 缩放比例
     */
    public void setScaleSize(int scale) {
        scaleSize = scale;
        invalidate();
    }

    /**
     * 设置缩放的item体数，即开口大小
     *
     * @param scaleItemCount 缩放的item体数
     */
    public void setScaleItemCount(int scaleItemCount) {
        this.scaleItemCount = scaleItemCount;
        invalidate();
    }

    private int dp(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px * scale + 0.5f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (event.getX() > (width - getPaddingRight() - singleTextHeight - 10)) {
                    eventY = event.getY();
                    invalidate();
                    return true;
                } else {
                    eventY = 0;
                    invalidate();
                    break;
                }
            case MotionEvent.ACTION_CANCEL:
                eventY = 0;
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                if (event.getX() > (width - getPaddingRight() - singleTextHeight - 10)) {
                    eventY = 0;
                    invalidate();
                    return true;
                } else
                    break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        DrawView(eventY);
    }

    private void DrawView(float y) {
        int currentSelectIndex = -1;
        if (y != 0) {
            for (int i = 0; i < letters.length; i++) {
                float currentItemY = itemHeight * i;
                float nextItemY = itemHeight * (i + 1);
                if (y >= currentItemY && y < nextItemY) {
                    currentSelectIndex = i;
                    if (callBack != null) {
                        callBack.onSelectStr(currentSelectIndex, letters[i]);
                    }
                    //画大的文字
                    Paint.FontMetrics fontMetrics = bigTextPaint.getFontMetrics();
                    float bigTextSize = fontMetrics.descent - fontMetrics.ascent;
                    canvas.drawText(letters[i], width - getPaddingRight() - scaleWidth - bigTextSize, singleTextHeight + itemHeight * i, bigTextPaint);
                }
            }
        }
        drawLetters(y, currentSelectIndex);
    }

    private void drawLetters(float y, int index) {
        //第一次进来没有缩放情况，默认画原图
        if (index == -1) {
            width = getMeasuredWidth();
            int h = getMeasuredHeight();
            itemHeight = h / letters.length;
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            singleTextHeight = fontMetrics.descent - fontMetrics.ascent;
            for (int i = 0; i < letters.length; i++) {
                canvas.drawText(letters[i], width - getPaddingRight(), singleTextHeight + itemHeight * i, textPaint);
            }
            //触摸的时候画缩放图
        } else {
            //遍历所有文字
            for (int i = 0; i < letters.length; i++) {
                //要画的文字的起始Y坐标
                float currentItemToDrawY = singleTextHeight + itemHeight * i;
                float centerItemToDrawY;
                if (index < i)
                    centerItemToDrawY = singleTextHeight + itemHeight * (index + scaleItemCount);
                else
                    centerItemToDrawY = singleTextHeight + itemHeight * (index - scaleItemCount);
                float delta = 1 - Math.abs((y - currentItemToDrawY) / (centerItemToDrawY - currentItemToDrawY));
                float maxRightX = width - getPaddingRight();
                //如果大于0，表明在y坐标上方
                scaleTextPaint.setTextSize(getTextSize() + getTextSize() * delta);
                float drawX = maxRightX - scaleWidth * delta;
                //超出边界直接花在边界上
                if (drawX > maxRightX)
                    canvas.drawText(letters[i], maxRightX, singleTextHeight + itemHeight * i, textPaint);
                else
                    canvas.drawText(letters[i], drawX, singleTextHeight + itemHeight * i, scaleTextPaint);
            }
        }
    }

    /**
     * //item选择监听器
     */
    public interface IndexSelectCallBack {
        /**
         * 选择文字
         *
         * @param index     选中文字的下标
         * @param selectStr 选中的文字
         */
        void onSelectStr(int index, String selectStr);
    }

}
