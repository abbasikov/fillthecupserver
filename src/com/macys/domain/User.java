package com.macys.domain;

import com.macys.domain.business.BusinessObject;

public interface User extends BusinessObject{

	public abstract void setIsSuperAdmin(String isSuperAdmin);

	public abstract String getIsSuperAdmin();

	public abstract void setPassword(String password);

	public abstract String getPassword();

	public abstract void setUserName(String userName);

	public abstract String getUserName();
		
}
