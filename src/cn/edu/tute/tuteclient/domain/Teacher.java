package cn.edu.tute.tuteclient.domain;

public class Teacher {
	
	private String id;
	private String name;
	
	public Teacher(String id, String name) {
		this.id   = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name + ":" + id;
	}
}
