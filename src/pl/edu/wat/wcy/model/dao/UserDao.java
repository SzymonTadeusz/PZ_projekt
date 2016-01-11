package pl.edu.wat.wcy.model.dao;

import pl.edu.wat.wcy.model.entities.User;

public class UserDao extends GenericDaoImpl<User> {

	public UserDao() {
		super(User.class);
	}

}
