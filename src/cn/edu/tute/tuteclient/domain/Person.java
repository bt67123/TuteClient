package cn.edu.tute.tuteclient.domain;


public class Person {

	private String name;
	private int collegeID;
	
	public Person(String name, int collegeID) {
		this.name      = name;
		this.collegeID = collegeID;
	}
	
	public void setCollegeID(int collegeID) {
		this.collegeID = collegeID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCollegeID() {
		return collegeID;
	}
	
	public String getName() {
		return name;
	}
}
