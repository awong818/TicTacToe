package com.example.tictactoe;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OnePlayerGame extends Activity implements ViewWasChangedListener, RestartAndQuitDialogFragment.Listener
{
	private TicTacToeBoard boardView;
	private Button confirmButton;
	private Button undoButton;
	private int difficulty;
	
	boolean playerMove = false;
	boolean player1Turn = true;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_player_game);
		
		boardView = (TicTacToeBoard)findViewById(R.id.gameBoard);
		boardView.setWasChangedListener(this);
		
		confirmButton = (Button)findViewById(R.id.confirmMove);
		confirmButton.setEnabled(false);
		
		undoButton = (Button)findViewById(R.id.undoMove);
		undoButton.setEnabled(false);
		
		difficulty = getIntent().getIntExtra("difficulty", 0);
		switch (difficulty)
		{
		case 0: // Easy
			((TextView)findViewById(R.id.difficultyText)).setText(R.string.easy);
			break;
		case 1: // Medium
			((TextView)findViewById(R.id.difficultyText)).setText(R.string.medium);
			break;
		case 2: // Hard
			((TextView)findViewById(R.id.difficultyText)).setText(R.string.hard);
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void endMove(View view)
	{
		boardView.confirmMove();
		undoButton.setEnabled(false);
		
		// Delay CPU decision by one second
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				boardView.CPUmove(difficulty);
				undoButton.setEnabled(true);
			}
		}, 1000);
	}
	
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
		if (isValidMove)
			confirmButton.setEnabled(true);
		else
			confirmButton.setEnabled(false);
	}
	
	public void resetBoard(View view)
	{
		DialogFragment dialog = new RestartAndQuitDialogFragment();
		Bundle bundle = new Bundle(1);
		bundle.putBoolean("isQuit", false);
		dialog.setArguments(bundle);
		dialog.show(getFragmentManager(), "restartAndQuit");
	}
	
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
	
	public void undoMove(View view)
	{
		boardView.undoMove();
		if (!boardView.hasMoveHistory())
			undoButton.setEnabled(false);
	}
	
}

