package com.example.tictactoe;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.Menu;
import android.view.View;

public class MainScreen extends Activity implements DifficultyDialogFragment.Listener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//final Button onePlayer = (Button)this.findViewById(R.id.OnePlayer);
		/*onePlayer.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(MainScreen.this, OnePlayerGame.class);
		}}); */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void toTestGame(View view)
	{
		Intent intent = new Intent(this, TestGame.class);
		startActivity(intent);
	}
	
	public void toTwoPlayer(View view)
	{
		Intent intent = new Intent(this, TwoPlayerGame.class);
		startActivity(intent);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void toOnePlayer(View view)
	{
		DialogFragment dialog = new DifficultyDialogFragment();
		dialog.show(getFragmentManager(), "difficulty");
	}
	
	public void difficultyConfirmed(int difficulty)
	{
		Intent intent = new Intent(this, OnePlayerGame.class);
		intent.putExtra("difficulty", difficulty);
		startActivity(intent);
	}
}
