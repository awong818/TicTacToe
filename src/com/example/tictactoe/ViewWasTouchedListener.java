package com.example.tictactoe;

public interface ViewWasTouchedListener
{
	void onViewTouched(int x, int y, int player);
	void onViewChanged(boolean isValidMove);
	void onFinishMove();
	void onWin();
}