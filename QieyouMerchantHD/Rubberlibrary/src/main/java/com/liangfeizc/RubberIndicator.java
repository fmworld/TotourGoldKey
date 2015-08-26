package com.liangfeizc;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangfeizc on 6/28/15.
 */
public class RubberIndicator extends RelativeLayout {
    private static final String TAG = "RubberIndicator";

    private static final int SMALL_CIRCLE_COLOR = 0xFFe5e5e5;
    private static final int LARGE_CIRCLE_COLOR = 0xFF00bb00;
    private static final int OUTER_CIRCLE_COLOR = 0xFF0000ee;

    private static final int SMALL_CIRCLE_RADIUS = 5;
    private static final int LARGE_CIRCLE_RADIUS = 7;
    private static final int OUTER_CIRCLE_RADIUS = 15;

    private static final int BEZIER_CURVE_ANCHOR_DISTANCE = 7;

    private static final int CIRCLE_TYPE_SMALL = 0x00;
    private static final int CIRCLE_TYPE_LARGE = 0x01;
    private static final int CIRCLE_TYPE_OUTER = 0x02;
    /**
     * colors
     */
    private int mSmallCircleColor;
    private int mLargeCircleColor;
    private int mOuterCircleColor;

    /**
     * coordinate values
     */
    private int mSmallCircleRadius;
    private int mLargeCircleRadius;
    private int mOuterCircleRadius;

    /**
     * views
     */
    private LinearLayout mContainer;
    private CircleView mLargeCircle;
    private CircleView mSmallCircle;
    private CircleView mOuterCircle;
    private List<CircleView> mCircleViews;

    /**
     * animations
     */
    private AnimatorSet mAnim;
    private PropertyValuesHolder mPvhScaleX;
    private PropertyValuesHolder mPvhScaleY;
    private PropertyValuesHolder mPvhScale;
    private PropertyValuesHolder mPvhRotation;

    /**
     * Movement Path
     */
    private Path mSmallCirclePath;

    /**
     * Indicator movement listener
     */
    private OnMoveListener mOnMoveListener;

    /**
     * Helper values
     */
    private int mFocusPosition = -1, mNextFocusPosition=-1;
    private int mBezierCurveAnchorDistance = dp2px(BEZIER_CURVE_ANCHOR_DISTANCE);

    public RubberIndicator(Context context) {
        super(context);
        init();
    }

    public RubberIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        /** Initialize views */
        View rootView = inflate(getContext(), R.layout.rubber_indicator, this);
        mContainer = (LinearLayout) rootView.findViewById(R.id.container);
        mOuterCircle = (CircleView) rootView.findViewById(R.id.outer_circle);
        mLargeCircle = createCircleView(CIRCLE_TYPE_LARGE);

        /** values */
        mSmallCircleColor = SMALL_CIRCLE_COLOR;
        mLargeCircleColor = LARGE_CIRCLE_COLOR;
        mOuterCircleColor = OUTER_CIRCLE_COLOR;

        mSmallCircleRadius = dp2px(SMALL_CIRCLE_RADIUS);
        mLargeCircleRadius = dp2px(LARGE_CIRCLE_RADIUS);
        mOuterCircleRadius = dp2px(OUTER_CIRCLE_RADIUS);

        /** animators */
        mPvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1, 0.8f, 1);
        mPvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1, 0.8f, 1);
        mPvhScale = PropertyValuesHolder.ofFloat("scaleY", 1, 0.5f, 1);
        mPvhRotation = PropertyValuesHolder.ofFloat("rotation", 0);

        mSmallCirclePath = new Path();

        /** circle view list */
        mCircleViews = new ArrayList<>();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mOuterCircle.setCenter(mLargeCircle.getCenter());
    }

    public void setCount(int count) {
        if (mFocusPosition == -1) {
            mFocusPosition =mNextFocusPosition= 0;
        }
        setCount(count, mFocusPosition);
    }

    /**
     * This method must be called before {@link #setCount(int)}, otherwise the focus position will
     * be set to the default value - zero.
     * @param pos the focus position
     */
    public void setFocusPosition(final int pos) {
        if (mAnim != null && mAnim.isRunning()) return;

        mNextFocusPosition = pos;
        move((mNextFocusPosition -mFocusPosition) >0);
    }

    public void setCount(int count, int focusPos) {
        if (count < 2) {
            throw new IllegalArgumentException("count must be greater than 2");
        }

        if (focusPos >= count) {
            throw new IllegalArgumentException("focus position must be less than count");
        }

        int i = 0;
        for (; i < focusPos; i++) {
            addSmallCircle();
        }
        addLargeCircle();
        for (i = focusPos + 1; i < count; i++) {
            addSmallCircle();
        }

        mFocusPosition =mNextFocusPosition= focusPos;
    }

    public int getFocusPosition() {
        return mFocusPosition;
    }

    public void moveToLeft() {
        if (mAnim != null && mAnim.isRunning()) return;
        mNextFocusPosition--;
        move(false);
    }

    public void moveToRight() {
        if (mAnim != null && mAnim.isRunning()) return;
        mNextFocusPosition++;
        move(true);
    }

    public void setOnMoveListener(final OnMoveListener moveListener) {
        mOnMoveListener = moveListener;
    }

    private void addSmallCircle() {
        CircleView smallCircle = createCircleView(CIRCLE_TYPE_SMALL);
        mCircleViews.add(smallCircle);
        mContainer.addView(smallCircle);
    }

    private void addLargeCircle() {
        mLargeCircle = createCircleView(CIRCLE_TYPE_LARGE);
        mCircleViews.add(mLargeCircle);
        mContainer.addView(mLargeCircle);
    }

    private CircleView createCircleView(int type) {
        CircleView circleView = new CircleView(getContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.gravity = Gravity.CENTER_VERTICAL;

        switch (type) {
            case CIRCLE_TYPE_SMALL:
                params.height = params.width = mSmallCircleRadius << 1;
                circleView.setColor(mSmallCircleColor);
                break;
            case CIRCLE_TYPE_LARGE:
                params.height = params.width = mLargeCircleRadius << 1;
                circleView.setColor(mLargeCircleColor);
                break;
            case CIRCLE_TYPE_OUTER:
                params.height = params.width = mOuterCircleRadius << 1;
                circleView.setColor(mOuterCircleColor);
                break;
        }

        circleView.setLayoutParams(params);

        return circleView;
    }

    private int getNextPosition(boolean toRight) {
        int nextPos = mFocusPosition + (toRight ? 1 : -1);
        if (nextPos < 0 || nextPos >= mCircleViews.size()) return -1;
        return nextPos;
    }

    private void swapCircles(int currentPos, int nextPos) {
        CircleView circleView = mCircleViews.get(currentPos);
        mCircleViews.set(currentPos, mCircleViews.get(nextPos));
        mCircleViews.set(nextPos, circleView);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void move(final boolean toRight) {
        final int nextPos = getNextPosition(toRight);
        if (nextPos == -1) return;

        mSmallCircle = mCircleViews.get(nextPos);

        // Calculate the new x coordinate for circles.
        float smallCircleX = toRight ? mLargeCircle.getX()
                : mLargeCircle.getX() + mLargeCircle.getWidth() - mSmallCircle.getWidth();
        float largeCircleX = toRight ?
                mSmallCircle.getX() + mSmallCircle.getWidth() - mLargeCircle.getWidth() : mSmallCircle.getX();
        float outerCircleX = mOuterCircle.getX() + largeCircleX - mLargeCircle.getX();

        // animations for large circle and outer circle.
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", mLargeCircle.getX(), largeCircleX);
        ObjectAnimator largeCircleAnim = ObjectAnimator.ofPropertyValuesHolder(
                mLargeCircle, pvhX, mPvhScaleX, mPvhScaleY);

        pvhX = PropertyValuesHolder.ofFloat("x", mOuterCircle.getX(), outerCircleX);
        ObjectAnimator outerCircleAnim = ObjectAnimator.ofPropertyValuesHolder(
                mOuterCircle, pvhX, mPvhScaleX, mPvhScaleY);

        // Animations for small circle
        PointF smallCircleCenter = mSmallCircle.getCenter();
        PointF smallCircleEndCenter = new PointF(
                smallCircleCenter.x - (mSmallCircle.getX() - smallCircleX), smallCircleCenter.y);

        // Create motion anim for small circle.
        mSmallCirclePath.reset();
        mSmallCirclePath.moveTo(smallCircleCenter.x, smallCircleCenter.y);
        mSmallCirclePath.quadTo(smallCircleCenter.x, smallCircleCenter.y,
                (smallCircleCenter.x + smallCircleEndCenter.x) / 2,
                (smallCircleCenter.y + smallCircleEndCenter.y) / 2 + mBezierCurveAnchorDistance);
        mSmallCirclePath.lineTo(smallCircleEndCenter.x, smallCircleEndCenter.y);

        ValueAnimator smallCircleAnim;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            smallCircleAnim = ObjectAnimator.ofObject(mSmallCircle, "center", null, mSmallCirclePath);

        } else {
            final PathMeasure pathMeasure = new PathMeasure(mSmallCirclePath, false);
            final float[] point = new float[2];
            smallCircleAnim = ValueAnimator.ofFloat(0.0f, 1.0f);
            smallCircleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    pathMeasure.getPosTan(
                            pathMeasure.getLength() * animation.getAnimatedFraction(), point, null);
                    mSmallCircle.setCenter(new PointF(point[0], point[1]));
                }
            });
        }

        mPvhRotation.setFloatValues(0, toRight ? -30f : 30f, 0, toRight ? 30f : -30f, 0);
        ObjectAnimator otherAnim = ObjectAnimator.ofPropertyValuesHolder(mSmallCircle, mPvhRotation, mPvhScale);

        mAnim = new AnimatorSet();
        mAnim.play(smallCircleAnim)
                .with(otherAnim).with(largeCircleAnim).with(outerCircleAnim);
        mAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnim.setDuration(500);
        mAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) {
                swapCircles(mFocusPosition, nextPos);
                mFocusPosition = nextPos;
                if(mFocusPosition !=mNextFocusPosition){
                    move(toRight);
                }else
                if (mOnMoveListener != null) {
                    if (toRight) {
                        mOnMoveListener.onMovedToRight();
                    } else {
                        mOnMoveListener.onMovedToLeft();
                    }
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });

        mAnim.start();
    }

    private int dp2px(int dpValue) {
        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
    }

    public interface OnMoveListener {
        void onMovedToLeft();
        void onMovedToRight();
    }
}
