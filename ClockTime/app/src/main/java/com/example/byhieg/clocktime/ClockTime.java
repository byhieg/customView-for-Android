package com.example.byhieg.clocktime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by byhieg on 16-7-21.
 */
public class ClockTime extends View{
    private Paint circlePaint;
    private Paint textPaint;
    private Paint pointPaint;
    private Paint linePaint;
    private Paint lPaint;
    private Paint innerCirclePaint;
    private float wlength,hlength;
    private Canvas canvas;
    private float outd,inerd;
    private float endx,endy;

    public float getEndy() {
        return endy;
    }

    public void setEndy(float endy) {
        this.endy = endy;
    }

    public float getEndx() {
        return endx;
    }

    public void setEndx(float endx) {
        this.endx = endx;
    }

    public ClockTime(Context context) {
        super(context);
        initView();
    }

    public ClockTime(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ClockTime(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(5);
        circlePaint.setColor(Color.BLACK);

        textPaint = new Paint();
        textPaint.setStrokeWidth(3);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(30);

        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setStrokeWidth(20);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(Color.GREEN);

        linePaint = new Paint();
        linePaint.setStrokeWidth(10);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(Color.GREEN);

        lPaint = new Paint();
        lPaint.setAntiAlias(true);
        lPaint.setStrokeWidth(2);
        lPaint.setStyle(Paint.Style.FILL);
        lPaint.setColor(Color.BLACK);

        innerCirclePaint = new Paint();
        innerCirclePaint.setAntiAlias(true);
        innerCirclePaint.setStrokeWidth(5);
        innerCirclePaint.setColor(Color.WHITE);
        innerCirclePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wresult = 500 ,hresult = 500;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            wresult = widthSize;
        } else if (widthMeasureSpec == MeasureSpec.AT_MOST) {
            wresult = Math.min(wresult, widthSize);
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            hresult = heightSize;
        } else if (heightSize == MeasureSpec.AT_MOST) {
            hresult = Math.min(hresult, heightSize);
        }

        wlength = wresult;
        hlength = hresult;
        setMeasuredDimension(wresult,hresult);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        super.onDraw(canvas);
        outd = (float)((wlength >= hlength ? hlength : wlength) * 0.8);
        inerd = (float)(outd * 0.7);
        canvas.drawCircle(
                wlength / 2,
                hlength / 2,
                outd / 2 ,
                circlePaint
        );
//        canvas.drawCircle(
//                wlength / 2,
//                hlength / 2,
//                inerd / 2,
//                innerCirclePaint
//        );
        //画中心原点
        canvas.drawCircle(
                wlength / 2 ,
                hlength / 2 ,
                10,
                pointPaint);
        //画小时的数值
        for(int i = 0 ;i < 12 ; i++){
            canvas.save();
            canvas.rotate(
                    - 30 * i,
                    wlength / 2,
                    hlength / 2);
            canvas.drawText(
                    12 - i + "",
                    wlength / 2,
                    (float) (hlength / 2 - inerd * 0.6) ,
                    textPaint);
            canvas.restore();
        }
        //画分钟的竖线
        for(int i = 0 ;i < 60 ;i ++) {
            canvas.save();
            canvas.rotate(
                    -6 * i,
                    wlength / 2,
                    hlength / 2);
            if (i % 5 != 0) {
                canvas.drawLine(
                        wlength / 2,
                        (float) (hlength / 2 - inerd * 0.6),
                        wlength / 2,
                        (float)(hlength / 2 - inerd * 0.65),
                        lPaint
                );
            }
            canvas.restore();
        }
        //画时针
        canvas.drawLine(
                wlength / 2,
                hlength /2 ,
                wlength / 2,
                (float)(hlength / 2 - 0.2 * outd),
                linePaint);
        if (getEndx() == 0.0 && getEndy() == 0.0) {
            drawLine(
                    canvas,
                    false,
                    wlength / 2 + outd  / 2 - 100,
                    hlength / 2);
        }else{
            drawLine(
                    canvas,
                    false,
                    getEndx(),
                    getEndy());
        }

    }

    private void drawLine(Canvas canvas,boolean flag,float endx,float endy) {
        if (flag) {
            //画分针
            canvas.drawLine(
                    wlength / 2,
                    hlength / 2,
                    endx,
                    endy,
                    linePaint);
            postInvalidate();
        }else{
            canvas.drawLine(
                    wlength / 2,
                    hlength / 2,
                    endx,
                    endy,
                    linePaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getRawX();
                float y = event.getRawY();
                Log.e("x",x + " ");
                Log.e("y", y + " ");
                setEndx(x);
                setEndy(y);
                postInvalidate();
                break;
        }
        return true;
    }
}
