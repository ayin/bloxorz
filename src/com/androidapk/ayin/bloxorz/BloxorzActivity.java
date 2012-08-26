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
	    	if(id==0)str="ȷ���˳�����";
	    	else if(id==1)
	    		str = "��������ʹ�õ������򻬶���Ļ���������ƶ�������ʹ֮������ͼ�ϵĺڶ��м�����ɸùأ�����������Ƶ��հ״��������ں�ɫ�ذ�����ʧ�ܡ�\n"+
	    			"�������O��X��ǵĵذ�ɿ�������ĳ���ذ忪����պϣ�()��ǵĵذ�ʹ�������Ϊ���Σ�ͨ���м����������֮�佻������Ϸ��33�ء�\n"+
	    			"�����ߣ�ayin\nEMAIL:ayin1989dy@qq.com\n��Ϸ��Դ��miniclip.com";
	    	switch(id){
	    		case 0:
	    			builder.setMessage(str) 
	    			.setCancelable(false) 
	    			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { 
	    				public void onClick(DialogInterface dialog, int id) {
	    					onDestroy();
	    	        		System.exit(0);
	    				} 
	    			}) 
	    			.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { 
	    				public void onClick(DialogInterface dialog, int id) { 
	    				} 
	    			});
	    			break;
	    		case 1:
	    			builder.setMessage(str) 
	    			.setCancelable(false) 
	    			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { 
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
		 if(mTextView!=null)mTextView.setText("��"+data.level+"/33��");
	 }
}