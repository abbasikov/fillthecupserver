package com.macys.domain;

import com.macys.domain.business.BusinessObject;

public interface User extends BusinessObject{

	public abstract void setIsSuperAdmin(String isSuperAdmin);

	public abstract String getIsSuperAdmin();

	public abstract void setPassword(String password);

	public abstract String getPassword();

	public abstract void setEmail(String email);

	public abstract String getEmail();

	public abstract void setLastName(String lastName);

	public abstract String getLastName();

	public abstract void setFirstName(String firstName);

	public abstract String getFirstName();
	
}
