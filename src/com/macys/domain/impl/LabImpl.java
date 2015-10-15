package com.macys.domain.impl;

import com.macys.dao.database.DBObject;
import com.macys.domain.Lab;
import com.macys.domain.business.BusinessObjectImpl;
import com.macys.domain.business.common.BusinessObjectTypeEnum;
import com.macys.domain.business.common.annotations.PersistentBusinessObject;
import com.macys.domain.business.common.annotations.PersistentMetadata;
import com.macys.utils.AppUtils;
import com.macys.valuesobjects.BaseDTO;
import com.macys.valuesobjects.LabVo;

@PersistentBusinessObject(type=BusinessObjectTypeEnum.LAB)
public class LabImpl extends BusinessObjectImpl implements Lab {

	public LabImpl(DBObject dbObject) {
		super(dbObject);
		// TODO Auto-generated constructor stub
	}
	
	@PersistentMetadata
	private String managerName;
	
	@PersistentMetadata
	private String pdmName;
	
	@PersistentMetadata
	private String description;
	
	@PersistentMetadata
	private String location;
	
	@PersistentMetadata
	private String lastClicked;
	
	@PersistentMetadata
	private String isActivated;

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
	public String getManagerName() {
		return managerName;
	}

	@Override
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	@Override
	public String getPdmName() {
		return pdmName;
	}

	@Override
	public void setPdmName(String pdmName) {
		this.pdmName = pdmName;
	}
	
	@Override
	public String getLastClicked() {
		return lastClicked;
	}

	@Override
	public void setLastClicked(String lastClicked) {
		this.lastClicked = lastClicked;
	}
	
	@Override
	public String getIsActivated() {
		return isActivated;
	}

	@Override
	public void setIsActivated(String isActivated) {
		this.isActivated = isActivated;
	}

	@Override
	public BaseDTO createDTO(){
		LabVo vo = new LabVo();
		vo.name = this.getName();
		vo.type = this.getType();
		vo.uuid = this.getUuid();
		vo.createdOnISO8601	= AppUtils.getDateISO8601(this.getCreatedOn());
		vo.createdBy		= this.getCreatedBy();
		
		vo.description 	= this.getDescription();
		vo.location		= this.getLocation();
		vo.pdmName		= this.getPdmName();
		vo.managerName	= this.getManagerName();
		vo.lastClicked	= this.getLastClicked();
		vo.isActivated	= this.getIsActivated();
		
		return vo;
	}
	
	

}
