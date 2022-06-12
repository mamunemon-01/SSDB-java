package com.callbackcops;

public class Student implements java.io.Serializable{
	private int id, age, ad_year;
	private String name,department,gender;

	public int getid() {
		return id;
	}
	public void setid(int iD) {
		this.id = iD;
	}
	public String getname() {
		return name;
	}
	public void setname(String nAME) {
		this.name = nAME;
	}
	public String getdepartment() {
		return department;
	}
	public void setdepartment(String dept_name) {
		this.department = dept_name;
	}
	public String getgender() {
		return gender;
	}
	public void setgender(String sex) {
		this.gender = sex;
	}
	public int getage() {
		return age;
	}
	public void setage(int aGE) {
		this.age = aGE;
	}
	public int getad_year() {
		return ad_year;
	}
	public void setad_year(int aD_YEAR) {
		this.ad_year = aD_YEAR;
	}	
}
