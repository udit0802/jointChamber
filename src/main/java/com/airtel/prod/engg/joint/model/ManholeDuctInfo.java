package com.airtel.prod.engg.joint.model;

import java.util.List;


public class ManholeDuctInfo {

	private String direction;
	
	private int noOfDucts;
	
	private List<Duct> ducts;

	

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getNoOfDucts() {
		return noOfDucts;
	}

	public void setNoOfDucts(int noOfDucts) {
		this.noOfDucts = noOfDucts;
	}

	public List<Duct> getDucts() {
		return ducts;
	}

	public void setDucts(List<Duct> ducts) {
		this.ducts = ducts;
	}

	@Override
	public String toString() {
		return "ManholeDuctInfo [direction=" + direction + ", noOfDucts=" + noOfDucts + ", ducts=" + ducts + "]";
	}

}
