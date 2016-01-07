package pl.edu.wat.wcy.model.dao;

import java.util.ArrayList;

public class GenericDaoImpl<T> implements GenericDao<T> {

	Class<T> type;
	private ArrayList<T> list;
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
		this.list = new ArrayList<T>();
	}

	@Override
	public T create(T t) {
		EMStorage.getEm().getTransaction().begin();
		EMStorage.getEm().persist(t);
		EMStorage.getEm().getTransaction().commit();
		list.add(t);
		return t;
	}

	@Override
	public void delete(Object id) {
		EMStorage.getEm().getTransaction().begin();
		T deleted = EMStorage.getEm().getReference(type, id);
		EMStorage.getEm().remove(EMStorage.getEm().getReference(type, id));
		EMStorage.getEm().getTransaction().commit();
		list.remove(deleted);
	}

	@Override
	public T retrieve(Object id) {
		return EMStorage.getEm().find(type, id);
	}

	@Override
	public T update(T t) {
		EMStorage.getEm().getTransaction().begin();
		EMStorage.getEm().merge(t);
		EMStorage.getEm().getTransaction().commit();
			return t;
	}
	
	public ArrayList<T> getList()
	{
		return this.list;
	}

}
