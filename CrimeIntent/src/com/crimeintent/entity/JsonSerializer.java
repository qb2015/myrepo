package com.crimeintent.entity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;

public class JsonSerializer {
	private Context context;
	private String fileName;
	
	public JsonSerializer(Context context, String fileName) {
		this.context = context;
		this.fileName = fileName;
	}
	public void saveCrime(List<Crime> crimes) throws Exception{
		JSONArray array = new JSONArray();
		for (Crime crime : crimes) {
			array.put(crime.toJson());
		}
		Writer writer = null;
		try{
			OutputStream out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		}finally{
			if (writer != null) {
				writer.close();
			}
		}
	}
	public List<Crime> loadCrimes(){
		List<Crime> lists = new ArrayList<Crime>();
		BufferedReader reader = null;
		InputStream in;
		try {
			in = context.openFileInput(fileName);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			JSONArray array = (JSONArray)new JSONTokener(sb.toString()).nextValue();
			for (int i = 0; i < array.length(); i++) {
				lists.add(new Crime(array.getJSONObject(i)));
			}
		} catch (Exception e) {
			
		}finally{
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return lists;
		
	}
}
