package com.example.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class TestGame extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_game);
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
}
