package com.example.tictactoe;

//import com.example.tictactoe.R.string;

import java.util.ArrayList;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.os.Bundle;



public class TwoPlayerGame extends Activity{
	
	private class Location
	{
		private int row, col;
		public Location(int r, int c)
		{
			
			row = r;
			col = c;
		}
		
		private void setRow(int r)
		{
			row = r;
		}
		
		private void setCol(int c)
		{
			col = c;
		}
		
		public int getRow()
		{
			return row;
		}
		
		public int getCol()
		{
			return col;
		}
		
	}
	
	
	boolean playerMove = false;
	Button[][] gameBoard =  new Button[9][9]; // holds data for every block in the 9x9 tictactoe board
	Button[][] gameBoard3x3 = new Button[3][3]; // holds data for larger 3x3 board to check for win in the whole game 
	boolean playerTurn1 = true;
	boolean playerTurn2 = false;
	
	ArrayList<Location> moveHistory = new ArrayList<Location>();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_player_game);
		System.out.println("Game start");
	setUpGame();
		
	//	playGame();
	}
	
	public void setUpGame()
	{
		System.out.println("SET UP GAME");
		Button[][] gameBoard =  new Button[9][9];
		
		LinearLayout row1 = (LinearLayout) findViewById(R.id.row_1);
		LinearLayout row2 = (LinearLayout) findViewById(R.id.row_2);
		LinearLayout row3 = (LinearLayout) findViewById(R.id.row_3);
		LinearLayout row4 = (LinearLayout) findViewById(R.id.row_4);
		LinearLayout row5 = (LinearLayout) findViewById(R.id.row_5);
		LinearLayout row6 = (LinearLayout) findViewById(R.id.row_6);
		LinearLayout row7 = (LinearLayout) findViewById(R.id.row_7);
		LinearLayout row8 = (LinearLayout) findViewById(R.id.row_8);
		LinearLayout row9 = (LinearLayout) findViewById(R.id.row_9);
		
		//add all 9 horizontal linear layouts
		LinearLayout[] boardRows = {row1, row2, row3, row4, row5, row6, row7, row8, row9};
		
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < boardRows[i].getChildCount(); j++) // inserted from left to right and top to bottom
			{
				// initializes all buttons to have empty strings allows them to be clicked
				gameBoard[i][j] = (Button) findViewById(boardRows[i].getChildAt(j).getId());
				gameBoard[i][j].setEnabled(true);
				gameBoard[i][j].setText("");
				gameBoard[i][j].setOnClickListener(new onBoardClick(i, j));
			}
		}
		
		
		
		
		
	
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void playGame()
	{

		/*
		while(!gameEnd()) // while game has not ended
		{
			if(playerTurn1)
			{
				while(playerMove)
				{
					
				}	
				// get where player 1 moved
				// update board with player 1 move
				// check if 3x3 grid with player 1 move has made him win this grid
				// 
				playerTurn1 = false;
				playerTurn2 = true;
				continue;
			}
			if(playerTurn2)
			{
				
				
				playerTurn2 = false;
				playerTurn1 = true;
				continue;
			}
		}
	*/
	}
	
	public void updateGameBoard3x3(int row, int col)
	{
		Location loc = moveHistory.get(moveHistory.size()-1);
		
		
	}
	
	public boolean boardHasWin(String[][] board) // always pass in 3x3 board 
	{ // detect whether someone has won the game
		
		return true;
	}
	/*
	public void updateGameBoard(int row, int col)
	{
		int blockNumber = getBlockNumber(row, col);
		int rLow = -1;
		int rHigh = -1;
		int cLow = -1;
		int cHigh = -1;
		switch(blockNumber)
		{
		case 1: rLow = 0; rHigh = 2; cLow = 0; cHigh = 2;
			break;
		case 2: rLow = 0; rHigh = 2; cLow = 3; cHigh = 5;
			break;
		case 3: rLow = 0; rHigh = 2; cLow = 6; cHigh = 8;
			break;
		case 4: rLow = 3; rHigh = 5; cLow = 0; cHigh = 2;
			break;
		case 5: rLow = 3; rHigh = 5; cLow = 3; cHigh = 5;
			break;
		case 6: rLow = 3; rHigh = 5; cLow = 6; cHigh = 8;
			break;
		case 7: rLow = 6; rHigh = 8; cLow = 0; cHigh = 2;
			break;
		case 8: rLow = 6; rHigh = 8; cLow = 3; cHigh = 5;
			break;
		case 9: rLow = 6; rHigh = 8; cLow = 6; cHigh = 8;
			break;
		}
		
		String [][] smallGame = new String[3][3];
		for(int i = rLow, row1 = 0; i <= rHigh; i++, row1++)
		{
			for(int j = cLow, col1 = 0; j <= cHigh; j++, col1++)
			{
				
				smallGame[row1][col1] = (String) gameBoard[i][j].getText();
			}
		}
		
	}*/

	
	
	public boolean isValidMove(int row, int col) // determines if valid move based on previous player's move
	{
		
		if(moveHistory.size() == 0)
		{
			return true;
		}
		else
		{
			Location prevLoc = moveHistory.get(moveHistory.size()-1);
			int prevRow = prevLoc.getRow();
			int prevCol = prevLoc.getCol();
			
			int blockNumber = getBlockNumber(prevRow, prevCol);
			
			// lower and upper bounds for rows and columns of 3x3 blocks
			int rLow = -1;
			int rHigh = -1;
			int cLow = -1;
			int cHigh = -1;
			
			switch(blockNumber)
			{
			case 1: rLow = 0; rHigh = 2; cLow = 0; cHigh = 2;
				break;
			case 2: rLow = 0; rHigh = 2; cLow = 3; cHigh = 5;
				break;
			case 3: rLow = 0; rHigh = 2; cLow = 6; cHigh = 8;
				break;
			case 4: rLow = 3; rHigh = 5; cLow = 0; cHigh = 2;
				break;
			case 5: rLow = 3; rHigh = 5; cLow = 3; cHigh = 5;
				break;
			case 6: rLow = 3; rHigh = 5; cLow = 6; cHigh = 8;
				break;
			case 7: rLow = 6; rHigh = 8; cLow = 0; cHigh = 2;
				break;
			case 8: rLow = 6; rHigh = 8; cLow = 3; cHigh = 5;
				break;
			case 9: rLow = 6; rHigh = 8; cLow = 6; cHigh = 8;
				break;
			}
			
			if((row >= rLow && row <= rHigh) && (col >= cLow && col <= cHigh))
			{
				return true;
			}
			else
			{
				System.out.println("Please make move between rows " + rLow +  " and " +  rHigh + " and between " + cLow + " and "+  cHigh);
				return false;
			}
		}
		
	}
	
	public int getBlockNumber(int row, int col) // 3x3 blocks numbered 1-9 starting left to right and then top to bottom 
	/* 1 2 3 
	 * 4 5 6
	 * 7 8 9
	 */
	{
		if((row == 0 || row == 3 || row == 6) && (col ==0 || col == 3 || col == 6))
		{
			return 1;
		}
		else if((row == 0 || row == 3 || row == 6) && (col ==1 || col == 4 || col == 7))
		{
			return 2;
		}
		else if((row == 0 || row == 3 || row == 6) && (col ==2 || col == 5 || col == 8))
		{
			return 3;
		}
		else if((row == 1 || row == 4 || row == 7) && (col == 0|| col == 3 || col == 6))
		{
			return 4;
		}
		else if((row == 1 || row == 4 || row == 7) && (col == 1 || col == 4 || col == 7))
		{
			return 5;
		}
		else if((row == 1 || row == 4 || row == 7) && (col == 2 || col == 5 || col == 8))
		{
			return 6;
		}
		else if((row == 2 || row == 5 || row == 8) && (col == 0 || col == 3 || col == 6))
		{
			return 7;
		}
		else if((row == 2 || row == 5 || row == 8) && (col == 1 || col == 4 || col == 7))
		{
			return 8;
		}
		else if((row == 2 || row == 5 || row == 8) && (col == 2 || col == 5 || col == 8))
		{
			return 9;
		}
		return -1;
	}
	
	private class onBoardClick implements View.OnClickListener // created separate class so that could determine which button was pressed
	{
		int buttonRow, buttonCol;
		public onBoardClick(int row, int col)
		{
			buttonRow = row;
			buttonCol = col;
		}
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			if(isValidMove(buttonRow, buttonCol))
			{
				if(playerTurn1)
				{
					System.out.println("row clicked: " + buttonRow + " column clicked: " + buttonCol);
					gameBoard[buttonRow][buttonCol].setText(R.string.XSign);
					/*moveHistory.add(new Location(buttonRow, buttonCol));
					updateGameBoard(buttonRow, buttonCol);*/
					playerTurn1 = false;
					playerTurn2 = true;
				}
				
				else if(playerTurn2)
				{
					gameBoard[buttonRow][buttonCol].setText(R.string.OSign);
					/*moveHistory.add(new Location(buttonRow, buttonCol));
					updateGameBoard3x3(buttonRow, buttonCol);*/
					playerTurn2 = false;
					playerTurn1 = true;
				}
				else
				{
					System.out.println("ERROR ONCLICK row: " + buttonRow + " column: " + buttonCol);
				}
				
			}
		}

	}
	
}


