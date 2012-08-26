package com.androidapk.ayin.bloxorz;


import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewTargeting.RunMode;
import com.adview.AdViewTargeting.UpdateMode;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;

public class MyAd{
	Context mContext;
	int addPoint = 0;
	boolean pointflag = false;
	public MyAd(Context context){
		this.mContext = context;
		LinearLayout layout = (LinearLayout)((Activity)mContext).findViewById(R.id.adLayout); 
		
	/*	AdView adView = new AdView((Activity)mContext, AdSize.BANNER, "a1502352a097930");
        layout.addView(adView);
        // Initiate a generic request to load it with an ad
        AdRequest request = new AdRequest();
        adView.loadAd(request);
       // request.setTesting(true);
    */    
        /* 下面两行仅仅用于调试，发布前注释掉 */ 
		//AdViewTargeting.setRunMode(RunMode.TEST); 
		//AdViewTargeting.setUpdateMode(UpdateMode.EVERYTIME); 
		AdViewLayout adViewLayout = new AdViewLayout((Activity)mContext, "SDK2012130901080371ml3ixqsah7fv8");
		
		//adViewLayout.setAdViewInterface(this); 
		//if(adViewLayout.getHeight()==0){layout.setVisibility(View.GONE);return;}
		
		layout.addView(adViewLayout); 
		layout.invalidate(); 
	}
}
