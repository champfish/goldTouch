package com.utd.goldtouch.goldtouch4;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Ball {
    public int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Bitmap image;
    public double x, y, width, height, yVelocity, yAceloration;
    public Ball(Bitmap bmp, int x, int y,int width,int height, double yAceloration) {
        this.width = width;
        this.height = height;
        image = bmp;
        this.x = x;
        this.y = y;
        this.yAceloration = yAceloration;
    }


    public void draw(Canvas canvas) {
        canvas.drawBitmap(image,(int) (x-width/2), (int) (y-height/2), null);
    }

    public void setYVelocity(double newYVel){
    this.yVelocity = newYVel;
    }

    public void update(double deltaTime) {
        yVelocity += yAceloration * deltaTime;
        y += yVelocity*deltaTime;
        if(y-height/2<0){
            y=height/2;
        }
        if(y+height/2>screenHeight){
            y=screenHeight-height/2;
            yVelocity=yAceloration * deltaTime;
        }
    }
}