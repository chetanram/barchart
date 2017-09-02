package com.barchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.View;

import java.util.ArrayList;


public class GraphViewY extends View {

    private ArrayList<StatisticsModel> list_statistics;
    private Context context;

    private Paint paint;
    private int newPower;
    private int slotDisplay;
    private int iteration;
    private float maxValue = 0;

    public GraphViewY(Context context, ArrayList<StatisticsModel> list_statistics) {
        super(context);
        this.context = context;
        this.list_statistics = list_statistics;
        for (int i = 0; i < list_statistics.size(); i++) {
            float value = Float.parseFloat(list_statistics.get(i).getAmount());
            if (value > maxValue) {
                maxValue = value;
            }
        }
        int numberOfDigit = String.valueOf((int) maxValue).length();
        newPower = (int) Math.pow(10, (numberOfDigit - 1));
        slotDisplay = (int) maxValue;
        if (numberOfDigit != 1) {

            slotDisplay = (int) (maxValue / newPower);
            if ((maxValue % newPower) != 0) {
                slotDisplay += 1;
            }
            slotDisplay *= newPower;
        }
        iteration = (slotDisplay / newPower) * 10;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        float border = context.getResources().getDimension(R.dimen.dp_30);
        float horstart = border * 2;
        float height = getHeight();
        float width = getWidth() - 1;
        float max = getMax();
        float min = getMin();
        float diff = max - min;
        float graphheight = height - (2 * border);
        float graphwidth = width - (2 * border);

        paint.setTextAlign(Align.LEFT);
        paint.setTextSize(context.getResources().getDimension(R.dimen.dp_15));
        int vers = iteration;
        for (int i = 0; i <= iteration; i++) {
            if (((newPower * i) / 10) % newPower == 0) {
                paint.setColor(Color.RED);
            } else {
                paint.setColor(Color.DKGRAY);
            }
            float y = ((graphheight / vers) * i) + border;
            paint.setColor(Color.BLACK);
            paint.setColor(Color.DKGRAY);
            if (((newPower * i) / 10) % newPower == 0) {
                canvas.drawText(String.valueOf(slotDisplay - (newPower * i / 10)), context.getResources().getDimension(R.dimen.dp_3), y + context.getResources().getDimension(R.dimen.dp_5), paint);
            }
        }

    }

    private float getMax() {
        float largest = Integer.MIN_VALUE;
        for (int i = 0; i < list_statistics.size(); i++) {
            float value = Float.parseFloat(list_statistics.get(i).getAmount());
            if (value > largest) {
                largest = value;
            }
        }
        return slotDisplay;
    }

    private float getMin() {
        float smallest = Integer.MAX_VALUE;
        for (int i = 0; i < list_statistics.size(); i++) {
            float value = Float.parseFloat(list_statistics.get(i).getAmount());
            if (value < smallest) {
                smallest = value;
            }
        }
        return 0;
    }

}