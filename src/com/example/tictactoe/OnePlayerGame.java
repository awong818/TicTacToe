package com.example.tictactoe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.os.Bundle;

public class OnePlayerGame extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
		
		LinearLayout[] boardRows = {row1, row2, row3, row4, row5, row6, row7, row8, row9};
		
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < boardRows[i].getChildCount(); j++) // inserted from left to right and top to bottom
			{
				gameBoard[i][j] = (Button) findViewById(boardRows[i].getChildAt(j).getId());
			}
		}
		
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
