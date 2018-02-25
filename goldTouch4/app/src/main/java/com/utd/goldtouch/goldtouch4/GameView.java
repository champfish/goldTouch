package com.utd.goldtouch.goldtouch4;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import android.telephony.SmsManager;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    public int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public int distBetweenPillers = screenWidth;
    private MainThread thread;

    private Ball ball;
    int ballWidth = screenWidth/5;
    Bitmap ballImg = getResizedBitmap(BitmapFactory.decodeResource
            (getResources(), R.drawable.ball2),ballWidth,ballWidth);
    double yAceloration = -1 * screenHeight * 0.5;
    double jumpVelocity = screenWidth * 0.85;

    private Piller piller;
    int pillerHeight = screenHeight/3;
    int pillerWidth = (int) (pillerHeight * (110.0/236.0));
    int pillerY = 0;
    int pillerXVelocity = -1 * screenHeight/4;
    int pillerGap =  (int)(screenWidth * 0.45); //0.35
    int centerVar = (int) (screenWidth * 0.35);

    Bitmap backImg = getResizedBitmap(BitmapFactory.decodeResource
            (getResources(), R.drawable.background),screenWidth,screenHeight);
    private Piller background = new Piller(backImg,screenWidth/2,screenHeight/2,screenWidth,screenHeight,0);

    ArrayList<Piller> pillers = new ArrayList<>();
    Bitmap pillerImg = getResizedBitmap(BitmapFactory.decodeResource
            (getResources(), R.drawable.pipe),pillerWidth,pillerHeight);

    public int score = 0;


    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        jump();
        return super.onTouchEvent(event);
    }

    void jump(){
        ball.setYVelocity(jumpVelocity);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        start();
        thread.setRunning(true);
        thread.start();

    }

    void start(){
        pillers.clear();
        score = 0;
        ball = new Ball(ballImg,screenWidth/2, screenHeight/2, ballWidth, ballWidth, yAceloration);
        piller = new Piller(pillerImg,screenWidth,screenHeight*3, pillerWidth, pillerHeight, pillerXVelocity);
        piller.scored = true;
        pillers.add(piller);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();

            } catch(InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    long lastTime = System.nanoTime();
    public void update() {
        long time = System.nanoTime();
        double deltaTime = (time-lastTime)/1000000000.0;
        System.out.println(deltaTime);
        ball.update(deltaTime);
        Piller last = null;
        for(int i =0; i< pillers.size(); i++){
            last = pillers.get(i);
            last.update(deltaTime);
            if(!last.scored && last.x<screenWidth/2){
                last.scored = true;
                score+=10;
            }
            if(pillers.get(i).touches(ball)){
                start();
            }
        }
        if(last.x<screenWidth){
            double center = (screenHeight/2 + (Math.random()<0.5?-1:1)*(centerVar * Math.random()));

            pillers.add(getNewPiller(last.x+distBetweenPillers,center+(pillerGap+pillerHeight/2)));
            pillers.add(getNewPiller(last.x+distBetweenPillers,center-(pillerGap+pillerHeight/2)));
        }
        lastTime = time;
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        if(canvas!=null) {
            background.draw(canvas);


            for(int i = 0; i< pillers.size(); i++){
                pillers.get(i).draw(canvas);
            }

            String text = Math.min(score,300)+"%";
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setTextSize(64 * getResources().getDisplayMetrics().density);
            int color = 0xFFFF0000;
            color += ((int)(0xFF * ((300.0-Math.min(score,300))/300.0))) << 8;
            color += ((int)(0xFF * ((300.0-Math.min(score,300))/300.0)));
            paint.setColor(color);
            canvas.drawText(text,0,screenHeight-20,paint);

            ball.draw(canvas);
        }
    }

    Piller getNewPiller(double x, double y){
        return new Piller(pillerImg, x,y, pillerWidth, pillerHeight, pillerXVelocity);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap =
                Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }






    // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA



}
