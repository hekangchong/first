package com.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/**
 * 
 * @author Hekangchong
 *
 */
public class MyView extends SurfaceView implements SurfaceHolder.Callback{
	
	MyThread myThread;
	SurfaceHolder mHolder;
	public MyView(Context context) {
		super(context);
		mHolder = this.getHolder();
		mHolder.addCallback(this);
		myThread = new MyThread(mHolder);
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
	
	/**
	 * 画图线程
	 * @author Hekangchong
	 *
	 */
	class MyThread extends Thread{
		SurfaceHolder holder;
		private boolean isRun;
		int i = 0;
		public MyThread(SurfaceHolder holder){
			isRun = true;
			this.holder = holder;
		}

		@Override
		public void run() {
			super.run();
			while(isRun){
				synchronized (holder) {
					Canvas canvas = null;
					i ++;
					System.out.println(i);
					drawPic(canvas,i);
				}
				
				try {
					sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	/**
	 * 画图
	 */
	private void drawPic(Canvas canvas,int i){
		canvas = mHolder.lockCanvas(null);
		canvas.drawColor(Color.BLACK);
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextSize(30f);
		canvas.drawText("执行时间："+i+"s", 100, 100, paint);
		mHolder.unlockCanvasAndPost(canvas);
	}

}
