package com.barchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class GraphViewX extends View {

    private ArrayList<StatisticsModel> list_statistics;
    private Context context;

    private Paint paint;
    private int newPower;
    private int slotDisplay;
    public int iteration;
    private float maxValue = 0;
    private List<Data> retangles;

    public GraphViewX(Context context, ArrayList<StatisticsModel> list_statistics) {
        super(context);
        this.context = context;
        this.list_statistics = list_statistics;

        retangles = new ArrayList<>();
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
        float horstart = 0;
        float height = getHeight();
        float width = getWidth() - 1;
        float max = getMax();
        float min = getMin();
        float diff = max - min;
        float graphheight = height - (2 * border);
        float graphwidth = width - (2 * border);

        paint.setTextAlign(Align.LEFT);
        int vers = iteration;
        for (int i = 0; i <= iteration; i++) {
            if (((newPower * i) / 10) % newPower == 0) {
                paint.setColor(Color.RED);
            } else {
                paint.setColor(Color.DKGRAY);
            }
            float y = ((graphheight / vers) * i) + border;
            canvas.drawLine(horstart, y, width, y, paint);
            paint.setColor(Color.BLACK);
//            canvas.drawText(verlabels[i], 0, y, paint);
            paint.setColor(Color.DKGRAY);
            if (((newPower * i) / 10) % newPower == 0) {
//                canvas.drawText(String.valueOf(slotDisplay - (newPower * i / 10)), 0, y, paint);
            }
        }
        int hors = list_statistics.size();
        paint.setTextSize(getResources().getDimension(R.dimen.dp_12));
        float colwidthDate = context.getResources().getDimension(R.dimen.dp_40);
        for (int i = 0; i < list_statistics.size(); i++) {
            paint.setColor(Color.DKGRAY);
            float x = (colwidthDate * i) + context.getResources().getDimension(R.dimen.dp_20);
            paint.setTextAlign(Align.CENTER);
            paint.setColor(Color.BLACK);
            canvas.drawText(list_statistics.get(i).getDate(), x, height - context.getResources().getDimension(R.dimen.dp_10), paint);
        }


        paint.setTextAlign(Align.CENTER);
//        canvas.drawText(title, (graphwidth / 2) + horstart, border - 4, paint);

        if (max != min) {
            paint.setColor(Color.RED);
            float datalength = list_statistics.size();
            float colwidth = context.getResources().getDimension(R.dimen.dp_40);
            for (int i = 0; i < list_statistics.size(); i++) {
                float val = Float.parseFloat(list_statistics.get(i).getAmount()) - min;
                float rat = val / diff;
                float h = graphheight * rat;
                RectF rectF = new RectF((i * colwidth) + horstart + context.getResources().getDimension(R.dimen.dp_10), (border - h) + graphheight, ((i * colwidth) + horstart) + (colwidth - 1), height - (border - 1));
//                canvas.drawRect((i * colwidth) + horstart + context.getResources().getDimension(R.dimen.dp_10), (border - h) + graphheight, ((i * colwidth) + horstart) + (colwidth - 1), height - (border - 1), paint);
                Data data = new Data();
                data.rectF = rectF;
                data.statistics = list_statistics.get(i);
                retangles.add(data);
                canvas.drawRect(rectF, paint);

            }

        }
        float left = context.getResources().getDimension(R.dimen.dp_25);
        float top = context.getResources().getDimension(R.dimen.dp_15);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Align.CENTER);
        for (int i = 0; i < list_statistics.size(); i++) {

            float x = (colwidthDate * i) + left;
            float val = Float.parseFloat(list_statistics.get(i).getAmount()) - min;
            float rat = val / diff;
            float h = graphheight * rat;
            canvas.drawText(list_statistics.get(i).getAmount(), x, (border - h) + graphheight + top, paint);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("Touching down!");

                break;
            case MotionEvent.ACTION_UP:
                System.out.println("Touching up!");
                for (Data data : retangles) {
                    if (data.rectF.contains(touchX, touchY)) {
//                        System.out.println("Touched Rectangle, start activity.");
//                        Intent i = new Intent(<your activity info>);
//                        startActivity(i);
                        Common.showToast(context, data.statistics.getAmount());

                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("Sliding your finger around on the screen.");
                break;
        }
        return true;
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