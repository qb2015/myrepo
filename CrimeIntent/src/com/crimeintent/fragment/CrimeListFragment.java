package com.crimeintent.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.crimeintent.activity.CrimeInfoActivity;
import com.crimeintent.activity.R;
import com.crimeintent.entity.Crime;
import com.crimeintent.entity.CrimeLab;

public class CrimeListFragment extends ListFragment {
	private List<Crime> crimes;
	public static final String ITEM_ID = "item_id";
	private boolean titleVisible;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setRetainInstance(true);
		titleVisible = false;
		getActivity().setTitle(R.string.crimes_title);
		crimes = CrimeLab.getCrimelaLab(getActivity()).getCrimes();
		CrimeListAdapter adapter = new CrimeListAdapter(crimes);
		setListAdapter(adapter);
	}
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Crime c = (Crime) getListAdapter().getItem(position);
		Intent intent = new Intent(getActivity(),CrimeInfoActivity.class);
		intent.putExtra(ITEM_ID, c.getId());
		startActivity(intent);
	}
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (titleVisible) {
				getActivity().getActionBar().setSubtitle("subtitle");
			}
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);
		MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
		if (titleVisible && showSubtitle != null) {
			showSubtitle.setTitle("subtitle");
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_new_crime:
			Crime c = new Crime();
			CrimeLab.getCrimelaLab(getActivity()).addCrime(c);
			Intent intent = new Intent(getActivity(), CrimeInfoActivity.class);
			intent.putExtra(ITEM_ID, c.getId());
			startActivityForResult(intent, 0);
			return true;
		case R.id.menu_item_show_subtitle:
			if (getActivity().getActionBar().getSubtitle() == null) {
				getActivity().getActionBar().setSubtitle("subtitle");
				titleVisible = true;
				item.setTitle("subtitle");
			}else {
				getActivity().getActionBar().setSubtitle(null);
				titleVisible = false;
				item.setTitle("null");
			}
		default:
			return super.onOptionsItemSelected(item);
		}
		
		
	}
	private class CrimeListAdapter extends ArrayAdapter<Crime>{

		public CrimeListAdapter( List<Crime> crimes) {
			super(getActivity(), 0, crimes);
			
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity())
						.inflate(R.layout.crime_list_item, null);
			}
			Crime c = getItem(position);
			TextView title = (TextView) convertView.findViewById(R.id.item_title); 
			title.setText(c.getTitle());
			TextView date = (TextView) convertView.findViewById(R.id.item_date);
			date.setText(c.getDate().toString());
			CheckBox solved = (CheckBox) convertView.findViewById(R.id.item_solved);
			solved.setChecked(c.isSolved());
			return convertView;
		}
	
		
	}
}
