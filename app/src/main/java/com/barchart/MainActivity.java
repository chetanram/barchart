package com.barchart;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LinearLayout ll_x_axis;
    private LinearLayout ll_y_axis;
    private ArrayList<StatisticsModel> list_statistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ll_x_axis = (LinearLayout) findViewById(R.id.ll_x_axis);
        ll_y_axis = (LinearLayout) findViewById(R.id.ll_y_axis);
        list_statistics = new ArrayList<StatisticsModel>();
        for (int i = 1; i <= 12; i++) {
            StatisticsModel s = new StatisticsModel();
            Random random = new Random();
            int r = random.nextInt(200 - 1) + 1;
            if (i == 1) {
                s.setAmount(String.valueOf(100));
            } else {
                s.setAmount(String.valueOf(r));
            }
//            s.setAmount(String.valueOf(i * 10));
            s.setDate("10/17");

            list_statistics.add(s);
        }
        GraphViewX graphViewX = new GraphViewX(this, list_statistics);
        GraphViewY graphViewY = new GraphViewY(this, list_statistics);
        float deviceWidth = Common.getDeviceWidth(MainActivity.this);

        int width = (int) (graphViewX.iteration * Common.convertDpToPixel(graphViewX.iteration + 5, MainActivity.this));
        if (width < deviceWidth) {
            graphViewX.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        } else {
            graphViewX.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
        }
        ll_x_axis.addView(graphViewX);
        ll_y_axis.addView(graphViewY);
    }
}
