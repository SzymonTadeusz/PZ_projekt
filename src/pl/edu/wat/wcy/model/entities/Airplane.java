package pl.edu.wat.wcy.model.entities;

import javax.persistence.*;

public class Airplane extends Vehicle {
	private String name;
	
	public Airplane() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
