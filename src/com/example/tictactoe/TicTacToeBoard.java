package com.example.tictactoe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TicTacToeBoard extends View{
	private Paint mBitmapPaint, mHighlightPaint, mLinePaint;
	private int tileSize;
	private int xWhitespace, yWhitespace;
	private Bitmap xMark, oMark;
	private int[][] boardData;
	private int curActionPointer = -1;
	private Rect drawingRect;
	private int cursorXPos, cursorYPos;
	
	public TicTacToeBoard(Context context, AttributeSet attributes)
	{
		super(context, attributes);
		mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mHighlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mHighlightPaint.setARGB(127, 255, 255, 0);
		mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mLinePaint.setARGB(255, 0, 0, 0);
		boardData = new int[9][9];
		drawingRect = new Rect(0, 0, 0, 0);
		cursorXPos = -1;
		cursorYPos = -1;
	}
	
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		tileSize = Math.min((w - getPaddingLeft() - getPaddingRight()) / 9, (h - getPaddingTop() - getPaddingBottom()) / 9);
		xWhitespace = (w - tileSize * 9) / 2;
		yWhitespace = (h - tileSize * 9) / 2;
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(getResources(), R.drawable.tictactoeo, options);
		
		options.inSampleSize = calculateInSampleSize(options, tileSize, tileSize);
		
		options.inJustDecodeBounds = false;
		oMark = BitmapFactory.decodeResource(getResources(), R.drawable.tictactoeo, options);
		xMark = BitmapFactory.decodeResource(getResources(), R.drawable.tictactoex, options);
	}
	
	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
	{
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth)
		{
			final int halfHeight = height / 2;
	        final int halfWidth = width / 2;
	
	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
	        	inSampleSize *= 2;
        	}
	    }
	
	    return inSampleSize;
	}
	
	protected void onDraw(Canvas canvas)
	{
		for (int i = 1; i < 9; i++)
		{
			canvas.drawLine((float)xWhitespace + i * tileSize, (float)yWhitespace, (float)xWhitespace + i * tileSize, (float)yWhitespace + tileSize * 9, mLinePaint);
			canvas.drawLine((float)xWhitespace, (float)yWhitespace + i * tileSize, (float)xWhitespace + tileSize * 9, (float)yWhitespace + i * tileSize, mLinePaint);
		}
		drawingRect.top = yWhitespace;
		drawingRect.bottom = yWhitespace + tileSize;
		for (int i = 0; i < 9; i++)
		{
			drawingRect.left = xWhitespace;
			drawingRect.right = xWhitespace + tileSize;
			for (int j = 0; j < 9; j++)
			{
				if (boardData[j][i] == 1)
					canvas.drawBitmap(xMark, null, drawingRect, mBitmapPaint);
				else if (boardData[j][i] == 2)
					canvas.drawBitmap(oMark, null, drawingRect, mBitmapPaint);
				drawingRect.left += tileSize;
				drawingRect.right += tileSize;
			}
			drawingRect.top += tileSize;
			drawingRect.bottom += tileSize;
		}
		if (cursorXPos >= 0 && cursorXPos < 9 && cursorYPos >= 0 && cursorYPos < 9)
		{
			drawingRect.left = xWhitespace + cursorXPos * tileSize;
			drawingRect.top = yWhitespace + cursorYPos * tileSize;
			drawingRect.right = xWhitespace + (cursorXPos + 1) * tileSize;
			drawingRect.bottom = yWhitespace + (cursorYPos + 1) * tileSize;
			canvas.drawRect(drawingRect, mHighlightPaint);
		}
	}
	
	public boolean onTouchEvent(MotionEvent e)
	{
		if (curActionPointer == -1 && e.getActionMasked() == MotionEvent.ACTION_DOWN)
			curActionPointer = e.getPointerId(0);
		if (curActionPointer == e.getPointerId(0))
		{
			translateToBoardPosition((int)e.getX(), (int)e.getY());
			if (e.getActionMasked() == MotionEvent.ACTION_UP)
			{
				if (cursorXPos >= 0 && cursorXPos < 9 && cursorYPos >= 0 && cursorYPos < 9)
				{
					boardData[cursorXPos][cursorYPos] += 1;
					boardData[cursorXPos][cursorYPos] %= 3;
				}
				cursorXPos = -1;
				cursorYPos = -1;
				curActionPointer = -1;
			}
			invalidate();
		}
		super.onTouchEvent(e);
		return true;
	}
	
	private void translateToBoardPosition(int x, int y)
	{
		cursorXPos = (x - xWhitespace) / tileSize;
		cursorYPos = (y - yWhitespace) / tileSize;
	}
}
