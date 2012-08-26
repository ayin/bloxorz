package com.androidapk.ayin.bloxorz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MapLayer extends layer{
	Bitmap imgd;
	Context mContext;
	public MapLayer(Context context){
		super(500,500);
		mContext = context;
		imgd = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.d,null);
	}
	public void paint(Graphics g) {
		byte a=(byte)(data.col-1);
		byte b=data.row;
		for(byte i=a;i>=0;i--)
			for(byte j=0;j<b;j++){
				g.drawRegion(imgd,30*data.map[j][i],0,30,20,0,250+j*7-(a-i)*22+x,11*j+4*(a-i)+y,0);
			}
	}
}
