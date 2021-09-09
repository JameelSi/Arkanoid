package com.majdj_jameels.ex2;

import android.graphics.Canvas;

public class BrickCollection {

    private int rows,cols;
    private float width,height;
    private Brick[][] brickArr;
    private int sum = 0;


    public BrickCollection(int rows,int cols, float height,float width){
        this.rows=rows;
        this.cols=cols;
//        height of the brick is total height / 20
        this.height=height/20;
//        width of the brick is total width / num of bricks in the same row
        this.width=width/cols;
        brickArr= new Brick[rows][cols];
//      create new bricks and assign the coordinates of them where the first brick coordinates are (250,5) 250 px from top , 5px from right , the on to the right has the same height
//        but margin 5px from the prev one and the one below it is 250 from the top + height of the brick above it + brick height +5 px margin
        for(int i=0;i<rows;i++)
            for(int j=0;j<cols;j++)
                brickArr[i][j]=new Brick(5+((j*this.width)+(j*5)),(200+this.height+(i*this.height)+5*i),this.height,this.width);

    }
    public Brick[][]getB(){
        return brickArr;
    }
    public void draw(Canvas canvas) {
        sum = 0;
        for(int i=0;i<rows;i++)
            for(int j=0;j<cols;j++) {
                if(!brickArr[i][j].collidedWithBall()) {
                    brickArr[i][j].draw(canvas);
                }
                sum+=1;

            }
    }

    public int returnSum() {
        return sum;
    }

    public Brick[][] returnBricks() {
        return brickArr;
    }
}
