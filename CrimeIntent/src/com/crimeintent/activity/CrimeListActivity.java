package com.crimeintent.activity;

import com.crimeintent.fragment.CrimeListFragment;

import android.support.v4.app.Fragment;

public class CrimeListActivity extends BaseActivity {

	@Override
	public Fragment createFragment() {
		
		return new CrimeListFragment();
	}

}
