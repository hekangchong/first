package com.demo.activity;

import com.example.mancontroldemo.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
/**
 * 控制人物动画
 * @author Hekangchong
 *
 */
public class ControlManDemo extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}
	
	class MyView extends SurfaceView implements SurfaceHolder.Callback{
		
		Bitmap bitmap;
		//人物位置SCREEN_X,SCREEN_Y|人物宽高MAN_W,MAN_H|屏幕宽高
		int SCREEN_X,SCREEN_Y,MAN_W,MAN_H,SCREEN_W,SCREEN_H;
		int frame = 0;
		int[] frameMan = {0,1,2,3,4,5,6,7,8,9};
		//x轴坐标速度
		int speed_x = 10;
		//y轴坐标速度
		int speed_y_up = 15;
		int speed_y_down = 15;
		boolean isTag = false;
		boolean isDown = false;
		private SurfaceHolder mHolder;
		private MyThread myThread;
		@SuppressWarnings("deprecation")
		public MyView(Context context) {
			super(context);
			mHolder = this.getHolder();
			mHolder.addCallback(this);
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.man);
			SCREEN_X = 50;
			SCREEN_Y = 350;
			MAN_W = bitmap.getWidth()/10;
			MAN_H = bitmap.getHeight();
			myThread = new MyThread();
			WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
			
			SCREEN_W = wm.getDefaultDisplay().getWidth();
			System.out.println("SCREEN_W==" + SCREEN_W);
			SCREEN_H = wm.getDefaultDisplay().getHeight();
			System.out.println("SCREEN_H==" + SCREEN_H);
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			myThread.isRun = true;
			myThread.start();
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			myThread.isRun = false;
		}
		
		
		class MyThread extends Thread{
			private boolean isRun = true;
			
			
			@Override
			public void run() {
				super.run();
				while(isRun){
					synchronized (mHolder) {
						Canvas c = null;
						c = mHolder.lockCanvas(null);
						c.drawColor(Color.BLACK);
						Paint paint = new Paint();
						//屏幕上显示区域
						Rect rect_display = new Rect(SCREEN_X-MAN_W/2
								, SCREEN_Y-MAN_H/2, SCREEN_X+MAN_W/2
								, SCREEN_Y+MAN_H/2);
						if(frame<9){
							frame ++;
						}else{
							frame = 0;
						}
						
						if(SCREEN_X>SCREEN_W){
							SCREEN_X = 50;
						}else{
							SCREEN_X = SCREEN_X+speed_x;
						}
						
						if(isTag){
							SCREEN_Y = SCREEN_Y-speed_y_up;
							speed_y_up --;
							
							if(speed_y_up<=0){
								speed_y_up = 0;
								isTag = false;
								isDown = true;
								speed_y_down = 15;
								System.out.println("SCREEN_Y==" +SCREEN_Y);
							}
						}
						
						
						if(isDown){
							SCREEN_Y = SCREEN_Y+speed_y_down;
							speed_y_down --;
							if(speed_y_down<=0){
								speed_y_down = 0;
								isDown = false;
								System.out.println("SCREEN_Y==" +SCREEN_Y);
							}
						}
						
						// 截取整张人物图的10分之一
						Rect rect_clip = new Rect(MAN_W*frameMan[frame], 0, MAN_W*(frameMan[frame]+1), MAN_H);
						c.drawBitmap(bitmap, rect_clip, rect_display, paint);
						paint.setColor(Color.RED);
						c.drawLine(0, 350+MAN_H/2, SCREEN_W, 350+MAN_H/2, paint);
						mHolder.unlockCanvasAndPost(c);
					}
					
					try {
						sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			System.out.println("onTouchEvent");
//			if(speed_x>MAN_W/2){
//				speed_x = MAN_W/2;
//			}else{
//				speed_x = speed_x+5;
//			}
			speed_y_up = 15;
			isTag = true;
			
			return super.onTouchEvent(event);
		}
		
		
		
		
		
		
	}
	
}
