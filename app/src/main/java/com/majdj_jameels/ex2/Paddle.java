package com.majdj_jameels.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class Paddle {

    private float x, y, height, width;
    private Paint padPaint;

    public Paddle(float x, float y, float height, float width) {
        this.x = x;
        this.y = y;
        this.height=height;
        this.width=width;
//paddle paint
        padPaint = new Paint();
        padPaint.setColor(Color.BLUE);
        padPaint.setStrokeWidth(1);
        padPaint.setStyle(Paint.Style.FILL);
    }

    public void move(float tx,int w) {
//        check boundaries if the paddle is out of boundaries  return it else move it
        if(this.x<tx && tx+width/2 >w)
                this.x--;
        else if(this.x>tx && tx-width/2 <0)
                    this.x++;
        else
            this.x=tx;

    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return this.width; }
    public float getHeight() { return this.height; }

    public void draw(Canvas canvas) {
        canvas.drawRect(x-(width/2),y-height,x+width/2,y,padPaint);
    }
// a function that checks if the coordinates tx,ty is inside the Paddle
    public boolean isInside(float tx, float ty)
    {
        if((ty>=y-this.height && ty<=this.y) && (tx<=x+this.width/2 && tx>=this.x-this.width/2))
            return true;
        return false;
    }


}
