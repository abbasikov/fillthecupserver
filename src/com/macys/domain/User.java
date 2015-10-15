package com.macys.domain;

import com.macys.domain.business.BusinessObject;

public interface User extends BusinessObject{

	public abstract void setIsSuperAdmin(String isSuperAdmin);

	public abstract String getIsSuperAdmin();

	public abstract void setPassword(String password);

	public abstract String getPassword();

	public abstract void setUserName(String userName);

	public abstract String getUserName();

	public abstract void setUserEmail(String userEmail);

	public abstract String getUserEmail();

	public abstract void setLastName(String lastName);

	public abstract String getLastName();

	public abstract void setFirstName(String firstName);

	public abstract String getFirstName();

	public abstract void setIsLabUser(String isLabUser);

	public abstract String getIsLabUser();

	public abstract void setIsLabManager(String isLabManager);

	public abstract String getIsLabManager();

	public abstract void setIsPasswordReset(String isPasswordReset);

	public abstract String getIsPasswordReset();
		
}
