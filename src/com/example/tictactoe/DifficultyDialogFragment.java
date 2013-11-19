package com.example.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DifficultyDialogFragment extends DialogFragment {
	
	private int savedChoice = -1;
	private Listener mListener;
	
	public interface Listener
	{
		public void difficultyConfirmed(int difficulty);
	}
	
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		mListener = (Listener)activity;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setTitle(R.string.pickDifficulty);
	
		builder.setSingleChoiceItems(R.array.difficulties, savedChoice, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which)
			{
				savedChoice = which;
			}
		});
		
		builder.setPositiveButton(R.string.confirmMove, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id)
			{
				mListener.difficultyConfirmed(savedChoice);
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
