package com.androidapk.ayin.bloxorz;

import android.graphics.Canvas;

public abstract class layer
{
    int x;				//×ø±ê
    int y;				//
    int width;
    int height;	
    boolean visible; 	//¿É¼û
    layer(int width, int height)
    {
        visible = true;
        this.width = width;
        this.height = height;
    }
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public abstract void paint(Graphics canvas);
    public void paint(Canvas canvas){
    	paint(new Graphics(canvas));
    }
}
