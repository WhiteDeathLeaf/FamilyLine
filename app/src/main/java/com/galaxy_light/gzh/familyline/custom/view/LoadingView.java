package com.galaxy_light.gzh.familyline.custom.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.galaxy_light.gzh.familyline.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 加载中视图
 * Created by gzh on 2017-9-18.
 */

public class LoadingView extends View {
    private final int DEFAULT_DURATION = 6;//默认时间间隔
    private final int DEFAULT_EXTERNAL_RADIUS = dp2px(48);//默认外圆半径
    private final int DEFAULT_INTERNAL_RADIUS = dp2px(8);//默认内圆半径
    private final int DEFAULT_RADIAN = 45;//小圆间夹角

    private int mWidth;
    private int mHeight;
    private Subscription mTimer;//定时器
    private Paint mPaint;
    private Path mPath = new Path();

    private int mColors[];//内圆颜色
    private int mAngle = 0;//外圆角度
    private int mCyclic = 0;//圈数
    private float mGetBiggerCircleRadius;//变大动画圆半径
    private float mGetSmallerCircleRadius;//移动圆半径

    private List<PointF> mPoints;//外圆
    private List<ValueAnimator> mAnimators;//属性动画集
    private float x0, y0;//外圆圆点
    private int mRadian = DEFAULT_RADIAN;//点间的弧度

    //时间间隔
    private int mDuration;
    private final static int MAX_DURATION = 120;
    private final static int MIN_DURATION = 1;

    //内圆半径
    private float mInternalR;
    private final int MAX_INTERNAL_R = dp2px(18);
    private final int MIN_INTERNAL_R = dp2px(2);

    //外圆半径
    private float mExternalR;
    private final int MAX_EXTERNAL_R = dp2px(150);
    private final int MIN_EXTERNAL_R = dp2px(25);

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mAnimators = new ArrayList<>();
        mPoints = new ArrayList<>();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        TypedArray typeArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.BezierLoadingView);
        mDuration = typeArray.getInt(R.styleable.BezierLoadingView_lv_duration, DEFAULT_DURATION);
        mInternalR = typeArray.getDimension(R.styleable.BezierLoadingView_lv_internal_radius, DEFAULT_INTERNAL_RADIUS);
        mExternalR = typeArray.getDimension(R.styleable.BezierLoadingView_lv_external_radius, DEFAULT_EXTERNAL_RADIUS);
        int defaultColor = 999999;
        int startColor = typeArray.getColor(R.styleable.BezierLoadingView_lv_start_color, defaultColor);
        int endColor = typeArray.getColor(R.styleable.BezierLoadingView_lv_end_color, defaultColor);
        List<Integer> colorList = new ArrayList<>();
        if (startColor != defaultColor) {
            colorList.add(startColor);
        }
        if (endColor != defaultColor) {
            colorList.add(endColor);
        }
        //needs >= 2 number of colors
        if (colorList.size() == 1) {
            colorList.add(colorList.get(0));
        }

        if (colorList.size() == 0) {
            mColors = new int[]{ContextCompat.getColor(getContext(), R.color.colorPrimaryDark), ContextCompat.getColor(getContext(), R.color.colorPrimary)};
        } else {
            mColors = new int[colorList.size()];
            for (int i = 0; i < colorList.size(); i++) {
                mColors[i] = colorList.get(i);
            }
        }
        typeArray.recycle();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        setShader();
        resetPoint();
    }

    /**
     * 设置渐变色
     */
    private void setShader() {
        Shader mLinearGradient = new LinearGradient(mWidth / 2 - mExternalR, mHeight / 2 - mExternalR, mWidth / 2 - mExternalR, mHeight / 2 + mExternalR, mColors, null,
                Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
    }

    /**
     * 启动定时器
     */
    public void start() {
        if (mTimer == null || mTimer.isUnsubscribed()) {
            mTimer = Observable.interval(mDuration, TimeUnit.MILLISECONDS)
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            dealTimerBusiness();
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {

                        }
                    });
        }
        this.setVisibility(VISIBLE);
    }

    /**
     * 停止
     */
    public void stop() {
        if (mTimer != null) {
            mTimer.unsubscribe();
        }
        this.setVisibility(GONE);
    }

    /**
     * 定时任务
     */
    private void dealTimerBusiness() {

        setOffset((mAngle % mRadian) / (float) mRadian);

        mAngle++;
        if (mAngle == 360) {
            mAngle = 0;
            mCyclic++;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        drawBezier(canvas);
    }

    /**
     * 绘制各点圆
     *
     * @param canvas canvas
     */
    private void drawCircle(Canvas canvas) {
        for (int i = 0; i < mPoints.size(); i++) {
            int index = mAngle / mRadian;
            if (isEvenCyclic()) {
                if (i == index) {
                    if (mAngle % mRadian == 0) {
                        canvas.drawCircle(getCircleX(mAngle), getCircleY(mAngle), getMaxInternalRadius(), mPaint);
                    } else if (mAngle % mRadian > 0) {
                        canvas.drawCircle(getCircleX(mAngle), getCircleY(mAngle), mGetSmallerCircleRadius < mInternalR ? mInternalR : mGetSmallerCircleRadius, mPaint);
                    }
                } else if (i == index + 1) {
                    if (mAngle % mRadian == 0) {
                        canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mInternalR, mPaint);
                    } else {
                        canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mGetBiggerCircleRadius < mInternalR ? mInternalR : mGetBiggerCircleRadius, mPaint);
                    }
                } else if (i > index + 1) {
                    canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mInternalR, mPaint);
                }
            } else {
                if (i < index) {
                    canvas.drawCircle(mPoints.get(i + 1).x, mPoints.get(i + 1).y, mInternalR, mPaint);
                } else if (i == index) {
                    if (mAngle % mRadian == 0) {
                        canvas.drawCircle(getCircleX(mAngle), getCircleY(mAngle), getMaxInternalRadius(), mPaint);
                    } else {
                        canvas.drawCircle(getCircleX(mAngle), getCircleY(mAngle), mGetSmallerCircleRadius < mInternalR ? mInternalR : mGetSmallerCircleRadius, mPaint);
                    }
                } else if (i == index + 1) {
                    if (mAngle % mRadian == 0) {
                        canvas.drawCircle(getCircleX(mAngle), getCircleY(mAngle), getMinInternalRadius(), mPaint);
                    } else if (mAngle % mRadian > 0) {
                        canvas.drawCircle(getCircleX(mAngle), getCircleY(mAngle), mGetBiggerCircleRadius < mInternalR ? mInternalR : mGetBiggerCircleRadius, mPaint);
                    }
                }
            }
        }
    }

    /**
     * 绘制贝赛尔曲线
     *
     * @param canvas canvas
     */
    private void drawBezier(Canvas canvas) {

        mPath.reset();

        int circleIndex = mAngle / mRadian;

        float rightX = getCircleX(mAngle);
        float rightY = getCircleY(mAngle);

        float leftX, leftY;
        if (isEvenCyclic()) {
            int index;
            index = circleIndex + 1;
            leftX = mPoints.get(index >= mPoints.size() ? mPoints.size() - 1 : index).x;
            leftY = mPoints.get(index >= mPoints.size() ? mPoints.size() - 1 : index).y;
        } else {
            leftX = mPoints.get(circleIndex < 0 ? 0 : circleIndex).x;
            leftY = mPoints.get(circleIndex < 0 ? 0 : circleIndex).y;
        }

        double theta = getTheta(new PointF(leftX, leftY), new PointF(rightX, rightY));
        float sinTheta = (float) Math.sin(theta);
        float cosTheta = (float) Math.cos(theta);

        PointF pointF1 = new PointF(leftX - mInternalR * sinTheta, leftY + mInternalR * cosTheta);
        PointF pointF2 = new PointF(rightX - mInternalR * sinTheta, rightY + mInternalR * cosTheta);
        PointF pointF3 = new PointF(rightX + mInternalR * sinTheta, rightY - mInternalR * cosTheta);
        PointF pointF4 = new PointF(leftX + mInternalR * sinTheta, leftY - mInternalR * cosTheta);

        if (isEvenCyclic()) {
            if (mAngle % mRadian < mRadian / 2) {

                mPath.moveTo(pointF3.x, pointF3.y);
                mPath.quadTo(rightX + (leftX - rightX) / (mRadian / 2) * (mAngle % mRadian > (mRadian / 2) ? (mRadian / 2) : mAngle % mRadian), rightY + (leftY - rightY) / (mRadian / 2) * (mAngle % mRadian > (mRadian / 2) ? (mRadian / 2) : mAngle % mRadian), pointF2.x, pointF2.y);
                mPath.lineTo(pointF3.x, pointF3.y);

                mPath.moveTo(pointF4.x, pointF4.y);
                mPath.quadTo(leftX + (rightX - leftX) / (mRadian / 2) * (mAngle % mRadian > (mRadian / 2) ? (mRadian / 2) : mAngle % mRadian), leftY + (rightY - leftY) / (mRadian / 2) * (mAngle % mRadian > (mRadian / 2) ? (mRadian / 2) : mAngle % mRadian), pointF1.x, pointF1.y);
                mPath.lineTo(pointF4.x, pointF4.y);

                mPath.close();
                canvas.drawPath(mPath, mPaint);
                return;
            }
        } else {
            if (circleIndex > 0 && mAngle % mRadian > mRadian / 2) {

                mPath.moveTo(pointF3.x, pointF3.y);
                mPath.quadTo(rightX + (leftX - rightX) / (mRadian / 2) * (mRadian - mAngle % mRadian > (mRadian / 2) ? (mRadian / 2) : (mRadian - mAngle % mRadian)), rightY + (leftY - rightY) / (mRadian / 2) * (mRadian - mAngle % mRadian > (mRadian / 2) ? (mRadian / 2) : (mRadian - mAngle % mRadian)), pointF2.x, pointF2.y);
                mPath.lineTo(pointF3.x, pointF3.y);

                mPath.moveTo(pointF4.x, pointF4.y);
                mPath.quadTo(leftX + (rightX - leftX) / (mRadian / 2) * (mRadian - mAngle % mRadian > (mRadian / 2) ? (mRadian / 2) : (mRadian - mAngle % mRadian)), leftY + (rightY - leftY) / (mRadian / 2) * (mRadian - mAngle % mRadian > (mRadian / 2) ? (mRadian / 2) : (mRadian - mAngle % mRadian)), pointF1.x, pointF1.y);
                mPath.lineTo(pointF4.x, pointF4.y);

                mPath.close();
                canvas.drawPath(mPath, mPaint);
                return;
            }
        }

        if (circleIndex == 0 && !isEvenCyclic()) return;

        mPath.moveTo(pointF1.x, pointF1.y);
        mPath.quadTo((leftX + rightX) / 2, (leftY + rightY) / 2, pointF2.x, pointF2.y);
        mPath.lineTo(pointF3.x, pointF3.y);
        mPath.quadTo((leftX + rightX) / 2, (leftY + rightY) / 2, pointF4.x, pointF4.y);
        mPath.lineTo(pointF1.x, pointF1.y);

        mPath.close();

        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 创建属性动画
     */
    private void createAnimator() {

        if (mPoints.isEmpty()) {
            return;
        }
        mAnimators.clear();

        ValueAnimator circleGetSmallerAnimator = ValueAnimator.ofFloat(getMaxInternalRadius(), getMinInternalRadius());
        circleGetSmallerAnimator.setDuration(5000L);
        circleGetSmallerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mGetSmallerCircleRadius = (float) animation.getAnimatedValue();
            }
        });
        mAnimators.add(circleGetSmallerAnimator);

        ValueAnimator circleGetBiggerAnimator = ValueAnimator.ofFloat(getMinInternalRadius(), getMaxInternalRadius());
        circleGetBiggerAnimator.setDuration(5000L);
        circleGetBiggerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mGetBiggerCircleRadius = (float) animation.getAnimatedValue();
            }
        });
        mAnimators.add(circleGetBiggerAnimator);

    }

    private void seekAnimator(float offset) {
        for (ValueAnimator animator : mAnimators) {
            animator.setCurrentPlayTime((long) (5000.0F * offset));
        }
    }

    /**
     * 设置偏移量
     *
     * @param offSet 偏移量
     */
    public void setOffset(float offSet) {
        createAnimator();
        seekAnimator(offSet);
        postInvalidate();
    }

    /**
     * 重置圆点
     */
    private void resetPoint() {

        x0 = mWidth / 2;
        y0 = mHeight / 2;

        createPoints();

        if (!mPoints.isEmpty()) {
            mGetBiggerCircleRadius = getMaxInternalRadius();
            mGetSmallerCircleRadius = getMinInternalRadius();
            postInvalidate();
        }
    }

    /**
     * 创建圆点
     * <p/>
     * 圆点坐标：(x0,y0)
     * 半径：r
     * 角度：a0
     * <p/>
     * 则圆上任一点为：（x1,y1）
     * x1 = x0 + r * cos(ao * 3.14 /180 )
     * y1 = y0 + r * sin(ao * 3.14 /180 )
     */
    private void createPoints() {
        mPoints.clear();
        for (int i = 0; i <= 360; i++) {
            if (i % mRadian == 0) {
                float x1 = getCircleX(i);
                float y1 = getCircleY(i);
                mPoints.add(new PointF(x1, y1));
            }
        }
    }

    /**
     * 是否是偶数圈
     *
     * @return 是否是偶数圈
     */
    private boolean isEvenCyclic() {
        return mCyclic % 2 == 0;
    }

    /**
     * 获得以(x0,y0)为圆心的圆y坐标
     *
     * @param angle 角度
     * @return y坐标
     */
    private float getCircleY(int angle) {
        return y0 + mExternalR * (float) Math.sin(angle * 3.14 / 180);
    }

    /**
     * 获得以(x0,y0)为圆心的圆x坐标
     *
     * @param angle 角度
     * @return x坐标
     */
    private float getCircleX(int angle) {
        return x0 + mExternalR * (float) Math.cos(angle * 3.14 / 180);
    }

    /**
     * 获取theta值
     *
     * @param pointCenterLeft  左圆心
     * @param pointCenterRight 右圆心
     * @return 角度
     */
    private double getTheta(PointF pointCenterLeft, PointF pointCenterRight) {
        return Math.atan((pointCenterRight.y - pointCenterLeft.y) / (pointCenterRight.x - pointCenterLeft.x));
    }

    /**
     * 设置外圆半径
     *
     * @param progress progress
     */
    public void setExternalRadius(int progress) {
        int R = (int) ((progress / 100f) * MAX_EXTERNAL_R);
        mExternalR = R < MIN_EXTERNAL_R ? MIN_EXTERNAL_R : R;
        setShader();
        createPoints();
    }

    /**
     * 设置内圆半径
     *
     * @param progress progress
     */
    public void setInternalRadius(int progress) {
        int r = (int) ((progress / 100f) * MAX_INTERNAL_R);
        mInternalR = r < MIN_INTERNAL_R ? MIN_INTERNAL_R : r;
    }

    /**
     * 设置时间间隔
     *
     * @param progress progress
     */
    public void setDuration(int progress) {
        stop();
        int duration = (int) ((1 - progress / 100f) * MAX_DURATION);
        mDuration = duration < MIN_DURATION ? MIN_DURATION : duration;
        start();
    }

    /**
     * 最大内圆半径
     *
     * @return 最大内圆半径
     */
    private float getMaxInternalRadius() {
        return mInternalR / 10f * 14f;
    }

    /**
     * 最小内圆半径
     *
     * @return 最小内圆半径
     */
    private float getMinInternalRadius() {
        return mInternalR / 10f;
    }

    private int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
