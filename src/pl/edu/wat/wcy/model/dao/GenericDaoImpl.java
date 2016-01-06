package pl.edu.wat.wcy.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GenericDaoImpl<T> implements GenericDao<T> {

	Class<T> type;
	
//	public GenericDaoImpl() {
//		super();
//		em.isOpen();
//		Type t = getClass().getGenericSuperclass();
//		ParameterizedType pt = (ParameterizedType)t;
//		type = (Class)pt.getActualTypeArguments()[0];
//	}

	public GenericDaoImpl(Class<T> type) {
		super();
		this.type = type;
	}

	@Override
	public T create(T t) {
		EMStorage.getEm().persist(t);
		return t;
	}

	@Override
	public void delete(Object id) {
		EMStorage.getEm().remove(EMStorage.getEm().getReference(type, id));
	}

	@Override
	public T retrieve(Object id) {
		return EMStorage.getEm().find(type, id);
	}

	@Override
	public T update(T t) {
		EMStorage.getEm().merge(t);
		return t;
	}

}
