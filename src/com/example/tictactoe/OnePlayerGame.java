package com.example.tictactoe;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//package com.example.tictactoe;
//import com.example.tictactoe.R.string;

public class OnePlayerGame extends Activity implements ViewWasTouchedListener, RestartAndQuitDialogFragment.Listener
{
	private TicTacToeBoard boardView;
	private Button confirmButton;
	
	boolean playerMove = false;
	boolean player1Turn = true;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.two_player_game);
		
		boardView = (TicTacToeBoard)findViewById(R.id.gameBoard);
		boardView.setWasTouchedListener(this);
		confirmButton = (Button)findViewById(R.id.confirmMove);
		//playGame();
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
	
	public void endMove(View view)
	{
		boardView.confirmMove();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				boardView.CPUmove(1);
			}
		}, 1000);
		//boardView.CPUmove(1);
	}
	
	public void onViewTouched(int row, int col, int player) {
		// TODO Auto-generated method stub
		/*if(player == 1)
		{
			gameBoard[row][col] = 1;
		}
		if(player == -1)
		{
			gameBoard[row][col] = -1;
		}*/
		
		//checkForMatches(row, col, player);
		
		TextView t = (TextView) findViewById(R.id.PlayerTurn);
		player1Turn = !player1Turn;
		if (player1Turn)
			t.setText(R.string.turnSolo);
		else
			t.setText(R.string.turnCPU);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void toMainMenu(View view)
	{
		DialogFragment dialog = new RestartAndQuitDialogFragment();
		Bundle bundle = new Bundle(1);
		bundle.putBoolean("isQuit", true);
		dialog.setArguments(bundle);
		dialog.show(getFragmentManager(), "restartAndQuit");
	}
	
	public void onFinishMove()
	{
		TextView t = (TextView) findViewById(R.id.PlayerTurn);
		player1Turn = !player1Turn;
		if (player1Turn)
			t.setText(R.string.turnSolo);
		else
			t.setText(R.string.turnCPU);
	}
	
	
	public void onViewChanged(boolean isValidMove)
	{
		//Log.d("TicTacToe", "" + isValidMove);
		if (isValidMove)
			confirmButton.setEnabled(true);
		else
			confirmButton.setEnabled(false);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void resetBoard(View view)
	{
		DialogFragment dialog = new RestartAndQuitDialogFragment();
		Bundle bundle = new Bundle(1);
		bundle.putBoolean("isQuit", false);
		dialog.setArguments(bundle);
		dialog.show(getFragmentManager(), "restartAndQuit");
	}
	
/*	public void updateTurnTextView(string s)
	{
		
	}
	*/
	
	public void onWin()
	{
		TextView t = (TextView) findViewById(R.id.PlayerTurn);
		// Text is reversed because winner is the person who moved before the "current" player
		if (player1Turn)
			t.setText(R.string.winnerCPU);
		else
			t.setText(R.string.winnerSolo);
	}
	
	public void onConfirmToQuit()
	{
		//Intent intent = new Intent(this, MainScreen.class);
		//startActivity(intent);
		finish();
	}
	
	public void onConfirmToRestart()
	{
		finish();
		startActivity(getIntent());
	}
	
	public void onBackPressed()
	{
		toMainMenu(null);
	}
}

