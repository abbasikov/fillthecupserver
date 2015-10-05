package com.macys.domain;

import com.macys.domain.business.BusinessObject;

public interface SystemComponent extends BusinessObject{

	public abstract void setDescription(String description);

	public abstract String getDescription();

	public abstract void setDisplayName(String displayName);

	public abstract String getDisplayName();

}
