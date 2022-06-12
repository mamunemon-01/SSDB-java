package com.callbackcops;

import java.rmi.*;
import java.rmi.server.*;
import java.sql.*;
import java.util.*;

class SDBGo extends UnicastRemoteObject implements SDB{
	SDBGo()throws RemoteException{}

	public List<Student> getResults(String sqlq){
		List<Student> list=new ArrayList<Student>();
		int size = 0;
		Statement st;
		ResultSet rs;
		try{
			Class.forName("org.postgresql.Driver");
			Connection con=DriverManager.getConnection("jdbc:postgresql://tiny.db.elephantsql.com/kjnrfpvi","kjnrfpvi","HQCoc1uwH-61wMxp0MghG5iY3CQHJaTR");
			st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs=st.executeQuery(sqlq);

			boolean valuefound = false;
			while(rs.next()){
				Student s=new Student();
				s.setid(rs.getInt(1));
				s.setname(rs.getString(2));
				s.setdepartment(rs.getString(3));
				s.setgender(rs.getString(4));
				s.setage(rs.getInt(5));
				s.setad_year(rs.getInt(6));
				list.add(s);
				valuefound=true;
			}
			if(valuefound==false) rs=null;

			//if(rs!=null){
			rs.last();	//moves cursor to the last row
			size = rs.getRow(); //get row id
			//}
		st.close();
		rs.close();
		con.close();
		}catch(Exception e){
			//rs=null;
			list=null;
			//System.out.println("While Getting Rows, Found Exception "+e);
		}
		System.out.println("Number of Rows Returned: "+size);

		return list;
	}//end of getStudents()

	public int getNumUpdRows(String sqlq){
		Statement st;
		int numrows=0;
		try{
			Class.forName("org.postgresql.Driver");
			
			Connection con=DriverManager.getConnection("jdbc:postgresql://tiny.db.elephantsql.com/kjnrfpvi","kjnrfpvi","HQCoc1uwH-61wMxp0MghG5iY3CQHJaTR");
			st=con.createStatement();
			numrows=st.executeUpdate(sqlq);

		st.close();
		con.close();
		}catch(Exception e){
			System.out.println("While Getting Number of Updated Rows, Found Exception "+e);
		}
		System.out.println("Number of Affected Rows: "+numrows);

		return numrows;
	}//end of getNumUpdRows()
}