package com.example.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class RestartAndQuitDialogFragment extends DialogFragment
{
	public interface Listener
	{
		public void onConfirmToQuit();
		public void onConfirmToRestart();
	}
	
	private Listener mListener;
	
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		mListener = (Listener)activity;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.restartAndQuit);
		// If the dialog is to quit the game:
		if (getArguments().getBoolean("isQuit"))
			builder.setPositiveButton(R.string.Quit, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id)
				{
					mListener.onConfirmToQuit();
				}
			});
		// If the dialog is to restart the game:
		else
			builder.setPositiveButton(R.string.restart, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id)
				{
					mListener.onConfirmToRestart();
				}
			});
		
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id)
				{
					// Do nothing
				}
			});
		return builder.create();
	}
}
