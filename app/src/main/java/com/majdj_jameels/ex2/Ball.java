package com.majdj_jameels.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball {
    private float x, y,dx,dy, radius;
    private Paint ballPaint;
    public Ball(float x, float y, float radius,float dx,float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.radius = radius;
//ball paint
        ballPaint = new Paint();
        ballPaint.setColor(Color.WHITE);
        ballPaint.setStrokeWidth(1);
        ballPaint.setStyle(Paint.Style.FILL);
    }

    public void flipDY() { this.dy=-dy; }

    public void setX(float x) {
        this.x = x;
    }

    public float getRad() {
        return radius;
    }

    public void setY(float y) {
        this.y = y;
    }
    public void move(int w) {
        x +=dx;
        y +=dy;

        // check border left or right
        if (x - radius < 0 || x + radius > w)
            dx = -dx;
        // top
        if (y - radius < 0)
            dy = -dy;
    }

//        check if ball fell
    public boolean fell(int h){
        if(y+radius>h)
            return true;
        return false;
    }



    public void draw(Canvas canvas)
    {
        canvas.drawCircle(x, y, radius, ballPaint);
    }

    public void collideWithPaddle(Paddle pad) {
//        if the ball reaches the same height of the paddle and paddle left corner < ball < paddle right corner
            if(pad.getY()-pad.getHeight()==this.y+this.radius && (this.x>=(pad.getX()-pad.getWidth()/2 -this.radius) && this.x<=pad.getX()+pad.getWidth()/2+this.radius))
                    flipDY();
    }

    public boolean collideWithBrick(Brick br) {
//      hit brick from the bottom
        if ((br.getY()>=this.y-this.radius) && (this.x >=br.getX() && this.x <= br.getX() + br.getWidth())) {
            flipDY();
            return true;
        }
//        hit from above
        else if ((br.getY()>=this.y+this.radius) && (this.x >=br.getX() && this.x <= br.getX() + br.getWidth())) {
            flipDY();
            return true;
        }
//        hit from the right
        else if ((this.y>=br.getY()&&this.y<=br.getHeight()) && (this.x-this.radius<=br.getX()+br.getWidth() )) {
            dx=-dx;
            return true;
        }
//        hit from left
        else if ((this.y>=br.getY()&&this.y<=br.getHeight()) && (this.x+this.radius>=br.getX() )) {
            dx=-dx;
            return true;
        }


        else
            return false;

    }

}

