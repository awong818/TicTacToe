package com.example.tictactoe;

import java.util.ArrayList;
/*
import com.example.tictactoe.TwoPlayerGame.Location;
import com.example.tictactoe.TwoPlayerGame.onBoardClick;
*/
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainScreen extends Activity {

	//@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final Button onePlayer = (Button)this.findViewById(R.id.OnePlayer);
		final Button twoPlayer = (Button)this.findViewById(R.id.TwoPlayer);
		onePlayer.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(MainScreen.this, OnePlayerGame.class);
			startActivity(intent);
		}}); 
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void toTwoPlayer(View view)
	{
		Intent intent = new Intent(this, TwoPlayerGame.class);
		startActivity(intent);
	}

	public void toTestGame(View view)
	{
		Intent intent = new Intent(this, TestGame.class);
		startActivity(intent);
	}
}