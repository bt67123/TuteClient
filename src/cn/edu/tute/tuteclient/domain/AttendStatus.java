package cn.edu.tute.tuteclient.domain;

import java.util.Date;

public class AttendStatus {

	private String switchID;
	private String longitude;
	private String latitude;
	private String status; //0 未签到  1 签到未签退  2 已签退
	private Date date;
	
	public AttendStatus(String switchID, String longitude, String latitude,
			String status, Date date) {
		super();
		this.switchID = switchID;
		this.longitude = longitude;
		this.latitude = latitude;
		this.status = status;
		this.date = date;
	}

	public String getSwitchID() {
		return switchID;
	}

	public void setSwitchID(String switchID) {
		this.switchID = switchID;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "AttendStatus [switchID=" + switchID + ", longitude="
				+ longitude + ", latitude=" + latitude + ", status=" + status
				+ ", date=" + date + "]";
	}
	
	
}
