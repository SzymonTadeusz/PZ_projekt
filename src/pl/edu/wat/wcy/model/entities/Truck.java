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
//		this.setCurrentCargo(new HashSet<Cargo>();
	}
	public Truck(String regNr, boolean able, int capacity, int x, int y)
	{
		super(capacity,x,y);
		this.setRegNumber(regNr);
		this.setAbleToWork(able);
//		this.setCurrentCargo(new HashSet<Cargo>();

	}
	
	@Override
	public String toString()
	{
		String cargo=" {";
		String sprawny = " ";
		sprawny=(this.isAbleToWork=true) ? " sprawny" : " niesprawny";
		if(this.getCurrentCargo()!=null)
			for(Cargo c: this.getCurrentCargo())
				cargo+=(c+" ");
		cargo+="}";
		return ("Pojazd "+this.getVehicleID()+ sprawny +" TIR - nr: " + this.getRegNumber() +", pojemnosc: "+this.getCapacity()+", kierowca: "+this.getCurrentDriver()+". Przewozi: "+cargo);
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
