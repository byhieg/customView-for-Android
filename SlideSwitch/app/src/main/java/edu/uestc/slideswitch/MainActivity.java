package edu.uestc.slideswitch;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SlideSwitch ss = (SlideSwitch) findViewById(R.id.ss);
        ss.setOnSlideCompleteListener(new SlideSwitch.OnSlideCompleteListener() {
            @Override
            public void onComplete() {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Complete!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ss.reset(); //重置控件的状态
                                dialog.dismiss();
                            }})
                        .show();
            }
        });
    }
}
