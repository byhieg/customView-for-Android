package com.example.byhieg.circleloadingview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewTestActivity extends AppCompatActivity {

    public CircleLoadingView circleLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);
        circleLoadingView = (CircleLoadingView) findViewById(R.id.loading);
        circleLoadingView.setViewVisable(true);
    }
}
