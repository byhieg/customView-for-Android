package com.example.byhieg.circleloadingview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by byhieg on 16-7-20.
 */
public class CircleLoadingView extends View {
    private int wlength;
    private int hlength;

    private float circleStrokewidth;
    private int circleColor;

    private int i = 0;
    private Paint paint;
    public CircleLoadingView(Context context) {
        super(context);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs){
        paint = new Paint();
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CircleLoadingView);
        circleStrokewidth = ta.getFloat(R.styleable.CircleLoadingView_circleStrokewidth, 0);
        circleColor = ta.getColor(R.styleable.CircleLoadingView_circleColor, 0);
        paint.setColor(circleColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(circleStrokewidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int result = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0,height = 0;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }else{
            result = 300;
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(result,widthSize);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }else {
            result = 300;
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(result, heightSize);
            }
        }

        wlength = width;
        hlength = height;
        setMeasuredDimension(width,height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float wdistance = (float)(wlength * 0.3);
        float hdistance = (float)(hlength * 0.3);

        RectF rectF = new RectF(
                wlength / 2 - wdistance,
                hlength / 2 - hdistance,
                wlength / 2 + wdistance,
                hlength / 2 + hdistance);
        canvas.drawArc(
                rectF,
                270 + 5 * i,
                (float)(66 * 3.6),
                false,
                paint
        );
        i++;
        postInvalidate();
    }

    public void setViewVisable(boolean choose) {
        if (choose) {
            this.setVisibility(View.VISIBLE);
        }else{
            this.setVisibility(View.GONE);
        }
    }
}
