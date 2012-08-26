package com.androidapk.ayin.bloxorz;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class DirecterView extends View{
	protected View mView;
	Context mContext;
	public DirecterView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}
	public DirecterView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}
	Button mBtnUp,mBtnDown,mBtnLeft,mBtnRight,mBtnChange;
	public DirecterView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		mScale = (data.height>>2)/(float)90.0;
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = inflater.inflate(R.layout.menu, null);
		mView.measure(MeasureSpec.makeMeasureSpec(156,MeasureSpec.EXACTLY), 
                MeasureSpec.makeMeasureSpec(90, MeasureSpec.EXACTLY));
		mView.layout(0, 0, mView.getMeasuredWidth(), mView.getMeasuredHeight()); 
		//mView.setDrawingCacheEnabled(true); 
		//mView.buildDrawingCache(true);
		mBtnUp = (Button)mView.findViewById(R.id.btnUp);
		mBtnDown = (Button)mView.findViewById(R.id.btnDown);
		mBtnLeft = (Button)mView.findViewById(R.id.btnLeft);
		mBtnRight = (Button)mView.findViewById(R.id.btnRight);
		mBtnChange = (Button)mView.findViewById(R.id.btnChange);

	}
	Matrix mMatrix = new Matrix();
	float mScale = (float)1;
	protected void onDraw(Canvas canvas) {
		mMatrix.setScale(mScale, mScale);
		mMatrix.postConcat(canvas.getMatrix());
		canvas.setMatrix(mMatrix);
		mView.draw(canvas);
	}
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec){
		setMeasuredDimension((int)(156*mScale),(int)(90*mScale));
		//setMeasuredDimension(MeasureSpec.makeMeasureSpec((int)(156*mScale),MeasureSpec.EXACTLY), 
         //       MeasureSpec.makeMeasureSpec((int)(90*mScale), MeasureSpec.EXACTLY));
	}
	boolean mMoveFlag = false;
	Button btn = null;
	int Res1 = R.drawable.arrow_up1, Res2 = R.drawable.arrow_up;
	float saveX,saveY;
	public boolean onTouchEvent(MotionEvent event){
		super.onTouchEvent(event);
		switch(event.getAction()) {  
        	case MotionEvent.ACTION_DOWN:
        		saveX = event.getX();saveY = event.getY();
        		mMoveFlag = false;
        		if(event.getX() < 30*mScale && event.getY() < 90*mScale){
        			btn = mBtnLeft; Res1 = R.drawable.arrow_left1; Res2 = R.drawable.arrow_left;
        		}else if(event.getX() > 126*mScale && event.getX() < 156*mScale && event.getY() < 90*mScale){
        			btn = mBtnRight; Res1 = R.drawable.arrow_right1; Res2 = R.drawable.arrow_right;
        		}else if(event.getX() > 30*mScale && event.getX() < 126*mScale && event.getY() < 29*mScale){
        			btn = mBtnUp; Res1 = R.drawable.arrow_up1; Res2 = R.drawable.arrow_up;
        		}else if(event.getX() > 30*mScale && event.getX() < 126*mScale && event.getY() > 59*mScale && event.getY() < 90*mScale){
        			btn = mBtnDown; Res1 = R.drawable.arrow_down1; Res2 = R.drawable.arrow_down;
        		}else if(event.getX() > 30*mScale && event.getX() < 126*mScale && event.getY() > 29*mScale && event.getY() < 59*mScale){
        			btn = mBtnChange; Res1 = R.drawable.select1; Res2 = R.drawable.select;
        		}else{
        			return true;
        		}
        		btn.setBackgroundResource(Res1);
        		this.invalidate();
        		break;  
        	case MotionEvent.ACTION_MOVE://手点击时判断一定的范围
        		if((event.getX()-saveX)*(event.getX()-saveX)+(event.getY()-saveY)*(event.getY()-saveY)>200)
        			mMoveFlag = true;
        		break;
        	case MotionEvent.ACTION_UP: 
        		if(btn == null)return true;
        		btn.setBackgroundResource(Res2);
        		this.invalidate();
        		if(!mMoveFlag){
        			((BloxorzActivity)mContext).BtnOnClick(btn);
        		}
        		mMoveFlag = false;
        		break;
        	default:
        		break;
		}
		return true;
	}
}