package pl.edu.wat.wcy.model.entities;

import java.util.HashSet;
import java.util.Set;

public class Role {

	private int roleID;
	private String roleName;
	private Set<User> setOfUsersWithThisRole = new HashSet<User>();
	
	public Role()
	{}
	
	public Role(String name)
	{
		this.setRoleName(name);
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<User> getSetOfUsersWithThisRole() {
		return setOfUsersWithThisRole;
	}

	public void setSetOfUsersWithThisRole(Set<User> setOfUsersWithThisRole) {
		this.setOfUsersWithThisRole = setOfUsersWithThisRole;
	}

	public void addToSet(User u)
	{
		this.setOfUsersWithThisRole.add(u);
	}
	
	public void removeFromSet(User u)
	{
		this.setOfUsersWithThisRole.remove(u);
	}
	
	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	
	
	
}
