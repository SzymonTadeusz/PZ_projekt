package pl.edu.wat.wcy.model.dao;

import java.util.ArrayList;
import pl.edu.wat.wcy.main.Main;

public class GenericDaoImpl<T> implements GenericDao<T> {

	Class<T> type;
	private ArrayList<T> list;

	public GenericDaoImpl(Class<T> type) {
		super();
		this.type = type;
		this.list = new ArrayList<T>();
	}

	@Override
	public T create(T t) {
		try {
			EMStorage.getEm().getTransaction().begin();
			EMStorage.getEm().persist(t);
			EMStorage.getEm().getTransaction().commit();
			list.add(t);
		} catch (Exception e) {
			Main.eventLog.severe("Blad tworzenia: " + e.getMessage());
			}
		return t;
	}

	@Override
	public void delete(Object id) {
		try {
			EMStorage.getEm().getTransaction().begin();
			T deleted = EMStorage.getEm().getReference(type, id);
			EMStorage.getEm().remove(EMStorage.getEm().getReference(type, id));
			EMStorage.getEm().getTransaction().commit();
			list.remove(deleted);
		} catch (Exception e) {
			Main.eventLog.severe("Blad usuwania: " + e.getMessage());
			}
	}

	@Override
	public T retrieve(Object id) {
		return EMStorage.getEm().find(type, id);
	}

	@Override
	public T update(T t) {
		try {
			EMStorage.getEm().getTransaction().begin();
			EMStorage.getEm().merge(t);
			EMStorage.getEm().getTransaction().commit();
		} catch (Exception e) {
			Main.eventLog.severe("Blad aktualizacji: " + e.getMessage());
			}
		return t;
	}

	public ArrayList<T> getList() {
		return this.list;
	}

}
