package pl.edu.wat.wcy.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

public class Truck extends Vehicle {
	private String regNumber;
	private boolean isAbleToWork;
	public Truck()
	{
		
	}
	public String getRegNumber() {
		return regNumber;
	}
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	public boolean isAbleToWork() {
		return isAbleToWork;
	}
	public void setAbleToWork(boolean isAbleToWork) {
		this.isAbleToWork = isAbleToWork;
	}

	
}
