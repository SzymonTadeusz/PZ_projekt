package pl.edu.wat.wcy.model.entities;

import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class TransportHistory {

	private int transportHistoryID;
	private HashSet<Transport> transportHistory;
}
