package com.example.tictactoe;

import java.util.ArrayList;
import java.util.Random;

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
//import android.util.Log;
import android.widget.TextView;



public class TicTacToeBoard extends View
{
	private Paint mBitmapPaint, mHighlightPaint, mLinePaint, mGreyOutPaint;

	private int tileSize;
	private int xWhitespace, yWhitespace;
	private Bitmap xMark, oMark;
	private int[][] boardData;
	private int[][] largeBoardData;
	private int curActionPointer = -1;
	private Rect drawingRect;
	private int cursorXPos, cursorYPos;
	
	private int playerturn = 1;
	private int rLow = 0, rHigh = 9, cLow = 0, cHigh = 9; // lower and upper bounds for rows and columns
	private int tmpRLow = 0, tmpRHigh = 9, tmpCLow = 0, tmpCHigh = 9; // for CPU move
	
	ArrayList <Integer> moveHistory = new ArrayList <Integer>();
	ArrayList<ViewWasTouchedListener> listeners = new ArrayList<ViewWasTouchedListener>();
	
	public void setWasTouchedListener(ViewWasTouchedListener listener)
	{
	    listeners.add(listener);
	}
	
	public TicTacToeBoard(Context context, AttributeSet attributes)
	{
		super(context, attributes);
		mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mHighlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mHighlightPaint.setARGB(127, 255, 255, 0);
		mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mLinePaint.setARGB(255, 0, 0, 0);
		mGreyOutPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mGreyOutPaint.setARGB(127, 0, 0, 0);
		boardData = new int[9][9];
		largeBoardData = new int[3][3];
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
		
		options.inSampleSize = calculateInSampleSize(options, tileSize * 3, tileSize * 3);
		
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
		// Draw large grid
		for (int i = 1; i < 3; i++)
		{
			// Vertical lines
			canvas.drawLine(xWhitespace + i * tileSize * 3, yWhitespace, xWhitespace + i * tileSize * 3, yWhitespace + tileSize * 9, mLinePaint);
			// Horizontal lines
			canvas.drawLine(xWhitespace, yWhitespace + i * tileSize * 3, xWhitespace + tileSize * 9, yWhitespace + i * tileSize * 3, mLinePaint);
		}
		
		// Draw small grids
		float fractionTile = tileSize * 0.15f;
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				for (int k = 1; k < 3; k++)
				{
					// Vertical lines
					canvas.drawLine(xWhitespace + (k + i * 3) * tileSize, yWhitespace + fractionTile + j * 3 * tileSize, xWhitespace + (k + i * 3) * tileSize, yWhitespace - fractionTile + (j * 3 + 3) * tileSize, mLinePaint);
					// Horizontal lines
					canvas.drawLine(xWhitespace + fractionTile + i * 3 * tileSize, yWhitespace + (k + j * 3) * tileSize, xWhitespace - fractionTile + (i * 3 + 3) * tileSize, yWhitespace + (k + j * 3) * tileSize, mLinePaint);
				}
			}
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
				if (largeBoardData[j / 3][i / 3] == 0)
				{
					if (boardData[j][i] == 1)
						canvas.drawBitmap(xMark, null, drawingRect, mBitmapPaint);
					else if (boardData[j][i] == -1)
						canvas.drawBitmap(oMark, null, drawingRect, mBitmapPaint);
				}
				drawingRect.left += tileSize;
				drawingRect.right += tileSize;
			}
			drawingRect.top += tileSize;
			drawingRect.bottom += tileSize;
		}
		
		// Draw large X and Os and grey out all invalid spaces
		drawingRect.top = yWhitespace;
		drawingRect.bottom = yWhitespace + tileSize * 3;
		for (int i = 0; i < 3; i++)
		{
			drawingRect.left = xWhitespace;
			drawingRect.right = xWhitespace + tileSize * 3;
			for (int j = 0; j < 3; j++)
			{
				if (largeBoardData[j][i] == 1)
					canvas.drawBitmap(xMark, null, drawingRect,  mBitmapPaint);
				else if (largeBoardData[j][i] == -1)
					canvas.drawBitmap(oMark,  null,  drawingRect, mBitmapPaint);
				if (j * 3 < cLow || j * 3 > cHigh || i * 3 < rLow || i * 3 > rHigh)
					canvas.drawRect(drawingRect, mGreyOutPaint);
				drawingRect.left += tileSize * 3;
				drawingRect.right += tileSize * 3;
			}
			drawingRect.top += tileSize * 3;
			drawingRect.bottom += tileSize * 3;
		}
		
		// Highlight box where the pointer is located
		if (moveIsValid(cursorYPos, cursorXPos) && cursorXPos >= 0 && cursorXPos < 9 && cursorYPos >= 0 && cursorYPos < 9)
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
				curActionPointer = -1;
			invalidate();
			for (ViewWasTouchedListener listener : listeners)
				listener.onViewChanged(moveIsValid(cursorYPos, cursorXPos));
		}
		super.onTouchEvent(e);
		return true;
	}
	
	private void getBoundsForNextMove(int row, int col, boolean isTemp)
	{
		if(!isTemp)
		{
			rLow = row % 3 * 3;
			rHigh = row % 3 * 3 + 2;
			cLow = col % 3 * 3;
			cHigh = col % 3 * 3 + 2;
		}
		else
		{
			tmpRLow = row % 3 * 3;
			tmpRHigh = row % 3 * 3 + 2;
			tmpCLow = col % 3 * 3;
			tmpCHigh = col % 3 * 3 + 2;
		}
		if (largeBoardData[cLow / 3][rLow / 3] != 0 && !isTemp)
		{
			rLow = 0;
			rHigh = 9;
			cLow = 0;
			cHigh = 9;
		}
		else if(largeBoardData[tmpCLow/3][tmpRLow/3] != 0 && isTemp)
		{
			tmpRLow = 0;
			tmpRHigh = 8;
			tmpCLow = 0;
			tmpCHigh = 8;
		}
	}
	
	
	private boolean moveIsValid(int row, int col)
	{
		if (row < 0 || row >= 9 || col < 0 || col >= 9)
			return false;
		if (largeBoardData[col / 3][row / 3] != 0)
			return false;
		return row >= rLow && row <= rHigh && col >= cLow && col <= cHigh && boardData[col][row] == 0;
	}
	
	
	private void translateToBoardPosition(int x, int y)
	{
		cursorXPos = (x - xWhitespace) / tileSize;
		cursorYPos = (y - yWhitespace) / tileSize;
	}
	
	public boolean confirmMove()
	{
		// Check for valid move
		if (!(cursorXPos >= 0 && cursorXPos < 9 && cursorYPos >= 0 && cursorYPos < 9 && moveIsValid(cursorYPos, cursorXPos)))
			return false;
		
		boardData[cursorXPos][cursorYPos] = playerturn;
		moveHistory.add((cursorXPos*9+cursorYPos));
		for (ViewWasTouchedListener listener:listeners)
			listener.onFinishMove();
		playerturn *= -1;
		largeBoardData[cursorXPos / 3][cursorYPos / 3] = isCompleted(cursorYPos / 3, cursorXPos / 3, boardData, false);

		getBoundsForNextMove(cursorYPos, cursorXPos, false);
		cursorXPos = -1;
		cursorYPos = -1;
		if(isWin()!=0) // if a player won
		{
			cLow = -1; cHigh = -1; rLow = -1; rHigh = -1;
			for (ViewWasTouchedListener listener:listeners)
				listener.onWin();
		}
		invalidate();
		for (ViewWasTouchedListener listener : listeners)
			listener.onViewChanged(false);
		return true;
	}
	
	// Returns 0 if not completed. Returns 1 if player one completed. Returns -1 if player 2 completed.
	public int isCompleted(int row, int col, int[][] board, boolean partialMatch)
	{
		// Check rows
		for (int i = 0; i < 3; i++)
		{
			int sum = 0;
			for (int j = 0; j < 3; j++)
				sum += board[col * 3 + j][row * 3 + i];
			if(partialMatch && sum == -2)
			{
				return -2;
			}
			if (sum == 3)
				return 1;
			if (sum == -3)
				return -1;
		}	
	
		// Check columns
		for (int i = 0; i < 3; i++)
		{
			int sum = 0;
			for (int j = 0; j < 3; j++)
				sum += board[col * 3 + i][row * 3 + j];
			if(partialMatch && sum == -2)
			{
				return -2;
			}
			if (sum == 3)
				return 1;
			if (sum == -3)
				return -1;
		}	
		
		// Check diagonals
		int sum = 0;
		for (int i = 0; i < 3; i++)
			sum += board[col * 3 + i][row * 3 + i];
		if(partialMatch && sum == -2)
		{
			return -2;
		}
		if (sum == 3)
			return 1;
		if (sum == -3)
			return -1;
		
		sum = 0;
		for (int i = 0; i < 3; i++)
			sum += board[col * 3 + i][row * 3 + 2 - i];
		if(partialMatch && sum == -2)
		{
			return -2;
		}
		if (sum == 3)
			return 1;
		if (sum == -3)
			return -1;
		
		// All checks failed
		return 0;
	}
	
	public int isWin()
	{
		return isCompleted(0,0, largeBoardData, false);
	}
	
	public void CPUmove(int difficulty)
	{
		Random rand =  new Random();
		int[] possSpots = new int[81];
		int index = 0;
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				if(moveIsValid(i, j))
				{
					possSpots[index] = i*9+j;
					index++;
				}
			}
		}
		
		// Medium and hard only processing
		// Weeds out any moves that don't result in an immediate 3 in a row
		if (difficulty >= 1)
		{
			int newIndex = 0;
			int[] newPossSpots = new int[index];
			
			int blockWinIndex = 0;
			int[] blockWin = new int[index];
			for (int i = 0; i < index; i++)
			{
				int testRow = possSpots[i] / 9;
				int testCol = possSpots[i] % 9;
				boardData[testCol][testRow] = -1;
				if (isCompleted(testRow / 3, testCol / 3, boardData, false) == -1)
				{
					newPossSpots[newIndex] = possSpots[i];
					newIndex++;
				}
				boardData[testCol][testRow] = 0;
			}
			// If there were moves to weed out, replace old data with new data
			if (newIndex > 0)
			{
				possSpots = newPossSpots;
				index = newIndex;
			}
			else
			{
				// if there are no possible moves to make AI win square, look for moves that will prevent opponents win
				for (int i = 0; i < index; i++) 
				{
					int testRow = possSpots[i] / 9;
					int testCol = possSpots[i] % 9;
					boardData[testCol][testRow] = 1;
					if (isCompleted(testRow / 3, testCol / 3, boardData, false) == 1)
					{
						blockWin[blockWinIndex] = possSpots[i];
						blockWinIndex++;
					}
					boardData[testCol][testRow] = 0;
				}
				
				if(blockWinIndex > 0)
				{
					possSpots = blockWin;
					index = blockWinIndex;
				}
			}
			
			// Hard only processing
			if (difficulty >= 2)
			{
				int noFutureWinIndex = 0;
				int [] noFutureWin = new int[index];
				
				if(newIndex == 0 && blockWinIndex == 0) // if no possible spots for winning a square or blocking a win
				{
					for (int i = 0; i < index; i++) 
					{
						int testRow = possSpots[i] / 9;
						int testCol = possSpots[i] % 9;
						getBoundsForNextMove(testRow, testCol, true);
					//	boardData[testCol][testRow] = 1;
						
						boolean badMove = false;
						for(int r = tmpRLow; r <= tmpRHigh; r++) // gets every possible move and sees if 
							//opponent can win square on next move
						{
							for(int c = tmpCLow; c <= tmpCHigh; c++)
							{
								if(boardData[c][r] == 0)
									boardData[c][r] = 1;
								else
									continue;
								if (isCompleted(r / 3, c / 3, boardData, false) == 1)
								{
									badMove = true;
								}
								boardData[c][r] = 0;
							}
							if(badMove)
								break;
						}
						
						if(!badMove) // adds moves that will not allow opponent to win 1 turn in future
						{
							noFutureWin[noFutureWinIndex] = possSpots[i];
							noFutureWinIndex++;
						}
					//	boardData[testCol][testRow] = 0;
					}
					if(noFutureWinIndex > 0)
					{
						index = noFutureWinIndex;
						possSpots = noFutureWin;
					}
					
					int noFutureBlockIndex = 0;
					int [] noFutureBlock = new int[index];
					for (int i = 0; i < index; i++) 
					{
						int testRow = possSpots[i] / 9;
						int testCol = possSpots[i] % 9;
						getBoundsForNextMove(testRow, testCol, true);
					//	boardData[testCol][testRow] = 1;
						
						boolean badMove = false;
						for(int r = tmpRLow; r <= tmpRHigh; r++) // gets every possible move and sees if 
							//opponent can win square on next move
						{
							for(int c = tmpCLow; c <= tmpCHigh; c++)
							{
								if(boardData[c][r] == 0)
									boardData[c][r] = 1;
								else
									continue;
								if (isCompleted(r / 3, c / 3, boardData, false) == 1)
								{
									badMove = true;
								}
								boardData[c][r] = 0;
							}
							if(badMove)
								break;
						}
						
						if(!badMove) // adds moves that will not allow opponent to win 1 turn in future
						{
							noFutureBlock[noFutureBlockIndex] = possSpots[i];
							noFutureBlockIndex++;
						}
					}
					
					if(noFutureBlockIndex > 0)
					{
						index = noFutureBlockIndex;
						possSpots = noFutureBlock;
					}
				}
				
				int[] partialMatch = new int[index];
				int partialMatchIndex = 0;
				
				for(int i = 0; i < index; i++) // attempts to make moves that will result in two in a row O's
				{
					int testRow = possSpots[i] / 9;
					int testCol = possSpots[i] % 9;
					boardData[testCol][testRow] = 1;
					if (isCompleted(testRow / 3, testCol / 3, boardData, true) == -2)
					{
						partialMatch[partialMatchIndex] = possSpots[i];
						partialMatchIndex++;
					}
					boardData[testCol][testRow] = 0;
				}
				
				if(partialMatchIndex > 0)
				{
					index = partialMatchIndex;
					possSpots = partialMatch;
				}
				
				int[] centerMove = new int[index];
				int centerMoveIndex = 0;
				for(int i = 0; i < index; i++)// prioritize center square over any other square
				{
					int testRow = possSpots[i] / 9;
					int testCol = possSpots[i] % 9;
					
					if(testRow%3 == 1 && testCol%3 == 1)
					{
						centerMove[centerMoveIndex] = possSpots[i];
						centerMoveIndex++;
					}
				}
				
				if(centerMoveIndex > 0)
				{
					index = centerMoveIndex;
					possSpots = centerMove;
				}
				else
				{
					int[] cornerMove = new int[index];
					int cornerMoveIndex = 0;
					for(int i = 0; i < index; i++)// prioritize corner squares over any other square
					{
						int testRow = possSpots[i] / 9;
						int testCol = possSpots[i] % 9;
						
						if((testRow%3 == 0 || testRow%3 == 2) && (testCol%3 == 0 || testCol%3 == 2))
						{
							cornerMove[cornerMoveIndex] = possSpots[i];
							cornerMoveIndex++;
						}
					}
					
					if(cornerMoveIndex > 0)
					{
						index = cornerMoveIndex;
						possSpots = cornerMove;
					}
				}
			}
			
			
			
			
			
		}
		
		int choice = rand.nextInt(index);
		
		int move = possSpots[choice];
		cursorXPos = move%9;
		cursorYPos = move/9;
		
		confirmMove();
	}
	
	public void undoMove()
	{
		if (moveHistory.size() > 1)
		{
			int move1 = moveHistory.get(moveHistory.size()-1);
			int row1 = move1/9;
			int col1 = move1%9;
			moveHistory.remove(moveHistory.size()-1);
			
			int move2 = moveHistory.get(moveHistory.size()-1);
			int row2 = move2/9;
			int col2 = move2%9;
			moveHistory.remove(moveHistory.size()-1);
			
			boardData[row1][col1] = 0;
			boardData[row2][col2] = 0;
			
			// Recalculate large board data
			for (int r = 0; r < 3; r++)
				for (int c = 0; c < 3; c++)
					largeBoardData[c][r] = isCompleted(r, c, boardData, false);
			
			invalidate();
		}
		if (moveHistory.size() > 0)
		{
			int move = moveHistory.get(moveHistory.size()-1);
			int row = move/9;
			int col = move%9;
			getBoundsForNextMove(col, row, false);
		}
		else
		{
			rLow = 0; rHigh = 9; cLow = 0; cHigh = 9;
		}
	}
	
	public boolean hasMoveHistory()
	{
		return moveHistory.size() > 0;
	}
}
