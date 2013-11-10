package com.example.tictactoe;

//import com.example.tictactoe.R.string;

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
	
	boolean playerMove = false;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button[] buttonArray =  new Button[81];
		
	//	LinearLayout row1 = (LinearLayout) getViewById(R.id.row1);
		playGame();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void registerMove(View view)
	{
		int buttonId = view.getId();
		
	}
	
	public void playerMoved(View view)
	{
		playerMove = true;
		
		registerMove(view);
	}
	
	public void playGame()
	{
		boolean playerTurn1 = true;
		boolean playerTurn2 = false;
		
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
	
	}
	
	public boolean gameEnd()
	{ // detect whether someone has won the game
		return true;
	}
	
/*	public void updateTurnTextView(string s)
	{
		
	}
	*/
	
	
	
}
