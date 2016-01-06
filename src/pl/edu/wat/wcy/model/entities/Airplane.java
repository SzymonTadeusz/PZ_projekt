package pl.edu.wat.wcy.model.entities;

@SuppressWarnings("serial")
public class Airplane extends Vehicle {
	
	private String name;
	
	public Airplane() {

	}
	public Airplane(String name){
		this.setName(name);
	}
	public Airplane(String name, int capacity, int x, int y)
	{
		super(capacity,x,y);
		this.setName(name);
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
