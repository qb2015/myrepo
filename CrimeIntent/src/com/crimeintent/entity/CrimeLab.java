package com.crimeintent.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class CrimeLab {
	
	private static final String TAG = "CrimeLab";
	private static final String FILENAME = "crime.json";
	private List<Crime> crimes;
	private JsonSerializer json;
	private static CrimeLab mCrimeLab;
	private Context appContext;
	
	private CrimeLab(Context context) {
		this.appContext = context;
		crimes = new ArrayList<Crime>();
		json = new JsonSerializer(appContext, FILENAME);
	}
	/**
	 * 获得CrimeLab单例
	 * @return
	 */
	public static CrimeLab getCrimelaLab(Context c){
		if (mCrimeLab == null) {
			mCrimeLab = new CrimeLab(c.getApplicationContext());
		}
		return mCrimeLab;
	}
	/**
	 * 获取Crime对象列表
	 * @return
	 */
	public List<Crime> getCrimes(){
		return crimes;
	}
	/**
	 * 通过UUID从列表中获取Crime对象
	 * @param id
	 * @return
	 */
	public Crime getCrimeById(UUID id){
		for (Crime c : crimes) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}
	public void addCrime(Crime c){
		crimes.add(c);
	}
	public boolean saveCrimes(){
		try {
			json.saveCrime(crimes);
			Log.d(TAG, "crimes saved to file");
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Error saving crimes:", e);
			return false;
		}
	}
}
