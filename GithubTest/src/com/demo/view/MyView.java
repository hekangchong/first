package com.demo.view;

import com.example.githubtest.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
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
	 * 线程
	 * @author Hekangchong
	 *
	 */
	class MyThread extends Thread{
		SurfaceHolder holder;
		private boolean isRun;
		int i = 0;
		int rota = 0;
		Bitmap bitmapOfRota,bitmapRound,bitmapMan;
		int width,height;
		
		//人物的控制点坐标和宽高  
	    private int X,Y,W,H;
	    //角色移动控制帧  
	    private int frame=0;
	    //人物播放的帧动画
	    private int[] frameMan={0,1,2,3,4,5,6,7,8,9}; 
	    
	    
		public MyThread(SurfaceHolder holder){
			isRun = true;
			this.holder = holder;
			bitmapOfRota = BitmapFactory.decodeResource(getResources(), R.drawable.rota);
			bitmapRound = BitmapFactory.decodeResource(getResources(), R.drawable.round);
			bitmapMan = BitmapFactory.decodeResource(getResources(), R.drawable.man);
			width = bitmapOfRota.getWidth()/2;
			height = bitmapOfRota.getHeight()/2;
			X = 200;
			Y = 200;
			W = bitmapMan.getWidth()/10;
			H = bitmapMan.getHeight();
		}

		@Override
		public void run() {
			super.run();
			while(isRun){
				synchronized (holder) {
					Canvas canvas = null;
					canvas = mHolder.lockCanvas(null);
					canvas.drawColor(Color.WHITE);
					i ++;
					drawPic(canvas,i);
					rota = rota + 12;
					drawRota(canvas,rota,width,height,bitmapOfRota,bitmapRound);
					if(frame<9){
						frame ++;
					}else{
						frame = 0;
					}
					rawMan(canvas,bitmapMan,X,Y,W,H,frameMan,frame);
					mHolder.unlockCanvasAndPost(canvas);
				}
				
				try {
					sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	/**
	 * 执行时间
	 * @param canvas
	 * @param i
	 */
	private void drawPic(Canvas canvas,int i){
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextSize(30f);
		canvas.drawText("执行时间"+i+"s", 100, 100, paint);
	}
	/**
	 * 旋转动画
	 * @param canvas
	 * @param rota 旋转贞
	 * @param width
	 * @param height
	 * @param bitmap
	 */
	private void drawRota(Canvas canvas,int rota,int width,int height,Bitmap...bitmap){
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		//以图片中心为圆心，角度为rota%360转动
		matrix.postRotate(rota%360, width, height);
		System.out.println("width=" + width+"height=" + height);
		//位移方向在距平幕上方边框300，屏幕左边框300的距离
		matrix.postTranslate(300, 300);
		canvas.drawBitmap(bitmap[0], matrix, paint);
		
		Matrix matrix1 = new Matrix();
		matrix1.postTranslate(300, 300);
		canvas.drawBitmap(bitmap[1], matrix1, paint);
	}
	/**
	 * 人物动画
	 * @param canvas 画布
	 * @param bitmapMan 人物图片
	 * @param X 显示x轴上的位置
	 * @param Y 显示y轴上的位置
	 * @param W 人物宽
	 * @param H 人物高
	 * @param frameMan 人物多少个图片
	 * @param frame 控制贞
	 */
	private void rawMan(Canvas canvas,Bitmap bitmapMan,int X,int Y,int W,int H,int[] frameMan,int frame){
		Paint paint = new Paint();
		//屏幕显示区域 
        Rect rect_dst=new Rect(X-W/2, Y-H/2, X+W/2, Y+H/2); 
        //图片截取区域 
        Rect rect_src=new Rect(frameMan[frame]*W, 0, (frameMan[frame]+1)*W, H); 
        canvas.drawBitmap(bitmapMan,rect_src,rect_dst,paint);
	}

}
