package com.crimeintent.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class BaseActivity extends FragmentActivity {
	public abstract Fragment createFragment();
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.ui_container);
		FragmentManager fm = getSupportFragmentManager();
		Fragment f = fm.findFragmentById(R.id.ui_container);
		if (f == null) {
			f = createFragment();
			fm.beginTransaction()
			.add(R.id.ui_container, f)
			.commit();
		}
	}
}
