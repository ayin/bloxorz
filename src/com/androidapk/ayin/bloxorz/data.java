package com.androidapk.ayin.bloxorz;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;

public class data {
	public static int width = 480,height = 800;
	public static byte total=33;
	public static byte step=0;
	public static byte px=0,py=0;
	public static byte px1=0,py1=0;
	public static byte level=0;
	public static byte row=1,col=1;
	public static byte[][] map;
	public static int[][] mapS;
	public static byte data[][]={
			{10,1,10,7},{12,1,2,1},{12,1,9,1},{13,1,1,8},
			{1,0,0,1},{1,2,0,1},{0,1,2,1},{2,1,1,0},{7,1,5,1},
			{13,1,13,7},{12,7,2,2},{5,6,7,6},{11,3,10,5},
			{14,6,12,9}
	};
	public static byte mapSNum;
	public static byte tearFlag=0;
	public static Context mContext;
	public  data(){
	}
	public  data(Context context){
		mContext = context;
	}
	public static int getX(){
		return 250+py*7-(col-1-px)*22;
	}
	public static int getY(){
		return 11*py+4*(col-1-px);
	}
	public static void setTear(){
		byte i=0;
		if(level==8)i=0;
		else if(level==9)i=1;
		else if(level==10)i=2;
		else if(level==15)i=3;
		else if(level==16){
			if(px==9&&py==6)i=4;
			else if(px==1&&py==2)i=5;
			else if(px==2&&py==1)i=6;
			else if(px==0&&py==1)i=7;
			else if(px==1&&py==0)i=8;
		}else if(level==20)i=9;
		else if(level==23)i=10;
		else if(level==24)i=11;
		else if(level==26)i=12;
		else if(level==28)i=13;
		px=data[i][0];py=data[i][1];px1=data[i][2];py1=data[i][3];
	}
	public void readMap(byte level){
		((BloxorzActivity)mContext).setTextView();
		mapSNum=0;
		try{
			int total;
			InputStream fsa = ((Activity)mContext).getAssets().open(level+".dat");
			total=fsa.available();
			byte a[]=new byte[4];
			fsa.read(a);
			row=a[1];col=a[0];px=a[3];py=a[2];
			map = new byte[row][col];
			for(byte i=0;i<row;i++)
				fsa.read(map[i]);
			int i=total-4-row*col;
			if(i>0){
				byte s[]=new byte[i];
				fsa.read(s);
				mapS=new int[i/3][3];
				int j,temp,l=0;
				while(l<i){
					j=(int)s[l++];
					if(j<0)j=j+256;
					temp=map[j/col][j%col];
					if(temp==2||temp==3){
						mapS[mapSNum][0]=j;
						mapS[mapSNum][1]=s[l++];
						mapS[mapSNum][2]=s[l++];
						mapSNum++;
					}
				}
			}
			fsa.close();
			fsa = null;
			System.gc();
		}catch (Exception e){
			System.out.println(e.toString());
			return;
		}
	}
	public void UserInfoRead(){
        try{
        	FileInputStream is =((Activity)mContext).openFileInput("UserInfo.txt");
        	level = (byte)is.read();
        	is.close();
        }catch(IOException e){
        	level = 0;
        }
	}
	public void UserInfoWrite(){
		try{
			FileOutputStream os = ((Activity)mContext).openFileOutput("UserInfo.txt", Activity.MODE_PRIVATE);  
			os.write(level);
			os.close();
		}catch(IOException e){
		}	
	}
}
