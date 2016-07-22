package com.example.byhieg.clocktime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by byhieg on 16-7-21.
 */
public class ClockTime extends View {
    private Paint circlePaint;
    private Paint textPaint;
    private Paint timePaint;
    private Paint pointPaint;
    private Paint linePaint;
    private Paint lPaint;
    private Paint innerCirclePaint;
    private float wlength, hlength;
    private Canvas canvas;
    private float outd, inerd;
    private float endx, endy;
    private double ratio;
    public static final double PI = Math.PI;

    private double distance;

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

    private void initView() {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setStrokeWidth(5);
        circlePaint.setColor(Color.GRAY);

        textPaint = new Paint();
        textPaint.setStrokeWidth(5);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(40);
        textPaint.setTypeface(Typeface.MONOSPACE);

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

        timePaint = new Paint();
        timePaint.setStrokeWidth(5);
        timePaint.setStyle(Paint.Style.STROKE);
        timePaint.setColor(Color.BLACK);
        timePaint.setTextAlign(Paint.Align.CENTER);
        timePaint.setTextSize(60);
        textPaint.setTypeface(Typeface.MONOSPACE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wresult = 800, hresult = 800;
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
        setMeasuredDimension(wresult, hresult);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        super.onDraw(canvas);
        outd = (float) ((wlength >= hlength ? hlength : wlength) * 0.8);
        inerd = (float) (outd * 0.7);
        canvas.drawCircle(
                wlength / 2,
                hlength / 2,
                outd / 2,
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
                wlength / 2,
                hlength / 2,
                10,
                pointPaint);
        //画小时的数值
        for (int i = 0; i < 12; i++) {
            canvas.save();
            canvas.rotate(
                    -30 * i,
                    wlength / 2,
                    hlength / 2);
            canvas.drawText(
                    12 - i + "",
                    wlength / 2,
                    (float) (hlength / 2 - inerd * 0.6),
                    textPaint);
            canvas.restore();
        }
        //画分钟的竖线
//        for (int i = 0; i < 60; i++) {
//            canvas.save();
//            canvas.rotate(
//                    -6 * i,
//                    wlength / 2,
//                    hlength / 2);
//            if (i % 5 != 0) {
//                canvas.drawLine(
//                        wlength / 2,
//                        (float) (hlength / 2 - inerd * 0.6),
//                        wlength / 2,
//                        (float) (hlength / 2 - inerd * 0.65),
//                        lPaint
//                );
//            }
//            canvas.restore();
//        }
//        //画时针
//        canvas.drawLine(
//                wlength / 2,
//                hlength / 2,
//                wlength / 2,
//                (float) (hlength / 2 - 0.2 * outd),
//                linePaint);

        if (getEndx() != 0.0 || getEndy() != 0.0) {
            drawLine(
                    canvas,
                    false,
                    getEndx(),
                    getEndy()
            );
            canvas.drawText(
                    getTime(getAngle(getEndx(),getEndy())),
                            wlength / 2,
                            hlength / 2 - outd / 2 - 20,
                            timePaint);
        }else{
            drawLine(
                    canvas,
                    false,
                    wlength / 2 + outd / 2 - 100,
                    hlength / 2);
        }


    }

    private double getAngle(float x, float y) {
        float xa = 0;
        float ya = -inerd / 2;

        float xb = x - wlength / 2;
        float yb = y - hlength / 2;

        float result = (ya * yb) / ((inerd / 2) * (inerd / 2));
        return Math.acos(result) * 180 / PI;
    }

    private String getTime(Double angle) {
        boolean flag = true;
        if(getEndx() > wlength / 2){
            flag = true;
        }else{
            flag = false;
        }

        if (flag) {
            if(angle >= 0 && angle < 30){
                return "12";
            } else if (angle >= 30 && angle < 60) {
                return "1";
            } else if (angle >= 60 && angle < 90) {
                return "2";
            } else if (angle >= 90 && angle < 120) {
                return "3";
            } else if (angle >= 120 && angle < 150) {
                return "4";
            } else if (angle >= 150 && angle < 180) {
                return "5";
            }
        }else{
            if (angle >= 0 && angle < 30) {
                return "11";
            } else if (angle >= 30 && angle < 60) {
                return "10";
            } else if (angle >= 60 && angle < 90) {
                return "9";
            } else if (angle >= 90 && angle < 120) {
                return "8";
            } else if (angle >= 120 && angle < 150) {
                return "7";
            } else if (angle >= 150 && angle < 180) {
                return "6";
            }
        }
        return null;
    }
    private void drawLine(Canvas canvas, boolean flag, float endx, float endy) {
        if (flag) {
            //画分针
            canvas.drawLine(
                    wlength / 2,
                    hlength /2 ,
                    wlength / 2 + outd  / 2 - 100,
                    hlength / 2,
                    linePaint);

        } else {
            canvas.drawLine(
                    wlength / 2,
                    hlength / 2,
                    getEndx(),
                    getEndy(),
                    linePaint);
        }

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
//                float x = event.getX();
//                float y = event.getY();
//
//                distance = Math.sqrt(
//                        (x - wlength / 2) * (x - wlength / 2) +
//                        (y - hlength / 2) * (y - hlength / 2));
//
//                ratio = (inerd / 2) / (distance - inerd / 2);
//                x = (float)((wlength / 2 + ratio * x) / (1 + ratio));
//                y = (float)((hlength / 2 + ratio * y) / (1 + ratio));
////                Log.e("x", x + " ");
////                Log.e("y", y + " ");
////                Log.e("wlength", wlength / 2 + "");
////                Log.e("hlength", hlength / 2 + "");
//                setEndx(x);
//                setEndy(y);
//                postInvalidate();
//                break;
//
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();

                distance = Math.sqrt(
                        (x - wlength / 2) * (x - wlength / 2) +
                                (y - hlength / 2) * (y - hlength / 2));

                ratio = (inerd / 2) / (distance - inerd / 2);
                x = (float)((wlength / 2 + ratio * x) / (1 + ratio));
                y = (float)((hlength / 2 + ratio * y) / (1 + ratio));
//                Log.e("x", x + " ");
//                Log.e("y", y + " ");
//                Log.e("wlength", wlength / 2 + "");
//                Log.e("hlength", hlength / 2 + "");
                setEndx(x);
                setEndy(y);
                postInvalidate();
                Log.e("Angle", getAngle(x, y) + "");
                break;
        }
        return true;
    }
}
