package com.example.tictactoe;

public interface ViewWasChangedListener
{
	void onViewChanged(boolean isValidMove);
	void onFinishMove();
	void onWin();
}