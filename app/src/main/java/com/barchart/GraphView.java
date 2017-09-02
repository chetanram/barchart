package com.barchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by chetan on 31/8/17.
 */


public class GraphView extends View {

    public static boolean BAR = true;
    public static boolean LINE = false;
    private float newPower;
    private Context context;
    private float slotDisplay;
    private float iteration;
    private ArrayList<StatisticsModel> list_statistics;


    private Paint paint;
    private boolean type;
    private float maxValue = 0;

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
    }

    public GraphView(Context context, ArrayList<StatisticsModel> list_statistics, boolean type) {
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
        newPower = (float) Math.pow(10, (numberOfDigit - 1));
        slotDisplay = maxValue;
        if (numberOfDigit != 1) {

            slotDisplay = (int) (maxValue / newPower);
            if ((maxValue % newPower) != 0) {
                slotDisplay += 1;
            }
            slotDisplay *= newPower;
        }
        iteration = (slotDisplay / newPower) * 10;
        iteration += 1;
        this.type = type;
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float border = 30;
        float horLineStart = context.getResources().getDimension(R.dimen.dp_40);
        float horTextStart = context.getResources().getDimension(R.dimen.dp_5);
        ;

        float height = getHeight();
        float width = getWidth() - 1;
        float graphheight = height - (2 * border);
        float graphwidth = width - (2 * border);
        paint.setTextAlign(Align.LEFT);
        for (int i = 0; i < iteration; i++) {
            if (((newPower * i) / 10) % newPower == 0) {
                paint.setColor(Color.RED);
            } else {
                paint.setColor(Color.DKGRAY);
            }
            float temp = ((graphheight / iteration) * i);
            float y = graphheight - temp;
            canvas.drawLine(horLineStart, y, width, y, paint);
            paint.setTextSize(context.getResources().getDimension(R.dimen.dp_15));
            if (((newPower * i) / 10) % newPower == 0) {
                paint.setColor(Color.DKGRAY);
                canvas.drawText(String.valueOf((newPower * i / 10)), horTextStart, y + ((graphheight * 1) / 100), paint);
            }

        }
        float t = 0;
        for (int i = 0; i < list_statistics.size(); i++) {
            float amount = Float.parseFloat(list_statistics.get(i).getAmount());
            float temp = ((graphheight * amount) / slotDisplay);
            float y = graphheight - temp;
            paint.setColor(Color.GREEN);
            canvas.drawRect(horLineStart + t, y, 100 + t, graphheight, paint);
            paint.setColor(Color.BLACK);
            paint.setTextAlign(Align.CENTER);
            canvas.drawText(String.valueOf(amount), horLineStart + t + 15, y + 20, paint);
            t += 50;
        }
      /*  int hors = horlabels.length - 1;
        for (int i = 0; i < horlabels.length; i++) {
            paint.setColor(Color.DKGRAY);
            float x = ((graphwidth / hors) * i) + horstart;
            canvas.drawLine(x, height - border, x, border, paint);
            paint.setTextAlign(Align.CENTER);
            if (i == horlabels.length - 1)
                paint.setTextAlign(Align.RIGHT);
            if (i == 0)
                paint.setTextAlign(Align.LEFT);
            paint.setColor(Color.BLACK);
            canvas.drawText(horlabels[i], x, height - 4, paint);
        }

        paint.setTextAlign(Align.CENTER);
        canvas.drawText(title, (graphwidth / 2) + horstart, border - 4, paint);

        if (max != min) {
            paint.setColor(Color.LTGRAY);
            if (type == BAR) {
                float datalength = values.length;
                float colwidth = (width - (2 * border)) / datalength;
                for (int i = 0; i < values.length; i++) {
                    float val = values[i] - min;
                    float rat = val / diff;
                    float h = graphheight * rat;
                    canvas.drawRect((i * colwidth) + horstart, (border - h) + graphheight, ((i * colwidth) + horstart) + (colwidth - 1), height - (border - 1), paint);
                }
            } else {
                float datalength = values.length;
                float colwidth = (width - (2 * border)) / datalength;
                float halfcol = colwidth / 2;
                float lasth = 0;
                for (int i = 0; i < values.length; i++) {
                    float val = values[i] - min;
                    float rat = val / diff;
                    float h = graphheight * rat;
                    if (i > 0)
                        canvas.drawLine(((i - 1) * colwidth) + (horstart + 1) + halfcol, (border - lasth) + graphheight, (i * colwidth) + (horstart + 1) + halfcol, (border - h) + graphheight, paint);
                    lasth = h;
                }
            }
        }*/
    }


}