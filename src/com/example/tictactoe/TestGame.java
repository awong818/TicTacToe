package com.example.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class TestGame extends Activity implements ViewWasTouchedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
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
	public void onViewTouched(int x, int y, int player) {
		// TODO Auto-generated method stub
		TextView t = (TextView) findViewById(R.id.PlayerTurn);
		t.setText(R.string.GameTitle);
	}
}
