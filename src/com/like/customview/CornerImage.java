package com.like.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CornerImage extends ImageView{
	
	private Paint mPaint;

	public CornerImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init(){
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLACK);
		mPaint.setStrokeWidth(3.0f);
		mPaint.setStyle(Style.STROKE);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Bitmap originBitmap = ((BitmapDrawable)getDrawable()).getBitmap();
		if (originBitmap != null) {
			Bitmap bitmap = createCornerBitmap(canvas.getWidth(), canvas.getHeight(), originBitmap, 10);
			canvas.drawBitmap(bitmap, 0, 0, mPaint);
		} 
	}
	
	/**
    *
    * @param x 图像的宽度
    * @param y 图像的高度
    * @param image 源图片
    * @param outerRadiusRat 圆角的大小
    * @return 圆角图片
    */
   private Bitmap createCornerBitmap(int x, int y, Bitmap image, float outerRadiusRat) {
       //根据源文件新建一个darwable对象
       Drawable imageDrawable = new BitmapDrawable(image);

       // 新建一个新的输出图片
       Bitmap output = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
       Canvas canvas = new Canvas(output);

       // 新建一个矩形
       RectF outerRect = new RectF(0, 0, x, y);

       // 产生一个红色的圆角矩形
       Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
       paint.setColor(Color.RED);
       canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);
       
//       //绘制一个长方形，盖住下面的圆角
//       RectF bottomRectF = new RectF(0,20,x,y);
//       canvas.drawRect(bottomRectF, paint);

       //将源图片绘制到这个圆角矩形上
       paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
       imageDrawable.setBounds(0, 0, x, y);
       canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
       imageDrawable.draw(canvas);
       canvas.restore();

       return output;
   }

}
