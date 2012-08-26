package com.androidapk.ayin.bloxorz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BloxLayer extends layer{
	int x1,y1,yStart; 
	Bitmap img[],img1[],imgNext;
	int state=3;
	byte flag=3;
	byte offset[][]={{-6,-34},{2,-46},{0,-23},{-25,-40},{-6,-52},{1,-24},{1,-19},{-20,-20},{-14,-41},{-5,-43},{0,-41},{-40,-38}};
	byte offset1[][]={{-7,-28},{0,-27},{0,-17},{-22,-24}};
	public byte datay[][]={
			{0,1,2,3,4},{4,3,2,1,0},{4,3,2,1,0},{0,1,2,3,4}
	};
	public byte datax[]={0,1,0,1};
	public byte dataz[]={0,1,0,5,2,3,4,3,4,5,2,1};
	public byte datav[][]={{4,3,2,1,0},{0,1,2,3,4},{0,1,2,3,4},{4,3,2,1,0},{0,1,2,3,4},{0,1,2,3,4},
			{0,1,2,3,4},{4,3,2,1,0},{4,3,2,1,0},{0,1,2,3,4},{4,3,2,1,0},{4,3,2,1,0}};
	byte changeFlag=0;
	Context mContext;
	int brickbitmap[]={R.drawable.brick0,R.drawable.brick1,R.drawable.brick2,
			R.drawable.brick3,R.drawable.brick4,R.drawable.brick5};
	public BloxLayer(Context context){
		super(500,500);
		mContext = context;
		img=new Bitmap[6];
		try{
			for(byte i=0;i<6;i++)
				img[i] = BitmapFactory.decodeResource(mContext.getResources(), brickbitmap[i],null);
				//img[i]=Image.createImage("/"+i+".png");
		}catch (Exception e){
			return;
		}
		img1=new Bitmap[2];
		try{
			img1[0] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.m,null);
			img1[1] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.n,null);
			//img1[0]=Image.createImage("/m.png");
			//img1[1]=Image.createImage("/n.png");
		}catch (Exception e){
			return;
		}
		try{
			imgNext = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.a,null);
			//imgNext=Image.createImage("/a.png");
		}catch (Exception e){
			return;
		}
	}
	//Image teimg;
	Bitmap teimg;
	byte dataf[]=new byte[5];
	int w=0,h=0;
	public void paint(Graphics g) {
		if(data.tearFlag==0){
			if(data.step==0){
				g.drawRegion(imgNext,0,0,30,55,0,x,yStart,0);
				yStart+=5;
				if(yStart>y)data.step=5;
			}else if(data.step==5){
				g.drawRegion(imgNext,0,0,30,55,0,x,y,0);
			}else if(data.step==1){
				g.drawRegion(teimg,w*dataf[yStart++],0,w,h,0,x,y,0);
				if(yStart>4)yStart=4;
			}else if(data.step==10){
				g.drawRegion(imgNext,yStart*30,0,30,55,0,x,y,0);
				yStart++;
				if(yStart>7)data.step=11;
			}
		}else{
			if(data.step==1){
				if(data.px+data.py*data.col<data.px1+data.py1*data.col){
					g.drawRegion(teimg,w*dataf[yStart++],0,w,h,0,x,y,0);
					if(yStart>4)yStart=4;
					g.drawRegion(img1[0],156,0,39,48,0,x1,y1,0);
				}else{
					g.drawRegion(img1[0],156,0,39,48,0,x1,y1,0);
					g.drawRegion(teimg,w*dataf[yStart++],0,w,h,0,x,y,0);
					if(yStart>4)yStart=4;
				}
			}
			if(changeFlag==1){
				g.drawImage(a,x-t1,y,0);
				g.drawRegion(a,0,0,17,35,Sprite.TRANS_MIRROR,x+t2,y,0);
				t1=t1-3;t2=t2-3;
			}
		}
	}
	public void initImg(){
		if(data.tearFlag==1){
			teimg=img1[datax[state-1]];
			dataf=datay[state-1];
		}
		else{
			teimg=img[dataz[state-1]];
			dataf=datav[state-1];
		}
		w=teimg.getWidth()/5;h=teimg.getHeight();
	}
	public void paintInit(){
		state=1;
		x=data.getX()+offset1[2][0];
		y=data.getY()+offset1[2][1];
		x1=250+data.py1*7-(data.col-1-data.px1)*22+offset1[2][0];
		y1=11*data.py1+4*(data.col-1-data.px1)+offset1[2][1];
		initImg();
	}
	int t1,t2;
	Bitmap a;
	public void initChange(){
		byte temp;
		changeFlag=1;
		t1=35;t2=47;
		temp=data.px;data.px=data.px1;data.px1=temp;
    	temp=data.py;data.py=data.py1;data.py1=temp;
    	paintInit();
    	try{
    		a = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.t,null);
    		//a=Image.createImage("/t.png");
    	}catch (Exception e){
			return;
		}
	}
	public void delChange(){
		changeFlag=0;
		a=null;
		System.gc();
	}
	public void setState(int s){
		yStart=0;data.step=1;
		if(data.tearFlag==1){
			if(s==2)state=1;
			else if(s==4)state=4;
			else if(s==6)state=2;
			else if(s==8)state=3;
			x=data.getX()+offset1[state-1][0];
			y=data.getY()+offset1[state-1][1];
			if(s==2)data.py--;
			else if(s==4)data.px--;
			else if(s==6)data.px++;
			else if(s==8)data.py++;
			initImg();
			return;
		}
		byte flag1=flag;
		if(flag==1){
			if(s==2)state=1;
			else if(s==6){state=2;flag=3;}
			else if(s==8)state=3;
			else if(s==4){state=4;flag=3;}
		}else if(flag==2){
			if(s==2){state=5;flag=3;}
			else if(s==6)state=6;
			else if(s==8){state=7;flag=3;}
			else if(s==4)state=8;
		}else if(flag==3){
			if(s==2){state=9;flag=2;}
			else if(s==6){state=10;flag=1;}
			else if(s==8){state=11;flag=2;}
			else if(s==4){state=12;flag=1;}
		}
		x=data.getX()+offset[state-1][0];
		y=data.getY()+offset[state-1][1];
		if(s==2){
			if(flag1==3)data.py-=2;
			else data.py--;
		}else if(s==4){
			if(flag1==3)data.px-=2;
			else data.px--;
		}else if(s==6){
			if(flag1==1)data.px+=2;
			else data.px++;
		}else if(s==8){
			if(flag1==2)data.py+=2;
			else data.py++;
		}
		initImg();
	}
	public int check(){
		byte i,j;
		byte px=data.px,py=data.py;
		if(px<0||py<0)
			return 1;
		if(px>=data.col||py>=data.row)
			return 1;
		i=data.map[py][px];
		if(data.tearFlag==1){
			if(px==data.px1){
				if(py==data.py1-1){
					data.tearFlag=0;
					flag=2;state=6;
				}else if(py==data.py1+1){
					data.py=data.py1;
					data.tearFlag=0;
					state=6;flag=2;
				}
			}
			if(py==data.py1){
				if(px==data.px1-1){
					data.tearFlag=0;
					flag=1;state=3;
				}else if(px==data.px1+1){
					data.px=data.px1;
					data.tearFlag=0;
					flag=1;state=3;
				}
			}
			if(i==0)
				return 1;
			else if(i==2)
				((BloxorzActivity)mContext).mGameView.gameMap(py*data.col+px+10);
			if(data.tearFlag==0){
				if(flag==1){
					x=data.getX()+offset[0][0];
					y=data.getY()+offset[0][1];
				}else if(flag==2){
					x=data.getX()+offset[7][0];
					y=data.getY()+offset[7][1];
				}
				initImg();
				return -5;
			}
			return 0;
		}
		if(flag==1){
			if(px+1>=data.col)
				return 1;
			j=data.map[py][px+1];
			if(i==0||j==0)
				return 1;
			else if(i==2)
				return py*data.col+px+10;
			else if(j==2)
				return py*data.col+px+11;
		}else if(flag==2){
			if(py+1>=data.row)
				return 1;
			j=data.map[py+1][px];
			if(i==0||j==0)
				return 1;
			else if(i==2)
				return py*data.col+px+10;
			else if(j==2)
				return (py+1)*data.col+px+10;
		}else if(flag==3){
			if(i==0||i==5)
				return 1;
			else if(i==7)
				return 2;
			else if(i==3||i==2){
				return py*data.col+px+10;
			}else if(i==6){
				return 3;
			}
		}
		return 0;
	}
	public void init(){
		data.step=0;flag=3;
		data.tearFlag=0;changeFlag=0;
		x=data.getX();
		y=data.getY()-40;
		yStart=y-50;
	}
	public void initNext(){
		data.step=10;yStart=0;
		x=data.getX();
		y=data.getY()-40;
	}
	public static int[] mMusic = {R.raw.sound1230,R.raw.sound1233,R.raw.sound1230,R.raw.sound1230,R.raw.sound1237,
		R.raw.sound1237,R.raw.sound1234,R.raw.sound1230,R.raw.sound1233};
	public int getMusic(){
		int rst;
		try{
		byte px=data.px,py=data.py;
		if(px<0||py<0)
			return mMusic[0];
		if(px>=data.col||py>=data.row)
			return mMusic[0];
		if(data.tearFlag==1){
			return mMusic[data.map[py][px]];
		}
		rst = mMusic[data.map[py][px]];
		if((flag == 1 && data.map[py][px] != data.map[py][px+1]) ||
			(flag == 2 && data.map[py][px] != data.map[py+1][px]))
			rst = mMusic[0];
		}catch (Exception e){
			return mMusic[0];
		}
		return rst;
	}
}
