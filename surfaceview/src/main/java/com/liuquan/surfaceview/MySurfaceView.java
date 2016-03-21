package com.liuquan.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by PC on 2016/3/18.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private  boolean flag = true;
    private SurfaceHolder holder;

    public MySurfaceView(Context context) {
        this(context, null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        new HolderThread(surfaceHolder).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        flag= false;
    }
    class HolderThread extends Thread{
        private SurfaceHolder surfaceholder;
        private Paint mPaint;
        private int i = 0;
        public HolderThread(SurfaceHolder surfaceholder) {
            this.surfaceholder = surfaceholder;
            this.mPaint = new Paint();
            mPaint.setColor(Color.RED);
        }

        @Override
        public void run() {
            while(flag){
                Canvas canvas = surfaceholder.lockCanvas();
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(300,400,100*(i%2+1),mPaint);
                surfaceholder.unlockCanvasAndPost(canvas);
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    }
}
