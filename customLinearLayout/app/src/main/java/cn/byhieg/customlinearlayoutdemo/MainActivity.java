package cn.byhieg.customlinearlayoutdemo;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyTabLayout.ITabListener{


    private ViewPager viewPager;

    private MyTabLayout tabLayout;
    ArrayList<TabItem> tabItems;

    private List<View> lists = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabLayout = (MyTabLayout) findViewById(R.id.myTab_layout);
        viewPager = (ViewPager) findViewById(R.id.myViewPager);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view1 = layoutInflater.inflate(R.layout.l1, null);
        View view2 = layoutInflater.inflate(R.layout.l2, null);
        View view3 = layoutInflater.inflate(R.layout.l3, null);
        View view4 = layoutInflater.inflate(R.layout.l4, null);

        lists.add(view1);
        lists.add(view2);
        lists.add(view3);
        lists.add(view4);

        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return lists.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(lists.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(lists.get(position));


                return lists.get(position);
            }
        };

        viewPager.setAdapter(pagerAdapter);


        tabItems = new ArrayList<>();
        tabItems.add(new TabItem(R.drawable.selector_tab_msg,"首页"));
        tabItems.add(new TabItem(R.drawable.selector_tab_moments, "通讯录"));
        tabItems.add(new TabItem(R.drawable.selector_tab_profile, "朋友圈"));
        tabItems.add(new TabItem(R.drawable.selector_tab_contact, "我"));
        tabLayout.initData(tabItems, this);
        tabLayout.setCurrentTab(0);

    }


    @Override
    public void onTabClick(TabItem item) {
        viewPager.setCurrentItem(tabItems.indexOf(item));
        tabLayout.setCurrentTab(tabItems.indexOf(item));
    }
}
