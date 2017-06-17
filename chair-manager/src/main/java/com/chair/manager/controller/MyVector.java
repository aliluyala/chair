package com.chair.manager.controller;

import java.util.Vector;

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
	
	
}
