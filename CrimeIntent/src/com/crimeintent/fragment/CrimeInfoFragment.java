package com.crimeintent.fragment;

import java.util.Date;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.crimeintent.activity.R;
import com.crimeintent.entity.Crime;
import com.crimeintent.entity.CrimeLab;

public class CrimeInfoFragment extends Fragment {
	private Crime crime;
	private Button date;
	public static Fragment newInstance(UUID id){
		Bundle args = new Bundle();
		args.putSerializable(CrimeListFragment.ITEM_ID, id);
		CrimeInfoFragment fragment = new CrimeInfoFragment();
		fragment.setArguments(args);
		return fragment;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		UUID id = (UUID) getArguments().getSerializable(CrimeListFragment.ITEM_ID);
		crime = CrimeLab.getCrimelaLab(getActivity()).getCrimeById(id);
	}
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.crime_info,null);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		EditText title = (EditText) view.findViewById(R.id.crime_info_title);
		title.setText(crime.getTitle());
		date = (Button) view.findViewById(R.id.crime_info_date);
		updataDate();
		date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				
				DatePickerDialog dialog = DatePickerDialog.newInstance(crime.getDate());
				dialog.setTargetFragment(CrimeInfoFragment.this, DatePickerDialog.REQUEST_CODE);
				dialog.show(fm, DatePickerDialog.DIALOG_DATE);
			}
		});
		CheckBox solved = (CheckBox) view.findViewById(R.id.crime_info_solved);
		solved.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				crime.setSolved(isChecked);
			}
		});
		return view;
	}
	@Override
	public void onPause() {
		super.onPause();
		CrimeLab.getCrimelaLab(getActivity()).saveCrimes();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if (requestCode == DatePickerDialog.REQUEST_CODE) {
			Date d = (Date) data.getSerializableExtra(DatePickerDialog.DIALOG_DATE);
			crime.setDate(d);
			updataDate();
		}
	}
	private void updataDate(){
		date.setText(crime.getDate().toString());
	}
}
