package cn.edu.tute.tuteclient.domain;

public class Switch {

	private String switchName;
	private String switchStatus;
	private String openLong;
	private String openLatitude;
	private String switchID;

	public Switch(String switchName, String switchStatus,
			String openLong, String openLatitude, String switchID) {
		this.setSwitchName(switchName);
		this.setSwitchStatus(switchStatus);
		this.setOpenLong(openLong);
		this.setOpenLatitude(openLatitude);
		this.setSwitchID(switchID);
	}

	public String getSwitchName() {
		return switchName;
	}

	public void setSwitchName(String switchName) {
		this.switchName = switchName;
	}

	public String getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(String switchStatus) {
		this.switchStatus = switchStatus;
	}

	public String getOpenLong() {
		return openLong;
	}

	public void setOpenLong(String openLong) {
		this.openLong = openLong;
	}

	public String getOpenLatitude() {
		return openLatitude;
	}

	public void setOpenLatitude(String openLatitude) {
		this.openLatitude = openLatitude;
	}

	public String getSwitchID() {
		return switchID;
	}

	public void setSwitchID(String switchID) {
		this.switchID = switchID;
	}

	@Override
	public String toString() {
		return "Switch [switchName=" + switchName + ", switchStatus="
				+ switchStatus + ", openLong=" + openLong + ", openLatitude="
				+ openLatitude + ", switchID=" + switchID + "]";
	}
	

}
