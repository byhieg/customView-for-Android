package cn.byhieg.customlinearlayoutdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.WindowDecorActionBar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by byhieg on 17/4/20.
 * Contact with byhieg@gmail.com
 */

public class TabView extends LinearLayout implements View.OnClickListener{

    private ImageView imageView;
    private TextView textView;


    public TabView(Context context) {
        super(context);
        initView(context);
    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(context).inflate(R.layout.tab_view,this,true);
        imageView = (ImageView)view.findViewById(R.id.tab_image);
        textView = (TextView) view.findViewById(R.id.tab_text);

    }


    public void initData(TabItem item) {
        imageView.setImageResource(item.imageResId);
        textView.setText(item.lableResId);
    }

    @Override
    public void onClick(View v) {

    }
}
