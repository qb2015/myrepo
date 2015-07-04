package com.crimeintent.fragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.crimeintent.activity.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class DatePickerDialog extends DialogFragment {
	public static final String DIALOG_DATE = "date";
	public static final int REQUEST_CODE = 0;
	private Date date;
	
	public static DatePickerDialog newInstance(Date date){
		Bundle args = new Bundle();
		args.putSerializable(DIALOG_DATE, date);
		DatePickerDialog dialog = new DatePickerDialog();
		dialog.setArguments(args);
		return dialog;
	}
	public void sendResult(int resultCode){
		if (getTargetFragment() == null) {
			return;
		}
		Intent i = new Intent();
		i.putExtra(DIALOG_DATE, date);
		getTargetFragment().onActivityResult(REQUEST_CODE, resultCode, i);
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		date = (Date) getArguments().getSerializable(DIALOG_DATE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_datepicker, null);
		
		DatePicker picker = (DatePicker) view.findViewById(R.id.dialog_datepicker);
		picker.init(year, month, day, new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				date = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
				getArguments().putSerializable(DIALOG_DATE, date);
			}
		});
		return new AlertDialog.Builder(getActivity())
		.setView(view)
		.setTitle("日期选择器")
		.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				sendResult(Activity.RESULT_OK);
			}
		})
		.create();
	}
}
