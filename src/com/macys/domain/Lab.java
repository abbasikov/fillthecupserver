package com.macys.domain;

import com.macys.domain.business.BusinessObject;

public interface Lab extends BusinessObject{

	public abstract void setLocation(String location);

	public abstract String getLocation();

	public abstract void setDescription(String description);

	public abstract String getDescription();

}