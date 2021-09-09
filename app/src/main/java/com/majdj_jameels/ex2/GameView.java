package com.majdj_jameels.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameView extends View {
    private Paddle pad;
    private  final int ROWS;
    private  final int COLS;
    private BrickCollection bricks;
    private Ball ball;
    private float dx,dy;
    private Thread ballThread;
//states
    private Paint textPaint,livesPaint,livesCirc;
    private static int GET_READY = 1;
    private static int PLAYING = 2;
    private static int GAME_OVER = 0;
    private int gameState;
    private boolean dragging ;
    private int score_num = 0;
    private int numOfLives=3;
    private Brick brick;
    private Brick[][] brickArr;
    private int sumOfBricks = 0;
    private int brickDown=0;
    private MediaPlayer mp;

    public GameView(Context context, AttributeSet atr)
    {

        super(context, atr);
        mp= MediaPlayer.create(getContext(), R.raw.score_notif);
//        generate random rows*columns num of bricks
        Random random = new Random();
        ROWS=random.nextInt(5)+2;
        COLS=random.nextInt(5)+3;

//        generating random speed
        dx=random.nextInt(13)+7;
        dy=random.nextInt(13)+7;
// score paint
        textPaint = new Paint();
        textPaint.setColor(Color.GREEN);
        textPaint.setTextSize(60);
        textPaint.setTextAlign(Paint.Align.CENTER);
//        lives paint
        livesCirc = new Paint();
        livesCirc.setColor(Color.GREEN);
        livesCirc.setStrokeWidth(6);
        livesCirc.setStyle(Paint.Style.STROKE);

        livesPaint = new Paint();
        livesPaint.setColor(Color.WHITE);
        livesPaint.setStrokeWidth(1);
        livesPaint.setStyle(Paint.Style.FILL);

//        current game state is get ready
        gameState=GET_READY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String score="Score: ";
        String lives="Lives: ";
//      draw paddle, ball , bricks , lives , score
        pad.draw(canvas);
        bricks.draw(canvas);
        sumOfBricks = bricks.returnSum();
        ball.draw(canvas);
        canvas.drawText(score+score_num, 150, 100, textPaint);
        canvas.drawText(lives, getWidth()-400, 100, textPaint);
        for(int i=0;i<3;i++) {
            canvas.drawCircle(getWidth()-250+(i*100), 80, 30, livesCirc);
            if(i<=numOfLives-1)
                canvas.drawCircle(getWidth()-250 +(i*100), 80, 20, livesPaint);
        }

        if(gameState==GET_READY)
            canvas.drawText("Click to PLAY!", getWidth()/2, getHeight()/2, textPaint);
        if(numOfLives==0) {
            gameState = GAME_OVER;
            canvas.drawText("GAME OVER - You Lose!", getWidth()/2, getHeight()/2, textPaint);
            brickDown=0;
            for(int i=0;i<ROWS;i++)
                for(int j=0;j<COLS;j++)
                        brickArr[i][j].setCollidedWith();
            invalidate();

        }
        if(numOfLives>0 && brickDown==sumOfBricks) {
            gameState = GAME_OVER;
            canvas.drawText("GAME OVER - You Win!", getWidth() /2, getHeight()/2, textPaint);
            for(int i=0;i<ROWS;i++)
                for(int j=0;j<COLS;j++)
                    brickArr[i][j].setCollidedWith();
                numOfLives=3;
                score_num=0;
            invalidate();

        }




    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
//      create bricks
        bricks=new BrickCollection(ROWS,COLS,h,w);
//        create a paddle which is 150 px high from the bottom , its width=brick width , height=half , and its position is in the middle
        pad=new Paddle(w/2,h-150,h/40,w/COLS);
//        create a ball that its position is on the top of the paddle and its radius is half the height of a brick
        ball = new Ball(pad.getX(),pad.getY()-pad.getHeight()-h/40,h/40,dx,-dy);
        callUIThread();

    }
    public void callUIThread(){

            ballThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        // update UI
                        updateUI();
                    }
                }
            });
            ballThread.start();

    }
    public void updateUI() {
            if(gameState==PLAYING) {
                ball.move(getWidth());

                ball.collideWithPaddle(pad);
                brickArr = bricks.returnBricks();
//            BRICK COLLIDE --------------------------------------------------------------------------
                for(int m=0;m<ROWS;m++) {
                    for (int n = 0; n < COLS; n++) {
                        brick = brickArr[m][n];
                        if(!brick.collidedWithBall()) {
                            if (ball.collideWithBrick(brick)) {
                                mp.start();
                                brickDown+=1;
                                brick.deleteBrick();
                                score_num += numOfLives*5;

                            }
                        }
                    }
                }
                if (ball.fell(getHeight())) {
                    ball.setX(pad.getX());
                    ball.setY(pad.getY() - pad.getHeight() - ball.getRad());
                    ball.flipDY();
                    gameState = GET_READY;
                    numOfLives--;
                    dragging = false;
                }
                postInvalidate();
                SystemClock.sleep(20);
            }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float tx = event.getX();
        float ty = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(gameState==PLAYING) {
                    if (pad.isInside(tx, ty)) {
                        dragging = true;
                        invalidate();
                    }
                }

                break;

            case MotionEvent.ACTION_MOVE:
                if(dragging){
                    pad.move(tx,getWidth());
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                if(gameState==GET_READY) {
                    gameState = PLAYING;
                }
                else if(gameState==GAME_OVER) {
//                    reset num of lives and score
                    numOfLives = 3;
                    score_num=0;
                    gameState = GET_READY;
                    invalidate();
                }

                else if(gameState==PLAYING)
                    dragging=false;
                break;

        }
        return true;
    }



}
