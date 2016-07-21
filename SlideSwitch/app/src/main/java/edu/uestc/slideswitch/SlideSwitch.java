package edu.uestc.slideswitch;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jun on 2016/4/29.
 */
public class SlideSwitch extends FrameLayout implements View.OnTouchListener {
    private Drawable mIcon;
    private ImageView mIconView;

    private String mText;
    private float mTextSize;
    private int mTextColor;
    private TextView mTextView;

    private int mBgColorLeft;
    private int mBgColorRight;
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float mAnimationSpeed = 2500;  //动画执行速度（像素/秒）

    private OnSlideCompleteListener mListener = null;


    public SlideSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.slide);
        mIcon = attributes.getDrawable(R.styleable.slide_barIcon);
        mText = attributes.getString(R.styleable.slide_text);
        mTextSize = attributes.getDimension(R.styleable.slide_textSize, 22);
        mTextColor = attributes.getColor(R.styleable.slide_textColor, Color.parseColor("#ff0000"));
        mBgColorLeft = attributes.getColor(R.styleable.slide_bgLeft, Color.parseColor("#ff0000"));
        mBgColorRight = attributes.getColor(R.styleable.slide_bgRight, Color.parseColor("#222244"));

        LayoutParams layoutParams;

        //初始化文本
        mTextView = new TextView(context);
        mTextView.setText(mText);
        mTextView.setTextColor(mTextColor);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        mTextView.setLayoutParams(layoutParams);
        addView(mTextView);

        //初始化拖拽图标
        mIconView = new ImageView(context);
        mIconView.setImageDrawable(mIcon);
        layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        mIconView.setLayoutParams(layoutParams);
        addView(mIconView);

        //要求绘制！
        setWillNotDraw(false);

        mIconView.setOnTouchListener(this);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //先计算各个边界
        float iconRadius = ((float) mIconView.getWidth()) / 2;
        float rectLeftStart = iconRadius + 1;
        float rectLeftEnd = mIconView.getX() + iconRadius;
        float rectRightStart = rectLeftEnd;
        float rectRightEnd = getWidth() - iconRadius - 1;

        mPaint.setStyle(Paint.Style.FILL);

        //绘制图标左边的背景
        mPaint.setColor(mBgColorLeft);
        canvas.drawCircle(iconRadius, iconRadius, iconRadius, mPaint);
        canvas.drawRect(rectLeftStart, 0, rectLeftEnd, getHeight(), mPaint);

        //绘制图标右边的背景
        mPaint.setColor(mBgColorRight);
        canvas.drawCircle(getWidth() - iconRadius, iconRadius, iconRadius, mPaint);
        canvas.drawRect(rectRightStart, 0, rectRightEnd, getHeight(), mPaint);
    }


    private float xStart;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xStart = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (isComplete()) {
                    moveIconView(mIconView.getX(), getWidth() - mIconView.getWidth());
                } else {
                    moveIconView(mIconView.getX(), 0);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float xPos = event.getX();
                float offset = xPos - xStart; //滑动偏移量

                float newLeft = mIconView.getX() + offset;
                float newRight = newLeft + mIconView.getWidth();

                //边界判断
                if (newLeft < 0) newLeft = 0;
                if (newRight > getWidth()) newLeft = getWidth() - mIconView.getWidth();

                mIconView.setX(newLeft);
                invalidate();
                break;
        }
        return true;
    }


    /**
     * 将移动图标从指定位置移动到指定位置（带动画）
     * @param start 移动的起始位置
     * @param end 移动的终止位置
     */
    private void moveIconView(final float start, final float end) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.setDuration(computeDuration(end - start));
        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mIconView.setX((Float)animation.getAnimatedValue());
                invalidate();
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isComplete()) {
                    callBack();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.start();
    }


    /**
     * 根据像素差计算动画持续时间
     * @param deltaPixel 像素差值
     * @return 动画应该持续的时间（毫秒）
     */
    private long computeDuration(float deltaPixel) {
        deltaPixel = Math.abs(deltaPixel);
        return (long) (1000 * deltaPixel / mAnimationSpeed);
    }


    /**
     * 判断是否滑动完成
     * @return
     */
    private boolean isComplete() {
        float right = mIconView.getX() + mIconView.getWidth();
        return getWidth() - right < mIconView.getWidth() / 2; //到右端的距离要小于半径
    }


    /**
     * 重置状态
     */
    public void reset() {
        mIconView.setX(0);
        invalidate();
    }

    private void callBack() {
        if (mListener != null) {
            mListener.onComplete();
        }
    }

    public void setOnSlideCompleteListener(OnSlideCompleteListener l) {
        this.mListener = l;
    }

    public interface OnSlideCompleteListener {
        void onComplete();
    }
}
