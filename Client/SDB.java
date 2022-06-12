package com.callbackcops;

import java.rmi.*;
import java.util.*;

interface SDB extends Remote{
	public List<Student> getResults(String sqlq)throws RemoteException;
	int getNumUpdRows(String sqlq)throws RemoteException;
}