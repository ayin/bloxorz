package com.androidapk.ayin.bloxorz;

import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View{
	Context mContext;
	MapLayer ml;
	BloxLayer bl;
	TimerTask task;
	Timer timer;
	Bitmap mCache;
    private int width=data.width;
    private int height=data.height;
	Graphics g;
	public GameView(Context context) {
        this(context, null);
    }
    
    public GameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        ml = new MapLayer(mContext);
        bl = new BloxLayer(mContext);
        initBitmap();
        gameInit();
    }
    int mProcessStep = 2;
    byte x,y;
    int yStart = 200;
    Matrix mMatrix = new Matrix();
    protected void onDraw(Canvas canvas) {
    	g = new Graphics(canvas);
    	if(mProcessStep == 0){
       		g.drawImage(imgStart1,width/2-81+x,height/2-14,0);
   			g.drawRegion(imgStart2,25*(data.level/10),0,25,28,0,width/2+32,height/2-14,0);
   			g.drawRegion(imgStart2,25*(data.level%10),0,25,28,0,width/2+57,height/2-14,0);
   			if(y==0){x+=5;y=1;}
   			else {x-=5;y=0;}
    		invalidate();
    		return;
    	}
    	mMatrix.setScale(total_scale, total_scale);
    	mMatrix.postTranslate((data.width -(int)(300*total_scale))>>1, 
    			(data.height - (int)(200*total_scale))>>1);
    	canvas.setMatrix(mMatrix);
    	if(mProcessStep == 1){
    		if(yStart>0){
    			yStart-=20;if(yStart<0)yStart=0;
    			ml.setPosition(0, yStart);
    			ml.paint(g);
    			postInvalidateDelayed(50);
    		}else if(data.step == 0){
    			ml.setPosition(0, 0);
    			ml.paint(g);
    			bl.paint(g);
    			postInvalidateDelayed(50);
    		}else{
    			ml.setPosition(0, 0);
    			ml.paint(g);
    			bl.paint(g);
    			mProcessStep = 2;
    			((BloxorzActivity)mContext).PlayMusic(R.raw.sound1230);
    		}
        }else{
        	ml.paint(g);
			bl.paint(g);
        }
    }
    public void initBitmap(){
    	try{
    		imgStart1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.s1,null);
    		imgStart2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.s2,null);
		}catch (Exception e){
			return;
		}
    }
    Bitmap imgStart1,imgStart2,img1;
    TimerTask mTask;
    Timer mTimer;
    public void gameInit(){
    	new data().UserInfoWrite();
    	data.level++;
    	mProcessStep = 0;
    	invalidate();
    	((BloxorzActivity)mContext).PlayMusic(R.raw.sound350);
    	mTask = new TimerTask(){
	    	public void run(){
	    		bl.init();
	    		data.step = 0;
	    		yStart = 200;
	    		mProcessStep = 1;
	    	}
	    };
		mTimer = new Timer();
		mTimer.schedule(mTask, 1000);
		new data().readMap(data.level);
    }
    public void gameStart(){
    	new data().readMap(data.level);
    	bl.init();
    	data.step = 0;
		yStart = 200;
		mProcessStep = 1;
		invalidate();
    }
    public void gameNext(){
    	bl.initNext();
    	new DateThread(10).start();
    //	while(data.step==10)
	//		drawScreen(g);
    //	gameInit();
    }
    int mC;
    public void gameMap(int c){
    	mC = c;
    	mTask = new TimerTask(){
    		int c = mC -10;
        	byte i,j;
        	int m,n;
        	byte temp[][]=new byte[data.mapSNum][4],l=0;
    		
    		int cnt = 0;
	    	public void run(){
	    		if(cnt == 0){
	    	    	for(i=0;i<data.mapSNum;i++){
	    	    		if(data.mapS[i][0]==c){
	    	    			m=data.mapS[i][1];
	    		    		if(m<0)m+=256;
	    		    		n=m%data.col;m=m/data.col;
	    		    		temp[l][0]=data.map[m][n];
	    		    		temp[l][1]=(byte)m;temp[l][2]=(byte)n;
	    		    		temp[l][3]=(byte)data.mapS[i][2];
	    		    		l++;
	    		    		data.map[m][n]=8;
	    	    		}
	    	    	}
	    	    	mHandler.sendEmptyMessage(0);
	    		}else if(cnt == 1){
		    		for(i=0;i<l;i++){
		        		m=temp[i][1];n=temp[i][2];
		        		c=temp[i][0];j=temp[i][3];
		        		data.map[m][n]=(byte)c;
		    	    	if(c==0){
		    	    		if(j!=2)
		    	    			data.map[m][n]=4;
		    	    	}else if(c==4){
		    	    		if(j!=1)
		    	    			data.map[m][n]=0;
		    	    	}
		        	}
		        	if(data.level==27&&c==87&&bl.flag==1)
		        		data.map[9][9]=0;
		        	mHandler.sendEmptyMessage(0);
	    		}else if(cnt == 2)
	    			mTimer.cancel();
	    		cnt++;
	    	}
	    };
		mTimer = new Timer();
		mTimer.schedule(mTask, 100,100);
    }
    public void gameTear(){
    	data.tearFlag=1;
    	data.setTear();
    	bl.paintInit();
    	invalidate();
    }
    /*
     * change the bloxorz between the separate two
     */
    public void gameChange(){
    	if(data.tearFlag==0)return;
    	byte k=7;
    	bl.initChange();
    	//while(k!=0){
    	//	drawScreen(g);
    	//	k--;
    	//}
    	//bl.delChange();
    	new ChangeThread(k).start();
    }
    boolean mKeyFlag = true;
	public boolean onTouchEvent (MotionEvent event){
		if(!mKeyFlag)return true;
		if(event.getPointerCount() == 2) {  
            scaleWithFinger(event);  
        }else if(event.getPointerCount() == 1) {  
            moveWithFinger(event);  
        }
    	return true;
	}
	float beforeLenght,afterLenght,total_scale = data.height/300;
	public void scaleWithFinger(MotionEvent event) {  
        float moveX = event.getX(1) - event.getX(0);  
        float moveY = event.getY(1) - event.getY(0);  
          
        switch(event.getAction()) {  
        case MotionEvent.ACTION_DOWN:  
            beforeLenght = (float) Math.sqrt( (moveX*moveX) + (moveY*moveY) );  
            break;  
        case MotionEvent.ACTION_MOVE:  
            //得到两个点之间的长度  
            afterLenght = (float) Math.sqrt( (moveX*moveX) + (moveY*moveY) );  
              
            float gapLenght = afterLenght - beforeLenght;  
              
            if(gapLenght == 0) {  
                break;  
            }  
            //如果当前时间两点距离大于前一时间两点距离，则传0，否则传1  
            if(gapLenght>10) {  
            	total_scale+=0.1;
            }else if(gapLenght<-10){  
            	total_scale-=0.1;
            }  
              if(total_scale>2)total_scale=(float)2;
              else if(total_scale<0.6)total_scale=(float)0.6;
            beforeLenght = afterLenght;  
        	break;
        }  
        invalidate();
    }
	float xx,yy;
	int mDir;
	public void moveWithFinger(MotionEvent event) {
		int flag1 = 0;
		float x=0,y=0;
		x = event.getX();
		y = event.getY();
    	switch (event.getAction())   
    	{ 
    		case MotionEvent.ACTION_DOWN:
    			xx = x; yy = y;
    			break;
    		case MotionEvent.ACTION_MOVE:
    			break;
    		case MotionEvent.ACTION_UP:
    			if(Math.abs(yy - y) < 100 && x - xx > 100){
    				mDir = 6; flag1 = 1;
    			}else if(Math.abs(yy - y) < 100 && xx - x > 100){
    				mDir = 4; flag1 = 1;
    			}else if(yy - y > 100 && Math.abs(xx - x) < 100){
    				mDir = 2; flag1 = 1;
    			}else if(y - yy > 100 && Math.abs(xx - x) < 100){
    				mDir = 8; flag1 = 1;
    			}
    			break;  
    	}
    	if(flag1 == 1){
    		gameMove(mDir);
		}
	}
	public void gameMove(int mDir){
		mKeyFlag = false;
		bl.setState(mDir);
		gameTurn(0);//will jump to afterTurn
		((BloxorzActivity)mContext).PlayMusic(bl.getMusic());
	}
	public void afterTurn(){
		int c=bl.check();
		mKeyFlag = true;
		if(c==1){
			((BloxorzActivity)mContext).PlayMusic(R.raw.sound425);
			gameStart();
		}
		else if(c==2){
			gameNext();
			((BloxorzActivity)mContext).PlayMusic(R.raw.sound158);
		}
		else if(c==3){
			gameTear();
			((BloxorzActivity)mContext).PlayMusic(R.raw.sound1211);
		}
		else if(c>10){
			((BloxorzActivity)mContext).PlayMusic(R.raw.sound1227);
			gameMap(c);
		}
		else if(c==-5)gameTurn(-5);
	}
	Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what == 0){
				invalidate();
			}else if(msg.what == 1){
				afterTurn();
			}else if(msg.what == 2){
				gameInit();
			}
		}
	};
	private class ChangeThread extends Thread {
		int mTimes;
		ChangeThread(int n){
			mTimes = n;
		}
		public void run() {
			while(mTimes-->=0){
				mHandler.sendEmptyMessage(0);
				SystemClock.sleep(100);
			}
			bl.delChange();
		}
	}
	private class DateThread extends Thread {
		int mStep;
		DateThread(int n){
			mStep = n;
		}
		public void run() {
			while(data.step == mStep){
				mHandler.sendEmptyMessage(0);
				SystemClock.sleep(100);
			}
			mHandler.sendEmptyMessage(2);
		}
	}
	private class ProgressThread extends Thread {
		int mTimes;
		ProgressThread(int n){
			mTimes = n;
		}
		public void run() {
			while(mTimes-->=0){
				mHandler.sendEmptyMessage(0);
				SystemClock.sleep(100);
			}
			mHandler.sendEmptyMessage(1);
		}
	}
	public void gameTurn(int a){
		new ProgressThread(a+6).start();
	}
}