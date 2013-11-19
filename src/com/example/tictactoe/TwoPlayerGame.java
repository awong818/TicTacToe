package com.example.tictactoe;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TwoPlayerGame extends Activity implements ViewWasChangedListener, RestartAndQuitDialogFragment.Listener
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
		boardView.setWasChangedListener(this);
		
		confirmButton = (Button)findViewById(R.id.confirmMove);
		confirmButton.setEnabled(false);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void endMove(View view)
	{
		boardView.confirmMove();
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
			t.setText(R.string.turn1);
		else
			t.setText(R.string.turn2);
	}
	
	public void onViewChanged(boolean isValidMove)
	{
		confirmButton.setEnabled(isValidMove);
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
			t.setText(R.string.winner2);
		else
			t.setText(R.string.winner1);
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
}
