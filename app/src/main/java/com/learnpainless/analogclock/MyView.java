package com.learnpainless.analogclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

    //width & height of clock
    int WIDTH, HEIGHT;
    int secHAND, minHAND, hrHAND; // lengths of the hands
    private long startTime;
    //center
    int cx, cy;

    Paint paint = new Paint();
    long op;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        final Handler h = new Handler();
        long elapsedTime = op;
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                invalidate(); //invalidate the graphics every 1 second
                //-------------------
                h.postDelayed(this, 16);
            }
        }, 16); // 1 second delay (takes millis)
        startTime = System.currentTimeMillis();

        // Initialization code for the view
    }

    public MyView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        //timer

    }


    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        int x = getWidth();
        int y = getHeight();

        cx = x / 2;
        cy = y / 2;

        secHAND = (x / 2) - (x / 25);
        minHAND = (x / 2) - (x / 12);
        hrHAND = (x / 2) - (x / 5);

        WIDTH = getWidth();
        HEIGHT = WIDTH;
        long elapsedTime = op;


        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawPaint(paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.argb(255, 128, 0, 128));
        canvas.drawCircle(cx, cy, WIDTH / 27, paint);


        // Calculate angles for hands
        float secAngle = (float) (elapsedTime % 60000) / 1000 * 006f;  // Continuous seconds motion
        float minAngle = (float) (elapsedTime % 3600000) / 60000; // Continuous minutes motion

        // Update your hands angles
        drawHand(secAngle, secHAND, Color.RED, WIDTH / 120, canvas);
        drawHand(minAngle, minHAND, Color.GREEN, WIDTH / 80, canvas);

        // Cover center hands
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawCircle(cx, cy, WIDTH / 50, paint);
    }

    private void drawHand(float angle, int length, int color, int strokeWidth, Canvas canvas) {
        int[] handCoord = msCoord((int) angle, length);
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawLine(cx, cy, handCoord[0], handCoord[1], paint);
    }
    private int[] msCoord(int val, int hlen) {
        int[] coord = new int[2];
//        val *= 6;   // each minute and second make 6 degrees

        if (val >= 0 && val <= 180) {
            coord[0] = cx + (int) (hlen * Math.sin(Math.PI * val / 180));
            coord[1] = cy - (int) (hlen * Math.cos(Math.PI * val / 180));
        } else {
            coord[0] = cx - (int) (hlen * -Math.sin(Math.PI * val / 180));
            coord[1] = cy - (int) (hlen * Math.cos(Math.PI * val / 180));
        }
        return coord;

    }

    void mo(long l){
        op=l;
    }
}