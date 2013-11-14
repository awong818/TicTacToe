package com.example.tictactoe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
//import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TicTacToeBoard extends View
{
	private Paint mBitmapPaint, mHighlightPaint, mLinePaint;
	private int tileSize;
	private int xWhitespace, yWhitespace;
	private Bitmap xMark, oMark;
	private int[][] boardData;
	private int curActionPointer = -1;
	private Rect drawingRect;
	private int cursorXPos, cursorYPos;
	
	private int playerturn = 1;
	private int rLow = 0, rHigh = 9, cLow = 0, cHigh = 9; // lower and upper bounds for rows and columns
	
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
		// Determine size of tile
		tileSize = Math.min((w - getPaddingLeft() - getPaddingRight()) / 9, (h - getPaddingTop() - getPaddingBottom()) / 9);
		xWhitespace = (w - tileSize * 9) / 2;
		yWhitespace = (h - tileSize * 9) / 2;
		
		// Resize oMark and xMark bitmap size to the smallest possible
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
		// Draw grid
		for (int i = 1; i < 9; i++)
		{
			canvas.drawLine((float)xWhitespace + i * tileSize, (float)yWhitespace, (float)xWhitespace + i * tileSize, (float)yWhitespace + tileSize * 9, mLinePaint);
			canvas.drawLine((float)xWhitespace, (float)yWhitespace + i * tileSize, (float)xWhitespace + tileSize * 9, (float)yWhitespace + i * tileSize, mLinePaint);
		}
		
		// Draw X and O marks on grid as appropriate
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
		
		// Highlight box where the pointer is located
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
		// If there is no current action, set current action to the one described in this event
		if (curActionPointer == -1 && e.getActionMasked() == MotionEvent.ACTION_DOWN)
			curActionPointer = e.getPointerId(0);
		
		// Only process if the event describes the current action of interest
		if (curActionPointer == e.getPointerId(0))
		{
			translateToBoardPosition((int)e.getX(), (int)e.getY());
			// On release
			if (e.getActionMasked() == MotionEvent.ACTION_UP)
			{
				if (cursorXPos >= 0 && cursorXPos < 9 && cursorYPos >= 0 && cursorYPos < 9)
				{
					if (moveIsValid(cursorXPos, cursorYPos))
					{
						if (playerturn == 1)
						{
							boardData[cursorXPos][cursorYPos] = 1;
							getBoundsForNextMove(cursorXPos, cursorYPos);
							playerturn = 2;
						}
						else
						{
							boardData[cursorXPos][cursorYPos] = 2;
							getBoundsForNextMove(cursorXPos, cursorYPos);
							playerturn = 1;
						}
					}
					//boardData[cursorXPos][cursorYPos] %= 3;
				}
				// Reset current action to null
				cursorXPos = -1;
				cursorYPos = -1;
				curActionPointer = -1;
			}
			invalidate();
		}
		super.onTouchEvent(e);
		return true;
	}
	
	private void getBoundsForNextMove(int row, int col)
	{
		rLow = row % 3 * 3;
		rHigh = row % 3 * 3 + 2;
		cLow = col % 3 * 3;
		cHigh = col % 3 * 3 + 2;
	}
	
	private boolean moveIsValid(int row, int col)
	{
		return row >= rLow && row <= rHigh && col >= cLow && col <= cHigh;
	}
	
	
	private void translateToBoardPosition(int x, int y)
	{
		cursorXPos = (x - xWhitespace) / tileSize;
		cursorYPos = (y - yWhitespace) / tileSize;
	}
}
