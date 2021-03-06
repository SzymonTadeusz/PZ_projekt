package pl.edu.wat.wcy.model.dao;

public interface GenericDao<T> {
	T create(T t);
	void delete(Object id);
	T retrieve(Object id);
	T update(T t);
}
