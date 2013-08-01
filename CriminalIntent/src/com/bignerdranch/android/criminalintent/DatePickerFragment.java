package com.bignerdranch.android.criminalintent;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class DatePickerFragment extends DialogFragment {
	
	public static final String EXTRA_DATE = "com.bignerdranch.android.criminalintent.date";
	
	private Date mDate;
	
	public static DatePickerFragment newInstance(Date date) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE, date);
		
		DatePickerFragment fragment = new DatePickerFragment();
		fragment.setArguments(args);
		
		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int year 	= calendar.get(Calendar.YEAR);
		int month 	= calendar.get(Calendar.MONTH);
		int day 	= calendar.get(Calendar.DAY_OF_MONTH);
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
		
		DatePicker datePicker = (DatePicker) v.findViewById(R.id.dialog_date_datePicker);
		
		datePicker.init(year, month, day, new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker view, int year, int month, int day) {
				//Translate year, month, day into a Date object using a Calendar
				mDate = new GregorianCalendar(year, month, day).getTime();
				
				getArguments().putSerializable(EXTRA_DATE, mDate);
				
				/**
				 * At the end of onDateChanged(�), you write the Date back to the fragment�s arguments. You do this to preserve the value of mDate 
				 * in case of rotation. If the device is rotated while the DatePickerFragment is on screen, then the FragmentManager will destroy 
				 * the current instance and create a new one. When the new instance is created, the FragmentManager will call onCreateDialog(�) on 
				 * it, and the instance will get the saved date from the arguments.
				 */
			}
		});
		
		return new AlertDialog.Builder(getActivity())
			.setView(v)
			.setTitle(R.string.date_picker_title)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					sendResult(Activity.RESULT_OK);
				}
			})
			.create();
	}
	
	/**
	 * (A positive button is what the user should press to accept what the dialog presents. There are two other buttons that you can add to an AlertDialog: 
	 * a negative button and a neutral button. These designations determine the positions of the buttons in the dialog (when there is more than one).
	 */
	
	private void sendResult(int resultCode) {
		if (getTargetFragment() == null) {
			return;
		}
		
		Intent i = new Intent();
		i.putExtra(EXTRA_DATE, mDate);
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
}
