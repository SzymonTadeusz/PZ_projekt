package pl.edu.wat.wcy.model.entities;

import java.util.HashSet;

import javax.persistence.*;


public class Cargo {
private int cargoID;
	private HashSet<Vehicle> vehicle;
	private String name;
	private int unitWeight;
	
}
