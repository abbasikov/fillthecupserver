package com.macys.domain.impl;

import com.macys.dao.database.DBObject;
import com.macys.domain.Lab;
import com.macys.domain.business.BusinessObject;
import com.macys.domain.business.BusinessObjectImpl;
import com.macys.domain.business.common.BusinessObjectTypeEnum;
import com.macys.domain.business.common.annotations.PersistentBusinessObject;
import com.macys.utils.AppUtils;
import com.macys.valuesobjects.BaseDTO;
import com.macys.valuesobjects.LabVo;

@PersistentBusinessObject(type=BusinessObjectTypeEnum.LAB)
public class LabImpl extends BusinessObjectImpl implements Lab {

	public LabImpl(DBObject dbObject) {
		super(dbObject);
		// TODO Auto-generated constructor stub
	}
	
	private String description;
	
	private String location;

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
	public BaseDTO createDTO(){
		LabVo vo = new LabVo();
		vo.name = this.getName();
		vo.type = this.getType();
		vo.uuid = this.getUuid();
		vo.createdOnISO8601	= AppUtils.getDateISO8601(this.getCreatedOn());
		
		vo.description 	= this.getDescription();
		vo.location		= this.getLocation();
		
		return vo;
	}
	
	

}
