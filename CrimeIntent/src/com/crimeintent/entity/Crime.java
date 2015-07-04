package com.crimeintent.entity;

import java.util.Date;
import java.util.UUID;

import org.json.JSONObject;

public class Crime {
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_DATE = "date";
	private static final String JSON_SOLVED = "isSolved";
	
	private UUID id;
	private String title;
	private Date date;
	private boolean isSolved;
	
	public JSONObject toJson() throws Exception{
		JSONObject json = new JSONObject();
		json.put(JSON_ID, id.toString());
		json.put(JSON_TITLE, title);
		json.put(JSON_DATE, date.getTime());
		json.put(JSON_SOLVED, isSolved);
		return json;
	}
	
	public Crime() {
		id = UUID.randomUUID();
		date = new Date();
	}
	public Crime(JSONObject json) throws Exception{
		id = UUID.fromString(json.getString(JSON_ID));
		if (json.has(JSON_TITLE)) {
			title = json.getString(JSON_TITLE);
		}
		date = new Date(json.getLong(JSON_DATE));
		isSolved = json.getBoolean(JSON_SOLVED);
				
	}
	public UUID getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isSolved() {
		return isSolved;
	}
	public void setSolved(boolean isSolved) {
		this.isSolved = isSolved;
	}
	
	@Override
	public String toString() {
		return "title:"+title+"\n"+date.toString();
	}
	
}
