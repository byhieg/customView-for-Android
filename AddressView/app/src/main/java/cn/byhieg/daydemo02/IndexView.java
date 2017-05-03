package cn.byhieg.daydemo02;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by byhieg on 17/4/20.
 * Contact with byhieg@gmail.com
 */

public class IndexView extends LinearLayout {

    private Context context;
    private ITextListener listener;

    public IndexView(Context context) {
        super(context);
        initView(context);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public IndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context){
        setOrientation(VERTICAL);
        this.context = context;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);

        for (char i = 'A';i <= 'Z';i++) {
            TextView text = new TextView(context);
            text.setText(i + "");
            text.setTextSize(20);
            text.setTextColor(Color.rgb(33, 65, 98));
            text.setGravity(Gravity.CENTER);
            text.setLayoutParams(layoutParams);
            text.setClickable(true);
            addView(text);
            final Character c = i;
            text.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onListener(c);
                    }
                }
            });
        }

    }

    public void setITextListener(ITextListener listener) {
        this.listener = listener;
    }

    public interface ITextListener{

        void onListener(Character s);
    }
}
