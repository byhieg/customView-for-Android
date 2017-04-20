package cn.byhieg.customlinearlayoutdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by byhieg on 17/4/20.
 * Contact with byhieg@gmail.com
 */

public class MyTabLayout extends LinearLayout implements View.OnClickListener{

    public ITabListener listener;
    private int tabCount;
    private View selectedView;

    public MyTabLayout(Context context) {
        super(context);
        initView();
    }

    public MyTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView(){
        setOrientation(HORIZONTAL);
    }

    public void initData(List<? extends TabItem> lists,ITabListener listener){
        this.listener = listener;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup
                .LayoutParams.MATCH_PARENT);

        params.weight = 1;

        tabCount = lists.size();
        for (TabItem item : lists) {
            TabView tabView = new TabView(getContext());
            tabView.setTag(item);
            tabView.initData(item);
            tabView.setOnClickListener(this);
            addView(tabView,params);
        }
    }

    public void setCurrentTab(int i) {
        if (i >= 0 && i < tabCount){
            View view = getChildAt(i);
            if (selectedView != view) {
                view.setSelected(true);
                if (selectedView != null) {
                    selectedView.setSelected(false);
                }
                selectedView = view;
            }
        }
    }

    @Override
    public void onClick(View v) {
        listener.onTabClick((TabItem) v.getTag());
    }


    public interface ITabListener{

        void onTabClick(TabItem item);
    }
}
