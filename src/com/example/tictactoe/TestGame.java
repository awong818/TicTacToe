package com.example.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class TestGame extends Activity implements ViewWasTouchedListener {

//	@Override
	int[][] gameBoard = new int[9][9];
	int[][] largeGameBoard = new int[3][3];
	private int rLow = 0, rHigh = 9, cLow = 0, cHigh = 9; // lower and upper bounds for rows and columns
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				gameBoard[i][j] = 0;
			}
		}
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				largeGameBoard[i][j] = 0;
			}
		}
		setContentView(R.layout.test_game);
		TicTacToeBoard t = (TicTacToeBoard) findViewById(R.id.gameBoard);
		t.setWasTouchedListener(this);
		//t.startGame();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void endMove(View view)
	{
		
	}
	
	public void test()
	{
		
	}

	@Override
	public void onViewTouched(int row, int col, int player) {
		// TODO Auto-generated method stub
		if(player == 1)
		{
			gameBoard[row][col] = 1;
		}
		if(player == -1)
		{
			gameBoard[row][col] = -1;
		}
		
		checkForMatches(row, col, player);
		
		TextView t = (TextView) findViewById(R.id.PlayerTurn);
		t.setText(R.string.GameTitle);
	}
	
	private boolean isWinner(int player)
	{
		int sumRow = 0; int sumCol = 0;
		TextView t = (TextView) findViewById(R.id.PlayerTurn);
		//t.setText(R.string.check);
		for(int i = 0; i < 3; i++)
		{
			sumRow += largeGameBoard[i][0] + largeGameBoard[i][1] + largeGameBoard[i][2];
			if(sumRow == 3*player)
			{
				if(player == 1)
					t.setText(R.string.winGame1);
				else
					t.setText(R.string.winGame2);
			}
			
			sumRow = 0;
		}
		
		for(int i = 0; i < 3; i++)
		{
			sumCol += largeGameBoard[0][i] + largeGameBoard[1][i] + largeGameBoard[2][i];
			//sumRow += largeGameBoard[i][0] + largeGameBoard[i][1] + largeGameBoard[i][2];
			if(sumCol == 3*player)
			{
				if(player == 1)
					t.setText(R.string.winGame1);
				else
					t.setText(R.string.winGame2);
				return true;
			}
			
			sumCol = 0;
		}
		
		if(largeGameBoard[0][0] + largeGameBoard[1][1] + largeGameBoard[2][2] == 3*player || 
				largeGameBoard[0][2] + largeGameBoard[1][1] + largeGameBoard[0][2] == 3*player)
		{
			if(player == 1)
				t.setText(R.string.winGame1);
			else
				t.setText(R.string.winGame2);
			return true;
		}
		
		return false;
		}
		
	
	
	private boolean checkForMatches(int row, int col, int player)
	{
		int sumRow = 0; int sumCol = 0; int forDiag = 0; int forDiag2 = 0; int backDiag = 0;
		
		switch(row%3)
		{
			case 0: sumRow += gameBoard[row][col] + gameBoard[row+1][col] + gameBoard[row+2][col]; break;
			case 1: sumRow += gameBoard[row-1][col] + gameBoard[row][col] + gameBoard[row+1][col]; break;
			case 2: sumRow += gameBoard[row-2][col] + gameBoard[row-1][col] + gameBoard[row][col]; break;
		}
		switch(col%3)
		{
			case 0: sumCol += gameBoard[row][col] + gameBoard[row][col+1] + gameBoard[row][col+2]; break;
			case 1: sumCol += gameBoard[row][col-1] + gameBoard[row][col] + gameBoard[row][col+1]; break;
			case 2: sumCol += gameBoard[row][col-2] + gameBoard[row][col-1] + gameBoard[row][col]; break;
		}
		if(row%3 == col%3)
		{
			switch(row%3)
			{
				case 0: forDiag += gameBoard[row][col] + gameBoard[row+1][col+1] + gameBoard[row+2][col+2]; break;
				case 1: forDiag += gameBoard[row-1][col-1] + gameBoard[row][col] + gameBoard[row+1][col+1];
				forDiag2 += gameBoard[row-1][col+1] + gameBoard[row][col] + gameBoard[row+1][col-1]; break;
				case 2:	forDiag += gameBoard[row-2][col-2] + gameBoard[row-1][col-1] + gameBoard[row][col];break;
			}
		}
		
		if(col%3-row%3 == 2)
		{
			backDiag += gameBoard[row][col] + gameBoard[row+1][col-1] + gameBoard[row+2][col-2];
		}
		else if(row%3-col%3 == 2)
		{
			backDiag += gameBoard[row][col] + gameBoard[row-1][col+1] + gameBoard[row-2][col+2];
		}
		TextView t = (TextView) findViewById(R.id.PlayerTurn);
		if(sumRow == 3*player || sumCol == 3*player ||forDiag == 3*player || forDiag2 == 3*player || backDiag == 3*player)
		{
			if(largeGameBoard[row/3][col/3] == 0)
			{
				largeGameBoard[row/3][col/3] = player;
				
			
				if(player == 1)
					t.setText(R.string.winSquare1);
				else
					t.setText(R.string.winSquare2);
				isWinner(player);
			}
			
			return true;
		}
		if(player == -1)
			t.setText(R.string.turn1);
		if(player == 1)
			t.setText(R.string.turn2);
		return false;
	}
	
	public void onFinishMove()
	{
	}
	
	public void onViewChanged(boolean isValidMove)
	{
	}
}
