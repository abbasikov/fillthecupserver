package com.macys.domain;

import com.macys.domain.business.BusinessObject;

public interface Lab extends BusinessObject{

	public abstract void setLocation(String location);

	public abstract String getLocation();

	public abstract void setDescription(String description);

	public abstract String getDescription();

	public abstract void setPdmName(String pdmName);

	public abstract String getPdmName();

	public abstract void setManagerName(String managerName);

	public abstract String getManagerName();

	public abstract void setLastClicked(String lastClicked);

	public abstract String getLastClicked();

	public abstract void setIsActivated(String isActivated);

	public abstract String getIsActivated();

}
