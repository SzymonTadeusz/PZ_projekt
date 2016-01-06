package pl.edu.wat.wcy.model.entities;

import java.util.HashSet;
import javax.persistence.*;


public class CurrentTransportsList {

	private int currentTransportsListID;
	private HashSet<Transport> transports;
}
