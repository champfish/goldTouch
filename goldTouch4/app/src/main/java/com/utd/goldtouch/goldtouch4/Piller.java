package com.utd.goldtouch.goldtouch4;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Piller {
    private Bitmap image;
    public double x, y, width, height, xVelocity;
    public boolean scored = false;
    public Piller(Bitmap bmp, double x, double y, double width, double height,double xVelocity) {
        image = bmp;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xVelocity = xVelocity;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, (int) (x-width/2), (int) (y-height/2), null);
    }

    public void update(double deltaTime) {
        x += xVelocity*deltaTime;
    }

    public boolean touches(Ball b){
        //return b.x+b.width/2>x-width/2 && b.x-b.width/2<x+width/2 &&
        //        b.y+b.height/2>y-height/2 && b.y-b.height/2<y+height/2;
        return b.x>x-width/2 && b.x-b.width/2<x+width/2 &&
                b.y+b.height/2>y-height/2 && b.y-b.height/2<y+height/2;

    }
}
