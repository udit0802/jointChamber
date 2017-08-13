package com.airtel.prod.engg.joint.model;

import java.util.List;

public class Manhole {

	private String manholeId;
	
	private int noOfJoints;
	
	private List<ManholeDuctInfo> manholeDuctInfo;
	
	private List<Joint> joints;

	public String getManholeId() {
		return manholeId;
	}

	public void setManholeId(String manholeId) {
		this.manholeId = manholeId;
	}

	public int getNoOfJoints() {
		return noOfJoints;
	}

	public void setNoOfJoints(int noOfJoints) {
		this.noOfJoints = noOfJoints;
	}

	

	public List<ManholeDuctInfo> getManholeDuctInfo() {
		return manholeDuctInfo;
	}

	public void setManholeDuctInfo(List<ManholeDuctInfo> manholeDuctInfo) {
		this.manholeDuctInfo = manholeDuctInfo;
	}

	public List<Joint> getJoints() {
		return joints;
	}

	public void setJoints(List<Joint> joints) {
		this.joints = joints;
	}

	@Override
	public String toString() {
		return "Manhole [manholeId=" + manholeId + ", noOfJoints=" + noOfJoints + ", manholeDuctInfo=" + manholeDuctInfo
				+ ", joints=" + joints + "]";
	}
	
}
