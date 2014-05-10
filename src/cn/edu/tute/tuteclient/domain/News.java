package cn.edu.tute.tuteclient.domain;

public class News {

	private String id;
	private String title;
	private String type;
	private String pubDate;
	private String pubPerson;
	private CharSequence content;
	
	public News(String id, String title, String type, String pubDate, String pubPerson) {
		this.setId(id);
		this.setTitle(title);
		this.setPubDate(pubDate);
		this.setPubPerson(pubPerson);
		this.setType(type);
		content = "";
	}
	
	public CharSequence getContent() {
		return content;
	}
	
	public String getType() {
		return type;
	}
	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
		
	}
	
	public String getPubDate() {
		return pubDate;
	}
	
	public String getPubPerson() {
		return pubPerson;
	}
	
	public void setContent(CharSequence content) {
		this.content = content;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	
	public void setPubPerson(String pubPerson) {
		this.pubPerson = pubPerson;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
