package com.androidapk.ayin.bloxorz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class BloxorzActivity extends Activity {
	GameView mGameView;
	boolean mMusic = false;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
				WindowManager.LayoutParams. FLAG_FULLSCREEN);
        DisplayMetrics dm=new DisplayMetrics();  
	    getWindowManager().getDefaultDisplay().getMetrics(dm); 
	    data.width = dm.widthPixels;
	    data.height = dm.heightPixels;
        new data(this).UserInfoRead();
        setContentView(R.layout.main);
        mGameView = (GameView)findViewById(R.id.IdGameView);
        new MyAd(this);
        CheckBox cb = (CheckBox)findViewById(R.id.MusicOn);
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
				mMusic = isChecked;
			}
		});
		setTextView();
    }
	class MusicThread extends Thread{
		MediaPlayer mp;
		int ResID;
		public MusicThread(int ResID){
			this.ResID = ResID;
		}
		@Override
		public void run() {
			mp = MediaPlayer.create(BloxorzActivity.this,ResID);
			mp.start();
			super.run();
		}
	}
	public void PlayMusic(int ResID){
		if(!mMusic)return;
		new MusicThread(ResID).start();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			this.showDialog(0);
			return true;
		}else{		
			return super.onKeyDown(keyCode, event);
		}
	}
	public void BtnOnClick(View view){
		int mDir=0,flag1=0;
		if(view.getId()==R.id.btnChange)
			mGameView.gameChange();
		else if(view.getId()==R.id.btnUp){
			mDir = 2; flag1 = 1;
		}else if(view.getId()==R.id.btnDown){
			mDir = 8; flag1 = 1;
		}else if(view.getId()==R.id.btnLeft){
			mDir = 4; flag1 = 1;
		}else if(view.getId()==R.id.btnRight){
			mDir = 6; flag1 = 1;
		}else if(view.getId() == R.id.gameInfo){
			this.showDialog(1);
		}
		if(flag1 == 1&&mGameView.mKeyFlag){
			mGameView.gameMove(mDir);
		}
	}
	 protected Dialog onCreateDialog (int id){
			AlertDialog.Builder builder = new Builder(this);
	    	String str = null;
	    	if(id==0)str="确定退出程序";
	    	else if(id==1)
	    		str = "基本规则：使用导航键或滑动屏幕上下左右移动立方块使之掉进地图上的黑洞中即可完成该关，如果立方块移到空白处或立放在红色地板上则失败。\n"+
	    			"特殊规则：O和X标记的地板可控制其他某处地板开启或闭合，()标记的地板使立方块分为两段，通过中间键可在两块之间交换。游戏共33关。\n"+
	    			"制作者：ayin\nEMAIL:ayin1989dy@qq.com\n游戏来源：miniclip.com";
	    	switch(id){
	    		case 0:
	    			builder.setMessage(str) 
	    			.setCancelable(false) 
	    			.setPositiveButton("确定", new DialogInterface.OnClickListener() { 
	    				public void onClick(DialogInterface dialog, int id) {
	    					onDestroy();
	    	        		System.exit(0);
	    				} 
	    			}) 
	    			.setNegativeButton("取消", new DialogInterface.OnClickListener() { 
	    				public void onClick(DialogInterface dialog, int id) { 
	    				} 
	    			});
	    			break;
	    		case 1:
	    			builder.setMessage(str) 
	    			.setCancelable(false) 
	    			.setPositiveButton("确定", new DialogInterface.OnClickListener() { 
	    				public void onClick(DialogInterface dialog, int id) {
	    				} 
	    			});
	    			break;
	    		default:break;
	    	}
	    	AlertDialog alert = builder.create();
	    	return alert;
	 }
	 public void setTextView(){
		 TextView mTextView = (TextView)findViewById(R.id.IdTextView);
		 if(mTextView!=null)mTextView.setText("第"+data.level+"/33关");
	 }
}