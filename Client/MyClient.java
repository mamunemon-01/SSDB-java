package com.callbackcops;

import javax.swing.JFrame;

public class MyClient {
	public static void main(String args[]) throws Exception{
		try{
			JFrame MyCFrame = new MyClientFrame();			
		} catch(Exception e) {
			System.out.println("Found Exception "+e);
		}
	}
}