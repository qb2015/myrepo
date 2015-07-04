package com.crimeintent.activity;

import java.util.List;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import com.crimeintent.entity.Crime;
import com.crimeintent.entity.CrimeLab;
import com.crimeintent.fragment.CrimeInfoFragment;
import com.crimeintent.fragment.CrimeListFragment;

public class CrimeInfoActivity extends FragmentActivity {
	private List<Crime> crimes;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		ViewPager viewPager = new ViewPager(this);
		viewPager.setId(R.id.viewPager);
		setContentView(viewPager);
		crimes = CrimeLab.getCrimelaLab(this).getCrimes();
		UUID id = (UUID) getIntent().getSerializableExtra(CrimeListFragment.ITEM_ID);
		FragmentManager fm = getSupportFragmentManager();
		viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				return crimes.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				Crime c = crimes.get(arg0);
				return CrimeInfoFragment.newInstance(c.getId());
			}
		});
		for (int i = 0; i < crimes.size(); i++) {
			if (crimes.get(i).getId().equals(id)) {
				viewPager.setCurrentItem(i);
				setTitle(crimes.get(i).getTitle());
				break;
			}
		}
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				Crime c = crimes.get(arg0);
				if (c.getTitle() != null) {
					setTitle(c.getTitle());
				}
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	

}
