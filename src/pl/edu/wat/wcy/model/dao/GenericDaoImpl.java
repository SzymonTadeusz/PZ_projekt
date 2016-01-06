package pl.edu.wat.wcy.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GenericDaoImpl<T> implements GenericDao<T> {

	Class<T> type;
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("PZ_projekt");
	public EntityManagerFactory getEmf() {
		return emf;
	}

	public EntityManager getEm() {
		return em;
	}

	EntityManager em;
	
//	public GenericDaoImpl() {
//		super();
//		em.isOpen();
//		Type t = getClass().getGenericSuperclass();
//		ParameterizedType pt = (ParameterizedType)t;
//		type = (Class)pt.getActualTypeArguments()[0];
//	}

	public GenericDaoImpl(Class<T> type) {
		super();
		em = emf.createEntityManager();
		this.type = type;
	}

	@Override
	public T create(T t) {
		em.persist(t);
		return t;
	}

	@Override
	public void delete(Object id) {
		em.remove(em.getReference(type, id));
	}

	@Override
	public T retrieve(Object id) {
		return em.find(type, id);
	}

	@Override
	public T update(T t) {
		em.merge(t);
		return t;
	}

}
