package cn.edu.tute.tuteclient.domain;


public class User {

	private String ID;
	private String name;
	private int collegeID;
	
	public User(String name, int collegeID) {
		super();
		this.name = name;
		this.collegeID = collegeID;
	}

	public User(String ID, String name, int collegeID) {
		this(name, collegeID);
		this.ID = ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	
	public String getID() {
		return ID;
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
