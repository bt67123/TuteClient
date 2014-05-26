package cn.edu.tute.tuteclient.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class EraseView extends View {
				
	private boolean isMove = false;
	private Bitmap bitmap = null;
	private Bitmap frontBitmap = null;
	private Path path;
	private Canvas mCanvas;
	private Paint paint;

	public EraseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
	    if (mCanvas == null) {
		    EraseBitmp();
	    } 
		canvas.drawBitmap(bitmap, 0, 0, null);	
		mCanvas.drawPath(path,paint);//绘制一个路径
		super.onDraw(canvas);
	}
	
	public void EraseBitmp() {
		
		bitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_4444);
		
		frontBitmap = CreateBitmap(Color.GRAY,getWidth(),getHeight());//生产灰色图片

	//设置画笔的样式
		paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);//设置画笔的风格：空心
		paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));//设置图片交叉时的显示模式
		paint.setAntiAlias(true);//防据齿
		paint.setDither(true);//防抖动
		paint.setStrokeJoin(Paint.Join.ROUND);//设置结合处的样子，ROUND为圆弧
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(20);//设置描边宽度
		
		path = new Path();

		mCanvas = new Canvas(bitmap);//设置一个带图片的画布
		mCanvas.drawBitmap(frontBitmap, 0, 0,null);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		float ax = event.getX();
		float ay = event.getY();
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			isMove = false;
			path.reset();
			path.moveTo(ax, ay);
			invalidate();
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			isMove = true;
			path.lineTo(ax,ay);
			invalidate();
			return true;
		}
		return super.onTouchEvent(event);
	}
	
	public  Bitmap CreateBitmap(int color,int width, int height) {
		int[] rgb = new int [width * height];
		
		for (int i=0;i<rgb.length;i++) {
			rgb[i] = color;
		}
		return Bitmap.createBitmap(rgb, width, height,Config.ARGB_8888);
	}
}
