package com.callbackcops;

import java.rmi.*;

public class MyServer{
	public static void main(String args[])throws Exception{
		try{
			Remote r=new SDBGo();
			Naming.rebind("rmi://localhost:9000/callbackcats",r);
		}catch(Exception e) {
			System.out.println("Found Exception "+e);
		}
	}
}