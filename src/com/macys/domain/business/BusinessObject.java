package com.macys.domain.business;

import java.util.Date;

import com.macys.dao.database.DBObject;
import com.macys.valuesobjects.BaseDTO;

public interface BusinessObject {
	
	public abstract DBObject getDbObject();

	public abstract void setStatusModifiedOn(Date statusModifiedOn);

	public abstract Date getStatusModifiedOn();

	public abstract void setStatus(Short status);

	public abstract Short getStatus();

	public abstract Date getModifiedOn();

	public abstract void setModifiedBy(String modifiedBy);

	public abstract String getModifiedBy();

	public abstract Date getCreatedOn();

	public abstract void setCreatedBy(String createdBy);

	public abstract String getCreatedBy();

	public abstract void setName(String name);

	public abstract String getName();

	public abstract Integer getVersion();

	public abstract String getUuid();

	public abstract String getType();

	public abstract Integer getCounter();
	
	public abstract BaseDTO createDTO();
	
}
