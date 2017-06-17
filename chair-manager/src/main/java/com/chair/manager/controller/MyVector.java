package com.chair.manager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.chair.manager.pojo.dto.TempDto;

public class MyVector {

	private static MyVector instance;

	private MyVector() {
	}
	
	public static MyVector getInstance(){
		if(instance == null){
			instance = new MyVector();
		}
		return instance;
	}
	
	
	private static Vector vector;
	
	public static Vector getVector(){
		if(vector == null){
			vector = new Vector();
		}
		return vector;
	}
	
	
	
	private static HashMap<String, TempDto> map;
	
	public static HashMap<String, TempDto> getMap(){
		if(map == null){
			map = new HashMap<String, TempDto>();
		}
		return map;
	}
	
}
