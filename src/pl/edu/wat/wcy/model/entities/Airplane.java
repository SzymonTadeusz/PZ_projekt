package pl.edu.wat.wcy.model.entities;

@SuppressWarnings("serial")
public class Airplane extends Vehicle {
	
	private String name;
	
	public Airplane() {

	}
	public Airplane(String name){
		this.setName(name);
//		this.setCurrentCargo(new HashSet<Cargo>());
	}
	public Airplane(String name, int capacity, int x, int y)
	{
		super(capacity,x,y);
		this.setName(name);
//		this.setCurrentCargo(new HashSet<Cargo>());

	}
	
	@Override
	public String toString()
	{
		String cargo=" ";
		if(this.getCurrentCargo()!=null)
			for(Cargo c: this.getCurrentCargo())
				cargo+=(c+" ");
		return ("Pojazd "+this.getVehicleID()+" SAMOLOT - nazwa: " + this.name +", pojemnosc: "+this.getCapacity()+", kierowca: "+this.getCurrentDriver()+". Przewozi: "+cargo);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
