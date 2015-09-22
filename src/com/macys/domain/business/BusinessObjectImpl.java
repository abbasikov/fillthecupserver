package com.macys.domain.business;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.ObjectUtils;

import com.macys.dao.database.DBObject;
import com.macys.valuesobjects.BaseDTO;

public abstract class BusinessObjectImpl implements BusinessObject {
	
	transient private DBObject dbObject;
	
	public BusinessObjectImpl(DBObject dbObject) {
		this.dbObject = dbObject;
	}
	
	@Override
	public boolean equals(Object obj) {	
		
		if(obj instanceof BusinessObject) {
			BusinessObject that = (BusinessObject) obj;
			
			return ObjectUtils.nullSafeEquals(this.getUuid(), that.getUuid());
			
		}
		
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		
		return ObjectUtils.nullSafeHashCode(getUuid());
		
	}
	
	public void setDbObject(DBObject dbObject) {
		this.dbObject = dbObject;
	}
	
	@Override
	public DBObject getDbObject() {		
		if(dbObject == null) {
			dbObject = new DBObject();
		}
		return dbObject;
	}

	@Override
	public Integer getCounter() {
		return getDbObject().getCounter();
	}

	@Override
	public String getType() {
		return getDbObject().getType();
	}

	public void setType(String type) {
		this.getDbObject().setType(type);
	}

	@Override
	public String getUuid() {
		return this.getDbObject().getUuid();
	}

//	public void setUuid(String uuid) {
//		this.getDbObject().setUuid(uuid);
//	}

	@Override
	public Integer getVersion() {
		return getDbObject().getVersion();
	}

//	public void setVersion(Integer version) {
//		this.version = version;
//	}

	@Override
	public String getName() {
		return getDbObject().getName();
	}

	@Override
	public void setName(String name) {
		this.getDbObject().setName(name);
	}

	@Override
	public String getCreatedBy() {
		return getDbObject().getCreatedBy();
	}

	@Override
	public void setCreatedBy(String createdBy) {
		this.getDbObject().setCreatedBy(createdBy);
	}

	@Override
	public Date getCreatedOn() {
		return this.getDbObject().getCreatedOn();
	}

//	public void setCreatedOn(Date createdOn) {
//		this.createdOn = createdOn;
//	}

	@Override
	public String getModifiedBy() {
		return this.getDbObject().getModifiedBy();
	}

	@Override
	public void setModifiedBy(String modifiedBy) {
		this.getDbObject().setModifiedBy(modifiedBy);
	}

	@Override
	public Date getModifiedOn() {
		return this.getDbObject().getModifiedOn();
	}

	/*
	public void setModifiedOn(Date modifiedOn) {
		this.getDbObject().setModifiedOn(modifiedOn);
	}
	*/

	@Override
	public Short getStatus() {
		return this.getDbObject().getStatus();
	}

	@Override
	public void setStatus(Short status) {
		this.getDbObject().setStatus(status);
	}

	@Override
	public Date getStatusModifiedOn() {
		return this.getDbObject().getStatusModifiedOn();
	}

	@Override
	public void setStatusModifiedOn(Date statusModifiedOn) {
		this.getDbObject().setStatusModifiedOn(statusModifiedOn);
	}
	
	public String toString() {
		if(dbObject != null) {
			return "uuid=" + dbObject.getUuid() + ", name=" + dbObject.getName();
		}
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public BaseDTO createDTO() {
		return null;
	}

}
