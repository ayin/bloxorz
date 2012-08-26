package com.androidapk.ayin.bloxorz;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Graphics{
	Canvas mCanvas;
	public Graphics(Canvas canvas){
		mCanvas = canvas;
	}
	public void drawImage(Bitmap bitmap,int x,int y,int z){
		mCanvas.drawBitmap(bitmap, x, y, null);
	}
	public void drawRegion(Bitmap img, int x_src, int y_src, int width, int height, 
			int transform, int x_dest, int y_dest,int what)
	{
		mCanvas.save();
		int ix = 0, iy = 0; 
		switch (transform) { 
			case 0://TRANS_NONE: // 0 
				break; 
			case 90://TRANS_ROT90: // 90 
				mCanvas.rotate(90, x_dest, y_dest); 
				iy = height; 
				break; 
			case 180://TRANS_ROT180: // 180 
				mCanvas.rotate(180, x_dest, y_dest); 
				iy = height; 
				ix = width; 
				break; 
			case 270://TRANS_ROT270: // 270 
				mCanvas.rotate(270, x_dest, y_dest); 
				ix = width; 
				break; 
			case -1://TRANS_MIRROR: // M 
				mCanvas.scale(-1, 1, x_dest, y_dest);// ¾µÏñ 
				ix = width; 
				break; 
/*			case TRANS_MIRROR_ROT90: // M90 j2me<-->android 270 
		canvas.scale(-1, 1, x_dest, y_dest);// ¾µÏñ 
		canvas.rotate(270, x_dest, y_dest); 
		ix = width; 
		iy = height; 
		break; 
	case TRANS_MIRROR_ROT180: // M180 
		canvas.scale(-1, 1, x_dest, y_dest);// ¾µÏñ 
		canvas.rotate(180, x_dest, y_dest); 
		iy = height; 
		break; 
	case TRANS_MIRROR_ROT270: // M270 j2me<-->android 90 
		canvas.scale(-1, 1, x_dest, y_dest);// ¾µÏñ90 
		canvas.rotate(90, x_dest, y_dest); 
		break; 
*/			} 
		mCanvas.clipRect(x_dest - ix, y_dest - iy, x_dest - ix + width, y_dest - iy + height); 
		mCanvas.drawBitmap(img, x_dest - ix - x_src, y_dest - iy - y_src, null); 
		mCanvas.restore(); 
	} 
}