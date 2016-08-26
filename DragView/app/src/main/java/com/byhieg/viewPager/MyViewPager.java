package com.byhieg.viewPager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by shiqifeng on 2016/8/25.
 * Mail:byhieg@gmail.com
 */
public class MyViewPager extends ViewGroup{

    private Scroller mScroller;
    private int lastX;
    private int mStart, mEnd;
    private int mScreenWidth;

    public MyViewPager(Context context) {
        super(context);
        init(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        mScroller = new Scroller(context);
        LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        LinearLayout l1 = new LinearLayout(context);
        l1.setLayoutParams(lp);
        l1.setBackgroundColor(context.getResources().getColor(android.R.color.holo_orange_dark));
        LinearLayout l2 = new LinearLayout(context);
        l2.setLayoutParams(lp);
        l2.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        LinearLayout l3 = new LinearLayout(context);
        l3.setLayoutParams(lp);
        l3.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_dark));
        addView(l1);
        addView(l2);
        addView(l3);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = 0;
        int childCount = getChildCount();
        for(int i = 0 ; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(width,0,width + child.getMeasuredWidth(),child.getMeasuredHeight());
            width += child.getMeasuredWidth();
            mScreenWidth = child.getMeasuredWidth();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录下起时点的位置
                mStart = getScrollX();
                //滑动结束，停止动画
                if (mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                //记录位置
                lastX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = lastX - x;
                Log.e("deltaX", deltaX + "");
                if (isMove(deltaX)) {
                    //在滑动范围内，允许滑动
                    scrollBy(deltaX, 0);
                }
                Log.e("getScrollX", getScrollX() + "");
                lastX = x;
                break;
            case MotionEvent.ACTION_UP:
                //判断滑动的距离
                int dScrollX = checkAlignment();
                //弹性滑动开启
                if(dScrollX > 0){//从左向右滑动
                    if (dScrollX < mScreenWidth / 2) {
                        Log.e("dScrollX", dScrollX + "");
                        mScroller.startScroll(getScrollX(),0,- dScrollX,0,500);
//                        scrollBy(-dScrollX,0);
                    }else {
                        mScroller.startScroll(getScrollX(),0,mScreenWidth - dScrollX,0,500);
//                        scrollBy(mScreenWidth - dScrollX,0);
                    }
                }else {//从右向左滑动
                    if (-dScrollX < mScreenWidth / 2) {
                        mScroller.startScroll(getScrollX(),0, - dScrollX,0,500);
//                        scrollBy(-dScrollX,0);
                    }else{
                        mScroller.startScroll(getScrollX(),0,-mScreenWidth - dScrollX,0,500);
//                        scrollBy(-mScreenWidth - dScrollX,0);
                    }
                }
                break;
        }
        //重绘
        invalidate();
        return true;
    }

    private boolean isMove(int deltaX){
        int scrollX = getScrollX();
        //滑动到第一屏，不能在向右滑动了
        if (deltaX < 0) {//从左向右滑动
            if (scrollX <= 0) {
                return false;
            } else if (deltaX + scrollX < 0) {
                scrollTo(0,0);
                return false;
            }
        }
        //滑动到最后一屏，不能在向左滑动
        int leftX = (getChildCount() - 1) * getWidth();
        if (deltaX > 0) {
            if (scrollX >= leftX) {
                return false;
            } else if (scrollX + deltaX > leftX) {
                scrollTo(leftX, 0);
                return false;
            }
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }

    private int checkAlignment(){
        //判断滑动的趋势，是向左还是向右，滑动的偏移量是多少
        mEnd = getScrollX();
        boolean isUp = ((mEnd - mStart) > 0);
        int lastPrev = mEnd % mScreenWidth;
        int lastNext = mScreenWidth - lastPrev;
        if (isUp) {
            return lastPrev;
        }else{
            return -lastNext;
        }
    }

}