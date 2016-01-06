package pl.edu.wat.wcy.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMStorage {

	private static EntityManagerFactory emf;
	private static EntityManager em;

	public static EntityManagerFactory getEmf() {
		if(emf==null) emf = Persistence.createEntityManagerFactory("PZ_projekt");
		return emf;
	}
	
	public static EntityManager getEm() {
		if(em==null)em=getEmf().createEntityManager();
		return em;
	}
}
