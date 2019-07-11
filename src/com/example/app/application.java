package com.example.app;

import java.util.HashMap;

import android.app.Application;

public class application extends Application{
	private HashMap<String,Object> map=new HashMap<String,Object>();
	private static application instance;
	public static application getInstance() {
		return instance;
	}
	public HashMap<String, Object> getMap() {
		return map;
	}
	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}
	public void onCreate(){
		super.onCreate();
		this.instance=this;
	}
}
