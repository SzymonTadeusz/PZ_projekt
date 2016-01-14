package pl.edu.wat.wcy.model.entities;

public class User {

	private int userID;
	private String name;
	private String password;
	private Role role;
	
	public User() {}
	
	public User(String name, String password, Role role)
	{
		this.name = name;
		this.password = password;
		this.role = role;
		role.addToSet(this);
	}
	
	public User(String name, String password)
	{
		this.name = name;
		this.password = password;
	}
	
	@Override
	public String toString() {
		return ("User: " + this.name + ", has³o: " + this.password);
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
