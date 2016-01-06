package pl.edu.wat.wcy.model.entities;

@SuppressWarnings("serial")
public class Truck extends Vehicle {
	
	private String regNumber;
	private boolean isAbleToWork;
	
	public Truck()
	{
		
	}
	public Truck(String regNr, boolean able)
	{
		this.setRegNumber(regNr);
		this.setAbleToWork(able);
	}
	public Truck(String regNr, boolean able, int capacity, int x, int y)
	{
		super(capacity,x,y);
		this.setRegNumber(regNr);
		this.setAbleToWork(able);
	}
	
	@Override
	public String toString()
	{
		return ("Nr rejestracyjny: "+this.regNumber+", sprawny: "+this.isAbleToWork);
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
