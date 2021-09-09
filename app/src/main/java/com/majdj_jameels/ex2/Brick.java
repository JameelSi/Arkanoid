package com.majdj_jameels.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Brick {

    private float x, y, height, width;
    private boolean collidedWith;
    private Paint brickPaint;


    public Brick(float x, float y, float height, float width) {
        collidedWith = false;

        this.x = x;
        this.y = y;
        this.height=height;
        this.width=width;

        brickPaint = new Paint();
        brickPaint.setColor(Color.RED);
        brickPaint.setStrokeWidth(10);
        brickPaint.setStyle(Paint.Style.FILL);
    }
    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return this.width; }
    public float getHeight() { return this.height; }
    public void setCollidedWith(){collidedWith=false;}
    public void draw(Canvas canvas) {
        canvas.drawRect(x,y+height,x+width,y,brickPaint);
    }

    public boolean collidedWithBall() {
        return collidedWith;
    }

    public void deleteBrick() {
        collidedWith = true;
    }

}